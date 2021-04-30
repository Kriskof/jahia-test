import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import objectRepository.loginPage;

public class ContentTest {
	
	static WebDriver driver = null;
	
	@BeforeClass
	public void login()
	{

		// set up browser property and create instance of driver
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Vincent\\Downloads\\browserdriver\\chromedriver.exe");
		driver = new ChromeDriver();

		// visit localhost port 8080 
		driver.get("http://localhost:8080/jahia/page-composer/default/en/sites/digitall/home.html");
		
		// maximize window
		driver.manage().window().maximize();
		
		// create an instance and login
		loginPage lP = new loginPage(driver);
		lP.loginToDigitall("root", "root");
		
		// implicitly wait 10 seconds for page to load
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}
	
	@Test
	public void publishRichText() {		
		
		/* Notes: 
		 * - create object repository for other pages 
		 * - create data provider such as JSON 
		 * */
		
		// switch to Tabs iframe
		driver.switchTo().frame(driver.findElement(By.id("page-composer-frame")));
		
		// wait for aboutLink element before timing out
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement aboutLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='JahiaGxtPagesTab']/descendant::span[contains(text(),'About')]")));
		
		// aboutLink element not getting clicked due to JavaScript or AJAX calls present
		Actions actions = new Actions(driver);
		actions.moveToElement(aboutLink).click().build().perform();
		
		// switch to Main Components iframe
		driver.switchTo().frame(driver.findElement(By.id("x-auto-72")));
		
		// click Any content button
		WebElement anyContectBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Any content')][1]")));
		anyContectBtn.click();
		
		// switch to Content Type iframe
		driver.switchTo().defaultContent();
		
		// click Content:Basic link
		WebElement contentBasicLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Content:Basic')]")));
		contentBasicLink.click();
		
		// click Rich Text link
		WebElement richTextLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Rich text')]")));
		richTextLink.click();
		
		// click Create button when not disabled
		WebElement createBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[not(@disabled)]/descendant::span[contains(text(),'CREATE')]")));
		createBtn.click();
		
		// switch to Rich Text iframe
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@aria-describedby='cke_75']")));
		
		// enter text
		WebElement richTextBody = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//body[@contenteditable='true']")));
		richTextBody.sendKeys("Hello, my name is Vincent.");
		
		// switch to Main Component iframe
		driver.switchTo().defaultContent();
		
		// click Save button
		WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button/descendant::span[contains(text(),'SAVE')]")));
		saveBtn.click();
		
		// click Publish Now button
		WebElement publishNowBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button/descendant::span[contains(text(),'PUBLISH NOW')]")));
		publishNowBtn.click();
		
		// assert Rich Text was published
		WebElement publishedLive = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@title,'Published')]/descendant::span[contains(text(),'Live')]")));
		Assert.assertEquals(true, publishedLive.isDisplayed());
		
		// navigate to content
		driver.navigate().to("http://localhost:8080/sites/digitall/home/about.html");
		
		// get last published content
		WebElement getLastPublishedContent = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='wrapper']/descendant::p[1]")));
		Assert.assertEquals(getLastPublishedContent.getText(), "Hello, my name is Vincent.");
		
	}
	
	@AfterClass
	public void tearDown(){
		
		// close driver
		driver.quit();
		
	}
	
}
