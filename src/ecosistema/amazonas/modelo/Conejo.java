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

public class Conejo extends Animal implements Reproducible{
    public Conejo (String nombre, double energia, int edad, int velocidad, double peso){
        super(nombre, energia, edad, velocidad, peso);
    }
   @Override
    public void comer(Ecosistema eco) {
        Planta comida = eco.buscarPlantaDisponible();
        if (comida != null) {
            double energiaGanada = comida.serComida();
           setEnergia(getEnergia() + energiaGanada);
            System.out.println(getNombre() + " comió una planta (+"+ energiaGanada +" energía)");
        } else {
            setEnergia(getEnergia() - 15); // Pérdida exacta por consigna 
        }
    }
    @Override
    public void actuar(Ecosistema eco) {
        if (estaVivo()) {
            comer(eco); 
            envejecer();
            reproducirse(eco); 
        }
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
        // Requisito: Energía > 60 [cite: 89]
        if (getEnergia() > 60) {
            eco.agregarEntidad(new Conejo("Cría de " + getNombre(), 30, 0, 10, 1.5));
            setEnergia(getEnergia() - 30);
        }
    }
}
