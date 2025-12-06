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

/**
 * Controlador completo para el módulo de Reservas
 * Implementa gestión completa de reservas con detalles
 */
public class ReservaController implements ActionListener {

    private ReservaPanel vista;
    private ReservaDAO dao;
    private DetalleReservaDAO detalleDAO;
    private ClienteDAO clienteDAO;
    private EmpleadoDAO empleadoDAO;
    private VehiculoDAO vehiculoDAO;
    private TemporadaDAO temporadaDAO;

    /**
     * Constructor - Inicializa el controlador
     * @param vista Panel de reservas
     */
    public ReservaController(ReservaPanel vista) {
        this.vista = vista;
        this.dao = new ReservaDAO();
        this.detalleDAO = new DetalleReservaDAO();
        this.clienteDAO = new ClienteDAO();
        this.empleadoDAO = new EmpleadoDAO();
        this.vehiculoDAO = new VehiculoDAO();
        this.temporadaDAO = new TemporadaDAO();

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
        
        if (clientes.isEmpty()) {
            MensajeUtil.error(dlg, "No hay clientes registrados.\nDebe crear al menos un cliente primero.");
            return;
        }
        
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
        
        if (empleados.isEmpty()) {
            MensajeUtil.error(dlg, "No hay empleados registrados.\nDebe crear al menos un empleado primero.");
            return;
        }
        
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

        // ⭐ OBTENER FACTOR DE TEMPORADA
        double factor = obtenerFactorTemporada(fechaInicio);
        
        // Abrir selector de vehículos
        VehiculoSelectorDialog selector = new VehiculoSelectorDialog(null, true);
        List<Vehiculo> vehiculosDisponibles = vehiculoDAO.listarVehiculosDisponibles();
        
        if (vehiculosDisponibles.isEmpty()) {
            MensajeUtil.error(dlg, "No hay vehículos disponibles en este momento.");
            return;
        }
        
        selector.cargarVehiculos(vehiculosDisponibles);

        // Listener para seleccionar vehículo
        selector.btnSeleccionar.addActionListener(e -> {
            Vehiculo vehSeleccionado = selector.getVehiculoSeleccionado();
            
            if (vehSeleccionado == null) {
                MensajeUtil.error(selector, "Seleccione un vehículo de la tabla.");
                return;
            }

            // Verificar disponibilidad en las fechas
            if (!dao.verificarDisponibilidad(vehSeleccionado.getIdVehiculo(), fechaInicio, fechaFin)) {
                MensajeUtil.error(selector, 
                    "El vehículo " + vehSeleccionado.getPlaca() + 
                    " no está disponible en las fechas seleccionadas.");
                return;
            }

            // ⭐ APLICAR FACTOR DE TEMPORADA AL PRECIO
            double precioBase = vehSeleccionado.getPrecioDia();
            double precioConTemporada = precioBase * factor;

            // Crear detalle y agregar a la tabla
            DetalleReserva detalle = new DetalleReserva();
            detalle.setIdVehiculo(vehSeleccionado.getIdVehiculo());
            detalle.setPlacaVehiculo(vehSeleccionado.getPlaca());
            detalle.setPrecioDia(precioConTemporada);
            detalle.setDias(dias);
            detalle.setSubtotal(precioConTemporada * dias);

            dlg.agregarDetalle(detalle);
            
            // Mostrar mensaje si hay recargo por temporada
            if (factor > 1.0) {
                double recargoPorc = (factor - 1.0) * 100;
                MensajeUtil.info(selector, 
                    String.format("✅ Vehículo agregado\n\n" +
                                 "⚠️ Temporada Alta: +%.0f%% de recargo aplicado\n" +
                                 "Precio base: ₡%.2f\n" +
                                 "Precio con recargo: ₡%.2f", 
                                 recargoPorc, precioBase, precioConTemporada));
            }
            
            selector.dispose();
        });

        // Listener para cancelar
        selector.btnCancelar.addActionListener(e -> selector.dispose());
        
        selector.setVisible(true);
    }

    /**
     * Obtiene el factor de temporada para una fecha específica
     * @param fecha Fecha en formato yyyy-MM-dd
     * @return Factor (1.0 si no hay temporada)
     */
    private double obtenerFactorTemporada(String fecha) {
        List<Temporada> temporadas = temporadaDAO.listarTemporadas();
        
        try {
            LocalDate fechaBuscar = LocalDate.parse(fecha);
            
            for (Temporada t : temporadas) {
                LocalDate inicio = LocalDate.parse(t.getFechaInicio());
                LocalDate fin = LocalDate.parse(t.getFechaFin());
                
                // Verificar si la fecha está dentro del rango
                if (!fechaBuscar.isBefore(inicio) && !fechaBuscar.isAfter(fin)) {
                    System.out.println("✅ Temporada encontrada: " + t.getNombre() + 
                                     " (Factor: " + t.getFactor() + ")");
                    return t.getFactor();
                }
            }
        } catch (Exception e) {
            System.err.println("⚠️ Error al obtener factor de temporada: " + e.getMessage());
        }
        
        return 1.0; // Sin recargo si no hay temporada
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
            System.err.println("Error calcularDias(): " + e.getMessage());
            return 1;
        }
    }

    /**
     * Guarda la reserva con validaciones completas
     */
    private boolean guardarReserva(ReservaDialog dlg) {
        // ===== VALIDAR FECHAS =====
        String fechaInicio = dlg.txtFechaInicio.getText().trim();
        String fechaFin = dlg.txtFechaFin.getText().trim();

        if (!ValidacionUtil.rangoFechasValido(fechaInicio, fechaFin)) {
            MensajeUtil.error(dlg, 
                "Rango de fechas inválido.\n" +
                "Verifique que:\n" +
                "- El formato sea yyyy-MM-dd\n" +
                "- La fecha fin sea posterior a la fecha inicio\n" +
                "- Las fechas no sean pasadas");
            return false;
        }

        // ===== VALIDAR DETALLES =====
        List<DetalleReserva> detalles = dlg.getDetalles();
        if (detalles.isEmpty()) {
            MensajeUtil.error(dlg, "Debe agregar al menos un vehículo a la reserva.");
            return false;
        }

        // ===== VALIDAR SELECCIÓN DE CLIENTE Y EMPLEADO =====
        if (dlg.cboCliente.getSelectedIndex() == -1) {
            MensajeUtil.error(dlg, "Debe seleccionar un cliente.");
            return false;
        }

        if (dlg.cboEmpleado.getSelectedIndex() == -1) {
            MensajeUtil.error(dlg, "Debe seleccionar un empleado.");
            return false;
        }

        // ===== CREAR RESERVA =====
        Reserva r = new Reserva();
        r.setIdCliente(dlg.getClienteSeleccionado());
        r.setIdEmpleado(dlg.getEmpleadoSeleccionado());
        r.setFechaInicio(fechaInicio);
        r.setFechaFin(fechaFin);

        // Generar JSON para el stored procedure
        String json = JSONUtil.generarJSONDetalles(detalles);
        
        System.out.println("📝 JSON enviado al SP: " + json);

        // ===== INSERTAR EN LA BD =====
        boolean exito = dao.insertarReservaCompleta(r, detalles, json);
        
        if (!exito) {
            MensajeUtil.error(dlg, 
                "Error al procesar la reserva.\n" +
                "Verifique que los vehículos estén disponibles.");
        }
        
        return exito;
    }

    /**
     * Maneja todos los eventos de los botones
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // ===== BOTÓN NUEVA RESERVA =====
        if (e.getSource() == vista.btnNuevaReserva) {
            abrirDialogoNuevaReserva();
        }

        // ===== BOTÓN VER DETALLE =====
        if (e.getSource() == vista.btnVerDetalle) {
            int idReserva = vista.getReservaSeleccionada();
            
            if (idReserva == -1) {
                MensajeUtil.error(vista, "Debe seleccionar una reserva de la tabla.");
                return;
            }

            List<DetalleReserva> detalles = detalleDAO.listarDetallesPorReserva(idReserva);
            vista.mostrarDetalle(detalles);
        }

        // ===== BOTÓN ELIMINAR RESERVA =====
        if (e.getSource() == vista.btnEliminarReserva) {
            int idReserva = vista.getReservaSeleccionada();
            
            if (idReserva == -1) {
                MensajeUtil.error(vista, "Debe seleccionar una reserva de la tabla.");
                return;
            }

            if (MensajeUtil.confirmar(vista, 
                "¿Está seguro de eliminar esta reserva?\n\n" +
                "Esta acción eliminará la reserva y todos sus detalles.")) {
                
                boolean exito = dao.eliminarReserva(idReserva);
                
                if (exito) {
                    MensajeUtil.info(vista, "Reserva eliminada correctamente.");
                    cargarReservas();
                } else {
                    MensajeUtil.error(vista, "Error al eliminar la reserva.");
                }
            }
        }

        // ===== BOTÓN BUSCAR =====
        if (e.getSource() == vista.btnBuscar) {
            String filtro = vista.txtBuscar.getText().trim();
            
            if (ValidacionUtil.esVacio(filtro)) {
                cargarReservas();
            } else {
                vista.filtrar(filtro);
            }
        }

        // ===== BOTÓN FILTRAR POR FECHAS =====
        if (e.getSource() == vista.btnFiltrarFechas) {
            String fechaInicio = vista.getFechaInicio();
            String fechaFin = vista.getFechaFin();

            if (ValidacionUtil.esVacio(fechaInicio) || ValidacionUtil.esVacio(fechaFin)) {
                MensajeUtil.error(vista, "Debe ingresar ambas fechas para filtrar.");
                return;
            }

            if (!ValidacionUtil.rangoFechasValido(fechaInicio, fechaFin)) {
                MensajeUtil.error(vista, "Rango de fechas inválido.");
                return;
            }

            List<Reserva> reservasFiltradas = dao.listarReservasPorFechas(fechaInicio, fechaFin);
            vista.cargarReservas(reservasFiltradas);
            
            if (reservasFiltradas.isEmpty()) {
                MensajeUtil.info(vista, "No se encontraron reservas en el rango de fechas especificado.");
            }
        }
    }
}