/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecosistema.amazonas.modelo;

import ecosistema.amazonas.sistema.Ecosistema;

/**
 *
 * @author 54351
 */
abstract class Animal extends Entidad {

    private int velocidad;
    private double peso;

    // Getters
    public Animal(String nombre, int edad, double energia, boolean viva, int velocidad, double peso) {
        super(nombre, edad, energia, viva);
        this.velocidad = velocidad;
        this.peso = peso;
    }

    @Override
    public void setearEdad(int edad) {
        if (edad >= 0 && edad <= 20) {
            super.setearEdad(edad);
        } else {
            System.out.println("Error: El animal es demasiado grande!.");
        }
    }

    public abstract void comer(Ecosistema eco);

    public void moverse() {
        System.out.println(getNombre() + " se desplazó.");
    }

    public void setearVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public void setearPeso(double peso) {
        if (peso >= 1.5 && peso <= 80) {
            this.peso = peso;
        } else {
            System.out.println("Error: El peso es excesivo o es muy poco");
        }
    }
}
