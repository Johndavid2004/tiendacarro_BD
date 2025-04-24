package Modelo;

public class Moto extends Vehiculo {
    private int cilindrada;

    public Moto(String marca, String modelo, String placa, String combustible, String color,
                int anio, boolean aireAcondicionado, boolean vidriosElectricos, int idUsuario,
                int cilindrada) {
        super(marca, modelo, placa, combustible, color, anio, aireAcondicionado, vidriosElectricos, idUsuario);
        this.cilindrada = cilindrada;
    }

    public int getCilindrada() {
        return cilindrada;
    }
}
