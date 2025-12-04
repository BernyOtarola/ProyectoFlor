package dao;

import modelo.DetalleReserva;
import util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleReservaDAO {

    /**
     * Lista los detalles de una reserva específica con información del vehículo
     * @param idReserva
     * @return 
     */
    public List<DetalleReserva> listarDetallesPorReserva(int idReserva) {
        List<DetalleReserva> lista = new ArrayList<>();
        String sql = "CALL sp_get_detalle_reserva(?)";

        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setInt(1, idReserva);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                DetalleReserva d = new DetalleReserva();
                d.setIdDetalle(rs.getInt("idDetalle"));
                d.setIdReserva(rs.getInt("idReserva"));
                d.setIdVehiculo(rs.getInt("idVehiculo"));
                d.setPlacaVehiculo(rs.getString("placa"));
                d.setPrecio(rs.getDouble("precio"));
                d.setDias(rs.getInt("dias"));
                d.setSubtotal(rs.getDouble("subtotal"));
                lista.add(d);
            }

        } catch (SQLException e) {
            System.err.println("Error listarDetallesPorReserva(): " + e.getMessage());
        }

        return lista;
    }
}
