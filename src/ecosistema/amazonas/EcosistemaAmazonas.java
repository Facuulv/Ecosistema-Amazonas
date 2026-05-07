package ecosistema.amazonas;

import ecosistema.amazonas.consola.MenuConsola;
/**
 * Clase principal actualizada para el simulador de Ecosistema Amazonas.
 * Incluye lógica de turnos, simulación de clima y reporte final.
 * 
 * @author facu_
 */
public class EcosistemaAmazonas {

    public static void main(String[] args) {
        /*
        // 1. Inicialización del Ecosistema
        Ecosistema selva = new Ecosistema();

        // 2. Creación e incorporación de entidades
        // Creamos al Lobo Alfa (Nombre, Energía, Edad)
        Lobo loboAlfa = new Lobo("Lobo Alfa", 100, 2);
        selva.agregarEntidad(loboAlfa);

        // 3. Simulación de la lógica de turnos
        // Ejecutamos 5 turnos donde el clima y la caza afectarán a las entidades
        for (int i = 1; i <= 5; i++) {
            System.out.println("\n===== INICIO TURNO " + i + " =====");
            
            // Procesa el cambio de clima, envejecimiento, caza y muertes
            selva.avanzarTurno(); 
            
            // Mostramos el estado individual después de la acción del turno
            loboAlfa.mostrarEstado();
            System.out.println("================================");
        }

        // 4. Reporte Final
        // Muestra el resumen de sobrevivientes y muertes totales del sistema
        selva.generarReporteFinal();
        */
        MenuConsola menu = new MenuConsola();

        menu.iniciar();
    }
}