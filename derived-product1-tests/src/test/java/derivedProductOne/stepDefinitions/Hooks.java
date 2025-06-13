package derivedProductOne.stepDefinitions;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import automationframework.driver.DriverManager;
import automationframework.utils.FileUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;

public class Hooks {

	private static Logger log = LoggerFactory.getLogger(Hooks.class);

	@Before
	public void setUp(Scenario scenario) {
		DriverManager.initDriver();

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
