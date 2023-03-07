import io.appium.java_client.windows.WindowsDriver;
import io.qameta.allure.*;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.substring;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.maven.surefire.shared.utils.StringUtils.replace;

//import static jdk.nashorn.internal.objects.NativeString.trim;

public class TksoForward {
    public static WindowsDriver chromeSession = null;
    public static WebDriverWait cWait;
    public List<WebElement> tabs;
    Actions chrAct = new Actions(chromeSession);
    public int i;
    WebElement adrSearch;
    WebElement newTab;
    public int tabY = 2;
    public String stepNumStr = "1";     // Step Number

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
    @DisplayName("TKSO forwarding test")
    @Description("Продвижение сайта вызовом переходов на него из поисковиков")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Desktop testing by WAD")
    @Feature("Ащкцфквштп testing")
    @Story("Продвижение через поисковики")
    public void forwardTest() throws InterruptedException {
//        newTab = chromeSession.findElementByName("Новая вкладка");
        newTab = chromeSession.findElementByXPath("//Button[@Name='Новая вкладка']");

        List<WebElement> tabsFin = chromeSession.findElementsByXPath("//Pane/Tab/Pane/Pane/TabItem/Button[@Name='Закрыть']");
        System.out.println("NumOfTabsFin: " + tabsFin.size());
        for (i = 1; i < tabsFin.size(); i++) {
            tabsFin.get(i).click();     // Закрыть все лишние закладки (если есть)
        }

        adrSearch = chromeSession.findElementByName("Адресная строка и строка поиска");
        adrSearch.clear();
        String curTKSOPageNum = Integer.toString((int) Math.ceil(Math.random() * 661 + 11));
        adrSearch.sendKeys("http://tkso.ru/index.php?limitstart=" + curTKSOPageNum + Keys.ENTER);

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
//        Thread.sleep(7000);
        byte[] scrSht = allureScreenshot("Фотка страницы tkso.ru");      //
        if (runningFromIntelliJ()) {
            makeScreenshot("tkso" + "_" + curTKSOPageNum + "_");
        }

        for (WebElement curArt : articles) {
//            chrAct.doubleClick(chromeSession.findElementByName("Перезагрузить")).build().perform();
            tabs = chromeSession.findElementsByXPath("//Pane/Tab/Pane/Pane/TabItem");
            System.out.println("NumOfTabs: " + tabs.size() + ":" + tabs.toString());

            System.out.println("curArt.value:" + curArt.getAttribute("Value.Value") + "\n"
                    + "curArt.text:" + curArt.getText());
            chrAct.doubleClick(tabs.get(1)).build().perform();
//            tabs.get(1).click();    // GOOGLE
            findGoogle(retWords(String.valueOf(curArt.getText()), 8), "tkso.ru", 3, stepNumStr);
            chrAct.doubleClick(tabs.get(tabY)).build().perform();
//            tabs.get(tabY).click();    // YANDEX
            findYandex(retWords(String.valueOf(curArt.getText()), 8), "tkso.ru", 4, stepNumStr);

        }
    }

    @Step("{stepN}. Google: {textSearch}")
    public void findGoogle(String textSearch, String whatSearch, int numPages, String stepN) throws InterruptedException {
        stepNumStr = String.valueOf(Integer.parseInt(stepNumStr) + 1);
        tabs.get(1).click();    // GOOGLE
        chrAct.doubleClick(chromeSession.findElementByName("Перезагрузить")).build().perform();
        Thread.sleep(5000);
        WebElement findAny = chromeSession.findElementByName("Найти");  // Поиск в Google

        findAny.clear();
        findAny.sendKeys(Keys.CONTROL + "a");       // Delete every request
        findAny.sendKeys(Keys.DELETE);
        // curArt.getText().substring(0, curArt.getText().indexOf("  "))
        findAny.sendKeys(textSearch + Keys.ENTER);
        Thread.sleep(3000);
        boolean fin = false;
        for (i = 1; i <= numPages; i++) {
            Thread.sleep(7000);
            List<WebElement> mySiteRes = chromeSession.findElementsByXPath("//Text[contains(@Name, '"
                    + whatSearch + "')]");
            System.out.println("Google. Page:" + i + "  NumOfmSRes: " + mySiteRes.size() + ":" + mySiteRes.toString() + "\n-----------------");
            for (WebElement mSRes : mySiteRes) {
                mSRes.click();          // Goto Serched Site
                Thread.sleep(7000);
//                callMySite("Google");
                byte[] scrShtG = allureScreenshot("Call from Google");
                if (runningFromIntelliJ()) {
                    makeScreenshot("tkso_Google_" + substring(replace(textSearch, '"', '_'), 0, 6) + "_");
                }
                tabs.get(1).click();    // GOOGLE                makeScreenshot("tkso_Google_" + i + "_");

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

    @Step("{stepN}. Yandex: {textSearch}")
    public void findYandex(String textSearch, String whatSearch, int numPages, String stepN) throws InterruptedException {
        stepNumStr = String.valueOf(Integer.parseInt(stepNumStr) + 1);
        List<WebElement> mainYandexPage = chromeSession.findElementsByName("Главная страница Яндекс");
        if (mainYandexPage.size() != 1) {
            tabs = chromeSession.findElementsByXPath("//Pane/Tab/Pane/Pane/TabItem");
            for (i = 0; i <= tabs.size(); i++) {
                chrAct.doubleClick(tabs.get(i)).build().perform();
//                tabs.get(i).click();     // Goto next page
                List<WebElement> yandexPage = chromeSession.findElementsByName("Главная страница Яндекс");
                System.out.println("Tab# " + i + ". Link to the Y main page " + yandexPage.size());
                if (yandexPage.size() == 1) {     // YANDEX ?
                    tabY = i;
                    break;
                }
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
        findAny.sendKeys(textSearch + Keys.ENTER);
        boolean fin = false;
        for (int i = 1; i <= numPages; i++) {
            Thread.sleep((int) Math.ceil(Math.random() * 12000 + 7000)); // (int)Math.ceil(Math.random()*12000+7000)
            List<WebElement> mySiteRes = chromeSession.findElementsByXPath("//Text[contains(@Name, '"
                    + whatSearch + "')]");
            System.out.println("Yandex. Page:" + i + "  NumOfmSRes: " + mySiteRes.size() + ":" + mySiteRes.toString() + "\n-----------------");
            for (WebElement mSRes : mySiteRes) {
                mSRes.click();          // Goto Serched Site
                Thread.sleep(7000);
//                callMySite("Yandex");
                byte[] scrShtY = allureScreenshot("Call from Google");
                if (runningFromIntelliJ()) {
                    makeScreenshot("tkso_Yandex_" + substring(replace(textSearch, '"', '_'), 0, 6) + "_");
                }
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

    //    @Step("Скринщот шага {testName}")
    private void makeScreenshot(String testName) {
            SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yy");
            String sDate = ft.format(new Date());
            try {
                File screenshot = ((TakesScreenshot) chromeSession)
                        .getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshot, new File("screenShots\\screenShot_" + testName + sDate + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
//        return screenshotBytes;
    }

    private boolean runningFromIntelliJ() {
        String classPath = System.getProperty("java.class.path");
        System.out.println("classPath = " + classPath);
        return classPath.contains("idea_rt.jar");
    }

    //    @Step("{name}")
    @SuppressWarnings("UnusedReturnValue")
    @Attachment(value = "{name}", type = "image/png", fileExtension = ".png")
    public byte[] allureScreenshot(String name) {
        return ((TakesScreenshot) chromeSession).getScreenshotAs(OutputType.BYTES);
    }

    @Step("Call me from: {whatFrom}")
    public void callMySite(String whatFrom) {
        byte[] scrsht = allureScreenshot("Переход на tkso.ru из " + whatFrom);      //
    }


}
