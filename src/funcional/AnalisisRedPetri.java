package funcional;

import interfaces.Panel;
import org.jdom2.Document;
import org.jdom2.Element;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.jdom2.input.SAXBuilder;
import java.util.List;
import org.jdom2.JDOMException;
import java.util.ArrayList;
import javax.swing.JFrame;

public final class AnalisisRedPetri {
    private File archivo;    
    private SAXBuilder builder; 
    private StringBuffer informacion,calculos;    
    private ArrayList<Lugar> lugares;
    private ArrayList<Transicion> tin;
    private ArrayList<Transicion> tout;
    private ArrayList<String> l;
    private ArrayList<String> transiciones;
    private int marcacion[];
    private int[][] d1;
    private int[][] d2;
    private int[][] d;
    private ArrayList<double[]> vectoresArbol;
    private double exd[];
    private Matriz matriz;
    private Arbol arbolAlcanzabilidad;
    
    public AnalisisRedPetri(File archivo){        
        setArchivo(archivo);
        this.builder=new SAXBuilder();
        this.informacion=new StringBuffer(); 
        this.calculos=new StringBuffer();
        this.lugares=new ArrayList<>();        
        this.l=new ArrayList<>();
        this.transiciones=new ArrayList<>();       
        this.vectoresArbol=new ArrayList<>();
        this.matriz=new Matriz();              
    }
    
    public StringBuffer analizar(){
        try{            
            Document documento=(Document)builder.build(this.archivo);
            Element root=documento.getRootElement();
            if(root.getName().equals("RedPetri")){
                this.informacion.append("CARACTERISTICAS DE LA RED -> \n");
                List<Element> listaelementos=root.getChildren();
                if(listaelementos.size() == 1){
                    Element at1=listaelementos.get(0);                    
                    if(at1.getName().equals("lugares")){                        
                        List<Element> lugares=at1.getChildren();
                        for(int i=0;i < lugares.size();i++){
                            Element l1=lugares.get(i);
                            String nombre=l1.getAttributeValue("name");
                            String marcado=l1.getAttributeValue("marcado");
                            boolean mar=(marcado.equals("si")) ? true : false;
                            int nummarcas=Integer.valueOf(l1.getAttributeValue("num"));                            
                            List<Element> elementoslugar=l1.getChildren();
                            Element i1=elementoslugar.get(0);
                            Element i2=elementoslugar.get(1);
                            List<Element> entradas=i1.getChildren();
                            List<Element> salidas=i2.getChildren();
                            this.tin=new ArrayList<Transicion>();
                            this.tout=new ArrayList<Transicion>();
                            if(entradas.size() != 0){                                
                                for(int e=0;e < entradas.size();e++){                                    
                                    Element t=entradas.get(e);                                       
                                    String nt=t.getAttributeValue("name");
                                    int num=Integer.valueOf(t.getAttributeValue("num"));
                                    this.tin.add(new Transicion(nt,num,false));
                                }                                                                        
                            }
                            if(salidas.size() != 0){                                
                                for(int e=0;e < salidas.size();e++){
                                    Element t=salidas.get(e);  
                                    String nt=t.getAttributeValue("name");
                                    int num=Integer.valueOf(t.getAttributeValue("num"));
                                    this.tout.add(new Transicion(nt,num,false));
                                }
                            }
                            this.lugares.add(new Lugar(nombre,mar,nummarcas,this.tout,this.tin));                            
                        }                        
                        infoRedPetri();
                    }else{
                        this.informacion.append("! ERROR !\n EL DOCUMENTO PUEDE ESTAR INCOMPLETO\n"
                                + "AL PARECER FALTAN LOS LUGARES Y/O TRANSICIONES DE LA RED DE PETRI\n"
                                + "RECOMENDACIONES :::>>\n 1-> REVISE EL DOCUMENTO \""+this.archivo.getAbsolutePath()+"\".");
                    }
                }
            }else{
                this.informacion.append("NO"); 
                return getResultado();
            }
            this.informacion.append("\nFIN DE ANALISIS");
        }catch(JDOMException | IOException e){
            JOptionPane.showMessageDialog(null,e.getCause(),"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        return getResultado();
    }
    
    public void getLugares(){
        if(this.lugares.size() == 0) return;
        else{
            for(int i=0;i < this.lugares.size();i++){
                String nomlugar=this.lugares.get(i).getNombre();
                boolean existe=false;
                for(int j=0;j < this.l.size();j++){
                    existe=false;
                    if(this.l.get(j).equals(nomlugar)){
                        existe=true;
                        break;
                    }
                }
                if(!existe) this.l.add(nomlugar);
            }
        }
    }
    
    public void getTransiciones(){
        if(this.lugares.size() == 0) return;
        else{
            for(int i=0;i < this.lugares.size();i++){
                ArrayList<Transicion> tr=this.lugares.get(i).getTentradas();
                for(int j=0;j < tr.size();j++){
                    boolean existe=false;
                    String nomtran=tr.get(j).getNombre();
                    for(int k=0;k < this.transiciones.size();k++){                        
                        existe=false;
                        if(this.transiciones.get(k).equals(nomtran)){
                            existe=true;
                            break;
                        }
                    }
                    if(!existe) this.transiciones.add(nomtran);
                }                
                tr=this.lugares.get(i).getTsalidas();
                for(int j=0;j < tr.size();j++){
                    boolean existe=false;
                    String nomtran=tr.get(j).getNombre();
                    for(int k=0;k < this.transiciones.size();k++){                        
                        existe=false;
                        if(this.transiciones.get(k).equals(nomtran)){
                            existe=true;
                            break;
                        }
                    }
                    if(!existe) this.transiciones.add(nomtran);
                }
            }
        }
    }
    
    public void getMarcacionInicial(){
        this.marcacion=new int[this.lugares.size()];
        for(int i=0;i < this.lugares.size();i++){
            int num=this.lugares.get(i).getNummarcados();
            this.marcacion[i]=num;
        }
    }
    
    public void getDMas(){
        this.d2=new int[this.transiciones.size()][this.lugares.size()];
        for(int i=0;i < this.d2.length;i++){
            for(int j=0;j < this.d2[i].length;j++){
                this.d2[i][j]=0;
            }
        }
        for(int i=0;i < this.lugares.size();i++){
            Lugar l=this.lugares.get(i);
            ArrayList<Transicion> ts=l.getTentradas();
            for(int j=0;j < ts.size();j++){
                Transicion t=ts.get(j);
                String nom=t.getNombre();
                int num=t.getNum();
                for(int k=0;k < this.transiciones.size();k++){
                    if(this.transiciones.get(k).equals(nom)){
                        this.d2[k][i]=num;
                    }
                }
            }        
        }
    }
    
    public void getDMenos(){
        this.d1=new int[this.transiciones.size()][this.lugares.size()];
        for(int i=0;i < this.d1.length;i++){
            for(int j=0;j < this.d1[i].length;j++){
                this.d1[i][j]=0;
            }
        }
        for(int i=0;i < this.lugares.size();i++){
            Lugar l=this.lugares.get(i);
            ArrayList<Transicion> ts=l.getTsalidas();
            for(int j=0;j < ts.size();j++){
                Transicion t=ts.get(j);
                String nom=t.getNombre();
                int num=t.getNum();
                for(int k=0;k < this.transiciones.size();k++){
                    if(this.transiciones.get(k).equals(nom)){
                        this.d1[k][i]=num;
                    }
                }
            }
        }
    }
    
    public void getD(){
        this.d=this.matriz.restar(d2, d1);
    }
    
    public boolean habilitada(double marcacion[]){        
        double copiad[][]=this.matriz.pasarADouble(this.d);        
        exd=this.matriz.multiplicar(marcacion,copiad);        
        this.calculos.append("M*D: \n\n[");
        for(int i=0;i < exd.length;i++){
        	if(exd[i] >= 0) this.calculos.append(" "+exd[i]+"   ");
        	else this.calculos.append(exd[i]+"   ");
        }              
        this.calculos.append("]");
        marcacion=this.matriz.pasarADouble(this.marcacion);
        exd=this.matriz.sumar(marcacion, exd);
        this.calculos.append("\n\nM0 - M*D: \n\n[ ");
        for(int i=0;i < exd.length;i++){
        	if(exd[i] >= 0) this.calculos.append(" "+exd[i]+"   ");
        	else this.calculos.append(exd[i]+"   ");
        }
        this.calculos.append("]\n");
        boolean valido=true;
        for(int i=0;i < exd.length;i++){ if(exd[i] < 0 || exd[i] % 1 != 0) valido=false; }
        if(valido) this.calculos.append("\nES UNA MARCACION VALIDA.\n\n");
        else this.calculos.append("\nNO ES UNA MARCACION VALIDA.\n\n");
        return valido;
    }
    
    public ArrayList<String> sacarRafaga(char c[]){
    	ArrayList<String> rt=new ArrayList<String>();    	
    	String palabra=new String();    	
    	for(int i=0;i < c.length;i++){       		
    		if(c[i] != ',' && c[i] != '.') palabra=palabra+c[i];
    		else{    	    			
    			rt.add(palabra);
    			palabra=new String();
    		}
    	}
    	boolean valida=true;    	
    	for(int i=0;i < rt.size();i++){
    		if(this.transiciones.indexOf(rt.get(i)) == -1){
    			valida=false;    	
    			break;
    		}
    	}
    	if(valida) return rt;
    	return null;
    }
    
    private int[] getVectorDisparo(String transicion){
    	int vector[]=new int[this.transiciones.size()];
    	for(int i=0;i < this.transiciones.size();i++){
    		if(this.transiciones.get(i).equals(transicion)) vector[i]=1;
    		else vector[i]=0;
    	}
    	return vector;
    }       
    
    public double[] rafagaDisparos(char c[]){
        double vector[]=null;
    	this.calculos.setLength(0);
    	ArrayList<String> rafagas=sacarRafaga(c);
    	if(rafagas == null) {
    		JOptionPane.showMessageDialog(null,"NO ES UNA RAFAGA VALIDA","ERROR",JOptionPane.ERROR_MESSAGE);    		
    	}else{
    		ArrayList<int[]> listaVectores=new ArrayList<>();    		
    		for(int i=0;i < rafagas.size();i++){
    			int vec[]=getVectorDisparo(rafagas.get(i));
    			listaVectores.add(vec);
    		}
    		vector=this.matriz.pasarADouble(listaVectores.get(0));
    		for(int i=1;i < listaVectores.size();i++){
    			double v[]=this.matriz.pasarADouble(listaVectores.get(i));
    			vector=this.matriz.sumar(vector, v);
    		}
    		this.calculos.append("\nVECTOR DE LA RAFAGA INTRODUCIDA :\n\n[   ");
    		for(int i=0;i < vector.length;i++){
    			this.calculos.append(vector[i]+"   ");
    		}
    		this.calculos.append("]\n");    		
    		getCalculos(vector);
    	}
        return vector;
    }      
    
    public void getCalculos(double marcacion[]){  
    	getD();
        this.calculos.append("\nMATRIZ D= D+ - D-\n\n");       
        for(int i=0;i < this.d.length;i++){
            for(int j=0;j < this.d[i].length;j++){
            	if(this.d[i][j] >= 0) this.calculos.append(" "+this.d[i][j]+"   ");
            	else this.calculos.append(this.d[i][j]+"   ");
            }
            this.calculos.append("\n");
        }       
        this.calculos.append("\n");
        habilitada(marcacion);        
    } 
    
    public void arbolAlcanzabilidad(){
        if(arbolAlcanzabilidad == null){
            Nodo root=new Nodo(marcacion,"",0);
            arbolAlcanzabilidad=new Arbol(root);
            vectoresArbol.add(matriz.pasarADouble(marcacion));
            for(int i=0;i < transiciones.size();i++){
                generarArbol(transiciones.get(i),arbolAlcanzabilidad.root,1);
            }
            ArrayList<Nodo> nodosARedisparar=arbolAlcanzabilidad.podarArbol();
            for(Nodo n : nodosARedisparar){
                for(String tran : transiciones)
                    generarArbol(n.id+","+tran, n, n.nivel+1);
            }
        }
        arbolAlcanzabilidad.asignarPosicionANodos(arbolAlcanzabilidad.root,
                arbolAlcanzabilidad.root.posX, arbolAlcanzabilidad.root.posY);
        this.calculos=new StringBuffer();
        arbolAlcanzabilidad.printArbol(arbolAlcanzabilidad.root,this.calculos); 
        loadVentanaArbol();
    }        
    
    public void loadVentanaArbol(){
        JFrame ventana=new JFrame("ARBOL DE ALCANZABILIDAD");
        Panel panel=new Panel();
        ventana.setSize(1200,600);
        ventana.setLocation(100,100);        
        panel.listaNodos=arbolAlcanzabilidad.listaNodos;
        ventana.getContentPane().add(panel);
        ventana.setVisible(true);
    }
    
    public void generarArbol(String rafaga,Nodo padre,int nivel){           
        double nuevaMarcacion[]=rafagaDisparos((rafaga + ".").toCharArray());
        if(nuevaMarcacion != null && habilitada(nuevaMarcacion)){                       
            Nodo nuevo=new Nodo(matriz.pasarAEntero(exd),rafaga,nivel);
            arbolAlcanzabilidad.agregarNodo(padre, nuevo);
            if( ! existeMarcacion(exd) && ! esMarcacionW(exd)){
                vectoresArbol.add(exd);                
                for(int i=0;i < transiciones.size();i++){                    
                    generarArbol(rafaga + ","+transiciones.get(i),nuevo,nivel+1);
                }   
            }          
        }
    }   
    
    public boolean existeMarcacion(double m[]){                
        if(vectoresArbol.size() > 0){
            for(double vector[] : vectoresArbol){
                if(matriz.compararVectores(m,vector) ) return true;
            }            
        }
        return false;
    }
    
    public boolean esMarcacionW(double marca[]){
        int contadorVectores=0;        
        for(int i=0;i < vectoresArbol.size();i++){            
            int contadorNumeroCambios=0;
            double v[]=vectoresArbol.get(i);
            for(int j=0;j < v.length;j++){
                if(v[j] != marca[j]) contadorNumeroCambios++;
            }
            if(contadorNumeroCambios == 1) contadorVectores++;
        }
        return contadorVectores >= 2;
    }
    
    
    public void infoRedPetri(){
        arbolAlcanzabilidad=null;
        getTransiciones();
        getLugares();
        getMarcacionInicial();
        getDMas();
        getDMenos();
        this.informacion.append("\nLUGARES:\n\n");
        for(int i=0;i < this.l.size();i++){
            this.informacion.append("Lugar : "+this.l.get(i)+"\n");
        }
        this.informacion.append("\nTRANSICIONES:\n\n");
        for(int i=0;i < this.transiciones.size();i++){
            this.informacion.append("Transicion : "+this.transiciones.get(i)+"\n");
        }
        this.informacion.append("\nMARCACION INICIAL :\n\n[  ");
        for(int i=0;i < this.marcacion.length;i++){
            this.informacion.append(this.marcacion[i]+"  ");
        }
        this.informacion.append("]\n");
        this.informacion.append("\nMATRIZ D+ #(Pi,O(Tj)):\n\n");
        for(int i=0;i < this.d2.length;i++){
            for(int j=0;j < this.d2[i].length;j++){
                this.informacion.append(this.d2[i][j]+"  ");
            }
            this.informacion.append("\n");
        }
        this.informacion.append("\nMATRIZ D- #(Pi,I(Tj)):\n\n");
        for(int i=0;i < this.d1.length;i++){
            for(int j=0;j < this.d1[i].length;j++){
                this.informacion.append(this.d1[i][j]+"  ");
            }
            this.informacion.append("\n");
        }
    }
    
    public int[] getMarcacion(char c[]){
    	ArrayList<String> marca=new ArrayList<String>();
    	String palabra=new String();    	
    	for(int i=0;i < c.length;i++){
    		if(c[i] != ',' && c[i] != '.') palabra=palabra+c[i];
    		else{
    			marca.add(palabra);    			
    			palabra=new String();
    		}
    	} 
    	boolean valida=true;
    	int v[]=new int[marca.size()];
    	if(marca.size() == this.marcacion.length){    		
    		for(int i=0;i < marca.size();i++){
    			try{
    				palabra=marca.get(i);
    				v[i]=Integer.valueOf(palabra);    		    				
    			}catch(Exception e){
    				valida=false;
    				JOptionPane.showMessageDialog(null,"LA MARCACION NO VALIDA","ERROR",JOptionPane.ERROR_MESSAGE);
    				break;    				
    			}
    		}    		
    	}else{
    		JOptionPane.showMessageDialog(null,"LA MARCACION NO VALIDA","ERROR",JOptionPane.ERROR_MESSAGE);
    		valida=false;
    	}
    	if(valida) return v;
    	else return null;
    }
    
    public void alcanzabilidad(char c[]){  
    	this.calculos.setLength(0);
    	getD();
    	this.calculos.setLength(0);
    	int v[]=getMarcacion(c);
    	if(v != null){
    		double copiaV[]=this.matriz.pasarADouble(v);
    		double copiaD[][]=this.matriz.pasarADouble(this.d);
    		double copiaM[]=this.matriz.pasarADouble(this.marcacion);
    		double resta[]=this.matriz.restar(copiaV,copiaM);
    		this.calculos.append("MATRIZ D= D+ - D-\n\n");       
            for(int i=0;i < this.d.length;i++){
                for(int j=0;j < this.d[i].length;j++){
                	if(this.d[i][j] >= 0) this.calculos.append(" "+this.d[i][j]+"   ");
                	else this.calculos.append(this.d[i][j]+"   ");
                }
                this.calculos.append("\n");
            }       
            this.calculos.append("\n");
    		this.calculos.append("\nM1 - M0\n\n[   ");
    		for(int i=0;i < resta.length;i++){ 
    			this.calculos.append(resta[i]+"   ");
    		}
    		this.calculos.append("]\n\n");    		
    		copiaD=this.matriz.invertirMatriz(copiaD);
    		int co=copiaD[0].length;
    		copiaD=this.matriz.cuadrar(copiaD);
    		int cd=copiaD[0].length;
    		int ca=cd-co;
    		this.calculos.append("MATRIZ DEL SISTEMA DE ECUACIONES : \n\n");    		
    		for(int i=0;i < copiaD.length;i++){
    			for(int j=0;j < copiaD[i].length;j++){
    				if(copiaD[i][j] >= 0) this.calculos.append(" "+copiaD[i][j]+"   ");
    				else this.calculos.append(copiaD[i][j]+"   ");
    			}
    			this.calculos.append("\n");
    		}    		
    		
    		this.calculos.append("\n");  
    		this.calculos.append("SOLUCION : \n\n[   ");  
    		double solucion[]=this.matriz.Gauss_Jordan1(copiaD,resta);	
    		for(int i=0;i < solucion.length-ca;i++){
    			this.calculos.append(solucion[i]+"   ");
    		}
    		this.calculos.append("]\n");
    		boolean valida=true;
    		for(int i=0;i < solucion.length;i++){
    			if(solucion[i] % 1 != 0  || solucion[i] < 0) valida=false; 
    		}
    		if(valida) this.calculos.append("\nES ALCANZABLE.\n\n");
    		else this.calculos.append("\nNO ES ALCANZABLE.\n\n");
    		this.calculos.append("FIN DE ANALISIS.");
    	}
    }
    
    public void conservatividad(){
    	this.calculos.setLength(0);
    	getD();
    	this.calculos.setLength(0);
    	double copiaD[][]=this.matriz.pasarADouble(this.d);
    	copiaD=this.matriz.invertirMatriz(copiaD);
		int co=copiaD[0].length;
		copiaD=this.matriz.cuadrar(copiaD);
		int cd=copiaD[0].length;
		int ca=cd-co;
		this.calculos.append("MATRIZ DEL SISTEMA DE ECUACIONES : \n\n");    		
		for(int i=0;i < copiaD.length;i++){
			for(int j=0;j < copiaD[i].length;j++){
				if(copiaD[i][j] >= 0) this.calculos.append(" "+copiaD[i][j]+"   ");
				else this.calculos.append(copiaD[i][j]+"   ");
			}
			this.calculos.append("\n");
		} 
		this.calculos.append("\n");
		double s[]=new double[copiaD.length];
		for(int i=0;i < copiaD.length;i++){
			s[i]=0;
		}
		this.calculos.append("SOLUCION : \n\n[   "); 
		double solucion[]=this.matriz.Gauss_Jordan1(copiaD,s);	
		for(int i=0;i < solucion.length-ca;i++){
			this.calculos.append(solucion[i]+"   ");
		}
		this.calculos.append("]\n");
                boolean valido=true;
                for(int i=0;i < solucion.length;i++){
                    if(solucion[i] < 1 || solucion[i] % 1 != 0) valido=false;
                }
                if(valido) this.calculos.append("\nLA RED ES CONSERVATIVA\n");
                else this.calculos.append("\nLA RED NO ES CONSERVATIVA");
    }
    
    public StringBuffer getCalculos(){
    	return this.calculos;
    }

    public void setArchivo(File archivo) {
        this.archivo = new File(archivo.getAbsolutePath());
    }
    
    public File getArchivo(){
        return this.archivo;
    }
    
    public StringBuffer getResultado(){
        return this.informacion;
    }
}
