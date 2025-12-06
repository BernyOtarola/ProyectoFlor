package util;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;


public class ModernComponents {

    // Paleta de colores moderna
    public static final Color PRIMARY = new Color(41, 128, 185);
    public static final Color SUCCESS = new Color(46, 204, 113);
    public static final Color DANGER = new Color(231, 76, 60);
    public static final Color WARNING = new Color(241, 196, 15);
    public static final Color INFO = new Color(52, 152, 219);
    public static final Color SECONDARY = new Color(149, 165, 166);
    public static final Color DARK = new Color(52, 73, 94);
    public static final Color LIGHT = new Color(236, 240, 241);
    public static final Color WHITE = Color.WHITE;
    
    /**
     * Crea un botón moderno con estilo personalizado
     * @param texto
     * @param color
     * @param icono
     * @return 
     */
    public static JButton crearBotonModerno(String texto, Color color, String icono) {
        JButton btn = new JButton(icono + " " + texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fondo con gradiente
                GradientPaint gradient = new GradientPaint(
                    0, 0, color,
                    0, getHeight(), color.darker()
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Efecto hover
                if (getModel().isRollover()) {
                    g2d.setColor(new Color(255, 255, 255, 30));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                }
                
                // Efecto presionado
                if (getModel().isPressed()) {
                    g2d.setColor(new Color(0, 0, 0, 30));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                }
                
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 40));
        
        // Efecto de hover
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btn.repaint();
            }
        });
        
        return btn;
    }
    
    /**
     * Crea un campo de texto moderno con placeholder
     * @param placeholder
     * @return 
     */
    public static JTextField crearCampoTextoModerno(String placeholder) {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                if (getText().isEmpty() && !isFocusOwner()) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(SECONDARY);
                    g2d.setFont(getFont().deriveFont(Font.ITALIC));
                    
                    FontMetrics fm = g2d.getFontMetrics();
                    int x = getInsets().left;
                    int y = (getHeight() + fm.getAscent()) / 2 - 2;
                    g2d.drawString(placeholder, x, y);
                    g2d.dispose();
                }
            }
        };
        
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setPreferredSize(new Dimension(250, 40));
        
        // Efecto focus
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY, 2, true),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
                field.repaint();
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(SECONDARY, 1, true),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
                field.repaint();
            }
        });
        
        return field;
    }
    
    /**
     * Crea una tabla moderna con estilos mejorados
     * @param modelo
     * @return 
     */
    public static JTable crearTablaModerna(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Configuración general
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(35);
        tabla.setShowGrid(true);
        tabla.setGridColor(new Color(224, 224, 224));
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setSelectionBackground(INFO);
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        
        // Header personalizado
        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(DARK);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setBorder(BorderFactory.createEmptyBorder());
        
        // Renderer personalizado para celdas
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                         boolean isSelected, boolean hasFocus,
                                                         int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : LIGHT);
                }
                
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return c;
            }
        });
        
        // Efecto hover (requiere un mouse listener personalizado)
        tabla.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = tabla.rowAtPoint(e.getPoint());
                if (row > -1) {
                    tabla.setRowSelectionInterval(row, row);
                }
            }
        });
        
        return tabla;
    }
    
    /**
     * Crea un panel con tarjeta (card) moderna
     * @param titulo
     * @return 
     */
    public static JPanel crearCard(String titulo) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Título del card
        if (titulo != null && !titulo.isEmpty()) {
            JLabel lblTitulo = new JLabel(titulo);
            lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
            lblTitulo.setForeground(DARK);
            card.add(lblTitulo, BorderLayout.NORTH);
        }
        
        return card;
    }
    
    /**
     * Crea un separador moderno
     * @return 
     */
    public static JSeparator crearSeparador() {
        JSeparator separator = new JSeparator();
        separator.setForeground(SECONDARY);
        separator.setPreferredSize(new Dimension(0, 1));
        return separator;
    }
    
    /**
     * Crea un label con estilo de encabezado
     * @param texto
     * @param nivel
     * @return 
     */
    public static JLabel crearEncabezado(String texto, int nivel) {
        JLabel label = new JLabel(texto);
        
        switch (nivel) {
            case 1 -> label.setFont(new Font("Segoe UI", Font.BOLD, 24));
            case 2 -> label.setFont(new Font("Segoe UI", Font.BOLD, 20));
            case 3 -> label.setFont(new Font("Segoe UI", Font.BOLD, 16));
            default -> label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        }
        
        label.setForeground(DARK);
        return label;
    }
    
    /**
     * Crea un ComboBox moderno
     * @return 
     */
    public static JComboBox<String> crearComboModerno() {
        JComboBox<String> combo = new JComboBox<>();
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setBackground(WHITE);
        combo.setForeground(DARK);
        combo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        combo.setPreferredSize(new Dimension(200, 40));
        combo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return combo;
    }
    
    /**
     * Crea un panel de búsqueda moderno
     * @return 
     */
    public static JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, LIGHT),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        return panel;
    }
    
    /**
     * Crea un diálogo de confirmación moderno
     * @param parent
     * @param mensaje
     * @param titulo
     * @return 
     */
    public static int mostrarConfirmacion(Component parent, String mensaje, String titulo) {
        UIManager.put("OptionPane.background", WHITE);
        UIManager.put("Panel.background", WHITE);
        UIManager.put("OptionPane.messageForeground", DARK);
        UIManager.put("Button.background", PRIMARY);
        UIManager.put("Button.foreground", Color.WHITE);
        
        return JOptionPane.showConfirmDialog(
            parent,
            mensaje,
            titulo,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
    }
    
    /**
     * Muestra un mensaje de éxito
     * @param parent
     * @param mensaje
     */
    public static void mostrarExito(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(
            parent,
            mensaje,
            "✅ Operación Exitosa",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Muestra un mensaje de error
     * @param parent
     * @param mensaje
     */
    public static void mostrarError(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(
            parent,
            mensaje,
            "❌ Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Muestra un mensaje de advertencia
     * @param parent
     * @param mensaje
     */
    public static void mostrarAdvertencia(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(
            parent,
            mensaje,
            "⚠️ Advertencia",
            JOptionPane.WARNING_MESSAGE
        );
    }
    
    /**
     * Crea un badge (etiqueta con estilo)
     * @param texto
     * @param color
     * @return 
     */
    public static JLabel crearBadge(String texto, Color color) {
        JLabel badge = new JLabel(texto, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(color);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(Color.WHITE);
        badge.setOpaque(false);
        badge.setPreferredSize(new Dimension(80, 25));
        
        return badge;
    }
    
    /**
     * Añade un efecto de elevación (sombra) a un componente
     * @param componente
     */
    public static void agregarSombra(JComponent componente) {
        componente.setBorder(BorderFactory.createCompoundBorder(
            new Border() {
                @Override
                public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Sombra
                    for (int i = 0; i < 5; i++) {
                        g2d.setColor(new Color(0, 0, 0, 10 - i * 2));
                        g2d.drawRoundRect(x + i, y + i, width - i * 2, height - i * 2, 10, 10);
                    }
                    
                    g2d.dispose();
                }
                
                @Override
                public Insets getBorderInsets(Component c) {
                    return new Insets(5, 5, 5, 5);
                }
                
                @Override
                public boolean isBorderOpaque() {
                    return false;
                }
            },
            componente.getBorder()
        ));
    }
}