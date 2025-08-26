package tienda.descuento;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class DiscountManager {

    // Instancia única (Singleton)
    private static volatile DiscountManager INSTANCE;

    // Tabla de promociones
    private final Map<String, Double> promotions = new HashMap<>();

    private DiscountManager() { preloadPromotions(); }

    public static DiscountManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DiscountManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DiscountManager();
                }
            }
        }
        return INSTANCE;
    }

    private void preloadPromotions() {
        promotions.put("PRIMAVERA10", 0.10);
        promotions.put("VERANO15",    0.15);
        promotions.put("OTOÑO12",     0.12);
        promotions.put("INVIERNO10",  0.10);
        promotions.put("KIDS12", 0.12);
        promotions.put("VIP15",  0.15);
    }

    public double applyDiscount(double precio, String codigo) {
        if (codigo == null || codigo.isBlank()) return precio;
        String key = codigo.toUpperCase(Locale.ROOT).trim();
        Double pct = promotions.get(key);
        if (pct == null || pct <= 0.0) return precio;
        return precio * (1.0 - pct);
    }

    public Double getPromotionPercent(String codigo) {
        if (codigo == null || codigo.isBlank()) return null;
        return promotions.get(codigo.toUpperCase(Locale.ROOT).trim());
    }
}
