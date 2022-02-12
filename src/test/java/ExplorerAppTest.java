import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class ExplorerAppTest {
    public static WindowsDriver explorerSession = null;
    public static WebDriverWait waitExp;


    @BeforeClass
    public static void setUpExp() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", "c:\\windows\\explorer.exe");
            capabilities.setCapability("platformName", "Windows");
            capabilities.setCapability("deviceName", "WindowsPC");
            System.out.println("Before Session");
            explorerSession = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            System.out.println("After Session");
            explorerSession.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            waitExp = new WebDriverWait(explorerSession, 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDownExp() {
//        calcSession.quit();
    }

    @Test
    public void expTest() throws InterruptedException {
        waitExp.until(ExpectedConditions.presenceOfElementLocated(By.name("Локальный диск (C:)")));
        explorerSession.findElementByName("Локальный диск (C:)").click();
    }
}
