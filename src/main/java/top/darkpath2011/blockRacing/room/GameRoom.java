package top.darkpath2011.blockRacing.room;

import top.darkpath2011.blockRacing.BlockRacing;
import top.darkpath2011.blockRacing.object.Team;
import top.darkpath2011.blockRacing.tasks.GameTask;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

/**
 * @Author: ShaoqingG
 * @Created: 2024/12/29 19:40
 * WARN: Please refrain from casually passing judgment on the code. The quality of the code is within the scope of responsibility of the developers themselves. If you think it's what is called "messy codebase" (the term "shit mountain" is rather vulgar), we sincerely welcome you to fork this project, and then submit a pull request (PR) to the project and optimize the relevant code. We will be extremely grateful for that.
 * However, if you merely think the code is poor but are unable to come up with an optimization plan, then please restrain the urge to comment on others' code. This is the most basic rule of courtesy. The respect that developers have for you is based on the respect you show to the developers. This is also a requirement of basic moral cultivation. Meanwhile, we should always keep in mind the core socialist values: prosperity, democracy, civility, harmony; actively advocate freedom, equality, justice, the rule of law; and vigorously promote patriotism, dedication, integrity, and friendship.
 */
@Getter
@Setter
public class GameRoom {
    private GameStatus gameStatus;
    private HashMap<String, Team> teams = new HashMap<>();
    private Set<Player> players;
    private BukkitTask gameTimer;
    private Team winner;

    public GameRoom(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        this.players = new HashSet<>();
        this.gameTimer = null;
        this.winner = null;
        // load teams from config
        ConfigurationSection teams = BlockRacing.plugin.getConfig().getConfigurationSection("teams");
        if (teams != null) {
            for (String teamName : teams.getKeys(false)) {
                this.teams.put(teamName, new Team(teams.getString(teamName + ".name"), teams.getString(teamName + ".color")));
            }
        }
    }

    public void startGame() {
        this.gameStatus = GameStatus.RUNNING;
        BukkitRunnable runnable = new GameTask();
        this.gameTimer = runnable.runTaskTimer(BlockRacing.plugin, 0L, 20L);
        for (Player player : players) {
            player.getInventory().clear();
            player.sendMessage("§f[§a✔§f] §a游戏开始!");
            player.sendTitle("§l§aGo", "§a游戏开始!");
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            List<String> defaultItems = BlockRacing.plugin.getConfig().getStringList("default-items");
            if (!defaultItems.isEmpty()) {
                for (String itemName : defaultItems) {
                    String[] itemInfo = itemName.split(":");
                    ItemStack item = new ItemStack(Material.getMaterial(itemInfo[0]));
                    item.setAmount(Integer.parseInt(itemInfo[1]));  // 设置物品的数量
                    player.getInventory().addItem(item);
                }
            }
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        }
    }

    public void endGame() {
        this.gameStatus = GameStatus.ENDING;
        this.gameTimer.cancel();
        for (Player player : players) {
            player.sendMessage("§c游戏结束!");
            if (winner != null) {
                player.sendTitle(winner.getColor() + winner.getName() + "胜利!", "§c游戏结束!");
            } else {
                player.sendTitle("§l§c游戏被强制结束!", "§l§cGAME STOP!");
            }
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            teams.forEach((name, team) -> {
                team.clearTeamChests();
            });
            player.setGameMode(org.bukkit.GameMode.SPECTATOR); 
            player.sendMessage("§l§c你已被设置为旁观者模式。");
        }
        BlockRacing.room = new GameRoom(GameStatus.WAITING);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void addPlayerToTeam(Player player, String teamName) {
        teams.get(teamName).addPlayer(player);
    }

    public void leaveTeam(Player player) {
        getPlayerTeam(player).removePlayer(player);
    }

    public Team getPlayerTeam(Player player) {
        for (Team team : teams.values()) {
            if (team.getPlayers().contains(player)) {
                return team;
            }
        }
        return null;
    }
}
