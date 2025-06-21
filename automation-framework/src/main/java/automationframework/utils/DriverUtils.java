package automationframework.utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import automationframework.config.ConfigManager;
import automationframework.context.TestContext;

/**
 * DriverUtils provides a set of reusable utility methods to interact with web
 * elements using WebDriver in a synchronized and reliable way.
 * <p>
 * It wraps WebDriverWait and ExpectedConditions to avoid flaky test behavior.
 */
public class DriverUtils {

	private WebDriverWait wait;

	public DriverUtils(TestContext context) {
		wait = new WebDriverWait(context.getDriver(), Duration.ofSeconds(ConfigManager.getInt("explicitWait")));
	}

	/**
	 * Returns the current WebDriverWait instance.
	 * 
	 * @return WebDriverWait object for fluent waiting operations
	 */
	public WebDriverWait getWait() {
		return wait;
	}

	/**
	 * Waits until the element is clickable and then performs a click.
	 * 
	 * @param element WebElement to be clicked
	 */
	public void click(WebElement element) {
		waitUntilClickable(element).click();
	}

	/**
	 * Waits for visibility of the element, clears it, and sends the provided text.
	 * 
	 * @param element WebElement input field
	 * @param text    Text to enter
	 */
	public void sendKeys(WebElement element, String text) {
		waitUntilVisible(element).clear();
		element.sendKeys(text);
	}

	/**
	 * Waits until the element is visible and returns its text content.
	 * 
	 * @param element WebElement to extract text from
	 * @return Visible text content of the element
	 */
	public String getText(WebElement element) {
		waitUntilVisible(element);
		return element.getText();
	}

	/**
	 * Waits until a specific element becomes visible.
	 * 
	 * @param element WebElement to wait for
	 * @return The same WebElement once it becomes visible
	 */
	public WebElement waitUntilVisible(WebElement element) {
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Waits until an element located by the provided locator becomes visible.
	 * 
	 * @param locator By locator to identify the element
	 * @return WebElement once visible
	 */
	public WebElement waitUntilVisible(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * Waits until the provided element is clickable.
	 * 
	 * @param element WebElement to be clicked
	 * @return WebElement once it's clickable
	 */
	public WebElement waitUntilClickable(WebElement element) {
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * Checks if the element exists in the DOM and is present using the given
	 * locator.
	 * 
	 * @param locator By locator of the element
	 * @return true if present within wait time; false otherwise
	 */
	public boolean isElementPresent(By locator) {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	/**
	 * Performs a mouse hover over the given element using Actions class.
	 * 
	 * @param element WebElement to hover over
	 */
	public void hoverElement(WebDriver driver, WebElement element) {
		new Actions(driver).moveToElement(element).perform();
	}

	/**
	 * Causes the thread to sleep for the given number of seconds.
	 * 
	 * @param seconds Duration to wait
	 */
	public void waitForSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000L);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Waits until all elements in the provided list are visible.
	 * 
	 * @param elements List of WebElements
	 * @return The same list after ensuring all elements are visible
	 */
	public List<WebElement> waitUntilVisibilityOfElements(List<WebElement> elements) {
		wait.until(ExpectedConditions.visibilityOfAllElements(elements));
		return elements;
	}

	/**
	 * Sets the browser zoom level to a specified percentage using JavaScript.
	 * 
	 * @param percentage Zoom percentage (e.g., 90, 100, 110)
	 */
	public void setBrowserZoomTo(WebDriver driver, int percentage) {
		((JavascriptExecutor) driver).executeScript("document.body.style.zoom='" + percentage + "%';");
	}

}
