import io.appium.java_client.windows.WindowsDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class Excel {
    public static WindowsDriver exSession = null;
    public static WebDriverWait wait;
    Actions addAct = new Actions(exSession);
//        builder.moveToElement(insights.get(1)).build().perform();


    public static String getDate() {
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    @BeforeClass
    public static void setUp() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", "C:/Program Files/Microsoft Office/root/Office16/excel.exe");  // "C:\\Windows\\System32\\mspaint.exe");  // "C:\\Windows\\System32\\notepad.exe"); //
            capabilities.setCapability("platformName", "Windows");
            capabilities.setCapability("deviceName", "WindowsPC");
            exSession = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
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
        System.out.println("Before wait Пустая книга");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("NetUIListViewItem")));
        System.out.println("Before Пустая книга");
        exSession.findElementByClassName("NetUIListViewItem").click();
        System.out.println("Before Заполнить яч A5");
        exSession.findElementByAccessibilityId("A5").click();
        exSession.findElementByAccessibilityId("FormulaBar").sendKeys("10");

        System.out.println("Goto Caption");
        exSession.findElementByClassName("NetUIOfficeCaption");

//        exSession.findElementByAccessibilityId("B7").click();
//        exSession.findElementByName("Вставка").click();           // function
        exSession.findElementByAccessibilityId("A5").clear();
        String resA5 = exSession.findElementByAccessibilityId("A5").getText().toString();
        System.out.println("resA5=" + resA5);
        resA5 = exSession.findElementByName(resA5).getText();
        System.out.println("resA5=" + resA5);

        WebElement elementFormulaBar = exSession.findElementByAccessibilityId("FormulaBar");
        WebElement elementB7 = exSession.findElementByName("\"B\" 7");
        addAct.doubleClick(elementB7).build().perform();
        elementFormulaBar.click();
        elementFormulaBar.sendKeys("20");

        System.out.println("Goto B8");
        WebElement elementB8 = exSession.findElementByName("\"B\" 8");
        addAct.doubleClick(elementB8).build().perform();
        elementFormulaBar.click();
        elementFormulaBar.sendKeys("30");

        String resB7 = exSession.findElementByAccessibilityId("FormulaBar").getText().toString();
        System.out.println("resB7=" + resB7);
        System.out.println("Before Заполнить яч B8");
        exSession.findElementByAccessibilityId("B8").sendKeys("30");
//        exSession.findElementByName("Положение").getTagName();
//        exSession.findElementByClassName("NetUIListViewItem").click();
//        System.out.println("Before A1");
//        exSession.findElementByAccessibilityId("A1").sendKeys("A1" + Keys.TAB);
    }

    @Test
    public void checkExcelSum() throws InterruptedException {
        Thread.sleep(1500);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("NetUIListViewItem")));
        exSession.findElementByClassName("NetUIListViewItem").click();


        enterSymbol("\"A\" 1", "10");
        enterSymbol("\"A\" 2", "20");
        enterSymbol("\"A\" 3", "30");
        enterSymbol("\"A\" 4", "40");
        enterSymbol("\"A\" 5", "50");
        String curSum = calcSum("\"A\" 6", "A1", "A5");
    }

    public void enterSymbol(String name, String value) {
        WebElement elementFormulaBar = exSession.findElementByAccessibilityId("FormulaBar");
        WebElement sellName = exSession.findElementByName(name);
        addAct.doubleClick(sellName).build().perform();
        elementFormulaBar.click();
        elementFormulaBar.sendKeys(value);
        System.out.println(value + " was inserted to sell " + name);
    }

    public String calcSum(String name, String start, String finish) throws InterruptedException {
        String summa = "0";
        WebElement elementFormulaBar = exSession.findElementByAccessibilityId("FormulaBar");
        WebElement sellName = exSession.findElementByName(name);
        addAct.doubleClick(sellName).build().perform();
        elementFormulaBar.click();
        System.out.println("Before inpet sum formula");

        exSession.findElementByName("Сумма").click();
        Thread.sleep(500);
        exSession.findElementByName("Сумма").click();
        Thread.sleep(500);
        exSession.findElementByName("Сумма").click();

        exSession.findElementByName("Лист1").click();
        elementFormulaBar.click();
        System.out.println("Print.Print");
        System.out.println("A6="+exSession.findElementByAccessibilityId("A6").getAttribute("Value"));

//        addAct.doubleClick(exSession.findElementByName("\"A\" 8")).build().perform();
//        addAct.doubleClick(exSession.findElementByName("\"A\" 6")).build().perform();

//        elementFormulaBar.sendKeys("=СУММ(");
//        elementFormulaBar.sendKeys(Keys.END+"=СУММ("+start+":"+finish+")");
//        elementFormulaBar.sendKeys(Keys.ENTER);
//        exSession.findElementByName("Сумма").click();
//        Thread.sleep(500);
//        exSession.findElementByName("Сумма").click();
//        elementFormulaBar.click();
//        exSession.findElementByName("UiTask").click();

//        exSession.findElementByName("Лист1").click();
//        System.out.println("Before press END + Enter");
//        elementFormulaBar.sendKeys(Keys.END);
//        elementFormulaBar.sendKeys(Keys.ENTER);
//
//        System.out.println("Goto A1");
//        addAct.doubleClick(exSession.findElementByName(start)).build().perform();
//        elementFormulaBar.click();
//        addAct.doubleClick(exSession.findElementByName(finish)).build().perform();
//        elementFormulaBar.click();
//        addAct.doubleClick(sellName).build().perform();
//        System.out.println("Goto "+sellName);
//        addAct.doubleClick(sellName).build().perform();
//        elementFormulaBar.click();
//        System.out.println("Befor ENTER in FormulaBar");
//        elementFormulaBar.sendKeys(Keys.ENTER);
//        System.out.println("To read a sum");
//        summa = exSession.findElementByName("\"A\" 6").getAttribute("LegacyValue.Value").toString(); //getText();
        summa = exSession.findElementByName("\"A\" 6").getText(); //getText();
        System.out.println(summa + " was getText calculated in sell " + name);
//        summa = elementFormulaBar.getAttribute("LegacyAccessible.Value");
        summa = elementFormulaBar.getText();
        System.out.println(summa + " get in sell formulaBar");
//        summa = exSession.findElementByName("\"A\" 5").getAttribute("LegacyValue").toString(); // getAttribute("Value.Value"); // getAttribute("LegacyAccessible.Value");  // getText();
        summa = exSession.findElementByName("\"A\" 5").getText(); // getAttribute("Value.Value"); // getAttribute("LegacyAccessible.Value");  // getText();
        System.out.println(summa + " was obtained from sell A5");
        return summa;
    }

}
