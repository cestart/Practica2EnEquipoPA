package Modelo.Practica2;

public class Categoria {
    private String idcategoria;
    private String nombre;

    public Categoria(String idcategoria, String nombre) {
        this.idcategoria = idcategoria;
        this.nombre = nombre;
    }

    public String getIdcategoria() {
        return idcategoria;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return idcategoria + " - " + nombre;
    }
}
