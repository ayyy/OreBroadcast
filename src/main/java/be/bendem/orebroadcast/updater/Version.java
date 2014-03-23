package be.bendem.orebroadcast.updater;

/**
 * @author bendem
 */
public class Version {

    protected final String  version;
    protected final Channel channel;

    public Version(String version) {
        this.channel = version.contains("BETA") ? Channel.Beta : Channel.Release;
        this.version = version.replace("-BETA", "");
    }

    public String getVersion() {
        return version;
    }

    public Channel getChannel() {
        return channel;
    }

    // TODO Refactor this
    public boolean isNewer(Version otherVersion) {
        // Same version, check channel
        if(version.equals(otherVersion.getVersion())) {
            return channel.isNewer(otherVersion.getChannel());
        }
        // Not the same version, the newest win
        return version.compareTo(otherVersion.getVersion()) < 0;
    }

    public static Version nameToVersion(String name) {
        return new Version(name.replace("OreBroadcast-", "").replace(".jar", ""));
    }

}
