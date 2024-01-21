package LoginScenarios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ExcelReadingGuru99 {
	Cell cell;
	List<String> userNameList = new ArrayList<String>();
	List<String> passwordList = new ArrayList<String>();

	public void excelReading() throws IOException {

		FileInputStream excel = new FileInputStream("C:\\Users\\Rafeek\\OneDrive\\Documents\\testData1.xlsx");
		Workbook workbook = new XSSFWorkbook(excel);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row rowvalue = rowIterator.next();

			Iterator<Cell> cellIterator = rowvalue.iterator();
			int i = 2;
			while (cellIterator.hasNext()) {
				if (i % 2 == 0) {
					userNameList.add(cellIterator.next().toString());
				} else {
					passwordList.add(cellIterator.next().toString());
				}
				i++;
			}
		}
		System.out.println(userNameList);
		System.out.println(passwordList);

	}

	public void login(String uname, String pwd) throws InterruptedException {
		System.setProperty("webdriver.driver.chrome", "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
		WebDriver driver = new ChromeDriver();

		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		Thread.sleep(5000);

		WebElement userName = driver.findElement(By.name("username"));
		userName.sendKeys(uname);
		WebElement passWord = driver.findElement(By.name("password"));
		passWord.sendKeys(pwd);
		WebElement loginButton = driver
				.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[3]/button"));
		loginButton.click();
	}

	public void executeTest() throws InterruptedException {
		for (int i = 0; i < userNameList.size(); i++) {
			login(userNameList.get(i), passwordList.get(i));
		}

	}

	public static void main(String[] args) throws IOException, InterruptedException {
		ExcelReadingGuru99 excelread = new ExcelReadingGuru99();
		excelread.excelReading();
		excelread.executeTest();

	}

}
