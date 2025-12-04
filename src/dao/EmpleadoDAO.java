
package dao;

import modelo.Empleado;
import util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    public boolean insertarEmpleado(Empleado e) {
        String sql = "CALL sp_insert_empleado(?,?,?,?)";

        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setString(1, e.getNombre());
            cs.setString(2, e.getCargo());
            cs.setString(3, e.getUsuario());
            cs.setString(4, e.getClave());

            cs.execute();
            return true;

        } catch (SQLException ex) {
            System.err.println("Error insertarEmpleado(): " + ex.getMessage());
            return false;
        }
    }

    public boolean actualizarEmpleado(Empleado e) {
        String sql = "CALL sp_update_empleado(?,?,?,?,?)";

        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setInt(1, e.getIdEmpleado());
            cs.setString(2, e.getNombre());
            cs.setString(3, e.getCargo());
            cs.setString(4, e.getUsuario());
            cs.setString(5, e.getClave());

            cs.execute();
            return true;

        } catch (SQLException ex) {
            System.err.println("Error actualizarEmpleado(): " + ex.getMessage());
            return false;
        }
    }

    public boolean eliminarEmpleado(int id) {
        String sql = "CALL sp_delete_empleado(?)";

        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.execute();
            return true;

        } catch (SQLException ex) {
            System.err.println("Error eliminarEmpleado(): " + ex.getMessage());
            return false;
        }
    }

    public Empleado getEmpleadoById(int id) {
        String sql = "CALL sp_get_empleado_by_id(?)";

        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                Empleado e = new Empleado();
                e.setIdEmpleado(rs.getInt("idEmpleado"));
                e.setNombre(rs.getString("nombre"));
                e.setCargo(rs.getString("cargo"));
                e.setUsuario(rs.getString("usuario"));
                e.setClave(rs.getString("clave"));
                return e;
            }

        } catch (SQLException ex) {
            System.err.println("Error getEmpleadoById(): " + ex.getMessage());
        }

        return null;
    }

    public List<Empleado> listarEmpleados() {
        List<Empleado> lista = new ArrayList<>();

        String sql = "SELECT * FROM Empleado ORDER BY nombre";

        try (Connection cn = DBUtil.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Empleado e = new Empleado();
                e.setIdEmpleado(rs.getInt("idEmpleado"));
                e.setNombre(rs.getString("nombre"));
                e.setCargo(rs.getString("cargo"));
                e.setUsuario(rs.getString("usuario"));
                e.setClave(rs.getString("clave"));
                lista.add(e);
            }

        } catch (SQLException ex) {
            System.err.println("Error listarEmpleados(): " + ex.getMessage());
        }

        return lista;
    }
}