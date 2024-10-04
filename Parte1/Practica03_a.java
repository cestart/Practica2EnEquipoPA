package Parte1.Practica2;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import Libreria.Practica2.Archivotxt;
import Modelo.Practica2.ListaCategorias;
import Modelo.Practica2.ListaInsumos;
import Modelo.Practica2.Categoria;
import Modelo.Practica2.Insumo;

public class Practica03_a extends JFrame implements ActionListener {
    private ListaInsumos listaInsumos;
    private ListaCategorias listaCategorias;
    private Archivotxt archivoInsumos;
    private Archivotxt archivoCategorias;
    private JTable tablaInsumos;
    private NonEditableTableModel modeloTabla;
    private JTextField Tid, Tinsumo;
    private JButton Bagregar, Beliminar, Bguardar, Bsalir, Bmodificar;
    private JList<String> listaCategoriasJList;
    private DefaultListModel<String> modeloCategorias;
    private JLabel imagenLabel;

    public Practica03_a() {
        super("Administración de Insumos con JTable");

        listaInsumos = new ListaInsumos();
        listaCategorias = new ListaCategorias();
        archivoInsumos = new Archivotxt("insumos.txt");
        archivoCategorias = new Archivotxt("categorias.txt");

        setBounds(0, 0, 600, 500);
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(null);
        getContentPane().add(panelFormulario, BorderLayout.CENTER);
        JLabel labelId = new JLabel("ID:");
        labelId.setBounds(10, 10, 100, 20);
        Tid = new JTextField(10);
        Tid.setEditable(false);
        Tid.setBounds(120, 10, 150, 20);
        panelFormulario.add(labelId);
        panelFormulario.add(Tid);

        JLabel labelInsumo = new JLabel("Insumo:");
        labelInsumo.setBounds(10, 40, 100, 20);
        Tinsumo = new JTextField(20);
        Tinsumo.setEditable(false);
        Tinsumo.setBounds(120, 40, 150, 20);
        panelFormulario.add(labelInsumo);
        panelFormulario.add(Tinsumo);

        JLabel labelCategoria = new JLabel("Categoría:");
        labelCategoria.setBounds(10, 70, 100, 20);
        modeloCategorias = new DefaultListModel<>();
        listaCategoriasJList = new JList<>(modeloCategorias);
        JScrollPane scrollCategorias = new JScrollPane(listaCategoriasJList);
        scrollCategorias.setBounds(120, 70, 150, 60);
        panelFormulario.add(labelCategoria);
        panelFormulario.add(scrollCategorias);

        Bagregar = new JButton("Agregar Insumo");
        Bagregar.setBounds(20, 150, 130, 20);
        Bagregar.addActionListener(this);
        panelFormulario.add(Bagregar);

        Beliminar = new JButton("Eliminar Insumo");
        Beliminar.setBounds(160, 150, 130, 20);
        Beliminar.addActionListener(this);
        panelFormulario.add(Beliminar);

        Bmodificar = new JButton("Modificar Insumo");
        Bmodificar.setBounds(300, 150, 130, 20);
        Bmodificar.addActionListener(this);
        panelFormulario.add(Bmodificar);

        Bguardar = new JButton("Guardar Insumos");
        Bguardar.setBounds(440, 150, 130, 20);
        Bguardar.addActionListener(this);
        panelFormulario.add(Bguardar);

        Bsalir = new JButton("Salir");
        Bsalir.setBounds(440, 180, 130, 20);
        Bsalir.addActionListener(this);
        panelFormulario.add(Bsalir);

        String[] columnas = {"ID", "Insumo", "Categoría"};
        modeloTabla = new NonEditableTableModel(columnas, 0);
        tablaInsumos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaInsumos);
        scrollTabla.setBounds(10, 200, 570, 150);
        panelFormulario.add(scrollTabla);

        imagenLabel = new JLabel();
        imagenLabel.setBounds(10, 370, 150, 150);
        panelFormulario.add(imagenLabel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                    cargarImagen(id);
                }
            }
            VolverAlInicio();
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

    private void cargarImagen(String id) {
        String ruta = "Imágenes/" + id + ".png";  
        System.out.println("Cargando imagen desde: " + ruta);
        File archivo = new File(ruta);
        if (archivo.exists()) {
            ImageIcon imagen = new ImageIcon(ruta);
            imagenLabel.setIcon(scaleImage(imagen));
            imagenLabel.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró la imagen: " + ruta);
            imagenLabel.setIcon(null);
        }
    }

    private ImageIcon scaleImage(ImageIcon imageIcon) {
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(imagenLabel.getWidth(), imagenLabel.getHeight(), Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public void Eliminar() {
        int selectedRow = tablaInsumos.getSelectedRow();
        if (selectedRow != -1) {
            String id = modeloTabla.getValueAt(selectedRow, 0).toString();
            if (listaInsumos.eliminarInsumoPorId(id)) {
                JOptionPane.showMessageDialog(this, "No se encontró el insumo con ID: " + id);
            } else {
                actualizarTabla();
                imagenLabel.setIcon(null);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un insumo para eliminar.");
        }
    }

    private void VolverAlInicio() {
        Tid.setEditable(false);
        Tinsumo.setEditable(false);
        Tid.setText("");
        Tinsumo.setText("");
        listaCategoriasJList.clearSelection();
        imagenLabel.setIcon(null);

        Bagregar.setEnabled(true);
        Beliminar.setEnabled(true);
        Bmodificar.setEnabled(true);
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
            if (Bmodificar.getText().equals("Modificar Insumo")) {
                Modificar();
                Bmodificar.setText("Salvar Cambios");
            } else if (Bmodificar.getText().equals("Salvar Cambios")) {
                String id = Tid.getText().trim();
                String nuevoInsumo = Tinsumo.getText().trim();
                String idCategoria = listaCategoriasJList.getSelectedValue().split(" - ")[0];

                if (!nuevoInsumo.isEmpty()) {
                    Insumo insumoModificado = new Insumo(id, nuevoInsumo, idCategoria);
                    listaInsumos.modificarInsumo(insumoModificado); 

                    actualizarTabla(); 
                    JOptionPane.showMessageDialog(this, "Insumo modificado con éxito.");
                    Bmodificar.setText("Modificar Insumo");
                    VolverAlInicio();
                } else {
                    JOptionPane.showMessageDialog(this, "El insumo no puede estar vacío.");
                }
            }
        }
    }
    
    public static void main(String[] args) {
        new Practica03_a();
    }

    class NonEditableTableModel extends DefaultTableModel {
        public NonEditableTableModel(Object[] columnNames, int rowCount) {
            super(columnNames, rowCount);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
