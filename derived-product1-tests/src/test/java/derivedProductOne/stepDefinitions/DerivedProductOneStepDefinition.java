package derivedProductOne.stepDefinitions;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

import automationframework.base.BaseClass;
import automationframework.config.ConfigManager;
import automationframework.utils.DriverUtils;
import automationframework.utils.JsonUtils;
import derivedProductOne.pages.SixersPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class DerivedProductOneStepDefinition extends BaseClass {

	private Logger log = LoggerFactory.getLogger(DerivedProductOneStepDefinition.class);

	private SixersPage dp1Page = PageFactory.initElements(getDriver(), SixersPage.class);;

	@Given("the user navigates to the DP1 home page {string}")
	public void the_user_navigates_to_the_dp1_home_page(String dp1Url) {
		String url = ConfigManager.get(dp1Url);
		getDriver().get(url);
		url = "<a href=\"" + url + "\"> URL </a>";
		getReport().addInfoLogging("Navigated to DP1 Home page: " + url);
		getReport().captureScreenshotAndAddToreport(getDriver(), "DP1HomePage");
	}

	@Then("count the number of slides present below the {string} menu")
	public void count_the_number_of_slides_present_below_the_menu(String string) {
		getReport().addPassLogging(
				"Number of slides present below the Tickets Menu : " + String.valueOf(dp1Page.getSlides().size()));
		getReport().captureScreenshotAndAddToreport(getDriver(), "SlidesCount");
	}

	@Then("compare the title of all slides with title from input data")
	public void compare_the_title_of_all_slides_with_title_from_input_data() {
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream(ConfigManager.get("inputJson"));
			JsonObject json = JsonUtils.loadTestData(is);

			StringBuilder table = new StringBuilder();

			table.append("<table border='1' style='border-collapse: collapse; width: 100%;'>");
			table.append("<tr>").append("<th>Slide#</th>").append("<th>Expected Title</th>")
					.append("<th>Actual Title</th>").append("</tr>");

			for (int i = 0; i < dp1Page.getSlides().size(); i++) {
				String actualTitle = dp1Page.getSlides().get(i).findElement(By.xpath("./div")).getText();
				String expTitle = json.get("slide" + (i + 1) + "title").getAsString();

				table.append("<tr>");
				table.append("<td>").append(i + 1).append("</td>");
				table.append("<td>").append(expTitle).append("</td>");

				if (actualTitle.equals(expTitle)) {
					table.append("<td>").append(actualTitle).append("</td>");
				} else {
					table.append("<td><span style='color:red;'>").append(actualTitle).append("</span></td>");
				}

				table.append("</tr>");
			}

			table.append("</table>");
			getReport().addInfoLogging(table.toString());

		} catch (

		Exception e) {
			log.error(e.getMessage());
		}
	}

	@Then("validate each slide is playing for at least {string} seconds")
	public void validate_each_slide_is_playing_for_at_least_seconds(String inputTime) {

		List<WebElement> slides = dp1Page.getSlides();

		List<Long> slideTimes = new ArrayList<Long>();

		for (WebElement slide : slides) {

			// Wait until the slide becomes active
			DriverUtils.getWait().until(d -> slide.getAttribute("aria-selected").equals("true"));
			long startTime = System.currentTimeMillis();

			// Wait until the slide is no longer active
			DriverUtils.getWait().until(d -> slide.getAttribute("aria-selected").equals("false"));
			long endTime = System.currentTimeMillis();

			long time = (endTime - startTime) / 1000; // Return in second

			slideTimes.add(time);
		}
		double avgTime = slideTimes.stream().mapToLong(Long::longValue).average().orElse(0);
		getReport().addInfoLogging("Each slide is playing for approximately " + avgTime + " seconds");

	}

}
