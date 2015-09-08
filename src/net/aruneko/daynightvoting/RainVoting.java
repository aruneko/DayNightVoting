package net.aruneko.daynightvoting;

import org.bukkit.entity.Player;

public class RainVoting implements VotingState {

	private static VotingState RainState = new RainVoting();
	public static VotingState getInstance() {
		return RainState;
	}

	@Override
	public boolean changeState(Voting v, Player p, String arg) {
		return execCommand(v, p, arg);
	}

	@Override
	public boolean execCommand(Voting v, Player p, String arg) {
		if (arg.equals("sun")) {
			return v.startVoting(v, p, "天気を雨", () -> {
				p.getWorld().setStorm(true);
			});
		} else if (arg.equals("yes") || arg.equals("no")) {
			return v.acceptVote(v, p, arg);
		}
		return false;
	}

}
