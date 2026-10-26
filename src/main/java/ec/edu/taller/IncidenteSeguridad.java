package ec.edu.taller;

public class IncidenteSeguridad {

    private String codigo;
    private String tipo;
    private String descripcion;
    private int prioridad;
    private String estado;

    public IncidenteSeguridad(String codigo, String tipo, String descripcion, int prioridad) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.estado = "";
    }

    
    @Override
    public String toString() {
        return codigo + " | " + tipo + " | " + prioridad + " | " + estado;
    }
}
