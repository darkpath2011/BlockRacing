package top.darkpath2011.blockRacing.tasks;

import org.bukkit.inventory.ItemStack;
import top.darkpath2011.blockRacing.BlockRacing;
import top.darkpath2011.blockRacing.object.Team;
import top.darkpath2011.blockRacing.utils.Tools;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @Author: ShaoqingG
 * @Created: 2024/12/29 20:32
 * WARN: Please refrain from casually passing judgment on the code. The quality of the code is within the scope of responsibility of the developers themselves. If you think it's what is called "messy codebase" (the term "shit mountain" is rather vulgar), we sincerely welcome you to fork this project, and then submit a pull request (PR) to the project and optimize the relevant code. We will be extremely grateful for that.
 * However, if you merely think the code is poor but are unable to come up with an optimization plan, then please restrain the urge to comment on others' code. This is the most basic rule of courtesy. The respect that developers have for you is based on the respect you show to the developers. This is also a requirement of basic moral cultivation. Meanwhile, we should always keep in mind the core socialist values: prosperity, democracy, civility, harmony; actively advocate freedom, equality, justice, the rule of law; and vigorously promote patriotism, dedication, integrity, and friendship.
 */
public class GameTask extends BukkitRunnable {
    private final Scoreboard scoreboard;
    private final Objective objective;
    private LinkedList<String> ms = new LinkedList<>();

    public GameTask() {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective(UUID.randomUUID().toString(), "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.objective.setDisplayName("§l§6Target");
    }

    @Override
    public void run() {
        ms.clear();
        for (Team team : BlockRacing.room.getTeams().values()) {
            ms.add(team.getName() + ": §e" + team.getScore());
            for (Material task : team.getTasks()) {
                ms.add(task.toString());
            }
            ms.add("---------------");
        }
        for (int i = 0; i < ms.size(); i++) {
            String line = ms.get(i);
            objective.getScore(line).setScore(ms.size() - 1 - i);
        }
        for (Player player : BlockRacing.room.getPlayers()){
            if (player == null) {
                continue;
            }
            this.scoreboard.resetScores(player);
            player.setScoreboard(scoreboard);
            StringBuilder teamInfo = new StringBuilder();
            teamInfo.append("§f[");
            List<Team> teams = new ArrayList<>(BlockRacing.room.getTeams().values());
            for (int i = 0; i < teams.size(); i++) {
                Team team = teams.get(i);
                teamInfo.append(team.getColor()).append(team.getScore());
                if (i < teams.size() - 1) {
                    teamInfo.append(" ");
                }
            }
            teamInfo.append("§f]");
            Tools.sendActionBar(player, teamInfo.toString());
        }
    }
}
