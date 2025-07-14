import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/** Simple loader for key‑value pairs inside `.env`. */
public class EnvLoader {
    private static final String ENV_FILE = ".env";
    private static final Properties props = new Properties();

    static {          // runs once when class is loaded
        try (FileInputStream fis = new FileInputStream(ENV_FILE)) {
            props.load(fis);
        } catch (IOException e) {
            System.err.println("⚠️  Could not load .env: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
