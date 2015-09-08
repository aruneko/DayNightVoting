package net.aruneko.daynightvoting;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AcceptVoting implements VotingState {

	private static VotingState acceptState = new AcceptVoting();
	public static VotingState getInstance() {
		return acceptState;
	}

	@Override
	public boolean changeState(Voting v, Player p, String arg) {
		switch (arg) {
		case "day":
			v.setState(DayVoting.getInstance());
			break;
		case "night":
			v.setState(NightVoting.getInstance());
			break;
		case "sun":
			v.setState(SunVoting.getInstance());
			break;
		case "rain":
			v.setState(RainVoting.getInstance());
			break;
		default:
			v.setState(AcceptVoting.getInstance());
			break;
		}
		return v.execCommand(p, arg);
	}

	@Override
	public boolean execCommand(Voting v, Player p, String arg) {
		//パーミッションを調べる
		if (!p.hasPermission("daynightvoting." + arg)) {
			p.sendMessage(ChatColor.DARK_AQUA + "[DayNightVote] コマンドの実行権限がありません");
			return true;
		}

		if (arg.equals("yes") || arg.equals("no")) {
			p.sendMessage(ChatColor.DARK_AQUA + "[DayNightVote] 現在投票は行われていません");
			return true;
		}
		return false;
	}
}
