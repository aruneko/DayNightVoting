package net.aruneko.daynightvoting;

import org.bukkit.entity.Player;

public class NightVoting implements VotingState {

	private static VotingState nightState = new NightVoting();
	public static VotingState getInstance() {
		return nightState;
	}

	@Override
	public boolean changeState(Voting v, Player p, String arg) {
		return execCommand(v, p, arg);
	}

	@Override
	public boolean execCommand(Voting v, Player p, String arg) {
		if (arg.equals("night")) {
			return v.startVoting(v, p, "時間を夜", () -> {
				p.getWorld().setTime(13000);
			});
		} else if (arg.equals("yes") || arg.equals("no")) {
			return v.acceptVote(v, p, arg);
		}
		return false;
	}

}
