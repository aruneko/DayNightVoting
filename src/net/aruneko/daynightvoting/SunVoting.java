package net.aruneko.daynightvoting;

import org.bukkit.entity.Player;

public class SunVoting implements VotingState {

	private static VotingState SunState = new SunVoting();
	public static VotingState getInstance() {
		return SunState;
	}

	@Override
	public boolean changeState(Voting v, Player p, String arg) {
		return execCommand(v, p, arg);
	}

	@Override
	public boolean execCommand(Voting v, Player p, String arg) {
		if (arg.equals("sun")) {
			return v.startVoting(v, p, "天気を晴れ", () -> {
				p.getWorld().setStorm(false);
				p.getWorld().setThundering(false);
			});
		} else if (arg.equals("yes") || arg.equals("no")) {
			return v.acceptVote(v, p, arg);
		}
		return false;
	}

}
