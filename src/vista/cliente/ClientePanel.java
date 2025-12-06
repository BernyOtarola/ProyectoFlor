package vista.cliente;

import java.util.List;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Cliente;

public class ClientePanel extends javax.swing.JPanel {

    public ClientePanel() {
        initComponents();
        configurarTabla();
        new controlador.ClienteController(this); 
    }

    private void configurarTabla() {
        tblClientes.setRowHeight(24);
        tblClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblClientes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        tblClientes.getColumnModel().getColumn(0).setPreferredWidth(40);   
        tblClientes.getColumnModel().getColumn(1).setPreferredWidth(200);  
        tblClientes.getColumnModel().getColumn(2).setPreferredWidth(120);  
        tblClientes.getColumnModel().getColumn(3).setPreferredWidth(120);  
        tblClientes.getColumnModel().getColumn(4).setPreferredWidth(200);  
        tblClientes.getColumnModel().getColumn(5).setPreferredWidth(200); 
    }

    public void cargarTabla(List<Cliente> lista) {
        DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
        modelo.setRowCount(0);

        for (Cliente c : lista) {
            modelo.addRow(new Object[]{
                c.getIdCliente(),
                c.getNombre(),
                c.getIdentificacion(),
                c.getTelefono(),
                c.getCorreo(),
                c.getDireccion()
            });
        }
    }

    public Cliente getClienteSeleccionado() {
        int fila = tblClientes.getSelectedRow();
        if (fila == -1) {
            return null;
        }

        Cliente c = new Cliente();
        c.setIdCliente((int) tblClientes.getValueAt(fila, 0));
        c.setNombre((String) tblClientes.getValueAt(fila, 1));
        c.setIdentificacion((String) tblClientes.getValueAt(fila, 2));
        c.setTelefono((String) tblClientes.getValueAt(fila, 3));
        c.setCorreo((String) tblClientes.getValueAt(fila, 4));
        c.setDireccion((String) tblClientes.getValueAt(fila, 5));

        return c;
    }

    public void filtrar(String filtro) {
        DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tblClientes.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + filtro));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(1150, 650));
        setPreferredSize(new java.awt.Dimension(1150, 650));

        lblBuscar.setText("Buscar:");

        txtBuscar.setMinimumSize(new java.awt.Dimension(400, 22));
        txtBuscar.setPreferredSize(new java.awt.Dimension(400, 22));
        txtBuscar.addActionListener(this::txtBuscarActionPerformed);

        btnBuscar.setText("🔎 Buscar");
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Identificación", "Teléfono", "Correo", "Direccion"
            }
        ));
        jScrollPane1.setViewportView(tblClientes);

        btnNuevo.setText("➕ Nuevo");
        btnNuevo.addActionListener(this::btnNuevoActionPerformed);

        btnEditar.setText("✏ Editar");

        btnEliminar.setText("❌ Eliminar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 171, Short.MAX_VALUE)
                .addComponent(btnNuevo)
                .addGap(50, 50, 50)
                .addComponent(btnEditar)
                .addGap(50, 50, 50)
                .addComponent(btnEliminar)
                .addGap(100, 100, 100))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuscar)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnNuevo)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnBuscar;
    public javax.swing.JButton btnEditar;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnNuevo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscar;
    public javax.swing.JTable tblClientes;
    public javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
