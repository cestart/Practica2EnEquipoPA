package Parte2.Practica2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Practica05 extends JFrame implements ActionListener {
    private JDesktopPane Escritorio;
    private JMenuBar BarraMenu;
    private JMenu Moperacion, Mconfiguracion, Msalir;
    private JMenuItem SMsalida, SMcategorias, SMinsumos;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Practica05 window = new Practica05();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Practica05() {
        setTitle("Practica 05");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        
        Escritorio = new JDesktopPane();
        setContentPane(Escritorio);
        Escritorio.setLayout(new BorderLayout());

        BarraMenu = new JMenuBar();
        setJMenuBar(BarraMenu);

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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.SMsalida) {
            this.dispose();
        } else if (e.getSource() == this.SMcategorias) {
            JOptionPane.showMessageDialog(this, "Llamando a Conceptos");
        } else if (e.getSource() == this.SMinsumos) {
            Practica03_b internalFrame = new Practica03_b(this);
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
