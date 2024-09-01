package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.Logger;
 

public class BaseClass {

	public static WebDriver driver;
	public Logger logger;
	public Properties pr;
	@BeforeClass(groups= {"Sanity","Regression","Master"})
	@Parameters({"os","browser"})
	public void setUp(String os,String browser) throws IOException
	{
		
		//Loading config properties file
		FileReader file=new FileReader("./src//test//resources//config.properties");
		pr=new Properties();
		pr.load(file);
		
		logger=LogManager.getLogger(this.getClass());  //lOG4J2
		
		//Selenium grid
		
		if(pr.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
				DesiredCapabilities capabalities=new DesiredCapabilities();
				
				//OS
				if(os.equalsIgnoreCase("windows"))
				{
					capabalities.setPlatform(Platform.WIN11);
				}
				else if(os.equalsIgnoreCase("mac"))
				{
					capabalities.setPlatform(Platform.MAC);
				}
				else if(os.equalsIgnoreCase("linux"))
				{
					capabalities.setPlatform(Platform.LINUX);
				}
				else
				{
					System.out.println("No matching os");
					return;
				}
				//Browser
				
				switch(browser.toLowerCase())
				{
				case "chrome":capabalities.setBrowserName("chrome");
				break;
				case "edge":capabalities.setBrowserName("MicrosoftEdge");
				break;
				case "firefox":capabalities.setBrowserName("firefox");
				break;
				default: System.out.println("No Matching browser");
				}
				
				driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabalities);
				
		}
		
		if(pr.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch (browser) {
			case "chrome":driver=new ChromeDriver();
			break;
			case "edge":driver=new EdgeDriver();
			break;
			case "firefox":driver=new FirefoxDriver();
			break;
			default: System.out.println("Invalid browser name..");
			return;
			}
		}
		
		
		
		
		logger=LogManager.getLogger(this.getClass());
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(pr.getProperty("url"));
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups= {"Sanity","Regression","Master"})
	public void tearDown()
	{
		driver.quit();
	}
	
	public String randomString()
	{
		String generatestring = RandomStringUtils.randomAlphabetic(5);
		return generatestring;
	}
	public String randomNumber()
	{
		String generatenumber = RandomStringUtils.randomNumeric(10);
		return generatenumber;
	}
	
	public String randomAlphanumeric()
	{
		String generatestring = RandomStringUtils.randomAlphanumeric(3);
		String generatenumber = RandomStringUtils.randomNumeric(3);
		return (generatestring+"@"+generatenumber);
	}
	
	public String captureScreen(String tname) throws IOException {

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetFile=new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
			
		return targetFilePath;

	}

}
