package automationframework.driver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import automationframework.config.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * DriverManager is responsible for managing the lifecycle of WebDriver
 * instances.
 * <p>
 * It supports local and remote execution of tests using Chrome, Firefox, and
 * Edge browsers. The browser type, headless mode, and implicit wait time are
 * configurable through the {@code config.properties} file.
 * <p>
 * Each thread has its own WebDriver instance, allowing parallel test execution.
 */
public class DriverManager {

	/** Thread-local variable to store WebDriver instances for each test thread */
	private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

	/** Logger instance for this class */
	private static final Logger log = LoggerFactory.getLogger(DriverManager.class);

	/**
	 * Initializes a WebDriver instance based on the configuration.
	 * <p>
	 * Supports the following values for {@code browser} in config.properties:
	 * <ul>
	 * <li>chrome</li>
	 * <li>firefox</li>
	 * <li>edge</li>
	 * <li>remote</li>
	 * </ul>
	 * <p>
	 * Remote execution requires {@code remoteUrl} and {@code remoteBrowser} to be
	 * defined. Headless mode and implicit wait are also configurable.
	 *
	 * @throws RuntimeException if the browser is unsupported or configuration is
	 *                          invalid
	 */
	public static void initDriver() {

		// If already initialized for this thread, do nothing
		if (driverThreadLocal.get() != null)
			return;

		String browser = ConfigManager.get("browser").toLowerCase();
		boolean headless = ConfigManager.getBoolean("headless");
		int implicitWait = ConfigManager.getInt("implicitWait");
		int explicitWait = ConfigManager.getInt("explicitWait");

		WebDriver driver;

		switch (browser) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			ChromeOptions chromeOptions = new ChromeOptions();
			if (headless)
				chromeOptions.addArguments("--headless=new");
			chromeOptions.addArguments("--start-maximized");
			chromeOptions.addArguments("disable-features=CookiesWithoutSameSiteMustBeSecure");
			chromeOptions.addArguments("disable-site-isolation-trials");
			chromeOptions.addArguments("--disable-third-party-cookies");
			driver = new ChromeDriver(chromeOptions);
			log.info("Invoked Chrome browser");
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			if (headless)
				firefoxOptions.addArguments("--headless");
			driver = new FirefoxDriver(firefoxOptions);
			driver.manage().window().maximize();
			log.info("Invoked Firefox browser");
			break;

		case "edge":
			WebDriverManager.edgedriver().setup();
			EdgeOptions edgeOptions = new EdgeOptions();
			if (headless)
				edgeOptions.addArguments("--headless=new");
			driver = new EdgeDriver(edgeOptions);
			driver.manage().window().maximize();
			log.info("Invoked Edge browser");
			break;

		case "remote":
			try {
				String remoteUrl = ConfigManager.get("remoteUrl");
				String remoteBrowser = ConfigManager.get("remoteBrowser");

				switch (remoteBrowser) {
				case "chrome":
					ChromeOptions remoteChrome = new ChromeOptions();
					if (headless)
						remoteChrome.addArguments("--headless=new");
					driver = new RemoteWebDriver(new URL(remoteUrl), remoteChrome);
					log.info("Invoked remote Chrome browser");
					break;

				case "firefox":
					FirefoxOptions remoteFirefox = new FirefoxOptions();
					if (headless)
						remoteFirefox.addArguments("--headless");
					driver = new RemoteWebDriver(new URL(remoteUrl), remoteFirefox);
					log.info("Invoked remote Firefox browser");
					break;

				case "edge":
					EdgeOptions remoteEdge = new EdgeOptions();
					if (headless)
						remoteEdge.addArguments("--headless=new");
					driver = new RemoteWebDriver(new URL(remoteUrl), remoteEdge);
					log.info("Invoked remote Edge browser");
					break;

				default:
					throw new RuntimeException("Unsupported remote browser: " + remoteBrowser);
				}
			} catch (MalformedURLException e) {
				throw new RuntimeException("Invalid Remote URL: " + e.getMessage());
			}
			break;

		default:
			throw new RuntimeException("Unsupported browser: " + browser);
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
		driverThreadLocal.set(driver);

		log.info("Browser Configuraion : ");
		log.info("browser : " + browser + ", implicitWait : " + implicitWait + ", explicitWait : " + explicitWait);

	}

	/**
	 * Returns the current thread's WebDriver instance.
	 *
	 * @return the active {@link WebDriver} instance
	 * @throws IllegalStateException if the driver is not initialized
	 */
	public static WebDriver getDriver() {
		WebDriver driver = driverThreadLocal.get();
		if (driver == null) {
			throw new IllegalStateException("WebDriver session has not been initialized or already quit.");
		}
		return driver;
	}

	/**
	 * Quits the WebDriver instance and removes it from the thread-local storage.
	 * Ensures proper cleanup of resources.
	 */
	public static void quitDriver() {
		if (driverThreadLocal.get() != null) {
			driverThreadLocal.get().quit();
			driverThreadLocal.remove();
		}
	}
}
