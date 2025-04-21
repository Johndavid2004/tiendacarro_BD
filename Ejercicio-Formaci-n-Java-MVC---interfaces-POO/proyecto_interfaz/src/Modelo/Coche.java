package Modelo;

public class Coche extends Vehiculo {
    private int puertas;

    public Coche(String marca, String modelo, String placa, int puertas) {
        super(marca, modelo, placa);
        this.puertas = puertas;
    }

    public int getPuertas() {
        return puertas;
    }
}
 