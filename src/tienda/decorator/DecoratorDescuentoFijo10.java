package tienda.decorator;

// Decorador concreto: aplica -10% sobre el precio
public class DecoratorDescuentoFijo10 extends Decorator {

    public DecoratorDescuentoFijo10(Component componente) {
        super(componente);
    }

    @Override
    public void operacion(PrecioContexto ctx) {
        super.operacion(ctx);
        ctx.precio = ctx.precio * 0.90; // -10%
    }
}
