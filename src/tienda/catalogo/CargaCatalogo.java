package tienda.catalogo;

import tienda.model.Producto;
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

    // Carga por defecto
    public static List<Producto> cargarPorDefecto() {

        try (InputStream in = CargaCatalogo.class.getResourceAsStream(RUTA_RECURSO)) {
            if (in != null) {
                return parsear(in);
            }
        } catch (Exception ignore) { }

        Path[] candidatos = new Path[] {
                Path.of("src", "tienda", "catalogo", "tienda_catalogo.csv"),
                Path.of("RopaOnline", "src", "tienda", "catalogo", "tienda_catalogo.csv")
        };

        for (Path p : candidatos) {
            if (Files.exists(p)) {
                try (InputStream in = new FileInputStream(p.toFile())) {
                    return parsear(in);
                } catch (Exception ignore) { }
            }
        }

        return new ArrayList<>();
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
