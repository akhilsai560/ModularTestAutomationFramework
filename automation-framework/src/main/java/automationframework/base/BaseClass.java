package automationframework.base;

import org.openqa.selenium.WebDriver;

import automationframework.driver.DriverManager;
import automationframework.report.ExtentReportManager;

public abstract class BaseClass {
	protected static WebDriver getDriver() {
		return DriverManager.getDriver();
	}

	protected ExtentReportManager getReport() {
		return new ExtentReportManager();
	}
}
