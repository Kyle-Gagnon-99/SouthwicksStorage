/**
 * 
 */
package com.southwicksstorage.southwicksstorage.integration.selenium.testSelenium;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.southwicksstorage.southwicksstorage.integration.selenium.BaseSeleniumTests;

/**
 * @author kyle
 *
 */
public class TestSelenium {

	private static final String CHROME_DRIVER_STRING = "chromedriver.exe";
	private static WebDriver webDriver;
	private static Logger log = LoggerFactory.getLogger(TestSelenium.class);
	
	public static void main(String[] args) {
		setup();
		login();
		
		webDriver.findElement(By.xpath("//*[@id='sidebar']/div/ul/li[12]/a")).click();
		WebDriverWait wait = new WebDriverWait(webDriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.xpath("//*[@id='settings']/li[2]/a"))));
		webDriver.findElement(By.xpath("//*[@id='settings']/li[2]/a")).click();
		//webDriver.findElement(By.id("currentPassword")).sendKeys("Test123!");
		webDriver.findElement(By.tagName("form")).submit();
		
		List<WebElement> invalidFeedback = webDriver.findElements(By.xpath("//div[@class='invalid-feedback']"));
		
		for(WebElement element : invalidFeedback) {
			log.info(element.getAttribute("innerHTML"));
		}
		
		//webDriver.quit();
		
	}
	
	//*[@id="view"]/li[7]/a
	//*[@id="sidebar"]/div/ul/li[2]/a
	//*[@id="employeeTable"]/tbody/tr[1]/td[1]
	//html/body/div[2]/div/main/div/div[2]/div/div/div[2]/div[1]/div/p[1]/a
	//*[@id="settings"]/li[2]/a
	
    public static void login() {
        webDriver.get("http://localhost:8080");
        WebElement usernameField = webDriver.findElement(By.id("username"));
        WebElement passwordField = webDriver.findElement(By.id("password"));
        WebElement submitButton = webDriver.findElement(By.id("submitForm"));
        usernameField.sendKeys("kgagnon");
        passwordField.sendKeys("Test123!");
        submitButton.click();
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
    
	public static void setup() {
        String driverFile = findFile();
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(driverFile))
                .build();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox"); // Bypass OS security model, MUST BE THE VERY FIRST OPTION
        //options.addArguments("--headless");
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.merge(capabilities);
        webDriver = new ChromeDriver(service, options);
	}
	
    public static void takeScreenshotOfTest(String testName, String testMethod, Rectangle drawRect) {
    	String fileToSaveAt = "C:\\Users\\kyle\\Documents\\SouthwicksStorageDevelopment\\TestingOutput\\SeleniumFailedTestScreenshot\\" 
    							+ testName + "\\" + testMethod + "Failed.png";
    	
    	File screenshotFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
    	//File saveSSTo = new File(fileToSaveAt);
    	BufferedImage image = null;
    	try {
			image = ImageIO.read(screenshotFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	
    	Graphics g = image.getGraphics();
    	g.setColor(Color.RED);
    	g.drawRect(drawRect.x, drawRect.y, drawRect.width, drawRect.height);
    	g.dispose();
    	try {
			ImageIO.write(image, "png", new File(fileToSaveAt));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
}
