package be.bendem.orebroadcast.updater;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author bendem
 */
@RunWith(Parameterized.class)
public class VersionComparisonTest {

    private final String version1;
    private final String version2;
    private final boolean result;

    public VersionComparisonTest(String version1, String version2, boolean result) {
        this.version1 = version1;
        this.version2 = version2;
        this.result = result;
    }

    @Test
    public void testNewestVersion() {
        if(version1.equals(version2)) {
            return;
        }
        Version ver1 = new Version(version1);
        Version ver2 = new Version(version2);
        String expected = ver1.getVersion() + " should be a" + (result ? "n older" : " newer") + " version then " + ver2.getVersion() + ".";
        Assert.assertEquals(expected, result, ver1.isNewer(ver2));
        // Test the contrary
        expected = ver2.getVersion() + " should be a" + (!result ? "n older" : "newer") + " version then " + ver1.getVersion() + ".";
        Assert.assertEquals(expected, !result, ver2.isNewer(ver1));
    }

    @Test
    public void testEqualsVersion() {
        if(!version1.equals(version2)) {
            return;
        }
        Version ver1 = new Version(version1);
        Version ver2 = new Version(version2);
        String expected = version1 + " compared to " + version2 + " should be always false.";
        Assert.assertEquals(expected, false, ver1.isNewer(ver2));
        Assert.assertEquals(expected, false, ver2.isNewer(ver1));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<>();
        data.add(new Object[] {
            "1.1", "1.2", true
        });

        data.add(new Object[] {
            "1.2-BETA", "1.2-BETA", false
        });
        data.add(new Object[] {
            "1.2-BETA", "1.1-BETA", false
        });

        data.add(new Object[] {
            "1.2-BETA", "1.1", false
        });

        data.add(new Object[] {
            "1.1-BETA", "1.2", true
        });

        data.add(new Object[] {
            "1.2.1", "1.2", false
        });

        data.add(new Object[] {
            "1.1.1", "1.2", true
        });

        data.add(new Object[] {
            "1.1", "1.2.1", true
        });

        data.add(new Object[] {
            "1.2.2", "1.0.3-BETA", false
        });

        return data;
    }

}
