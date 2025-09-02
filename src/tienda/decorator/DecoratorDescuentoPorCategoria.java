package tienda.decorator;

import java.util.Locale;

// Descuento por categoría: aplica porcentaje si la categoría coincide
public class DecoratorDescuentoPorCategoria extends Decorator {

    private final String categoriaObjetivo;
    private final double porcentaje;

    public DecoratorDescuentoPorCategoria(Component componente, String categoriaObjetivo, double porcentaje) {
        super(componente);
        this.categoriaObjetivo = categoriaObjetivo == null ? "" : categoriaObjetivo.trim().toLowerCase(Locale.ROOT);
        double pct = Double.isNaN(porcentaje) ? 0.0 : porcentaje;
        if (pct < 0.0) pct = 0.0;
        this.porcentaje = pct;
    }

    @Override
    public void operacion(PrecioContexto ctx) {
        if (ctx == null) return;


        super.operacion(ctx);

        // categoría del producto
        String cat = ctx.producto == null || ctx.producto.getCategoria() == null
                ? ""
                : ctx.producto.getCategoria().trim().toLowerCase(Locale.ROOT);

        // si coincide, aplica el porcentaje
        if (!categoriaObjetivo.isEmpty() && categoriaObjetivo.equals(cat) && porcentaje > 0.0) {
            ctx.precio = ctx.precio * (1.0 - porcentaje);
        }
    }
}
