package Parte2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CrearObraInternalFrame extends JInternalFrame {
    private int idObra; // ID oculto
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JButton btnGuardar;

    public CrearObraInternalFrame() {
        setTitle("Crear Obra");
        setSize(353, 325);
        setResizable(true); // Permitir redimensionar
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null); // Usar null layout para permitir el tamaño editable

        // Generar automáticamente el ID de la obra
        idObra = generarIdObra();

        // Campo para el nombre de la obra
        JLabel lblNombre = new JLabel("Nombre de la obra:");
        lblNombre.setBounds(10, 11, 150, 25); // Establecer posición y tamaño
        txtNombre = new JTextField();
        txtNombre.setBounds(20, 47, 307, 25); // Establecer posición y tamaño

        // Campo para la descripción de la obra
        JLabel lblDescripcion = new JLabel("Descripción de la obra:");
        lblDescripcion.setBounds(10, 85, 150, 25); // Establecer posición y tamaño
        txtDescripcion = new JTextField();
        txtDescripcion.setBounds(10, 121, 317, 122); // Establecer posición y tamaño

        // Botón para guardar la obra
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(227, 254, 100, 30); // Establecer posición y tamaño

        // Agregar ActionListener al botón
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarObra();
            }
        });

        // Agregar componentes al internal frame
        getContentPane().add(lblNombre);
        getContentPane().add(txtNombre);
        getContentPane().add(lblDescripcion);
        getContentPane().add(txtDescripcion);
        getContentPane().add(btnGuardar);
    }

    private int generarIdObra() {
        // Lógica para generar un ID único (puedes personalizar esto)
        return (int) (Math.random() * 10000);
    }

    private void guardarObra() {
        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("obras.txt", true))) {
            writer.write("ID: " + idObra + ", Nombre: " + nombre + ", Descripción: " + descripcion);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Obra guardada exitosamente.");
            txtNombre.setText("");
            txtDescripcion.setText("");
            idObra = generarIdObra(); // Generar un nuevo ID para la próxima obra
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la obra: " + e.getMessage());
        }
    }

    // Método para mostrar el internal frame en un JDesktopPane
    public static void main(String[] args) {
        JFrame frame = new JFrame("Aplicación de Obras");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JDesktopPane desktopPane = new JDesktopPane();
        frame.getContentPane().add(desktopPane);

        CrearObraInternalFrame crearObraFrame = new CrearObraInternalFrame();
        desktopPane.add(crearObraFrame);
        crearObraFrame.setVisible(true); // Mostrar el internal frame

        frame.setVisible(true);
    }
}