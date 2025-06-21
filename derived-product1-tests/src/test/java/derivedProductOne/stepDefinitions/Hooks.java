package derivedProductOne.stepDefinitions;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import automationframework.context.TestContext;
import automationframework.driver.DriverManager;
import automationframework.report.ExtentReportManager;
import automationframework.utils.FileUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;

public class Hooks {

	private final TestContext context;
	private static Logger log = LoggerFactory.getLogger(Hooks.class);

	public Hooks(TestContext context) {
		this.context = context;
	}

	@Before
	public void setUp(Scenario scenario) {
		DriverManager.initDriver();
		context.setDriver(DriverManager.getDriver());
		context.setReport(new ExtentReportManager());

	}

	@BeforeAll
	public static void cleanScreenshotFolder() {
		FileUtils.cleanReportsDirectory("reports" + File.separator + "screenshots");
	}

	@After
	public void tearDown(Scenario scenario) {
		if (DriverManager.getDriver() != null) {
			try {
				DriverManager.quitDriver();
			} catch (Exception e) {
				log.error("Error while quitting driver: {}", e.getMessage());
			}
		}
	}
}
