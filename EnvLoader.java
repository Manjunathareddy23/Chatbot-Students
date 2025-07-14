import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvLoader {
    private static final String ENV_FILE = ".env";
    private static Properties props = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream(ENV_FILE)) {
            props.load(fis);
        } catch (IOException e) {
            System.err.println("⚠️ Could not load .env file: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
