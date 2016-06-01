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
public class Nodo {    
    public String id;
    public int nivel,posX,posY;
    public int marca[];
    public ArrayList<Nodo> hijos;
    
    public Nodo(int marca[],String id,int n){
        this.marca=marca;
        this.id=id;
        nivel=n;
        hijos=new ArrayList<>();
    }
    
    public String printNodo(){
        String cadena="Secuncia : "+(id.length()!=0?id:"ROOT")+"  Nivel : "+nivel+"-> ( ";
        if(marca != null){
            for(int n : marca)
                cadena+=n+"  ";
        }        
        return cadena+") \n\n";
    }
    
    public boolean compararVectorNodo(int vector[]){
        if(this.marca.length != vector.length) return false;
        for(int i=0;i < vector.length;i++){
            if(this.marca[i] != vector[i]) return false;
        }
        return true;
    }        
    
    public String printVectorNodo(){
        String cadena="( ";
        for(int n : marca)
                cadena+=n+"  ";
        return cadena+" )";
    }
    
    public String obtenerUltimoDisparo(){
        if(id.length() == 0) return "root";
        String aux=id;
        while(aux.contains(",")){            
            aux=aux.substring(aux.indexOf(",")+1,aux.length());
        }
        return aux;
    }
        
}
