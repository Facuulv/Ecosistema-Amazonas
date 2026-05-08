/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecosistema.amazonas.modelo;

import ecosistema.amazonas.sistema.Ecosistema;

/**
 *
 * @author Faxxir 2
 */
public class Lobo extends Animal {

    private int exitosCaza;

    public Lobo(String nombre, double energia, int edad) {
        super(nombre, edad, energia, true, 12, 35.0);
        this.exitosCaza = 0;
    }

    @Override
    public void actuar(Ecosistema eco) {

        if (!estaVivo()) {
            return;
        }

        System.out.println("El lobo " + getNombre() + " está buscando presas...");

        comer(eco);

        if (eco.esClima("Lluvioso")) {
            setEnergia(getEnergia() - 5);
        }

        envejecer();
    }

    @Override
    public void comer(Ecosistema eco) {
        cazar(eco);
    }

    private void cazar(Ecosistema eco) {

        Conejo presa = eco.buscarConejoDisponible();

        if (presa != null) {

            double probabilidad = getEnergia() / 180.0;

            if (eco.esClima("Invierno")) {
                probabilidad = probabilidad + 0.20;
            }

            if (probabilidad > 0.70) {
                probabilidad = 0.70;
            }

            double azar = Math.random();

            if (azar < probabilidad) {

                presa.morir();
                exitosCaza++;

                setEnergia(getEnergia() + 30);

                System.out.println(
                        getNombre()
                        + " cazó a "
                        + presa.getNombre()
                        + " (+30 energía) [cacerías: "
                        + exitosCaza
                        + "]"
                );

            } else {

                setEnergia(getEnergia() - 10);

                System.out.println(getNombre() + " falló la caza (-10 energía)");
            }

        } else {

            setEnergia(getEnergia() - 15);

            System.out.println(getNombre() + " no encontró conejos para cazar (-15 energía)");
        }
    }

    @Override
    public void mostrarEstado() {

        System.out.println(
                "[Lobo] Nombre: "
                + getNombre()
                + " | Energía: "
                + getEnergia()
                + " | Edad: "
                + getEdad()
                + " | Cacerías exitosas: "
                + exitosCaza
                + " | Vivo: "
                + estaVivo()
        );
    }
}
