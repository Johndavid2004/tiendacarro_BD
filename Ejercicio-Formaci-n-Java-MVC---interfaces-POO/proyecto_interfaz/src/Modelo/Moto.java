package Modelo;

public class Moto extends Vehiculo {
    private int cilindrada;

    public Moto(String marca, String modelo, String placa, int cilindrada) {
        super(marca, modelo, placa);
        this.cilindrada = cilindrada;
    }

    public int getCilindrada() {
        return cilindrada;
    }
}
