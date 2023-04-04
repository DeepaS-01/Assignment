import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ProductScraper {
    public static void main(String[] args) throws InterruptedException, IOException {

        String url = "https://www.staples.com/Computer-office-desks/cat_CL210795/663ea?icid=BTS:2020:STUDYSPACE:DESKS";
        String driverPath = "C:/webdrivers/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", driverPath);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(url);

        List<WebElement> productElements = driver.findElements(By.cssSelector("div.product-details"));
        List<String[]> productDetails = new ArrayList<String[]>();
        for (WebElement productElement : productElements) {
            String[] details = new String[6];
            details[0] = productElement.findElement(By.cssSelector("a.product-title-link")).getText();
            details[1] = productElement.findElement(By.cssSelector("span.product-price > span")).getText();
            details[2] = productElement.findElement(By.cssSelector("div.product-sku")).getText().replace("Item # ", "");
            details[3] = productElement.findElement(By.cssSelector("div.product-model")).getText().replace("Model # ", "");
            details[4] = productElement.findElement(By.cssSelector("div.product-category")).getText().replace("Category: ", "");
            details[5] = productElement.findElement(By.cssSelector("div.product-description")).getText();
            productDetails.add(details);
        }

        driver.quit();

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("product_details.csv")));
        for (String[] details : productDetails) {
            pw.println(String.join(",", details));
        }
        pw.close();

        System.out.println("Product details saved to product_details.csv");
    }
}
