package top.darkpath2011.blockRacing.object;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@Getter
public class Menu {
    private final String title;
    private final MenuHolder menuHolder;
    private final Inventory inventory;
    private final Map<Integer, Consumer<Player>> itemCallbacks;

    public Menu(String title, int size) {
        this.title = title;
        this.menuHolder = new MenuHolder(UUID.randomUUID());
        this.inventory = Bukkit.createInventory(menuHolder, size, title);
        this.itemCallbacks = new HashMap<>();
    }

    public void addItem(int slot, Material material, String displayName, Consumer<Player> callback) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(displayName);
            item.setItemMeta(meta);
        }

        inventory.setItem(slot, item);
        itemCallbacks.put(slot, callback);
    }

    public void open(Player player) {
        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1.0f, 1.0f);// 播放村民音效？
        BlockRacing.getPlugin().getMenuManager().registerMenu(this);// 注册一下菜单
    }

    public void close(Player player) {
        player.closeInventory();
        BlockRacing.getPlugin().getMenuManager().unregisterMenu(this);
    }
}
