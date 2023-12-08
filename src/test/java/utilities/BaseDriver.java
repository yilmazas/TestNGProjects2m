package utilities;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseDriver {

    public static WebDriver driver;

    public static WebDriverWait wait;

    public static Faker randomGenerator;

    @BeforeClass

    public void initialOperations(){

        Logger logger = Logger.getLogger(""); // Get output logs.
        logger.setLevel(Level.SEVERE);

        driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        wait= new WebDriverWait(driver,Duration.ofSeconds(30));
        randomGenerator= new Faker();

    }

    @AfterClass
    public void endingOperations(){
        MyFunction.wait(3); // it was put here for educational purposes
        driver.quit();
    }


}
