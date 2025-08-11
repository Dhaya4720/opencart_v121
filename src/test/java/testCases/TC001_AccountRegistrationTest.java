package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
		
	@Test(groups= {"Regression","Master"})
	public void verify_account_registration() {
		try
		{
		logger.info("**Starting TC001_AccountRegistrationTest**");
		
		HomePage Home = new HomePage(driver);
		Home.clickMyAccount();
		Home.clickRegister();
		logger.info("Clicked on Register Link...");
		
		AccountRegistrationPage acr = new AccountRegistrationPage(driver);
		logger.info("Providing Customer Details");
		acr.setFirstName(randomString().toUpperCase());
		acr.setLastName(randomString().toUpperCase());
		acr.setEmail(randomString()+"@gmail.com");
		acr.setTelephone(randomNumber());
		
		String Pass = randomAlphaNum();
		acr.setPassword(Pass);
		acr.setConfirmPassword(Pass);
		
		acr.setPrivacyPolicy();
		acr.clickContinue();
		
		logger.info("Validating Expected Messages");
		String Status = acr.getConfirmationMsg();
		
		Assert.assertEquals(Status, "Your Account Has Been Created!");
		}
		catch(Exception e)
		{
			logger.error("Test Failed");
			logger.debug("Debug logs");
			Assert.fail();
		}
		logger.info("**Finished TC001_AccountRegistrationTest**");
	}
	
}
