package coreproduct.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NewsAndFeaturesPage {
	private WebDriver driver;

	public NewsAndFeaturesPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@data-testid='tile-icon']")
	private List<WebElement> videos;

	@FindBy(xpath = "//h3[text()='VIDEOS']/parent::div/following-sibling::div//time//span")
	private List<WebElement> videos_time;

	public List<WebElement> getVideos() {
		return videos;
	}

	public List<WebElement> getVideosTime() {
		return videos_time;
	}

}
