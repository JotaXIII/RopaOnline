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

    // Carga tienda_catalogo.csv desde rutas conocidas
    public static List<Producto> cargarPorDefecto() {
        // Primer intento
        try (InputStream in = CargaCatalogo.class.getResourceAsStream(RUTA_RECURSO)) {
            if (in != null) {
                return parsear(in);
            }
        } catch (Exception e) {
            // sigue con rutas locales
        }

        // Luego intenta desde la carpeta del proyecto
        Path rutaLocal = Path.of("src", "catalogo", "tienda_catalogo.csv");
        if (Files.exists(rutaLocal)) {
            try (InputStream in = new FileInputStream(rutaLocal.toFile())) {
                return parsear(in);
            } catch (Exception e) {
                throw new RuntimeException("Error cargando catálogo desde ruta local: " + e.getMessage(), e);
            }
        }

        // Si no lo encuentra en ninguna ruta
        return List.of();
    }

    // Convierte el CSV en una lista de objetos Producto
    private static List<Producto> parsear(InputStream in) {
        // Lectura del CSV
        List<Map<String, String>> filas = UtilCsv.leerCsvComoMapas(in);

        // Lista final de productos
        List<Producto> lista = new ArrayList<>(filas.size());
        for (Map<String, String> f : filas) {
            lista.add(Producto.desdeMapa(f));
        }
        return lista;
    }
}
