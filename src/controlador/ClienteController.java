
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

public class ClienteController implements ActionListener {

    private ClientePanel vista;
    private ClienteDAO dao;

    public ClienteController(ClientePanel vista) {
        this.vista = vista;
        this.dao = new ClienteDAO();

        // Listeners
        vista.btnNuevo.addActionListener(this);
        vista.btnEditar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnBuscar.addActionListener(this);

        cargarTabla();
    }

    private void cargarTabla() {
        List<Cliente> lista = dao.listarClientes();
        vista.cargarTabla(lista);
    }

    private void abrirDialogo(Cliente c) {
        ClienteDialog dlg = new ClienteDialog(null, true);

        if (c != null) {
            dlg.txtId.setText(String.valueOf(c.getIdCliente()));
            dlg.txtNombre.setText(c.getNombre());
            dlg.txtIdentificacion.setText(c.getIdentificacion());
            dlg.txtTelefono.setText(c.getTelefono());
            dlg.txtCorreo.setText(c.getCorreo());
            dlg.txtDireccion.setText(c.getDireccion());
        }

        dlg.btnGuardar.addActionListener(ev -> {
            if (guardarCliente(dlg)) {
                dlg.dispose();
                cargarTabla();
            }
        });

        dlg.setVisible(true);
    }

    private boolean guardarCliente(ClienteDialog dlg) {

        String nombre = dlg.txtNombre.getText();
        String identificacion = dlg.txtIdentificacion.getText();

        if (ValidacionUtil.esVacio(nombre) || ValidacionUtil.esVacio(identificacion)) {
            MensajeUtil.error(dlg, "Nombre e identificación son obligatorios.");
            return false;
        }

        Cliente c = new Cliente();
        c.setNombre(nombre);
        c.setIdentificacion(identificacion);
        c.setTelefono(dlg.txtTelefono.getText());
        c.setCorreo(dlg.txtCorreo.getText());
        c.setDireccion(dlg.txtDireccion.getText());

        if (dlg.txtId.getText().isEmpty()) {
            return dao.insertarCliente(c);
        } else {
            c.setIdCliente(Integer.parseInt(dlg.txtId.getText()));
            return dao.actualizarCliente(c);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.btnNuevo) {
            abrirDialogo(null);
        }

        if (e.getSource() == vista.btnEditar) {
            Cliente c = vista.getClienteSeleccionado();
            if (c == null) {
                MensajeUtil.error(vista, "Seleccione un cliente.");
                return;
            }
            abrirDialogo(c);
        }

        if (e.getSource() == vista.btnEliminar) {
            Cliente c = vista.getClienteSeleccionado();
            if (c == null) {
                MensajeUtil.error(vista, "Seleccione un cliente.");
                return;
            }
            if (MensajeUtil.confirmar(vista, "¿Eliminar el cliente?")) {
                dao.eliminarCliente(c.getIdCliente());
                cargarTabla();
            }
        }

        if (e.getSource() == vista.btnBuscar) {
            String filtro = vista.txtBuscar.getText();
            vista.filtrar(filtro);
        }
    }
}
