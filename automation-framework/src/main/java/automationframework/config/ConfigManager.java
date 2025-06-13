package automationframework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ConfigManager is responsible for loading and providing access to
 * configuration properties defined in the {@code config.properties} file
 * located in the resources directory.
 * 
 * <p>
 * This class supports retrieval of configuration values as {@code String},
 * {@code int}, and {@code boolean}.
 * 
 * <p>
 * The configuration is loaded statically when the class is first accessed.
 */
public class ConfigManager {

	/**
	 * Properties object holding all key-value pairs loaded from config.properties
	 */
	private static final Properties properties = new Properties();

	/** Logger instance for logging configuration loading status */
	private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);

	// Static block to load the configuration file only once at class loading
	static {
		try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties")) {
			if (input == null) {
				throw new RuntimeException("Unable to find config.properties");
			}
			properties.load(input);
			log.info("Properties file load success");
		} catch (IOException e) {
			throw new RuntimeException("Failed to load config.properties", e);
		}
	}

	/**
	 * Retrieves the value associated with the given key as a {@code String}.
	 *
	 * @param key the property key to look up
	 * @return the property value as a String, or {@code null} if the key does not
	 *         exist
	 */
	public static String get(String key) {
		return properties.getProperty(key);
	}

	/**
	 * Retrieves the value associated with the given key as an {@code int}.
	 *
	 * @param key the property key to look up
	 * @return the property value parsed as an int
	 * @throws NumberFormatException if the value is not a valid integer
	 * @throws NullPointerException  if the key is not present in the properties
	 */
	public static int getInt(String key) {
		return Integer.parseInt(properties.getProperty(key));
	}

	/**
	 * Retrieves the value associated with the given key as a {@code boolean}.
	 *
	 * <p>
	 * Accepts case-insensitive values such as "true", "false".
	 *
	 * @param key the property key to look up
	 * @return {@code true} if the value is "true" (ignoring case), otherwise
	 *         {@code false}
	 */
	public static boolean getBoolean(String key) {
		return Boolean.parseBoolean(properties.getProperty(key));
	}

}
