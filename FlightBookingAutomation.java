import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.time.Duration;

public class FlightBookingAutomation {
    public static void main(String[] args) {
        // Set up WebDriver using WebDriver Manager
        WebDriverManager.chromedriver().setup();
        WebDriver driver = null;

        try {
            // Initialize ChromeOptions for headless mode
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");  // Run in headless mode

            // Initialize WebDriver with the options
            driver = new ChromeDriver(options);

            // Step 1: Navigate to the website
            driver.get("https://www.goindigo.in/");
            System.out.println("Page Title: " + driver.getTitle());
            System.out.println("Page URL: " + driver.getCurrentUrl());

            // Step 2: Verify landing on the correct page (title or URL check)
            if (!driver.getTitle().contains("IndiGo")) {
                System.out.println("Not on the correct page!");
                return;
            }

            // Create WebDriverWait object for waiting elements
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 3: Wait and click on the "Book" option
            WebElement bookMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='book-flight-tab']")));
            bookMenu.click();

            // Step 4: Wait and click on the "Flight" option
            WebElement flightOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("oneWay")));
            flightOption.click();

            // Step 5: Select passengers (2 Adults)
            WebElement paxDropdown = driver.findElement(By.xpath("//div[contains(@class, 'paxDropDown')]"));
            paxDropdown.click();

            WebElement addAdultButton = driver.findElement(By.xpath("//button[@aria-label='Adult Plus']"));
            addAdultButton.click(); // Increases the count to 2
            WebElement doneButton = driver.findElement(By.xpath("//button[text()='Done']"));
            doneButton.click();

            // Step 6: Enter "From" and "To" locations
            WebElement fromInput = driver.findElement(By.id("bookFlightOriginInput"));
            fromInput.sendKeys("Hyderabad");
            driver.findElement(By.xpath("//li[contains(text(),'Hyderabad')]")).click();

            WebElement toInput = driver.findElement(By.id("bookFlightDestinationInput"));
            toInput.sendKeys("Delhi");
            driver.findElement(By.xpath("//li[contains(text(),'Delhi')]")).click();

            // Step 7: Select travel date (1 month from today)
            WebElement dateField = driver.findElement(By.id("departureCalendar"));
            dateField.click();

            // Get current date and add 1 month
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 1);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String travelDate = sdf.format(calendar.getTime());

            // Fixing XPath for travel date selection
            WebElement travelDateElement = driver.findElement(By.xpath("//span[text()='" + travelDate + "']"));
            travelDateElement.click();

            // Step 8: Search for flights
            WebElement searchButton = driver.findElement(By.xpath("//button[text()='Search Flight']"));
            searchButton.click();

            // Step 9: Select the first recommended flight
            WebElement selectFlight = driver.findElement(By.xpath("//button[text()='Select']"));
            selectFlight.click();

            // Step 10: Proceed to the next step and enter passenger details
            WebElement nextButton = driver.findElement(By.xpath("//button[text()='Next']"));
            nextButton.click();

            WebElement maleOption = driver.findElement(By.id("radioMale"));
            maleOption.click();

            WebElement firstNameField = driver.findElement(By.id("firstName"));
            firstNameField.sendKeys("John");

            WebElement lastNameField = driver.findElement(By.id("lastName"));
            lastNameField.sendKeys("Doe");

            WebElement dobField = driver.findElement(By.id("dateOfBirth"));
            dobField.sendKeys("01/01/1990");

            System.out.println("Automation completed successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                // Close the browser
                driver.quit();
            }
        }
    }
}
