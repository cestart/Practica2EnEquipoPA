package Parte2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Practica05_c extends JFrame implements ActionListener {
    private JDesktopPane Escritorio;
    private JMenuBar BarraMenu;
    private JMenu Moperacion, Mconfiguracion, Msalir;
    private JMenuItem SMsalida, SMcategorias, SMinsumos;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Practica05_c window = new Practica05_c(); // Corrige el nombre de la clase aquí
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Practica05_c() {
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
        SMsalida = new JMenuItem("Salida");

        Mconfiguracion.add(SMcategorias);
        Mconfiguracion.add(SMinsumos);
        Msalir.add(SMsalida);

        SMcategorias.addActionListener(this);
        SMinsumos.addActionListener(this);
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
        }
    }
}
