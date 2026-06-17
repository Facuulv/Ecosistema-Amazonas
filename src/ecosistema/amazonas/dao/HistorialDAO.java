/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecosistema.amazonas.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HistorialDAO {

    private static final String CARPETA_DATA = "data";
    private static final String ARCHIVO_HISTORIAL = "data/historial_partidas.txt";

    // guarda el historial en un .txt
    public void guardarResumenPartida(String resumen) throws IOException {
        if (resumen == null || resumen.trim().isEmpty()) {
            throw new IOException("No hay resumen para guardar.");
        }

        crearCarpetaDataSiNoExiste();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_HISTORIAL, true))) {
            writer.write(resumen);
            writer.newLine();
            writer.newLine();
        }
    }

    public String cargarHistorial() throws IOException {
        File archivo = new File(ARCHIVO_HISTORIAL);

        if (!archivo.exists()) {
            return "Todavía no hay partidas guardadas en el historial.";
        }

        StringBuilder contenido = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        }

        if (contenido.toString().trim().isEmpty()) {
            return "Todavía no hay partidas guardadas en el historial.";
        }

        return contenido.toString();
    }

    private void crearCarpetaDataSiNoExiste() {
        File carpeta = new File(CARPETA_DATA);

        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }
}