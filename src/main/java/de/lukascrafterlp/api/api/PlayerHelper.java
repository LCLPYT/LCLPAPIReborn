package de.lukascrafterlp.api.api;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerHelper {

    public static List<Player> getPlayers() {
        return new ArrayList<>(Bukkit.getOnlinePlayers());
    }

    public static double getMaxHealth(Player p) {
        return Optional.ofNullable(p.getAttribute(Attribute.GENERIC_MAX_HEALTH))
                .map(AttributeInstance::getValue)
                .orElse(20D);
    }

    public static boolean isFullHealth(Player p) {
        return p.getHealth() >= getMaxHealth(p);
    }

    public static boolean isFullHunger(Player p) {
        return p.getFoodLevel() >= 20;
    }

    public static void heal(Player p, double amount) {
        if (p.getHealth() < 0D || p.isDead()) return;

        double max = getMaxHealth(p);
        double health = p.getHealth();

        p.setHealth(Math.min(health + amount, max));
    }

    public static void feed(Player p, int amount) {
        p.setFoodLevel(Math.min(p.getFoodLevel() + amount, 20));
    }

    public static void removePotionEffects(Player p) {
        for (PotionEffect pe : p.getActivePotionEffects())
            p.removePotionEffect(pe.getType());
    }

    public static void addAbsorptionHearts(Player p, double amount) {
        double newAmount = p.getAbsorptionAmount() + amount;
        p.setAbsorptionAmount(Math.max(newAmount, 0));
    }
}
