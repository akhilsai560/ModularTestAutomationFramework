package derivedProductOne.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automationframework.utils.DriverUtils;

public class SixersPage {
	private final WebDriver driver;
	private DriverUtils driverUtils;

	public SixersPage(WebDriver driver, DriverUtils driverUtils) {
		this.driver = driver;
		this.driverUtils = driverUtils;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[contains(@class, 'TileHero')]")
	private List<WebElement> heroTiles;

	public String currentSlide = "button[aria-selected='true']";

	public String progressBar = ".TileHeroStories_tileHeroStoriesButtonBarInner__dkmP1";

	public List<WebElement> getSlides() {
		return driverUtils.waitUntilVisibilityOfElements(heroTiles);
	}

}
