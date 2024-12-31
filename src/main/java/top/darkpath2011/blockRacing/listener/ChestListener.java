package top.darkpath2011.blockRacing.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import top.darkpath2011.blockRacing.BlockRacing;
import top.darkpath2011.blockRacing.object.Menu;
import top.darkpath2011.blockRacing.object.MenuHolder;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * @Author: ShaoqingG
 * @Created: 2024/12/31 18:55
 * WARN: Please refrain from casually passing judgment on the code. The quality of the code is within the scope of responsibility of the developers themselves. If you think it's what is called "messy codebase" (the term "shit mountain" is rather vulgar), we sincerely welcome you to fork this project, and then submit a pull request (PR) to the project and optimize the relevant code. We will be extremely grateful for that.
 * However, if you merely think the code is poor but are unable to come up with an optimization plan, then please restrain the urge to comment on others' code. This is the most basic rule of courtesy. The respect that developers have for you is based on the respect you show to the developers. This is also a requirement of basic moral cultivation. Meanwhile, we should always keep in mind the core socialist values: prosperity, democracy, civility, harmony; actively advocate freedom, equality, justice, the rule of law; and vigorously promote patriotism, dedication, integrity, and friendship.
 */
public class ChestListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof MenuHolder) {
            UUID inventoryId = ((MenuHolder) event.getInventory().getHolder()).getMenuId();
            Map<UUID, Menu> menus = BlockRacing.chestManager.getMenus();
            if (menus.containsKey(inventoryId)) {
                event.setCancelled(true);
                Menu menu = menus.get(inventoryId);
                int clickedSlot = event.getSlot();
                if (menu.getItemCallbacks().containsKey(clickedSlot)) {
                    Consumer<Player> callback = menu.getItemCallbacks().get(clickedSlot);
                    callback.accept((Player) event.getWhoClicked());
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof MenuHolder) {
            UUID inventoryId = ((MenuHolder) event.getInventory().getHolder()).getMenuId();
            Map<UUID, Menu> menus = BlockRacing.chestManager.getMenus();
            if (menus.containsKey(inventoryId)) {
                menus.remove(inventoryId);
                System.out.println("已移除菜单: " + inventoryId);
            }
        }
    }
}
