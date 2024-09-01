package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass{
	
	@Test(groups={"Sanity","Master"})
	void verify_Login()
	{
		logger.info("**** Starting TC002_LoginTest****");
		try
		{
			
			
			//Home Page
			HomePage hp=new HomePage(driver);
			hp.clickMyAccount();
			logger.info("Clicked on MyAccount..");
			hp.clickLogin();
			logger.info("Clicked on Login..");
			
			//Login Page
			LoginPage lp=new LoginPage(driver);
			lp.setEmail(pr.getProperty("email"));
			logger.info("Entered emailaddress..");
			lp.setPassword(pr.getProperty("password"));
			logger.info("Entered password..");
			lp.clickLogin();
			logger.info("Clicked on login..");
			//Myaccount page
			
			MyAccountPage map=new MyAccountPage(driver);
			boolean targetPage = map.isMyAccountPageExists();
			Assert.assertTrue(targetPage);
			//Assert.assertEquals(targetPage, true);
			logger.info("Verified MyAccount page..");
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("**** Finished TC002_LoginTest****");

	}
	
}
