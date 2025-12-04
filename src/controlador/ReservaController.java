package controlador;

import dao.*;
import modelo.*;
import util.*;
import vista.reserva.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ReservaController implements ActionListener {

    private ReservaPanel vista;
    private ReservaDAO dao;
    private DetalleReservaDAO detalleDAO;
    private ClienteDAO clienteDAO;
    private EmpleadoDAO empleadoDAO;
    private VehiculoDAO vehiculoDAO;

    public ReservaController(ReservaPanel vista) {
        this.vista = vista;
        this.dao = new ReservaDAO();
        this.detalleDAO = new DetalleReservaDAO();
        this.clienteDAO = new ClienteDAO();
        this.empleadoDAO = new EmpleadoDAO();
        this.vehiculoDAO = new VehiculoDAO();

        // Asignar listeners
        vista.btnNuevaReserva.addActionListener(this);
        vista.btnVerDetalle.addActionListener(this);
        vista.btnEliminarReserva.addActionListener(this);
        vista.btnBuscar.addActionListener(this);
        vista.btnFiltrarFechas.addActionListener(this);

        // Cargar datos iniciales
        cargarReservas();
    }

    /**
     * Carga todas las reservas en la tabla
     */
    private void cargarReservas() {
        List<Reserva> lista = dao.listarReservas();
        vista.cargarReservas(lista);
    }

    /**
     * Abre el diálogo para crear una nueva reserva
     */
    private void abrirDialogoNuevaReserva() {
        ReservaDialog dlg = new ReservaDialog(null, true);

        // Cargar clientes y empleados en los combos
        cargarClientesEnCombo(dlg);
        cargarEmpleadosEnCombo(dlg);

        // Configurar fechas por defecto
        LocalDate hoy = LocalDate.now();
        dlg.txtFechaInicio.setText(hoy.format(DateTimeFormatter.ISO_LOCAL_DATE));
        dlg.txtFechaFin.setText(hoy.plusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE));

        // Listener para agregar vehículo
        dlg.btnAgregarVehiculo.addActionListener(e -> {
            agregarVehiculoAReserva(dlg);
        });

        // Listener para quitar detalle
        dlg.btnQuitarDetalle.addActionListener(e -> {
            dlg.quitarDetalle();
        });

        // Listener para guardar
        dlg.btnGuardar.addActionListener(e -> {
            if (guardarReserva(dlg)) {
                MensajeUtil.info(dlg, "Reserva realizada correctamente.");
                dlg.dispose();
                cargarReservas();
            }
        });

        // Listener para cancelar
        dlg.btnCancelar.addActionListener(e -> dlg.dispose());

        dlg.setVisible(true);
    }

    /**
     * Carga clientes en el combo del diálogo
     */
    private void cargarClientesEnCombo(ReservaDialog dlg) {
        List<Cliente> clientes = clienteDAO.listarClientes();
        dlg.cboCliente.removeAllItems();
        
        for (Cliente c : clientes) {
            dlg.cboCliente.addItem(c.getIdCliente() + " - " + c.getNombre());
        }
    }

    /**
     * Carga empleados en el combo del diálogo
     */
    private void cargarEmpleadosEnCombo(ReservaDialog dlg) {
        List<Empleado> empleados = empleadoDAO.listarEmpleados();
        dlg.cboEmpleado.removeAllItems();
        
        for (Empleado e : empleados) {
            dlg.cboEmpleado.addItem(e.getIdEmpleado() + " - " + e.getNombre());
        }
    }

    /**
     * Abre selector de vehículos y agrega el seleccionado a la tabla de detalles
     */
    private void agregarVehiculoAReserva(ReservaDialog dlg) {
        // Validar fechas primero
        String fechaInicio = dlg.txtFechaInicio.getText().trim();
        String fechaFin = dlg.txtFechaFin.getText().trim();

        if (!ValidacionUtil.rangoFechasValido(fechaInicio, fechaFin)) {
            MensajeUtil.error(dlg, "Debe ingresar un rango de fechas válido antes de agregar vehículos.");
            return;
        }

        // Calcular días
        int dias = calcularDias(fechaInicio, fechaFin);

        // Abrir selector de vehículos
        VehiculoSelectorDialog selector = new VehiculoSelectorDialog(null, true);
        List<Vehiculo> vehiculosDisponibles = vehiculoDAO.listarVehiculosDisponibles();
        selector.cargarVehiculos(vehiculosDisponibles);

        selector.btnSeleccionar.addActionListener(e -> {
            Vehiculo vehSeleccionado = selector.getVehiculoSeleccionado();
            
            if (vehSeleccionado == null) {
                MensajeUtil.error(selector, "Seleccione un vehículo.");
                return;
            }

            // Verificar disponibilidad en las fechas
            if (!dao.verificarDisponibilidad(vehSeleccionado.getIdVehiculo(), fechaInicio, fechaFin)) {
                MensajeUtil.error(selector, "El vehículo no está disponible en las fechas seleccionadas.");
                return;
            }

            // Crear detalle y agregar a la tabla
            DetalleReserva detalle = new DetalleReserva();
            detalle.setIdVehiculo(vehSeleccionado.getIdVehiculo());
            detalle.setPlacaVehiculo(vehSeleccionado.getPlaca());
            detalle.setPrecioDia(vehSeleccionado.getPrecioDia());
            detalle.setDias(dias);
            detalle.setSubtotal(vehSeleccionado.getPrecioDia() * dias);

            dlg.agregarDetalle(detalle);
            selector.dispose();
        });

        selector.btnCancelar.addActionListener(e -> selector.dispose());
        selector.setVisible(true);
    }

    /**
     * Calcula días entre dos fechas
     */
    private int calcularDias(String fechaInicio, String fechaFin) {
        try {
            LocalDate inicio = LocalDate.parse(fechaInicio);
            LocalDate fin = LocalDate.parse(fechaFin);
            long dias = ChronoUnit.DAYS.between(inicio, fin);
            return dias > 0 ? (int) dias : 1;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * Guarda la reserva con validaciones completas
     */
    private boolean guardarReserva(ReservaDialog dlg) {
        // Validar fechas
        String fechaInicio = dlg.txtFechaInicio.getText().trim();
        String fechaFin = dlg.txtFechaFin.getText().trim();

        if (!ValidacionUtil.rangoFechasValido(fechaInicio, fechaFin)) {
            MensajeUtil.error(dlg, "Rango de fechas inválido.");
            return false;
        }

        // Validar que haya detalles
        List<DetalleReserva> detalles = dlg.getDetalles();
        if (detalles.isEmpty()) {
            MensajeUtil.error(dlg, "Debe agregar al menos un vehículo a la reserva.");
            return false;
        }

        // Validar selección de cliente y empleado
        if (dlg.cboCliente.getSelectedIndex() == -1) {
            MensajeUtil.error(dlg, "Seleccione un cliente.");
            return false;
        }

        if (dlg.cboEmpleado.getSelectedIndex() == -1) {
            MensajeUtil.error(dlg, "Seleccione un empleado.");
            return false;
        }

        // Crear reserva
        Reserva r = new Reserva();
        r.setIdCliente(dlg.getClienteSeleccionado());
        r.setIdEmpleado(dlg.getEmpleadoSeleccionado());
        r.setFechaInicio(fechaInicio);
        r.setFechaFin(fechaFin);

        // Generar JSON para el stored procedure
        String json = JSONUtil.generarJSONDetalles(detalles);

        // Insertar en la BD
        return dao.insertarReservaCompleta(r, detalles, json);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnNuevaReserva) {
            abrirDialogoNuevaReserva();
        }

        if (e.getSource() == vista.btnVerDetalle) {
            int idReserva = vista.getReservaSeleccionada();
            if (idReserva == -1) {
                MensajeUtil.error(vista, "Seleccione una reserva.");
                return;
            }

            List<DetalleReserva> detalles = detalleDAO.listarDetallesPorReserva(idReserva);
            vista.mostrarDetalle(detalles);
        }

        if (e.getSource() == vista.btnEliminarReserva) {
            int idReserva = vista.getReservaSeleccionada();
            if (idReserva == -1) {
                MensajeUtil.error(vista, "Seleccione una reserva.");
                return;
            }

            if (MensajeUtil.confirmar(vista, "¿Está seguro de eliminar esta reserva?")) {
                if (dao.eliminarReserva(idReserva)) {
                    MensajeUtil.info(vista, "Reserva eliminada correctamente.");
                    cargarReservas();
                } else {
                    MensajeUtil.error(vista, "Error al eliminar la reserva.");
                }
            }
        }

        if (e.getSource() == vista.btnBuscar) {
            String filtro = vista.txtBuscar.getText().trim();
            vista.filtrar(filtro);
        }

        if (e.getSource() == vista.btnFiltrarFechas) {
            String fechaInicio = vista.getFechaInicio();
            String fechaFin = vista.getFechaFin();

            if (ValidacionUtil.esVacio(fechaInicio) || ValidacionUtil.esVacio(fechaFin)) {
                MensajeUtil.error(vista, "Ingrese ambas fechas para filtrar.");
                return;
            }

            if (!ValidacionUtil.rangoFechasValido(fechaInicio, fechaFin)) {
                MensajeUtil.error(vista, "Rango de fechas inválido.");
                return;
            }

            List<Reserva> reservasFiltradas = dao.listarReservasPorFechas(fechaInicio, fechaFin);
            vista.cargarReservas(reservasFiltradas);
        }
    }
}