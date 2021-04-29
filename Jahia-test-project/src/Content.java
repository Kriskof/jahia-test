import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Content {
	
	public static void main(String[] args) {
		
		// set up browser property and create instance of driver
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Vincent\\Downloads\\browserdriver\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		
		// visit localhost port 8080 
		driver.get("http://localhost:8080/jahia/page-composer/default/en/sites/digitall/home.html");
		
		// maximize window
		driver.manage().window().maximize();
		
		// enter username
		WebElement username = driver.findElement(By.xpath("//input[@name='username']"));
		username.sendKeys("root");
		
		// enter password
		WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
		password.sendKeys("root");
		
		// click login
		WebElement loginBtn = driver.findElement(By.xpath("//button[@type='submit']"));
		loginBtn.click();
		
		// implicitly wait 10 seconds
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

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
		
		
	}
	
}
