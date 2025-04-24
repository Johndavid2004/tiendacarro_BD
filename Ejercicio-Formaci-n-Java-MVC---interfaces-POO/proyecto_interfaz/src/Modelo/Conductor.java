package Modelo;

public class Conductor extends Usuario {
    private String licencia;

    public Conductor(String nombre, String email, String licencia) {
        super(nombre, email);
        this.licencia = licencia;
    }

    public String getLicencia() {
        return licencia;
    }
}
