package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LearnTestNG {

	WebDriver driver;
	String browser;
	String url;

	@BeforeClass
	public void readConfig() {
		// BufferedReader //InputStream //FileReader //Scanner - - Reading any kind of
		// files

		Properties prop = new Properties();

		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used: " + browser);
			url = prop.getProperty("url");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@BeforeMethod
	public void launchBrowser() {

		// https://www.selenium.dev/downloads/ to download browsers

		if (browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.get("https://www.techfios.com/billing/?ng=admin/");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	// @Test(priority=1)
	public void loginTest() throws InterruptedException {

		Assert.assertEquals(driver.getTitle(), "Login - iBilling", "Wrong Page!!!");

		WebElement USER_NAME_ELEMENT = driver.findElement(By.xpath("//input[@id='username']"));
		WebElement PASSWORD_ELEMENT = driver.findElement(By.xpath("//input[@id='password']"));
		WebElement SUBMIT_BUTTON_ELEMENT = driver.findElement(By.xpath("//button[@type='submit']"));

		// login data
		String loginID = "demo@techfios.com";
		String password = "abc123";

		USER_NAME_ELEMENT.sendKeys(loginID);
		PASSWORD_ELEMENT.sendKeys(password);
		SUBMIT_BUTTON_ELEMENT.click();

		WebElement DASHBOARD_TITLE_ELEMENT = driver.findElement(By.xpath("//h2[contains(text(), 'Dashboard')]"));
		Assert.assertEquals(DASHBOARD_TITLE_ELEMENT.getText(), "Dashboard", "Wrong Page!!!");
	}

	@Test(priority = 2)
	public void addCustomer() {
		Assert.assertEquals(driver.getTitle(), "Login - iBilling", "Wrong Page!!!");

		WebElement USER_NAME_ELEMENT = driver.findElement(By.xpath("//input[@id='username']"));
		WebElement PASSWORD_ELEMENT = driver.findElement(By.xpath("//input[@id='password']"));
		WebElement SUBMIT_BUTTON_ELEMENT = driver.findElement(By.xpath("//button[@type='submit']"));

		// login data
		String loginID = "demo@techfios.com";
		String password = "abc123";

		// Test data of Mock data
		String fullName = "Test Janurary";
		String companyName = "Google";
		String email = "techfios@gmail.com";
		String phone = "2145555555";

		USER_NAME_ELEMENT.sendKeys(loginID);
		PASSWORD_ELEMENT.sendKeys(password);
		SUBMIT_BUTTON_ELEMENT.click();

		WebElement DASHBOARD_TITLE_ELEMENT = driver.findElement(By.xpath("//h2[contains(text(), 'Dashboard')]"));
		Assert.assertEquals(DASHBOARD_TITLE_ELEMENT.getText(), "Dashboard", "Wrong Page!!!");

		WebElement CUSTOMER_ELEMENT = driver.findElement(By.xpath("//*[@id=\"side-menu\"]/li[3]/a/span[1]"));
		WebElement ADD_CUSTOMER_ELEMENT = driver.findElement(By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a"));

		CUSTOMER_ELEMENT.click();
		
		WebDriverWait wait = new WebDriverWait(driver,5);
		//wait.until(ExpectedConditions.visibilityOf(CUSTOMER_ELEMENT));
		
		ADD_CUSTOMER_ELEMENT.click();

		WebElement FULL_NAME_ELEMENT = driver.findElement(By.xpath("//input[@name= 'account']"));
		WebElement COMPANY_DROPDOWN_ELEMENT = driver.findElement(By.xpath("//select[@id= 'cid']"));
		WebElement EMAIL_ELEMENT = driver.findElement(By.xpath("//input[@id= 'email']"));
		WebElement PHONE_ELEMENT = driver.findElement(By.xpath("//input[@id= 'phone']"));
		WebElement ADDRESS_ELEMENT = driver.findElement(By.xpath("//input[@id= 'address']"));
		WebElement CITY_ELEMENT = driver.findElement(By.xpath("//input[@id= 'city']"));
		WebElement STATE_ELEMENT = driver.findElement(By.xpath("//input[@id= 'state']"));
		WebElement ZIP_ELEMENT = driver.findElement(By.xpath("//input[@id= 'zip']"));

		wait.until(ExpectedConditions.visibilityOf(FULL_NAME_ELEMENT));
		FULL_NAME_ELEMENT.sendKeys(fullName);
		
		// Dropdown
		Select sel = new Select(COMPANY_DROPDOWN_ELEMENT);
		sel.selectByVisibleText(companyName);
		
		//Generate Random Number
		Random rnd = new Random();
		int randomNum = rnd.nextInt(999);
		
		//fill email
		EMAIL_ELEMENT.sendKeys(randomNum+email);
		

	}

	//@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();

	}
}
