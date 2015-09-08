package net.aruneko.daynightvoting;

import org.bukkit.entity.Player;

public interface VotingState {
	public abstract boolean changeState(Voting c, Player p, String arg);
	public abstract boolean execCommand(Voting v, Player p, String arg);
}
