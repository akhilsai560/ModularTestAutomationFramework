package coreproduct.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automationframework.utils.DriverUtils;

public class HomePageWarriors {

	private WebDriver driver;

	public HomePageWarriors(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@role='dialog']//div[text()='x']")
	private WebElement x_btn;

	@FindBy(xpath = "//li[@class='menu-item']//span[text()='Shop']")
	private WebElement shop_menu;

	@FindBy(xpath = "//a[starts-with(text(),'Men')]")
	private WebElement shopMenu_mens;

	@FindBy(xpath = "//li[contains(@class, 'menu-item')]/a/span[text()='...']/..")
	private WebElement menuDots;

	@FindBy(xpath = "//li[contains(@class, 'menu-item')]//a[@title='News & Features']")
	private WebElement newsAndFeatures;

	public void clickShopMenu() {
		DriverUtils.click(shop_menu);
	}

	public void clickMensFromShopMenu() {
		DriverUtils.waitUntilVisible(shop_menu);
		DriverUtils.hoverElement(shop_menu);
		DriverUtils.click(shopMenu_mens);
	}

	public void closeHomePageDialog() {
		DriverUtils.click(x_btn);
	}

	public void clickOnNewsAndFeatures() {
		DriverUtils.waitUntilVisible(menuDots);
		DriverUtils.hoverElement(menuDots);
		DriverUtils.waitUntilVisible(newsAndFeatures);
		DriverUtils.click(newsAndFeatures);
	}
}
