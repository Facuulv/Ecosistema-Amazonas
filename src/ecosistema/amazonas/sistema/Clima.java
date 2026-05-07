package ecosistema.amazonas.sistema;

import java.util.Random;

public class Clima {

    private String estado; // Ejemplo: Soleado, Lluvioso, Tormenta
    private Random random = new Random();

    public Clima(String estado) {
        this.estado = estado;
    }

    public void cambiarClima() {
        String[] opciones = {"Soleado", "Lluvioso", "Sequía", "Invierno"};
        this.estado = opciones[random.nextInt(opciones.length)];
        System.out.println("El clima ha cambiado a: " + estado);
    }

    public double getMultiplicadorReproduccionPlantas() {
        switch (estado) {
            case "Soleado":
                return 1.5;
            case "Lluvioso":
                return 2.0;
            case "Sequía":
                return 0.5;
            case "Invierno":
                return 0.0;
            default:
                return 1.0;
        }
    }

    public double getEnergiaExtraConejo() {
        switch (estado) {
            case "Soleado":
                return 5.0;
            case "Lluvioso":
                return 3.0;
            case "Sequía":
                return -5.0;
            case "Invierno":
                return -8.0;
            default:
                return 0.0;
        }
    }

    public double getEnergiaExtraLobo() {
        switch (estado) {
            case "Lluvioso":
                return -5.0;
            default:
                return 0.0;
        }
    }
    
    public double getBonusCazaLobo() {
        switch (estado) {
            case "Invierno":
                return 20.0;
            default:
                return 0.0;
        }
    }

    public String getEstado() {
        return estado;
    }
    
    @Override
    public String toString() {
        return estado;
}
}
