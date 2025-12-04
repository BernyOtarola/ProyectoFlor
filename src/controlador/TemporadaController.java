
package controlador;

import dao.TemporadaDAO;
import modelo.Temporada;
import util.MensajeUtil;
import vista.temporada.TemporadaDialog;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import vista.temporada.TemporadaPanel;

public class TemporadaController implements ActionListener {

    private TemporadaPanel vista;
    private TemporadaDAO dao;

    public TemporadaController(TemporadaPanel vista) {
        this.vista = vista;
        this.dao = new TemporadaDAO();

        vista.btnNuevo.addActionListener(this);
        vista.btnEditar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);

        cargarTabla();
    }

    private void cargarTabla() {
        List<Temporada> lista = dao.listarTemporadas();
        vista.cargarTabla(lista);
    }

    private void abrirDialogo(Temporada t) {
        TemporadaDialog dlg = new TemporadaDialog(null, true);

        if (t != null) {
            dlg.txtId.setText(String.valueOf(t.getIdTemporada()));
            dlg.txtNombre.setText(t.getNombre());
            dlg.txtFechaInicio.setText(t.getFechaInicio());
            dlg.txtFechaFin.setText(t.getFechaFin());
            dlg.txtFactor.setText(String.valueOf(t.getFactor()));
        }

        dlg.btnGuardar.addActionListener(ev -> {
            if (guardar(dlg)) {
                dlg.dispose();
                cargarTabla();
            }
        });

        dlg.setVisible(true);
    }

    private boolean guardar(TemporadaDialog dlg) {
        Temporada t = new Temporada();
        t.setNombre(dlg.txtNombre.getText());
        t.setFechaInicio(dlg.txtFechaInicio.getText());
        t.setFechaFin(dlg.txtFechaFin.getText());
        t.setFactor(Double.parseDouble(dlg.txtFactor.getText()));

        if (dlg.txtId.getText().isEmpty()) {
            return dao.insertarTemporada(t);
        } else {
            t.setIdTemporada(Integer.parseInt(dlg.txtId.getText()));
            return dao.actualizarTemporada(t);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.btnNuevo)
            abrirDialogo(null);

        if (e.getSource() == vista.btnEditar) {
            Temporada t = vista.getTemporadaSeleccionada();
            if (t == null) {
                MensajeUtil.error(vista, "Seleccione una temporada.");
                return;
            }
            abrirDialogo(t);
        }

        if (e.getSource() == vista.btnEliminar) {
            Temporada t = vista.getTemporadaSeleccionada();
            if (t == null) {
                MensajeUtil.error(vista, "Seleccione una temporada.");
                return;
            }

            if (MensajeUtil.confirmar(vista, "¿Eliminar temporada?")) {
                dao.eliminarTemporada(t.getIdTemporada());
                cargarTabla();
            }
        }
    }
}