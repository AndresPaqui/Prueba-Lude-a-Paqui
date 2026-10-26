package ec.edu.taller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ventana {
    private JPanel Ventana;
    private JTabbedPane tabbedPane1;
    private JPanel Registro;
    private JPanel Gestion;
    private JComboBox cmbTipo;
    private JTextArea txtDescripcion;
    private JSpinner spnPrioridad;
    private JTextField txtCodigo;
    private JButton btnRegistrar;
    private JLabel lblPendientes;
    private JLabel lblDisponibles;
    private JLabel lblCapacidad;
    private JLabel lblInfoCodigo;
    private JLabel lblInfoDescripcion;
    private JLabel lblInfoEstado;
    private JLabel lblInfoPrioridad;
    private JLabel lblInfoTipo;
    private JButton btnSiguiente;
    private JButton btnAtender;
    private JButton btnListar;
    private JTextArea txtAreaLista;
    private JButton gestionarIncidentesButton;


    public Ventana() {
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnSiguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnAtender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ventana");
        frame.setContentPane(new Ventana().Ventana);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
