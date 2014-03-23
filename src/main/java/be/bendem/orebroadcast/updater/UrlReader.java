package be.bendem.orebroadcast.updater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author bendem
 */
public class UrlReader {

    protected final String url;
    protected String cache = null;

    public UrlReader(String url) {
        this.url = url;
    }

    public String read() {
        if(!resetCache()) {
            return cache;
        }

        URL url;
        try {
            url = new URL(this.url);
        } catch(MalformedURLException e) {
            e.printStackTrace();
            return setCache(null);
        }

        String response = null;
        try {
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = reader.readLine();
        } catch(IOException e) {}

        return setCache(response);
    }

    /**
     * Only set a cache if current is null or new is not
     *
     * @param cache New cache
     *
     * @return Cache state after update
     */
    public String setCache(String cache) {
        if(cache != null || this.cache == null) {
            this.cache = cache;
        }
        return this.cache;
    }

    protected boolean resetCache() {
        // TODO Check for a period of time before checking again
        if(cache == null/* || periodOfTimeHasPassed()*/) {
            cache = null;
            return true;
        }
        return false;
    }

}
