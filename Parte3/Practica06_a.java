package Parte3.Practica2;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Libreria.Practica2.Archivotxt;
import Modelo.Practica2.ListaCategorias;
import Modelo.Practica2.ListaInsumos;
import Modelo.Practica2.Categoria;
import Modelo.Practica2.Insumo;

public class Practica06_a extends JFrame implements ActionListener {
    private ListaInsumos listaInsumos;
    private ListaCategorias listaCategorias;
    private Archivotxt archivoInsumos;
    private Archivotxt archivoCategorias;
    private JTextField Tid, Tinsumo;
    private JButton Bagregar, Beliminar, Bguardar, Bsalir, Bmodificar;
    private JList<String> listaCategoriasJList;
    private DefaultListModel<String> modeloCategorias;
    private JTable tablaInsumos;
    private DefaultTableModel modeloTabla;

    public Practica06_a() {
        super("Administración de Insumos con FlowLayout");

        listaInsumos = new ListaInsumos();
        listaCategorias = new ListaCategorias();
        archivoInsumos = new Archivotxt("insumos.txt");
        archivoCategorias = new Archivotxt("categorias.txt");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new BorderLayout()); 

        JPanel panelInputs = new JPanel(new FlowLayout());
        
        JLabel labelId = new JLabel("ID:");
        Tid = new JTextField(10);
        Tid.setEditable(false);
        panelInputs.add(labelId);
        panelInputs.add(Tid);

        JLabel labelInsumo = new JLabel("Insumo:");
        Tinsumo = new JTextField(20);
        Tinsumo.setEditable(false);
        panelInputs.add(labelInsumo);
        panelInputs.add(Tinsumo);

        JLabel labelCategoria = new JLabel("Categoría:");
        modeloCategorias = new DefaultListModel<>();
        listaCategoriasJList = new JList<>(modeloCategorias);
        JScrollPane scrollCategorias = new JScrollPane(listaCategoriasJList);
        scrollCategorias.setPreferredSize(new Dimension(150, 60)); 
        panelInputs.add(labelCategoria);
        panelInputs.add(scrollCategorias);

        JPanel panelButtons = new JPanel(new FlowLayout());
        
        Bagregar = new JButton("Agregar Insumo");
        Bagregar.addActionListener(this);
        panelButtons.add(Bagregar);

        Beliminar = new JButton("Eliminar Insumo");
        Beliminar.addActionListener(this);
        panelButtons.add(Beliminar);

        Bmodificar = new JButton("Modificar Insumo");
        Bmodificar.addActionListener(this);
        panelButtons.add(Bmodificar);

        Bguardar = new JButton("Guardar Insumos");
        Bguardar.addActionListener(this);
        panelButtons.add(Bguardar);

        Bsalir = new JButton("Salir");
        Bsalir.addActionListener(this);
        panelButtons.add(Bsalir);

        String[] columnas = {"ID", "Insumo", "Categoría"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaInsumos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaInsumos);
        scrollTabla.setPreferredSize(new Dimension(570, 150));

        add(panelInputs, BorderLayout.NORTH); 
        add(scrollTabla, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH); 
        setVisible(true);
        cargarInsumos();
        inicializarCategorias();
    }

    private void cargarInsumos() {
        if (archivoInsumos.existeArchivo()) {
            listaInsumos.cargar(archivoInsumos.leer());
            actualizarTabla(); 
        }
    }

    private void inicializarCategorias() {
        Categoria nodo1 = new Categoria("01", "Materiales");
        Categoria nodo2 = new Categoria("02", "Mano de Obra");
        Categoria nodo3 = new Categoria("03", "Maquinaria y Equipo");
        Categoria nodo4 = new Categoria("04", "Servicios");

        listaCategorias.agregarCategoria(nodo1);
        listaCategorias.agregarCategoria(nodo2);
        listaCategorias.agregarCategoria(nodo3);
        listaCategorias.agregarCategoria(nodo4);

        for (String categoria : listaCategorias.CategoriasArreglo()) {
            modeloCategorias.addElement(categoria);
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0); 
        for (Insumo insumo : listaInsumos.getInsumos()) {
            modeloTabla.addRow(new Object[]{insumo.getIdProducto(), insumo.getProducto(), insumo.getIdCategoria()});
        }
    }

    private void guardarInsumos() {
        archivoInsumos.guardar(listaInsumos.toArchivo());
        JOptionPane.showMessageDialog(this, "Insumos guardados con éxito.");
    }

    public void Altas() {
        if (Bagregar.getText().equals("Agregar Insumo")) {
            Bagregar.setText("Salvar");
            Bsalir.setText("Cancelar");
            Tid.setEditable(true);
            Tinsumo.setEditable(true);
        } else {
            if (!Tid.getText().trim().isEmpty() && !Tinsumo.getText().trim().isEmpty() && listaCategoriasJList.getSelectedValue() != null) {
                String id = Tid.getText().trim();
                String insumo = Tinsumo.getText().trim();
                String idCategoria = listaCategoriasJList.getSelectedValue().split(" - ")[0];
                Insumo nuevoInsumo = new Insumo(id, insumo, idCategoria);

                if (listaInsumos.agregarInsumo(nuevoInsumo)) {
                    JOptionPane.showMessageDialog(this, "ID duplicado: " + id);
                } else {
                    actualizarTabla();
                }
            }
            VolverAlInicio();
        }
    }

    public void Eliminar() {
        Object[] opciones = listaInsumos.idInsumos();
        String id = (String) JOptionPane.showInputDialog(this, "Seleccione un insumo:", "Eliminar Insumo", JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        if (id != null && !id.isEmpty()) {
            if (listaInsumos.eliminarInsumoPorId(id)) {
                JOptionPane.showMessageDialog(this, "No se encontró el insumo con ID: " + id);
            } else {
                actualizarTabla(); 
            }
        }
    }

    public void Modificar() {
        Object[] opciones = listaInsumos.idInsumos();
        String id = (String) JOptionPane.showInputDialog(this, "Seleccione un insumo:", "Modificar Insumo", JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        if (id != null && !id.isEmpty()) {
            Insumo insumo = listaInsumos.buscarInsumo(id);
            if (insumo != null) {
                Tid.setText(insumo.getIdProducto());
                Tinsumo.setText(insumo.getProducto());
                for (int i = 0; i < listaCategoriasJList.getModel().getSize(); i++) {
                    if (listaCategoriasJList.getModel().getElementAt(i).contains(insumo.getIdCategoria())) {
                        listaCategoriasJList.setSelectedIndex(i);
                        break;
                    }
                }

                Tid.setEditable(true);
                Tinsumo.setEditable(true);
                Bagregar.setText("Salvar Cambios");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el insumo con ID: " + id);
            }
        }
    }

    private void VolverAlInicio() {
        Bagregar.setText("Agregar Insumo");
        Bsalir.setText("Salir");
        Tid.setEditable(false);
        Tinsumo.setEditable(false);
        Tid.setText("");
        Tinsumo.setText("");
        listaCategoriasJList.clearSelection();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Bagregar) {
            Altas();
        } else if (e.getSource() == Beliminar) {
            Eliminar();
        } else if (e.getSource() == Bguardar) {
            guardarInsumos();
        } else if (e.getSource() == Bsalir) {
            dispose();
        } else if (e.getSource() == Bmodificar) {
            Modificar();
        }
    }

    public static void main(String[] args) {
        new Practica06_a();
    }
}
