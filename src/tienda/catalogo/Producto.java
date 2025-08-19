package tienda.catalogo;

import java.util.Map;
import java.util.Objects;

public class Producto {

    // Datos del CSV
    private final String sku;
    private final String nombre;
    private final String categoria;
    private final String subcategoria;
    private final String grupoEdad;
    private final String talla;
    private final String color;
    private final String temporada;
    private final double precio;

    // Constructor directo
    public Producto(String sku, String nombre, String categoria, String subcategoria,
                    String grupoEdad, String talla, String color, String temporada, double precio) {
        this.sku = Objects.requireNonNull(sku);
        this.nombre = Objects.requireNonNull(nombre);
        this.categoria = Objects.requireNonNull(categoria);
        this.subcategoria = Objects.requireNonNull(subcategoria);
        this.grupoEdad = Objects.requireNonNull(grupoEdad);
        this.talla = Objects.requireNonNull(talla);
        this.color = Objects.requireNonNull(color);
        this.temporada = Objects.requireNonNull(temporada);
        this.precio = precio;
    }

    // Crea un Producto (CSV)
    public static Producto desdeMapa(Map<String, String> fila) {
        String sku = fila.getOrDefault("sku", "").trim();
        String nombre = fila.getOrDefault("nombre", "").trim();
        String categoria = fila.getOrDefault("categoria", "").trim();
        String subcategoria = fila.getOrDefault("subcategoria", "").trim();
        String grupoEdad = fila.getOrDefault("grupo_edad", "").trim();
        String talla = fila.getOrDefault("talla", "").trim();
        String color = fila.getOrDefault("color", "").trim();
        String temporada = fila.getOrDefault("temporada", "").trim();
        String precioStr = fila.getOrDefault("precio", "0").trim();
        double precio = Double.parseDouble(precioStr); // acá confiamos en que viene número

        return new Producto(sku, nombre, categoria, subcategoria, grupoEdad, talla, color, temporada, precio);
    }


    // Getters
    public String getSku() { return sku; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public String getSubcategoria() { return subcategoria; }
    public String getGrupoEdad() { return grupoEdad; }
    public String getTalla() { return talla; }
    public String getColor() { return color; }
    public String getTemporada() { return temporada; }
    public double getPrecio() { return precio; }

    @Override
    public String toString() {
        return sku + " - " + nombre + " (" + categoria + "/" + subcategoria + ") $" + precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;
        Producto that = (Producto) o;
        return sku.equals(that.sku);
    }

    @Override
    public int hashCode() {
        return sku.hashCode();
    }
}
