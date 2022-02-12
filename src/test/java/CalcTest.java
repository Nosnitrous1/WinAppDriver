import io.appium.java_client.MobileBy;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class CalcTest {

    public static WindowsDriver calcSession = null;
    public static WebDriverWait wait;

    public static String getDate() {
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    @BeforeClass
    public static void setUp() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
            capabilities.setCapability("platformName", "Windows");
            capabilities.setCapability("deviceName", "WindowsPC");
            calcSession = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            calcSession.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            wait = new WebDriverWait(calcSession, 3);
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
//        calcSession.quit();
    }

    @Test
    public void checkHelpButton() throws InterruptedException {
        // Thread.sleep(4000);
//        System.out.println("Before wait One");
//        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.name("One")));
//        System.out.println("Before One");
//        calcSession.findElementByName("One").click();

        System.out.println("Before wait Two");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Two")));
        WindowsElement varTwo = (WindowsElement) calcSession.findElementByName("Two");
        System.out.println("Twice TWO");
        ((WindowsElement) varTwo).click();

        System.out.println("Before wait PLUS");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Plus")));
        System.out.println("PLUS");
        calcSession.findElementByName("Plus").click();

        ((WindowsElement) varTwo).click();

        System.out.println("Before wait PLUS");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Plus")));
        System.out.println("PLUS");
        calcSession.findElementByName("Plus").click();

        ((WindowsElement) varTwo).click();

        System.out.println("Before wait EQUALS");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Equals")));
        System.out.println("EQUALS");
        calcSession.findElementByName("Equals").click();

        String res = calcSession.findElementByAccessibilityId("CalculatorResults").getText().
                replace("Display is ", "");   // getAttribute("LegacyAccessible.Value");
        System.out.println("Result is "+ res);

        Assertions.assertEquals("6", res, "Ubnormal Calculator Result");


////        notepadSession.findElementByName("Приложение").click();
////        WebElement helpMenuItem = notepadSession.findElementByName("Приложение");
////        helpMenuItem.click();
//
//        Thread.sleep(4000);
//        System.out.println("Before  wait of 'About NotePad'");
//        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("65")));
//        System.out.println("Before 'About NotePad'");
//        notepadSession.findElementByAccessibilityId("65").click();
////        notepadSession.findElementByName("О программе").click();
//        Thread.sleep(4000);
//        System.out.println("Before wait OK Button");
//        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.name("ОК")));
//        System.out.println("Before OK Button");
//        notepadSession.findElementByName("ОК").click();
////        notepadSession.findElementByName("OK").click();
//
////        notepadSession.findElementByXPath("/Pane[@Name=\"Рабочий стол 1\"][@ClassName=\"#32769\"]/Window[@Name=\"1.txt – Блокнот\"][@ClassName=\"Notepad\"]/Window[@Name=\"Блокнот: сведения\"][@ClassName=\"#32770\"]/Button[@Name=\"ОК\"][@ClassName=\"Button\"]").click();
    }

}
