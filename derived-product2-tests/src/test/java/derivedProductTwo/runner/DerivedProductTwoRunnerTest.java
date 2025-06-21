package derivedProductTwo.runner;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/derivedProductTwo", glue = {
		"derivedProductTwo.stepDefinitions" }, plugin = { "pretty", "html:reports/cucumber-html-report.html",
				"json:reports/cucumber-report.json",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"timeline:test-output-thread" }, monochrome = true, dryRun = false)
public class DerivedProductTwoRunnerTest extends AbstractTestNGCucumberTests {

	@BeforeClass(alwaysRun = true)
	@Parameters("cucumber.filter.tags")
	public void setTags(String tags) {
		System.setProperty("cucumber.filter.tags", tags);
	}

	@Override
	@DataProvider(parallel = false)
	public Object[][] scenarios() {
		return super.scenarios();
	}
}
