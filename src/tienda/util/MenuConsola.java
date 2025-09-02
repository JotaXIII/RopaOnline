package tienda.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class MenuConsola {

    private MenuConsola() { }

    // Lee entero simple
    public static int leerEntero(String prompt) {
        if (prompt != null && !prompt.isBlank()) {
            System.out.print(prompt);
        }
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine().trim();
        try { return Integer.parseInt(s); } catch (NumberFormatException e) { return -1; }
    }

    // Muestra opciones enumeradas
    public static void imprimirOpciones(List<String> opciones, String titulo) {
        if (titulo != null && !titulo.isBlank()) System.out.println(titulo);
        if (opciones == null || opciones.isEmpty()) {
            System.out.println("sin opciones");
            return;
        }
        for (int i = 0; i < opciones.size(); i++) {
            System.out.printf(" %d) %s%n", i + 1, opciones.get(i));
        }
    }

    public static int elegirOpcion(int totalOpciones, String prompt) {
        int op = leerEntero(prompt == null ? "Opción: " : prompt);
        if (op >= 1 && op <= totalOpciones) return op;
        return -1;
    }

    public static int elegirDeLista(List<String> opciones, String titulo, String prompt) {
        imprimirOpciones(opciones, titulo);
        return elegirOpcion(opciones == null ? 0 : opciones.size(), prompt);
    }

    // Convierte objetos a lista de strings
    public static <T> List<String> mapear(List<T> datos, java.util.function.Function<T, String> fn) {
        List<String> out = new ArrayList<>();
        if (datos == null) return out;
        for (T t : datos) out.add(fn.apply(t));
        return out;
    }
}
