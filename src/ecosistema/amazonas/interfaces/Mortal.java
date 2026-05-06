/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecosistema.amazonas.interfaces;

/**
 *
 * @author Documentos
 */
public interface Mortal {
    boolean estaVivo();
    void morir();
    default void verificarMuerte(double energia) {
        if (energia <= 0) {
            morir();
            System.out.println("--- Una entidad ha muerto por falta de energía ---");
        }
    }
}
