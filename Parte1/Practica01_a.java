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

public class Practica01_a extends JFrame implements ActionListener {
    private ListaInsumos listaInsumos;
    private ListaCategorias listaCategorias;
    private DefaultListModel<String> modeloCategorias; 
    private JTextField Tid, Tinsumo;
    private JButton Bagregar, Beliminar, Bsalir;
    private JTextArea areaProductos;
    private JList<String> listaCategoriasJList;
    private JPanel panelFormulario;

    public Practica01_a() {
        super("Administración de Insumos");
        listaInsumos = new ListaInsumos();
        listaCategorias = new ListaCategorias();
        modeloCategorias = new DefaultListModel<>(); 
        inicializarCategorias();  
        setBounds(0, 0, 400, 400);
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
        listaCategoriasJList = new JList<>(modeloCategorias); 
        JScrollPane scrollCategorias = new JScrollPane(listaCategoriasJList);
        scrollCategorias.setBounds(120, 70, 150, 60);
        panelFormulario.add(labelCategoria);
        panelFormulario.add(scrollCategorias);

        Bagregar = new JButton("Agregar");
        Bagregar.setBounds(20, 150, 100, 20);
        Bagregar.addActionListener(this);
        panelFormulario.add(Bagregar);

        Beliminar = new JButton("Eliminar");
        Beliminar.setBounds(140, 150, 100, 20);
        Beliminar.addActionListener(this);
        panelFormulario.add(Beliminar);

        Bsalir = new JButton("Salir");
        Bsalir.setBounds(260, 150, 100, 20);
        Bsalir.addActionListener(this);
        panelFormulario.add(Bsalir);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 180, 370, 150);
        panelFormulario.add(scrollPane);
        areaProductos = new JTextArea(10, 40);
        scrollPane.setViewportView(areaProductos);
        areaProductos.setEditable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void inicializarCategorias() {
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

    public void VolverAlInicio() {
        Bagregar.setText("Agregar");
        Bsalir.setText("Salir");
        Beliminar.setEnabled(true);
        Tid.setEditable(false);
        Tinsumo.setEditable(false);
        Tid.setText("");
        Tinsumo.setText("");
        listaCategoriasJList.clearSelection();
    }

    public void Altas() {
        if (Bagregar.getText().equals("Agregar")) {
            Bagregar.setText("Salvar");
            Bsalir.setText("Cancelar");
            Beliminar.setEnabled(false);
            Tid.setEditable(true);
            Tinsumo.setEditable(true);
        } else {
            if (!Tid.getText().trim().isEmpty()) {
                String id = Tid.getText().trim();
                String insumo = Tinsumo.getText().trim();
                String categoriaSeleccionada = listaCategoriasJList.getSelectedValue();
                Insumo nuevoInsumo = new Insumo(id, insumo, categoriaSeleccionada);

                if (listaInsumos.agregarInsumo(nuevoInsumo)) {
                    JOptionPane.showMessageDialog(null, "ID duplicado: " + id);
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
                JOptionPane.showMessageDialog(this, "No se encontró ese ID");
            } else {
                areaProductos.setText(listaInsumos.toString());
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
            if (Bsalir.getText().equals("Cancelar")) {
                VolverAlInicio();
            } else {
                dispose();
            }
        }
    }

    public static void main(String[] args) {
        new Practica01_a();
    }
}