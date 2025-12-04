package modelo;

public class Empleado {

    private int idEmpleado;
    private String nombre;
    private String cargo;
    private String usuario;
    private String clave;

    public Empleado() {
    }

    public Empleado(int idEmpleado, String nombre, String cargo, String usuario, String clave) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.cargo = cargo;
        this.usuario = usuario;
        this.clave = clave;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
