package Parte1.Practica2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Libreria.Practica2.Archivotxt;
import Modelo.Practica2.ListaCategorias;
import Modelo.Practica2.ListaInsumos;
import Modelo.Practica2.Categoria;
import Modelo.Practica2.Insumo;

public class Practica01_b extends JFrame implements ActionListener {
    private ListaInsumos listaInsumos;
    private ListaCategorias listaCategorias;
    private Archivotxt archivoInsumos;
    private Archivotxt archivoCategorias;
    private JList<String> listaCategoriasJList;
    private DefaultListModel<String> modeloCategorias;
    private JTextField Tid, Tinsumo;
    private JButton Bagregar, Beliminar, Bguardar, Bsalir, Bmodificar; 
    private JTextArea areaProductos;
    private JPanel panelFormulario;

    public Practica01_b() {
        super("Administración de Insumos");

        listaInsumos = new ListaInsumos();
        listaCategorias = new ListaCategorias();
        archivoInsumos = new Archivotxt("insumos.txt");
        archivoCategorias = new Archivotxt("categorias.txt");

        setBounds(0, 0, 400, 450); 
        panelFormulario = new JPanel();
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
        Bagregar.setBounds(20, 150, 126, 20);
        Bagregar.addActionListener(this);
        panelFormulario.add(Bagregar);

        Beliminar = new JButton("Eliminar");
        Beliminar.setBounds(156, 150, 100, 20);
        Beliminar.addActionListener(this);
        panelFormulario.add(Beliminar);

        Bmodificar = new JButton("Modificar");  
        Bmodificar.setBounds(20, 180, 126, 20);
        Bmodificar.addActionListener(this);
        panelFormulario.add(Bmodificar);

        Bguardar = new JButton("Guardar");
        Bguardar.setBounds(156, 181, 100, 20);
        Bguardar.addActionListener(this);
        panelFormulario.add(Bguardar);

        Bsalir = new JButton("Salir");
        Bsalir.setBounds(274, 180, 100, 20);
        Bsalir.addActionListener(this);
        panelFormulario.add(Bsalir);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 220, 370, 150);
        panelFormulario.add(scrollPane);
        areaProductos = new JTextArea(10, 40);
        scrollPane.setViewportView(areaProductos);
        areaProductos.setEditable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        cargarInsumos();
        inicializarCategorias();
    }

    private void cargarInsumos() {
        if (archivoInsumos.existeArchivo()) {
            listaInsumos.cargar(archivoInsumos.leer());
            areaProductos.setText(listaInsumos.toString());
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

    private void guardarInsumos() {
        archivoInsumos.guardar(listaInsumos.toArchivo());
        JOptionPane.showMessageDialog(this, "Insumos guardados con éxito.");
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

    public void Altas() {
        if (Bagregar.getText().equals("Agregar Insumo")) {
            Bagregar.setText("Salvar");
            Bsalir.setText("Cancelar");
            Beliminar.setEnabled(false);
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
                    areaProductos.setText(listaInsumos.toString());
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
                areaProductos.setText(listaInsumos.toString());
            }
        }
    }

    private void VolverAlInicio() {
        Bagregar.setText("Agregar Insumo");
        Bsalir.setText("Salir");
        Beliminar.setEnabled(true);
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
        } else if (e.getSource() == Bmodificar) {
            Modificar();
        } else if (e.getSource() == Bguardar) {
            guardarInsumos();
        } else if (e.getSource() == Bsalir) {
            if (Bsalir.getText().equals("Cancelar")) {
                VolverAlInicio();
            } else {
                dispose();
            }
        }
    }

    public static void main(String[] args) {
        new Practica01_b();
    }
}
