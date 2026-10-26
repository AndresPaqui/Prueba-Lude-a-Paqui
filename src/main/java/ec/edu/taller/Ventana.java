package ec.edu.taller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Ventana {
    private static final int CAPACIDAD_MAXIMA = 10;

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
    private final GestorIncidentes gestorIncidentes;


    public Ventana() {
        gestorIncidentes = new GestorIncidentes(CAPACIDAD_MAXIMA);
        txtAreaLista.setEditable(false);
        actualizarContadores();

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarIncidente();
            }
        });
        btnSiguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IncidenteSeguridad incidente = gestorIncidentes.consultarSiguienteIncidente();
                mostrarIncidente(incidente);
                if (incidente == null) {
                    JOptionPane.showMessageDialog(Ventana,
                            "No hay incidentes pendientes.",
                            "Cola vacía",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        btnAtender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IncidenteSeguridad incidente = gestorIncidentes.atenderSiguienteIncidente();
                mostrarIncidente(incidente);
                actualizarContadores();
                actualizarListado();
                if (incidente == null) {
                    JOptionPane.showMessageDialog(Ventana,
                            "No hay incidentes pendientes para atender.",
                            "Cola vacía",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        btnListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarListado();
            }
        });
    }

    private void registrarIncidente() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(Ventana,
                    "Ingrese el código del incidente.",
                    "Código requerido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (gestorIncidentes.existeIncidente(codigo)) {
            JOptionPane.showMessageDialog(Ventana,
                    "Ya existe un incidente con ese código.",
                    "Código duplicado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (gestorIncidentes.consultarEspaciosDisponibles() == 0) {
            JOptionPane.showMessageDialog(Ventana,
                    "La cola alcanzó su capacidad máxima de " + CAPACIDAD_MAXIMA + " incidentes.",
                    "Cola llena",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object tipoSeleccionado = cmbTipo.getSelectedItem();
        String tipo = tipoSeleccionado == null ? "" : tipoSeleccionado.toString().trim();
        if (tipo.isEmpty()) {
            JOptionPane.showMessageDialog(Ventana,
                    "Seleccione un tipo de incidente.",
                    "Tipo requerido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int prioridad = (Integer) spnPrioridad.getValue();
        IncidenteSeguridad incidente = new IncidenteSeguridad(
                codigo,
                tipo,
                txtDescripcion.getText().trim(),
                prioridad);

        if (!gestorIncidentes.registrarIncidente(incidente)) {
            JOptionPane.showMessageDialog(Ventana,
                    "No se pudo registrar el incidente.",
                    "Error de registro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        txtCodigo.setText("");
        txtDescripcion.setText("");
        mostrarIncidente(gestorIncidentes.consultarSiguienteIncidente());
        actualizarContadores();
        actualizarListado();
        JOptionPane.showMessageDialog(Ventana,
                "Incidente registrado correctamente.",
                "Registro exitoso",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarIncidente(IncidenteSeguridad incidente) {
        if (incidente == null) {
            lblInfoCodigo.setText("CODIGO: ---");
            lblInfoTipo.setText("TIPO: ---");
            lblInfoPrioridad.setText("PRIORIDAD: ---");
            lblInfoEstado.setText("ESTADO: ---");
            lblInfoDescripcion.setText("DESCRIPCION: ---");
            return;
        }

        lblInfoCodigo.setText("CODIGO: " + incidente.getCodigo());
        lblInfoTipo.setText("TIPO: " + incidente.getTipo());
        lblInfoPrioridad.setText("PRIORIDAD: " + incidente.getPrioridad());
        lblInfoEstado.setText("ESTADO: " + incidente.getEstado());
        lblInfoDescripcion.setText("DESCRIPCION: " + incidente.getDescripcion());
    }

    private void actualizarContadores() {
        lblPendientes.setText("PENDIENTES: " + gestorIncidentes.contarIncidentesPendientes());
        lblDisponibles.setText("DISPONIBLES: " + gestorIncidentes.consultarEspaciosDisponibles());
        lblCapacidad.setText("CAPACIDAD: " + CAPACIDAD_MAXIMA);
    }

    private void actualizarListado() {
        List<IncidenteSeguridad> incidentes = gestorIncidentes.listarIncidentes();
        if (incidentes.isEmpty()) {
            txtAreaLista.setText("No hay incidentes pendientes.");
            return;
        }

        StringBuilder listado = new StringBuilder(
                "INCIDENTES PENDIENTES (menor número = mayor prioridad):\n");
        for (IncidenteSeguridad incidente : incidentes) {
            listado.append(incidente)
                    .append(" | ")
                    .append(incidente.getDescripcion())
                    .append('\n');
        }
        txtAreaLista.setText(listado.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Ventana");
            frame.setContentPane(new Ventana().Ventana);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
