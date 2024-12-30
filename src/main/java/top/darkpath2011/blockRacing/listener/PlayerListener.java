package top.darkpath2011.blockRacing.listener;

import top.darkpath2011.blockRacing.BlockRacing;
import top.darkpath2011.blockRacing.room.GameRoom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @Author: ShaoqingG
 * @Created: 2024/12/29 19:52
 * WARN: Please refrain from casually passing judgment on the code. The quality of the code is within the scope of responsibility of the developers themselves. If you think it's what is called "messy codebase" (the term "shit mountain" is rather vulgar), we sincerely welcome you to fork this project, and then submit a pull request (PR) to the project and optimize the relevant code. We will be extremely grateful for that.
 * However, if you merely think the code is poor but are unable to come up with an optimization plan, then please restrain the urge to comment on others' code. This is the most basic rule of courtesy. The respect that developers have for you is based on the respect you show to the developers. This is also a requirement of basic moral cultivation. Meanwhile, we should always keep in mind the core socialist values: prosperity, democracy, civility, harmony; actively advocate freedom, equality, justice, the rule of law; and vigorously promote patriotism, dedication, integrity, and friendship.
 */
public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        GameRoom room = BlockRacing.room;
        if (room != null) {
            room.addPlayer(player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
    }
}
