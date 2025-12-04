package dao;

import modelo.Vehiculo;
import util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    public boolean insertarVehiculo(Vehiculo v) {
        String sql = "CALL sp_insert_vehiculo(?,?,?,?,?,?)";
        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setString(1, v.getPlaca());
            cs.setString(2, v.getMarca());
            cs.setString(3, v.getModelo());
            cs.setInt(4, v.getAnio());
            cs.setDouble(5, v.getPrecioDia());
            cs.setString(6, v.getEstado());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("Error insertarVehiculo(): " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarVehiculo(Vehiculo v) {
        String sql = "CALL sp_update_vehiculo(?,?,?,?,?,?,?)";
        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setInt(1, v.getIdVehiculo());
            cs.setString(2, v.getPlaca());
            cs.setString(3, v.getMarca());
            cs.setString(4, v.getModelo());
            cs.setInt(5, v.getAnio());
            cs.setDouble(6, v.getPrecioDia());
            cs.setString(7, v.getEstado());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("Error actualizarVehiculo(): " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarVehiculo(int id) {
        String sql = "CALL sp_delete_vehiculo(?)";
        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("Error eliminarVehiculo(): " + e.getMessage());
            return false;
        }
    }

    public Vehiculo getVehiculoById(int id) {
        String sql = "CALL sp_get_vehiculo_by_id(?)";
        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                Vehiculo v = new Vehiculo();
                v.setIdVehiculo(rs.getInt("idVehiculo"));
                v.setPlaca(rs.getString("placa"));
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setAnio(rs.getInt("anio"));
                v.setPrecioDia(rs.getDouble("precioDia"));
                v.setEstado(rs.getString("estado"));
                return v;
            }

        } catch (SQLException e) {
            System.err.println("Error getVehiculoById(): " + e.getMessage());
        }

        return null;
    }

    public List<Vehiculo> listarVehiculos() {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Vehiculo ORDER BY marca, modelo";

        try (Connection cn = DBUtil.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Vehiculo v = new Vehiculo();
                v.setIdVehiculo(rs.getInt("idVehiculo"));
                v.setPlaca(rs.getString("placa"));
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setAnio(rs.getInt("anio"));
                v.setPrecioDia(rs.getDouble("precioDia"));
                v.setEstado(rs.getString("estado"));
                lista.add(v);
            }

        } catch (SQLException e) {
            System.err.println("Error listarVehiculos(): " + e.getMessage());
        }

        return lista;
    }

    /**
     * NUEVO: Lista solo vehículos disponibles para reservar
     * @return 
     */
    public List<Vehiculo> listarVehiculosDisponibles() {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Vehiculo WHERE estado = 'Disponible' ORDER BY marca, modelo";

        try (Connection cn = DBUtil.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Vehiculo v = new Vehiculo();
                v.setIdVehiculo(rs.getInt("idVehiculo"));
                v.setPlaca(rs.getString("placa"));
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setAnio(rs.getInt("anio"));
                v.setPrecioDia(rs.getDouble("precioDia"));
                v.setEstado(rs.getString("estado"));
                lista.add(v);
            }

        } catch (SQLException e) {
            System.err.println("Error listarVehiculosDisponibles(): " + e.getMessage());
        }

        return lista;
    }
}