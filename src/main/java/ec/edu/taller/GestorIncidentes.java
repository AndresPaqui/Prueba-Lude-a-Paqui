package ec.edu.taller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class GestorIncidentes {

    private final Queue<IncidenteSeguridad> colaIncidentes;
    private final int capacidadMaxima;

    public GestorIncidentes(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.colaIncidentes = new PriorityQueue<>(
                Comparator.comparingInt(IncidenteSeguridad::getPrioridad));
    }

    public boolean registrarIncidente(IncidenteSeguridad incidente) {
        if (incidente == null
                || incidente.getCodigo() == null
                || incidente.getCodigo().trim().isEmpty()
                || colaIncidentes.size() >= capacidadMaxima
                || existeIncidente(incidente.getCodigo())) {
            return false;
        }
        return colaIncidentes.offer(incidente);
    }

    public boolean existeIncidente(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return false;
        }
        for (IncidenteSeguridad incidente : colaIncidentes) {
            if (codigo.equals(incidente.getCodigo())) {
                return true;
            }
        }
        return false;
    }

    public IncidenteSeguridad consultarSiguienteIncidente() {
        return colaIncidentes.peek();
    }

    public IncidenteSeguridad atenderSiguienteIncidente() {
        IncidenteSeguridad incidente = colaIncidentes.poll();
        if (incidente != null) {
            incidente.setEstado("ATENDIDO");
        }
        return incidente;
    }

    public int contarIncidentesPendientes() {
        return colaIncidentes.size();
    }

    public int consultarEspaciosDisponibles() {
        return capacidadMaxima - colaIncidentes.size();
    }

    public List<IncidenteSeguridad> listarIncidentes() {
        Queue<IncidenteSeguridad> copia = new PriorityQueue<>(colaIncidentes);
        List<IncidenteSeguridad> incidentes = new ArrayList<>(copia.size());
        while (!copia.isEmpty()) {
            incidentes.add(copia.poll());
        }
        return incidentes;
    }
}
