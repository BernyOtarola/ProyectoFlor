package controlador;

import dao.TemporadaDAO;
import modelo.Temporada;
import util.MensajeUtil;
import util.ValidacionUtil;
import vista.temporada.TemporadaDialog;
import vista.temporada.TemporadaPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para el módulo de Temporadas
 * Implementa CRUD completo con validaciones
 */
public class TemporadaController implements ActionListener {

    private TemporadaPanel vista;
    private TemporadaDAO dao;

    /**
     * Constructor - Inicializa el controlador
     * @param vista Panel de temporadas
     */
    public TemporadaController(TemporadaPanel vista) {
        this.vista = vista;
        this.dao = new TemporadaDAO();

        // Registrar listeners
        vista.btnNuevo.addActionListener(this);
        vista.btnEditar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnBuscar.addActionListener(this);

        // Cargar datos iniciales
        cargarTabla();
    }

    /**
     * Carga todas las temporadas en la tabla
     */
    private void cargarTabla() {
        List<Temporada> lista = dao.listarTemporadas();
        vista.cargarTabla(lista);
    }

    /**
     * Abre el diálogo para crear/editar temporada
     * @param t Temporada a editar (null para nueva)
     */
    private void abrirDialogo(Temporada t) {
        TemporadaDialog dlg = new TemporadaDialog(null, true);

        // Si es edición, cargar datos
        if (t != null) {
            dlg.txtId.setText(String.valueOf(t.getIdTemporada()));
            dlg.txtNombre.setText(t.getNombre());
            dlg.txtFechaInicio.setText(t.getFechaInicio());
            dlg.txtFechaFin.setText(t.getFechaFin());
            dlg.txtFactor.setText(String.valueOf(t.getFactor()));
            
            // Calcular y mostrar recargo
            double recargo = (t.getFactor() - 1.0) * 100;
            dlg.txtRecargo.setText(String.format("%.2f", recargo));
        } else {
            // Valores por defecto para nueva temporada
            dlg.txtFactor.setText("1.00");
            dlg.txtRecargo.setText("0.00");
        }

        // ⭐ Sincronizar Factor y Recargo automáticamente
        dlg.txtRecargo.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    double recargo = Double.parseDouble(dlg.txtRecargo.getText());
                    double factor = 1.0 + (recargo / 100.0);
                    dlg.txtFactor.setText(String.format("%.2f", factor));
                } catch (NumberFormatException ex) {
                    // Ignorar si no es número válido
                }
            }
        });

        dlg.txtFactor.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    double factor = Double.parseDouble(dlg.txtFactor.getText());
                    double recargo = (factor - 1.0) * 100;
                    dlg.txtRecargo.setText(String.format("%.2f", recargo));
                } catch (NumberFormatException ex) {
                    // Ignorar si no es número válido
                }
            }
        });

        // Listener para guardar
        dlg.btnGuardar.addActionListener(ev -> {
            if (guardar(dlg)) {
                MensajeUtil.info(dlg, "Temporada guardada correctamente.");
                dlg.dispose();
                cargarTabla();
            }
        });

        // Listener para cancelar
        dlg.btnCancelar.addActionListener(ev -> dlg.dispose());

        dlg.setVisible(true);
    }

    /**
     * Guarda la temporada con validaciones completas
     * @param dlg Diálogo con los datos
     * @return true si se guardó correctamente
     */
    private boolean guardar(TemporadaDialog dlg) {
        // ===== VALIDACIONES =====
        String nombre = dlg.txtNombre.getText().trim();
        String fechaInicio = dlg.txtFechaInicio.getText().trim();
        String fechaFin = dlg.txtFechaFin.getText().trim();
        String factorStr = dlg.txtFactor.getText().trim();

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

        // Validar fechas
        if (!ValidacionUtil.validarFormatoFecha(fechaInicio)) {
            MensajeUtil.error(dlg, "Formato de fecha inicio inválido.\nUse: yyyy-MM-dd");
            dlg.txtFechaInicio.requestFocus();
            return false;
        }

        if (!ValidacionUtil.validarFormatoFecha(fechaFin)) {
            MensajeUtil.error(dlg, "Formato de fecha fin inválido.\nUse: yyyy-MM-dd");
            dlg.txtFechaFin.requestFocus();
            return false;
        }

        if (!ValidacionUtil.rangoFechasValido(fechaInicio, fechaFin)) {
            MensajeUtil.error(dlg, "El rango de fechas no es válido.\nLa fecha fin debe ser posterior a la fecha inicio.");
            dlg.txtFechaFin.requestFocus();
            return false;
        }

        // Validar que las fechas no sean pasadas
        try {
            LocalDate inicio = LocalDate.parse(fechaInicio);
            LocalDate hoy = LocalDate.now();
            
            if (inicio.isBefore(hoy) && dlg.txtId.getText().isEmpty()) {
                // Solo validar para nuevas temporadas
                MensajeUtil.error(dlg, "La fecha de inicio no puede ser anterior a hoy.");
                dlg.txtFechaInicio.requestFocus();
                return false;
            }
        } catch (Exception ex) {
            MensajeUtil.error(dlg, "Error al validar fechas: " + ex.getMessage());
            return false;
        }

        // Validar factor
        if (!ValidacionUtil.esNumeroPositivo(factorStr)) {
            MensajeUtil.error(dlg, "El factor debe ser un número positivo.");
            dlg.txtFactor.requestFocus();
            return false;
        }

        double factor = Double.parseDouble(factorStr);

        if (factor < 1.0) {
            MensajeUtil.error(dlg, "El factor debe ser al menos 1.00 (sin recargo).");
            dlg.txtFactor.requestFocus();
            return false;
        }

        if (factor > 3.0) {
            MensajeUtil.error(dlg, "El factor no puede exceder 3.00 (200% de recargo).");
            dlg.txtFactor.requestFocus();
            return false;
        }

        // ===== VALIDAR SOLAPAMIENTO DE FECHAS =====
        if (!validarSolapamiento(dlg.txtId.getText(), fechaInicio, fechaFin)) {
            MensajeUtil.error(dlg, 
                "Ya existe otra temporada en este rango de fechas.\n" +
                "Las temporadas no pueden solaparse.");
            return false;
        }

        // ===== CREAR OBJETO TEMPORADA =====
        Temporada t = new Temporada();
        t.setNombre(nombre);
        t.setFechaInicio(fechaInicio);
        t.setFechaFin(fechaFin);
        t.setFactor(factor);

        // ===== INSERTAR O ACTUALIZAR =====
        boolean exito;
        if (dlg.txtId.getText().isEmpty()) {
            // Insertar nueva
            exito = dao.insertarTemporada(t);
            if (!exito) {
                MensajeUtil.error(dlg, "Error al guardar la temporada.");
            }
        } else {
            // Actualizar existente
            t.setIdTemporada(Integer.parseInt(dlg.txtId.getText()));
            exito = dao.actualizarTemporada(t);
            if (!exito) {
                MensajeUtil.error(dlg, "Error al actualizar la temporada.");
            }
        }

        return exito;
    }

    /**
     * Valida que no haya solapamiento de fechas con otras temporadas
     * @param idActual ID de la temporada actual (vacío si es nueva)
     * @param fechaInicio Fecha inicio a validar
     * @param fechaFin Fecha fin a validar
     * @return true si no hay solapamiento
     */
    private boolean validarSolapamiento(String idActual, String fechaInicio, String fechaFin) {
        List<Temporada> todas = dao.listarTemporadas();
        
        try {
            LocalDate inicio = LocalDate.parse(fechaInicio);
            LocalDate fin = LocalDate.parse(fechaFin);
            
            for (Temporada t : todas) {
                // Saltar la temporada actual si es edición
                if (!idActual.isEmpty() && 
                    t.getIdTemporada() == Integer.parseInt(idActual)) {
                    continue;
                }
                
                LocalDate tInicio = LocalDate.parse(t.getFechaInicio());
                LocalDate tFin = LocalDate.parse(t.getFechaFin());
                
                // Verificar solapamiento
                boolean solapa = !(fin.isBefore(tInicio) || inicio.isAfter(tFin));
                
                if (solapa) {
                    return false;
                }
            }
        } catch (Exception e) {
            System.err.println("Error validando solapamiento: " + e.getMessage());
            return false;
        }
        
        return true;
    }

    /**
     * Realiza búsqueda/filtrado en la tabla
     * @param filtro Texto a buscar
     */
    private void buscar(String filtro) {
        if (ValidacionUtil.esVacio(filtro)) {
            cargarTabla();
            return;
        }

        List<Temporada> lista = dao.listarTemporadas();
        String filtroLower = filtro.toLowerCase();

        List<Temporada> filtradas = lista.stream()
            .filter(temp -> 
                temp.getNombre().toLowerCase().contains(filtroLower) ||
                temp.getFechaInicio().contains(filtro) ||
                temp.getFechaFin().contains(filtro)
            )
            .collect(Collectors.toList());

        vista.cargarTabla(filtradas);

        if (filtradas.isEmpty()) {
            MensajeUtil.info(vista, "No se encontraron temporadas con el criterio: " + filtro);
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
            Temporada t = vista.getTemporadaSeleccionada();
            
            if (t == null) {
                MensajeUtil.error(vista, "Debe seleccionar una temporada de la tabla.");
                return;
            }
            
            abrirDialogo(t);
        }

        // ===== BOTÓN ELIMINAR =====
        if (e.getSource() == vista.btnEliminar) {
            Temporada t = vista.getTemporadaSeleccionada();
            
            if (t == null) {
                MensajeUtil.error(vista, "Debe seleccionar una temporada de la tabla.");
                return;
            }

            // Confirmar eliminación
            String mensaje = String.format(
                "¿Está seguro de eliminar la temporada?\n\n" +
                "Nombre: %s\nFechas: %s a %s\nRecargo: %.0f%%",
                t.getNombre(), t.getFechaInicio(), t.getFechaFin(), 
                (t.getFactor() - 1.0) * 100
            );

            if (MensajeUtil.confirmar(vista, mensaje)) {
                boolean exito = dao.eliminarTemporada(t.getIdTemporada());
                
                if (exito) {
                    MensajeUtil.info(vista, "Temporada eliminada correctamente.");
                    cargarTabla();
                } else {
                    MensajeUtil.error(vista, "Error al eliminar la temporada.");
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