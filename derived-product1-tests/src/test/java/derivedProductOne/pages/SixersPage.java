package derivedProductOne.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import automationframework.utils.DriverUtils;

public class SixersPage {
	@FindBy(xpath = "//button[contains(@class, 'TileHero')]")
	private List<WebElement> heroTiles;

	public String currentSlide = "button[aria-selected='true']";

	public String progressBar = ".TileHeroStories_tileHeroStoriesButtonBarInner__dkmP1";

	public List<WebElement> getSlides() {
		return DriverUtils.waitUntilVisibilityOfElements(heroTiles);
	}

}
