package funcional;

public final class Transicion {
    private String nombre;
    private boolean activo;
    private int num;

    public Transicion(String nombre,int n,boolean activo){
        setNombre(nombre);
        setActivo(activo);
        setNum(n);
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public boolean getActivo(){
        return this.activo;
    }
    
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    
    public void setActivo(boolean activo){
        this.activo=activo;
    }
    
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    
}
