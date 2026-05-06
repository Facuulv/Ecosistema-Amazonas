/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecosistema.amazonas.modelo;
import ecosistema.amazonas.sistema.Ecosistema;

/**
 *
 * @author facu_
 */
  

    public  abstract class Entidad {
        
    private String nombre;
    private double energia;
    private int edad;
    private boolean viva;
    
    
     public Entidad(String nombre, double energia, int edad) {
        this.nombre = nombre;
        this.energia = energia;
        this.edad = edad;
        this.viva = true;
    }
     
     
        public abstract void actuar(Ecosistema eco);
        public abstract void mostrarEstado();
        
         public void envejecer() {
        edad++;
        energia -= 5;

        if (energia <= 0) {
            energia = 0;
            viva = false;
        }
    }
           public String getNombre() {
        return nombre;
    }

    public double getEnergia() {
        return energia;
    }

    public void setEnergia(double energia) {
        this.energia = Math.max(energia, 0);
    }

    public int getEdad() {
        return edad;
    }

    public boolean estaVivo() {
        return viva;
    }

    public void morir() {
        viva = false;
    }
    

        
}
