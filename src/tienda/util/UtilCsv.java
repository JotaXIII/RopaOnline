package tienda.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class UtilCsv {

    private UtilCsv() { }

    // Lectura archivo CSV y muestra lista de productos
    public static List<Map<String, String>> leerCsvComoMapas(InputStream in) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {

            // cabecera con los nombres
            String cabecera = br.readLine();
            if (cabecera == null) return Collections.emptyList();

            String[] columnas = partir(cabecera);
            List<Map<String, String>> filas = new ArrayList<>();

            // Recorremos el resto de líneas y armamos cada fila como mapa
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;

                String[] valores = partir(linea);
                // LinkedHashMap para mantener el orden de las columnas
                Map<String, String> fila = new LinkedHashMap<>();

                for (int i = 0; i < columnas.length; i++) {
                    String key = columnas[i].trim();
                    String val = (i < valores.length) ? valores[i].trim() : "";
                    fila.put(key, val);
                }
                filas.add(fila);
            }

            return filas;

        } catch (Exception e) {

            throw new RuntimeException("Error leyendo CSV: " + e.getMessage(), e);
        }
    }

    private static String[] partir(String linea) {
        return linea.split(",", -1);
    }
}
