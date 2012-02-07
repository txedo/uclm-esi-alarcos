package persistence;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;

/**
 * Utility class that allows transparent reading of files from the current
 * working directory or from the classpath.
 * 
 * @author Pepijn Van Eeckhoudt
 */
public class ResourceRetriever {
    public static URL getResource(final String filename) throws IOException {
        // Try to load resource from jar
        URL url = ClassLoader.getSystemResource(filename);
        // If not found in jar, then load from disk
        if (url == null) {
            return new URL("file", "localhost", filename);
        } else {
            return url;
        }
    }

    public static InputStream getResourceAsStream(final String filename)
            throws IOException {
        // Try to load resource from jar
        InputStream stream = ClassLoader.getSystemResourceAsStream(filename);
        if (stream == null)
            stream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(filename);
        // If not found in jar, then load from disk
        if (stream == null) {
            stream = new FileInputStream(filename);
        }
        return stream;
    }

    public static byte[] getResourceAsByteArray(final String filename)
            throws IOException {
        InputStream stream = getResourceAsStream(filename);
        return IOUtils.toByteArray(stream);
    }

    public static InputStream toInputStream(byte[] data) {
        return new ByteArrayInputStream(data);
    }

    public static boolean isResourceAvailable(String filename)
            throws IOException {
        boolean available = false;
        if (getResourceAsStream(filename) != null)
            available = true;
        return available;
    }
}
