package automationframework.context;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import automationframework.report.ExtentReportManager;

public class TestContext {
	private WebDriver driver;
	private ExtentReportManager report;
	private final Map<String, Object> dataMap = new HashMap<>();

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public ExtentReportManager getReport() {
		return report;
	}

	public void setReport(ExtentReportManager report) {
		this.report = report;
	}

	// --- Scenario-level data ---
	public void set(String key, Object value) {
		dataMap.put(key, value);
	}

	public <T> T get(String key, Class<T> clazz) {
		return clazz.cast(dataMap.get(key));
	}

	public boolean contains(String key) {
		return dataMap.containsKey(key);
	}
}
