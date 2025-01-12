package top.darkpath2011.blockRacing.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("blockracinghelp")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage("§l§a当前版本：1.0");
                player.sendMessage("§l§a作者：darkpath2011/shazi_awa");
                player.sendMessage("§l§a检查更新：§bhttps://github.com/darkpath2011/BlockRacing");
                player.sendMessage("§l§a提示：现在是测试版本，存在不稳定因素，欢迎提交Issues。");
            } else {
                sender.sendMessage("§l§a当前版本：1.0");
                sender.sendMessage("§l§a作者：darkpath2011/shazi_awa");
                sender.sendMessage("§l§a检查更新：§bhttps://github.com/darkpath2011/BlockRacing");
                sender.sendMessage("§l§a提示：现在是测试版本，存在不稳定因素，欢迎提交Issues。");
            }
            return true;
        }
        return false;
    }
}
