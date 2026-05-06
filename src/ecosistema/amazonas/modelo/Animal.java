/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecosistema.amazonas.modelo;

/**
 *
 * @author Documentos
 */
import ecosistema.amazonas.interfaces.Mortal;
import ecosistema.amazonas.sistema.Ecosistema;
        
public abstract class Animal extends Entidad implements Mortal {
   
    private int velocidad;
    private double peso;

    public Animal(String nombre, double energia, int edad, int velocidad, double peso) {
        super(nombre, energia, edad);
        
        this.velocidad = velocidad;
        this.peso = peso;
    }

    public abstract void comer(Ecosistema eco); // Obligatorio 

    public void moverse() {
        System.out.println(getNombre() + " se desplazó por el ecosistema.");
    }
}
