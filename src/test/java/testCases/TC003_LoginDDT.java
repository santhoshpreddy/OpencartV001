package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;


/*Data is valid  - login success - test pass  - logout
Data is valid -- login failed - test fail

Data is invalid - login success - test fail  - logout
Data is invalid -- login failed - test pass
*/

public class TC003_LoginDDT extends BaseClass
{

	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups="datadriven")
	public void verify_loginDDT(String email, String password, String exp) throws InterruptedException
	{
		logger.info("**** Starting TC_003_LoginDDT *****");
		
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
			lp.setEmail(email);
			logger.info("Entered emailaddress..");
			lp.setPassword(password);
			logger.info("Entered password..");
			lp.clickLogin();
			logger.info("Clicked on login..");
			
			MyAccountPage map=new MyAccountPage(driver);
			boolean targetPage = map.isMyAccountPageExists();
			
			if(exp.equalsIgnoreCase("Valid"))
			{
				if(targetPage==true)
				{
					map.clickLogout();
					Assert.assertTrue(true);
				}
				else
				{
					Assert.assertTrue(false);
				}
			}
			
			if(exp.equalsIgnoreCase("Invalid"))
			{
				if(targetPage==true)
				{
					map.clickLogout();
					Assert.assertTrue(false);
				}
				else
				{
					Assert.assertTrue(true);
				}
			}

		} 
		catch (Exception e) {
			
			Assert.fail("An exception occurred: " + e.getMessage());	
		}
		Thread.sleep(3000);
		logger.info("**** Finished TC_003_LoginDDT *****");

	}
	
}








