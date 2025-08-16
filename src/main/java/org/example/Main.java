//package org.example;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//
////TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
//// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//public class Main {
//    public static void main(String[] args) {
//
//        System.setProperty("webdriver.chrome.driver",
//                "C:\\Users\\GariE\\Desktop\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
//
//        // Setup Chrome options
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--start-maximized");
//
//        WebDriver driver = new ChromeDriver(options);
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//
//        try {
//            // Open the website
//            driver.get("https://chatgpt.com");
//
//            // Wait 2 seconds
//            Thread.sleep(2000);
//
//            // Wait until the "Accept all" button is clickable, then click it
//
//            // Locate the SVG by the <path> structure (or class if available)
//            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(
//                    // Try locating by aria-label or role first, more stable than SVG path
//                    By.xpath("//button[@aria-label='Close' or contains(@class,'close') or @role='button']")
//            ));
//
//            closeButton.click();
//
//
//            WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(
//                    By.xpath("//button[contains(@class,'btn-secondary') and .//div[text()='Accept all']]")
//            ));
//
//
//            acceptButton.click();
//
//            // Wait until the input area is visible
//            WebElement inputArea = wait.until(ExpectedConditions.elementToBeClickable(
//                    By.xpath("//p[@data-placeholder='Ask anything']")
//            ));
//
//// Click on the field to focus
//            inputArea.click();
//
//// Type text
//            inputArea.sendKeys("Hello, this is a test!");
//
//// Optionally, press Enter if needed to submit
//            inputArea.sendKeys(org.openqa.selenium.Keys.ENTER);
//
//            System.out.println("Typed text successfully.");
//
//
//            // Now you can continue with typing in fields, etc.
//            System.out.println("Clicked 'Accept all' button successfully.");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//}