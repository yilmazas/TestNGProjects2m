package testingProject4;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.BaseDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Project4 extends BaseDriver {

    String mail, password;

    /*Test Case 1: Registrations Test

➢ https://demo.nopcommerce.com/register?returnUrl=%2F Sitesine gidiniz

            ➢ Register butonuna tıklayınız

➢ First Name ve Last Name giriniz

➢ Doğum tarihi kısımlarını select ile seciniz

➢ Email giriniz

➢ Password ve password confirm giriniz

➢ Register butonuna tıklayınız

➢ Başarılı bir şekilde kaydolduğunuzu doğrulayınız.*/

    @Test(priority = 1) // it indicates which test case is going to start first
    public void registrationTest() {
        driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");

        WebElement registerLink = driver.findElement(By.linkText("Register"));
        registerLink.click();

        WebElement firstName = driver.findElement(By.id("FirstName"));
        firstName.sendKeys(randomGenerator.name().firstName());

        WebElement lastName = driver.findElement(By.id("LastName"));
        lastName.sendKeys(randomGenerator.name().lastName());

        WebElement dayMenu = driver.findElement(By.name("DateOfBirthDay"));
        Select day = new Select(dayMenu);
        day.selectByIndex(3);

        WebElement monthMenu = driver.findElement(By.name("DateOfBirthMonth"));
        Select month = new Select(monthMenu);
        month.selectByIndex(6);

        WebElement yearMenu = driver.findElement(By.name("DateOfBirthYear"));
        Select year = new Select(yearMenu);
        year.selectByIndex(5);

        mail = randomGenerator.internet().emailAddress();

        WebElement email = driver.findElement(By.id("Email"));
        email.sendKeys(mail);

        password = randomGenerator.internet().password();

        WebElement passwordElement = driver.findElement(By.id("Password"));
        passwordElement.sendKeys(password);

        WebElement confirmPasswordElement = driver.findElement(By.id("ConfirmPassword"));
        confirmPasswordElement.sendKeys(password);

        WebElement registerButton = driver.findElement(By.id("register-button"));
        registerButton.click();

        WebElement confirmationMessage = driver.findElement(By.xpath("//div[text()='Your registration completed']"));
        Assert.assertEquals("Your registration completed", confirmationMessage.getText());

    }

    @Test(priority = 2, dependsOnMethods = {"registrationTest"})
    public void loginTest() {
   /* Test Case 2: Login Test

➢ Login butonuna tıklayınız

➢ Kaydolduğunuz Email ve password giriniz

➢ LOG IN butonunna tıklayınız

➢ Başarılı bir şekilde login olduğunuzu doğrulayınız. */

        driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");

        WebElement loginLink = driver.findElement(By.linkText("Log in"));
        loginLink.click();

        WebElement email = driver.findElement(By.id("Email"));
        email.sendKeys(mail);

        WebElement passwordElement = driver.findElement(By.id("Password"));
        passwordElement.sendKeys(password);

        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Log in']"));
        loginButton.click();

        WebElement logOutLink = driver.findElement(By.linkText("Log out"));
        Assert.assertEquals(logOutLink.getText(), "Log out");

        logOutLink.click();

    }

    @Test(dataProvider = "getData", priority = 3, dependsOnMethods = {"registrationTest"})
    public void dataProviderLoginTest(String mailPro, String passwordPro) {
        /* Test Case 3: Data Provider Login

➢ Login butonuna tıklayınız

➢ Geçerli ve geçersiz Email ve password’u Data Provider metodundan aliniz

➢ LOG IN butonunna tıklayınız

➢ başarılı bir şekilde login olup olmadığınızı doğrulayınız. */

        driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");

        WebElement loginLink = driver.findElement(By.linkText("Log in"));
        loginLink.click();

        WebElement email = driver.findElement(By.id("Email"));
        email.sendKeys(mailPro);

        WebElement passwordElement = driver.findElement(By.id("Password"));
        passwordElement.sendKeys(passwordPro);

        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Log in']"));
        loginButton.click();

       // SoftAssert softAssert = new SoftAssert();
        if (mailPro.equals(mail) && passwordPro.equals(password)) {
            WebElement logOutLink = driver.findElement(By.linkText("Log out"));
            Assert.assertEquals(logOutLink.getText(), "Log out");
            //softAssert.assertEquals(logOutLink.getText(), "Log out");
            logOutLink.click();
        } else {
            WebElement failureMessage = driver.findElement(By.cssSelector("[class='message-error validation-summary-errors']"));
            Assert.assertEquals(failureMessage.getText(), "Login was unsuccessful. Please correct the errors and try again.\n" + "No customer account found");
            //softAssert.assertEquals(failureMessage.getText(), "Login was unsuccessful. Please correct the errors and try again.\n" + "No customer account found");
        }

       //softAssert.assertAll();


    }

    @DataProvider
    public Object[][] getData() {

        Object[][] data = {{mail, password}, {randomGenerator.internet().emailAddress(), randomGenerator.internet().password()}};
        return data;
    }

//    @DataProvider
//    public  Object[][] getData() {
//        return new Object[][]{{mail, password}, {randomGenerator.internet().emailAddress(), randomGenerator.internet().password()}}; // premier method
//    }

@Test(priority = 4)
    public void tabMenuTest() {
    /* Test Case 4: Tab Menu Test

➢ Tab menudeki ürünlerin isimlerini liste atiniz

➢ Tab menu listesini locate ediniz

➢ Locate ettiginiz listenin elemanlarinin tab menudeki ürünleri içerdiğini doğrulayınız. */

    driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");

    List<String> tabMenuList = new ArrayList<>(Arrays.asList("Computers", "Electronics", "Apparel", "Digital downloads", "Books", "Jewelry", "Gift Cards"));

    List<WebElement> tabMenuWebElements= driver.findElements(By.xpath("//ul[@class='top-menu notmobile'] /li"));

    for (int i = 0; i < tabMenuWebElements.size(); i++) {
        Assert.assertTrue(tabMenuWebElements.get(i).getText().contains(tabMenuList.get(i)));
    }

}
@Test(priority = 5)
    public void orderGiftsTest(){
         /* Test Case 5: Order Gifts Test

➢ Tab menuden gifts’e tıklayınız

➢ Physical giftlerden birini random olarak seciniz

➢ Açılan sayfada recipient name, sender name ve message bölümlerini doldurunuz

➢ Add To Cart’a tıklayınız

➢ Urunun sepete eklendiğini doğrulayınız */

    driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");

    List<WebElement> tabMenuWebElements= driver.findElements(By.xpath("//ul[@class='top-menu notmobile'] /li"));
    tabMenuWebElements.get(6).click();


    List<WebElement> giftCards= Collections.singletonList(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'Physical Gift Card')]"))));
   int randomNumber= (int) (Math.random()* giftCards.size());
    giftCards.get(randomNumber).click();
    WebElement recipientName= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("giftcard_44_RecipientName")));
    Actions actions= new Actions(driver);
    Action action= actions
            .moveToElement(recipientName)
            .click()
            .sendKeys(randomGenerator.name().fullName())
            .sendKeys(Keys.TAB)
            .sendKeys(randomGenerator.name().fullName())
            .sendKeys(Keys.TAB)
            .sendKeys(randomGenerator.chuckNorris().fact())
            .sendKeys(Keys.TAB)
            .sendKeys(Keys.TAB)
            .sendKeys(Keys.ENTER)
            .build();
            action.perform();

            WebElement confirmationMessage= driver.findElement(By.xpath("//p[text()='The product has been added to your '] "));
            Assert.assertTrue(confirmationMessage.getText().contains("The product has been added to your shopping cart"),"add to card was unsuccessful");
    }

@Test(priority = 6)
    public void orderComputerTest(){
        /*Test Case 6: Order Computer Test

➢ Tab menudeki Computers üzerine gidiniz

➢ Drop down dan Desktops’a tıklayınız

➢ Açılan sayfadan Build your own computer’i seciniz

➢ Random bir RAM seciniz

➢ Random bir HDD seciniz

➢ ADD TO CART’a tıklayınız

➢ Urunun başarılı bir şekilde sepete eklendiğini doğrulayınız
 */

    driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");

    List<WebElement> tabMenuWebElements= driver.findElements(By.xpath("//ul[@class='top-menu notmobile'] /li"));
    tabMenuWebElements.get(0).click();

    WebElement desktops= driver.findElement(By.linkText("Desktops"));
    desktops.click();

    WebElement buildYourOwnComputer= driver.findElement(By.linkText("Build your own computer"));
    buildYourOwnComputer.click();

    WebElement ramMenu= driver.findElement(By.cssSelector("[name='product_attribute_2']"));
    List<WebElement> options= driver.findElements(By.xpath("//select[@name='product_attribute_2'] / option"));

    Select smenu= new Select(ramMenu);
    smenu.selectByIndex((int) (Math.random()* options.size()-1)+1);

    List<WebElement> HDDs= driver.findElements(By.xpath("//input[@name='product_attribute_3']"));
    HDDs.get((int) (Math.random()* HDDs.size())).click();

    WebElement addToCard= driver.findElement(By.cssSelector("[id='add-to-cart-button-1']"));
    addToCard.click();

    WebElement confirmationMessage= driver.findElement(By.xpath("//p[text()='The product has been added to your '] "));
    Assert.assertTrue(confirmationMessage.getText().contains("The product has been added to your shopping cart"),"add to card was unsuccessful");


//    WebElement ramMenu = wait.until(ExpectedConditions.elementToBeClickable(By.id("product_attribute_2")));
//    Select ramSelect=new Select(ramMenu);
//    List<WebElement> ramOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("select[id='product_attribute_2']>option")));
//    ramSelect.selectByIndex((int) (Math.random() * ramOptions.size() - 1) + 1);


    }

    @Test(priority = 7)
    @Parameters("searchText")
    public void parameterSearchTest(String searchWord){
         /*Test Case 7: Parametreli Search Test

➢ Search’e xml den aldiginiz “Adobe Photoshop CS4” giriniz

➢ Search butonuna tıklayınız

➢ Açılan sayfadaki urun baslığının bu text’I içerdiğini doğrulayınız
 */
        driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");

        WebElement searchBox= driver.findElement(By.cssSelector("[id='small-searchterms']"));
        searchBox.sendKeys(searchWord);

        WebElement searchButton= driver.findElement(By.cssSelector("[class='button-1 search-box-button']"));
        searchButton.click();

        WebElement productTitle= driver.findElement(By.xpath("//h2[@class='product-title']/a"));
        Assert.assertEquals(productTitle.getText(),"Adobe Photoshop CS4");


    }










}



