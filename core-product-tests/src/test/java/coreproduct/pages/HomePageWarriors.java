package coreproduct.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automationframework.utils.DriverUtils;

public class HomePageWarriors {

	private WebDriver driver;
	private DriverUtils driverUtils;

	public HomePageWarriors(WebDriver driver, DriverUtils driverUtils) {
		this.driver = driver;
		this.driverUtils = driverUtils;
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
		driverUtils.click(shop_menu);
	}

	public void clickMensFromShopMenu() {
		driverUtils.waitUntilVisible(shop_menu);
		driverUtils.hoverElement(driver, shop_menu);
		driverUtils.click(shopMenu_mens);
	}

	public void closeHomePageDialog() {
		driverUtils.click(x_btn);
	}

	public void clickOnNewsAndFeatures() {
		driverUtils.waitUntilVisible(menuDots);
		driverUtils.hoverElement(driver, menuDots);
		driverUtils.waitUntilVisible(newsAndFeatures);
		driverUtils.click(newsAndFeatures);
	}
}
