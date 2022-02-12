import io.appium.java_client.MobileBy;
import io.appium.java_client.windows.WindowsDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.sound.midi.Soundbank;
import javax.swing.*;
import javax.xml.bind.Element;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NotepadeTest {

    public static WindowsDriver notepadSession = null;
    public static WebDriverWait wait;

    public static String getDate() {
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    @BeforeClass
    public static void setUp() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", "C:\\Windows\\System32\\notepad.exe");
            capabilities.setCapability("platformName", "Windows");
            capabilities.setCapability("deviceName", "WindowsPC");
            notepadSession = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            notepadSession.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            wait = new WebDriverWait(notepadSession, 10);
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

//        notepadSession.quit();
    }

    @Test
    public void checkHelpButton() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("Before wait Help");
        wait.until(ExpectedConditions.elementToBeClickable(By.name("Приложение"))); // presenceOfElementLocated(MobileBy.name("Приложение")));
        System.out.println("Before Help");
        List<WebElement> menuItems = notepadSession.findElementsByName("Приложение"); // sendKeys(Keys.ENTER);
        System.out.println("Before vlick");
        for (WebElement curItem : menuItems) {
            System.out.println("Current El=" + curItem.toString());
            curItem.click();
        }
//        menuItems[1].click(); // sendKeys(Keys.ENTER);
//        System.out.println("Before 'element to be SELECTED'");
//        wait.until(ExpectedConditions.elementToBeSelected(By.name("Приложение")));
//        System.out.println("Before ARROW_RIGHT1");
//        System.out.println("Before ARROW_RIGHT2");
//        menuItem.sendKeys(Keys.ARROW_RIGHT); // sendKeys(Keys.ENTER);
//        System.out.println("Before ARROW_RIGHT3");
//        menuItem.sendKeys(Keys.ARROW_RIGHT); // sendKeys(Keys.ENTER);
//        menuItem.click(); // sendKeys(Keys.ENTER);
//        notepadSession.findElementByName("Приложение").F FirstOrDefault(e => e.Displayed).Click();;
//        WebElement helpMenuItem = notepadSession.findElementByName("Приложение");
//        helpMenuItem.click();

        Thread.sleep(4000);
        System.out.println("Before  wait of 'About NotePad'");
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("65")));
        System.out.println("Before 'About NotePad'");
        notepadSession.findElementByAccessibilityId("65").click();
//        notepadSession.findElementByName("О программе").click();
        Thread.sleep(4000);
        System.out.println("Before wait OK Button");
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.name("ОК")));
        System.out.println("Before OK Button");
        notepadSession.findElementByName("ОК").click();
//        notepadSession.findElementByName("OK").click();

//        notepadSession.findElementByXPath("/Pane[@Name=\"Рабочий стол 1\"][@ClassName=\"#32769\"]/Window[@Name=\"1.txt – Блокнот\"][@ClassName=\"Notepad\"]/Window[@Name=\"Блокнот: сведения\"][@ClassName=\"#32770\"]/Button[@Name=\"ОК\"][@ClassName=\"Button\"]").click();
    }

    @Test
    public void checkAboutWindow() {
//        notepadSession.findElementByName("Help").click();
//        notepadSession.findElementByName("About Notepad").click();
//        notepadSession.findElementByName("OK").click();
        System.out.println("Before Справка");
        notepadSession.findElementByName("Приложение");     //.click();
        System.out.println("Before О программе");
        notepadSession.findElementByXPath("/Pane[@Name=\"Рабочий стол 1\"][@ClassName=\"#32769\"]/Window[@Name=\"*1.txt – Блокнот\"][@ClassName=\"Notepad\"]/Menu[@Name=\"Справка\"][@ClassName=\"#32768\"]/MenuItem[@Name=\"О программе\"]").click();
//        notepadSession.findElementByClassName("#32768").click();
//        notepadSession.findElementByName("О программе").click();
        System.out.println("Before Button");
        notepadSession.findElementByClassName("Button").click();
    }

    @Test
    public void sendTestText() {
        System.out.println("Before Wait ...");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("Edit")));
        System.out.println("Before Sending Date");
        notepadSession.findElementByClassName("Edit").sendKeys(getDate());

        System.out.println("inputed: "+getDate());
        System.out.println("1. getText() " + notepadSession.findElementByClassName("Edit").getText().toString());
        System.out.println("2. LegacyValue.Value " + notepadSession.findElementByClassName("Edit").
                getAttribute("LegacyValue"));
        System.out.println("3. Value " + notepadSession.findElementByClassName("Edit").getAttribute("Value"));

//        System.out.println("Before wait of Cleaning");
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("Edit")));
//        System.out.println("Before cleaning");
//        notepadSession.findElementByName("Текстовый редактор").clear();
//        System.out.println("2-d cleaning");
//        notepadSession.findElementByClassName("Edit").clear();

                System.out.println("Before wait of close");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Закрыть")));
        System.out.println("Before close");
        notepadSession.findElementByName("Закрыть").click();    // .sendKeys(Keys.ENTER);

        System.out.println("Before wait saving/not saving");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Не сохранять")));
        System.out.println("Before saving/not saving");
        notepadSession.findElementByName("Не сохранять").click();
    }

    @Test
    public void pressTimeAndDateButton() {
        System.out.println("Before wait  Edit");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Текстовый редактор")));
        System.out.println("Before  Edit");
        notepadSession.findElementByName("Текстовый редактор").click();
//        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.ByAccessibilityId("26")));
        System.out.println("Before 26");
        notepadSession.findElementByAccessibilityId("26").click();
        Assertions.assertNotNull(notepadSession.findElementByClassName("Edit"), "My. No such Element");
        notepadSession.findElementByClassName("Edit").clear();
    }
}

