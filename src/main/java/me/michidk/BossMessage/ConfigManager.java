package me.michidk.BossMessage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ml
 * Date: 01.09.13
 * Time: 00:42
 * To change this template use File | Settings | File Templates.
 */
public class ConfigManager {

    final static String path = "plugins/BossMessage/config.yml";
    File file = new File(path);
    FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    public boolean enabled = false;
    public boolean random = true;
    public int interval = 60;
    public int show = 20;
    public List<List<String>> messages;
    public List<String> players;
    public boolean whitelist = false;
    public List<String> worlds;
    //public String configVersion = Main.getInstance().getDescription().getVersion();

    public void createConfig () {
        //if (newConfig() == false)
        //    return;

        config.addDefault("BossMessage.Enabled", true);
        config.addDefault("BossMessage.Random", false);
        config.addDefault("BossMessage.Interval", 25);
        config.addDefault("BossMessage.Show", 10);
        config.addDefault("BossMessage.Whitelist", false);

        List<List<String>> exampleList = new ArrayList<List<String>>();
        
        List<String> msg1 = new ArrayList<>();
        msg1.add("&5First message");
        msg1.add("100");
        exampleList.add(msg1);
        
        List<String> msg2 = new ArrayList<>();
        msg2.add("&bSecond message with 60% on BossBar");
        msg2.add("60");
        exampleList.add(msg2);
        
        config.addDefault("BossMessage.Messages", exampleList);

        List<String> playersList = new ArrayList<>();
        playersList.add("testPlayer");
        playersList.add("examplePlayer");
        playersList.add("your name");
        config.addDefault("BossMessage.IgnorePlayers", playersList);

        List<String> worldList = new ArrayList<>();
        worldList.add("world");
        worldList.add("world_nether");
        worldList.add("ExampleWorld");
        config.addDefault("BossMessage.WhitelistedWorlds", worldList);

        //config.set("BossMessage.configVersion", Main.getInstance().getDescription().getVersion());

        config.options().copyDefaults(true);

        save();
    }

    /*      --- addDefault dont overwrite values ---
    //return true: generate new config
    private boolean newConfig() {
        if (!config.contains("BossMessage.configVersion")) {
            return true;
        }
        configVersion = config.getString("BossMessage.configVersion");

        if (configVersion.equalsIgnoreCase(Main.getInstance().getDescription().getVersion())) {
            System.out.println("return false");
            return false;
        } else {
            System.out.println("return true");
            return true;
        }
    }
    */

    public void readConfig() {
        enabled = config.getBoolean("BossMessage.Enabled");
        random = config.getBoolean("BossMessage.Random");
        interval = config.getInt("BossMessage.Interval");
        show = config.getInt("BossMessage.Show");
        messages = (List<List<String>>) config.getList("BossMessage.Messages");
        players = config.getStringList("BossMessage.IgnorePlayers");
        whitelist = config.getBoolean("BossMessage.Whitelist");
        worlds = config.getStringList("BossMessage.WhitelistedWorlds");
    }

    private void save() {
        try {
            config.save(path);
        } catch (IOException e) {
            System.out.println("[BossMessage] Error 'createConfig' on " + path);
        }
    }
}
