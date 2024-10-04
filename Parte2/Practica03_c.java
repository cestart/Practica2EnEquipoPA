package Parte2; 

import Modelo.Categoria;
import Modelo.ListaCategorias;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Practica03_c extends JInternalFrame implements ActionListener {
    ListaCategorias listaCategorias; // Maneja las categorías
    private JTextField Tid, Tcategoria;
    private JButton Bagregar, Beliminar, Bsalir;
    private JTable Tcategorias;
    private DefaultTableModel modeloTabla;
    private JPanel panelFormulario;

    public Practica03_c() {
        super("Administración de Categorías", true, true, true, true);
        this.listaCategorias = new ListaCategorias();
        cargarCategoriasDesdeArchivo();

        setBounds(100, 100, 500, 400);
        panelFormulario = new JPanel();
        panelFormulario.setLayout(null);
        getContentPane().add(panelFormulario, BorderLayout.CENTER);

        // Etiqueta y Campo de ID
        JLabel labelId = new JLabel("ID:");
        labelId.setBounds(10, 20, 71, 20);
        panelFormulario.add(labelId);

        Tid = new JTextField(10);
        Tid.setEditable(false);
        Tid.setBounds(100, 20, 147, 20);
        panelFormulario.add(Tid);

        // Etiqueta y Campo de Categoría
        JLabel labelCategoria = new JLabel("Categoría:");
        labelCategoria.setBounds(10, 60, 71, 20);
        panelFormulario.add(labelCategoria);

        Tcategoria = new JTextField(20);
        Tcategoria.setEditable(false); // Inicia desactivado
        Tcategoria.setBounds(100, 60, 147, 20);
        panelFormulario.add(Tcategoria);

        // Botones
        Bagregar = new JButton("Agregar");
        Bagregar.setBounds(20, 100, 100, 30);
        Bagregar.addActionListener(this);
        panelFormulario.add(Bagregar);

        Beliminar = new JButton("Eliminar");
        Beliminar.setBounds(140, 100, 100, 30);
        Beliminar.addActionListener(this);
        panelFormulario.add(Beliminar);

        Bsalir = new JButton("Salir");
        Bsalir.setBounds(260, 100, 100, 30);
        Bsalir.addActionListener(this);
        panelFormulario.add(Bsalir);

        // Crear tabla
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Categoría"}, 0) {
            // Hacer que las celdas no sean editables
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Tcategorias = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(Tcategorias);
        scrollPane.setBounds(10, 150, 460, 200);
        panelFormulario.add(scrollPane);

        // Mostrar categorías cargadas
        mostrarCategoriasEnTabla();
    }

    // Cargar categorías desde el archivo de texto
    private void cargarCategoriasDesdeArchivo() {
        File archivo = new File("categorias.txt");
        if (archivo.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                ArrayList<String> datos = new ArrayList<>();
                String linea;
                while ((linea = br.readLine()) != null) {
                    datos.add(linea);
                }
                listaCategorias.cargar(datos);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + e.getMessage());
            }
        }
    }

    // Mostrar categorías en el JTable
    private void mostrarCategoriasEnTabla() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        String[] categorias = listaCategorias.CategoriasArreglo();
        for (String cat : categorias) {
            String[] partes = cat.split(" - ");
            if (partes.length == 2) {
                modeloTabla.addRow(partes);
            }
        }
    }

    // Guardar categorías en el archivo de texto
    private void guardarEnArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("categorias.txt"))) {
            String[] categorias = listaCategorias.CategoriasArreglo();
            for (String cat : categorias) {
                String[] partes = cat.split(" - ");
                if (partes.length == 2) {
                    bw.write(partes[0] + "\t" + partes[1]);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar en el archivo: " + e.getMessage());
        }
    }

    // Volver al estado inicial
    public void Volveralinicio() {
        this.Bagregar.setText("Agregar");
        this.Bsalir.setText("Salir");
        this.Beliminar.setEnabled(true);
        this.Tid.setEditable(false);
        this.Tcategoria.setEditable(false); // Se desactiva de nuevo
        this.Tid.setText("");
        this.Tcategoria.setText("");
    }

    // Método para agregar una nueva categoría
    public void Altas() {
        if (this.Bagregar.getText().equals("Agregar")) {
            this.Bagregar.setText("Salvar");
            this.Bsalir.setText("Cancelar");
            this.Beliminar.setEnabled(false);
            this.Tid.setEditable(true);
            this.Tcategoria.setEditable(true); // Se activa al agregar
            this.Tid.setText(generarNuevoId()); // Generar ID automáticamente
            this.Tcategoria.requestFocus();
        } else {
            String id = this.Tid.getText().trim();
            String nombre = this.Tcategoria.getText().trim();

            if (id.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Los campos ID y Categoría no pueden estar vacíos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (existeId(id)) {
                JOptionPane.showMessageDialog(this, "El ID " + id + " ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Categoria nuevaCategoria = new Categoria(id, nombre);
            listaCategorias.agregarCategoria(nuevaCategoria);
            mostrarCategoriasEnTabla();
            guardarEnArchivo();
            Volveralinicio();
        }
    }

    // Método para verificar si un ID ya existe
    private boolean existeId(String id) {
        String[] categorias = listaCategorias.CategoriasArreglo();
        for (String cat : categorias) {
            String[] partes = cat.split(" - ");
            if (partes.length >= 1 && partes[0].equals(id)) {
                return true;
            }
        }
        return false;
    }

    // Método para eliminar una categoría
    public void Eliminar() {
        String[] categorias = listaCategorias.CategoriasArreglo();
        if (categorias.length == 0) {
            JOptionPane.showMessageDialog(this, "No hay categorías para eliminar.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String categoriaSeleccionada = (String) JOptionPane.showInputDialog(
                this,
                "Selecciona una categoría para eliminar:",
                "Eliminación de Categorías",
                JOptionPane.PLAIN_MESSAGE,
                null,
                categorias,
                categorias[0]
        );

        if (categoriaSeleccionada != null) {
            String[] partes = categoriaSeleccionada.split(" - ");
            if (partes.length == 2) {
                String id = partes[0];
                String nombre = partes[1];

                int confirmacion = JOptionPane.showConfirmDialog(
                        this,
                        "¿Estás seguro de que deseas eliminar la categoría \"" + nombre + "\"?",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmacion == JOptionPane.YES_OPTION) {
                    // Crear una nueva lista sin la categoría eliminada
                    ArrayList<Categoria> nuevaLista = new ArrayList<>();
                    for (String cat : categorias) {
                        String[] p = cat.split(" - ");
                        if (p.length == 2 && !p[0].equals(id)) {
                            nuevaLista.add(new Categoria(p[0], p[1]));
                        }
                    }

                    // Reiniciar la lista de categorías
                    listaCategorias = new ListaCategorias();
                    ArrayList<String> datos = new ArrayList<>();
                    for (Categoria cat : nuevaLista) {
                        datos.add(cat.getIdcategoria() + "\t" + cat.getNombre());
                    }
                    listaCategorias.cargar(datos);

                    // Actualizar la tabla y el archivo
                    mostrarCategoriasEnTabla();
                    guardarEnArchivo();

                    JOptionPane.showMessageDialog(this, "Categoría eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Bagregar) {
            Altas();
        } else if (e.getSource() == Beliminar) {
            Eliminar();
        } else if (e.getSource() == Bsalir) {
            dispose();
        }
    }

    // Método para generar un nuevo ID automáticamente
    private String generarNuevoId() {
        int maxId = 0;
        String[] categorias = listaCategorias.CategoriasArreglo();
        for (String cat : categorias) {
            String[] partes = cat.split(" - ");
            try {
                int id = Integer.parseInt(partes[0]);
                if (id > maxId) {
                    maxId = id;
                }
            } catch (NumberFormatException ex) {
                // Ignorar errores de formato
            }
        }
        return String.valueOf(maxId + 1);
    }
}