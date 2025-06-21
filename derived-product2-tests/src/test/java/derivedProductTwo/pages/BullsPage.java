package derivedProductTwo.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automationframework.utils.DriverUtils;

public class BullsPage {

	private WebDriver driver;
	private final DriverUtils driverUtils;

	public BullsPage(WebDriver driver, DriverUtils driverUtils) {
		this.driver = driver;
		this.driverUtils = driverUtils;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[contains(text(),'Copyright')]")
	private WebElement footer;

	@FindBy(xpath = "//ul[@data-testid='footer-list']/li/a")
	private List<WebElement> sectionsList;

	public void moveToFooterSection() {
		driverUtils.hoverElement(driver, footer);
	}

	public List<WebElement> getFooterSections() {
		return driverUtils.waitUntilVisibilityOfElements(sectionsList);
	}

}
