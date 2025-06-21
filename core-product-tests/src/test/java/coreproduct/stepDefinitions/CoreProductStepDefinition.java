package coreproduct.stepDefinitions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import automationframework.config.ConfigManager;
import automationframework.context.TestContext;
import automationframework.utils.DriverUtils;
import automationframework.utils.FileUtils;
import coreproduct.pages.HomePageWarriors;
import coreproduct.pages.NewsAndFeaturesPage;
import coreproduct.pages.ShopMensPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CoreProductStepDefinition {

	private final TestContext context;
	private DriverUtils driverUtils;
	private HomePageWarriors homePage;
	private ShopMensPage shopMensPage;
	private NewsAndFeaturesPage newsPage;
	private List<List<String>> allJacketDetails;

	public CoreProductStepDefinition(TestContext context) {
		this.context = context;
		this.driverUtils = new DriverUtils(context);
	}

	@Given("navigate to the CP home page URL {string}")
	public void navigate_to_the_cp_home_page_url(String urlStr) {
		String url = ConfigManager.get(urlStr);
		context.getDriver().get(url);
		url = "<a href=\"" + url + "\"> URL </a>";
		context.getReport().addInfoLogging("Navigated to Home Page: " + url);
		homePage = new HomePageWarriors(context.getDriver(), driverUtils);
		homePage.closeHomePageDialog();
		context.getReport().captureScreenshotAndAddToreport(context.getDriver(), "CoreProductHomePage");

	}

	@When("the user hovers on the Shop Menu item and clicks on {string}")
	public void the_user_hovers_on_the_shop_menu_item_and_clicks_on(String string) {
		String homeTab = context.getDriver().getWindowHandle();
		homePage.clickMensFromShopMenu();
		context.getReport().addPassLogging("Clicked on Shop > Men's Section");
		context.getReport().captureScreenshotAndAddToreport(context.getDriver(), "ShopMenuPage");
		for (String w : context.getDriver().getWindowHandles()) {
			if (!w.equals(homeTab)) {
				context.getDriver().switchTo().window(w);
				context.getReport().addInfoLogging("Driver switched to new Tab");
			}
		}

	}

	@When("extract the title, price, and top seller message for each jacket from all pages")
	public void extract_the_title_price_and_top_seller_message_for_each_jacket_from_all_pages() {

		shopMensPage = new ShopMensPage(context.getDriver(), driverUtils);

		shopMensPage.clickJacketsButton();
		context.getReport().addPassLogging("Selected jackets ");
		context.getReport().captureScreenshotAndAddToreport(context.getDriver(), "JacketsRadioButton");

		allJacketDetails = new ArrayList<List<String>>();
		driverUtils.waitUntilVisible(By.xpath(shopMensPage.pagination));
		int size = context.getDriver().findElements(By.xpath(shopMensPage.pagination)).size();

		for (int i = 1; i <= size; i++) {

			driverUtils
					.waitUntilVisible(
							By.xpath(shopMensPage.pagination_pageNo.replaceAll("PAGENUMBER", String.valueOf(i))))
					.click();

			context.getReport().addPassLogging("Clicked on Page : " + i);
			context.getReport().captureScreenshotAndAddToreport(context.getDriver(), "Pagination_" + i + "_");
			List<List<String>> productDetails = shopMensPage.getJacketDetailsByPage();
			allJacketDetails.addAll(productDetails);
			context.getReport().addPassLogging("Jackets from page" + i + " : " + productDetails.size());

		}

		context.getReport().addPassLogging("All Jackets from all pages : " + allJacketDetails.size());
//		for (List<String> details : allJacketDetails) {
//			System.out.println("Title: " + details.get(0) + ", Price: " + details.get(1));
//		}

	}

	@Then("store the extracted jacket details in a text file")
	public void store_the_extracted_jacket_details_in_a_text_file() {
		String filePath = System.getProperty("user.dir") + File.separator + "reports" + File.separator
				+ ConfigManager.get("outputFileName");
		FileUtils.storeTheExtractedJacketDetailsInATextFile(allJacketDetails, filePath);
		context.getReport()
				.addPassLogging("File has been created at path : " + "reports/" + ConfigManager.get("outputFileName"));

	}

	@Then("attach the text file to the test report")
	public void attach_the_text_file_to_the_test_report() {
		String filePath = System.getProperty("user.dir") + File.separator + "reports"
				+ ConfigManager.get("outputFileName");
		context.getReport().addFileToReport(filePath);

	}

	@When("the user hovers on the menu item and clicks on {string}")
	public void the_user_hovers_on_the_menu_item_and_clicks_on(String string) {
		driverUtils.setBrowserZoomTo(context.getDriver(), 80);
		homePage.clickOnNewsAndFeatures();
		context.getReport().addInfoLogging("Clicked on News & Features");
		context.getReport().captureScreenshotAndAddToreport(context.getDriver(), "News & Features");
	}

	@Then("count the total number of video feeds on the page")
	public void count_the_total_number_of_video_feeds_on_the_page() {
		newsPage = new NewsAndFeaturesPage(context.getDriver(), driverUtils);
		context.getReport().addPassLogging(
				"Number of Videos in News & Features Page : " + String.valueOf(newsPage.getVideos().size() - 1));
		;
	}

	@Then("count the number of video feeds labeled as {string}")
	public void count_the_number_of_video_feeds_labeled_as(String string) {
		List<WebElement> videos_time = newsPage.getVideosTime();
		int videosGreaterThan3Days = 0;
		for (int i = 1; i < videos_time.size(); i++) {
			WebElement eachVideoTime = videos_time.get(i);
			String time = eachVideoTime.getText().substring(0, 1);
			if (Integer.valueOf(time) >= 3) {
				videosGreaterThan3Days += 1;
			}
		}
		context.getReport()
				.addPassLogging("Number of Videos Greater than 3 Days are " + String.valueOf(videosGreaterThan3Days));
	}
}
