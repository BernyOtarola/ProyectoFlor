package vista;

import vista.cliente.ClientePanel;
import vista.empleado.EmpleadoPanel;
import vista.reserva.ReservaPanel;
import vista.temporada.TemporadaPanel;
import vista.vehiculo.VehiculoPanel;
import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class VentanaPrincipal extends JFrame {

    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 73, 94);
    private static final Color ACCENT_COLOR = new Color(46, 204, 113);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final Color HOVER_COLOR = new Color(52, 152, 219);
    private JPanel headerPanel;
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JLabel lblFechaHora;
    private Timer relojTimer;

    public VentanaPrincipal() {
        configurarVentana();
        inicializarComponentes();
        aplicarEstilosModernos();
        iniciarReloj();
        setVisible(true);
    }

    private void configurarVentana() {
        setTitle("Sistema de Reservas de Vehículos - UTN");
        setSize(1400, 900);
        setMinimumSize(new Dimension(1200, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.err.println("Error configurando Look and Feel: " + e.getMessage());
        }

        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        crearHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        crearTabbedPane();
        add(tabPrincipal, BorderLayout.CENTER);
        crearFooterPanel();
    }

    private void crearHeaderPanel() {
        headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 100));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        
        lblTitulo = new JLabel("Sistema de Reservas de Vehículos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        lblSubtitulo = new JLabel("Gestión Integral de Reservas - UTN");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(236, 240, 241));
        
        leftPanel.add(lblTitulo);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(lblSubtitulo);
        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);

        lblFechaHora = new JLabel();
        lblFechaHora.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblFechaHora.setForeground(Color.WHITE);
        lblFechaHora.setAlignmentX(Component.RIGHT_ALIGNMENT);

        rightPanel.add(lblFechaHora);

        headerPanel.add(leftPanel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);

        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(0, 0, 0, 30)),
                BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));
    }

    private void crearTabbedPane() {
        tabPrincipal = new JTabbedPane();
        tabPrincipal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabPrincipal.setBackground(BACKGROUND_COLOR);
        tabPrincipal.setForeground(TEXT_COLOR);

        agregarPestanaConEstilo("Clientes", new ClientePanel(), 0);
        agregarPestanaConEstilo("Empleados", new EmpleadoPanel(), 1);
        agregarPestanaConEstilo("Vehículos", new VehiculoPanel(), 2);
        agregarPestanaConEstilo("Temporadas", new TemporadaPanel(), 3);
        agregarPestanaConEstilo("Reservas", new ReservaPanel(), 4);

        tabPrincipal.addChangeListener(e -> {
            int index = tabPrincipal.getSelectedIndex();
            System.out.println("✅ Pestaña seleccionada: " + tabPrincipal.getTitleAt(index));
        });
    }

    private void agregarPestanaConEstilo(String titulo, JPanel panel, int index) {

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BACKGROUND_COLOR);
        wrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        wrapper.add(panel, BorderLayout.CENTER);

        tabPrincipal.addTab(titulo, wrapper);
        tabPrincipal.setBackgroundAt(index, CARD_COLOR);
    }

    private void aplicarEstilosModernos() {
        
        UIManager.put("TabbedPane.selected", CARD_COLOR);
        UIManager.put("TabbedPane.contentAreaColor", BACKGROUND_COLOR);
        UIManager.put("TabbedPane.borderHightlightColor", PRIMARY_COLOR);
        UIManager.put("TabbedPane.selectHighlight", PRIMARY_COLOR);
        UIManager.put("TabbedPane.focus", PRIMARY_COLOR);

        UIManager.put("Button.background", PRIMARY_COLOR);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 12));

        UIManager.put("Table.gridColor", new Color(189, 195, 199));
        UIManager.put("Table.selectionBackground", HOVER_COLOR);
        UIManager.put("Table.selectionForeground", Color.WHITE);

        tabPrincipal.setBackground(BACKGROUND_COLOR);
        tabPrincipal.setOpaque(true);

        tabPrincipal.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
                    int x, int y, int w, int h, boolean isSelected) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                if (isSelected) {
                    g2d.setColor(PRIMARY_COLOR);
                } else {
                    g2d.setColor(CARD_COLOR);
                }

                g2d.fillRoundRect(x, y, w, h, 10, 10);
            }

            @Override
            protected void paintText(Graphics g, int tabPlacement, Font font,
                    FontMetrics metrics, int tabIndex, String title,
                    Rectangle textRect, boolean isSelected) {
                g.setFont(font);
                g.setColor(isSelected ? Color.WHITE : TEXT_COLOR);
                g.drawString(title, textRect.x, textRect.y + metrics.getAscent());
            }
        });
    }

    private void crearFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BorderLayout());
        footerPanel.setBackground(SECONDARY_COLOR);
        footerPanel.setPreferredSize(new Dimension(getWidth(), 40));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JLabel lblFooter = new JLabel("© 2024 Sistema de Reservas - Universidad Técnica Nacional | Versión 2.0");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFooter.setForeground(new Color(189, 195, 199));

        JLabel lblEstado = new JLabel("Sistema Operativo");
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEstado.setForeground(ACCENT_COLOR);

        footerPanel.add(lblFooter, BorderLayout.WEST);
        footerPanel.add(lblEstado, BorderLayout.EAST);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private void iniciarReloj() {
        relojTimer = new Timer(1000, e -> actualizarReloj());
        relojTimer.start();
        actualizarReloj();
    }

    private void actualizarReloj() {
        java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
        java.time.format.DateTimeFormatter formato
                = java.time.format.DateTimeFormatter.ofPattern(
                        "EEEE, dd 'de' MMMM 'de' yyyy | HH:mm:ss",
                        new java.util.Locale("es", "CR")
                );
        lblFechaHora.setText(ahora.format(formato));
    }

    private static void mostrarSplashScreen() {
        JWindow splash = new JWindow();
        splash.setSize(500, 300);
        splash.setLocationRelativeTo(null);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(PRIMARY_COLOR);
        content.setBorder(BorderFactory.createLineBorder(SECONDARY_COLOR, 3));

        JLabel lblLogo = new JLabel("🚗", SwingConstants.CENTER);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 80));
        lblLogo.setForeground(Color.WHITE);

        JLabel lblCargando = new JLabel("Sistema de Reservas de Vehículos", SwingConstants.CENTER);
        lblCargando.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblCargando.setForeground(Color.WHITE);

        JLabel lblVersion = new JLabel("Cargando versión 2.0...", SwingConstants.CENTER);
        lblVersion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblVersion.setForeground(new Color(236, 240, 241));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(lblLogo);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(lblCargando);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(lblVersion);

        content.add(centerPanel, BorderLayout.CENTER);
        splash.setContentPane(content);
        splash.setVisible(true);

        // Cerrar splash después de 2 segundos
        Timer timer = new Timer(2000, e -> splash.dispose());
        timer.setRepeats(false);
        timer.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
    public static void main(String[] args) {
        // Configurar Look and Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            }
        }

        // Crear ventana en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            System.out.println("🚀 Iniciando Sistema de Reservas de Vehículos...");

            // Mostrar splash screen
            mostrarSplashScreen();

            // Crear ventana principal
            new VentanaPrincipal();

            System.out.println("✅ Sistema iniciado correctamente");
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane tabPrincipal;
    // End of variables declaration//GEN-END:variables

}
