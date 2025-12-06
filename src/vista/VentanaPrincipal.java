package vista;

import vista.cliente.ClientePanel;
import vista.empleado.EmpleadoPanel;
import vista.reserva.ReservaPanel;
import vista.temporada.TemporadaPanel;
import vista.vehiculo.VehiculoPanel;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends javax.swing.JFrame {

    public VentanaPrincipal() {
        initComponents();
        configurarVentana();
        cargarPestanas();
    }

    private void configurarVentana() {
        setTitle("🚗 Sistema de Reservas de Vehículos - ProyectoFlor");
        setSize(1400, 850);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Icono y Look & Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarPestanas() {
        // Personalizar las pestañas
        tabPrincipal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabPrincipal.setBackground(new Color(240, 240, 240));
        
        tabPrincipal.addTab("📋 Clientes", new ClientePanel());
        tabPrincipal.addTab("👤 Empleados", new EmpleadoPanel());
        tabPrincipal.addTab("🚗 Vehículos", new VehiculoPanel());
        tabPrincipal.addTab("📅 Temporadas", new TemporadaPanel());
        tabPrincipal.addTab("🎫 Reservas", new ReservaPanel());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabPrincipal = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1200, 700));
        setPreferredSize(new java.awt.Dimension(1200, 700));

        tabPrincipal.setMinimumSize(new java.awt.Dimension(1200, 700));
        tabPrincipal.setPreferredSize(new java.awt.Dimension(1200, 700));
        tabPrincipal.setRequestFocusEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane tabPrincipal;
    // End of variables declaration//GEN-END:variables
}
