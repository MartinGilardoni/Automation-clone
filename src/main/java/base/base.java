package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;

public class base {

    public WebDriver driver;
    public Properties prop;

    public WebDriver initializeDriver() throws IOException {
        prop = new Properties();
        String pathProject = System.getProperty("user.dir");
        FileInputStream fis = new FileInputStream(pathProject + "/src/main/java/config/data.properties");

        prop.load(fis);
        String browserName = prop.getProperty("browser");
        System.out.println(browserName);

        if (browserName.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\webdriver\\chromedriver.exe");
            driver = new ChromeDriver();
        } else if (browserName.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", "C:\\webdriver\\geckodriver.exe");
            driver = new FirefoxDriver();
            driver.manage().window().setSize(new Dimension(740, 650));
        } else if (browserName.equals("androide")) {
            DesiredCapabilities capa = new DesiredCapabilities();
            capa.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_3a_API_27");
            capa.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
            new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capa);
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    public static void Android(String[] args) throws MalformedURLException {
        DesiredCapabilities capa = new DesiredCapabilities();
        capa.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_3a_API_27");
        capa.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
        new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capa);
    }

    public String getScreenShotPath(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destinationFile = System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
        FileUtils.copyFile(source, new File(destinationFile));
        return destinationFile;
    }

    public WebDriver back() {
        driver.navigate().back();
        return driver;
    }

    public WebDriver forward() {
        driver.navigate().forward();
        return driver;
    }

    //FindElementLocator
    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    //FindElementLocator
    public WebElement Assert(By locator) {
        return driver.findElement(locator);
    }

    public void verify(By locator) {
        WebElement element = driver.findElement(locator);
        String str = element.getText();
        System.out.println(str);
    }

    public WebElement Assertions(By locator) {
        WebElement element = driver.findElement(locator);
        element.getText();
        System.out.println("Printing " + element);
        return element;
    }

    public WebElement Waitfor(By by) {
        WebElement result = null;
        long maxTime = 5 * 1000; // time in milliseconds
        long timeSlice = 250;
        long elapsedTime = 0;

        do {
            try {
                Thread.sleep(timeSlice);
                elapsedTime += timeSlice;
                result = driver.findElement(by);
            } catch (Exception e) {
            }
        } while (result == null && elapsedTime < maxTime);

        return result;
    }

    //Element List
    public List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    //Force the action of Click Method
    public boolean retryingFindClick(By by) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 2) {
            try {
                driver.findElement(by).click();
                result = true;
                break;
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
    }

    //Force the action of Drop Method
    public boolean Dropforce(By by) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 2) {
            try {
                WebElement element = driver.findElement(by);
                Actions builder = new Actions(driver);
                builder.moveToElement(element).release().perform();
                result = true;
                break;
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
    }

    //Force the action of Drag Method
    public boolean Dragforce(By by) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 2) {
            try {
                WebElement element = driver.findElement(by);
                Actions builder = new Actions(driver);
                builder.moveToElement(element).clickAndHold().perform();
                result = true;
                break;
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
    }

    //Force the action of MovetoElement Method
    public boolean Moveforce(By by) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 2) {
            try {
                WebElement element = driver.findElement(by);
                Actions builder = new Actions(driver);
                builder.moveToElement(element).perform();
                result = true;
                break;
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
    }

    //Get Text method
    public String getText(WebElement element) {
        return element.getText();
    }

    //Get Text method
    public String getText(By locator) {
        return driver.findElement(locator).getText();
    }

    //Input Text Action Method
    public void type(String inputText, By locator) {
        driver.findElement(locator).sendKeys(inputText);
    }

    //Wait for Element Present Method
    public void wait(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf((WebElement) locator));
    }

    //Simple Click Method
    public void click(By locator) {
        driver.findElement(locator).click();
    }

    public void swichtoframe(By locator) {
        driver.switchTo().frame(driver.findElement(locator));
    }

    //Simple Drag Method
    public void Drag(By locator) {
        WebElement element = driver.findElement(locator);
        Actions builder = new Actions(driver);
        builder.moveToElement(element).clickAndHold().perform();
    }

    //Simple move Method
    public void Move(By locator) {
        WebElement element = driver.findElement(locator);
        Actions builder = new Actions(driver);
        builder.moveToElement(element).perform();
    }

    //Simple Drop Method
    public void Drop(By locator) {
        WebElement element = driver.findElement(locator);
        Actions builder = new Actions(driver);
        builder.moveToElement(element).release().perform();
    }

    //Simple move Method
    public void DragAndD(By locator) {
        Actions act = new Actions(driver);
        WebElement drag = driver.findElement(locator);
        WebElement drop = driver.findElement(locator);
        Actions builder = new Actions(driver);
        builder.moveToElement(drop).perform();
        act.dragAndDrop(drag, drop).build().perform();
    }

    public void DoubleClick(By locator) {

        WebElement element = driver.findElement(locator);
        Actions actions = new Actions(driver);
        actions.doubleClick(element).perform();
    }

    //Is Diplayed Method
    public Boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }

    }

    public void bench(By locator) {
        WebElement element = driver.findElement(locator); // Let the element id be abcd
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
        //Visit URL Method
    }

    public void visit(String url) {
        driver.get(url);
    }
}
