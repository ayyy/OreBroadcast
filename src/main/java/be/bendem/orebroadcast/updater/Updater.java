package be.bendem.orebroadcast.updater;

import be.bendem.orebroadcast.OreBroadcast;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * AutoUpdater written by bendem, inspired by :
 * + https://github.com/Ribesg/NPlugins/tree/master/NCore/src/main/java/fr/ribesg/bukkit/ncore/updater
 * + https://github.com/gravitylow/Updater
 *
 * TODO Download file and check update in another thread!!!!!!!
 *
 * @author bendem
 */
public class Updater {

    public static final int    PROJECT_ID     = 72299;
    public static final String API_URL        = "https://api.curseforge.com/servermods/files?projectIds=";
    public static final String UPDATE_MESSAGE = ChatColor.GOLD + "A new version is available, type " + ChatColor.BOLD + "/ob update" + ChatColor.RESET + ChatColor.GOLD + " to download it";

    private final OreBroadcast plugin;
    private final Version      localVersion;
    private final Channel      updateChannel;

    public Updater(OreBroadcast plugin) {
        this.plugin = plugin;
        this.localVersion = new Version(plugin.getDescription().getVersion());
        this.updateChannel = plugin.getConfig().getBoolean("use-beta", false) ? Channel.Beta : Channel.Release;
    }

    public boolean checkNewVersion() {
        FileDescription newest = getLastVersion();
        if(newest == null) {
            return false;
        }
        return checkNewVersion(newest.getVersion());
    }

    public boolean checkNewVersion(Version remoteVersion) {
        return localVersion.isNewer(remoteVersion);
    }

    public boolean update() {
        FileDescription remoteVersion = getLastVersion();
        if(remoteVersion == null) {
            return false;
        }
        if(checkNewVersion(remoteVersion.getVersion())) {
            downloadFile(remoteVersion);
        }
        return false;
    }

    public void notifyConsole() {
        OreBroadcast.sendLogMessage(UPDATE_MESSAGE, ChatColor.GREEN);
    }

    public void notifyOps() {
        for(OfflinePlayer op : Bukkit.getOperators()) {
            if(op.isOnline()) {
                notifyOp(op.getPlayer());
            }
        }
    }

    public void notifyOp(Player op) {
        op.sendMessage(UPDATE_MESSAGE);
    }

    public static FileDescription getNewestVersion(List<FileDescription> files) {
        if(files.size() == 0) {
            return null;
        }

        FileDescription newest = files.get(0);
        for(FileDescription file : files) {
            if(newest.getVersion().isNewer(file.getVersion())) {
                newest = file;
            }
        }

        return newest;
    }

    protected FileDescription getLastVersion() {
        String json = new UrlReader(API_URL + PROJECT_ID).read();
        if(json == null) {
            OreBroadcast.sendLogMessage("Could not contact Curse API...", ChatColor.RED);
            return null;
        }

        // Parse all files to get FileDescription's
        List<FileDescription> files = new ArrayList<>();

        JSONArray array = (JSONArray) JSONValue.parse(json);
        for(Object object : array.toArray()) {
            FileDescription file = new FileDescription((JSONObject) object);
            // Ignore beta files if updateChannel is release
            if(file.getChannel().equals(Channel.Beta) && updateChannel.equals(Channel.Release)) {
                files.add(file);
            }
        }

        if(files.size() == 0) {
            return null;
        }
        return getNewestVersion(files);
    }

    protected void downloadFile(FileDescription file) {
   		// Update folder
   		if (!Bukkit.getUpdateFolderFile().exists()) {
   			Bukkit.getUpdateFolderFile().mkdir();
   		}

   		// Build URL object
   		URL url;
   		try {
   			url = new URL(file.getDownloadUrl());
   		} catch (final MalformedURLException e) {
            OreBroadcast.sendLogMessage("URL malformed", ChatColor.RED);
            return;
   		}

   		// Determine file length
   		final int fileLength;
   		try {
   			fileLength = url.openConnection().getContentLength();
   		} catch (final IOException e) {
            OreBroadcast.sendLogMessage("Failed to open the connection", ChatColor.RED);
            return;
   		}

   		// Download the file
   		try (BufferedInputStream in = new BufferedInputStream(url.openStream());
   				FileOutputStream outputStream = new FileOutputStream(Bukkit.getUpdateFolderFile().getAbsolutePath() + File.separator + file)) {
   			final byte[] data = new byte[256];
   			int chunk;
   			long downloaded = 0, lastTime = 0;
   			while ((chunk = in.read(data, 0, 256)) != -1) {
   				downloaded += chunk;
                outputStream.write(data, 0, chunk);
   				if (lastTime < System.currentTimeMillis() - 100) {
   					OreBroadcast.sendLogMessage("Downloading... " + new DecimalFormat("#00.00").format(downloaded * 100.0 / fileLength) + '%', ChatColor.GRAY);
   					lastTime = System.currentTimeMillis();
   				}
   			}
            OreBroadcast.sendLogMessage("Download complete!", ChatColor.GREEN);
   		} catch (final IOException e) {
            OreBroadcast.sendLogMessage("Download failed...", ChatColor.RED);
   		}
   	}

}
