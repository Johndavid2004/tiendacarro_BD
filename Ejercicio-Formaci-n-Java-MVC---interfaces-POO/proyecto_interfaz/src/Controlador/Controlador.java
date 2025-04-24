package Controlador;

import Modelo.Administrador;
import Modelo.Coche;
import Modelo.Conductor;
import Modelo.Moto;
import Modelo.Usuario;
import Modelo.Vehiculo;
import conexionBD.ConexionBD;

import vista.formulario_registro_usuario;
import vista.formulario_registro_vehiculo;
import vista.tabla_registro_usuarios;
import vista.vista_lista_vehiculos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

public class Controlador {
    
    // Vistas
    private final formulario_registro_usuario formUsuario;
    private final formulario_registro_vehiculo formVehiculo;
    private final tabla_registro_usuarios vistaUsuarios;
    private final vista_lista_vehiculos vistaListaVehiculos;

    // Constructor
    public Controlador() {
        formUsuario = new formulario_registro_usuario();
        formVehiculo = new formulario_registro_vehiculo();
        vistaUsuarios = new tabla_registro_usuarios();
        vistaListaVehiculos = new vista_lista_vehiculos();

        // Mostrar las ventanas
        formUsuario.setVisible(true);
        formVehiculo.setVisible(true);
        vistaUsuarios.setVisible(true);
        vistaListaVehiculos.setVisible(true);

        // Evento: Registrar usuario
        formUsuario.getRegistrarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });

        // Evento: Registrar vehículo
        formVehiculo.getRegistrarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarVehiculo();
            }
        });

        // Evento: Cargar usuarios en la tabla
        vistaUsuarios.getBtnCargarUsuarios().addActionListener(e -> {
            List<Usuario> lista = ConexionBD.obtenerUsuarios(); // Método del modelo
            vistaUsuarios.cargarTablaUsuarios(lista);
        });

        // Evento: Cargar vehículos de un usuario
        vistaListaVehiculos.getBtnCargarVehiculos().addActionListener(e -> {
            int usuarioId = 1; // ⚠ Reemplaza esto con ID seleccionado desde tabla más adelante
            List<String> vehiculos = ConexionBD.obtenerVehiculosPorUsuario(usuarioId);
            vistaListaVehiculos.cargarListaVehiculos(vehiculos);
        });
        
        vistaUsuarios.getTablaUsuarios().addMouseListener(new java.awt.event.MouseAdapter() {
    @       Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = vistaUsuarios.getTablaUsuarios().getSelectedRow();

                if (fila != -1) {
                    int id = (int) vistaUsuarios.getTablaUsuarios().getValueAt(fila, 0); // columna ID
                    conexionBD.ConexionBD.idDelUsuarioSeleccionado = id;

                    // 🔄 Obtener y mostrar vehículos del usuario clickeado
                    List<String> vehiculos = ConexionBD.obtenerVehiculosPorUsuario(id);
                    vistaListaVehiculos.cargarListaVehiculos(vehiculos);

                    // Opcional: mostrar mensaje o refrescar ventana
                    System.out.println("Vehículos del usuario ID " + id + " cargados.");
                }
            }
        });

    }

    // Mostrar solo formularios (si deseas hacerlo desde otro lado)
    public void mostrarFormularioUsuario() {
        formUsuario.setVisible(true);
    }

    public void mostrarFormularioVehiculo() {
        formVehiculo.setVisible(true);
    }

    // Lógica para registrar usuario
    private void registrarUsuario() {
        String nombre = formUsuario.getNombreField().getText().trim();
        String email = formUsuario.getEmailField().getText().trim();
        String tipo = formUsuario.getTipoUsuarioCombo().getSelectedItem().toString();

        if (nombre.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(formUsuario, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario;
        if (tipo.equals("ADMINISTRADOR")) {
            usuario = new Administrador(nombre, email, "Alto");
        } else {
            usuario = new Conductor(nombre, email, "123456");
        }

        if (ConexionBD.registrarUsuario(usuario, tipo)) {
            JOptionPane.showMessageDialog(formUsuario, "Usuario registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Lógica para registrar vehículo
    private void registrarVehiculo() {
        String marca = formVehiculo.getMarcaField().getText().trim();
        String modelo = formVehiculo.getModeloField().getText().trim();
        String placa = formVehiculo.getPlacaField().getText().trim();
        String tipo = formVehiculo.getTipoVehiculoCombo().getSelectedItem().toString();
        String combustible = formVehiculo.getCombustibleSeleccionado(); // JRadioButton seleccionado
        String color = formVehiculo.getColorSeleccionado(); // JRadioButton seleccionado
        int anio = Integer.parseInt(formVehiculo.getAnioCombo().getSelectedItem().toString());
        boolean aire = formVehiculo.getCheckAire().isSelected();
        boolean vidrios = formVehiculo.getCheckVidrios().isSelected();
        int idUsuario = conexionBD.ConexionBD.idDelUsuarioSeleccionado;

        // Validación básica
        if (marca.isEmpty() || modelo.isEmpty() || placa.isEmpty() || combustible == null || color == null) {
            JOptionPane.showMessageDialog(formVehiculo, "Por favor, complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    Vehiculo vehiculo;

    if (tipo.equals("AUTOMOVIL")) {
        int puertas = 4; // Puedes hacer dinámico si gustas
        vehiculo = new Coche(marca, modelo, placa, combustible, color, anio, aire, vidrios, idUsuario, puertas);
    } else {
        int cilindrada = 150; // Puedes hacer dinámico si gustas
        vehiculo = new Moto(marca, modelo, placa, combustible, color, anio, aire, vidrios, idUsuario, cilindrada);
    }

    if (ConexionBD.registrarVehiculo(vehiculo, tipo)) {
        JOptionPane.showMessageDialog(formVehiculo, "Vehículo registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(formVehiculo, "Error al registrar el vehículo.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

}
