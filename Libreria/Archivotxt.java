package Libreria.Practica2;

import java.io.*;
import java.util.ArrayList;
public class Archivotxt {
    private String nombreArchivo;
    public Archivotxt(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
    public void guardar(ArrayList<String> datos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (String linea : datos) {
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> leer() {
        ArrayList<String> datos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                datos.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datos;
    }

    public boolean existeArchivo() {
        File archivo = new File(nombreArchivo);
        return archivo.exists();
    }
}
