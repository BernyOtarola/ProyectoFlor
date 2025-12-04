package util;

import javax.swing.JOptionPane;
import java.awt.Component;

public class MensajeUtil {

    public static void info(Component c, String msg) {
        JOptionPane.showMessageDialog(c, msg, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void error(Component c, String msg) {
        JOptionPane.showMessageDialog(c, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean confirmar(Component c, String msg) {
        int r = JOptionPane.showConfirmDialog(
                c,
                msg,
                "Confirmar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        return r == JOptionPane.YES_OPTION;
    }
}