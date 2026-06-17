/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecosistema.amazonas.controlador;

import ecosistema.amazonas.modelo.Clima;
import ecosistema.amazonas.modelo.Conejo;
import ecosistema.amazonas.modelo.Ecosistema;
import ecosistema.amazonas.modelo.Entidad;
import ecosistema.amazonas.modelo.Lobo;
import ecosistema.amazonas.modelo.Planta;
import ecosistema.amazonas.dao.EcosistemaDAO;
import ecosistema.amazonas.dao.HistorialDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ControladorEcosistema {

    private Ecosistema ecosistema;
    private Random random;
    private EcosistemaDAO ecosistemaDAO;
    private HistorialDAO historialDAO;

    private static final int FILAS_GRILLA = 10;
    private static final int COLUMNAS_GRILLA = 10;

    public ControladorEcosistema() {
        this.random = new Random();
        this.ecosistemaDAO = new EcosistemaDAO();
        this.historialDAO = new HistorialDAO();
    }

    public Ecosistema getEcosistema() {
        return ecosistema;
    }

    public boolean haySimulacionEnCurso() {
        return ecosistema != null && !ecosistema.getFinalizada();
    }

    public String iniciarNuevaSimulacion(int cantidadPlantas, int cantidadConejos, int cantidadLobos, Clima climaInicial, int turnosTotales) {
        String error = validarConfiguracionInicial(cantidadPlantas, cantidadConejos, cantidadLobos, climaInicial, turnosTotales);

        if (error != null) {
            return error;
        }

        ecosistema = new Ecosistema(climaInicial, turnosTotales);

        crearPlantasIniciales(cantidadPlantas);
        crearConejosIniciales(cantidadConejos);
        crearLobosIniciales(cantidadLobos);

        registrarConteosHistorial();
        agregarEvento("Simulación iniciada con clima " + climaInicial.getNombreVisible() + ".");

        return null;
    }

    private String validarConfiguracionInicial(int plantas, int conejos, int lobos, Clima clima, int turnos) {
        if (plantas < 5 || plantas > 30) {
            return "La cantidad de plantas debe estar entre 5 y 30.";
        }

        if (conejos < 2 || conejos > 15) {
            return "La cantidad de conejos debe estar entre 2 y 15.";
        }

        if (lobos < 1 || lobos > 5) {
            return "La cantidad de lobos debe estar entre 1 y 5.";
        }

        if (clima == null) {
            return "Debe seleccionar un clima inicial.";
        }

        if (turnos < 10 || turnos > 50) {
            return "La cantidad de turnos debe estar entre 10 y 50.";
        }

        return null;
    }

    // Metodo para Cargar Plantas 
    private void crearPlantasIniciales(int cantidad) {
        for (int i = 1; i <= cantidad; i++) {
            Planta planta = crearPlanta("Planta-" + i);

            if (planta != null) {
                ecosistema.getPlantas().add(planta);
            }
        }
    }

    private void crearConejosIniciales(int cantidad) {
        for (int i = 1; i <= cantidad; i++) {
            Conejo conejo = crearConejo("Conejo-" + i);

            if (conejo != null) {
                ecosistema.getConejos().add(conejo);
            }
        }
    }

    private void crearLobosIniciales(int cantidad) {
        for (int i = 1; i <= cantidad; i++) {
            Lobo lobo = crearLobo("Lobo-" + i);

            if (lobo != null) {
                ecosistema.getLobos().add(lobo);
            }
        }
    }

    private Planta crearPlanta(String nombre) {
        return crearPlanta(nombre, null);
    }

    private Planta crearPlanta(String nombre, List<? extends Entidad> reservas) {
        int[] posicion = obtenerPosicionLibre(reservas);

        if (posicion == null) {
            return null;
        }

        int edad = 0;
        double energia = numeroAleatorio(30, 60);
        boolean viva = true;
        int fila = posicion[0];
        int columna = posicion[1];
        int tamanio = random.nextInt(5) + 1;

        return new Planta(nombre, edad, energia, viva, fila, columna, tamanio);
    }

    // Metodo Para Cargar Conejos 
    private Conejo crearConejo(String nombre) {
        return crearConejo(nombre, null);
    }

    private Conejo crearConejo(String nombre, List<? extends Entidad> reservas) {
        int[] posicion = obtenerPosicionLibre(reservas);

        if (posicion == null) {
            return null;
        }

        int edad = 0;
        double energia = numeroAleatorio(50, 90);
        boolean viva = true;
        int fila = posicion[0];
        int columna = posicion[1];
        int velocidad = numeroAleatorioEntero(5, 12);
        double peso = numeroAleatorio(1, 4);

        return new Conejo(nombre, edad, energia, viva, fila, columna, velocidad, peso);
    }

    private Lobo crearLobo(String nombre) {
        int[] posicion = obtenerPosicionLibre();

        if (posicion == null) {
            return null;
        }

        int edad = 0;
        double energia = numeroAleatorio(80, 120);
        boolean viva = true;
        int fila = posicion[0];
        int columna = posicion[1];
        int velocidad = numeroAleatorioEntero(8, 16);
        double peso = numeroAleatorio(25, 45);
        int exitosCaza = 0;

        return new Lobo(nombre, edad, energia, viva, fila, columna, velocidad, peso, exitosCaza);
    }

    public String avanzarTurno() {
        if (ecosistema == null) {
            return "No hay simulación en curso.";
        }

        if (ecosistema.getFinalizada()) {
            return "La simulación ya finalizó.";
        }

        ecosistema.setTurnoActual(ecosistema.getTurnoActual() + 1);
        ecosistema.getEventosTurno().clear();

        agregarEvento("=== Turno " + ecosistema.getTurnoActual() + " ===");

        procesarReproduccionPlantas();
        procesarAlimentacionConejos();
        procesarReproduccionConejos();
        procesarCazaLobos();
        aplicarGastoPorEdad();
        verificarMuertesPorEnergia();
        registrarConteosHistorial();
        registrarTurnoMayorActividad();
        verificarFinSimulacion();

        return null;
    }

    private void procesarReproduccionPlantas() {
        if (ecosistema.getClimaActual() == Clima.INVIERNO) {
            agregarEvento("Las plantas no se reproducen por el invierno.");
            return;
        }

        ArrayList<Planta> nuevasPlantas = new ArrayList<>();

        for (Planta planta : ecosistema.getPlantas()) {
            if (planta.getViva() && planta.getEnergia() > 30) {
                double multiplicador = ecosistema.getClimaActual().getMultiplicadorReproduccionPlantas();

                if (random.nextDouble() < multiplicador / 2.0) {
                    Planta nueva = crearPlanta("Planta-" + (ecosistema.getPlantas().size() + nuevasPlantas.size() + 1), nuevasPlantas);

                    if (nueva == null) {
                        agregarEvento(planta.getNombre() + " intentó reproducirse, pero no hay espacio libre en la grilla.");
                        continue;
                    }

                    nuevasPlantas.add(nueva);

                    planta.setEnergia(planta.getEnergia() - 10);
                    ecosistema.setNacimientosPlantas(ecosistema.getNacimientosPlantas() + 1);

                    agregarEvento(planta.getNombre() + " se reprodujo. Nació " + nueva.getNombre() + ".");
                }
            }
        }

        ecosistema.getPlantas().addAll(nuevasPlantas);
    }

    private void procesarAlimentacionConejos() {
        for (Conejo conejo : ecosistema.getConejos()) {
            if (!conejo.getViva()) {
                continue;
            }

            Planta planta = buscarPlantaVivaAleatoria();

            if (planta != null) {
                double energiaGanada = planta.getTamanio() * 10.0;

                planta.setViva(false);
                planta.setEnergia(0);
                ecosistema.setMuertesPlantas(ecosistema.getMuertesPlantas() + 1);

                conejo.setEnergia(conejo.getEnergia() + energiaGanada);
                agregarEvento(conejo.getNombre() + " comió " + planta.getNombre() + " y ganó " + energiaGanada + " de energía.");
            } else {
                conejo.setEnergia(conejo.getEnergia() - 15);
                agregarEvento(conejo.getNombre() + " no encontró plantas y perdió 15 de energía.");
            }

            int energiaClima = ecosistema.getClimaActual().getEnergiaExtraConejo();

            if (energiaClima != 0) {
                conejo.setEnergia(conejo.getEnergia() + energiaClima);
                agregarEvento(conejo.getNombre() + " recibió efecto de clima sobre energía: " + energiaClima + ".");
            }
        }
    }

    private void procesarReproduccionConejos() {
        ArrayList<Conejo> nuevosConejos = new ArrayList<>();

        int conejosVivos = getCantidadConejosVivos();

        if (conejosVivos < 2) {
            return;
        }

        for (Conejo conejo : ecosistema.getConejos()) {
            if (conejo.getViva() && conejo.getEnergia() > 60) {
                if (random.nextDouble() < 0.35) {
                    Conejo nuevo = crearConejo("Conejo-" + (ecosistema.getConejos().size() + nuevosConejos.size() + 1), nuevosConejos);

                    if (nuevo == null) {
                        agregarEvento(conejo.getNombre() + " intentó reproducirse, pero no hay espacio libre en la grilla.");
                        continue;
                    }

                    nuevosConejos.add(nuevo);

                    conejo.setEnergia(conejo.getEnergia() - 20);
                    ecosistema.setNacimientosConejos(ecosistema.getNacimientosConejos() + 1);

                    agregarEvento(conejo.getNombre() + " se reprodujo. Nació " + nuevo.getNombre() + ".");
                }
            }
        }

        ecosistema.getConejos().addAll(nuevosConejos);
    }

    private void procesarCazaLobos() {
        for (Lobo lobo : ecosistema.getLobos()) {
            if (!lobo.getViva()) {
                continue;
            }

            Conejo presa = buscarConejoVivoAleatorio();

            if (presa == null) {
                lobo.setEnergia(lobo.getEnergia() - 15);
                agregarEvento(lobo.getNombre() + " no encontró conejos y perdió 15 de energía.");
            } else {
                double probabilidad = lobo.getEnergia() / 120.0;
                probabilidad += ecosistema.getClimaActual().getBonusCazaLobo();

                if (probabilidad > 0.70) {
                    probabilidad = 0.70;
                }

                boolean cazaExitosa = random.nextDouble() < probabilidad;

                if (cazaExitosa) {
                    presa.setViva(false);
                    presa.setEnergia(0);
                    ecosistema.setMuertesConejos(ecosistema.getMuertesConejos() + 1);

                    lobo.setEnergia(lobo.getEnergia() + 30);
                    lobo.setExitosCaza(lobo.getExitosCaza() + 1);

                    agregarEvento(lobo.getNombre() + " cazó a " + presa.getNombre() + ". Cacerías: " + lobo.getExitosCaza() + ".");
                } else {
                    lobo.setEnergia(lobo.getEnergia() - 10);
                    agregarEvento(lobo.getNombre() + " falló la caza y perdió 10 de energía.");
                }
            }

            int energiaClima = ecosistema.getClimaActual().getEnergiaExtraLobo();

            if (energiaClima != 0) {
                lobo.setEnergia(lobo.getEnergia() + energiaClima);
                agregarEvento(lobo.getNombre() + " recibió efecto de clima sobre energía: " + energiaClima + ".");
            }
        }
    }

    private void aplicarGastoPorEdad() {
        for (Planta planta : ecosistema.getPlantas()) {
            if (planta.getViva()) {
                planta.setEdad(planta.getEdad() + 1);
                planta.setEnergia(planta.getEnergia() - 2);
            }
        }

        for (Conejo conejo : ecosistema.getConejos()) {
            if (conejo.getViva()) {
                conejo.setEdad(conejo.getEdad() + 1);
                conejo.setEnergia(conejo.getEnergia() - 2);
            }
        }

        for (Lobo lobo : ecosistema.getLobos()) {
            if (lobo.getViva()) {
                lobo.setEdad(lobo.getEdad() + 1);
                lobo.setEnergia(lobo.getEnergia() - 2);
            }
        }
    }

    private void verificarMuertesPorEnergia() {
        for (Planta planta : ecosistema.getPlantas()) {
            if (planta.getViva() && planta.getEnergia() <= 0) {
                planta.setViva(false);
                ecosistema.setMuertesPlantas(ecosistema.getMuertesPlantas() + 1);
                agregarEvento(planta.getNombre() + " murió por quedarse sin energía.");
            }
        }

        for (Conejo conejo : ecosistema.getConejos()) {
            if (conejo.getViva() && conejo.getEnergia() <= 0) {
                conejo.setViva(false);
                ecosistema.setMuertesConejos(ecosistema.getMuertesConejos() + 1);
                agregarEvento(conejo.getNombre() + " murió por quedarse sin energía.");
            }
        }

        for (Lobo lobo : ecosistema.getLobos()) {
            if (lobo.getViva() && lobo.getEnergia() <= 0) {
                lobo.setViva(false);
                ecosistema.setMuertesLobos(ecosistema.getMuertesLobos() + 1);
                agregarEvento(lobo.getNombre() + " murió por quedarse sin energía.");
            }
        }
    }
    
    //Metodo de Cambio de cambio de clima , el clima si es vacio devuelve un mensaje de error 
    public String cambiarClima(Clima nuevoClima) {
        if (ecosistema == null) {
            return "No hay simulación en curso.";
        }

        if (nuevoClima == null) {
            return "El clima seleccionado no es válido.";
        }

        ecosistema.setClimaActual(nuevoClima);
        agregarEvento("El clima cambió a " + nuevoClima.getNombreVisible() + ".");

        return null;
    }

    public String agregarEntidad(String tipo) {
        if (ecosistema == null) {
            return "No hay simulación en curso.";
        }

        if (tipo == null) {
            return "Debe seleccionar una entidad.";
        }

        String tipoNormalizado = tipo.trim().toLowerCase();

        switch (tipoNormalizado) {
            case "planta":
                Planta planta = crearPlanta("Planta-" + (ecosistema.getPlantas().size() + 1));

                if (planta == null) {
                    return "No hay posiciones libres en la grilla para agregar una planta.";
                }

                ecosistema.getPlantas().add(planta);
                agregarEvento("Se agregó " + planta.getNombre() + " al ecosistema.");
                break;

            case "conejo":
                Conejo conejo = crearConejo("Conejo-" + (ecosistema.getConejos().size() + 1));

                if (conejo == null) {
                    return "No hay posiciones libres en la grilla para agregar un conejo.";
                }

                ecosistema.getConejos().add(conejo);
                agregarEvento("Se agregó " + conejo.getNombre() + " al ecosistema.");
                break;

            case "lobo":
                if (getCantidadLobosVivos() >= 5) {
                    return "No se pueden tener más de 5 lobos vivos en el ecosistema.";
                }

                Lobo lobo = crearLobo("Lobo-" + (ecosistema.getLobos().size() + 1));

                if (lobo == null) {
                    return "No hay posiciones libres en la grilla para agregar un lobo.";
                }

                ecosistema.getLobos().add(lobo);
                agregarEvento("Se agregó " + lobo.getNombre() + " al ecosistema.");
                break;

            default:
                return "Tipo de entidad no válido.";
        }

        verificarFinSimulacion();
        return null;
    }

    private Planta buscarPlantaVivaAleatoria() {
        ArrayList<Planta> vivas = new ArrayList<>();

        for (Planta planta : ecosistema.getPlantas()) {
            if (planta.getViva()) {
                vivas.add(planta);
            }
        }

        if (vivas.isEmpty()) {
            return null;
        }

        return vivas.get(random.nextInt(vivas.size()));
    }

    private Conejo buscarConejoVivoAleatorio() {
        ArrayList<Conejo> vivos = new ArrayList<>();

        for (Conejo conejo : ecosistema.getConejos()) {
            if (conejo.getViva()) {
                vivos.add(conejo);
            }
        }

        if (vivos.isEmpty()) {
            return null;
        }

        return vivos.get(random.nextInt(vivos.size()));
    }

    private void verificarFinSimulacion() {
        if (ecosistema == null) {
            return;
        }

        if (ecosistema.getFinalizada()) {
            return;
        }

        if (getCantidadPlantasVivas() == 0) {
            ecosistema.setFinalizada(true);
            ecosistema.setCausaFinalizacion("Colapso del ecosistema: se extinguieron las plantas.");
            agregarEvento(ecosistema.getCausaFinalizacion());
            guardarPartidaEnHistorial();
            return;
        }

        if (getCantidadConejosVivos() == 0) {
            ecosistema.setFinalizada(true);
            ecosistema.setCausaFinalizacion("Colapso del ecosistema: se extinguieron los conejos.");
            agregarEvento(ecosistema.getCausaFinalizacion());
            guardarPartidaEnHistorial();
            return;
        }

        if (getCantidadLobosVivos() == 0) {
            ecosistema.setFinalizada(true);
            ecosistema.setCausaFinalizacion("Colapso del ecosistema: se extinguieron los lobos.");
            agregarEvento(ecosistema.getCausaFinalizacion());
            guardarPartidaEnHistorial();
            return;
        }

        if (ecosistema.getTurnoActual() >= ecosistema.getTurnosTotales()) {
            ecosistema.setFinalizada(true);
            ecosistema.setCausaFinalizacion("Se alcanzó la cantidad total de turnos configurada.");
            agregarEvento(ecosistema.getCausaFinalizacion());
            guardarPartidaEnHistorial();
        }
    }

    private void registrarConteosHistorial() {
        ecosistema.getHistorialPlantas().add(getCantidadPlantasVivas());
        ecosistema.getHistorialConejos().add(getCantidadConejosVivos());
        ecosistema.getHistorialLobos().add(getCantidadLobosVivos());
    }

    private void registrarTurnoMayorActividad() {
        int cantidadEventos = ecosistema.getEventosTurno().size();

        if (cantidadEventos > ecosistema.getCantidadEventosMayorActividad()) {
            ecosistema.setCantidadEventosMayorActividad(cantidadEventos);
            ecosistema.setTurnoMayorActividad(ecosistema.getTurnoActual());
        }
    }

    private void agregarEvento(String evento) {
        if (ecosistema == null || evento == null) {
            return;
        }

        ecosistema.getEventosTurno().add(evento);
        ecosistema.getEventosTotales().add("Turno " + ecosistema.getTurnoActual() + ": " + evento);
    }

    public int getCantidadPlantasVivas() {
        if (ecosistema == null) {
            return 0;
        }

        int cantidad = 0;

        for (Planta planta : ecosistema.getPlantas()) {
            if (planta.getViva()) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public int getCantidadConejosVivos() {
        if (ecosistema == null) {
            return 0;
        }

        int cantidad = 0;

        for (Conejo conejo : ecosistema.getConejos()) {
            if (conejo.getViva()) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public int getCantidadLobosVivos() {
        if (ecosistema == null) {
            return 0;
        }

        int cantidad = 0;

        for (Lobo lobo : ecosistema.getLobos()) {
            if (lobo.getViva()) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public List<String> getEventosTurno() {
        if (ecosistema == null) {
            return new ArrayList<>();
        }

        return ecosistema.getEventosTurno();
    }

    public List<String> getEventosTotales() {
        if (ecosistema == null) {
            return new ArrayList<>();
        }

        return ecosistema.getEventosTotales();
    }

    public Object[][] obtenerDatosTablaEntidades() {
        if (ecosistema == null) {
            return new Object[0][0];
        }

        List<Entidad> entidades = ecosistema.getEntidadesVivas();
        Object[][] datos = new Object[entidades.size()][7];

        for (int i = 0; i < entidades.size(); i++) {
            Entidad entidad = entidades.get(i);

            datos[i][0] = obtenerTipoEntidad(entidad);
            datos[i][1] = entidad.getNombre();
            datos[i][2] = String.format("%.1f", entidad.getEnergia());
            datos[i][3] = entidad.getEdad();
            datos[i][4] = entidad.getFila();
            datos[i][5] = entidad.getColumna();
            datos[i][6] = obtenerEstadoEntidad(entidad);
        }

        return datos;
    }

    public String[][] obtenerGrillaEcosistema() {
        String[][] grilla = new String[FILAS_GRILLA][COLUMNAS_GRILLA];

        for (int fila = 0; fila < FILAS_GRILLA; fila++) {
            for (int columna = 0; columna < COLUMNAS_GRILLA; columna++) {
                grilla[fila][columna] = "";
            }
        }

        if (ecosistema == null) {
            return grilla;
        }

        for (Planta planta : ecosistema.getPlantas()) {
            if (planta.getViva()) {
                grilla[planta.getFila()][planta.getColumna()] = "P";
            }
        }

        for (Conejo conejo : ecosistema.getConejos()) {
            if (conejo.getViva()) {
                grilla[conejo.getFila()][conejo.getColumna()] = "C";
            }
        }

        for (Lobo lobo : ecosistema.getLobos()) {
            if (lobo.getViva()) {
                grilla[lobo.getFila()][lobo.getColumna()] = "L";
            }
        }

        return grilla;
    }

    private String obtenerTipoEntidad(Entidad entidad) {
        if (entidad instanceof Planta) {
            return "Planta";
        }

        if (entidad instanceof Conejo) {
            return "Conejo";
        }

        if (entidad instanceof Lobo) {
            return "Lobo";
        }

        return "Entidad";
    }

    private String obtenerEstadoEntidad(Entidad entidad) {
        if (!entidad.getViva()) {
            return "Muerta";
        }

        if (entidad instanceof Conejo && entidad.getEnergia() < 20) {
            return "En peligro";
        }

        if (entidad instanceof Lobo) {
            Lobo lobo = (Lobo) entidad;
            return "Cacerías: " + lobo.getExitosCaza();
        }

        return "Viva";
    }

    public String generarReporteFinal() {
        if (ecosistema == null) {
            return "No hay simulación en curso.";
        }

        StringBuilder reporte = new StringBuilder();

        reporte.append("REPORTE FINAL\n");
        reporte.append("============================\n\n");

        reporte.append("Causa de finalización:\n");
        reporte.append(ecosistema.getCausaFinalizacion().isEmpty() ? "La simulación aún no finalizó." : ecosistema.getCausaFinalizacion()).append("\n\n");

        reporte.append("Turnos jugados: ").append(ecosistema.getTurnoActual()).append(" de ").append(ecosistema.getTurnosTotales()).append("\n\n");

        reporte.append("Sobrevivientes:\n");
        reporte.append("Plantas: ").append(getCantidadPlantasVivas()).append("\n");
        reporte.append("Conejos: ").append(getCantidadConejosVivos()).append("\n");
        reporte.append("Lobos: ").append(getCantidadLobosVivos()).append("\n\n");

        reporte.append("Nacimientos:\n");
        reporte.append("Plantas: ").append(ecosistema.getNacimientosPlantas()).append("\n");
        reporte.append("Conejos: ").append(ecosistema.getNacimientosConejos()).append("\n\n");

        reporte.append("Muertes:\n");
        reporte.append("Plantas: ").append(ecosistema.getMuertesPlantas()).append("\n");
        reporte.append("Conejos: ").append(ecosistema.getMuertesConejos()).append("\n");
        reporte.append("Lobos: ").append(ecosistema.getMuertesLobos()).append("\n\n");

        reporte.append("Turno de mayor actividad:\n");
        reporte.append("Turno ").append(ecosistema.getTurnoMayorActividad());
        reporte.append(" con ").append(ecosistema.getCantidadEventosMayorActividad()).append(" eventos.\n\n");

        reporte.append("Entidad más longeva por tipo:\n");
        reporte.append(obtenerLongevos()).append("\n");

        reporte.append("Lobo con más cacerías:\n");
        reporte.append(obtenerLoboConMasCacerias()).append("\n");

        return reporte.toString();
    }

    private String obtenerLongevos() {
        Planta plantaLongeva = null;
        Conejo conejoLongevo = null;
        Lobo loboLongevo = null;

        for (Planta planta : ecosistema.getPlantas()) {
            if (plantaLongeva == null || planta.getEdad() > plantaLongeva.getEdad()) {
                plantaLongeva = planta;
            }
        }

        for (Conejo conejo : ecosistema.getConejos()) {
            if (conejoLongevo == null || conejo.getEdad() > conejoLongevo.getEdad()) {
                conejoLongevo = conejo;
            }
        }

        for (Lobo lobo : ecosistema.getLobos()) {
            if (loboLongevo == null || lobo.getEdad() > loboLongevo.getEdad()) {
                loboLongevo = lobo;
            }
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Planta: ");
        sb.append(plantaLongeva != null ? plantaLongeva.getNombre() + " - edad " + plantaLongeva.getEdad() : "Sin datos");
        sb.append("\n");

        sb.append("Conejo: ");
        sb.append(conejoLongevo != null ? conejoLongevo.getNombre() + " - edad " + conejoLongevo.getEdad() : "Sin datos");
        sb.append("\n");

        sb.append("Lobo: ");
        sb.append(loboLongevo != null ? loboLongevo.getNombre() + " - edad " + loboLongevo.getEdad() : "Sin datos");
        sb.append("\n");

        return sb.toString();
    }

    private String obtenerLoboConMasCacerias() {
        Lobo mejorLobo = null;

        for (Lobo lobo : ecosistema.getLobos()) {
            if (mejorLobo == null || lobo.getExitosCaza() > mejorLobo.getExitosCaza()) {
                mejorLobo = lobo;
            }
        }

        if (mejorLobo == null) {
            return "Sin lobos registrados.";
        }

        return mejorLobo.getNombre() + " con " + mejorLobo.getExitosCaza() + " cacerías exitosas.";
    }

    public boolean requiereIntervencion() {
        if (ecosistema == null) {
            return false;
        }

        return ecosistema.getTurnoActual() > 0
                && ecosistema.getTurnoActual() % 3 == 0
                && !ecosistema.getFinalizada();
    }

    public Clima getClimaActual() {
        if (ecosistema == null) {
            return Clima.SOLEADO;
        }

        return ecosistema.getClimaActual();
    }

    public int getTurnoActual() {
        if (ecosistema == null) {
            return 0;
        }

        return ecosistema.getTurnoActual();
    }

    public int getTurnosTotales() {
        if (ecosistema == null) {
            return 0;
        }

        return ecosistema.getTurnosTotales();
    }

    public boolean simulacionFinalizada() {
        return ecosistema != null && ecosistema.getFinalizada();
    }

    public String getCausaFinalizacion() {
        if (ecosistema == null) {
            return "";
        }

        return ecosistema.getCausaFinalizacion();
    }

    private double numeroAleatorio(int minimo, int maximo) {
        return minimo + (maximo - minimo) * random.nextDouble();
    }

    private int numeroAleatorioEntero(int minimo, int maximo) {
        return minimo + random.nextInt(maximo - minimo + 1);
    }

    public String guardarSimulacion() {
        if (ecosistema == null) {
            return "No hay simulación para guardar.";
        }

        try {
            ecosistemaDAO.guardar(ecosistema);
            return null;
        } catch (IOException e) {
            return "Error al guardar la simulación: " + e.getMessage();
        }
    }

    public String cargarSimulacion() {
        try {
            this.ecosistema = ecosistemaDAO.cargar();
            return null;
        } catch (IOException e) {
            return "Error al cargar la simulación: " + e.getMessage();
        }
    }

    public boolean existeSimulacionGuardada() {
        return ecosistemaDAO.existeGuardado();
    }

    private String generarResumenHistorial() {
        if (ecosistema == null) {
            return "";
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        StringBuilder sb = new StringBuilder();

        sb.append("========================================\n");
        sb.append("Fecha: ").append(LocalDateTime.now().format(formato)).append("\n");
        sb.append("Turnos: ").append(getTurnoActual()).append(" / ").append(getTurnosTotales()).append("\n");
        sb.append("Clima final: ").append(getClimaActual().getNombreVisible()).append("\n");

        if (getCausaFinalizacion() != null && !getCausaFinalizacion().trim().isEmpty()) {
            sb.append("Causa: ").append(getCausaFinalizacion()).append("\n");
        } else {
            sb.append("Causa: Simulación guardada manualmente.\n");
        }

        sb.append("Plantas vivas: ").append(getCantidadPlantasVivas()).append("\n");
        sb.append("Conejos vivos: ").append(getCantidadConejosVivos()).append("\n");
        sb.append("Lobos vivos: ").append(getCantidadLobosVivos()).append("\n");

        sb.append("Nacimientos plantas: ").append(ecosistema.getNacimientosPlantas()).append("\n");
        sb.append("Nacimientos conejos: ").append(ecosistema.getNacimientosConejos()).append("\n");

        sb.append("Muertes plantas: ").append(ecosistema.getMuertesPlantas()).append("\n");
        sb.append("Muertes conejos: ").append(ecosistema.getMuertesConejos()).append("\n");
        sb.append("Muertes lobos: ").append(ecosistema.getMuertesLobos()).append("\n");

        sb.append("Turno de mayor actividad: ").append(ecosistema.getTurnoMayorActividad()).append("\n");
        sb.append("Cantidad de eventos en ese turno: ").append(ecosistema.getCantidadEventosMayorActividad()).append("\n");
        sb.append("========================================");

        return sb.toString();
    }

    public String guardarPartidaEnHistorial() {
        if (ecosistema == null) {
            return "No hay simulación para guardar en el historial.";
        }

        try {
            String resumen = generarResumenHistorial();
            historialDAO.guardarResumenPartida(resumen);
            return null;
        } catch (IOException e) {
            return "Error al guardar el historial: " + e.getMessage();
        }
    }

    public String cargarHistorialPartidas() {
        try {
            return historialDAO.cargarHistorial();
        } catch (IOException e) {
            return "Error al cargar el historial: " + e.getMessage();
        }
    }

    private boolean posicionOcupada(int fila, int columna) {
        if (ecosistema == null) {
            return false;
        }

        for (Planta planta : ecosistema.getPlantas()) {
            if (planta.getViva() && planta.getFila() == fila && planta.getColumna() == columna) {
                return true;
            }
        }

        for (Conejo conejo : ecosistema.getConejos()) {
            if (conejo.getViva() && conejo.getFila() == fila && conejo.getColumna() == columna) {
                return true;
            }
        }

        for (Lobo lobo : ecosistema.getLobos()) {
            if (lobo.getViva() && lobo.getFila() == fila && lobo.getColumna() == columna) {
                return true;
            }
        }

        return false;
    }

    private int[] obtenerPosicionLibre() {
        return obtenerPosicionLibre(null);
    }

    private int[] obtenerPosicionLibre(List<? extends Entidad> reservas) {
        int intentosMaximos = FILAS_GRILLA * COLUMNAS_GRILLA;

        for (int i = 0; i < intentosMaximos; i++) {
            int fila = random.nextInt(FILAS_GRILLA);
            int columna = random.nextInt(COLUMNAS_GRILLA);

            if (!posicionOcupada(fila, columna) && !posicionReservada(fila, columna, reservas)) {
                return new int[]{fila, columna};
            }
        }

        return null;
    }

    private boolean posicionReservada(int fila, int columna, List<? extends Entidad> reservas) {
        if (reservas == null) {
            return false;
        }

        for (Entidad entidad : reservas) {
            if (entidad != null && entidad.getViva()
                    && entidad.getFila() == fila
                    && entidad.getColumna() == columna) {
                return true;
            }
        }

        return false;
    }
}
