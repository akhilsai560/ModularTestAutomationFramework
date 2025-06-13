package derivedProductTwo.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import automationframework.utils.DriverUtils;

public class BullsPage {

	@FindBy(xpath = "//div[contains(text(),'Copyright')]")
	private WebElement footer;

	@FindBy(xpath = "//ul[@data-testid='footer-list']/li/a")
	private List<WebElement> sectionsList;

	public void moveToFooterSection() {
		DriverUtils.moveToElement(footer);
	}

	public List<WebElement> getFooterSections() {
		return DriverUtils.waitUntilVisibilityOfElements(sectionsList);
	}

}
