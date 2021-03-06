package net.PixelizedMC.BossMessage;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import me.confuser.barapi.BarAPI;

public class Lib {
	
	static ConfigManager cm;
	static RandomExt random = new RandomExt(new Random());
	static int count = 0;
	
	public static void onEnable() {
        cm = new ConfigManager();
        cm.createConfig();
        cm.readConfig();
	}
	
	public static List<String> getMessage() {
		if (cm.random) {
			List<List<String>> messages = cm.messages;
			int r = random.randInt(0, messages.size() - 1);
			List<String> message = messages.get(r);
			String coloredmsg = ChatColor.translateAlternateColorCodes('&', message.get(0));
			message.set(0, coloredmsg);
			return message;
		} else {
			List<List<String>> messages = cm.messages;
			List<String> message = messages.get(count);
			count++;
			if (count >= messages.size())
			count = 0;
			String coloredmsg = ChatColor.translateAlternateColorCodes('&', message.get(0));
			message.set(0, coloredmsg);
			return message;
		}
	}
	
	public static void setPlayerMsg(Player p, List<String> msg) {
		if (msg.size() == 2) {
			if (msg.get(0) != null && NumberUtils.isNumber(msg.get(1))) {
				
				String message = msg.get(0);
				float percent = Float.parseFloat(msg.get(1));

				if (message.toLowerCase().contains("%player%".toLowerCase())) {
					message = message.replaceAll("(?i)%player%", p.getName());
				}
				if (message.toLowerCase().contains("%rdm_color%".toLowerCase())) {
					String colorcode;
					while (message.toLowerCase().contains("%rdm_color%".toLowerCase())) {
						colorcode = ChatColor.COLOR_CHAR + "" + cm.colorcodes.charAt(random.randInt(cm.colorcodes.length()));
						message = message.replaceFirst("(?i)%rdm_color%", colorcode);
					}
				}
				
				BarAPI.setMessage(p, message, percent);
			}
		}
	}
	
	public static void setMsg(List<String> msg) {
		if (cm.whitelist) {
			List<String> worlds = cm.worlds;
			List<Player> players;
			for (String w:worlds) {
				if (Bukkit.getWorld(w) != null) {
					players = Bukkit.getWorld(w).getPlayers();
					for (Player p:players) {
						setPlayerMsg(p, msg);
					}
					players.clear();
				}
			}
		} else {
			for (Player p:Bukkit.getOnlinePlayers()) {
				setPlayerMsg(p, msg);
			}
		}
	}
}