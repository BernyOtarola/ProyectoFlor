package controlador;

import dao.ClienteDAO;
import modelo.Cliente;
import util.MensajeUtil;
import util.ValidacionUtil;
import vista.cliente.ClienteDialog;
import vista.cliente.ClientePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador completo para el módulo de Clientes
 * Implementa CRUD completo con validaciones exhaustivas
 */
public class ClienteController implements ActionListener {

    private ClientePanel vista;
    private ClienteDAO dao;

    /**
     * Constructor - Inicializa el controlador
     * @param vista Panel de clientes
     */
    public ClienteController(ClientePanel vista) {
        this.vista = vista;
        this.dao = new ClienteDAO();

        // Registrar listeners
        vista.btnNuevo.addActionListener(this);
        vista.btnEditar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnBuscar.addActionListener(this);

        // Cargar datos iniciales
        cargarTabla();
    }

    /**
     * Carga todos los clientes en la tabla
     */
    private void cargarTabla() {
        List<Cliente> lista = dao.listarClientes();
        vista.cargarTabla(lista);
    }

    /**
     * Abre el diálogo para crear/editar cliente
     * @param c Cliente a editar (null para nuevo)
     */
    private void abrirDialogo(Cliente c) {
        ClienteDialog dlg = new ClienteDialog(null, true);

        // Si es edición, cargar datos
        if (c != null) {
            dlg.txtId.setText(String.valueOf(c.getIdCliente()));
            dlg.txtNombre.setText(c.getNombre());
            dlg.txtIdentificacion.setText(c.getIdentificacion());
            dlg.txtTelefono.setText(c.getTelefono());
            dlg.txtCorreo.setText(c.getCorreo());
            dlg.txtDireccion.setText(c.getDireccion());
        }

        // Listener para guardar
        dlg.btnGuardar.addActionListener(ev -> {
            if (guardarCliente(dlg)) {
                MensajeUtil.info(dlg, "Cliente guardado correctamente.");
                dlg.dispose();
                cargarTabla();
            }
        });

        // Listener para cancelar
        dlg.btnCancelar.addActionListener(ev -> dlg.dispose());

        dlg.setVisible(true);
    }

    /**
     * Guarda el cliente con validaciones completas
     * @param dlg Diálogo con los datos
     * @return true si se guardó correctamente
     */
    private boolean guardarCliente(ClienteDialog dlg) {
        // ===== VALIDACIONES =====
        String nombre = dlg.txtNombre.getText().trim();
        String identificacion = dlg.txtIdentificacion.getText().trim();
        String telefono = dlg.txtTelefono.getText().trim();
        String correo = dlg.txtCorreo.getText().trim();
        String direccion = dlg.txtDireccion.getText().trim();

        // Validar campos obligatorios
        if (ValidacionUtil.esVacio(nombre)) {
            MensajeUtil.error(dlg, "El nombre es obligatorio.");
            dlg.txtNombre.requestFocus();
            return false;
        }

        if (nombre.length() < 3) {
            MensajeUtil.error(dlg, "El nombre debe tener al menos 3 caracteres.");
            dlg.txtNombre.requestFocus();
            return false;
        }

        if (ValidacionUtil.esVacio(identificacion)) {
            MensajeUtil.error(dlg, "La identificación es obligatoria.");
            dlg.txtIdentificacion.requestFocus();
            return false;
        }

        // Validar formato de identificación (Costa Rica: #-####-####)
        if (!identificacion.matches("^\\d{1}-\\d{4}-\\d{4}$")) {
            MensajeUtil.error(dlg, 
                "Formato de identificación inválido.\n" +
                "Formato correcto: #-####-####\n" +
                "Ejemplo: 1-1234-5678");
            dlg.txtIdentificacion.requestFocus();
            return false;
        }

        // Validar teléfono (opcional pero si se ingresa debe ser válido)
        if (!ValidacionUtil.esVacio(telefono)) {
            if (!telefono.matches("^\\d{4}-\\d{4}$")) {
                MensajeUtil.error(dlg, 
                    "Formato de teléfono inválido.\n" +
                    "Formato correcto: ####-####\n" +
                    "Ejemplo: 8888-8888");
                dlg.txtTelefono.requestFocus();
                return false;
            }
        }

        // Validar correo (opcional pero si se ingresa debe ser válido)
        if (!ValidacionUtil.esVacio(correo)) {
            if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                MensajeUtil.error(dlg, 
                    "Formato de correo electrónico inválido.\n" +
                    "Ejemplo: usuario@ejemplo.com");
                dlg.txtCorreo.requestFocus();
                return false;
            }
        }

        // ===== CREAR OBJETO CLIENTE =====
        Cliente c = new Cliente();
        c.setNombre(nombre);
        c.setIdentificacion(identificacion);
        c.setTelefono(ValidacionUtil.esVacio(telefono) ? "N/A" : telefono);
        c.setCorreo(ValidacionUtil.esVacio(correo) ? "N/A" : correo);
        c.setDireccion(ValidacionUtil.esVacio(direccion) ? "N/A" : direccion);

        // ===== INSERTAR O ACTUALIZAR =====
        boolean exito;
        if (dlg.txtId.getText().isEmpty()) {
            // Insertar nuevo
            exito = dao.insertarCliente(c);
            if (!exito) {
                MensajeUtil.error(dlg, 
                    "Error al guardar el cliente.\n" +
                    "Verifique que la identificación no esté duplicada.");
            }
        } else {
            // Actualizar existente
            c.setIdCliente(Integer.parseInt(dlg.txtId.getText()));
            exito = dao.actualizarCliente(c);
            if (!exito) {
                MensajeUtil.error(dlg, "Error al actualizar el cliente.");
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

        // Filtrar clientes
        List<Cliente> lista = dao.listarClientes();
        String filtroLower = filtro.toLowerCase();

        List<Cliente> filtrados = lista.stream()
            .filter(cli -> 
                cli.getNombre().toLowerCase().contains(filtroLower) ||
                cli.getIdentificacion().contains(filtro) ||
                (cli.getTelefono() != null && cli.getTelefono().contains(filtro)) ||
                (cli.getCorreo() != null && cli.getCorreo().toLowerCase().contains(filtroLower)) ||
                (cli.getDireccion() != null && cli.getDireccion().toLowerCase().contains(filtroLower))
            )
            .collect(Collectors.toList());

        vista.cargarTabla(filtrados);

        // Mensaje si no hay resultados
        if (filtrados.isEmpty()) {
            MensajeUtil.info(vista, "No se encontraron clientes con el criterio: " + filtro);
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
            Cliente c = vista.getClienteSeleccionado();
            
            if (c == null) {
                MensajeUtil.error(vista, "Debe seleccionar un cliente de la tabla.");
                return;
            }
            
            abrirDialogo(c);
        }

        // ===== BOTÓN ELIMINAR =====
        if (e.getSource() == vista.btnEliminar) {
            Cliente c = vista.getClienteSeleccionado();
            
            if (c == null) {
                MensajeUtil.error(vista, "Debe seleccionar un cliente de la tabla.");
                return;
            }

            // Confirmar eliminación
            String mensaje = String.format(
                "¿Está seguro de eliminar el cliente?\n\n" +
                "Nombre: %s\n" +
                "Identificación: %s",
                c.getNombre(), c.getIdentificacion()
            );

            if (MensajeUtil.confirmar(vista, mensaje)) {
                boolean exito = dao.eliminarCliente(c.getIdCliente());
                
                if (exito) {
                    MensajeUtil.info(vista, "Cliente eliminado correctamente.");
                    cargarTabla();
                } else {
                    MensajeUtil.error(vista, 
                        "No se puede eliminar este cliente.\n" +
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