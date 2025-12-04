
package dao;

import modelo.Temporada;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TemporadaDAO {

    public boolean insertarTemporada(Temporada t) {
        String sql = "CALL sp_insert_temporada(?,?,?,?)";

        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setString(1, t.getNombre());
            cs.setString(2, t.getFechaInicio());
            cs.setString(3, t.getFechaFin());
            cs.setDouble(4, t.getFactor());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("Error insertarTemporada(): " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarTemporada(Temporada t) {
        String sql = "CALL sp_update_temporada(?,?,?,?,?)";

        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setInt(1, t.getIdTemporada());
            cs.setString(2, t.getNombre());
            cs.setString(3, t.getFechaInicio());
            cs.setString(4, t.getFechaFin());
            cs.setDouble(5, t.getFactor());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("Error actualizarTemporada(): " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarTemporada(int id) {
        String sql = "CALL sp_delete_temporada(?)";

        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("Error eliminarTemporada(): " + e.getMessage());
            return false;
        }
    }

    public Temporada getTemporadaById(int id) {
        String sql = "CALL sp_get_temporada_by_id(?)";

        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                Temporada t = new Temporada();
                t.setIdTemporada(rs.getInt("idTemporada"));
                t.setNombre(rs.getString("nombre"));
                t.setFechaInicio(rs.getString("fechaInicio"));
                t.setFechaFin(rs.getString("fechaFin"));
                t.setFactor(rs.getDouble("factor"));
                return t;
            }

        } catch (SQLException e) {
            System.err.println("Error getTemporadaById(): " + e.getMessage());
        }

        return null;
    }

    public List<Temporada> listarTemporadas() {
        List<Temporada> lista = new ArrayList<>();
        String sql = "SELECT * FROM Temporada ORDER BY fechaInicio";

        try (Connection cn = DBUtil.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Temporada t = new Temporada();
                t.setIdTemporada(rs.getInt("idTemporada"));
                t.setNombre(rs.getString("nombre"));
                t.setFechaInicio(rs.getString("fechaInicio"));
                t.setFechaFin(rs.getString("fechaFin"));
                t.setFactor(rs.getDouble("factor"));
                lista.add(t);
            }

        } catch (SQLException e) {
            System.err.println("Error listarTemporadas(): " + e.getMessage());
        }

        return lista;
    }
}