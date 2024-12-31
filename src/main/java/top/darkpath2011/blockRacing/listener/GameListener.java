package top.darkpath2011.blockRacing.listener;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import top.darkpath2011.blockRacing.BlockRacing;
import top.darkpath2011.blockRacing.object.Team;
import top.darkpath2011.blockRacing.room.GameRoom;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class GameListener implements Listener {

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        handleItemInteraction(event.getPlayer(), event.getItem().getItemStack());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getType() == InventoryType.PLAYER) {
            Player player = (Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();
            handleItemInteraction(player, item);
        }
    }

    private void handleItemInteraction(Player player, ItemStack item) {
        GameRoom room = BlockRacing.room;
        Team team = room.getPlayerTeam(player);

        if (team.getTasks().contains(item.getType())) {
            team.addScore(player.getName(), item.getType());
            team.removeTask(item.getType());

            if (team.getScore() == BlockRacing.plugin.getConfig().getInt("winner_socer")) {
                Bukkit.getServer().broadcastMessage(
                        "§f[§6✔§f] " + team.getColor() + team.getName() + "§r队伍获胜!"
                );
                room.setWinner(team);
                room.endGame();
                return;
            }
            giveItemsToOtherTeams(item, team);
        }
    }

    private void giveItemsToOtherTeams(ItemStack item,Team team) {
        GameRoom room = BlockRacing.room;

        for (Team otherTeam : room.getTeams().values()) {
            if (otherTeam != team) {
                for (Player player : otherTeam.getPlayers()) {
                    player.getInventory().addItem(new ItemStack(item.getType(), 64));
                }
            }
        }
    }
}
