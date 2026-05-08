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

            double energia = 30 + Math.random() * 40; // 30 a 70
            int tamanio = (int) (Math.random() * 5) + 1; // 1 a 5

            Planta planta = new Planta("Planta", energia, 0, tamanio);

            entidades.add(planta);

        } else if (tipo.equals("conejo")) {

            double energia = 40 + Math.random() * 40; // 40 a 80
            int velocidad = (int) (Math.random() * 11) + 5; // 5 a 15
            double peso = 1 + Math.random() * 4; // 1 a 5

            Conejo conejo = new Conejo("Conejo", energia, 0, velocidad, peso);

            entidades.add(conejo);

        } else if (tipo.equals("lobo")) {

            double energia = 70 + Math.random() * 40; // 70 a 110

            Lobo lobo = new Lobo("Lobo", energia, 0);

            entidades.add(lobo);
        }
    }

    public void avanzarTurno() {
        procesarTurno();
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public int getTurnosTotales() {
        return turnosTotales;
    }

    public Clima getClimaActual() {
        return climaActual;
    }

    public void cambiarClima(Clima nuevoClima) {
        this.climaActual = nuevoClima;
    }

    public boolean esClima(String estado) {
        return climaActual.toString().equalsIgnoreCase(estado);
    }

    public void procesarTurno() {
        turnoActual++;

        int cantidadInicialTurno = entidades.size();

        for (int i = 0; i < cantidadInicialTurno; i++) {
            Entidad e = entidades.get(i);

            if (e.estaVivo()) {
                e.actuar(this);
            }
        }

        for (int i = entidades.size() - 1; i >= 0; i--) {
            Entidad e = entidades.get(i);

            if (!e.estaVivo()) {
                entidades.remove(i);
                totalMuertes++;
            }
        }
    }

    public boolean ecosistemaColapsado() {
        return getCantidadPlantas() == 0 || getCantidadConejos() == 0 || getCantidadLobos() == 0;
    }

    public int getCantidadPlantas() {
        int cantidad = 0;

        for (Entidad e : entidades) {
            if (e instanceof Planta && e.estaVivo()) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public int getCantidadConejos() {
        int cantidad = 0;

        for (Entidad e : entidades) {
            if (e instanceof Conejo && e.estaVivo()) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public int getCantidadConejosVivos() {
        return getCantidadConejos();
    }

    public int getCantidadLobos() {
        int cantidad = 0;

        for (Entidad e : entidades) {
            if (e instanceof Lobo && e.estaVivo()) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public Planta buscarPlantaDisponible() {
        for (Entidad e : entidades) {
            if (e instanceof Planta && e.estaVivo()) {
                return (Planta) e;
            }
        }

        return null;
    }

    public Conejo buscarConejoDisponible() {

        for (Entidad e : entidades) {
            if (e instanceof Conejo && e.estaVivo()) {
                return (Conejo) e;
            }
        }

        return null;
    }

    public void mostrarEstado() {
        System.out.println("Estado actual del ecosistema:");
        System.out.println("Plantas: " + getCantidadPlantas());
        System.out.println("Conejos: " + getCantidadConejos());
        System.out.println("Lobos: " + getCantidadLobos());
        System.out.println("Clima: " + climaActual);
    }

    public void mostrarEstadisticas() {
        generarReporteFinal();
    }

    public void generarReporteFinal() {
        System.out.println("\n--- REPORTE FINAL ---");
        System.out.println("Sobrevivientes: " + entidades.size());
        System.out.println("Muertes totales: " + totalMuertes);
        System.out.println("---------------------");
    }
}
