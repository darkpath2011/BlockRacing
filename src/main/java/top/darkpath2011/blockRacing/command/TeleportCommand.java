package top.darkpath2011.blockRacing.command;

import top.darkpath2011.blockRacing.BlockRacing;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @Author: ShaoqingG
 * @Created: 2024/12/30 21:01
 * WARN: Please refrain from casually passing judgment on the code. The quality of the code is within the scope of responsibility of the developers themselves. If you think it's what is called "messy codebase" (the term "shit mountain" is rather vulgar), we sincerely welcome you to fork this project, and then submit a pull request (PR) to the project and optimize the relevant code. We will be extremely grateful for that.
 * However, if you merely think the code is poor but are unable to come up with an optimization plan, then please restrain the urge to comment on others' code. This is the most basic rule of courtesy. The respect that developers have for you is based on the respect you show to the developers. This is also a requirement of basic moral cultivation. Meanwhile, we should always keep in mind the core socialist values: prosperity, democracy, civility, harmony; actively advocate freedom, equality, justice, the rule of law; and vigorously promote patriotism, dedication, integrity, and friendship.
 */
public class TeleportCommand extends Command {
    public TeleportCommand() {
        super("btp");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (strings.length == 0) {
                player.sendMessage("§c请指定要传送到的玩家!");
                return false;
            }
            Player target = Bukkit.getPlayer(strings[0]);
            if (target == null) {
                player.sendMessage("§c目标玩家不在线!");
                return false;
            }
            if (!(BlockRacing.room.getPlayerTeam(player).getPlayers().contains(target))){
                player.sendMessage("§c你不能传送到非本队的玩家位置！请确保你要传送的目标与您在同一队。");
                return false;
            }
            player.teleport(target);
            player.sendMessage("§a成功传送到 " + target.getName() + "!");
        } else {
            commandSender.sendMessage("§c此命令只能由玩家执行!");
        }
        return false;
    }
}
