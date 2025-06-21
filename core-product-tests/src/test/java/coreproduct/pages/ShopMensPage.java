package coreproduct.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automationframework.utils.DriverUtils;

public class ShopMensPage {

	private final WebDriver driver;
	private DriverUtils driverUtils;

	public ShopMensPage(WebDriver driver, DriverUtils driverUtils) {
		this.driver = driver;
		this.driverUtils = driverUtils;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//span[@class='allDepartmentsBoxes-link-text' and text()='Jackets']")
	private WebElement jacketsRadioBtn;

	@FindBy(css = "div.product-card")
	private List<WebElement> products;

	@FindBy(css = "div.product-card-title a")
	private WebElement productTitle;

	@FindBy(xpath = "//span[@class='price']/span[@class='money-value'][1]")
	private WebElement productPrice;

	@FindBy(css = "div.product-card-badge span")
	private WebElement topSellerBadge;

	public String pagination = "//div[@class='product-grid-top-area']//a[@data-talos='linkSearchResultsPage']";

	public String pagination_pageNo = "//div[@class='product-grid-top-area']//a[@data-talos='linkSearchResultsPage'][text()='PAGENUMBER']";

	@FindBy(css = "div[data-talos='itemCount']")
	private WebElement itemCount;

	public WebElement getJacketsRadioBtn() {
		return jacketsRadioBtn;
	}

	public List<WebElement> getProducts() {
		return products;
	}

	// If needed for single product item usage
	public WebElement getProductTitle() {
		return productTitle;
	}

	public WebElement getProductPrice() {
		return productPrice;
	}

	public WebElement getTopSellerBadge() {
		return topSellerBadge;
	}

	public void clickJacketsButton() {
		try {
			driverUtils.click(jacketsRadioBtn);
		} catch (StaleElementReferenceException | TimeoutException e) {
			jacketsRadioBtn.click();
		}
	}

	public String getItemCount() {
		return driverUtils.getText(itemCount);
	}

	public List<List<String>> getJacketDetailsByPage() {
		List<List<String>> allDetails = new ArrayList<>();

		for (WebElement product : driverUtils.waitUntilVisibilityOfElements(products)) {
			try {
				String title = product.findElement(By.cssSelector("div.product-card-title a")).getText();
				String price = product.findElement(By.xpath(".//span[@class='price']/span[@class='money-value'][1]"))
						.getText();

				List<String> details = new ArrayList<>();
				details.add(title);
				details.add(price);
				allDetails.add(details);
			} catch (Exception e) {
				System.out.println("Error extracting product info: " + e.getMessage());
			}
		}

		return allDetails;
	}

}
