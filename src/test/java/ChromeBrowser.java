import io.appium.java_client.windows.WindowsDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static jdk.nashorn.internal.objects.NativeString.trim;

public class ChromeBrowser {
    public static WindowsDriver chromeSession = null;
    public static WebDriverWait cWait;
    Actions chrAct = new Actions(chromeSession);

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

//        chromeSession.quit();
    }

    @Test
    public void sendTestText() throws InterruptedException {
        WebElement adrSearch = chromeSession.findElementByName("Адресная строка и строка поиска");
        String oldAdr = adrSearch.getText();
        System.out.println("OldSearch= " + oldAdr);
        adrSearch.clear();
        adrSearch.sendKeys("http://tkso.ru" + Keys.ENTER);
        Thread.sleep(2000);

        System.out.println("adrSearch1 = " + adrSearch.getAttribute("Value.Value"));

        chromeSession.findElementByXPath("//MenuItem[contains(@Name,'Расширения')]").click();
        chrAct.doubleClick(chromeSession.findElementByXPath("//Button[@Name='Управление расширениями']"))
                .build().perform();
//        chromeSession.findElementByName("Управление расширениями").sendKeys(Keys.ENTER);
        System.out.println("Extentions list is opened");

        String curWinHandle = chromeSession.getWindowHandle();
        System.out.println("cur window: " + curWinHandle);
        Set<String> winHandles = chromeSession.getWindowHandles();
        System.out.println("NumOfWins: " + winHandles.size() + ":" + winHandles.toString());
        for (String win : winHandles) {
            System.out.println("Another window: " + win);
        }

        List<WebElement> tabs = chromeSession.findElementsByXPath("//Pane/Tab/Pane/Pane/TabItem");
        System.out.println("NumOfTabs: " + tabs.size() + ":" + tabs.toString());
        for (WebElement curTab : tabs) {
            System.out.println("Another tab: " + curTab + "\nText:" + curTab.getText()
                    + "\nValue:" + curTab.getAttribute("Value.Value"));
            curTab.click();         // chrAct.doubleClick(curTab).build().perform();
        }

        List<WebElement> extents = chromeSession.findElementsByAccessibilityId("detailsButton");  // "viewManager");
        System.out.println("NumOfExt: " + extents.size() + ":" + extents.toString());
        for (WebElement curExt : extents) {
//            System.out.println("Another ext: " + curExt + "\nText:" + curExt.getText()
//                    + "\nValue:" + curExt.getAttribute("Value.Value"));
        }
//        extents.get(2).click();         // chrAct.doubleClick(curTab).build().perform();

        tabs.get(0).click();
        tabs.get(0).click();
        List<WebElement> articles = chromeSession.findElementsByXPath("//Document[@ClassName='Chrome_RenderWidgetHostHWND']/Document");
        System.out.println("NumOfArt: " + articles.size() + ":" + articles.toString() + "\n-----------------");

        tabs.get(1).click();
        adrSearch.clear();
        adrSearch.sendKeys("google.com" + Keys.ENTER);
        Thread.sleep(1000);
        WebElement findAny = chromeSession.findElementByName("Найти");
        findAny.clear();
        findAny.sendKeys(Keys.CONTROL + "a");       // Delete every request
        findAny.sendKeys(Keys.DELETE);
        findAny.sendKeys("Ситуация на Украине" + Keys.ENTER);

        for (WebElement curArt : articles) {
            chrAct.doubleClick(chromeSession.findElementByName("Перезагрузить")).build().perform();
            Thread.sleep(5000);
            //            chromeSession.findElementByName("Перезагрузить").click();
            findAny = chromeSession.findElementByName("Найти");        // Repeat Element (its changed)
            findAny.clear();
            findAny.sendKeys(Keys.CONTROL + "a");       // Delete every request
            findAny.sendKeys(Keys.DELETE);
            // curArt.getText().substring(0, curArt.getText().indexOf("  "))
            findAny.sendKeys(retWords(curArt.getText(), 8) + Keys.ENTER);
            Thread.sleep(3000);
            for (int i = 1; i <= 6; i++) {
                List<WebElement> mySiteRes = chromeSession.findElementsByXPath("//Text[contains(@Name, 'tkso.ru')]");
                System.out.println("Page:" + i + "  NumOfmSRes: " + mySiteRes.size() + ":" + mySiteRes.toString() + "\n-----------------");
                for (WebElement mSRes : mySiteRes) {
                    Thread.sleep(2000);
                    //                System.out.println("Another Result -------" + mSRes + "\nText:" + mSRes.getText()
                    //                        + "\nValue:" + mSRes.getAttribute("Value.Value")
                    //                        + "\nName:" + mSRes.getAttribute("Name"));
                    oldAdr = adrSearch.getText();
                    mSRes.click();
                    adrSearch.sendKeys(oldAdr + Keys.ENTER);
                }
                chromeSession.findElementByName("Следующая").click();
            }
            //            break;
        }


//        System.out.println("Before Extentions tab close");
//        chromeSession.findElementByXPath("/TabItem[@Name='Расширения']/Button[@Name='Закрыть']").click();
//        System.out.println("After Extentions tab close");
    }

    String retWords(String str, int NofW) {
        String[] words = str.split(" ");
        int i = 0;
        String retVal = "";
        System.out.println("str=" + str + "\nWORDS=" + words.toString());
        for (String word : words) {
            retVal = retVal + word + " ";
            i++;
            if (i > NofW) break;
        }
        return trim(retVal); // —
    }
}
