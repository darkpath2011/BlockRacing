package top.darkpath2011.blockRacing.command;

import top.darkpath2011.blockRacing.BlockRacing;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @Author: ShaoqingG
 * @Created: 2024/12/29 20:38
 */
public class GameCommand extends Command {
    public GameCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender commandSender, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (args.length == 0) {
                player.sendMessage("§c请提供有效的命令参数!");
                return false;
            }
            switch (args[0].toLowerCase()) {
                case "start":
                    BlockRacing.room.startGame();
                    player.sendMessage("§f[§6✔§f] §a成功开始游戏！");
                    break;
                case "stop":
                    BlockRacing.room.endGame();
                    player.sendMessage("§f[§6✔§f] §c成功结束游戏！");
                    break;
                case "team":
                    if (args.length < 2) {
                        player.sendMessage("§c请提供有效的队伍命令（join/leave）!");
                        return false;
                    }
                    switch (args[1].toLowerCase()) {
                        case "join":
                            if (args.length < 4) {
                                player.sendMessage("§c请提供玩家名和队伍名！");
                                return false;
                            }
                            Player targetPlayer = Bukkit.getPlayer(args[2]);
                            String teamName = args[3];
                            if (targetPlayer == null) {
                                player.sendMessage("§c玩家 " + args[2] + " 不在线！");
                                return false;
                            }
                            BlockRacing.room.addPlayerToTeam(targetPlayer, teamName);
                            player.sendMessage("§f[§6✔§f] §a成功将 " + args[2] + " 加入至 " + teamName + " 队伍！");
                            break;
                        case "leave":
                            if (args.length < 3) {
                                player.sendMessage("§c请提供玩家名！");
                                return false;
                            }
                            Player leavingPlayer = Bukkit.getPlayer(args[2]);
                            if (leavingPlayer == null) {
                                player.sendMessage("§c玩家 " + args[2] + " 不在线！");
                                return false;
                            }

                            BlockRacing.room.leaveTeam(leavingPlayer);
                            player.sendMessage("§f[§6✔§f] §a成功将 " + args[2] + " 离开队伍！");
                            break;
                        default:
                            player.sendMessage("§c无效的队伍命令，使用 join 或 leave！");
                            break;
                    }
                    break;

                default:
                    player.sendMessage("§c无效的命令!");
                    break;
            }
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
        } else {
            commandSender.sendMessage("§c此命令只能由玩家执行!");
        }
        return false;
    }
}
