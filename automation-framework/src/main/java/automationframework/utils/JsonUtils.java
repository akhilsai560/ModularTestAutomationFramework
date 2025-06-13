package automationframework.utils;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Utility class for loading JSON test data from input streams.
 */
public class JsonUtils {

	/**
	 * Loads JSON test data from an {@link InputStream} and returns it as a
	 * {@link JsonObject}.
	 *
	 * @param stream The input stream containing JSON content (typically loaded from
	 *               a file in test resources)
	 * @return Parsed {@link JsonObject} representing the test data
	 * @throws RuntimeException if the input stream is null or parsing fails
	 */
	public static JsonObject loadTestData(InputStream stream) {
		if (stream == null) {
			throw new RuntimeException("Input stream for JSON data is null.");
		}

		try {
			return JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
		} catch (Exception e) {
			throw new RuntimeException("Failed to parse JSON from input stream.", e);
		}
	}
}
