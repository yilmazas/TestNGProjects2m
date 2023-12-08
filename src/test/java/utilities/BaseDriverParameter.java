package utilities;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseDriverParameter {

    public WebDriver driver;

    public static WebDriverWait wait;

    public static Faker randomGenerator;

    @BeforeClass
    @Parameters("browserType")

    public void initialOperations(String browserType){

        Logger logger = Logger.getLogger(""); // Get output logs.
        logger.setLevel(Level.SEVERE);

        switch (browserType.toLowerCase()){
            case "firefox": driver=new FirefoxDriver();
            break;
            case "edge": driver= new EdgeDriver();
            break;
            case "safari": driver= new SafariDriver();
            break;
            default: driver= new ChromeDriver();
        }
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

