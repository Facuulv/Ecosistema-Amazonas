/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecosistema.amazonas.consola;

import ecosistema.amazonas.sistema.Clima;
import ecosistema.amazonas.sistema.Ecosistema;
import java.util.Scanner;

/**
 *
 * @author facu_
 */
public class MenuConsola {

    private Scanner teclado;

    public MenuConsola() {
        teclado = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("=================================");
        System.out.println(" SIMULADOR DE ECOSISTEMA AMAZONAS ");
        System.out.println("=================================");

        int cantPlantas = pedirEntero("Ingresar la cantidad de plantas (5 a 30): ", 5, 30);
        int cantConejos = pedirEntero("Ingresar la cantidad de conejos (2 a 15): ", 2, 15);
        int cantLobos = pedirEntero("Ingresar la cantidad de lobos (1 a 5): ", 1, 5);
        Clima climaInicial = pedirClima();
        int turnosTotales = pedirEntero("Ingresar la cantidad de turnos totales (10 a 50): ", 10, 50);

        mostrarConfiguracion(cantPlantas, cantConejos, cantLobos, climaInicial, turnosTotales);

        if (confirmar("¿Desea iniciar la simulación? SI - NO: ")) {
            Ecosistema eco = new Ecosistema(climaInicial, turnosTotales);
            eco.inicializar(cantPlantas, cantConejos, cantLobos);

            ejecutarSimulacion(eco);
        } else {
            System.out.println("La simulación fue cancelada.");
        }
    }

    private void ejecutarSimulacion(Ecosistema eco) {
        while (eco.getTurnoActual() < eco.getTurnosTotales() && !eco.ecosistemaColapsado()) {

            System.out.println("");
            System.out.println(">>> Presionar ENTER para avanzar al siguiente turno...");
            teclado.nextLine();

            eco.procesarTurno();

            System.out.println("");
            System.out.println("=== TURNO " + eco.getTurnoActual() + " | Clima: " + eco.getClimaActual() + " ===");
            System.out.println("-- Estado del ecosistema --");
            eco.mostrarEstado();

            if (eco.getTurnoActual() % 3 == 0 && !eco.ecosistemaColapsado()) {
                mostrarIntervencion(eco);
            }
        }

        System.out.println("");
        System.out.println("=== FIN DE LA SIMULACIÓN ===");

        if (eco.ecosistemaColapsado()) {
            System.out.println("La simulación terminó porque el ecosistema colapsó.");
        } else {
            System.out.println("La simulación terminó porque se alcanzó la cantidad de turnos indicada.");
        }

        eco.generarReporteFinal();
        
        if (confirmar("¿Querés ver las estadísticas detalladas? SI - NO: ")) {
            eco.mostrarEstadisticas();
        }
    }
    
    private void mostrarIntervencion(Ecosistema eco) {
        int opcion;

        System.out.println("");
        System.out.println("=== INTERVENCIÓN DEL JUGADOR (cada 3 turnos) ===");
        System.out.println("Clima actual: " + eco.getClimaActual());
        System.out.println("1 -> Cambiar clima");
        System.out.println("2 -> Agregar una entidad");
        System.out.println("3 -> Solo avanzar");

        opcion = pedirEntero("Ingrese una opción: ", 1, 3);

        switch (opcion) {
            case 1:
                cambiarClimaDesdeMenu(eco);
                break;

            case 2:
                agregarEntidadDesdeMenu(eco);
                break;

            case 3:
                System.out.println("No se realizó ninguna intervención.");
                break;
        }
    }

    // Sirve para cambiar el clima depende la confirmacion del usuario
    private void cambiarClimaDesdeMenu(Ecosistema eco) {
        Clima nuevoClima;

        nuevoClima = pedirClima();

        if (confirmar("El clima se va a cambiar a " + nuevoClima + ". ¿Confirmar? SI - NO: ")) {
            eco.cambiarClima(nuevoClima);
            System.out.println("El clima se ha cambiado correctamente.");
        } else {
            System.out.println("El cambio de clima ha sido cancelado.");
        }
    }

    private void agregarEntidadDesdeMenu(Ecosistema eco) {
        int opcion;
        String tipo = "";

        System.out.println("");
        System.out.println("¿Qué entidad querés agregar?");
        System.out.println("1. Planta");
        System.out.println("2. Conejo");
        System.out.println("3. Lobo");

        opcion = pedirEntero("Ingrese una opción: ", 1, 3);

        switch (opcion) {
            case 1:
                tipo = "planta";
                break;

            case 2:
                tipo = "conejo";
                break;

            case 3:
                tipo = "lobo";
                break;
        }

        if (tipo.equals("lobo") && eco.getCantidadLobos() >= 5) {
            System.out.println("ERROR: No se pueden tener más de 5 lobos en el ecosistema.");
        } else {
            if (confirmar("¿Confirma agregar un/a " + tipo + "? SI - NO: ")) {
                eco.agregarEntidad(tipo);
                System.out.println("La entidad se ha agregado correctamente.");
            } else {
                System.out.println("Acción cancelada.");
            }
        }
    }

    private Clima pedirClima() {
        int opcion;

        System.out.println("");
        System.out.println("Seleccione el clima:");
        System.out.println("1. Soleado");
        System.out.println("2. Lluvioso");
        System.out.println("3. Sequía");
        System.out.println("4. Invierno");

        opcion = pedirEntero("Ingrese una opción: ", 1, 4);

        switch (opcion) {
            case 1:
                return Clima.SOLEADO;

            case 2:
                return Clima.LLUVIOSO;

            case 3:
                return Clima.SEQUIA;

            case 4:
                return Clima.INVIERNO;
        }

        return Clima.SOLEADO;
    }

    // Metodo que devuelve un entero, pidiendo minimo y maximo como parametros, y obviamente numero pedido. Metodo para reutilizar
    // Validaciones para el metodopara que no se rompa
    private int pedirEntero(String mensaje, int minimo, int maximo) {
        int numero;

        System.out.print(mensaje);
        numero = teclado.nextInt();

        while (numero < minimo || numero > maximo) {

            if (numero < minimo) {
                System.out.println("ERROR: el número es menor al mínimo (" + minimo + ").");
            } else {
                System.out.println("ERROR: el número es mayor al máximo (" + maximo + ").");
            }

            System.out.print(mensaje);
            numero = teclado.nextInt();
        }

        teclado.nextLine();

        return numero;
    }

    private boolean confirmar(String mensaje) {
        String respuesta;

        System.out.print(mensaje);
        respuesta = teclado.nextLine();

        if (respuesta.equals("SI") || respuesta.equals("si")) {
            return true;
        } else {
            return false;
        }
    }

    private void mostrarConfiguracion(int plantas, int conejos, int lobos, Clima clima, int turnos) {
        System.out.println("");
        System.out.println("=== CONFIGURACIÓN INICIAL ===");
        System.out.println("Plantas: " + plantas);
        System.out.println("Conejos: " + conejos);
        System.out.println("Lobos: " + lobos);
        System.out.println("Clima inicial: " + clima);
        System.out.println("Turnos totales: " + turnos);
        System.out.println("=============================");
    }
}
