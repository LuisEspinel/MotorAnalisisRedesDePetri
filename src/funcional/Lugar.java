package funcional;

import java.util.ArrayList;

public final class Lugar {
    private String nombre;
    private boolean marcado;
    private int nummarcas;
    private ArrayList tsalidas;
    private ArrayList tentradas;
    
    public Lugar(String nombre,boolean marcado,int nummarcas,ArrayList tout,ArrayList tin){
        setNombre(nombre);
        setMarcado(marcado);  
        setNummarcas(nummarcas);
        this.tsalidas=tout;
        this.tentradas=tin;
    }            

    public ArrayList getTsalidas() {
        return tsalidas;
    }

    public void setTsalidas(ArrayList tsalidas) {
        this.tsalidas = tsalidas;
    }

    public ArrayList getTentradas() {
        return tentradas;
    }

    public void setTentradas(ArrayList tentradas) {
        this.tentradas = tentradas;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public boolean getMarcado(){
        return this.marcado;
    }
    
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    
    public void setMarcado(boolean marcado){
        this.marcado=marcado;
    }
    
    public void setNummarcas(int nummarcas){
        this.nummarcas=nummarcas;
    }
    
    public int getNummarcados(){
        return this.nummarcas;
    }
}
