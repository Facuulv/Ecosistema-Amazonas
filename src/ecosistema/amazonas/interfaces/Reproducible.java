/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ecosistema.amazonas.interfaces;

/**
 *
 * @author facu_
 */
public interface Reproducible {
    void reproducirse(Ecosistema eco);

    boolean puedeReproducirse(Ecosistema eco);

    /**
     * Método default que centraliza la lógica de reproducción.
     */
    default void intentarReproduccion(Ecosistema eco) {
        if (puedeReproducirse(eco)) {
            reproducirse(eco);
        }
    }
}
