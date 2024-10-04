package Modelo.Practica2;

public class Insumo {
    private String idProducto;
    private String producto;
    private String idCategoria;

    public Insumo(String idProducto, String producto, String idCategoria) {
        this.idProducto = idProducto;
        this.producto = producto;
        this.idCategoria = idCategoria;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public String getProducto() {
        return producto;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    @Override
    public String toString() {
        return idProducto + "\t\t" + producto + "\t\t" + idCategoria;
    }
}
