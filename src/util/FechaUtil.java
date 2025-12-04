
package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FechaUtil {

    private static final SimpleDateFormat formatoBD = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat formatoVista = new SimpleDateFormat("dd/MM/yyyy");

    public static String dateToString(Date d) {
        return formatoBD.format(d);
    }

    public static String dateToStringVista(Date d) {
        return formatoVista.format(d);
    }

    public static Date stringToDate(String s) {
        try {
            return formatoBD.parse(s);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date stringVistaToDate(String s) {
        try {
            return formatoVista.parse(s);
        } catch (ParseException e) {
            return null;
        }
    }
}