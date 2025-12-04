package modelo;

public class DetalleReserva {

    private int idDetalle;
    private int idReserva;
    private int idVehiculo;
    private String placaVehiculo;
    private double precio;
    private double precioDia;
    private int dias;
    private double subtotal;

    public DetalleReserva() {
    }

    public DetalleReserva(int idDetalle, int idReserva, int idVehiculo,
            double precio, int dias, double subtotal) {
        this.idDetalle = idDetalle;
        this.idReserva = idReserva;
        this.idVehiculo = idVehiculo;
        this.precio = precio;
        this.precioDia = precio;
        this.dias = dias;
        this.subtotal = subtotal;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
        this.precioDia = precio;
    }

    public double getPrecioDia() {
        return precioDia;
    }

    public void setPrecioDia(double precioDia) {
        this.precioDia = precioDia;
        this.precio = precioDia;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
