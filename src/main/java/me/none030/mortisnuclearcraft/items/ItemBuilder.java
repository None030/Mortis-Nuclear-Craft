package me.none030.mortisnuclearcraft.items;

import me.none030.mortisnuclearcraft.MortisNuclearCraft;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static me.none030.mortisnuclearcraft.utils.MessageUtils.colorMessage;

public class ItemBuilder {

    private final MortisNuclearCraft plugin = MortisNuclearCraft.getInstance();
    private ItemStack item;

    public ItemBuilder(Material material, int amount) {
        setItem(new ItemStack(material, amount));
    }

    public void setCustomModelData(int customModelData) {
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(customModelData);
        item.setItemMeta(meta);
    }

    public void setName(String name) {
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(colorMessage(name)));
        item.setItemMeta(meta);
    }

    public void setLore(List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        List<Component> components = new ArrayList<>();
        for (String line : lore) {
            components.add(Component.text(colorMessage(line)));
        }
        meta.lore(components);
        item.setItemMeta(meta);
    }

    public void setEnchants(List<String> enchants) {
        HashMap<Enchantment, Integer> enchantments = convertEnchants(enchants);
        ItemMeta meta = item.getItemMeta();
        for (Enchantment enchant : enchantments.keySet()) {
            int level = enchantments.get(enchant);
            meta.addEnchant(enchant, level, true);
        }
        item.setItemMeta(meta);
    }

    public void setFlags(List<String> flags) {
        ItemMeta meta = item.getItemMeta();
        for (String rawFlag : flags) {
            ItemFlag flag;
            try {
                flag = ItemFlag.valueOf(rawFlag);
            }catch (IllegalArgumentException exp) {
                continue;
            }
            meta.addItemFlags(flag);
        }
        item.setItemMeta(meta);
    }

    private HashMap<Enchantment, Integer> convertEnchants(List<String> enchants) {
        HashMap<Enchantment, Integer> enchantments = new HashMap<>();
        for (String line : enchants) {
            String[] raw = line.split(":");
            NamespacedKey key = new NamespacedKey(plugin, raw[0]);
            Enchantment enchant = Enchantment.getByKey(key);
            int level = Integer.parseInt(raw[1]);
            if (enchant == null) {
                continue;
            }
            enchantments.put(enchant, level);
        }
        return enchantments;
    }

    public ItemStack getItem() {
        return item;
    }

    private void setItem(ItemStack item) {
        this.item = item;
    }
}
