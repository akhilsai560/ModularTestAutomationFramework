package automationframework.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

/**
 * FileUtils provides utility methods to handle file operations such as writing
 * extracted data to text/CSV files and cleaning directories.
 */
public class FileUtils {

	private static final Logger log = LogManager.getLogger(FileUtils.class);

	/**
	 * Stores extracted jacket details into a plain text file. Each entry consists
	 * of title and price, separated by a pipe delimiter.
	 *
	 * @param jacketDetails List of jacket details (each sublist contains title and
	 *                      price)
	 * @param filePath      Absolute or relative file path to save the text file
	 */
	public static void storeTheExtractedJacketDetailsInATextFile(List<List<String>> jacketDetails, String filePath) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			writer.write("Title  |  Price");
			writer.newLine();

			for (List<String> jacket : jacketDetails) {
				String line = String.join(" | ", jacket);
				writer.write(line);
				writer.newLine();
			}
			log.info("Details saved to file: {}", filePath);
		} catch (IOException e) {
			log.error("Error writing details to file: {}", e.getMessage());
		}
	}

	/**
	 * Cleans (deletes all files from) the specified report directory.
	 *
	 * @param path Relative path under user directory to clean (e.g.,
	 *             "reports/screenshots")
	 */
	public static void cleanReportsDirectory(String path) {
		log.info("User directory: {}", System.getProperty("user.dir"));
		File directory = new File(System.getProperty("user.dir"), path);

		if (directory.exists() && directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (files != null && files.length > 0) {
				for (File file : files) {
					if (file.isFile() && !file.delete()) {
						log.warn("Failed to delete file: {}", file.getAbsolutePath());
					}
				}
				log.info("Cleaned directory: {}", directory.getAbsolutePath());
			} else {
				log.info("Directory already empty: {}", directory.getAbsolutePath());
			}
		} else {
			log.warn("Directory not found or not a directory: {}", directory.getAbsolutePath());
		}
	}

	/**
	 * Exports all non-empty href links from a list of WebElements into a CSV file.
	 *
	 * @param elements List of WebElements (typically <a> tags)
	 * @param fileName CSV file path to store links (can be relative or absolute)
	 */
	public static void exportLinksToCSV(List<WebElement> elements, String fileName) {
		CSVPrinter printer = null;

		try {
			String filePath = Paths.get(fileName).toString();
			log.info("Exporting links to: {}", filePath);
			File file = new File(filePath);

			if (!file.exists()) {
				log.info("File does not exist. Creating new file...");
				file.createNewFile();
			}

			FileWriter out = new FileWriter(filePath);
			printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader("Index", "Link"));

			int index = 1;
			for (WebElement element : elements) {
				String href = element.getAttribute("href");
				if (href != null && !href.trim().isEmpty()) {
					printer.printRecord(index++, href.trim());
				}
			}

			log.info("Successfully exported links to CSV.");

		} catch (IOException e) {
			log.error("Failed to export links to CSV", e);
		} finally {
			try {
				if (printer != null) {
					printer.flush();
					log.info("CSV Printer flushed and closed.");
				}
			} catch (IOException e) {
				log.error("Error while closing CSV printer", e);
			}
		}
	}

	/**
	 * Detects and returns a set of duplicate href links from a list of WebElements.
	 *
	 * @param elements List of WebElements (typically anchor tags)
	 * @return Set of duplicate link URLs
	 */
	public static Set<String> findDuplicateLinks(List<WebElement> elements) {
		Set<String> seen = new HashSet<>();
		Set<String> duplicates = new HashSet<>();

		for (WebElement element : elements) {
			String href = element.getAttribute("href");
			if (href != null && !href.trim().isEmpty()) {
				if (!seen.add(href.trim())) {
					duplicates.add(href.trim());
				}
			}
		}

		if (!duplicates.isEmpty()) {
			log.warn("Duplicate links found: {}", duplicates);
		} else {
			log.info("No duplicate links found.");
		}

		return duplicates;
	}
}
