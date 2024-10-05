package Partefinal;

import javax.swing.*;

import Parte2.CrearObraInternalFrame;
import Parte2.Practica03_c;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener {
    private JDesktopPane Escritorio;
    private JMenuBar BarraMenu;
    private JMenu Moperacion, Mconfiguracion, Msalir;
    private JMenuItem SMsalida, SMcategorias, SMinsumos, SMobras; // Agregado SMobras

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Main() {
        setTitle("Practica 05");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        
        Escritorio = new JDesktopPane();
        setContentPane(Escritorio);
        Escritorio.setLayout(new BorderLayout());

        BarraMenu = new JMenuBar();
        setJMenuBar(BarraMenu);

        Moperacion = new JMenu("Operación");
        Mconfiguracion = new JMenu("Configuración");
        Msalir = new JMenu("Salir");

        BarraMenu.add(Moperacion);
        BarraMenu.add(Mconfiguracion);
        BarraMenu.add(Msalir);

        SMcategorias = new JMenuItem("Categorías");
        SMinsumos = new JMenuItem("Insumos");
        SMobras = new JMenuItem("Obras"); // Nuevo ítem de menú para obras
        SMsalida = new JMenuItem("Salida");

        Mconfiguracion.add(SMcategorias);
        Mconfiguracion.add(SMinsumos);
        Mconfiguracion.add(SMobras); // Agregar a la configuración
        Msalir.add(SMsalida);

        SMcategorias.addActionListener(this);
        SMinsumos.addActionListener(this);
        SMobras.addActionListener(this); // Agregar ActionListener
        SMsalida.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.SMsalida) {
            this.dispose();
        } else if (e.getSource() == this.SMcategorias) {
            // Crear y mostrar la ventana interna de Practica03_c
            Practica03_c internalFrame = new Practica03_c(); // Cambia esto a Practica03_c
            Escritorio.add(internalFrame);
            internalFrame.setVisible(true);
            try {
                internalFrame.setSelected(true); 
            } catch (java.beans.PropertyVetoException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == this.SMinsumos) {
        	Practica03_c internalFrame = new Practica03_c(); // Asegúrate de que este constructor exista
            Escritorio.add(internalFrame);
            internalFrame.setVisible(true);
            try {
                internalFrame.setSelected(true);
            } catch (java.beans.PropertyVetoException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == this.SMobras) { // Manejar la acción de obras
            CrearObraInternalFrame internalFrame = new CrearObraInternalFrame(); // Asegúrate de que este constructor exista
            Escritorio.add(internalFrame);
            internalFrame.setVisible(true);
            try {
                internalFrame.setSelected(true);
            } catch (java.beans.PropertyVetoException e1) {
                e1.printStackTrace();
            }
        }
    }
}