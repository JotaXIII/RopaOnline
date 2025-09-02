package tienda.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class UtilCsv {

    private UtilCsv() { }

    // Lee un CSV simple (UTF-8)
    // Devuelve una lista de mapas
    public static List<Map<String, String>> leerCsvComoMapas(InputStream in) {
        List<Map<String, String>> filas = new ArrayList<>();
        if (in == null) return filas;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {

            // Lee cabecera
            String headerLine = br.readLine();
            if (headerLine == null) {
                return filas;
            }

            // Separa columnas por coma
            String[] cabeceras = headerLine.split(",", -1);

            // Limpia BOM si existe en la primera cabecera
            if (cabeceras.length > 0 && cabeceras[0] != null) {
                cabeceras[0] = quitarBom(cabeceras[0]);
            }

            // Normaliza cabeceras (trim + lower)
            for (int i = 0; i < cabeceras.length; i++) {
                String h = trimSafe(cabeceras[i]).toLowerCase(Locale.ROOT);
                cabeceras[i] = h;
            }

            // Lee filas
            String line;
            while ((line = br.readLine()) != null) {
                // ignora filas vacías
                if (line.trim().isEmpty()) continue;

                // separa por coma
                String[] celdas = line.split(",", -1);

                // ajusta tamaño
                if (celdas.length < cabeceras.length) {
                    String[] ajustadas = new String[cabeceras.length];
                    System.arraycopy(celdas, 0, ajustadas, 0, celdas.length);
                    for (int i = celdas.length; i < cabeceras.length; i++) ajustadas[i] = "";
                    celdas = ajustadas;
                }

                Map<String, String> fila = new LinkedHashMap<>();
                for (int i = 0; i < cabeceras.length; i++) {
                    String key = cabeceras[i];
                    String val = (i < celdas.length) ? trimSafe(celdas[i]) : "";
                    fila.put(key, val);
                }
                filas.add(fila);
            }
        } catch (Exception e) {

        }

        return filas;
    }

    // quita BOM UTF-8 si está presente
    private static String quitarBom(String s) {
        if (s != null && !s.isEmpty() && s.charAt(0) == '\uFEFF') {
            return s.substring(1);
        }
        return s;
    }

    // nulos y espacios
    private static String trimSafe(String v) {
        return v == null ? "" : v.trim();
    }
}
