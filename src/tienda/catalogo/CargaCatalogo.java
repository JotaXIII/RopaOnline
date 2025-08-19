package tienda.catalogo;

import tienda.util.UtilCsv;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class CargaCatalogo {

    // Ruta del CSV
    private static final String RUTA_RECURSO = "/catalogo/tienda_catalogo.csv";

    private CargaCatalogo() { }

    // Se establecen 2 posibles rutas para la carga del CSV
    public static List<Producto> cargarPorDefecto() {
        // Primero intentamos leerlo desde la primera opción
        try (InputStream in = CargaCatalogo.class.getResourceAsStream(RUTA_RECURSO)) {
            if (in != null) {
                return parsear(in);
            }
        } catch (Exception ignored) {

        }

        // Opción 2
        String[] posibles = {
                "src/main/recursos/catalogo/tienda_catalogo.csv",

        };

        for (String ruta : posibles) {
            try {
                Path path = Path.of(ruta);
                if (Files.exists(path)) {
                    try (InputStream in = new FileInputStream(path.toFile())) {
                        return parsear(in);
                    }
                }
            } catch (Exception ignored) {
                // si una ruta falla, probamos la siguiente
            }
        }

        // Si no se encuentra el archivo en ningún lado
        throw new RuntimeException("Error cargando catálogo: no se encontró " + RUTA_RECURSO);
    }

    // Convierte el CSV en una lista de objetos Producto
    private static List<Producto> parsear(InputStream in) {
        // Leectura del CSV
        List<Map<String, String>> filas = UtilCsv.leerCsvComoMapas(in);

        // Lista final de productos
        List<Producto> lista = new ArrayList<>(filas.size());
        for (Map<String, String> f : filas) {
            lista.add(Producto.desdeMapa(f));
        }
        return lista;
    }
}
