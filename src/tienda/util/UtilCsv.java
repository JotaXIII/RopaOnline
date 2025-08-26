package tienda.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class UtilCsv {

    private UtilCsv() { }

    // Lee un CSV simple (UTF-8)
    // Usa la primera línea como cabecera y devuelve una lista de mapas
    public static List<Map<String, String>> leerCsvComoMapas(InputStream in) {
        List<Map<String, String>> filas = new ArrayList<>();
        if (in == null) {
            return filas;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            // Lee cabecera
            String headerLine = br.readLine();
            if (headerLine == null) {
                return filas;
            }

            // Separa columnas por coma
            String[] cabeceras = headerLine.split(",", -1);

            if (cabeceras.length > 0 && cabeceras[0] != null) {
                cabeceras[0] = quitarBom(cabeceras[0]);
            }

            for (int i = 0; i < cabeceras.length; i++) {
                cabeceras[i] = trimSafe(cabeceras[i]);
            }


            String linea;
            while ((linea = br.readLine()) != null) {
                // Separa por coma manteniendo vacíos
                String[] valores = linea.split(",", -1);
                Map<String, String> mapa = new LinkedHashMap<>();
                for (int i = 0; i < cabeceras.length; i++) {
                    String key = cabeceras[i];
                    String val = (i < valores.length) ? trimSafe(valores[i]) : "";
                    mapa.put(key, val);
                }
                filas.add(mapa);
            }
        } catch (Exception e) {

        }
        return filas;
    }


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
