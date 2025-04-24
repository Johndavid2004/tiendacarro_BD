package Modelo;

public class Vehiculo {
    protected String marca;
    protected String modelo;
    protected String placa;
    protected String combustible;
    protected String color;
    protected int anio;
    protected boolean aireAcondicionado;
    protected boolean vidriosElectricos;
    protected int idUsuario; 

    public Vehiculo(String marca, String modelo, String placa, String combustible, String color, int anio, boolean aireAcondicionado, boolean vidriosElectricos, int idUsuario) {
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.combustible = combustible;
        this.color = color;
        this.anio = anio;
        this.aireAcondicionado = aireAcondicionado;
        this.vidriosElectricos = vidriosElectricos;
        this.idUsuario = idUsuario;
    }

    public String getMarca(){
        return marca; 
    }
    
    public String getModelo() { 
        return modelo; 
    }
    
    public String getPlaca() { 
        return placa; 
    }
    
    public String getCombustible() { 
        return combustible; 
    }
    
    public String getColor() { 
        return color; 
    }
    
    public int getAnio() { 
        return anio; 
    }
    
    public boolean tieneAireAcondicionado() { 
        return aireAcondicionado; 
    }
    
    public boolean tieneVidriosElectricos() { 
        return vidriosElectricos; 
    }
    
    public int getIdUsuario() { 
        return idUsuario; 
    }
}
