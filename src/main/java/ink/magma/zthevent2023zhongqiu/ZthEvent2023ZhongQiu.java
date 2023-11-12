package ink.magma.zthevent2023zhongqiu;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;
import java.util.List;

public final class ZthEvent2023ZhongQiu extends JavaPlugin implements Listener {
    public static LegacyComponentSerializer legacyComponentSerializer;

    @Override
    public void onEnable() {
        // Plugin startup logic
        legacyComponentSerializer = LegacyComponentSerializer.legacy('&');

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        // 判断是否晚于 UTC+8 中国标准时间(本地时间) 2023-10-07 00:00:00
        if (LocalDateTime.now().isAfter(LocalDateTime.of(2023, 10, 7, 0, 0))) return;

        List<EntityType> typeList = List.of(EntityType.ZOMBIE, EntityType.SPIDER, EntityType.SKELETON, EntityType.CREEPER);
        if (typeList.contains(event.getEntity().getType()) && Math.random() <= 0.1) {
            event.getDrops().add(getPaper());
        }
    }

    private static ItemStack getPaper() {
        ItemStack paper = new ItemStack(Material.PAPER, 1);
        ItemMeta itemMeta = paper.getItemMeta();

        TextComponent name = legacyComponentSerializer.deserialize("&r&b包装纸").decoration(TextDecoration.ITALIC, false);

        List<TextComponent> lore = List.of(
                Component.text("看起来好像没什么用的包装纸").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
                Component.text("倒是意外的非常干净").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
                Component.text("你在怀疑这怪物到底是来干什么的").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
                Component.text("<2023中秋-材料>").color(NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)
        );

        itemMeta.displayName(name);
        itemMeta.lore(lore);

        paper.setItemMeta(itemMeta);

        return paper;
    }
}
