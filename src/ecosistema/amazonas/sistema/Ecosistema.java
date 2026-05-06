/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecosistema.amazonas.sistema;

/**
 *
 * @author Documentos
 */

import ecosistema.amazonas.modelo.Entidad;
import ecosistema.amazonas.modelo.Planta;
import ecosistema.amazonas.modelo.Conejo;
import java.util.ArrayList;
import java.util.List;

public class Ecosistema {
    private ArrayList<Planta> plantas;
    private ArrayList<Conejo> conejos; 
    private List<Entidad> nuevosNacimientos;
    
    public Ecosistema() {
        this.plantas = new ArrayList<>();
        this.conejos = new ArrayList<>();
        this.nuevosNacimientos = new ArrayList<>();
    }
    
    public void agregarEntidad(Entidad nueva) {
        this.nuevosNacimientos.add(nueva);
    }
    
    
    public Planta buscarPlantaDisponible() {
        for (Planta p : plantas) {
            if (p.estaVivo()) {
                return p;
            }
        }
        return null;
    } 
    
    public void mostrarEstado() {
        System.out.println("Estado actual: " + 
            "Plantas: " + plantas.size() + " | " +
            "Conejos: " + conejos.size());
        System.out.println("--------------------------------------");
    }
}
