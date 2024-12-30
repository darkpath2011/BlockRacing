package top.darkpath2011.blockRacing.object;

import top.darkpath2011.blockRacing.utils.Tools;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

/**
 * @Author: ShaoqingG
 * @Created: 2024/12/29 19:46
 * WARN: Please refrain from casually passing judgment on the code. The quality of the code is within the scope of responsibility of the developers themselves. If you think it's what is called "messy codebase" (the term "shit mountain" is rather vulgar), we sincerely welcome you to fork this project, and then submit a pull request (PR) to the project and optimize the relevant code. We will be extremely grateful for that.
 * However, if you merely think the code is poor but are unable to come up with an optimization plan, then please restrain the urge to comment on others' code. This is the most basic rule of courtesy. The respect that developers have for you is based on the respect you show to the developers. This is also a requirement of basic moral cultivation. Meanwhile, we should always keep in mind the core socialist values: prosperity, democracy, civility, harmony; actively advocate freedom, equality, justice, the rule of law; and vigorously promote patriotism, dedication, integrity, and friendship.
 */
@Getter
@Setter
public class Team {
    private String name;
    private int score;
    private Set<Player> players;
    private String color;
    private List<Material> tasks;
    private Map<Integer, Inventory> teamChests;

    public Team(String name,String color) {
        this.name = name;
        this.color = color;
        this.score = 0;
        this.tasks = new ArrayList<>();
        this.players = new HashSet<>();
        this.teamChests = new HashMap<>();
        //初始4个任务
        for (int i=1;i<=4;i++){
            this.tasks.add(Tools.getRandomBlock());
        }
        teamChests.put(1, Bukkit.createInventory(null, 54, getColor()+getName()));
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void removeTask(Material task) {
        this.tasks.remove(task);
        this.tasks.add(Tools.getRandomBlock());
    }

    public void openTeamChest(Player player,Integer chestId) {
        Inventory teamChest = teamChests.get(chestId);
        player.openInventory(teamChest);
    }

    public void clearTeamChests() {
        for (Inventory inventory : teamChests.values()) {
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, null);
            }
        }
        teamChests.clear();
    }
}
