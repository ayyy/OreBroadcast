package be.bendem.orebroadcast.updater;

/**
 * @author bendem
 */
public class Version {

    protected final String  version;
    protected final Channel channel;

    public Version(String version) {
        this.channel = version.contains("BETA") ? Channel.Beta : Channel.Release;
        this.version = normalize(version.replace("-BETA", ""));
    }

    private String normalize(String version) {
        return getMainVersion(version) + "." + getMinorVersion(version) + "." + getVersionRevision(version);
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
        return otherVersion.getVersion().compareTo(version) > 0;
    }

    public static Version nameToVersion(String name) {
        return new Version(name.replace("OreBroadcast-", "").replace(".jar", ""));
    }

    public String toString() {
        return version + "-" + channel.name();
    }

    protected static int getMainVersion(String version) {
        String[] parts = version.split("\\.");
        if(parts.length < 1) {
            return 0;
        }
        return Integer.parseInt(parts[0]);
    }

    protected static int getMinorVersion(String version) {
        String[] parts = version.split("\\.");
        if(parts.length < 2) {
            return 0;
        }
        return Integer.parseInt(parts[1]);
    }

    protected static int getVersionRevision(String version) {
        String[] parts = version.split("\\.");
        if(parts.length < 3) {
            return 0;
        }
        return Integer.parseInt(parts[2]);
    }

}
