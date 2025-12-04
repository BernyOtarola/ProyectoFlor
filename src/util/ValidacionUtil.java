
package util;

public class ValidacionUtil {

    public static boolean esVacio(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean esNumero(String s) {
        try {
            Double.valueOf(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean esEntero(String s) {
        try {
            Integer.valueOf(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean rangoFechasValido(String inicio, String fin) {
        try {
            return FechaUtil.stringToDate(inicio).compareTo(FechaUtil.stringToDate(fin)) <= 0;
        } catch (Exception e) {
            return false;
        }
    }
}