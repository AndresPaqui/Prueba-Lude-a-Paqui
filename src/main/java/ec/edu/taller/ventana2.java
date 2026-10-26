package ec.edu.taller;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

public class ventana2 extends JFrame {

    private JPanel panelPrincipal;
    private JLabel etiquetaTitulo;
    private JPanel panelFormulario;
    private JLabel etiquetaCodigo;
    private JTextField campoCodigo;
    private JLabel etiquetaTipo;
    private JComboBox<String> comboTipo;
    private JLabel etiquetaDescripcion;
    private JTextField campoDescripcion;
    private JLabel etiquetaPrioridad;
    private JSpinner spinnerPrioridad;
    private JPanel panelCentro;
    private JScrollPane desplazamientoIncidentes;
    private JTextArea areaIncidentes;
    private JPanel panelInferior;
    private JLabel etiquetaEspacios;
    private JPanel panelBotones;
    private JButton botonRegistrar;
    private JButton botonConsultar;
    private JButton botonListar;
    private JButton botonAtender;

    private final GestorIncidentes gestor;

    public ventana2() {
        super("Gestión de incidentes de seguridad");
        gestor = new GestorIncidentes(10);
        crearComponentes();
        conectarAcciones();
        actualizarEspacios();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelPrincipal);
        setMinimumSize(new java.awt.Dimension(720, 480));
        pack();
        setLocationRelativeTo(null);
    }

    private void crearComponentes() {
        panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));

        etiquetaTitulo = new JLabel("Gestión de incidentes de seguridad", JLabel.CENTER);
        panelPrincipal.add(etiquetaTitulo, BorderLayout.NORTH);

        panelFormulario = new JPanel(new GridLayout(4, 2, 8, 8));
        etiquetaCodigo = new JLabel("Código:");
        campoCodigo = new JTextField();
        etiquetaTipo = new JLabel("Tipo:");
        comboTipo = new JComboBox<>(new String[] {
                "PHISHING", "MALWARE", "ACCESO_NO_AUTORIZADO", "FUGA_DATOS", "OTRO"
        });
        etiquetaDescripcion = new JLabel("Descripción:");
        campoDescripcion = new JTextField();
        etiquetaPrioridad = new JLabel("Prioridad (menor número = más urgente):");
        spinnerPrioridad = new JSpinner(
                new SpinnerNumberModel(3, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
        panelFormulario.add(etiquetaCodigo);
        panelFormulario.add(campoCodigo);
        panelFormulario.add(etiquetaTipo);
        panelFormulario.add(comboTipo);
        panelFormulario.add(etiquetaDescripcion);
        panelFormulario.add(campoDescripcion);
        panelFormulario.add(etiquetaPrioridad);
        panelFormulario.add(spinnerPrioridad);

        areaIncidentes = new JTextArea(12, 60);
        areaIncidentes.setEditable(false);
        areaIncidentes.setLineWrap(true);
        areaIncidentes.setWrapStyleWord(true);
        desplazamientoIncidentes = new JScrollPane(areaIncidentes);

        panelCentro = new JPanel(new BorderLayout(8, 8));
        panelCentro.add(panelFormulario, BorderLayout.NORTH);
        panelCentro.add(desplazamientoIncidentes, BorderLayout.CENTER);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        panelInferior = new JPanel(new BorderLayout(8, 8));
        etiquetaEspacios = new JLabel();
        panelInferior.add(etiquetaEspacios, BorderLayout.NORTH);
        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        botonRegistrar = new JButton("Registrar");
        botonConsultar = new JButton("Consultar siguiente");
        botonListar = new JButton("Listar pendientes");
        botonAtender = new JButton("Atender siguiente");
        panelBotones.add(botonRegistrar);
        panelBotones.add(botonConsultar);
        panelBotones.add(botonListar);
        panelBotones.add(botonAtender);
        panelInferior.add(panelBotones, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
    }

    private void conectarAcciones() {
        botonRegistrar.addActionListener(event -> registrarIncidente());
        botonConsultar.addActionListener(event -> consultarSiguiente());
        botonListar.addActionListener(event -> listarIncidentes());
        botonAtender.addActionListener(event -> atenderSiguiente());
    }

    private void registrarIncidente() {
        IncidenteSeguridad incidente = new IncidenteSeguridad(
                campoCodigo.getText().trim(),
                (String) comboTipo.getSelectedItem(),
                campoDescripcion.getText().trim(),
                (Integer) spinnerPrioridad.getValue());
        if (gestor.registrarIncidente(incidente)) {
            campoCodigo.setText("");
            campoDescripcion.setText("");
            listarIncidentes();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo registrar: revise el código, que no esté duplicado y que haya espacio.",
                    "No se pudo registrar",
                    JOptionPane.WARNING_MESSAGE);
        }
        actualizarEspacios();
    }

    private void consultarSiguiente() {
        IncidenteSeguridad incidente = gestor.consultarSiguienteIncidente();
        areaIncidentes.setText(incidente == null
                ? "No hay incidentes pendientes."
                : "Siguiente incidente:\n" + incidente);
    }

    private void listarIncidentes() {
        List<IncidenteSeguridad> incidentes = gestor.listarIncidentes();
        if (incidentes.isEmpty()) {
            areaIncidentes.setText("No hay incidentes pendientes.");
            return;
        }
        StringBuilder contenido = new StringBuilder("Incidentes pendientes (por prioridad):\n");
        for (IncidenteSeguridad incidente : incidentes) {
            contenido.append(incidente).append('\n');
        }
        areaIncidentes.setText(contenido.toString());
    }

    private void atenderSiguiente() {
        IncidenteSeguridad incidente = gestor.atenderSiguienteIncidente();
        if (incidente == null) {
            areaIncidentes.setText("No hay incidentes pendientes para atender.");
        } else {
            areaIncidentes.setText("Incidente atendido:\n" + incidente + "\n\n");
            for (IncidenteSeguridad pendiente : gestor.listarIncidentes()) {
                areaIncidentes.append(pendiente + "\n");
            }
        }
        actualizarEspacios();
    }

    private void actualizarEspacios() {
        etiquetaEspacios.setText("Espacios disponibles: " + gestor.consultarEspaciosDisponibles());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ventana2().setVisible(true));
    }
}
