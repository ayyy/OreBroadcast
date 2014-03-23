package be.bendem.orebroadcast.updater;

import be.bendem.orebroadcast.OreBroadcast;
import org.json.simple.JSONObject;

/**
 * @author bendem
 */
public class FileDescription {

    protected static final String NAME_KEY         = "name";
    protected static final String DOWNLOAD_URL_KEY = "downloadUrl";
    protected static final String CHANNEL_KEY      = "releaseType";

    protected final String  name;
    protected final Version version;
    protected final Channel channel;
    protected final String  downloadUrl;

    public FileDescription(JSONObject json) {
        name = (String) json.get(NAME_KEY);
        version = Version.nameToVersion(name);
        channel = json.get(CHANNEL_KEY).equals("release") ? Channel.Release : Channel.Beta;
        downloadUrl = (String) json.get(DOWNLOAD_URL_KEY);
    }

    public String getName() {
        return name;
    }

    public Version getVersion() {
        return version;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

}
