package util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ValidacionUtil {

    /**
     * @param s
     * @return 
     */
    public static boolean esVacio(String s) {
        return s == null || s.trim().isEmpty();
    }

    /**
     * @param s
     * @return 
     */
    public static boolean esNumero(String s) {
        if (esVacio(s)) return false;
        
        try {
            Double.valueOf(s.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @param s
     * @return 
     */
    public static boolean esEntero(String s) {
        if (esVacio(s)) return false;
        
        try {
            Integer.valueOf(s.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Valida formato de fecha YYYY-MM-DD
     * CORREGIDO: Ahora valida correctamente el formato
     */
    public static boolean validarFormatoFecha(String fecha) {
        if (esVacio(fecha)) return false;
        
        try {
            LocalDate.parse(fecha); // Valida formato yyyy-MM-dd
            return true;
        } catch (DateTimeParseException e) {
            System.err.println("⚠️ Formato de fecha inválido: " + fecha);
            return false;
        }
    }

    /**
     * Valida que un rango de fechas sea lógico
     * CORREGIDO: Valida que no sean fechas pasadas y que el rango sea válido
     * @param inicio
     * @param fin
     * @return 
     */
    public static boolean rangoFechasValido(String inicio, String fin) {
        // Validar formato
        if (!validarFormatoFecha(inicio) || !validarFormatoFecha(fin)) {
            System.err.println("❌ Formato de fechas inválido");
            return false;
        }
        
        try {
            LocalDate fechaInicio = LocalDate.parse(inicio);
            LocalDate fechaFin = LocalDate.parse(fin);
            LocalDate hoy = LocalDate.now();
            
            // ⭐ Validar que la fecha inicio no sea pasada
            if (fechaInicio.isBefore(hoy)) {
                System.err.println("⚠️ La fecha de inicio no puede ser anterior a hoy");
                return false;
            }
            
            // ⭐ Validar que la fecha fin sea posterior a la inicio
            if (fechaFin.isBefore(fechaInicio)) {
                System.err.println("⚠️ La fecha fin debe ser posterior a la fecha inicio");
                return false;
            }
            
            // ⭐ Validar que el rango sea razonable (máximo 365 días)
            long diasDiferencia = java.time.temporal.ChronoUnit.DAYS.between(fechaInicio, fechaFin);
            if (diasDiferencia > 365) {
                System.err.println("⚠️ El rango de fechas no puede ser mayor a 365 días");
                return false;
            }
            
            return true;
            
        } catch (Exception e) {
            System.err.println("❌ Error validando fechas: " + e.getMessage());
            return false;
        }
    }

    /**
     * Valida que un número sea positivo
     * @param s
     * @return 
     */
    public static boolean esNumeroPositivo(String s) {
        if (!esNumero(s)) return false;
        
        try {
            double numero = Double.parseDouble(s.trim());
            return numero > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Valida que un entero sea positivo
     * @param s
     * @return 
     */
    public static boolean esEnteroPositivo(String s) {
        if (!esEntero(s)) return false;
        
        try {
            int numero = Integer.parseInt(s.trim());
            return numero > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}