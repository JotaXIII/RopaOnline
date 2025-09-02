package tienda.model;

import tienda.decorator.Component;
import tienda.decorator.ConcreteComponentProducto;
import tienda.decorator.DecoratorDescuentoFijo10;
import tienda.decorator.DecoratorDescuentoPorCategoria;
import tienda.decorator.PrecioContexto;

import java.util.*;

public final class DiscountManager {

    private static volatile DiscountManager INSTANCE;

    private final Map<String, Double> promotions = new HashMap<>();
    private final Map<String, Double> promosCategoria = new HashMap<>();

    private DiscountManager() { preloadPromotions(); }

    public static DiscountManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DiscountManager.class) {
                if (INSTANCE == null) INSTANCE = new DiscountManager();
            }
        }
        return INSTANCE;
    }

    private void preloadPromotions() {
        promotions.put("PRIMAVERA10", 0.10);
        promotions.put("INVIERNO10", 0.10);
        promotions.put("CYBER20", 0.20);
    }

    // lista de códigos promocionales
    public List<String> getPromotionCodesSorted() {
        List<String> list = new ArrayList<>(promotions.keySet());
        Collections.sort(list);
        return list;
    }

    public double applyDiscount(double precio, String codigo) {
        if (codigo == null || codigo.isBlank()) return precio;
        Double pct = promotions.get(codigo.toUpperCase(Locale.ROOT).trim());
        if (pct == null || pct <= 0.0) return precio;
        return precio * (1.0 - pct);
    }

    public Double getPromotionPercent(String codigo) {
        if (codigo == null || codigo.isBlank()) return null;
        return promotions.get(codigo.toUpperCase(Locale.ROOT).trim());
    }

    public void putPromotion(String codigo, double porcentaje) {
        if (codigo == null || codigo.isBlank()) return;
        double pct = Math.max(0.0, Math.min(0.90, porcentaje));
        promotions.put(codigo.toUpperCase(Locale.ROOT).trim(), pct);
    }

    public void setPromoCategoria(String categoria, double porcentaje) {
        if (categoria == null || categoria.isBlank()) return;
        double pct = Math.max(0.0, Math.min(0.90, porcentaje));
        promosCategoria.put(categoria.trim().toLowerCase(Locale.ROOT), pct);
    }

    public Double getPromoCategoriaPercent(String categoria) {
        if (categoria == null) return null;
        return promosCategoria.get(categoria.trim().toLowerCase(Locale.ROOT));
    }

    public Component buildChain(Producto p, String codigo) {
        Component comp = new ConcreteComponentProducto(p);
        String cat = p.getCategoria() == null ? "" : p.getCategoria().trim().toLowerCase(Locale.ROOT);
        Double pctCat = getPromoCategoriaPercent(cat);
        if (pctCat != null && pctCat > 0.0) {
            comp = new DecoratorDescuentoPorCategoria(comp, cat, pctCat);
        }
        Double pctCode = getPromotionPercent(codigo);
        if (pctCode != null && Math.abs(pctCode - 0.10) < 1e-9) {
            comp = new DecoratorDescuentoFijo10(comp);
        }
        return comp;
    }

    public double applyDecorators(Producto p, String codigo) {
        Component comp = buildChain(p, codigo);
        PrecioContexto ctx = new PrecioContexto(p);
        comp.operacion(ctx);
        Double pctCode = getPromotionPercent(codigo);
        if (pctCode != null && pctCode > 0.0 && Math.abs(pctCode - 0.10) >= 1e-9) {
            ctx.precio = ctx.precio * (1.0 - pctCode);
        }
        return ctx.precio;
    }
}
