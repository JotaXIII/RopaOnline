package tienda.command;

import java.util.ArrayList;
import java.util.List;

public class Invoker {

    // Invoker del patrón Command
    // Mantiene una lista de comandos y los ejecuta en orden.

    private final List<Command> cola = new ArrayList<>();

    // Agrega un comando a la cola
    public void agregarComando(Command c) {
        if (c != null) {
            cola.add(c);
        }
    }

    // Ejecuta todos los comandos y limpia la cola
    public void ejecutarComandos() {
        for (Command c : cola) {
            c.ejecutar();
        }
        cola.clear();
    }
}
