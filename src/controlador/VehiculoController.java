package controlador;

import dao.VehiculoDAO;
import modelo.Vehiculo;
import util.MensajeUtil;
import util.ValidacionUtil;
import vista.vehiculo.VehiculoDialog;
import vista.vehiculo.VehiculoPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para el módulo de Vehículos
 * Implementa CRUD completo con validaciones
 * 
 * ⭐ VERSIÓN CORREGIDA - Búsqueda funcionando
 */
public class VehiculoController implements ActionListener {

    private VehiculoPanel vista;
    private VehiculoDAO dao;

    /**
     * Constructor - Inicializa el controlador
     * @param vista Panel de vehículos
     */
    public VehiculoController(VehiculoPanel vista) {
        this.vista = vista;
        this.dao = new VehiculoDAO();

        // Registrar listeners
        vista.btnNuevo.addActionListener(this);
        vista.btnEditar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnBuscar.addActionListener(this); // ⭐ CORREGIDO: Listener agregado

        // Cargar datos iniciales
        cargarTabla();
    }

    /**
     * Carga todos los vehículos en la tabla
     */
    private void cargarTabla() {
        List<Vehiculo> lista = dao.listarVehiculos();
        vista.cargarTabla(lista);
    }

    /**
     * Abre el diálogo para crear/editar vehículo
     * @param v Vehículo a editar (null para nuevo)
     */
    private void abrirDialogo(Vehiculo v) {
        VehiculoDialog dlg = new VehiculoDialog(null, true);

        // ⭐ INICIALIZAR COMBO DE ESTADOS
        dlg.cboEstado.removeAllItems();
        dlg.cboEstado.addItem("Disponible");
        dlg.cboEstado.addItem("Reservado");
        dlg.cboEstado.addItem("Mantenimiento");

        // Si es edición, cargar datos
        if (v != null) {
            dlg.txtId.setText(String.valueOf(v.getIdVehiculo()));
            dlg.txtPlaca.setText(v.getPlaca());
            dlg.txtMarca.setText(v.getMarca());
            dlg.txtModelo.setText(v.getModelo());
            dlg.txtAño.setText(String.valueOf(v.getAnio()));
            dlg.txtPrecioDia.setText(String.valueOf(v.getPrecioDia()));
            dlg.cboEstado.setSelectedItem(v.getEstado());
        } else {
            // Para nuevo vehículo, seleccionar "Disponible" por defecto
            dlg.cboEstado.setSelectedItem("Disponible");
        }

        // Listener para guardar
        dlg.btnGuardar.addActionListener(ev -> {
            if (guardar(dlg)) {
                MensajeUtil.info(dlg, "Vehículo guardado correctamente.");
                dlg.dispose();
                cargarTabla();
            }
        });

        // Listener para cancelar
        dlg.btnCancelar.addActionListener(ev -> dlg.dispose());

        dlg.setVisible(true);
    }

    /**
     * Guarda el vehículo con validaciones completas
     * @param dlg Diálogo con los datos
     * @return true si se guardó correctamente
     */
    private boolean guardar(VehiculoDialog dlg) {
        // ===== VALIDACIONES =====
        String placa = dlg.txtPlaca.getText().trim();
        String marca = dlg.txtMarca.getText().trim();
        String modelo = dlg.txtModelo.getText().trim();
        String añoStr = dlg.txtAño.getText().trim();
        String precioDiaStr = dlg.txtPrecioDia.getText().trim();
        String estado = (String) dlg.cboEstado.getSelectedItem();

        // Validar campos obligatorios
        if (ValidacionUtil.esVacio(placa)) {
            MensajeUtil.error(dlg, "La placa es obligatoria.");
            dlg.txtPlaca.requestFocus();
            return false;
        }

        if (ValidacionUtil.esVacio(marca)) {
            MensajeUtil.error(dlg, "La marca es obligatoria.");
            dlg.txtMarca.requestFocus();
            return false;
        }

        if (ValidacionUtil.esVacio(modelo)) {
            MensajeUtil.error(dlg, "El modelo es obligatorio.");
            dlg.txtModelo.requestFocus();
            return false;
        }

        // Validar formato de placa (ejemplo: ABC123 o ABC-123)
        if (!placa.matches("^[A-Z]{3}-?[0-9]{3,4}$")) {
            MensajeUtil.error(dlg, "Formato de placa inválido.\nEjemplo: ABC123 o ABC-123");
            dlg.txtPlaca.requestFocus();
            return false;
        }

        // Validar año
        if (!ValidacionUtil.esEnteroPositivo(añoStr)) {
            MensajeUtil.error(dlg, "El año debe ser un número válido.");
            dlg.txtAño.requestFocus();
            return false;
        }

        int año = Integer.parseInt(añoStr);
        int añoActual = java.time.Year.now().getValue();

        if (año < 1900 || año > añoActual + 1) {
            MensajeUtil.error(dlg, 
                String.format("El año debe estar entre 1900 y %d.", añoActual + 1));
            dlg.txtAño.requestFocus();
            return false;
        }

        // Validar precio
        if (!ValidacionUtil.esNumeroPositivo(precioDiaStr)) {
            MensajeUtil.error(dlg, "El precio debe ser un número positivo.");
            dlg.txtPrecioDia.requestFocus();
            return false;
        }

        double precioDia = Double.parseDouble(precioDiaStr);

        if (precioDia < 1000) {
            MensajeUtil.error(dlg, "El precio por día debe ser al menos ₡1,000.");
            dlg.txtPrecioDia.requestFocus();
            return false;
        }

        if (precioDia > 1000000) {
            MensajeUtil.error(dlg, "El precio por día no puede exceder ₡1,000,000.");
            dlg.txtPrecioDia.requestFocus();
            return false;
        }

        // Validar estado
        if (estado == null || estado.isEmpty()) {
            MensajeUtil.error(dlg, "Debe seleccionar un estado.");
            return false;
        }

        // ===== CREAR OBJETO VEHICULO =====
        Vehiculo v = new Vehiculo();
        v.setPlaca(placa.toUpperCase()); // Normalizar a mayúsculas
        v.setMarca(marca);
        v.setModelo(modelo);
        v.setAnio(año);
        v.setPrecioDia(precioDia);
        v.setEstado(estado);

        // ===== INSERTAR O ACTUALIZAR =====
        boolean exito;
        if (dlg.txtId.getText().isEmpty()) {
            // Insertar nuevo
            exito = dao.insertarVehiculo(v);
            if (!exito) {
                MensajeUtil.error(dlg, "Error al guardar. Verifique que la placa no esté duplicada.");
            }
        } else {
            // Actualizar existente
            v.setIdVehiculo(Integer.parseInt(dlg.txtId.getText()));
            exito = dao.actualizarVehiculo(v);
            if (!exito) {
                MensajeUtil.error(dlg, "Error al actualizar el vehículo.");
            }
        }

        return exito;
    }

    /**
     * ⭐ CORREGIDO: Realiza búsqueda/filtrado en la tabla
     * @param filtro Texto a buscar
     */
    private void buscar(String filtro) {
        if (ValidacionUtil.esVacio(filtro)) {
            // Si está vacío, mostrar todos
            cargarTabla();
            return;
        }

        // Filtrar vehículos
        List<Vehiculo> lista = dao.listarVehiculos();
        String filtroLower = filtro.toLowerCase();

        List<Vehiculo> filtrados = lista.stream()
            .filter(veh -> 
                veh.getPlaca().toLowerCase().contains(filtroLower) ||
                veh.getMarca().toLowerCase().contains(filtroLower) ||
                veh.getModelo().toLowerCase().contains(filtroLower) ||
                veh.getEstado().toLowerCase().contains(filtroLower) ||
                String.valueOf(veh.getAnio()).contains(filtro)
            )
            .collect(Collectors.toList());

        vista.cargarTabla(filtrados);

        // Mensaje si no hay resultados
        if (filtrados.isEmpty()) {
            MensajeUtil.info(vista, "No se encontraron vehículos con el criterio: " + filtro);
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
            Vehiculo v = vista.getVehiculoSeleccionado();
            
            if (v == null) {
                MensajeUtil.error(vista, "Debe seleccionar un vehículo de la tabla.");
                return;
            }
            
            abrirDialogo(v);
        }

        // ===== BOTÓN ELIMINAR =====
        if (e.getSource() == vista.btnEliminar) {
            Vehiculo v = vista.getVehiculoSeleccionado();
            
            if (v == null) {
                MensajeUtil.error(vista, "Debe seleccionar un vehículo de la tabla.");
                return;
            }

            // Confirmar eliminación
            String mensaje = String.format(
                "¿Está seguro de eliminar el vehículo?\n\n" +
                "Placa: %s\nMarca: %s %s\nAño: %d",
                v.getPlaca(), v.getMarca(), v.getModelo(), v.getAnio()
            );

            if (MensajeUtil.confirmar(vista, mensaje)) {
                boolean exito = dao.eliminarVehiculo(v.getIdVehiculo());
                
                if (exito) {
                    MensajeUtil.info(vista, "Vehículo eliminado correctamente.");
                    cargarTabla();
                } else {
                    MensajeUtil.error(vista, 
                        "No se puede eliminar este vehículo.\n" +
                        "Puede tener reservas asociadas.");
                }
            }
        }

        // ===== BOTÓN BUSCAR ===== ⭐ CORREGIDO: Funcionalidad agregada
        if (e.getSource() == vista.btnBuscar) {
            String filtro = vista.txtBuscar.getText().trim();
            buscar(filtro);
        }
    }
}