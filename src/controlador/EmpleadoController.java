
package controlador;

import dao.EmpleadoDAO;
import modelo.Empleado;
import util.MensajeUtil;
import vista.empleado.EmpleadoDialog;
import vista.empleado.EmpleadoPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EmpleadoController implements ActionListener {

    private EmpleadoPanel vista;
    private EmpleadoDAO dao;

    public EmpleadoController(EmpleadoPanel vista) {
        this.vista = vista;
        this.dao = new EmpleadoDAO();

        vista.btnNuevo.addActionListener(this);
        vista.btnEditar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);

        cargarTabla();
    }

    private void cargarTabla() {
        List<Empleado> lista = dao.listarEmpleados();
        vista.cargarTabla(lista);
    }

    private void abrirDialogo(Empleado e) {
        EmpleadoDialog dlg = new EmpleadoDialog(null, true);

        if (e != null) {
            dlg.txtId.setText(String.valueOf(e.getIdEmpleado()));
            dlg.txtNombre.setText(e.getNombre());
            dlg.txtCargo.setText(e.getCargo());
            dlg.txtUsuario.setText(e.getUsuario());
            dlg.txtClave.setText(e.getClave());
        }

        dlg.btnGuardar.addActionListener(ev -> {
            if (guardar(dlg)) {
                dlg.dispose();
                cargarTabla();
            }
        });

        dlg.setVisible(true);
    }

    private boolean guardar(EmpleadoDialog dlg) {
        Empleado e = new Empleado();
        e.setNombre(dlg.txtNombre.getText());
        e.setCargo(dlg.txtCargo.getText());
        e.setUsuario(dlg.txtUsuario.getText());
        e.setClave(dlg.txtClave.getText());

        if (dlg.txtId.getText().isEmpty()) {
            return dao.insertarEmpleado(e);
        } else {
            e.setIdEmpleado(Integer.parseInt(dlg.txtId.getText()));
            return dao.actualizarEmpleado(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.btnNuevo) {
            abrirDialogo(null);
        }

        if (e.getSource() == vista.btnEditar) {
            Empleado emp = vista.getEmpleadoSeleccionado();
            if (emp == null) {
                MensajeUtil.error(vista, "Seleccione un empleado.");
                return;
            }
            abrirDialogo(emp);
        }

        if (e.getSource() == vista.btnEliminar) {
            Empleado emp = vista.getEmpleadoSeleccionado();
            if (emp == null) {
                MensajeUtil.error(vista, "Seleccione un empleado.");
                return;
            }

            if (MensajeUtil.confirmar(vista, "¿Eliminar empleado?")) {
                dao.eliminarEmpleado(emp.getIdEmpleado());
                cargarTabla();
            }
        }
    }
}