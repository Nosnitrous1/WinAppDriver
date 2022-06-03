import io.appium.java_client.MobileBy;
import io.appium.java_client.windows.WindowsDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MesAuth {
    public static WindowsDriver mesSession = null;
    public static WebDriverWait wait;
    Actions addAct = new Actions(mesSession);
//        builder.moveToElement(insights.get(1)).build().perform();

    public static String getDate() {
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    @BeforeClass
    public static void setUp() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", "C:/Program Files (x86)/Proficy/Proficy Workflow/Program/ProficyClient.exe");  // "C:\\Windows\\System32\\mspaint.exe");  // "C:\\Windows\\System32\\notepad.exe"); //
            capabilities.setCapability("platformName", "Windows");
            capabilities.setCapability("deviceName", "WindowsPC");
            mesSession = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            mesSession.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            wait = new WebDriverWait(mesSession, 30);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() {
        mesSession.findElementByName("Закрыть").click();
        mesSession.findElementByName("Не сохранять").click();
    }

    @Test
    public void checkExcelSum() throws InterruptedException {
        Thread.sleep(10000);

        String mesWinHandle = mesSession.getWindowHandle();
        Set<String> winHandles = mesSession.getWindowHandles();     // Всего д.б. 2 окна
        System.out.println("NumOfWin: " + winHandles.size() + ":" + winHandles.toString());
        for (String win : winHandles) {
            mesSession.switchTo().window(win);                      // Перейти на др. (или то же) окно
            String curWinHandle = mesSession.getWindowHandle();
            if (!curWinHandle.equals(mesWinHandle)) {
                break;
            }
        }           // Всё! Мы в др. окне, нажать "OK"
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("OK")));     //Окно ошибки
        mesSession.findElementByName("OK").click();

        mesSession.switchTo().window(mesWinHandle);                 // Возврат к MES окну
        System.out.println("It must be a windows "+mesWinHandle);
        Thread.sleep(3000);

//        wait.until(ExpectedConditions.presenceOfElementLocated(new MobileBy.ByAccessibilityId("LoginImage")));
        mesSession.findElementByAccessibilityId("LoginImage").click();          //Картинка с чел


        Set<String> winHandles1 = mesSession.getWindowHandles();     // Всего д.б. 2 окна
        System.out.println("NumOfWin: " + winHandles1.size() + ":" + winHandles1.toString());
        for (String win1 : winHandles1) {
            mesSession.switchTo().window(win1);                      // Перейти на др. (или то же) окно
            String curWinHandle1 = mesSession.getWindowHandle();
            System.out.println("Another window: " + win1);
            if (!curWinHandle1.equals(mesWinHandle)) {
                break;
            }
        }           // Всё! Мы в др. окне, нажать "OK"

        Thread.sleep(3000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Проверка подлинности Proficy")));     //Окно ошибки
        mesSession.findElementByName("Проверка подлинности Proficy").click();    //Подлинность, Login, Pass
        System.out.println("Before Login input");
        mesSession.findElementByAccessibilityId("_UserNameBox").sendKeys("admin");  //Login
        System.out.println("Before Pass input");
        mesSession.findElementByAccessibilityId("_PasswordBox").sendKeys("1qaZXsw2"); //Pass
        System.out.println("Before 'OK' pressing");
        mesSession.findElementByAccessibilityId("_AuthenticateButton").click();

        mesSession.switchTo().window(mesWinHandle);                 // Возврат к MES окну
        System.out.println("It must be a windows "+mesWinHandle);
        Thread.sleep(3000);
        wait.until(ExpectedConditions.presenceOfElementLocated(new MobileBy.ByAccessibilityId("_taskTitleTextBlock")));
        List<WebElement> taskList = mesSession.findElementsByAccessibilityId("_taskTitleTextBlock");
        System.out.println("List of Tasks:");
        for (WebElement curItem : taskList) {
            System.out.println("Current El=" + curItem.toString());
//            curItem.click();
        }
//        Assertions.assertEquals("150", curSum,"Incorrect sum");
    }


}
