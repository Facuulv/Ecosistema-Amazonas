/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ecosistema.amazonas.modelo;

/**
 *
 * @author 54351
 */
public interface Mortal {
    
    boolean estaVivo();
    void morir();
    double getEnergia();

    // fuerza la muerte si la energia es menor o igual a cero
    default void verificarMuerte() {
        if (getEnergia() <= 0) {
            morir();
            System.out.println("Una entidad ha muerto.");
        }
    }
}
