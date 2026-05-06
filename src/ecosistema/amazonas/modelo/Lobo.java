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
public class Lobo  extends Animal {
    public Lobo(String nombre, double energia, int edad) {
        super(nombre, energia, edad);}
    
    @Override
    public void actuar(Ecosistema eco) {
        if (!estaVivo()) return;

        System.out.println("El lobo " + getNombre() + " está buscando presas...");
        
       
        cazar(eco);
        
        envejecer();
    }
    private void cazar(Ecosistema eco) {
        
        setEnergia(getEnergia() + 20); 
        System.out.println(getNombre() + " ha cazado exitosamente.");
    }

    @Override
    public void mostrarEstado() {
        System.out.println("[Lobo] Nombre: " + getNombre() + 
                           " | Energía: " + getEnergia() + 
                           " | Edad: " + getEdad() + 
                           " | Vivo: " + estaVivo());
    }
}

