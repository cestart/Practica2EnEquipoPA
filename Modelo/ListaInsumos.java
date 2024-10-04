package Modelo.Practica2;

import java.util.ArrayList;

public class ListaInsumos {
    private ArrayList<Insumo> listaInsumos;

    public ListaInsumos() {
        listaInsumos = new ArrayList<>();
    }

    public boolean agregarInsumo(Insumo insumo) {
        // Verificar si ya existe el insumo
        for (Insumo i : listaInsumos) {
            if (i.getIdProducto().equals(insumo.getIdProducto())) {
                return true;  // El ID ya existe
            }
        }
        listaInsumos.add(insumo);
        return false; 
    }

    public void modificarInsumo(Insumo insumoModificado) {
        for (int i = 0; i < listaInsumos.size(); i++) {
            Insumo insumo = listaInsumos.get(i);
            if (insumo.getIdProducto().equals(insumoModificado.getIdProducto())) {
                listaInsumos.set(i, insumoModificado); 
                return; 
            }
        }
    }

    public boolean eliminarInsumoPorId(String id) {
        for (Insumo insumo : listaInsumos) {
            if (insumo.getIdProducto().equals(id)) {
                listaInsumos.remove(insumo);
                return false;  
            }
        }
        return true;  
    }

    public Object[] idInsumos() {
        Object[] ids = new Object[listaInsumos.size()];
        for (int i = 0; i < listaInsumos.size(); i++) {
            ids[i] = listaInsumos.get(i).getIdProducto();
        }
        return ids;
    }

    public Insumo buscarInsumo(String id) {
        for (Insumo insumo : listaInsumos) {
            if (insumo.getIdProducto().equals(id)) {
                return insumo;
            }
        }
        return null; 
    }

    public ArrayList<Insumo> getInsumos() {
        return listaInsumos;
    }

    public void cargar(ArrayList<String> datos) {
        for (String linea : datos) {
            String[] partes = linea.split("\t\t");
            if (partes.length == 3) { 
                Insumo insumo = new Insumo(partes[0], partes[1], partes[2]);
                listaInsumos.add(insumo);
            }
        }
    }

    public ArrayList<String> toArchivo() {
        ArrayList<String> datos = new ArrayList<>();
        for (Insumo insumo : listaInsumos) {
            datos.add(insumo.toString());
        }
        return datos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID\t\tProducto\t\tCategoría\n");
        for (Insumo insumo : listaInsumos) {
            sb.append(insumo.toString()).append("\n");
        }
        return sb.toString();
    }
}
