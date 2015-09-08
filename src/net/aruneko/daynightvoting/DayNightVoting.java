package net.aruneko.daynightvoting;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class DayNightVoting extends JavaPlugin {

	Configuration conf;
	Voting voting;

	@Override
	public void onEnable() {
		// 設定ファイルの読み込みと投票クラスの初期化
		conf = new Configuration(this);
		conf.loadConfig();
		voting = new Voting(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			// プレイヤー名を取得
			final Player player = (Player)sender;

			if (cmd.getName().equalsIgnoreCase("dnv") && args.length > 0) {
				if (Arrays.asList(voting.getCommandsList()).contains(args[0])) {
					// 投票拒否時間でないか調べる
					if (voting.isContainedRejectPlayer(player)) {
						player.sendMessage(ChatColor.DARK_AQUA + "[DayNightVote] 投票中および投票終了後" + Configuration.getRejectTime() + "秒間は投票を開始できません");
						return true;
					}
				}
				// 何らかの引数を得た場合Votingに処理を委託
				return voting.acceptCommand(player, args[0]);
			}
		} else {
			// コマンドラインから実行したとき
			sender.sendMessage("[DayNightVote] You can't execute this plugin via command line.");
			return false;
		}
		return false;
	}

	@Override
	public void onDisable() {

	}
}
