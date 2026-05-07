/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecosistema.amazonas.modelo;
import ecosistema.amazonas.sistema.Ecosistema;
public abstract class Entidad {
    private String nombre;
    private double energia;
    private int edad;
    private boolean viva;

    //Getters
    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public double getEnergia() {
        return energia;
    }

    public boolean getViva() {
        return viva;
    }

    public Entidad(String nombre, int edad, double energia, boolean viva) {
        setearNombre(nombre);
        setearEdad(edad);
        setearEnergia(energia);
        setearViva(viva);
    }

    public void verificarEstadoVital() {
        verificarMuerte(this.energia);
    }

    public void procesarTurno() {
        avanzarTurno();
    }

    public void setearNombre(String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            this.nombre = nombre;
        } else {
            System.out.println("Error, el nombre no puede estar vacío.");
        }
    }

    public void setearEdad(int edad) {
        this.edad = edad;
        // Hay que poner verificaciones de edad en cada clase segun el animal o la planta
    }

    public void setearEnergia(double energia) {
        if (energia > 0) {
            this.energia = energia;
        } else {
            System.out.println("Error, la energia no puede ser nula ni ser menor o igual a cero.");
        }
        verificarMuerte();
    }

    public void setearViva(boolean viva) {
        this.viva = viva;
    }

    public abstract void actuar(Ecosistema eco);

    public abstract void comer(Ecosistema eco);

    public abstract void mostrarEstado();

    protected void envejecer() {
        edad++;
        energia -= 2;

        if (energia < 0) {
            energia = 0;
        }
    }

    // Recuerden setear en cada clase la cantidad de seres que debe haber, que como minimo debe haber 5 en plantas y tal
}
