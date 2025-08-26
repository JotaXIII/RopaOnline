package tienda.decorator;

// Clase abstracta del patrón Decorator

public abstract class Decorator implements Component {

    protected final Component componente;

    public Decorator(Component componente) {
        this.componente = componente;
    }

    @Override
    public void operacion(PrecioContexto ctx) {
        if (componente != null) {
            componente.operacion(ctx);
        }
    }
}
