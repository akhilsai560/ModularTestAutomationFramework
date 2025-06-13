package automationframework.report;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

/**
 * ExtentReportManager is a utility class responsible for handling screenshots
 * and logging to ExtentReports in a Cucumber BDD framework.
 * <p>
 * It integrates with ExtentCucumberAdapter to log step-level information,
 * attach screenshots, and other custom logs to the report.
 */
public class ExtentReportManager {

	/** Logger for logging internal operations */
	private static Logger log = LogManager.getLogger(ExtentReportManager.class);

	/**
	 * Captures a screenshot and saves it under the
	 * 'target/extent-reports/screenshots' directory.
	 *
	 * @param driver         The WebDriver instance
	 * @param screenshotName Desired name of the screenshot file (without extension)
	 * @return Relative path to the saved screenshot (used for linking in reports)
	 */
	public String captureScreenshot(WebDriver driver, String screenshotName) {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		String relativePath = "screenshots/" + screenshotName + ".png";
		String fullPath = System.getProperty("user.dir") + "/target/extent-reports/" + relativePath;

		File destFile = new File(fullPath);
		destFile.getParentFile().mkdirs(); // ensure parent folders exist

		try {
			FileUtils.copyFile(src, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return relativePath;
	}

	/**
	 * Captures a screenshot and embeds it directly into the Extent report.
	 * <p>
	 * Screenshot is encoded to Base64 for inlining in the report.
	 *
	 * @param driver         The WebDriver instance
	 * @param screenshotName Label to show alongside the screenshot in the report
	 */
	public void captureScreenshotAndAddToreport(WebDriver driver, String screenshotName) {
		try {
			String screenshotDir = System.getProperty("user.dir") + "/reports/screenshots/";
			File dir = new File(screenshotDir);
			if (!dir.exists())
				dir.mkdirs();

			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String fileName = screenshotName + "_" + System.currentTimeMillis() + ".png";
			File destFile = new File(screenshotDir + fileName);
			FileUtils.copyFile(srcFile, destFile);

			byte[] fileContent = Files.readAllBytes(destFile.toPath());
			String encodedFile = Base64.getEncoder().encodeToString(fileContent);

			ExtentCucumberAdapter.getCurrentStep().info(screenshotName,
					MediaEntityBuilder.createScreenCaptureFromBase64String(encodedFile).build());

			log.info("📸 Screenshot captured: " + fileName);

		} catch (Exception e) {
			log.error("Failed to capture screenshot: ", e);
		}
	}

	/**
	 * Adds a clickable file reference to the report.
	 *
	 * @param filePath Absolute or relative path to the file being linked
	 */
	public void addFileToReport(String filePath) {
		ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,
				"<a href='file:" + filePath + "'>Click Here for Attached File</a>");
	}

	/**
	 * Logs a passed step/message into the report.
	 *
	 * @param msg Descriptive message to log as PASS
	 */
	public void addPassLogging(String msg) {
		ExtentCucumberAdapter.getCurrentStep().log(Status.PASS, msg);
	}

	/**
	 * Logs a failed step/message into the report.
	 *
	 * @param msg Descriptive message to log as FAIL
	 */
	public void addFailLogging(String msg) {
		ExtentCucumberAdapter.getCurrentStep().log(Status.FAIL, msg);
	}

	/**
	 * Logs an informational step/message into the report.
	 *
	 * @param msg Descriptive message to log as INFO
	 */
	public void addInfoLogging(String msg) {
		ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, msg);
	}
}
