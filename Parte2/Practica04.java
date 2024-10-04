package Parte2.Practica2;
import javax.swing.*;
import Parte1.Practica2.Practica03_a;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Practica04 extends JFrame implements ActionListener {
    private JFrame VentanaPrincipal;
    private JMenuBar BarraMenu;
    private JMenu Moperacion, Mconfiguracion, Msalir;
    private JMenuItem SMsalida, SMcategorias, SMinsumos;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Practica04 window = new Practica04();
                    window.VentanaPrincipal.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public Practica04() {
        VentanaPrincipal = new JFrame("Practica 04");
        VentanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VentanaPrincipal.setSize(600, 400);
        VentanaPrincipal.setLayout(null);

        BarraMenu = new JMenuBar();
        VentanaPrincipal.setJMenuBar(BarraMenu);

        Moperacion = new JMenu("Operacion");
        Mconfiguracion = new JMenu("Configuracion");
        Msalir = new JMenu("Salir");

        BarraMenu.add(Moperacion);
        BarraMenu.add(Mconfiguracion);
        BarraMenu.add(Msalir);

        SMcategorias = new JMenuItem("Categorias");
        SMinsumos = new JMenuItem("Insumos");
        SMsalida = new JMenuItem("Salida");

        Mconfiguracion.add(SMcategorias);
        Mconfiguracion.add(SMinsumos);
        Msalir.add(SMsalida);

        SMcategorias.addActionListener(this);
        SMinsumos.addActionListener(this);
        SMsalida.addActionListener(this);

        VentanaPrincipal.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.SMsalida) {
            this.VentanaPrincipal.dispose();
        } else if (e.getSource() == this.SMcategorias) {
            JOptionPane.showMessageDialog(this.VentanaPrincipal, "Llamando a Conceptos");
        } else if (e.getSource() == this.SMinsumos) {

            Practica03_a h10 = new Practica03_a();
            h10.setVisible(true);
        }
    }

}