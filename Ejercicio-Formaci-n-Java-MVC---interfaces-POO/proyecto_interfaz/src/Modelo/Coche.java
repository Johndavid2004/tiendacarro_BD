package Modelo;

public class Coche extends Vehiculo {
    private int puertas;

    public Coche(String marca, String modelo, String placa, String combustible, String color,
                 int anio, boolean aireAcondicionado, boolean vidriosElectricos, int idUsuario,
                 int puertas) {
        super(marca, modelo, placa, combustible, color, anio, aireAcondicionado, vidriosElectricos, idUsuario);
        this.puertas = puertas;
    }

    public int getPuertas() {
        return puertas;
    }
}
