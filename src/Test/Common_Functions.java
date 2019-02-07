package Test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Common_Functions {
	public static boolean fail = false;
	public static WebDriver driver;

	public static void textBoxInput(WebElement tbObject, String tbName, String tbValue) throws Exception {

		try {
			tbObject.sendKeys(tbValue);
			logMessage(tbValue + " is entered into the " + tbName + " Textbox field.");
		} catch (Exception e) {
			logErrorMessage(tbValue + " is not entered into the " + tbName + " Textbox field.");
			logErrorMessage(e.getLocalizedMessage());
		}
	}

	public static void logMessage(String messageToLog) {

		try {
			System.out.println(messageToLog);
		} catch (Exception e) {
		}
	}

	// Fail and stop the current iteration

	public static void logErrorMessage(String messageToLog) throws Exception {
		fail = true;
		System.err.println(messageToLog);
		takeSnap(messageToLog);
		throw new Exception();
	}

	public static void takeSnap(String msg) throws IOException, InterruptedException {
		DateFormat dateFormat = new SimpleDateFormat("hh_mm_ss");
		Date date = new Date();
		if (msg.length() > 199) {
			msg = msg.substring(0, 100);
		}
		msg = msg.trim() + dateFormat.format(date);
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "//src//snaps//" + msg + ".png"));
		Thread.sleep(2000);
	}

	public static WebElement findObjectByxPath(WebDriver driver, String xpathVal, String objName) throws Exception {

		// Identifies the element & if its available it returns it
		try {
			WebElement webObj = driver.findElement(By.xpath(xpathVal));
			logMessage("The Object " + objName + " is available.");
			return webObj;
		} catch (Exception ex) {
			logErrorMessage("The Object " + objName + " is not available.");
			return null;
		}
	}

	public static void enterTextBoxValue(WebDriver driver, String attr, String attrVal, String objName,
			String ValuetoEnter, String... tagName) throws Exception {

		// identify the object
		WebElement webObj = findObject(driver, attr, attrVal, objName, tagName);

		if (webObj != null) {
			try {
				webObj.clear();
				webObj.sendKeys(ValuetoEnter);
				logMessage(ValuetoEnter + " is entered into " + objName);
			} catch (Exception e) {
				logErrorMessage("There is an Exception while entering " + ValuetoEnter + " in the " + objName);
			}
		}
	}

	public static WebElement findObject(WebDriver driver, String attr, String attrVal, String objName,
			String... tagName) throws Exception {

		switch (attr) {
		// identifies object by id value
		case "id":
			return findObjectById(driver, attrVal, objName);

		case "name":
			return findObjectByName(driver, attrVal, objName);

		// identifies object by id value
		case "xpath":
			return findObjectByxPath(driver, attrVal, objName);

		case "css":
			return findObjectByCSS(driver, attrVal, objName);

		case "className":
			return findObjectByclass(driver, attrVal, objName);

		case "class":
			return findObjectByclass(driver, attrVal, objName);

		case "linkText":
			return findObjectBylinkText(driver, attrVal, objName);
		default:
			logErrorMessage("The given attribute value " + attr
					+ " may be invalid or it is not yet programmed. Please verify common_Functions.findObject method");
			return null;
		}
	}

	public static WebElement findObjectByclass(WebDriver driver, String className, String objName) throws Exception {

		try {
			WebElement webObj = driver.findElement(By.className(className));
			return webObj;
		} catch (Exception ex) {
			logErrorMessage("The Object " + objName + " is not available.");
			return null;
		}
	}

	public static WebElement findObjectByCSS(WebDriver driver, String CSSValue, String objName) throws Exception {

		// Identifies the element & if its available it returns it
		try {
			WebElement webObj = driver.findElement(By.cssSelector(CSSValue));
			return webObj;
		} catch (Exception ex) {
			logErrorMessage("The Object " + objName + " is not available.");
			return null;
		}
	}

	public static WebElement findObjectByName(WebDriver driver, String idVal, String objName) throws Exception {

		// Identifies the element & if its available it returns it
		try {
			WebElement webObj = driver.findElement(By.name(idVal));
			logMessage("The Object " + objName + " is available.");
			return webObj;
		} catch (Exception ex) {
			logErrorMessage("The Object " + objName + " is not available.");
			return null;
		}
	}

	public static WebElement findObjectById(WebDriver driver, String idVal, String objName) throws Exception {

		// Identifies the element & if its available it returns it
		try {
			WebElement webObj = driver.findElement(By.id(idVal));
			logMessage("The Object " + objName + " is available.");
			return webObj;
		} catch (Exception ex) {
			logErrorMessage("The Object " + objName + " is not available.");
			return null;
		}
	}

	public static WebElement findObjectByTagName(WebDriver driver, String idVal, String objName) throws Exception {

		// Identifies the element & if its available it returns it
		try {
			WebElement webObj = driver.findElement(By.tagName(idVal));
			logMessage("The Object " + objName + " is available.");
			return webObj;
		} catch (Exception ex) {
			logErrorMessage("The Object " + objName + " is not available.");
			return null;
		}
	}

	/**
	 * This method identifies the object using "xpath" value
	 * <p>
	 * This method always returns the WebElement (i.e Object) if it is available. If
	 * the object is not available it returns null.
	 * 
	 * @param driver   (WebDriver) WebDriver object on which we need to search the
	 *                 element
	 * @param xpathVal (String) This is value of the "id" attribute of the object
	 * @param objName  (String) This is name of object & it will be used to log
	 *                 messages
	 * @return If the object is identified it return the object as WebElement else
	 *         it will return null
	 * @throws Exception
	 * 
	 */

	public static List<WebElement> findObjectsByxPath(WebDriver driver, String xpathVal, String objName)
			throws Exception {

		// Identifies the element & if its available it returns it
		try {
			List<WebElement> webObj = driver.findElements(By.xpath(xpathVal));
			logMessage("The Object " + objName + " is available.");
			return webObj;
		} catch (Exception ex) {
			logErrorMessage("The Object " + objName + " is not available.");
			return null;
		}
	}

	/**
	 * This method identifies the object using "xpath" value
	 * <p>
	 * This method always returns the WebElement (i.e Object) if it is available. If
	 * the object is not available it returns null.
	 * 
	 * @param driver   (WebDriver) WebDriver object on which we need to search the
	 *                 element
	 * @param xpathVal (String) This is value of the "id" attribute of the object
	 * @param objName  (String) This is name of object & it will be used to log
	 *                 messages
	 * @return If the object is identified it return the object as WebElement else
	 *         it will return null
	 * @throws Exception
	 * 
	 */

	public static WebElement findObjectBylinkText(WebDriver driver, String linkValue, String objName) throws Exception {

		// Identifies the element & if its available it returns it
		try {
			WebElement webObj = driver.findElement(By.linkText(linkValue));
			logMessage("The Object " + objName + " is available.");
			return webObj;
		} catch (Exception ex) {
			logErrorMessage("The Object " + objName + " is not available.");
			return null;
		}
	}

	public static WebElement findObjectByclassName(String Value, String objName) throws Exception {

		// Identifies the element & if its available it returns it
		try {
			WebElement webObj = driver.findElement(By.className(Value));
			logMessage("The Object " + objName + " is available.");
			return webObj;
		} catch (Exception ex) {
			logErrorMessage("The Object " + objName + " is not available.");
			return null;
		}
	}

	public static void clickOnWebElement(WebDriver driver, String attr, String attrVal, String objName,
			String... tagName) throws Exception {

		// identify the object
		WebElement webObj = findObject(driver, attr, attrVal, objName, tagName);

		if (webObj != null) {
			try {
				webObj.click();
				logMessage(objName + " is clicked.");
			} catch (Exception e) {
				logErrorMessage("There is an Exception while clicking " + objName);
			}
		}
	}

	public static void switchNextWindow() {

		// Switching from parent window to child window
		int i = 0;
		for (String Child_Window : driver.getWindowHandles()) {
			if (i > 0) {
				driver.switchTo().window(Child_Window);
			}
			i++;
		}
	}

	public static void quit() {

		try {
			driver.quit();
		} catch (Exception e) {
		}
	}

	public static void checkCheckBox(WebDriver driver, String attr, String attrVal, String objName, String... tagName)
			throws Exception {

		// identify the object
		WebElement chkBox = findObject(driver, attr, attrVal, objName, tagName);

		if (chkBox != null) {
			try {
				chkBox.click();
				logMessage(objName + "CheckBox is checked.");
			} catch (Exception e) {
				logErrorMessage("There is an Exception while checking " + objName + " Checkbox");
			}
		}
	}

	public static void waitForElement(String attr, String attrValue, int time) throws Exception {

		try {
			WebDriverWait wait = new WebDriverWait(driver, time);
			if (attr.equalsIgnoreCase("id")) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id(attrValue)));
			} else if (attr.equalsIgnoreCase("name")) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.name(attrValue)));
			} else if (attr.equalsIgnoreCase("xpath")) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(attrValue)));
			} else {
				Common_Functions.logMessage("Invalid attribute value passed. Use only id, name or xpath");
			}
		} catch (Exception e) {
			Common_Functions.logErrorMessage("Error while waiting for object. Object may not available.");
			e.printStackTrace();
		}
	}

	// *********************************************************************************************
	// Explicit Wait

	public static void Explicitwait_presenceOfElementLocated(int wait_time, String attr, String attr_value,
			String objname) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, wait_time);
		try {
			switch (attr) {

			case "xpath":
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(attr_value)));
				break;

			case "id":
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id(attr_value)));
				break;

			case "name":

				wait.until(ExpectedConditions.presenceOfElementLocated(By.name(attr_value)));
				break;

			case "class":

				wait.until(ExpectedConditions.presenceOfElementLocated(By.className(attr_value)));
				break;

			case "linktext":

				wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(attr_value)));
				break;

			default:
				Common_Functions.logErrorMessage("The given attribute value " + attr
						+ " may be invalid or it is not yet programmed. Please verify common_Functions.presenceOfElementLocated method");
				throw new Exception();

			}
			Common_Functions.logMessage("The object " + objname + " is present");
		} catch (Exception e) {
			Common_Functions.logErrorMessage("The object " + objname + " is not present");
			System.out.println(fail);
			throw new Exception();
		}

	}

	// ************************************************************************************************
	public static void Explicitwait_elementToBeClickable(int wait_time, String attr, String attr_value, String objname)
			throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, wait_time);
		try {
			switch (attr) {

			case "xpath":
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(attr_value)));
				break;

			case "id":
				wait.until(ExpectedConditions.elementToBeClickable(By.id(attr_value)));
				break;

			case "name":

				wait.until(ExpectedConditions.elementToBeClickable(By.name(attr_value)));
				break;

			case "class":

				wait.until(ExpectedConditions.elementToBeClickable(By.className(attr_value)));
				break;

			case "linktext":

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(attr_value)));
				break;

			default:
				Common_Functions.logErrorMessage("The given attribute value " + attr
						+ " may be invalid or it is not yet programmed. Please verify common_Functions.presenceOfElementLocated method");
				throw new Exception();

			}
			Common_Functions.logMessage("The object " + objname + " is present");
		} catch (Exception e) {
			Common_Functions.logErrorMessage("The object " + objname + " is not present");
			throw new Exception();
		}

	}

	// **************************************************************************************************

	public static void Explicitwait_visibilityOfElementLocated(int wait_time, String attr, String attr_value,
			String objname) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, wait_time);
		try {
			switch (attr) {

			case "xpath":
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(attr_value)));
				break;

			case "id":
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(attr_value)));
				break;

			case "name":

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(attr_value)));
				break;

			case "class":

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(attr_value)));
				break;

			case "linktext":

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(attr_value)));
				break;

			default:
				Common_Functions.logErrorMessage("The given attribute value " + attr
						+ " may be invalid or it is not yet programmed. Please verify common_Functions.presenceOfElementLocated method");
				throw new Exception();

			}
			Common_Functions.logMessage("The object " + objname + " is visible");
		} catch (Exception e) {
			Common_Functions.logErrorMessage("The object " + objname + " is not visible");
			throw new Exception();
		}

	}

	// *************************************************************************************************

	public static void Explicitwait_invisibilityOfElementLocated(int wait_time, String attr, String attr_value,
			String objname) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, wait_time);
		try {
			switch (attr) {

			case "xpath":
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(attr_value)));
				break;

			case "id":
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(attr_value)));
				break;

			case "name":

				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.name(attr_value)));
				break;

			case "class":

				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(attr_value)));
				break;

			default:
				Common_Functions.logErrorMessage("The given attribute value " + attr
						+ " may be invalid or it is not yet programmed. Please verify common_Functions.presenceOfElementLocated method");
				throw new Exception();

			}

		} catch (Exception e) {
			Common_Functions.logErrorMessage("The object " + objname + " is still not visible");
			throw new Exception();
		}

	}

	public static void res_InvokeBrowser(String browserName) throws Exception {
		if (browserName.equalsIgnoreCase("FireFox")) {

			try {
				driver = new FirefoxDriver();
				Common_Functions.logMessage("\nFirefox browser is invoked");
			} catch (Exception e) {
				Common_Functions.logErrorMessage("FireFox browser is not invoked");
			}
		} else if (browserName.equalsIgnoreCase("ie")) {
			try {
				System.setProperty("webdriver.ie.driver", "C:/Jar/browser/IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				Common_Functions.logMessage("\nInternet Explorer is invoked");
			} catch (Exception e) {
				Common_Functions.logErrorMessage("Internet Explorer browser is not invoked");
			}
		} else if (browserName.equalsIgnoreCase("Chrome")) {
			try {
				System.setProperty("webdriver.chrome.driver", "C:\\Jar\\browser\\chromedriver.exe");
				driver = new ChromeDriver();
				Common_Functions.logMessage("\nChrome is invoked");
			} catch (Exception e) {
				Common_Functions.logErrorMessage("\nChrome browser is not invoked");
			}
		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();

	}

}
