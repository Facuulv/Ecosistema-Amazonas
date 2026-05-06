/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecosistema.amazonas.modelo;

/**
 *
 * @author facu_
 */
import ecosistema.amazonas.sistema.Ecosistema;

public abstract class Entidad {
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
       this.edad++;
        setEnergia(this.energia - 5); // Gasto de energía base por turno [cite: 73]
    
    }
    public void morir() {
        this.viva = false;
        this.energia = 0;
    }
    public
         String getNombre() { return nombre; }
    public double getEnergia() { return energia; }
    public void setEnergia(double energia) {
        this.energia = Math.max(0, energia);
        if (this.energia <= 0) morir();
    }
    public int getEdad() { return edad; }
    public boolean estaVivo() { return viva; }
}
