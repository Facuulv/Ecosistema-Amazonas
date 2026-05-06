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
public abstract class Animal extends Entidad   {
    public Animal(String nombre, double energia, int edad) {
        super(nombre, energia, edad);}
    
    public void desplazarse() {
        setEnergia(getEnergia() - 2);
        System.out.println(getNombre() + " se ha desplazado.");
    }
    @Override
    public void envejecer() {
        super.envejecer(); // Llama a la lógica de Entidad (edad++ y energia -5)
        // Podríamos decir que los animales son más frágiles al envejecer
        if (getEdad() > 10) {
            setEnergia(getEnergia() - 2);
        }
    }
    
}
