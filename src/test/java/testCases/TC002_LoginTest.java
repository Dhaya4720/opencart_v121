package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	
	@Test(groups={"Sanity","Master"})
	public void verify_login() {
		logger.info("*** Started Login Test ***");
		try
		{
		//HomePage
		HomePage Hp = new HomePage(driver);
		Hp.clickMyAccount();
		Hp.clickLogin();
		
		//LoginPage
		LoginPage Lp = new LoginPage(driver);
		Lp.setEmail(pro.getProperty("email"));//these are keys so that...
		Lp.setPassword(pro.getProperty("password"));
		Lp.clickLogin();
		
		//MyAccountPage
		MyAccountPage AP = new MyAccountPage(driver);
		boolean targetpage = AP.isMyAccountPageExists();
		Assert.assertTrue(targetpage);
		}
		catch (Exception e)
		{
			Assert.fail();
		}
		logger.info("*** Finished Login Test ***");
		
	}

}
