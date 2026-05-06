/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ecosistema.amazonas.interfaces;

/**
 *
 * @author 54351
 */
public interface Mortal {
    
    boolean viva();
    void morir();
    double getEnergia();

    default void verificarMuerte() {
        if (getEnergia() <= 0) {
            morir();
            System.out.println(nombre + " no tiene más energía, y ha fallecido...");
        }
    }
}
