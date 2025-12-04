package modelo;

public class Cliente {

    private int idCliente;
    private String nombre;
    private String identificacion;
    private String telefono;
    private String correo;
    private String direccion;
    private String fechaRegistro;

    public Cliente() {
    }

    public Cliente(int idCliente, String nombre, String identificacion, String telefono,
            String correo, String direccion, String fechaRegistro) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
