package tienda.decorator;

// Descuento fijo del 10% sobre el precio actual
public class DecoratorDescuentoFijo10 extends Decorator {

    public DecoratorDescuentoFijo10(Component componente) {
        super(componente);
    }

    @Override
    public void operacion(PrecioContexto ctx) {
        if (ctx == null) return;

        super.operacion(ctx);

        ctx.precio = ctx.precio * 0.90;
    }
}
