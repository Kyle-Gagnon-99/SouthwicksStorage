/**
 * 
 */
package com.southwicksstorage.southwicksstorage.integration.selenium;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.southwicksstorage.southwicksstorage.SouthwicksStorageApplication;
import com.southwicksstorage.southwicksstorage.integration.selenium.configuration.CustomSeleniumTestWatcher;
import com.southwicksstorage.southwicksstorage.integration.selenium.configuration.H2SeleniumSetup;
import com.southwicksstorage.southwicksstorage.integration.selenium.configuration.SeleniumConstants;
import com.southwicksstorage.southwicksstorage.services.UserService;
import com.southwicksstorage.southwicksstorage.testconfigurations.SeleniumTestNameGenerator;

/**
 * @author kyle
 * Configure Selenium specific tests
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {SouthwicksStorageApplication.class, H2SeleniumSetup.class})
@TestPropertySource(locations = "classpath:application.properties")
@ActiveProfiles(value = "selenium-test")
@ExtendWith(CustomSeleniumTestWatcher.class)
@DisplayNameGeneration(SeleniumTestNameGenerator.class)
public abstract class BaseSeleniumTests {
	
	protected static Logger log = LoggerFactory.getLogger(BaseSeleniumTests.class);

	@LocalServerPort
	protected int localPort;
	
	@Autowired
	protected UserService userService;
	
	@Autowired
	protected PasswordEncoder bCryptPasswordEncoder;
	
	// Base only
	private static final String CHROME_DRIVER_STRING = "chromedriver.exe";
	
	// Used for all
	protected static WebDriver webDriver;
	protected static WebDriverWait wait;
	
	@BeforeEach
	public void setup() {
        String driverFile = findFile();
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(driverFile))
                .build();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox"); // Bypass OS security model, MUST BE THE VERY FIRST OPTION
        options.addArguments("--headless");
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.merge(capabilities);
        setWebDriver(new ChromeDriver(service, options));
        
        wait = new WebDriverWait(webDriver, 10);
	}
    
    @AfterEach
    public void tearDown() {
        if (getWebDriver() != null) {
        	webDriver.quit();
        }
    }
    
    /**
     * Find the webdriver executable in the resources
     * @return The string to the file
     */
    private static String findFile() {
        ClassLoader classLoader = BaseSeleniumTests.class.getClassLoader();
        File url = FileUtils.toFile(classLoader.getResource(CHROME_DRIVER_STRING));
        return url.getAbsolutePath();
    }
    
    /**
     * Get the localhost url for the specific test
     * @return The string containting the proper url
     */
    public String getLocalhost() {
    	return "http://localhost:" + localPort;
    }
    
    /**
     * Take a screenshot of the screen for the test
     * @param testName The file name for all the tests
     * @param testMethod The method name for the current test
     */
    public static void takeScreenshotOfTest(String testName, String testMethod) {
    	String fileToSaveAt = "C:\\Users\\kyle\\Documents\\SouthwicksStorageDevelopment\\TestingOutput\\" + testName + "\\" + testMethod + ".png";
    	
    	try {
    		Thread.sleep(2000);
    	} catch(Exception e) {
    		log.error("Failed to sleep thread");
    	}
    	
    	File screenshotFile = ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.FILE);
    	File saveSSTo = new File(fileToSaveAt);
    	
    	try {
			FileUtils.copyFile(screenshotFile, saveSSTo);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Failed to take screenshot");
		}
    }
    
    /**
     * Take a screenshot of the screen and draw rectangles around specific areas (1 or more)
     * @param testName The file name for all the tests
     * @param testMethod The method name for the current test
     * @param rectangle The list of rectangles to draw on the screenshot
     */
    public static void takeScreenshotOfTest(String testName, String testMethod, Throwable throwable) {
    	File fileToSaveAt = new File("C:\\Users\\kyle\\Documents\\SouthwicksStorageDevelopment\\TestingOutput\\SeleniumFailedTestScreenshot\\" 
    								+ testName + "\\" + testMethod + ".png");
    	fileToSaveAt.getParentFile().mkdirs();
    	
    	try {
    		Thread.sleep(2000);
    	} catch(Exception e) {
    		log.error("Failed to sleep thread");
    	}
    	
    	File screenshotFile = ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.FILE);
    	
    	BufferedImage image = null;
    	try {
			image = ImageIO.read(screenshotFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	
    	Graphics g = image.getGraphics();
    	g.setColor(SeleniumConstants.TEXT_COLOR);
    	g.setFont(new Font("SansSerif", Font.PLAIN, 18));
    	g.drawString("Failed because " + throwable.getMessage(), 10, 100);
    	g.dispose();
    	try {
			ImageIO.write(image, "png", fileToSaveAt);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public static WebDriver getWebDriver() {
		return webDriver;
	}

	public static void setWebDriver(WebDriver webDriver) {
		BaseSeleniumTests.webDriver = webDriver;
	}
}
