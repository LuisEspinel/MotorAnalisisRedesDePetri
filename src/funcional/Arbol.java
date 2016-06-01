/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funcional;

import java.util.ArrayList;

/**
 *
 * @author luis
 */
public class Arbol {
    private final int AUMENTO_NIVEL=50;
    private int nivel;
    public Nodo root;
    public ArrayList<Nodo> listaNodos;
    public ArrayList<Nodo> hojas;
    
    public Arbol(){
        root=null; 
        listaNodos=new ArrayList<>();
        hojas=new ArrayList<>();
    }
    public Arbol(Nodo n){        
        root=n;
        root.posX=600;
        root.posY=20;
        nivel=1;
        listaNodos=new ArrayList<>();
        listaNodos.add(n);
        hojas=new ArrayList<>();
    }
    
    public void agregarNodo(Nodo padre,Nodo hijo){
        padre.hijos.add(hijo);
        listaNodos.add(hijo);
    }
    
    public void printArbol(Nodo nodo,StringBuffer cadena){        
        if(nodo != null){
            cadena.append(nodo.printNodo());
            for(Nodo n : nodo.hijos)
                printArbol(n,cadena);
        }
    }
    
    public void obtenerNodosHojas(){
        for(Nodo nodo : listaNodos)
            if(nodo.hijos.isEmpty()|| nodo.hijos == null)
                hojas.add(nodo);
    }
    
    public boolean esHoja(Nodo nodo){
        return nodo.hijos.isEmpty() || nodo.hijos == null;
    }
    
    public ArrayList podarArbol(){
        obtenerNodosHojas();
        ArrayList<Nodo> listaADisparar=new ArrayList<>();
        for(Nodo nodo : hojas){            
            if(buscarNodoProfundo(nodo))
                listaADisparar.add(nodo);
        }
        return listaADisparar;
    }
    
    public boolean buscarNodoProfundo(Nodo nodo){
        for(Nodo n : listaNodos){
            if(!n.id.equals(nodo.id) && nodo.compararVectorNodo(n.marca) && nodo.nivel < n.nivel && esHoja(nodo)){                
                n.hijos=new ArrayList<>();
                return true;
            }
        }
        return false;
    }
    
    public void asignarPosicionANodos(Nodo nodo,int posX,int posY){
        if( ! nodo.hijos.isEmpty() || nodo.hijos != null){
            int nuevaPosX,nuevaPosY;
            if(nodo.hijos.size() == 1)
                nuevaPosX=posX;                            
            else{
                int tamanioBloqueHijos=nodo.hijos.size()*150;
                nuevaPosX=posX - (tamanioBloqueHijos / 2);
            }           
            nuevaPosY=posY+AUMENTO_NIVEL;
            boolean tieneHijosPar=(nodo.hijos.size() % 2 == 0);
            for(int i=0;i < nodo.hijos.size();i++){
                Nodo n=nodo.hijos.get(i);
                if(tieneHijosPar && i == (nodo.hijos.size() / 2))
                    nuevaPosX+=150;                
                n.posX=nuevaPosX; n.posY=nuevaPosY;
                nuevaPosX+=150;
                asignarPosicionANodos(n, n.posX, n.posY);
            }            
        }
    }
        
}
