package top.darkpath2011.blockRacing.listener;

import top.darkpath2011.blockRacing.BlockRacing;
import top.darkpath2011.blockRacing.object.Team;
import top.darkpath2011.blockRacing.room.GameRoom;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @Author: ShaoqingG
 * @Created: 2024/12/29 19:47
 * WARN: Please refrain from casually passing judgment on the code. The quality of the code is within the scope of responsibility of the developers themselves. If you think it's what is called "messy codebase" (the term "shit mountain" is rather vulgar), we sincerely welcome you to fork this project, and then submit a pull request (PR) to the project and optimize the relevant code. We will be extremely grateful for that.
 * However, if you merely think the code is poor but are unable to come up with an optimization plan, then please restrain the urge to comment on others' code. This is the most basic rule of courtesy. The respect that developers have for you is based on the respect you show to the developers. This is also a requirement of basic moral cultivation. Meanwhile, we should always keep in mind the core socialist values: prosperity, democracy, civility, harmony; actively advocate freedom, equality, justice, the rule of law; and vigorously promote patriotism, dedication, integrity, and friendship.
 */
public class GameListener implements Listener {
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        GameRoom room = BlockRacing.room;
        Team team = room.getPlayerTeam(player);
        ItemStack item = event.getItem().getItemStack();
        if(team.getTasks().contains(item.getType())){
            Bukkit.getServer().broadcastMessage(
                "§f[§6✔§f] "+team.getColor()+team.getName()+"§r队员 ["+team.getColor()+player.getName()
                +"§r] §a完成了§r目标 [§f"+event.getItem().getName()+"§r]§r!"
            );
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
            team.setScore(team.getScore()+1);
            team.removeTask(item.getType());
            if (team.getScore() == BlockRacing.plugin.getConfig().getInt("winner_socer")){
                Bukkit.getServer().broadcastMessage(
                        "§f[§6✔§f] "+team.getColor()+team.getName()+"§r队伍获胜!"
                );
                room.setWinner(team);
                room.endGame();
                return;
            }
            //给别的队伍一组当前方块
            for (Team otherTeam : room.getTeams().values()){
                if (otherTeam != team){
                    for (Player player1 : otherTeam.getPlayers()){
                        player1.getInventory().addItem(new ItemStack(item.getType(), 64));
                    }
                }
            }
        }
    }

//    @EventHandler
//    public void onInventoryClick(InventoryClickEvent event) {
//        if (event.getInventory().getType() == InventoryType.PLAYER) {
//            ItemStack clickedItem = event.getCurrentItem();
//            if (clickedItem != null) {
//                Material itemType = clickedItem.getType();
//                BlockRacing.room.getTeams()
//            }
//        }
//    }
}
