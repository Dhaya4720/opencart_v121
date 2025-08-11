package testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;

import org.apache.logging.log4j.LogManager;//log4j
import org.apache.logging.log4j.Logger;//log4j
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

public class BaseClass {

	public Logger logger;// log4j
	public static WebDriver driver;
	public Properties pro;

	@BeforeClass(groups = {"Sanity","Regression","Master"})
	@Parameters({ "os", "browser" })
	public void setup(String os, String br) throws IOException {

		FileInputStream file = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources//config.properties");
		pro = new Properties();
		pro.load(file);

		logger = LogManager.getLogger(this.getClass());
		
		if(pro.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities=new DesiredCapabilities();
			
			//os
			if(os.equalsIgnoreCase("windows"))
			{
				capabilities.setPlatform(Platform.WIN10);
			}
			else if (os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.MAC);
			}
			else if (os.equalsIgnoreCase("linux"))
			{
				capabilities.setPlatform(Platform.LINUX);
			}
			else
			{
				System.out.println("No matching os");
				return;
			}
			
			//browser
			switch(br.toLowerCase())
			{
			case "chrome": capabilities.setBrowserName("chrome"); break;
			case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
			case "firefox": capabilities.setBrowserName("firefox"); break;
			default: System.out.println("No matching browser"); return;
			}
			
			driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
		}
		
		//Local
		if(pro.getProperty("execution_env").equalsIgnoreCase("local"))
		{
		switch (br.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		default:
			System.out.println("Invalid Browser Name");
			return;
		}
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(pro.getProperty("appURL"));
	}

	@AfterClass(groups = {"Sanity","Regression","Master"})
	public void teardown() {
		driver.quit();
	}

	public String randomString() {
		String genstring = RandomStringUtils.randomAlphabetic(5);
		return genstring;
	}

	public String randomNumber() {
		String genNum = RandomStringUtils.randomNumeric(5);
		return genNum;
	}

	public String randomAlphaNum() {
		String genstring = RandomStringUtils.randomNumeric(5);
		String genNum = RandomStringUtils.randomAlphabetic(5);
		return genstring + "@#" + genNum;
	}
	
	public String captureScreen(String tname) throws IOException{
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		TakesScreenshot takesscreenshot =(TakesScreenshot)driver; 
		File srcfile = takesscreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetfilepath = System.getProperty("user.dir")+"\\screenshots\\"+tname+"_"+timeStamp+".png";
		File Targetfile = new File(targetfilepath);
		
		srcfile.renameTo(Targetfile);
		return targetfilepath;
	}
	
}
