package net.aruneko.daynightvoting;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CountDownTimer extends BukkitRunnable {

	private int count;
	private JavaPlugin jplug;

	public CountDownTimer(JavaPlugin jp) {
		this.count = Configuration.getVotableTime();
		this.jplug = jp;
	}

	@Override
	public void run() {
		if (count > 0) {
			if (count % 10 == 0) {
				jplug.getServer().broadcastMessage(ChatColor.DARK_AQUA + "[DayNightVote] 投票終了まであと" + count + "秒です");
			}
			count--;
		} else {
			this.cancel();
		}
	}
}
