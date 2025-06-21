package derivedProductTwo.stepDefinitions;

import java.io.File;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import automationframework.config.ConfigManager;
import automationframework.context.TestContext;
import automationframework.utils.DriverUtils;
import automationframework.utils.FileUtils;
import derivedProductTwo.pages.BullsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class DerivedProductTwoStepDefinition {

	private final TestContext context;
	private BullsPage dp2Page;
	private DriverUtils driverUtils;
	private Logger log = LoggerFactory.getLogger(DerivedProductTwoStepDefinition.class);

	public DerivedProductTwoStepDefinition(TestContext context) {
		this.context = context;
		driverUtils = new DriverUtils(context);

	}

	@Given("the user navigates to the DP2 home page {string}")
	public void the_user_navigates_to_the_dp2_home_page(String dp2Url) {
		String url = ConfigManager.get(dp2Url);
		context.getDriver().get(url);
		driverUtils.setBrowserZoomTo(context.getDriver(), 80);
		url = "<a href=\"" + url + "\"> URL </a>";
		context.getReport().addInfoLogging("Navigated to DP2 Home page: " + url);
		context.getReport().captureScreenshotAndAddToreport(context.getDriver(), "DP2HomePage");
	}

	@When("the user scroll down to the footer")
	public void the_user_scroll_down_to_the_footer() {
		dp2Page = new BullsPage(context.getDriver(), driverUtils);
		dp2Page.moveToFooterSection();
		context.getReport().addInfoLogging("Moved to Footer Section");
		context.getReport().captureScreenshotAndAddToreport(context.getDriver(), "FooterSection");
	}

	@When("export all the hyperlinks of the Footer links into a CSV file")
	public void export_all_the_hyperlinks_of_the_footer_links_into_a_csv_file() {
		String csvPath = System.getProperty("user.dir") + File.separator + "reports" + File.separator
				+ "section-links.csv";
		context.getReport().addInfoLogging("Number of links in Footer section : " + dp2Page.getFooterSections().size());
		FileUtils.exportLinksToCSV(dp2Page.getFooterSections(), csvPath);
		File file = new File(csvPath);
		context.getReport().addPassLogging(
				"All Links were exported to the csv :" + "reports" + File.separator + "section-links.csv");
		context.getReport().addFileToReport(csvPath);
	}

	@When("report if any duplicate hyperlinks are present")
	public void report_if_any_duplicate_hyperlinks_are_present() {

		Set<String> duplicates = FileUtils.findDuplicateLinks(dp2Page.getFooterSections());
		if (!duplicates.isEmpty()) {
			context.getReport().addInfoLogging("Duplicates Found, Below are the links : ");
			duplicates.forEach(link -> context.getReport().addInfoLogging(link));
		} else {
			context.getReport().addPassLogging("Duplicates not found");
		}
	}

}
