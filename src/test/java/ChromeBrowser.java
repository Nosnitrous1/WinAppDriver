import io.appium.java_client.windows.WindowsDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class ChromeBrowser {
    public static WindowsDriver chromeSession = null;
    public static WebDriverWait cWait;

    public static String getDate() {
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    @BeforeClass
    public static void setUp() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
            capabilities.setCapability("platformName", "Windows");
            capabilities.setCapability("deviceName", "WindowsPC");
            chromeSession = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            chromeSession.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            cWait = new WebDriverWait(chromeSession, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @AfterClass
//    @AfterMethod
//    public void cleanApp() {
//        notepadSession.quit();
//        setUp();
//    }

    @AfterClass
    public static void tearDown() {

        chromeSession.quit();
    }

    @Test
    public void sendTestText() {
        ;

    }
}
