package util;

import modelo.DetalleReserva;
import javax.swing.JTable;
import java.util.List;

/**
 * Utilidad para generar JSON manualmente sin librerías externas
 * Compatible con MySQL JSON
 */
public class JSONUtil {

    /**
     * Genera JSON desde una lista de DetalleReserva
     * Formato: [{"idVehiculo":1,"precio":45000.0,"dias":3},...]
     * @param detalles
     * @return 
     */
    public static String generarJSONDetalles(List<DetalleReserva> detalles) {
        StringBuilder json = new StringBuilder();
        json.append("[");

        for (int i = 0; i < detalles.size(); i++) {
            DetalleReserva d = detalles.get(i);

            json.append("{");
            json.append("\"idVehiculo\":").append(d.getIdVehiculo()).append(",");
            json.append("\"precio\":").append(d.getPrecio()).append(",");
            json.append("\"dias\":").append(d.getDias());
            json.append("}");

            if (i < detalles.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");
        return json.toString();
    }

    /**
     * Genera JSON desde una JTable (usado en ReservaDialog)
     * Lee directamente de la tabla de detalles en la interfaz
     * Columnas esperadas: IdVehiculo, Placa, Precio, Dias, Subtotal
     * @param tabla
     * @return 
     */
    public static String generarJSONDesdeTabla(JTable tabla) {
        StringBuilder json = new StringBuilder();
        json.append("[");

        int rowCount = tabla.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            // Obtener datos de la fila
            int idVehiculo = Integer.parseInt(tabla.getValueAt(i, 0).toString());
            double precio = Double.parseDouble(tabla.getValueAt(i, 2).toString());
            int dias = Integer.parseInt(tabla.getValueAt(i, 3).toString());

            json.append("{");
            json.append("\"idVehiculo\":").append(idVehiculo).append(",");
            json.append("\"precio\":").append(precio).append(",");
            json.append("\"dias\":").append(dias);
            json.append("}");

            if (i < rowCount - 1) {
                json.append(",");
            }
        }

        json.append("]");
        return json.toString();
    }

    /**
     * Escapa caracteres especiales para JSON
     */
    private static String escaparJSON(String texto) {
        if (texto == null) return "";
        
        return texto
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");
    }

    /**
     * Valida si un string es JSON válido (básico)
     * @param json
     * @return 
     */
    public static boolean esJSONValido(String json) {
        if (json == null || json.trim().isEmpty()) {
            return false;
        }

        json = json.trim();
        return (json.startsWith("{") && json.endsWith("}")) ||
               (json.startsWith("[") && json.endsWith("]"));
    }
}