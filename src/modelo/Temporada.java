package modelo;

public class Temporada {

    private int idTemporada;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private double factor;

    public Temporada() {
    }

    public Temporada(int idTemporada, String nombre, String fechaInicio,
            String fechaFin, double factor) {
        this.idTemporada = idTemporada;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.factor = factor;
    }

    public int getIdTemporada() {
        return idTemporada;
    }

    public void setIdTemporada(int idTemporada) {
        this.idTemporada = idTemporada;
    }

    public int getId() {
        return idTemporada;
    }

    public void setId(int id) {
        this.idTemporada = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public double getRecargo() {
        return (factor - 1.0) * 100;
    }

    public void setRecargo(double recargo) {
        this.factor = 1.0 + (recargo / 100.0);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
