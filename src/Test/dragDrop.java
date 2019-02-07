package Test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Test.Common_Functions;

public class dragDrop extends Common_Functions {
	public static void main(String[] args) throws Exception {
		//Invoke browser
		Common_Functions.res_InvokeBrowser("Chrome");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		
		try {
            //Go to url : http://quickfuseapps.com/
			driver.get("http://quickfuseapps.com/");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='link-create']")));
			//Click on Create an App
			Common_Functions.clickOnWebElement(driver, "xpath", ".//*[@id='link-create']", "Create An App");
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[contains(text(),'get started!')]")));
			Common_Functions.clickOnWebElement(driver, "xpath", ".//*[contains(text(),'get started!')]",
					"Let's get started");
			//Create a new page and give it a name
			Common_Functions.clickOnWebElement(driver, "xpath", ".//*[@id='add-page']", "New Page button");
			Common_Functions.enterTextBoxValue(driver, "xpath", "(.//*[@name='name'])[3]", "Page Name",
					"New Page Name");
			Common_Functions.clickOnWebElement(driver, "xpath", "(.//*[text()='Create'])[2]", "Create button");
			//Click on Messaging group
			Common_Functions.clickOnWebElement(driver, "xpath", ".//*[contains(text(),'Messaging')]",
					"Messaging group");
			Thread.sleep(2000);

			WebElement sms = driver
					.findElement(By.xpath("(.//*[text()='Send an SMS' and following::*[@title='Add']])[1]"));

			WebElement page = driver
					.findElement(By.xpath(".//*[@class='ui-page-panel ui-tabs-panel ui-widget-content ui-droppable']"));

			// Drag 'Send an SMS' component to the main page
			Actions act = new Actions(driver);
			act.dragAndDrop(sms, page).perform();
			Thread.sleep(2000);

			// Fill the details of Phone Number and Message text
			Common_Functions.enterTextBoxValue(driver, "xpath", ".//textarea[@name='phone_constant']", "Phone number",
					"2131243253435");
			Common_Functions.enterTextBoxValue(driver, "xpath", "(.//*[@name='message_phrase[]'])[1]", "Message Text",
					"Hello World");

			// Connect start node and Send an SMS receptor
			WebElement startNode = driver
					.findElement(By.xpath("((//*[text()='Start'])[last()]/following::*[@unselectable='on'])[1]"));
			WebElement smsRec = driver.findElement(By.xpath(
					"(.//*[text()='Send an SMS'])/following::div[@class='syn-receptor ui-droppable syn-receptor-north ui-draggable syn-receptor-draggable']"));
			act.dragAndDrop(startNode, smsRec).perform();
			Action connector1 = act.clickAndHold(startNode).moveToElement(smsRec).release(smsRec).build();
			connector1.perform();
			System.out.println("Start Node and SMS Receptor connected");

			// Drag 'Send an Email' component to the main page
			WebElement email = driver
					.findElement(By.xpath("(.//*[text()='Send an Email' and following::*[@title='Add']])[1]"));

			act.dragAndDrop(email, page).perform();
			System.out.println("Email menu dropped");
			Thread.sleep(2000);

			// Adjust the position of Email in main page
			WebElement dragInPage = driver
					.findElement(By.xpath("(.//*[@class='panel-icon ui-icon ui-icon-transferthick-e-w'])[2]"));
			act.clickAndHold(dragInPage).moveToElement(dragInPage, 130, 130).click().build().perform();
			System.out.println("Adjusted the email position in page");
			Thread.sleep(2000);
			// Fill the details in Email module
			Common_Functions.enterTextBoxValue(driver, "xpath", ".//*[@name='smtp_url']", "SMTP URL", "smtp.gmail.com");
			Common_Functions.enterTextBoxValue(driver, "xpath", ".//*[@name='port']", "Port Num", "465");
			Common_Functions.enterTextBoxValue(driver, "xpath", ".//*[@name='username']", "Username", "abc@gmail.com");
			Common_Functions.enterTextBoxValue(driver, "xpath", ".//*[@name='password']", "Password", "******");
			Common_Functions.enterTextBoxValue(driver, "xpath", ".//*[@name='from_constant']", "From address",
					"abc@gmail.com");
			Common_Functions.enterTextBoxValue(driver, "xpath", ".//*[@name='subject_constant']", "Subject",
					"SMS not sent");
			Common_Functions.enterTextBoxValue(driver, "xpath", ".//*[@name='to_constant']", "To address",
					"xyz@gmail.com");
			Common_Functions.enterTextBoxValue(driver, "xpath",
					"(.//*[@name='message_phrase[]' and preceding::*[@name='cc_constant']])[1]", "Message Text",
					"SMS to phone no 2131243253435 nt sent");

			// Connect SMS Not Sent Node and Email Receptor
			WebElement smsNotSentNode = driver
					.findElement(By.xpath("(.//*[text()='Not sent']//preceding-sibling::*[@unselectable='on'])[2]"));
			WebElement emailRec = driver
					.findElement(By.xpath("((.//*[text()='Not sent'])[last()]/following::*[@unselectable='on'])[1]"));

			act.dragAndDrop(smsNotSentNode, emailRec).perform();
			Action connector2 = act.clickAndHold(smsNotSentNode).moveToElement(emailRec).release(emailRec).build();
			connector2.perform();
			System.out.println("SMS Not Sent Node and Email Receptor connected");
			Thread.sleep(2000);
			// Click on Basic component
			driver.findElement(By.xpath(".//*[contains(text(),'Basic')]")).click();

			// Drag the Exit app to the main page
			WebElement hangUp = driver
					.findElement(By.xpath("(.//*[text()='Hang Up or Exit' and following::*[@title='Add']])[1]"));
			act.dragAndDrop(hangUp, page).perform();
			System.out.println("Exit App 1 dropped");

			// Adjust the position of Exit App
			WebElement dragInPage1 = driver.findElement(By.xpath("(.//*[text()='Exit App'])[1]"));
			act.clickAndHold(dragInPage1).moveToElement(dragInPage1, -100, -100).click().build().perform();
			System.out.println("Adjusted the exit app 1 position in page");

			// Connect SMS Sent Node and Exit App Receptor 1
			WebElement smsSentNode = driver
					.findElement(By.xpath("(.//*[text()='Sent']//preceding-sibling::*[@unselectable='on'])[1]"));
			WebElement exitAppRec = driver
					.findElement(By.xpath("(.//*[text()='Exit App']//following::*[@unselectable='on'])[1]"));

			act.dragAndDrop(smsSentNode, exitAppRec).perform();
			Action connector3 = act.clickAndHold(smsSentNode).moveToElement(exitAppRec).release(exitAppRec).build();
			connector3.perform();
			System.out.println("SMS Sent Node and Exit App Receptor 1 connected");

			// Drag the Exit app into main page
			WebElement hangUp2 = driver
					.findElement(By.xpath("(.//*[text()='Hang Up or Exit' and following::*[@title='Add']])[1]"));
			act.dragAndDrop(hangUp2, page).perform();
			System.out.println("Exit App 2 dropped");
			Thread.sleep(2000);
			// Adjust the exit app position in main page
			WebElement dragInPage2 = driver.findElement(By.xpath("(.//*[text()='Exit App'])[2]"));
			act.clickAndHold(dragInPage2).moveToElement(dragInPage2, -85, -105).click().build().perform();
			System.out.println("Adjusted the exit app 2 position in page");
			// Connect Email Sent Node and Exit App Receptor 2
			WebElement emailSentNode = driver
					.findElement(By.xpath("(.//*[text()='Sent']//preceding-sibling::*[@unselectable='on'])[3]"));
			WebElement exitAppRec2 = driver
					.findElement(By.xpath("(.//*[text()='Exit App']//following::*[@unselectable='on'])[5]"));

			act.dragAndDrop(emailSentNode, exitAppRec2).perform();
			Action connector4 = act.clickAndHold(emailSentNode).moveToElement(exitAppRec2).release(exitAppRec2).build();
			connector4.perform();
			System.out.println("Email Sent Node and Exit App Receptor 2 connected");

			// Drag the Exit app into main page
			WebElement hangUp3 = driver
					.findElement(By.xpath("(.//*[text()='Hang Up or Exit' and following::*[@title='Add']])[1]"));
			act.dragAndDrop(hangUp3, page).perform();
			System.out.println("Exit App 3 dropped");
			Thread.sleep(2000);
			// Adjust the exit app position in main page
			WebElement dragInPage3 = driver.findElement(By.xpath("(.//*[text()='Exit App'])[3]"));
			act.clickAndHold(dragInPage3).moveToElement(dragInPage3, 200, 250).click().build().perform();
			System.out.println("Adjusted the exit app 3 position in page");

			// Connect Email Not Sent Node and Exit App Receptor 3
			WebElement emailNotSentNode = driver
					.findElement(By.xpath("(.//*[text()='Sent']//preceding-sibling::*[@unselectable='on'])[4]"));
			WebElement exitAppRec3 = driver
					.findElement(By.xpath("(.//*[text()='Exit App']//following::*[@unselectable='on'])[9]"));
			act.dragAndDrop(emailNotSentNode, exitAppRec3).perform();
			Action connector5 = act.clickAndHold(emailNotSentNode).moveToElement(exitAppRec3).release(exitAppRec3)
					.build();
			connector5.perform();
			System.out.println("Email Not Sent Node and Exit App Receptor 3 connected");
			System.out.println("Scenario ----->PASS");
			//Just to see the completed scenario visually, I have added some wait
			Thread.sleep(15000);
		} catch (Exception e) {
			System.out.println("Scenario ----->FAIL");

		}
		driver.quit();
	}

}
