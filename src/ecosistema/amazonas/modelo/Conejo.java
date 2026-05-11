/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecosistema.amazonas.modelo;

/**
 *
 * @author Documentos
 */
import ecosistema.amazonas.sistema.Ecosistema;
import ecosistema.amazonas.interfaces.Reproducible;

public class Conejo extends Animal implements Reproducible {

    public Conejo(String nombre, double energia, int edad, int velocidad, double peso) {
        super(nombre, edad, energia, true, velocidad, peso);
    }

    @Override
    public void comer(Ecosistema eco) {
        Planta comida = eco.buscarPlantaDisponible();
        if (comida != null) {
            double energiaGanada = comida.serComida();
            setEnergia(getEnergia() + energiaGanada);
            System.out.println(getNombre() + " comió una planta (+" + energiaGanada + " energía)");
        } else {
            setEnergia(getEnergia() - 15);
            System.out.println(getNombre() + " no encontró comida (-15 energía)");
        }
    }

    @Override
    public void actuar(Ecosistema eco) {

        if (estaVivo()) {

            comer(eco);

            if (eco.esClima("Soleado")) {
                setEnergia(getEnergia() + 5);
            } else if (eco.esClima("Lluvioso")) {
                setEnergia(getEnergia() + 3);
            } else if (eco.esClima("Sequía")) {
                setEnergia(getEnergia() - 5);
            } else if (eco.esClima("Invierno")) {
                setEnergia(getEnergia() - 8);
            }

            envejecer();

            intentarReproduccion(eco);
            verificarMuerte();
        }
    }

    @Override
    public boolean puedeReproducirse() {

        return getEnergia() > 120;
    }

    @Override
    public void mostrarEstado() {
        System.out.print("[Conejo] " + getNombre() + " | Energía: " + getEnergia());
        if (getEnergia() < 20) {
            System.out.print(" ¡EN PELIGRO!");
        }
        System.out.println();
    }

    @Override
    public void reproducirse(Ecosistema eco) {

        if (puedeReproducirse() && eco.getCantidadConejosVivos() >= 2 && eco.getCantidadPlantas() >= 15) {

            int velocidad = (int) (Math.random() * 11) + 5;
            double peso = 1 + Math.random() * 4;

            eco.agregarEntidad(new Conejo("Cría de " + getNombre(), 30, 0, velocidad, peso));
            
            int costoEnergia = 50;

            if (eco.esClima("Sequía") || eco.esClima("Invierno")) {
                costoEnergia = 70;
            }

            setEnergia(getEnergia() - costoEnergia);

            System.out.println(getNombre() + " se reprodujo.");
        }
    }
}
