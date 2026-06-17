/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ecosistema.amazonas.modelo;

import ecosistema.amazonas.modelo.Ecosistema;
/**
 *
 * @author facu_
 */
public interface Reproducible {
    void reproducirse(Ecosistema eco);

    boolean puedeReproducirse();

    /**
     * Método default que centraliza la lógica de reproducción.
     */
    default void intentarReproduccion(Ecosistema eco) {
        if (puedeReproducirse()) {
            reproducirse(eco);
        }
    }
}
