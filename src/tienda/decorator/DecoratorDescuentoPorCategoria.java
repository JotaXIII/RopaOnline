package tienda.decorator;

import java.util.Locale;

// Decorador concreto: si la categoría coincide, aplica dscto
public class DecoratorDescuentoPorCategoria extends Decorator {

    private final String categoriaObjetivo;
    private final double porcentaje;

    public DecoratorDescuentoPorCategoria(Component componente, String categoriaObjetivo, double porcentaje) {
        super(componente);
        this.categoriaObjetivo = categoriaObjetivo == null ? "" : categoriaObjetivo.trim().toLowerCase(Locale.ROOT);
        this.porcentaje = porcentaje;
    }

    @Override
    public void operacion(PrecioContexto ctx) {
        super.operacion(ctx);
        String cat = ctx.producto.getCategoria() == null ? "" : ctx.producto.getCategoria().trim().toLowerCase(Locale.ROOT);
        if (!categoriaObjetivo.isEmpty() && categoriaObjetivo.equals(cat)) {
            ctx.precio = ctx.precio * (1.0 - porcentaje);
        }
    }
}
