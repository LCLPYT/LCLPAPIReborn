package de.lukascrafterlp.api.api;

import com.destroystokyo.paper.Namespaced;
import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class StackEditor {

    protected final ItemStack stack;

    protected StackEditor(ItemStack stack) {
        this.stack = Objects.requireNonNull(stack);
    }

    public StackEditor displayName(Component component) {
        stack.editMeta(im -> im.displayName(component));
        return this;
    }

    public StackEditor enchant(Enchantment enchantment, int level) {
        stack.editMeta(im -> im.addEnchant(enchantment, level, true));
        return this;
    }

    public StackEditor unbreakable() {
        return unbreakable(true);
    }

    public StackEditor unbreakable(boolean unbreakable) {
        stack.editMeta(im -> im.setUnbreakable(unbreakable));
        return this;
    }

    public StackEditor customModelData(@Nullable Integer customModelData) {
        stack.editMeta(im -> im.setCustomModelData(customModelData));
        return this;
    }

    public StackEditor flags(ItemFlag... flags) {
        stack.editMeta(im -> im.addItemFlags(flags));
        return this;
    }

    public StackEditor removeFlags(ItemFlag... flags) {
        stack.editMeta(im -> im.removeItemFlags(flags));
        return this;
    }

    public StackEditor attributeModifier(Attribute attribute, AttributeModifier modifier) {
        stack.editMeta(im -> im.addAttributeModifier(attribute, modifier));
        return this;
    }

    public StackEditor removeAttributeModifier(Attribute attribute) {
        stack.editMeta(im -> im.removeAttributeModifier(attribute));
        return this;
    }

    public StackEditor removeAttributeModifier(Attribute attribute, AttributeModifier modifier) {
        stack.editMeta(im -> im.removeAttributeModifier(attribute, modifier));
        return this;
    }

    public StackEditor removeAttribute(EquipmentSlot slot) {
        stack.editMeta(im -> im.removeAttributeModifier(slot));
        return this;
    }

    public StackEditor destroyable(Namespaced... keys) {
        return destroyable(Arrays.asList(keys));
    }

    public StackEditor destroyable(Collection<Namespaced> keys) {
        stack.editMeta(im -> im.setDestroyableKeys(keys));
        return this;
    }

    public StackEditor placeable(Namespaced... keys) {
        return placeable(Arrays.asList(keys));
    }

    public StackEditor placeable(Collection<Namespaced> keys) {
        stack.editMeta(im -> im.setPlaceableKeys(keys));
        return this;
    }

    public ItemStack get() {
        return stack;
    }

    public static StackEditor of(ItemStack stack) {
        return new StackEditor(stack);
    }
}
