
package controlador;

import dao.VehiculoDAO;
import modelo.Vehiculo;
import util.MensajeUtil;
import vista.vehiculo.VehiculoDialog;
import vista.vehiculo.VehiculoPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VehiculoController implements ActionListener {

    private VehiculoPanel vista;
    private VehiculoDAO dao;

    public VehiculoController(VehiculoPanel vista) {
        this.vista = vista;
        this.dao = new VehiculoDAO();

        vista.btnNuevo.addActionListener(this);
        vista.btnEditar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);

        cargarTabla();
    }

    private void cargarTabla() {
        List<Vehiculo> lista = dao.listarVehiculos();
        vista.cargarTabla(lista);
    }

    private void abrirDialogo(Vehiculo v) {
        VehiculoDialog dlg = new VehiculoDialog(null, true);

        if (v != null) {
            dlg.txtId.setText(String.valueOf(v.getIdVehiculo()));
            dlg.txtPlaca.setText(v.getPlaca());
            dlg.txtMarca.setText(v.getMarca());
            dlg.txtModelo.setText(v.getModelo());
            dlg.txtAño.setText(String.valueOf(v.getAnio()));
            dlg.txtPrecioDia.setText(String.valueOf(v.getPrecioDia()));
            dlg.cboEstado.setSelectedItem(v.getEstado());
        }

        dlg.btnGuardar.addActionListener(ev -> {
            if (guardar(dlg)) {
                dlg.dispose();
                cargarTabla();
            }
        });

        dlg.setVisible(true);
    }

    private boolean guardar(VehiculoDialog dlg) {

        Vehiculo v = new Vehiculo();
        v.setPlaca(dlg.txtPlaca.getText());
        v.setMarca(dlg.txtMarca.getText());
        v.setModelo(dlg.txtModelo.getText());
        v.setAnio(Integer.parseInt(dlg.txtAño.getText()));
        v.setPrecioDia(Double.parseDouble(dlg.txtPrecioDia.getText()));
        v.setEstado(dlg.cboEstado.getSelectedItem().toString());

        if (dlg.txtId.getText().isEmpty()) {
            return dao.insertarVehiculo(v);
        } else {
            v.setIdVehiculo(Integer.parseInt(dlg.txtId.getText()));
            return dao.actualizarVehiculo(v);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnNuevo) abrirDialogo(null);

        if (e.getSource() == vista.btnEditar) {
            Vehiculo v = vista.getVehiculoSeleccionado();
            if (v == null) {
                MensajeUtil.error(vista, "Seleccione un vehículo.");
                return;
            }
            abrirDialogo(v);
        }

        if (e.getSource() == vista.btnEliminar) {
            Vehiculo v = vista.getVehiculoSeleccionado();
            if (v == null) {
                MensajeUtil.error(vista, "Seleccione un vehículo.");
                return;
            }

            if (MensajeUtil.confirmar(vista, "¿Eliminar vehículo?")) {
                dao.eliminarVehiculo(v.getIdVehiculo());
                cargarTabla();
            }
        }
    }
}