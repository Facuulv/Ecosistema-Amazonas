/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecosistema.amazonas.sistema;

import ecosistema.amazonas.modelo.Entidad;
import ecosistema.amazonas.modelo.Planta;
import ecosistema.amazonas.modelo.Conejo;
import ecosistema.amazonas.modelo.Lobo;
import java.util.ArrayList;
import java.util.List;

public class Ecosistema {

    private List<Entidad> entidades = new ArrayList<>();
    private int totalMuertes = 0; // Para llevar la cuenta
    private Clima climaActual;
    private int turnosTotales;
    private int turnoActual;

    public Ecosistema(Clima climaInicial, int turnosTotales) {
        this.climaActual = climaInicial;
        this.turnosTotales = turnosTotales;
        this.turnoActual = 0;
    }

    public void inicializar(int cantPlantas, int cantConejos, int cantLobos) {

        for (int i = 1; i <= cantPlantas; i++) {
            agregarEntidad("planta");
        }

        for (int i = 1; i <= cantConejos; i++) {
            agregarEntidad("conejo");
        }

        for (int i = 1; i <= cantLobos; i++) {
            agregarEntidad("lobo");
        }
    }

    public void agregarEntidad(Entidad e) {
        entidades.add(e);
    }

    public void agregarEntidad(String tipo) {

        if (tipo.equals("planta")) {

            Planta planta = new Planta("Planta", 0, 50, true);
            entidades.add(planta);

        } else if (tipo.equals("conejo")) {

            Conejo conejo = new Conejo("Conejo", 1, 80, true, 10, 5.0);
            entidades.add(conejo);

        } else if (tipo.equals("lobo")) {

            Lobo lobo = new Lobo("Lobo", 100, 2);
            entidades.add(lobo);
        }
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
