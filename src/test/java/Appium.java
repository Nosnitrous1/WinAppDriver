import io.appium.java_client.AppiumDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class Appium {
    public static AppiumDriver exSession = null;
    public static WebDriverWait wait;

    public static String getDate() {
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    @BeforeClass
    public static void setUp() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
//            capabilities.setCapability("app", "Microsoft.BingWeather_8wekyb3d8bbwe!App");
            capabilities.setCapability("app", "C:/Program Files/Microsoft Office/root/Office16/excel.exe");  // "C:\\Windows\\System32\\mspaint.exe");  // "C:\\Windows\\System32\\notepad.exe"); //
            capabilities.setCapability("platformName", "Windows");
            capabilities.setCapability("deviceName", "WindowsPC");
            capabilities.setCapability("platformVersion","10");
            exSession = new AppiumDriver<>(new URL("http://127.0.0.1:4723"), capabilities);
//            WeatherSession = new AppiumDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);
//            exSession = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            exSession.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            wait = new WebDriverWait(exSession, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() {

//        exSession.quit();
    }

    @Test
    public void checkExcelCells() throws InterruptedException {
//        Thread.sleep(4000);
        System.out.println("wait NameAndConditions");
//        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("NameAndConditions")));
        System.out.println("Before read a Page Source");
        System.out.println(exSession.getPageSource());
//        exSession.findElementByClassName("NetUIListViewItem").click();


    }
}
