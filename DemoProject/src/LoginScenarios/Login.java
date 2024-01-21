package LoginScenarios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Login {

	String[][] data = null;
	WebDriver driver;

	@DataProvider(name = "loginData")
	public String[][] loginDataprovider() throws BiffException, IOException {
		data = getExcelDAta();
		return data;
	}

	public String[][] getExcelDAta() throws BiffException, IOException {

		FileInputStream excel = new FileInputStream("C:\\Users\\Rafeek\\OneDrive\\Documents\\testdata.xls");

		Workbook workbook = Workbook.getWorkbook(excel);

		Sheet sheet = workbook.getSheet(0);

		int rowsCount = sheet.getRows();
		int columnsCount = sheet.getColumns();

		String testData[][] = new String[rowsCount - 1][columnsCount];
		for (int i = 1; i < rowsCount; i++) {
			for (int j = 0; j < columnsCount; j++) {
				testData[i - 1][j] = sheet.getCell(j, i).getContents();

			}
		}
		return testData;

	}

	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.driver.chrome", "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
		driver = new ChromeDriver();
		
	}

	@Test(dataProvider = "loginData")
	public void loginWithCorrectUsernamePwd(String uname, String pwd) throws InterruptedException {
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		Thread.sleep(5000);

		WebElement userName = driver.findElement(By.name("username"));
		userName.sendKeys(uname);
		WebElement passWord = driver.findElement(By.name("password"));
		passWord.sendKeys(pwd);
		WebElement loginButton = driver
				.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[3]/button"));
		loginButton.click();
		System.out.println("Demo PRoject");

	}

	@AfterTest
	public void AfterTest() {
		driver.quit();
	}
}
