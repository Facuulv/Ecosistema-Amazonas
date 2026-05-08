/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecosistema.amazonas.modelo;

import ecosistema.amazonas.sistema.Ecosistema;
import ecosistema.amazonas.interfaces.Reproducible;

public class Planta extends Entidad implements Reproducible {

    private int tamanio;

    public Planta(String nombre, double energia, int edad, int tamanio) {
        super(nombre, edad, energia, true);
        this.tamanio = Math.min(5, Math.max(1, tamanio));
    }

    public double serComida() {
        double valorNutritivo = tamanio * 10.0;
        morir();
        return valorNutritivo;
    }

    @Override
    public void actuar(Ecosistema eco) {
        if (estaVivo()) {
            envejecer();
            if (!eco.esClima("Invierno")) {
                intentarReproduccion(eco);
            }
        }
    }

    @Override
    public boolean puedeReproducirse() {

        return getEnergia() > 30;
    }

    @Override
    public void reproducirse(Ecosistema eco) {

        int cantidadNuevas = 1;

        if (eco.esClima("Soleado")) {
            cantidadNuevas = 2;
        } else if (eco.esClima("Lluvioso")) {
            cantidadNuevas = 2;
        } else if (eco.esClima("Sequía")) {
            cantidadNuevas = 0;
        } else if (eco.esClima("Invierno")) {
            cantidadNuevas = 0;
        }

        for (int i = 1; i <= cantidadNuevas; i++) {

            int tamanioNuevo = (int) (Math.random() * 5) + 1;

            eco.agregarEntidad(
                    new Planta(
                            "Brote de " + getNombre(),
                            20,
                            0,
                            tamanioNuevo
                    )
            );
        }

        setEnergia(getEnergia() - 20);
    }

    @Override
    public void mostrarEstado() {
        System.out.println("[Planta] " + getNombre() + "Tamaño " + tamanio + " | Energía: " + getEnergia() + " | Viva: " + estaVivo());
    }
}
