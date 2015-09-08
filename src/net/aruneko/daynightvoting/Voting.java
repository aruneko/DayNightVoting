package net.aruneko.daynightvoting;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Voting {
	private VotingState state = null;

	private JavaPlugin jplug;

	private List<Player> rejectPlayers = new ArrayList<>();
	private List<Player> finishedPlayers = new ArrayList<>();

	private static int yes, no;

	private final String[] commandsList = {"day", "night", "sun", "rain"};

	public Voting(JavaPlugin j) {
		jplug = j;
		state = AcceptVoting.getInstance();
		yes = no = 0;
	}

	public void setState(VotingState s) {
		state = s;
	}

	public boolean acceptCommand(Player p, String arg) {
		return state.changeState(this, p, arg);
	}

	public boolean execCommand(Player p, String arg) {
		return state.execCommand(this, p, arg);
	}

	public String[] getCommandsList() {
		return commandsList;
	}

	public JavaPlugin getJPlugin() {
		return jplug;
	}

	public boolean isContainedRejectPlayer(Player p) {
		if (rejectPlayers.contains(p)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isContainedFinishedPlayer(Player p) {
		if (finishedPlayers.contains(p)) {
			return true;
		} else {
			return false;
		}
	}

	public List<Player> getRejectPlayers() {
		return rejectPlayers;
	}

	public List<Player> getFinishedPlayers() {
		return finishedPlayers;
	}

	public void setRejectPlayer(Player p) {
		rejectPlayers.add(p);
	}

	public void setFinishedPlayer(Player p) {
		finishedPlayers.add(p);
	}

	public boolean viewResult(Player player) {
		jplug.getServer().broadcastMessage(ChatColor.DARK_AQUA + "[DayNightVote] 賛成" + yes + "票、" + "反対" + no + "票");
		if (yes > no) {
			jplug.getServer().broadcastMessage(ChatColor.DARK_AQUA + "[DayNightVote] よって" + player.getName() + "さんの投票は可決されました。");
			return true;
		} else {
			jplug.getServer().broadcastMessage(ChatColor.DARK_AQUA + "[DayNightVote] よって" + player.getName() + "さんの投票は否決されました。");
			return false;
		}
	}

	public boolean startVoting(Voting v, Player p, String mesg, Runnable com) {
		// 自分自身で賛成票を投じる
		++yes;
		v.getJPlugin().getServer().broadcastMessage(ChatColor.DARK_AQUA + "[DayNightVote] " + p.getName() + "さんが" + mesg + "に変更する投票を開始しました");
		v.setRejectPlayer(p);
		v.setFinishedPlayer(p);

		// 投票終了後拒否時間経過まで投票開始プレイヤーを除外
		new BukkitRunnable() {
			@Override
			public void run() {
				v.getRejectPlayers().remove(p);
			}
		}.runTaskLater(v.getJPlugin(), (Configuration.getRejectTime() + Configuration.getVotableTime()) * 20);

		// 投票終了後のリセット処理
		new BukkitRunnable() {
			@Override
			public void run() {
				if (v.viewResult(p)) {
					com.run();
				}
				v.getFinishedPlayers().clear();
				yes = no = 0;
				v.setState(AcceptVoting.getInstance());
			}
		}.runTaskLater(v.getJPlugin(), Configuration.getVotableTime() * 20);

		new CountDownTimer(v.getJPlugin()).runTaskTimer(v.getJPlugin(), 0, 20);
		return true;
	}

	public boolean acceptVote(Voting v, Player p, String arg) {
		if (arg.equals("yes")) {
			if (!v.getFinishedPlayers().contains(p)) {
				v.setFinishedPlayer(p);
				++yes;
				p.sendMessage(ChatColor.DARK_AQUA + "[DayNightVote] Yesに投票しました");
			} else {
				p.sendMessage(ChatColor.DARK_AQUA + "[DayNightVote] すでに投票済みです");
			}
			return true;
		} else if (arg.equals("no")) {
			if (!v.getFinishedPlayers().contains(p)) {
				v.setFinishedPlayer(p);
				++no;
				p.sendMessage(ChatColor.DARK_AQUA + "[DayNightVote] Noに投票しました");
			} else {
				p.sendMessage(ChatColor.DARK_AQUA + "[DayNightVote] すでに投票済みです");
			}
			return true;
		}
		return false;
	}
}
