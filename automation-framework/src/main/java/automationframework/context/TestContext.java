package automationframework.context;

import org.openqa.selenium.WebDriver;

import automationframework.report.ExtentReportManager;

public class TestContext {
	private WebDriver driver;
	private ExtentReportManager report;

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
}
