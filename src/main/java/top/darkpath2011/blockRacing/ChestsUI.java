package top.darkpath2011.blockRacing;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import top.darkpath2011.blockRacing.object.Menu;

/**
 * @Author: ShaoqingG
 * @Created: 2024/12/31 18:22
 * WARN: Please refrain from casually passing judgment on the code. The quality of the code is within the scope of responsibility of the developers themselves. If you think it's what is called "messy codebase" (the term "shit mountain" is rather vulgar), we sincerely welcome you to fork this project, and then submit a pull request (PR) to the project and optimize the relevant code. We will be extremely grateful for that.
 * However, if you merely think the code is poor but are unable to come up with an optimization plan, then please restrain the urge to comment on others' code. This is the most basic rule of courtesy. The respect that developers have for you is based on the respect you show to the developers. This is also a requirement of basic moral cultivation. Meanwhile, we should always keep in mind the core socialist values: prosperity, democracy, civility, harmony; actively advocate freedom, equality, justice, the rule of law; and vigorously promote patriotism, dedication, integrity, and friendship.
 */
public class ChestsUI {
    public static void mainGui(Player player){
        Menu menu = new Menu("游戏菜单",9);
        menu.addItem(0,Material.ENDER_PEARL,"§l§a团队传送点", p -> {
            locationGui(player);
        });
        for (int i = 1; i<= BlockRacing.room.getPlayerTeam(player).getTeamChests().size(); i++){
            int finalI = i;
            menu.addItem(finalI+1,Material.ENDER_CHEST,"§l§a团队"+finalI+"号箱子",item ->{
                BlockRacing.room.getPlayerTeam(player).openTeamChest(player, finalI);
            });
        }
        menu.addItem(7,Material.GRASS_BLOCK,"§l§a创建团队传送点", item ->{
            BlockRacing.room.getPlayerTeam(player).createTeamLocation(player.getLocation());
            player.sendMessage("§l§a已创建团队传送点");
        });
        menu.addItem(8,Material.COMMAND_BLOCK,"§l§aROLL一下", item ->{
            BlockRacing.room.getPlayerTeam(player).roll();
            player.sendMessage("§l§c执行成功!");
        });
        BlockRacing.chestManager.addMenu(menu);
        menu.open(player);
    }

    public static void locationGui(Player player){
        Menu menu = new Menu("团队传送",54);
        for (int i=1;i<= BlockRacing.room.getPlayerTeam(player).getTeamLocations().size(); i++){
            int finalI = i;
            menu.addItem(finalI-1,Material.NOTE_BLOCK,"传送点"+i,item ->{
                player.teleport(BlockRacing.room.getPlayerTeam(player).getTeamLocations().get(finalI));
                player.sendMessage("§l§a传送成功");
            });
        }
        BlockRacing.chestManager.addMenu(menu);
        menu.open(player);
    }
}
