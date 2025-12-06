package controlador;

import dao.EmpleadoDAO;
import modelo.Empleado;
import util.MensajeUtil;
import util.ValidacionUtil;
import vista.empleado.EmpleadoDialog;
import vista.empleado.EmpleadoPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para el módulo de Empleados
 * Implementa CRUD completo con validaciones
 */
public class EmpleadoController implements ActionListener {

    private EmpleadoPanel vista;
    private EmpleadoDAO dao;

    /**
     * Constructor - Inicializa el controlador
     * @param vista Panel de empleados
     */
    public EmpleadoController(EmpleadoPanel vista) {
        this.vista = vista;
        this.dao = new EmpleadoDAO();

        // Registrar listeners
        vista.btnNuevo.addActionListener(this);
        vista.btnEditar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnBuscar.addActionListener(this);

        // Cargar datos iniciales
        cargarTabla();
    }

    /**
     * Carga todos los empleados en la tabla
     */
    private void cargarTabla() {
        List<Empleado> lista = dao.listarEmpleados();
        vista.cargarTabla(lista);
    }

    /**
     * Abre el diálogo para crear/editar empleado
     * @param e Empleado a editar (null para nuevo)
     */
    private void abrirDialogo(Empleado e) {
        EmpleadoDialog dlg = new EmpleadoDialog(null, true);

        // Si es edición, cargar datos
        if (e != null) {
            dlg.txtId.setText(String.valueOf(e.getIdEmpleado()));
            dlg.txtNombre.setText(e.getNombre());
            dlg.txtCargo.setText(e.getCargo());
            dlg.txtUsuario.setText(e.getUsuario());
            dlg.txtClave.setText(e.getClave());
        }

        // Listener para guardar
        dlg.btnGuardar.addActionListener(ev -> {
            if (guardar(dlg)) {
                MensajeUtil.info(dlg, "Empleado guardado correctamente.");
                dlg.dispose();
                cargarTabla();
            }
        });

        // Listener para cancelar
        dlg.btnCancelar.addActionListener(ev -> dlg.dispose());

        dlg.setVisible(true);
    }

    /**
     * Guarda el empleado con validaciones completas
     * @param dlg Diálogo con los datos
     * @return true si se guardó correctamente
     */
    private boolean guardar(EmpleadoDialog dlg) {
        // ===== VALIDACIONES =====
        String nombre = dlg.txtNombre.getText().trim();
        String cargo = dlg.txtCargo.getText().trim();
        String usuario = dlg.txtUsuario.getText().trim();
        String clave = dlg.txtClave.getText().trim();

        if (ValidacionUtil.esVacio(nombre)) {
            MensajeUtil.error(dlg, "El nombre es obligatorio.");
            dlg.txtNombre.requestFocus();
            return false;
        }

        if (ValidacionUtil.esVacio(usuario)) {
            MensajeUtil.error(dlg, "El usuario es obligatorio.");
            dlg.txtUsuario.requestFocus();
            return false;
        }

        if (ValidacionUtil.esVacio(clave)) {
            MensajeUtil.error(dlg, "La clave es obligatoria.");
            dlg.txtClave.requestFocus();
            return false;
        }

        // Validar longitud de usuario
        if (usuario.length() < 3) {
            MensajeUtil.error(dlg, "El usuario debe tener al menos 3 caracteres.");
            dlg.txtUsuario.requestFocus();
            return false;
        }

        // Validar longitud de clave
        if (clave.length() < 4) {
            MensajeUtil.error(dlg, "La clave debe tener al menos 4 caracteres.");
            dlg.txtClave.requestFocus();
            return false;
        }

        // ===== CREAR OBJETO EMPLEADO =====
        Empleado e = new Empleado();
        e.setNombre(nombre);
        e.setCargo(cargo.isEmpty() ? "Sin cargo" : cargo);
        e.setUsuario(usuario);
        e.setClave(clave);

        // ===== INSERTAR O ACTUALIZAR =====
        boolean exito;
        if (dlg.txtId.getText().isEmpty()) {
            // Insertar nuevo
            exito = dao.insertarEmpleado(e);
            if (!exito) {
                MensajeUtil.error(dlg, "Error al guardar. Verifique que el usuario no esté duplicado.");
            }
        } else {
            // Actualizar existente
            e.setIdEmpleado(Integer.parseInt(dlg.txtId.getText()));
            exito = dao.actualizarEmpleado(e);
            if (!exito) {
                MensajeUtil.error(dlg, "Error al actualizar el empleado.");
            }
        }

        return exito;
    }

    /**
     * Realiza búsqueda/filtrado en la tabla
     * @param filtro Texto a buscar
     */
    private void buscar(String filtro) {
        if (ValidacionUtil.esVacio(filtro)) {
            // Si está vacío, mostrar todos
            cargarTabla();
            return;
        }

        // Filtrar empleados
        List<Empleado> lista = dao.listarEmpleados();
        String filtroLower = filtro.toLowerCase();

        List<Empleado> filtrados = lista.stream()
            .filter(emp -> 
                emp.getNombre().toLowerCase().contains(filtroLower) ||
                emp.getCargo().toLowerCase().contains(filtroLower) ||
                emp.getUsuario().toLowerCase().contains(filtroLower)
            )
            .collect(Collectors.toList());

        vista.cargarTabla(filtrados);

        // Mensaje si no hay resultados
        if (filtrados.isEmpty()) {
            MensajeUtil.info(vista, "No se encontraron empleados con el criterio: " + filtro);
        }
    }

    /**
     * Maneja todos los eventos de los botones
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // ===== BOTÓN NUEVO =====
        if (e.getSource() == vista.btnNuevo) {
            abrirDialogo(null);
        }

        // ===== BOTÓN EDITAR =====
        if (e.getSource() == vista.btnEditar) {
            Empleado emp = vista.getEmpleadoSeleccionado();
            
            if (emp == null) {
                MensajeUtil.error(vista, "Debe seleccionar un empleado de la tabla.");
                return;
            }
            
            abrirDialogo(emp);
        }

        // ===== BOTÓN ELIMINAR =====
        if (e.getSource() == vista.btnEliminar) {
            Empleado emp = vista.getEmpleadoSeleccionado();
            
            if (emp == null) {
                MensajeUtil.error(vista, "Debe seleccionar un empleado de la tabla.");
                return;
            }

            // Confirmar eliminación
            String mensaje = String.format(
                "¿Está seguro de eliminar al empleado?\n\nNombre: %s\nUsuario: %s",
                emp.getNombre(), emp.getUsuario()
            );

            if (MensajeUtil.confirmar(vista, mensaje)) {
                boolean exito = dao.eliminarEmpleado(emp.getIdEmpleado());
                
                if (exito) {
                    MensajeUtil.info(vista, "Empleado eliminado correctamente.");
                    cargarTabla();
                } else {
                    MensajeUtil.error(vista, 
                        "No se puede eliminar este empleado.\n" +
                        "Puede tener reservas asociadas.");
                }
            }
        }

        // ===== BOTÓN BUSCAR =====
        if (e.getSource() == vista.btnBuscar) {
            String filtro = vista.txtBuscar.getText().trim();
            buscar(filtro);
        }
    }
}