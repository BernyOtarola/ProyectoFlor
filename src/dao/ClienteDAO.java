
package dao;

import modelo.Cliente;
import util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public boolean insertarCliente(Cliente c) {
        String sql = "CALL sp_insert_cliente(?,?,?,?,?)";
        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setString(1, c.getNombre());
            cs.setString(2, c.getIdentificacion());
            cs.setString(3, c.getTelefono());
            cs.setString(4, c.getCorreo());
            cs.setString(5, c.getDireccion());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("Error insertarCliente(): " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarCliente(Cliente c) {
        String sql = "CALL sp_update_cliente(?,?,?,?,?)";
        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setInt(1, c.getIdCliente());
            cs.setString(2, c.getNombre());
            cs.setString(3, c.getTelefono());
            cs.setString(4, c.getCorreo());
            cs.setString(5, c.getDireccion());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("Error actualizarCliente(): " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarCliente(int id) {
        String sql = "CALL sp_delete_cliente(?)";
        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("Error eliminarCliente(): " + e.getMessage());
            return false;
        }
    }

    public Cliente getClienteById(int id) {
        String sql = "CALL sp_get_cliente_by_id(?)";
        try (Connection cn = DBUtil.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setNombre(rs.getString("nombre"));
                c.setIdentificacion(rs.getString("identificacion"));
                c.setTelefono(rs.getString("telefono"));
                c.setCorreo(rs.getString("correo"));
                c.setDireccion(rs.getString("direccion"));
                c.setFechaRegistro(rs.getString("fechaRegistro"));
                return c;
            }

        } catch (SQLException e) {
            System.err.println("Error getClienteById(): " + e.getMessage());
        }
        return null;
    }

    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM Cliente ORDER BY nombre";

        try (Connection cn = DBUtil.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setNombre(rs.getString("nombre"));
                c.setIdentificacion(rs.getString("identificacion"));
                c.setTelefono(rs.getString("telefono"));
                c.setCorreo(rs.getString("correo"));
                c.setDireccion(rs.getString("direccion"));
                c.setFechaRegistro(rs.getString("fechaRegistro"));
                lista.add(c);
            }

        } catch (SQLException e) {
            System.err.println("Error listarClientes(): " + e.getMessage());
        }

        return lista;
    }
}