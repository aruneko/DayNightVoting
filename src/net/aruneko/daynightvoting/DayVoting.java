package net.aruneko.daynightvoting;

import org.bukkit.entity.Player;

public class DayVoting implements VotingState {

	private static VotingState dayState = new DayVoting();
	public static VotingState getInstance() {
		return dayState;
	}

	@Override
	public boolean changeState(Voting v, Player p, String arg) {
		return execCommand(v, p, arg);
	}

	@Override
	public boolean execCommand(Voting v, Player p, String arg) {
		if (arg.equals("day")) {
			return v.startVoting(v, p, "時間を朝", () -> {
				p.getWorld().setTime(0);
			});
		} else if (arg.equals("yes") || arg.equals("no")) {
			return v.acceptVote(v, p, arg);
		}
		return false;
	}

}
