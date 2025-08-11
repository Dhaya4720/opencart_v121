package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

//data valid = login success --> test passed - logout
//data valid = login unsuccessful -->test failed

//data invalid = login success -->test Failed -logout
//data invalid = login unsuccessful --> test passed

public class TC003_LoginDDT extends BaseClass {

	@Test(dataProvider="LoginData",dataProviderClass = DataProviders.class,groups="DataProvider")//getting data provider from different package
	public void Verify_loginDDT(String email,String Pass, String Exp) {
		
		logger.info("*** Starting TC003_LoginDDT ***");
		
		try
		{
		// HomePage
		HomePage Hp = new HomePage(driver);
		Hp.clickMyAccount();
		Hp.clickLogin();

		// LoginPage
		LoginPage Lp = new LoginPage(driver);
		Lp.setEmail(email);// these are keys so that...
		Lp.setPassword(Pass);
		Lp.clickLogin();

		// MyAccountPage
		MyAccountPage AP = new MyAccountPage(driver);
		boolean targetpage = AP.isMyAccountPageExists();
		
		if(Exp.equalsIgnoreCase("Valid"))
		{
			if(targetpage == true) {
				AP.clickLogout();
				Assert.assertTrue(true);
				
			}
			else
			{
				Assert.assertTrue(false);
			}
		}
		if(Exp.equalsIgnoreCase("Invalid"))
		{
			if(targetpage == true) {
				AP.clickLogout();
				Assert.assertTrue(false);
				
			}
			else
			{
				Assert.assertTrue(true);
			}
		}
		}catch(Exception e) {
			Assert.fail();
		}
		logger.info("*** Finnishing TC003_LoginDDT ***");
	}
}
