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

public class Practica06_c extends JFrame implements ActionListener {
    private ListaInsumos listaInsumos;
    private ListaCategorias listaCategorias;
    private Archivotxt archivoInsumos;
    private Archivotxt archivoCategorias;
    private JTextField Tid, Tinsumo;
    private JButton Bagregar, Beliminar, Bmodificar, Bguardar, Bsalir;
    private JList<String> listaCategoriasJList;
    private DefaultListModel<String> modeloCategorias;
    private DefaultTableModel modeloTabla;
    private JTable tablaInsumos;

    public Practica06_c() {
        super("Administración de Insumos");
        listaInsumos = new ListaInsumos();
        listaCategorias = new ListaCategorias();
        archivoInsumos = new Archivotxt("insumos.txt");
        archivoCategorias = new Archivotxt("categorias.txt");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new GridLayout(4, 1));

        JPanel panel1 = new JPanel(new FlowLayout());
        Tid = new JTextField(10);
        Tinsumo = new JTextField(10);
        panel1.add(new JLabel("ID:"));
        panel1.add(Tid);
        panel1.add(new JLabel("Insumo:"));
        panel1.add(Tinsumo);
        add(panel1);
        JPanel panel2 = new JPanel(new FlowLayout());
        modeloCategorias = new DefaultListModel<>();
        listaCategoriasJList = new JList<>(modeloCategorias);
        JScrollPane scrollCategorias = new JScrollPane(listaCategoriasJList);
        scrollCategorias.setPreferredSize(new Dimension(150, 60));
        panel2.add(new JLabel("Categoría:"));
        panel2.add(scrollCategorias);
        add(panel2);
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Insumo", "Categoría"}, 0);
        tablaInsumos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaInsumos);
        add(scrollTabla);
        JPanel panel4 = new JPanel(new FlowLayout());
        Bagregar = new JButton("Agregar");
        Beliminar = new JButton("Eliminar");
        Bmodificar = new JButton("Modificar");
        Bguardar = new JButton("Guardar");
        Bsalir = new JButton("Salir");

        panel4.add(Bagregar);
        panel4.add(Beliminar);
        panel4.add(Bmodificar);
        panel4.add(Bguardar);
        panel4.add(Bsalir);
        add(panel4);

        Bagregar.addActionListener(this);
        Beliminar.addActionListener(this);
        Bmodificar.addActionListener(this);
        Bguardar.addActionListener(this);
        Bsalir.addActionListener(this);

        cargarInsumos();
        inicializarCategorias();
        setVisible(true);
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
    }

    public void Eliminar() {
        int selectedRow = tablaInsumos.getSelectedRow();
        if (selectedRow != -1) {
            String id = modeloTabla.getValueAt(selectedRow, 0).toString();
            if (!listaInsumos.eliminarInsumoPorId(id)) {
                JOptionPane.showMessageDialog(this, "No se encontró el insumo con ID: " + id);
            } else {
                actualizarTabla();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un insumo para eliminar.");
        }
    }

    public void Modificar() {
        int selectedRow = tablaInsumos.getSelectedRow();
        if (selectedRow != -1) {
            String id = modeloTabla.getValueAt(selectedRow, 0).toString();
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
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el insumo con ID: " + id);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un insumo para modificar.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Bagregar) {
            Altas();
        } else if (e.getSource() == Beliminar) {
            Eliminar();
        } else if (e.getSource() == Bmodificar) {
            Modificar();
        } else if (e.getSource() == Bguardar) {
            guardarInsumos();
        } else if (e.getSource() == Bsalir) {
            dispose();
        }
    }

    public static void main(String[] args) {
        new Practica06_c();
    }
}
