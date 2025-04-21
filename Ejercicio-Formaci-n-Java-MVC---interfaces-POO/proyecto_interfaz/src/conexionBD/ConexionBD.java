package conexionBD;

import Modelo.Administrador;
import Modelo.Coche;
import Modelo.Conductor;
import Modelo.Moto;
import Modelo.Usuario;
import Modelo.Vehiculo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3307/taller_mecanico";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "12345678";

    // ⚠️ Este ID debe ser seteado desde el controlador
    public static int idDelUsuarioSeleccionado;

    // ------------------ CONEXIÓN ------------------
    public static Connection conectar() {
        try {
            Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos");
            return conexion;
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            return null;
        }
    }

    // ------------------ REGISTRAR USUARIO ------------------
    public static boolean registrarUsuario(Usuario usuario, String tipoUsuario) {
        String query = "INSERT INTO usuarios (nombre, email, tipo, dato_extra) VALUES (?, ?, ?, ?)";
        try (Connection conexion = conectar();
             PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, tipoUsuario);
            stmt.setString(4, usuario instanceof Administrador ? ((Administrador) usuario).getNivelAcceso()
                            : usuario instanceof Conductor ? ((Conductor) usuario).getLicencia()
                            : "N/A");

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    // ------------------ REGISTRAR VEHÍCULO ------------------
    public static boolean registrarVehiculo(Vehiculo vehiculo, String tipoVehiculo) {
        String query = "INSERT INTO vehiculos (usuario_id, marca, modelo, placa, tipo, dato_extra) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexion = conectar();
             PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setInt(1, idDelUsuarioSeleccionado); // El ID del usuario seleccionado
            stmt.setString(2, vehiculo.getMarca());
            stmt.setString(3, vehiculo.getModelo());
            stmt.setString(4, vehiculo.getPlaca());
            stmt.setString(5, tipoVehiculo);
            stmt.setString(6, vehiculo instanceof Coche ? String.valueOf(((Coche) vehiculo).getPuertas())
                            : vehiculo instanceof Moto ? String.valueOf(((Moto) vehiculo).getCilindrada())
                            : "N/A");

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar vehículo: " + e.getMessage());
            return false;
        }
    }

    // ------------------ OBTENER USUARIOS ------------------
    public static List<Usuario> obtenerUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String query = "SELECT * FROM usuarios";

        try (Connection conexion = conectar();
             PreparedStatement stmt = conexion.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");

                Usuario u = new Usuario(nombre, email);
                u.setId(id); // Tu clase Usuario debe tener setId()
                lista.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener usuarios: " + e.getMessage());
        }

        return lista;
    }

    // ------------------ OBTENER VEHÍCULOS POR USUARIO ------------------
    public static List<String> obtenerVehiculosPorUsuario(int usuarioId) {
        List<String> lista = new ArrayList<>();
        String query = "SELECT marca, modelo, placa, tipo, dato_extra FROM vehiculos WHERE usuario_id = ?";

        try (Connection conexion = conectar();
             PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                String placa = rs.getString("placa");
                String tipo = rs.getString("tipo");
                String extra = rs.getString("dato_extra");

                String descripcion = String.format("%s %s (%s) - %s: %s", marca, modelo, placa, tipo, extra);
                lista.add(descripcion);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener vehículos: " + e.getMessage());
        }

        return lista;
    }
}
