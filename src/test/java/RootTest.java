import com.sun.applet2.Applet2;
import io.appium.java_client.windows.WindowsDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RootTest {
    public static WindowsDriver allSession = null;
    public static WebDriverWait aWait;
    Actions rootAct = new Actions(allSession);
//    static ProcessBuilder wadBuilder = new ProcessBuilder("C:/Program Files/Windows Application Driver/WinAppDriver.exe");
//    static Applet2 startWAD;
//        Process startWAD = wadBuilder.start();          // WinAppDriver
//        startWAD.destroy();


    public RootTest() throws IOException {
    }

    public static String getDate() {
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    @BeforeClass
    public static void setUp() {
        try {
            DesiredCapabilities capa = new DesiredCapabilities();
            capa.setCapability("app", "Root");
            capa.setCapability("platformName", "Windows");
            capa.setCapability("deviceName", "WindowsPC");
            allSession = new WindowsDriver(new URL("http://127.0.0.1:4723"), capa);
            allSession.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            aWait = new WebDriverWait(allSession, 10);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() {
        allSession.quit();
    }

    @Test
    public void sendTestText() throws InterruptedException, IOException {
        ProcessBuilder builder = new ProcessBuilder("C:/Program Files/Google/Chrome/Application/chrome.exe").inheritIO();
        Process startChrome = builder.start();

        WebElement adrString = allSession.findElementByName("Адресная строка и строка поиска");

        System.out.println("Adr String: " + adrString.getAttribute("Value.Value"));
        adrString.clear();
        adrString.sendKeys("yandex.ru" + Keys.ENTER);
        Thread.sleep(3000);
        allSession.findElementByName("Запрос").sendKeys("Положение на Украине" + Keys.ENTER);

        System.out.println("Before start of IDE");
        allSession.findElementByXPath("//MenuItem[starts-with(@Name,\"Selenium IDE\")]").click();
        System.out.println("After start of IDE");
        Thread.sleep(1000);

        System.out.println("Whats here?");

        String curWinHandle = allSession.getWindowHandle();         // Save Handle of Cur Window
        Set<String> winHandles = allSession.getWindowHandles();     // Всего д.б. 2 окна
        System.out.println("NumOfWin: " + winHandles.size() + ":" + winHandles.toString());
        for (String win : winHandles) {
            allSession.switchTo().window(win);                      // Перейти на др. (или то же) окно
            System.out.println("Another window: " + win);
            if (!allSession.getWindowHandle().equals(curWinHandle)) {
                break;
            }
        }           // Всё! Мы в др. окне, нажать "OK"
//                  // 1. google Chrome Window 2. Selenium IDE Window
//        "/Pane[@Name=\"Рабочий стол 1\"][@ClassName=\"#32769\"]/Pane[@Name=\"Поиск - Google Chrome\"][@ClassName=\"Chrome_WidgetWin_1\"]/Pane[@Name=\"Google Chrome\"]/Pane/Pane[position()=1]/Pane/ToolBar[position()=1]/Pane[position()=7]/MenuItem[@Name=\"Расширения\"]"
//        "/Pane[@Name=\"Рабочий стол 1\"][@ClassName=\"#32769\"]/Pane[@Name=\"Selenium IDE\"]         [@ClassName=\"Chrome_WidgetWin_1\"]/Document[@Name=\"Selenium IDE\"][@ClassName=\"Chrome_RenderWidgetHostHWND\"]/Custom[@Name=\"Welcome to Selenium IDE! Version 3.17.2\"]/List[position()=5]/ListItem"
//        "/Pane[@Name=\"Рабочий стол 1\"][@ClassName=\"#32769\"]/Pane[@Name=\"Selenium IDE\"][@ClassName=\"Chrome_WidgetWin_1\"]/Document[@Name=\"Selenium IDE\"][@ClassName=\"Chrome_RenderWidgetHostHWND\"]/Custom[@Name=\"Welcome to Selenium IDE! Version 3.17.2\"]/List[position()=5]/ListItem[position()=1]"
//        "/Pane[@Name=\"Рабочий стол 1\"][@ClassName=\"#32769\"]/Pane[@Name=\"Selenium IDE\"][@ClassName=\"Chrome_WidgetWin_1\"]/Document[@Name=\"Selenium IDE\"][@ClassName=\"Chrome_RenderWidgetHostHWND\"]/Custom[@Name=\"Welcome to Selenium IDE! Version 3.17.2\"]/List[position()=5]/ListItem[position()=2]"
//        "/Pane[@Name=\"Рабочий стол 1\"][@ClassName=\"#32769\"]/Pane[@Name=\"Selenium IDE\"][@ClassName=\"Chrome_WidgetWin_1\"]/Document[@Name=\"Selenium IDE\"][@ClassName=\"Chrome_RenderWidgetHostHWND\"]/Custom[@Name=\"Welcome to Selenium IDE! Version 3.17.2\"]/List[position()=5]/ListItem[position()=3]"

//        System.out.println("Before list");
//        List<String> lstItems = allSession.findElementsByXPath("//Custom[@Name='Welcome to Selenium IDE! Version 3.17.2']/List/ListItem");
//        for (String lst : lstItems) {
//            System.out.println("Another Element: " + lst);
//        }
//        System.out.println("After list");

//        System.out.println("Before start Rec");         // Attempt to stert RECORDING
//        allSession.findElementByXPath("//Document[@Name='Selenium IDE'][@ClassName='Chrome_RenderWidgetHostHWND']/Custom[@Name='Welcome to Selenium IDE! Version 3.17.2']/List[position()=5]/ListItem")
//                        .click();
//        System.out.println("After start Rec");


        System.out.println("Before Close IDE");
//        allSession.findElementByXPath("//Button[@Name='Закрыть']").click();
        rootAct.keyDown(Keys.ALT);
        rootAct.sendKeys(Keys.F4);
        rootAct.keyUp(Keys.ALT);
        rootAct.build().perform();
        System.out.println("After Close IDE");
        Thread.sleep(1000);

//            allSession.findElementByName("Закрыть").click();
//        WebElement chromeClose = allSession.findElementByXPath("//Pane[@Name='Google Chrome']/Pane/Pane/Button[@Name='Закрыть']");
//        chromeClose.click();

        System.out.println("Before Close App");
        rootAct.keyDown(Keys.ALT);
        rootAct.sendKeys(Keys.F4);
        rootAct.keyUp(Keys.ALT);
        rootAct.build().perform();
        System.out.println("After Close App");

//        chromeClose.sendKeys(Keys.LEFT_ALT + Keys.F4);
//            allSession.findElementByName("Закрыть").click();
//            System.out.println("El-t IDE" + allSession.findElementByXPath(
//                    "/Document[@Name=\"Selenium IDE\"][@ClassName=\"Chrome_RenderWidgetHostHWND\"]/Custom[@Name=\"Welcome to Selenium IDE! Version 3.17.2\"]/List[position()=5]/ListItem[position()=3]")
//                            .getText());
//            System.out.println("El-t Chrome" + allSession.findElementByXPath(
//                    "/Pane[@Name=\"Рабочий стол 1\"][@ClassName=\"#32769\"]/Pane[starts-with(@Name,\"Положение на Украине — Яндекс: нашлось 9 млн результатов - Googl\")][@ClassName=\"Chrome_WidgetWin_1\"]/Document[@Name=\"Положение на Украине — Яндекс: нашлось 9 млн результатов\"][@ClassName=\"Chrome_RenderWidgetHostHWND\"]/Group[position()=3]/Table/Hyperlink[@Name=\"Главная страница Яндекс\"]")
//                    .getText());

//            allSession.findElementByName("starts-with:Selenium IDE").click();
    }
}