package ec.edu.taller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GestorIncidentes {

    private Queue<IncidenteSeguridad> colaIncidentes;
    private int capacidadMaxima;

    public GestorIncidentes(int capacidadMaxima) {

        this.colaIncidentes = null;
    }

    public boolean registrarIncidente(IncidenteSeguridad incidente) {
        // TODO: implementar validaciones y registro en cola
        return false;
    }

    public boolean existeIncidente(String codigo) {
        // TODO: verificar si existe un incidente por código
        return false;
    }

    public IncidenteSeguridad consultarSiguienteIncidente() {
        // TODO: consultar sin eliminar
        return null;
    }

    public IncidenteSeguridad atenderSiguienteIncidente() {
        // TODO: eliminar el primero de la cola y cambiar estado a ATENDIDO
        return null;
    }

    public int contarIncidentesPendientes() {
        // TODO: retornar cantidad de incidentes pendientes
        return 0;
    }

    public int consultarEspaciosDisponibles() {
        // TODO: retornar capacidad disponible
        return 0;
    }

    public List<IncidenteSeguridad> listarIncidentes() {
        // TODO: retornar copia de los incidentes pendientes sin modificar la cola
        return new ArrayList<>();
    }
}
