package Modelo.Practica2;

import java.util.ArrayList;

public class ListaCategorias {
    private ArrayList<Categoria> listaCategorias;

    public ListaCategorias() {
        this.listaCategorias = new ArrayList<>();
    }

    public void agregarCategoria(Categoria categoria) {
        this.listaCategorias.add(categoria);
    }

    public String[] CategoriasArreglo() {
        String[] arreglo = new String[listaCategorias.size()];
        for (int i = 0; i < listaCategorias.size(); i++) {
            Categoria categoria = listaCategorias.get(i);
            arreglo[i] = categoria.getIdcategoria() + " - " + categoria.getNombre();
        }
        return arreglo;
    }

    public void cargar(ArrayList<String> datos) {
        for (String linea : datos) {
            String[] partes = linea.split("\t");
            if (partes.length == 2) {
                Categoria categoria = new Categoria(partes[0], partes[1]);
                agregarCategoria(categoria);
            }
        }
    }

}
