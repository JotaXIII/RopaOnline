package tienda.model;

import java.util.Objects;
import java.util.UUID;

public class Usuario {

    // Identificador único
    private final String id;
    // Nombre visible del usuario
    private final String nombre;
    // Email opcional
    private String email;

    public Usuario(String nombre) {
        this(null, nombre, null);
    }

    public Usuario(String id, String nombre, String email) {
        this.id = (id == null || id.isBlank()) ? UUID.randomUUID().toString() : id.trim();
        this.nombre = Objects.requireNonNull(nombre, "nombre").trim();
        this.email = (email == null || email.isBlank()) ? null : email.trim().toLowerCase();
    }

    // getters simples
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }

    // setea email
    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            this.email = null;
        } else {
            this.email = email.trim().toLowerCase();
        }
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        return id.equals(((Usuario) o).id);
    }
    @Override public int hashCode() { return id.hashCode(); }
}
