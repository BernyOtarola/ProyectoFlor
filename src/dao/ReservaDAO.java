package dao;

import modelo.Reserva;
import modelo.DetalleReserva;
import util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    /**
     * Inserta una reserva completa con sus detalles usando JSON
     * @param r
     * @param detalles
     * @param jsonDetalles
     * @return 
     */
    public boolean insertarReservaCompleta(Reserva r, List<DetalleReserva> detalles, String jsonDetalles) {
        String sql = "CALL sp_insert_reserva_con_detalle(?,?,?,?,?)";

        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setInt(1, r.getIdCliente());
            cs.setInt(2, r.getIdEmpleado());
            cs.setString(3, r.getFechaInicio());
            cs.setString(4, r.getFechaFin());
            cs.setString(5, jsonDetalles);

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("Error insertarReservaCompleta(): " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todas las reservas con información del cliente
     * @return 
     */
    public List<Reserva> listarReservas() {
        List<Reserva> lista = new ArrayList<>();
        String sql = "CALL sp_listar_reservas()";

        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Reserva r = new Reserva();
                r.setIdReserva(rs.getInt("idReserva"));
                r.setIdCliente(rs.getInt("idCliente"));
                r.setClienteNombre(rs.getString("clienteNombre"));
                r.setIdEmpleado(rs.getInt("idEmpleado"));
                r.setFechaReserva(rs.getString("fechaReserva"));
                r.setFechaInicio(rs.getString("fechaInicio"));
                r.setFechaFin(rs.getString("fechaFin"));
                r.setTotal(rs.getDouble("total"));
                lista.add(r);
            }

        } catch (SQLException e) {
            System.err.println("Error listarReservas(): " + e.getMessage());
        }

        return lista;
    }

    /**
     * Lista reservas por rango de fechas
     * @param fechaInicio
     * @param fechaFin
     * @return 
     */
    public List<Reserva> listarReservasPorFechas(String fechaInicio, String fechaFin) {
        List<Reserva> lista = new ArrayList<>();
        String sql = "CALL sp_listar_reservas_por_fechas(?,?)";

        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setString(1, fechaInicio);
            cs.setString(2, fechaFin);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Reserva r = new Reserva();
                r.setIdReserva(rs.getInt("idReserva"));
                r.setIdCliente(rs.getInt("idCliente"));
                r.setClienteNombre(rs.getString("clienteNombre"));
                r.setIdEmpleado(rs.getInt("idEmpleado"));
                r.setFechaReserva(rs.getString("fechaReserva"));
                r.setFechaInicio(rs.getString("fechaInicio"));
                r.setFechaFin(rs.getString("fechaFin"));
                r.setTotal(rs.getDouble("total"));
                lista.add(r);
            }

        } catch (SQLException e) {
            System.err.println("Error listarReservasPorFechas(): " + e.getMessage());
        }

        return lista;
    }

    /**
     * Obtiene una reserva por ID
     * @param id
     * @return 
     */
    public Reserva getReservaById(int id) {
        Reserva r = null;
        String sql = "SELECT * FROM Reservas WHERE idReserva = ?";

        try (Connection cn = DBUtil.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                r = new Reserva();
                r.setIdReserva(rs.getInt("idReserva"));
                r.setIdCliente(rs.getInt("idCliente"));
                r.setIdEmpleado(rs.getInt("idEmpleado"));
                r.setFechaReserva(rs.getString("fechaReserva"));
                r.setFechaInicio(rs.getString("fechaInicio"));
                r.setFechaFin(rs.getString("fechaFin"));
                r.setTotal(rs.getDouble("total"));
            }

        } catch (SQLException e) {
            System.err.println("Error getReservaById(): " + e.getMessage());
        }

        return r;
    }

    /**
     * Elimina una reserva
     * @param id
     * @return 
     */
    public boolean eliminarReserva(int id) {
        String sql = "DELETE FROM Reservas WHERE idReserva = ?";

        try (Connection cn = DBUtil.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error eliminarReserva(): " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica disponibilidad de vehículo en fechas específicas
     * @param idVehiculo
     * @param fechaInicio
     * @param fechaFin
     * @return 
     */
    public boolean verificarDisponibilidad(int idVehiculo, String fechaInicio, String fechaFin) {
        String sql = "CALL sp_verificar_disponibilidad_vehiculo(?,?,?)";

        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setInt(1, idVehiculo);
            cs.setString(2, fechaInicio);
            cs.setString(3, fechaFin);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                int conflictos = rs.getInt("reservasConflicto");
                return conflictos == 0; // true si está disponible
            }

        } catch (SQLException e) {
            System.err.println("Error verificarDisponibilidad(): " + e.getMessage());
        }

        return false;
    }
}