package tienda.descuento;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class DiscountManager {

    // Instancia única Singleton
    private static volatile DiscountManager INSTANCE;

    private final Map<String, Double> promotions = new HashMap<>();

    // Constructor privado
    private DiscountManager() {
        preloadPromotions();
    }

    // Punto de acceso único
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

    // Carga promociones
    private void preloadPromotions() {
        // X temporadas
        promotions.put("PRIMAVERA10", 0.10);
        promotions.put("VERANO15",    0.15);
        promotions.put("OTOÑO12",     0.12);
        promotions.put("INVIERNO10",  0.10);
        // VIP
        promotions.put("VIP15", 0.15);
        // Niños
        promotions.put("KIDS12", 0.12);
    }

    // Aplica el descuento por código, si no existe se cobra el precio original
    public double applyDiscount(double price, String promoCode) {
        if (price < 0) throw new IllegalArgumentException("El precio no puede ser negativo.");
        if (promoCode == null || promoCode.isBlank()) return price;

        String key = normalize(promoCode);
        Double pct = promotions.get(key);
        if (pct == null) return price;

        double finalPrice = price * (1.0 - pct);
        return Math.max(0.0, finalPrice);
    }

    private String normalize(String code) {
        return code.toUpperCase(Locale.ROOT).trim();
    }

    public Double getPromotionPercent(String code) {
        if (code == null || code.isBlank()) return null;
        return promotions.get(normalize(code));
    }
}
