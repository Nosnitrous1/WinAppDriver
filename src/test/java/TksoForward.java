import io.appium.java_client.windows.WindowsDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static jdk.nashorn.internal.objects.NativeString.trim;

public class TksoForward {
    public static WindowsDriver chromeSession = null;
    public static WebDriverWait cWait;
    public List<WebElement> tabs;
    Actions chrAct = new Actions(chromeSession);
    public int i;
    WebElement adrSearch;
    WebElement newTab;
    public int tabY = 2;

    public static String getDate() {
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    @BeforeAll
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

    @AfterAll
    public static void tearDown() {

//        chromeSession.quit();
    }

    @Test
    public void forwardTest() throws InterruptedException {
        newTab = chromeSession.findElementByName("Новая вкладка");

        List<WebElement> tabsFin = chromeSession.findElementsByXPath("//Pane/Tab/Pane/Pane/TabItem/Button[@Name='Закрыть']");
        System.out.println("NumOfTabsFin: " + tabsFin.size());
        for (i = 1; i < tabsFin.size(); i++) {
            tabsFin.get(i).click();     // Закрыть все лишние закладки (если есть)
        }

        adrSearch = chromeSession.findElementByName("Адресная строка и строка поиска");
        adrSearch.clear();

        adrSearch.sendKeys("http://tkso.ru/index.php?limitstart=" + Integer.toString((int) Math.ceil(Math.random() * 661 + 11))
                + Keys.ENTER);

//        adrSearch.sendKeys("http://tkso.ru" + Keys.ENTER);
//        Thread.sleep(2000);
//        chromeSession.findElementByName(Integer.toString((int)Math.ceil(Math.random()*9+1))).click();

        newTab.click();   // GOOGLE
        adrSearch.clear();
        adrSearch.sendKeys("http://google.com" + Keys.ENTER);
        chromeSession.findElementByName("Найти").sendKeys("Ситуация на Украине" + Keys.ENTER);

        newTab.click();   // YANDEX
        adrSearch.clear();
        adrSearch.sendKeys("http://yandex.ru" + Keys.ENTER);
        chromeSession.findElementByName("Запрос").sendKeys("Ситуация на Украине" + Keys.ENTER);
        WebElement findInYandex = chromeSession.findElementByName("Запрос");
        // Close the ZEN tab
        tabsFin = chromeSession.findElementsByXPath("//Pane/Tab/Pane/Pane/TabItem/Button[@Name='Закрыть']");
        tabsFin.get(2).click();     // Закрыть все лишние закладки (если есть)

        tabs = chromeSession.findElementsByXPath("//Pane/Tab/Pane/Pane/TabItem");
        tabs.get(0).click();    // TKSO
        List<WebElement> articles = chromeSession.findElementsByXPath("//Document[@ClassName='Chrome_RenderWidgetHostHWND']/Document");
        System.out.println("NumOfArticles: " + articles.size() + ":" + articles.toString() + "\n-----------------");

        for (WebElement curArt : articles) {
//            chrAct.doubleClick(chromeSession.findElementByName("Перезагрузить")).build().perform();
            tabs = chromeSession.findElementsByXPath("//Pane/Tab/Pane/Pane/TabItem");
            System.out.println("NumOfTabs: " + tabs.size() + ":" + tabs.toString());

            System.out.println("curArt.value:" + curArt.getAttribute("Value.Value") + "\n"
                    + "curArt.text:" + curArt.getText());
            chrAct.doubleClick(tabs.get(1)).build().perform();
//            tabs.get(1).click();    // GOOGLE
            findGoogle(String.valueOf(curArt.getText()), "tkso.ru", 3, 8);
            chrAct.doubleClick(tabs.get(tabY)).build().perform();
//            tabs.get(tabY).click();    // YANDEX
            findYandex(String.valueOf(curArt.getText()), "tkso.ru", 4, 7);

        }
    }

    public void findGoogle(String textSearch, String whatSearch, int numPages, int numOfWords) throws InterruptedException {
        tabs.get(1).click();    // GOOGLE
        chrAct.doubleClick(chromeSession.findElementByName("Перезагрузить")).build().perform();
        Thread.sleep(5000);
        WebElement findAny = chromeSession.findElementByName("Найти");  // Поиск в Google

        findAny.clear();
        findAny.sendKeys(Keys.CONTROL + "a");       // Delete every request
        findAny.sendKeys(Keys.DELETE);
        // curArt.getText().substring(0, curArt.getText().indexOf("  "))
        findAny.sendKeys(retWords(textSearch, numOfWords) + Keys.ENTER);
        Thread.sleep(3000);
        boolean fin = false;
        for (i = 1; i <= numPages; i++) {
            Thread.sleep(7000);
            List<WebElement> mySiteRes = chromeSession.findElementsByXPath("//Text[contains(@Name, '"
                    + whatSearch + "')]");
            System.out.println("Google. Page:" + i + "  NumOfmSRes: " + mySiteRes.size() + ":" + mySiteRes.toString() + "\n-----------------");
            for (WebElement mSRes : mySiteRes) {
                mSRes.click();          // Goto Serched Site
                tabs.get(1).click();    // GOOGLE
                fin = true;
            }
            if (fin) break;
            List<WebElement> nextGoogleButton = chromeSession.findElementsByName("Следующая");
            System.out.println("Google: Next: " + nextGoogleButton.size());
            if (nextGoogleButton.size() == 1) {
                chromeSession.findElementByName("Следующая").click();   // Next Page
            } else break;
        }
    }

    public void findYandex(String textSearch, String whatSearch, int numPages, int numOfWords) throws InterruptedException {
        List<WebElement> mainYandexPage = chromeSession.findElementsByName("Главная страница Яндекс");
        if (mainYandexPage.size() != 1) {
            tabs = chromeSession.findElementsByXPath("//Pane/Tab/Pane/Pane/TabItem");
            for (i = 0; i <= tabs.size(); i++) {
                chrAct.doubleClick(tabs.get(i)).build().perform();
//                tabs.get(i).click();     // Goto next page
                List<WebElement> yandexPage = chromeSession.findElementsByName("Главная страница Яндекс");
                System.out.println("Tab# "+i+". Link to the Y main page "+yandexPage.size());
                if (yandexPage.size() == 1) {     // YANDEX ?
                    tabY = i;
                    break; }
            }
        }

        System.out.println("Yandex: start");

        List<WebElement> imNotRobot = chromeSession.findElementsByName("\"Я не робот\"");
        if (imNotRobot.size() == 1) {
            chromeSession.findElementByName("\"Я не робот\"").click();
        }

        chrAct.doubleClick(chromeSession.findElementByName("Перезагрузить")).build().perform();
        Thread.sleep(5000);
        WebElement findAny = chromeSession.findElementByName("Запрос");

        findAny.clear();
        findAny.sendKeys(Keys.CONTROL + "a");       // Delete every request
        findAny.sendKeys(Keys.DELETE);
        // curArt.getText().substring(0, curArt.getText().indexOf("  "))
        findAny.sendKeys(retWords(textSearch, numOfWords) + Keys.ENTER);
        boolean fin = false;
        for (int i = 1; i <= numPages; i++) {
            Thread.sleep((int) Math.ceil(Math.random() * 12000 + 7000)); // (int)Math.ceil(Math.random()*12000+7000)
            List<WebElement> mySiteRes = chromeSession.findElementsByXPath("//Text[contains(@Name, '"
                    + whatSearch + "')]");
            System.out.println("Yandex. Page:" + i + "  NumOfmSRes: " + mySiteRes.size() + ":" + mySiteRes.toString() + "\n-----------------");
            for (WebElement mSRes : mySiteRes) {
                mSRes.click();          // Goto Serched Site
                tabs.get(2).click();    // YANDEX
                fin = true;
                break;
            }
            if (fin) break;
            chromeSession.findElementByName("дальше").click();   // Next Page
        }
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
