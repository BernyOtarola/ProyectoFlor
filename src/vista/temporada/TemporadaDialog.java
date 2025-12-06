package vista.temporada;

import javax.swing.JOptionPane;

/**
 * Diálogo para crear/editar temporadas Permite gestionar periodos con recargos
 * especiales (Navidad, Semana Santa, etc.)
 */
public class TemporadaDialog extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger
            = java.util.logging.Logger.getLogger(TemporadaDialog.class.getName());

    /**
     * Constructor del diálogo
     *
     * @param parent Frame padre
     * @param modal true para modal, false para no modal
     */
    public TemporadaDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Temporada - Gestión de Recargos");

        // Campo ID no editable (autoincremental)
        txtId.setEditable(false);

        // Configurar sincronización entre Recargo y Factor
        configurarSincronizacion();
    }

    /**
     * Configura la sincronización automática entre % Recargo y Factor Recargo:
     * Porcentaje visual (ej: 30%) Factor: Multiplicador matemático (ej: 1.30)
     */
    private void configurarSincronizacion() {
        // Cuando cambia el Recargo (%), actualizar Factor
        txtRecargo.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    String texto = txtRecargo.getText().trim();
                    if (!texto.isEmpty()) {
                        double recargo = Double.parseDouble(texto);
                        double factor = 1.0 + (recargo / 100.0);
                        txtFactor.setText(String.format("%.2f", factor));
                    }
                } catch (NumberFormatException ex) {
                    // Ignorar si no es número válido
                }
            }
        });

        // Cuando cambia el Factor, actualizar Recargo (%)
        txtFactor.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    String texto = txtFactor.getText().trim();
                    if (!texto.isEmpty()) {
                        double factor = Double.parseDouble(texto);
                        double recargo = (factor - 1.0) * 100;
                        txtRecargo.setText(String.format("%.2f", recargo));
                    }
                } catch (NumberFormatException ex) {
                    // Ignorar si no es número válido
                }
            }
        });
    }

    /**
     * Limpia todos los campos del formulario
     */
    public void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtFechaInicio.setText("");
        txtFechaFin.setText("");
        txtRecargo.setText("0.00");
        txtFactor.setText("1.00");
    }

    /**
     * Carga los datos de una temporada en el formulario
     *
     * @param t Temporada a cargar
     */
    public void cargarTemporada(modelo.Temporada t) {
        txtId.setText(String.valueOf(t.getIdTemporada()));
        txtNombre.setText(t.getNombre());
        txtFechaInicio.setText(t.getFechaInicio());
        txtFechaFin.setText(t.getFechaFin());

        // Calcular recargo desde el factor
        double recargo = (t.getFactor() - 1.0) * 100;
        txtRecargo.setText(String.format("%.2f", recargo));
        txtFactor.setText(String.format("%.2f", t.getFactor()));
    }

    /**
     * Obtiene la temporada desde el formulario con validaciones básicas Las
     * validaciones exhaustivas se hacen en el controlador
     *
     * @return Temporada con los datos del formulario o null si hay error
     */
    public modelo.Temporada obtenerTemporada() {
        // Validaciones básicas
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
            return null;
        }
        if (txtFechaInicio.getText().trim().isEmpty()
                || txtFechaFin.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar fechas");
            return null;
        }
        if (txtFactor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un factor");
            return null;
        }

        // Crear temporada
        modelo.Temporada t = new modelo.Temporada();

        // ID solo si se está editando
        if (!txtId.getText().trim().isEmpty()) {
            t.setIdTemporada(Integer.parseInt(txtId.getText()));
        }

        t.setNombre(txtNombre.getText().trim());
        t.setFechaInicio(txtFechaInicio.getText().trim());
        t.setFechaFin(txtFechaFin.getText().trim());

        // Usar el factor directamente
        t.setFactor(Double.parseDouble(txtFactor.getText().trim()));

        return t;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtFechaInicio = new javax.swing.JTextField();
        txtRecargo = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        txtFechaFin = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtFactor = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(500, 450));
        setPreferredSize(new java.awt.Dimension(500, 450));

        jLabel1.setText("ID:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Fecha Inicio:");

        jLabel4.setText("Fecha Fin:");

        jLabel5.setText("Recargo (%):");

        txtFechaInicio.addActionListener(this::txtFechaInicioActionPerformed);

        txtRecargo.addActionListener(this::txtRecargoActionPerformed);

        btnGuardar.setText("📝 Guardar ");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnCancelar.setText("🚫 Cancelar ");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        jLabel6.setText("Factor:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtFechaFin))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFechaInicio))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(58, 58, 58)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(25, 25, 25)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnGuardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCancelar))
                            .addComponent(txtRecargo)
                            .addComponent(txtFactor))))
                .addContainerGap(118, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addComponent(jLabel4))
                    .addComponent(txtFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtRecargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtFactor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar))
                .addGap(53, 53, 53))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtRecargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRecargoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRecargoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtFechaInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaInicioActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            TemporadaDialog dialog = new TemporadaDialog(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCancelar;
    public javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    public javax.swing.JTextField txtFactor;
    public javax.swing.JTextField txtFechaFin;
    public javax.swing.JTextField txtFechaInicio;
    public javax.swing.JTextField txtId;
    public javax.swing.JTextField txtNombre;
    public javax.swing.JTextField txtRecargo;
    // End of variables declaration//GEN-END:variables
}
