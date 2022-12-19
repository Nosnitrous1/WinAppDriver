import io.appium.java_client.windows.WindowsDriver;
import io.qameta.allure.*;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

//@ExtendWith(SerenityJUnit5Extension.class)
//@ExtendWith(SerenityRunner.class)
//@net.serenitybdd.junit5.SerenityTest
//@ExtendWith(SerenityJUnit5Extension.class)     //   SerenityRunner::class)
public class Excel {
    public static WindowsDriver exSession = null;
    public static WebDriverWait wait;
    Actions addAct = new Actions(exSession);
//        builder.moveToElement(insights.get(1)).build().perform();

    @BeforeAll
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

    @AfterAll
    public static void tearDown() {
        exSession.findElementByName("Закрыть").click();
        exSession.findElementByName("Не сохранять").click();
    }

    @Test
    @DisplayName("Excel test. Calculation test")
    @Description("Проверка помещения слогаемых в ячейки и суммирование их")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Desktop testing by WAD")
    @Feature("Excel testing")
    @Story("Суммирование в EXCEL")
    public void checkExcelSum() throws InterruptedException {
        Thread.sleep(3500);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("NetUIListViewItem")));
        exSession.findElementByClassName("NetUIListViewItem").click();
        System.out.println("Before Entering Data");
        Thread.sleep(3500);

        enterSymbol("A1", "10");
//        byte[] scrsht1 = allureScreenshot("После заполнения 1-й ячейки");      //
        enterSymbol("A2", "20");
//        byte[] scrsht2 = allureScreenshot("После заполнения 2-й ячейки");      //
        enterSymbol("A3", "30");
//        byte[] scrsht3 = allureScreenshot("После заполнения 3-й ячейки");      //
        enterSymbol("A4", "40");
//        byte[] scrsht4 = allureScreenshot("После заполнения 4-й ячейки");      //
        enterSymbol("A5", "50");
//        byte[] scrsht5 = allureScreenshot("После заполнения 5-й ячейки");      //
        String curSum = calcSum("A6", "A1", "A5");
        Assertions.assertEquals("150", curSum, "Incorrect sum");
        byte[] scrsht = allureScreenshot("Фотка страницы результата");      //
        makeScreenshot("Excel_");
    }

    @Step("Поместить {value} в ячейку {name}")
    public void enterSymbol(String name, String value) {
        WebElement elementFormulaBar = exSession.findElementByAccessibilityId("FormulaBar");
        WebElement sellName = exSession.findElementByName(name);
        addAct.doubleClick(sellName).build().perform();
        elementFormulaBar.click();
        elementFormulaBar.sendKeys(value);
        System.out.println(value + " was inserted to sell " + name);
    }

    @Step("Расчитать сумму от {start} до {finish}, сумма = {summa}")
    public String calcSum(String name, String start, String finish) throws InterruptedException {
        WebElement elementFormulaBar = exSession.findElementByAccessibilityId("FormulaBar");
        WebElement sellName = exSession.findElementByName(name);
        addAct.doubleClick(sellName).build().perform();
        elementFormulaBar.click();
        System.out.println("Before input sum formula");

        exSession.findElementByName("Сумма").click();
        Thread.sleep(1000);
        exSession.findElementByName("Сумма").click();
        Thread.sleep(1000);
        exSession.findElementByName("Сумма").click();

        exSession.findElementByName("Лист1").click();
        elementFormulaBar.click();
//        System.out.println("LegacyValue = "+exSession.findElementByName("\"A\" 6")
//                .getAttribute("LegacyValue"));
//
//        System.out.println("Value.Value = "+exSession.findElementByName("\"A\" 6")
//                .getAttribute("Value.Value"));
        System.out.println("******************");
        String summa = exSession.findElementByName(name).getText();
//        System.out.println(summa + " was getText calculated in sell " + name);
//        summa = elementFormulaBar.getText();
//        System.out.println(summa + " get in sell formulaBar");
        return summa;
    }

    private void makeScreenshot(String testName) {
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yy");
        String sDate = ft.format(new Date());
        try {
            File screenshot = ((TakesScreenshot) exSession).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("screenShots\\screenShot_" + testName + sDate + ".png"));
//            Allure.addAttachment("My attachment", screenshot.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    @Attachment(value = "{name}", type = "image/png", fileExtension = ".png")
    public byte[] allureScreenshot(String name) {
        return ((TakesScreenshot) exSession).getScreenshotAs(OutputType.BYTES);
    }


}
