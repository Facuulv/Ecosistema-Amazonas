<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecosistema.amazonas.sistema;

/**
 *
 * @author 54351
 */
public class Ecosistema {
    // Borrar luego
}
package ecosistema.amazonas.sistema;

import ecosistema.amazonas.modelo.Entidad;
import java.util.ArrayList;
import java.util.List;

public class Ecosistema {
    private List<Entidad> entidades = new ArrayList<>();
    private int totalMuertes = 0; // Para llevar la cuenta

    public void agregarEntidad(Entidad e) {
        entidades.add(e);
    }

    public void avanzarTurno() {
        // Lógica de actuación
        for (int i = 0; i < entidades.size(); i++) {
            Entidad e = entidades.get(i);
            if (e.estaVivo()) {
                e.actuar(this);
            }
        }

        // Lógica de muertes (lo que pide tu nota en image_fb82e7.png)
        for (int i = entidades.size() - 1; i >= 0; i--) {
            if (!entidades.get(i).estaVivo()) {
                entidades.remove(i);
                totalMuertes++;
            }
        }
    }

    // ESTE ES EL MÉTODO QUE TE FALTA O TIENE ERROR
    public void generarReporteFinal() {
        System.out.println("\n--- REPORTE FINAL ---");
        System.out.println("Sobrevivientes: " + entidades.size());
        System.out.println("Muertes totales: " + totalMuertes);
        System.out.println("---------------------");
    }
}
