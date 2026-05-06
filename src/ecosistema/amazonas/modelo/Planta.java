/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecosistema.amazonas.modelo;

import ecosistema.amazonas.sistema.Ecosistema;
import ecosistema.amazonas.interfaces.Reproducible;

public class Planta extends Entidad implements Reproducible {
    private int tamanio;
public Planta(String nombre, double energia, int edad) {
        super(nombre, energia, edad);
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
            reproducirse(eco);
        }
    }

    @Override
    public void reproducirse(Ecosistema eco) {
        if (getEnergia() > 40) { // 
            eco.agregarEntidad(new Planta("Brote de " + getNombre(), 20, 0));
            setEnergia(getEnergia() - 20);
        }
    }

    @Override
    public void mostrarEstado() {
        System.out.println("[Planta] " + getNombre() + "Tamaño " + tamanio + " | Energía: " + getEnergia() + " | Viva: " + estaVivo());
    }
}
