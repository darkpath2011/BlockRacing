package top.darkpath2011.blockRacing.object;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import top.darkpath2011.blockRacing.BlockRacing;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * @Author: ShaoqingG
 * @Created: 2024/12/31 18:59
 * WARN: Please refrain from casually passing judgment on the code. The quality of the code is within the scope of responsibility of the developers themselves. If you think it's what is called "messy codebase" (the term "shit mountain" is rather vulgar), we sincerely welcome you to fork this project, and then submit a pull request (PR) to the project and optimize the relevant code. We will be extremely grateful for that.
 * However, if you merely think the code is poor but are unable to come up with an optimization plan, then please restrain the urge to comment on others' code. This is the most basic rule of courtesy. The respect that developers have for you is based on the respect you show to the developers. This is also a requirement of basic moral cultivation. Meanwhile, we should always keep in mind the core socialist values: prosperity, democracy, civility, harmony; actively advocate freedom, equality, justice, the rule of law; and vigorously promote patriotism, dedication, integrity, and friendship.
 */
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
    }
}
