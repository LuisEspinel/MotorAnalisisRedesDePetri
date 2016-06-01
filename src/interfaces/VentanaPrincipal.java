package interfaces;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import funcional.*;
import java.awt.Color;
import java.io.File;
import javax.swing.border.Border;

public class VentanaPrincipal extends JFrame implements ActionListener{
    private final JMenuBar barraMenu;
    private final JMenu menu;
    private final JMenuItem item1,item2;
    private JFileChooser buscador;
    private final JLabel et1,et3,et4;
    private final JTextField dirarchivo;
    private final JTextArea area1,area2;
    private final JScrollPane scroll1,scroll2;
    private final JButton salir,buscar,analizar,ok,limpiar;
    private final JPanel panel1,panel2;
    private final TitledBorder bordetitulo;
    private final Border borde1;
    private final JRadioButton bt1,bt2,bt3; 
    private final ButtonGroup grupobotones;
    private AnalisisRedPetri analisis;
    private File archivo;
    private String direccion;
    private boolean extendida;
    
    public VentanaPrincipal(){
        super("ANALISIS DE REDES DE PETRI");
        super.setSize(1000,160);
        super.setLocation(200,100);
        super.setResizable(false);
        super.setCursor(HAND_CURSOR);   
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.panel1=new JPanel(null);
        this.panel1.setBounds(10,10,980,90);
                
        this.barraMenu=new JMenuBar();        
        this.menu=new JMenu("¿ MÁS ?");
        this.setJMenuBar(this.barraMenu);
        this.item1=new JMenuItem("ABOUT"); 
        this.item1.addActionListener(this);
        this.menu.add(item1);        
        this.item2=new JMenuItem("SALIR");  
        this.item2.addActionListener(this);
        this.menu.add(item2);
        this.barraMenu.add(this.menu);
        
        this.bordetitulo=BorderFactory.createTitledBorder("CARGAR ARCHIVO");
        this.bordetitulo.setTitleJustification(TitledBorder.CENTER);
        this.et1=new JLabel("DIRECCION ARCHIVO");
        this.et1.setBounds(100,25,150,20);
        this.dirarchivo=new JTextField();
        this.dirarchivo.setBounds(250,25,450,20);
        this.buscar=new JButton("BUSCAR");
        this.buscar.setBounds(700,27,100,15);
        this.buscar.addActionListener(this);
        this.analizar=new JButton("ANALIZAR RED");
        this.analizar.setBounds(420,55,150,20);
        this.analizar.addActionListener(this);
        this.panel1.setBorder(this.bordetitulo);
        this.panel1.add(this.et1);
        this.panel1.add(this.dirarchivo);
        this.panel1.add(this.buscar);
        this.panel1.add(this.analizar);        
        this.area1=new JTextArea();        
        this.area1.setLineWrap(true);
        this.area1.setWrapStyleWord(true);
        this.area1.setForeground(Color.BLUE);
        this.area1.setEditable(false);
        this.scroll1=new JScrollPane(this.area1);
        this.scroll1.setBounds(5,5,400,430);
        this.borde1=BorderFactory.createLineBorder(Color.DARK_GRAY,5); 
        this.area2=new JTextArea();
        this.area2.setForeground(Color.BLUE);
        this.area2.setLineWrap(true);
        this.area2.setWrapStyleWord(true);
        this.area2.setEditable(false);
        this.scroll2=new JScrollPane(this.area2);
        this.scroll2.setBounds(575,5,400,430);
        this.panel2=new JPanel(null);
        this.panel2.setBounds(10,110,980,440);
        this.panel2.setBorder(this.borde1);
        this.panel2.add(this.scroll1);
        this.panel2.add(this.scroll2);
        this.bt1=new JRadioButton("RAFAGA DISPAROS",false);            
        this.bt2=new JRadioButton("ARBOL ALCANZABILIDAD",false);
        this.bt3=new JRadioButton("ALCANZABILIDAD",false);
        this.bt1.setBounds(406,100,167,20);
        this.bt2.setBounds(406,125,167,20);
        this.bt3.setBounds(406,150,167,20);        
        this.grupobotones=new ButtonGroup();
        this.grupobotones.add(this.bt1);
        this.grupobotones.add(this.bt2);
        this.grupobotones.add(this.bt3);        
        this.et3=new JLabel("OPCIONES DE RED");
        this.et3.setBounds(424,65,200,30);
        this.et4=new JLabel("OTRAS OPCIONES");
        this.et4.setBounds(426,300,200,30);
        this.ok=new JButton("CARGAR");
        this.ok.setBounds(437,180,100,20);
        this.ok.addActionListener(this);
        this.limpiar=new JButton("LIMPIAR");
        this.limpiar.setBounds(437,340,100,20);
        this.limpiar.addActionListener(this);
        this.salir=new JButton("SALIR");
        this.salir.setBounds(437,370,100,20); 
        this.salir.addActionListener(this);        
        this.panel2.add(this.salir);        
        this.panel2.add(this.et3);  
        this.panel2.add(this.et4);
        this.panel2.add(this.bt1);
        this.panel2.add(this.bt2);
        this.panel2.add(this.bt3);
        this.panel2.add(this.ok);
        this.panel2.add(this.limpiar);
        super.setLayout(null);
        super.getContentPane().add(this.panel1);
        super.getContentPane().add(this.panel2);       
    }
    
    public void cargarBuscadorArchivos(){
        this.buscador=new JFileChooser("/home/luigy/");        
        FileNameExtensionFilter filtro=new FileNameExtensionFilter("solo archivos xml","xml");
        this.buscador.setFileFilter(filtro);
        int seleccion=this.buscador.showOpenDialog(this.getContentPane());
        if(seleccion == JFileChooser.APPROVE_OPTION){        
            this.direccion=this.buscador.getSelectedFile().getAbsolutePath();
            this.dirarchivo.setText(this.direccion);
        }
    }
    
    public void cargarArchivo(){
        try{            
            if(this.dirarchivo.getText().equals("")){
                JOptionPane.showMessageDialog(null,"INGRESE UNA DIRECCION DE ARCHIVO","POR FAVOR",JOptionPane.INFORMATION_MESSAGE);
            }else{
                this.direccion=this.dirarchivo.getText();            
                this.archivo=new File(this.direccion);
                if(!this.archivo.exists()){
                    JOptionPane.showMessageDialog(null,"              RECOMENDACIONES\n1->REVISAR LA DIRECCION DEL ARCHIVO"
                    + "\n2->REVISAR QUE EL ARCHIVO EXISTA","ERROR",JOptionPane.ERROR_MESSAGE);
                }else{                    
                    this.analisis=new AnalisisRedPetri(this.archivo);                                                            
                    StringBuffer rs=this.analisis.analizar();
                    String r=rs.toString();
                    if(!r.equals("NO")){                          
                        agrandarVentana();
                        this.extendida=true;
                        this.area1.setText(rs.toString());
                    }else{                        
                        restaurarVentana();
                        this.extendida=false;
                        JOptionPane.showMessageDialog(null,"AL PARECER EL ARCHIVO SELECCIONADO\nNO DESCRIBE UNA RED DE PETRI","MENSAJE",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"AL PARECER SE PRODUJO UN ERROR\n INTERNO EN LA APLICACION DE TIPO: \n"
                    +e.getMessage()+"","ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public File getArchivo(){
        return this.archivo;
    }
    
    public void agrandarVentana(){
        try{
            if(this.extendida) return;
            for(int i=140;i <= 600;i+=10){
                Thread.sleep(10);
                this.setSize(1000,i);
            }                         
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"ERROR EN LA APLICACION",JOptionPane.ERROR_MESSAGE);
        }        
    }
    
    public void restaurarVentana(){
        try{
            if(!this.extendida) return;
            for(int i=600;i >= 138;i-=10){
                Thread.sleep(10);
                this.setSize(1000,i);
            }   
            this.setSize(1000,138);            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"ERROR EN LA APLICACION",JOptionPane.ERROR_MESSAGE);
        } 
    }
    
    public void salir(){
    	int seleccion=JOptionPane.showOptionDialog(this,"     ¿ QUIERE SALIR ?","SALIENDO",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[] {"si","no"},"si");
        if(seleccion != -1){
        	if(seleccion+1 == 1) System.exit(0);
        }
    }
    
    public void cargarSeleccion(){
        if(this.bt1.isSelected()){        	
            rafagaDisparos();            
        }else{
            if(this.bt2.isSelected()){
            	arbolAlcazabilidad();
            }else{
                if(this.bt3.isSelected()){
                	alcanzabilidad();
                }else{
                    JOptionPane.showMessageDialog(null,"ESCOJA UNA OPCION DE ANALISIS DE RED","MENSAJE",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }       
    
    public void rafagaDisparos(){
        try{
            String m=JOptionPane.showInputDialog(null,"Ingrese la rafaga de disparos mostrando cada transicion separado con una coma ','\n\tejemplo : t1,t2,t3","INGRESE LOS DATOS",JOptionPane.INFORMATION_MESSAGE);
            if(m.equals("")) JOptionPane.showMessageDialog(null,"NO INGRESO NINGUNA RAFAGA","ERROR",JOptionPane.INFORMATION_MESSAGE);
            else{
            	this.area2.setText("");
            	m+=".";
            	char c[]=m.toCharArray();                   	
            	this.analisis.rafagaDisparos(c);
            	this.area2.setText(this.analisis.getCalculos().toString());
            }            
        }catch(Exception e){        	
            JOptionPane.showMessageDialog(null,"OPERACION CANCELADA","CANCELADO",JOptionPane.ERROR_MESSAGE);
        }                            
    }
    
    public void alcanzabilidad(){
    	try{
    		String m=JOptionPane.showInputDialog(null,"INGRESE LA MARCACION A ANALIZAR\nEJEMPLO 1,1,0,0","INGRESE LOS DATOS",JOptionPane.INFORMATION_MESSAGE);
    		if(m.equals("")) JOptionPane.showMessageDialog(null,"NO INGRESO NINGUNA MARCACION","ERROR",JOptionPane.INFORMATION_MESSAGE);
    		else{
    			this.area2.setText("");
    			m+=".";
    			char c[]=m.toCharArray();
    			this.analisis.alcanzabilidad(c);
    			this.area2.setText(this.analisis.getCalculos().toString());
    		}
    	}catch(Exception e){
    		JOptionPane.showMessageDialog(null,"OPERACION CANCELADA","CANCELADO",JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    public void arbolAlcazabilidad(){
    	this.analisis.arbolAlcanzabilidad();
    	this.area2.setText(this.analisis.getCalculos().toString());
    }
    
    public void limpiarAreas(){
        int seleccion=JOptionPane.showOptionDialog(this,"ESTO LIMPIARA LOS RESULTADOS\n            ¿ESTA SEGURO?","ADVERTENCIA",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[] {"si, seguro","no, cancelar"},"si");
        if(seleccion != -1){
            if(seleccion+1 == 1){
                this.area1.setText("");
                this.area2.setText("");
            }
        }        
    }
    
    public void cargarAbout(){
        VentanaAbout ventanaAbout=new VentanaAbout();
        ventanaAbout.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this.buscar) cargarBuscadorArchivos();        
        if(e.getSource() == this.analizar) cargarArchivo();                     
        if(e.getSource() == this.ok) cargarSeleccion();
        if(e.getSource() == this.limpiar) limpiarAreas();
        if(e.getSource() == this.salir || e.getSource() == this.item2) salir();
        if(e.getSource() == this.item1) cargarAbout();
    }
        
}

