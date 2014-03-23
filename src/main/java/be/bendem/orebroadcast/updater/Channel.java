package be.bendem.orebroadcast.updater;

/**
 * @author bendem
 */
public enum Channel {
    Beta, Release;

    public boolean isNewer(Channel channel) {
        return this.ordinal() < channel.ordinal();
    }
}
