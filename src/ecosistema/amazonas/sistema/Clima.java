package ecosistema.amazonas.sistema;

import java.util.Random;

public class Clima {
    private String estado; // Ejemplo: Soleado, Lluvioso, Tormenta
    private Random random = new Random();

    public Clima() {
        this.estado = "Soleado";
    }

    public void cambiarClima() {
        String[] opciones = {"Soleado", "Lluvioso", "Tormenta"};
        this.estado = opciones[random.nextInt(opciones.length)];
        System.out.println("El clima ha cambiado a: " + estado);
    }

    public double getImpactoEnergia() {
        
        switch (estado) {
            case "Tormenta": return -10.0; 
            case "Lluvioso": return -2.0;
            default: return 0.0; 
        }
    }

    public String getEstado() {
        return estado;
    }
}