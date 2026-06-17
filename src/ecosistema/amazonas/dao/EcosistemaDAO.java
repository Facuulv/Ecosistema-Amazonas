/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecosistema.amazonas.dao;

import ecosistema.amazonas.modelo.Clima;
import ecosistema.amazonas.modelo.Conejo;
import ecosistema.amazonas.modelo.Ecosistema;
import ecosistema.amazonas.modelo.Lobo;
import ecosistema.amazonas.modelo.Planta;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EcosistemaDAO {

    private static final String CARPETA_DATA = "data";
    private static final String ARCHIVO_GUARDADO = "data/simulacion_guardada.txt";

    public void guardar(Ecosistema ecosistema) throws IOException {
        if (ecosistema == null) {
            throw new IOException("No hay simulación para guardar.");
        }

        crearCarpetaDataSiNoExiste();

        BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_GUARDADO));

        writer.write("========================================");
        writer.newLine();
        writer.write("SIMULACIÓN GUARDADA - ECOSISTEMA AMAZONAS");
        writer.newLine();
        writer.write("========================================");
        writer.newLine();
        writer.newLine();

        writer.write("[CONFIGURACION]");
        writer.newLine();

        writer.write("TURNO=" + ecosistema.getTurnoActual());
        writer.newLine();

        writer.write("TURNOS_TOTALES=" + ecosistema.getTurnosTotales());
        writer.newLine();

        writer.write("CLIMA=" + ecosistema.getClimaActual().name());
        writer.newLine();

        writer.write("FINALIZADA=" + ecosistema.getFinalizada());
        writer.newLine();

        writer.write("CAUSA_FINALIZACION=" + ecosistema.getCausaFinalizacion());
        writer.newLine();

        writer.newLine();

        writer.write("[RESUMEN_ESTADISTICO]");
        writer.newLine();

        writer.write("NAC_PLANTAS=" + ecosistema.getNacimientosPlantas());
        writer.newLine();

        writer.write("NAC_CONEJOS=" + ecosistema.getNacimientosConejos());
        writer.newLine();

        writer.write("MUE_PLANTAS=" + ecosistema.getMuertesPlantas());
        writer.newLine();

        writer.write("MUE_CONEJOS=" + ecosistema.getMuertesConejos());
        writer.newLine();

        writer.write("MUE_LOBOS=" + ecosistema.getMuertesLobos());
        writer.newLine();

        writer.write("TURNO_MAYOR_ACTIVIDAD=" + ecosistema.getTurnoMayorActividad());
        writer.newLine();

        writer.write("EVENTOS_MAYOR_ACTIVIDAD=" + ecosistema.getCantidadEventosMayorActividad());
        writer.newLine();

        writer.newLine();

        writer.write("[RESULTADO_FINAL]");
        writer.newLine();

        writer.write("PLANTAS_FINALES=" + contarPlantasVivas(ecosistema));
        writer.newLine();

        writer.write("CONEJOS_FINALES=" + contarConejosVivos(ecosistema));
        writer.newLine();

        writer.write("LOBOS_FINALES=" + contarLobosVivos(ecosistema));
        writer.newLine();

        writer.newLine();

        writer.write("[PLANTAS]");
        writer.newLine();

        for (Planta planta : ecosistema.getPlantas()) {
            writer.write(convertirPlantaATexto(planta));
            writer.newLine();
        }

        writer.write("[CONEJOS]");
        writer.newLine();

        for (Conejo conejo : ecosistema.getConejos()) {
            writer.write(convertirConejoATexto(conejo));
            writer.newLine();
        }

        writer.write("[LOBOS]");
        writer.newLine();

        for (Lobo lobo : ecosistema.getLobos()) {
            writer.write(convertirLoboATexto(lobo));
            writer.newLine();
        }

        writer.write("[EVENTOS_TOTALES]");
        writer.newLine();

        for (String evento : ecosistema.getEventosTotales()) {
            writer.write(escaparTexto(evento));
            writer.newLine();
        }

        writer.write("[HISTORIAL_PLANTAS]");
        writer.newLine();
        writer.write(convertirListaEnterosATexto(ecosistema.getHistorialPlantas()));
        writer.newLine();

        writer.write("[HISTORIAL_CONEJOS]");
        writer.newLine();
        writer.write(convertirListaEnterosATexto(ecosistema.getHistorialConejos()));
        writer.newLine();

        writer.write("[HISTORIAL_LOBOS]");
        writer.newLine();
        writer.write(convertirListaEnterosATexto(ecosistema.getHistorialLobos()));
        writer.newLine();

        writer.close();
    }

    public Ecosistema cargar() throws IOException {
        File archivo = new File(ARCHIVO_GUARDADO);

        if (!archivo.exists()) {
            throw new IOException("No existe una simulación guardada.");
        }

        BufferedReader reader = new BufferedReader(new FileReader(archivo));

        Ecosistema ecosistema = new Ecosistema();

        ecosistema.getPlantas().clear();
        ecosistema.getConejos().clear();
        ecosistema.getLobos().clear();
        ecosistema.getEventosTurno().clear();
        ecosistema.getEventosTotales().clear();
        ecosistema.getHistorialPlantas().clear();
        ecosistema.getHistorialConejos().clear();
        ecosistema.getHistorialLobos().clear();

        String seccionActual = "";
        String linea;

        while ((linea = reader.readLine()) != null) {
            linea = linea.trim();

            if (linea.isEmpty()) {
                continue;
            }

            if (linea.startsWith("[") && linea.endsWith("]")) {
                seccionActual = linea;
                continue;
            }

            if (seccionActual.isEmpty() || seccionActual.equals("[CONFIGURACION]") || seccionActual.equals("[RESUMEN_ESTADISTICO]")) {
                cargarDatoGeneral(ecosistema, linea);
            } else if (seccionActual.equals("[PLANTAS]")) {
                ecosistema.getPlantas().add(convertirTextoAPlanta(linea));
            } else if (seccionActual.equals("[CONEJOS]")) {
                ecosistema.getConejos().add(convertirTextoAConejo(linea));
            } else if (seccionActual.equals("[LOBOS]")) {
                ecosistema.getLobos().add(convertirTextoALobo(linea));
            } else if (seccionActual.equals("[EVENTOS_TOTALES]")) {
                ecosistema.getEventosTotales().add(desescaparTexto(linea));
            } else if (seccionActual.equals("[HISTORIAL_PLANTAS]")) {
                cargarHistorialPlantas(ecosistema, linea);
            } else if (seccionActual.equals("[HISTORIAL_CONEJOS]")) {
                cargarHistorialConejos(ecosistema, linea);
            } else if (seccionActual.equals("[HISTORIAL_LOBOS]")) {
                cargarHistorialLobos(ecosistema, linea);
            }
        }

        reader.close();

        ecosistema.getEventosTurno().clear();
        ecosistema.getEventosTurno().add("Simulación cargada correctamente desde archivo.");

        return ecosistema;
    }

    public boolean existeGuardado() {
        File archivo = new File(ARCHIVO_GUARDADO);
        return archivo.exists();
    }

    public void eliminarGuardado() {
        File archivo = new File(ARCHIVO_GUARDADO);

        if (archivo.exists()) {
            archivo.delete();
        }
    }

    private void crearCarpetaDataSiNoExiste() {
        File carpeta = new File(CARPETA_DATA);

        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }

    private int contarPlantasVivas(Ecosistema ecosistema) {
        int cantidad = 0;

        for (Planta planta : ecosistema.getPlantas()) {
            if (planta.getViva()) {
                cantidad++;
            }
        }

        return cantidad;
    }

    private int contarConejosVivos(Ecosistema ecosistema) {
        int cantidad = 0;

        for (Conejo conejo : ecosistema.getConejos()) {
            if (conejo.getViva()) {
                cantidad++;
            }
        }

        return cantidad;
    }

    private int contarLobosVivos(Ecosistema ecosistema) {
        int cantidad = 0;

        for (Lobo lobo : ecosistema.getLobos()) {
            if (lobo.getViva()) {
                cantidad++;
            }
        }

        return cantidad;
    }

    private String convertirPlantaATexto(Planta planta) {
        return planta.getNombre() + ";"
                + planta.getEdad() + ";"
                + formatearDecimal(planta.getEnergia()) + ";"
                + planta.getViva() + ";"
                + planta.getFila() + ";"
                + planta.getColumna() + ";"
                + planta.getTamanio();
    }

    private String convertirConejoATexto(Conejo conejo) {
        return conejo.getNombre() + ";"
                + conejo.getEdad() + ";"
                + formatearDecimal(conejo.getEnergia()) + ";"
                + conejo.getViva() + ";"
                + conejo.getFila() + ";"
                + conejo.getColumna() + ";"
                + conejo.getVelocidad() + ";"
                + formatearDecimal(conejo.getPeso());
    }

    private String convertirLoboATexto(Lobo lobo) {
        return lobo.getNombre() + ";"
                + lobo.getEdad() + ";"
                + formatearDecimal(lobo.getEnergia()) + ";"
                + lobo.getViva() + ";"
                + lobo.getFila() + ";"
                + lobo.getColumna() + ";"
                + lobo.getVelocidad() + ";"
                + formatearDecimal(lobo.getPeso()) + ";"
                + lobo.getExitosCaza();
    }

    private String formatearDecimal(double valor) {
        return String.format(java.util.Locale.US, "%.1f", valor);
    }

    private Planta convertirTextoAPlanta(String linea) throws IOException {
        String[] partes = linea.split(";");

        if (partes.length != 7) {
            throw new IOException("Formato inválido de planta: " + linea);
        }

        String nombre = partes[0];
        int edad = Integer.parseInt(partes[1]);
        double energia = Double.parseDouble(partes[2]);
        boolean viva = Boolean.parseBoolean(partes[3]);
        int fila = Integer.parseInt(partes[4]);
        int columna = Integer.parseInt(partes[5]);
        int tamanio = Integer.parseInt(partes[6]);

        return new Planta(nombre, edad, energia, viva, fila, columna, tamanio);
    }

    private Conejo convertirTextoAConejo(String linea) throws IOException {
        String[] partes = linea.split(";");

        if (partes.length != 8) {
            throw new IOException("Formato inválido de conejo: " + linea);
        }

        String nombre = partes[0];
        int edad = Integer.parseInt(partes[1]);
        double energia = Double.parseDouble(partes[2]);
        boolean viva = Boolean.parseBoolean(partes[3]);
        int fila = Integer.parseInt(partes[4]);
        int columna = Integer.parseInt(partes[5]);
        int velocidad = Integer.parseInt(partes[6]);
        double peso = Double.parseDouble(partes[7]);

        return new Conejo(nombre, edad, energia, viva, fila, columna, velocidad, peso);
    }

    private Lobo convertirTextoALobo(String linea) throws IOException {
        String[] partes = linea.split(";");

        if (partes.length != 9) {
            throw new IOException("Formato inválido de lobo: " + linea);
        }

        String nombre = partes[0];
        int edad = Integer.parseInt(partes[1]);
        double energia = Double.parseDouble(partes[2]);
        boolean viva = Boolean.parseBoolean(partes[3]);
        int fila = Integer.parseInt(partes[4]);
        int columna = Integer.parseInt(partes[5]);
        int velocidad = Integer.parseInt(partes[6]);
        double peso = Double.parseDouble(partes[7]);
        int exitosCaza = Integer.parseInt(partes[8]);

        return new Lobo(nombre, edad, energia, viva, fila, columna, velocidad, peso, exitosCaza);
    }

    private void cargarDatoGeneral(Ecosistema ecosistema, String linea) {
        String[] partes = linea.split("=", 2);

        if (partes.length != 2) {
            return;
        }

        String clave = partes[0];
        String valor = partes[1];

        switch (clave) {
            case "TURNO":
                ecosistema.setTurnoActual(Integer.parseInt(valor));
                break;

            case "TURNOS_TOTALES":
                ecosistema.setTurnosTotales(Integer.parseInt(valor));
                break;

            case "CLIMA":
                ecosistema.setClimaActual(Clima.desdeTexto(valor));
                break;

            case "NAC_PLANTAS":
                ecosistema.setNacimientosPlantas(Integer.parseInt(valor));
                break;

            case "NAC_CONEJOS":
                ecosistema.setNacimientosConejos(Integer.parseInt(valor));
                break;

            case "MUE_PLANTAS":
                ecosistema.setMuertesPlantas(Integer.parseInt(valor));
                break;

            case "MUE_CONEJOS":
                ecosistema.setMuertesConejos(Integer.parseInt(valor));
                break;

            case "MUE_LOBOS":
                ecosistema.setMuertesLobos(Integer.parseInt(valor));
                break;

            case "FINALIZADA":
                ecosistema.setFinalizada(Boolean.parseBoolean(valor));
                break;

            case "CAUSA_FINALIZACION":
                ecosistema.setCausaFinalizacion(valor);
                break;

            case "TURNO_MAYOR_ACTIVIDAD":
                ecosistema.setTurnoMayorActividad(Integer.parseInt(valor));
                break;

            case "EVENTOS_MAYOR_ACTIVIDAD":
                ecosistema.setCantidadEventosMayorActividad(Integer.parseInt(valor));
                break;
        }
    }

    private String convertirListaEnterosATexto(java.util.ArrayList<Integer> lista) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < lista.size(); i++) {
            sb.append(lista.get(i));

            if (i < lista.size() - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

    private void cargarHistorialPlantas(Ecosistema ecosistema, String linea) {
        ecosistema.getHistorialPlantas().clear();
        cargarListaEnteros(ecosistema.getHistorialPlantas(), linea);
    }

    private void cargarHistorialConejos(Ecosistema ecosistema, String linea) {
        ecosistema.getHistorialConejos().clear();
        cargarListaEnteros(ecosistema.getHistorialConejos(), linea);
    }

    private void cargarHistorialLobos(Ecosistema ecosistema, String linea) {
        ecosistema.getHistorialLobos().clear();
        cargarListaEnteros(ecosistema.getHistorialLobos(), linea);
    }

    private void cargarListaEnteros(java.util.ArrayList<Integer> lista, String linea) {
        if (linea == null || linea.trim().isEmpty()) {
            return;
        }

        String[] partes = linea.split(",");

        for (String parte : partes) {
            lista.add(Integer.parseInt(parte.trim()));
        }
    }

    private String escaparTexto(String texto) {
        if (texto == null) {
            return "";
        }

        return texto.replace(";", ",").replace("\n", " ");
    }

    private String desescaparTexto(String texto) {
        if (texto == null) {
            return "";
        }

        return texto;
    }
}
