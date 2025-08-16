package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    // ---------- Utility Methods ----------

    /** Random human-like delay */
    private static void humanDelay(int minMillis, int maxMillis) {
        try {
            int delay = ThreadLocalRandom.current().nextInt(minMillis, maxMillis + 1);
            Thread.sleep(delay);
        } catch (InterruptedException ignored) {}
    }

    /** Type text slowly, simulating human typing */
    private static void typeLikeHuman(WebElement element, String text) {
        for (char c : text.toCharArray()) {
            element.sendKeys(Character.toString(c));
            humanDelay(50, 200); // short delay between chars
        }
    }

    /** Try to click element safely, ignore if not found */
    private static void safeClick(WebDriverWait wait, By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            new Actions(wait.until(driver -> driver))
                    .moveToElement(element)
                    .pause(Duration.ofMillis(ThreadLocalRandom.current().nextInt(200, 700)))
                    .click()
                    .perform();
        } catch (TimeoutException e) {
            System.out.println("Optional element not found: " + locator);
        } catch (Exception e) {
            System.out.println("Failed to click: " + locator + " | " + e.getMessage());
        }
    }

    /** Wait for element visibility safely */
    private static WebElement safeWait(WebDriverWait wait, By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            System.out.println("Element not found: " + locator);
            return null;
        }
    }

    /** Setup Chrome with anti-bot options */
    private static ChromeDriver setupChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu", "--no-sandbox", "window-size=1920,1080",
                "--disable-blink-features=AutomationControlled",
                "--disable-infobars", "--start-maximized");

       // options.addArguments("--proxy-server=socks5://127.0.0.1:9050");


        // hide webdriver flag
        ChromeDriver driver = new ChromeDriver(options);
        driver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument",
                Map.of("source", "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})"));

        return driver;
    }

    // ---------- Main Logic ----------

    public static void main(String[] args) {



        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\GariE\\Desktop\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        ChromeDriver driver = setupChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Scanner scanner = new Scanner(System.in);

        try {
            driver.get("https://chatgpt.com");
            humanDelay(2000, 5000);

            driver.navigate().refresh();
            humanDelay(5000, 8000);

            // Handle optional checkbox (CAPTCHA-like)
            safeClick(wait, By.xpath("//input[@type='checkbox']"));

            // Handle optional close button popup
            safeClick(wait, By.xpath("//button[@aria-label='Close' or contains(@class,'close') or @role='button']"));

            // Wait for input field
            WebElement inputArea = safeWait(wait, By.xpath("//p[@data-placeholder='Ask anything']"));
            if (inputArea == null) {
                System.out.println("Input area not found. Exiting...");
                return;
            }

            // First prompt
            String prompt = "From now on, act as a self-made billionaire mentor...";
            inputArea.click();
            typeLikeHuman(inputArea, prompt);
            inputArea.sendKeys(Keys.ENTER);

            // Wait for bot response
            safeWait(wait, By.xpath("//div[contains(@class,'message')]//p[not(@data-placeholder)]"));

            // Interactive loop
            while (true) {
                System.out.print("Enter text to send (or type 'exit' to quit): ");
                String userInput = scanner.nextLine();

                if ("exit".equalsIgnoreCase(userInput)) {
                    System.out.println("Exiting...");
                    break;
                }

                // Send message
                inputArea.click();
                typeLikeHuman(inputArea, userInput);
                inputArea.sendKeys(Keys.ENTER);

                // Wait for response
                humanDelay(1500, 3000);
                List<WebElement> responses = driver.findElements(
                        By.xpath("//div[contains(@class,'message')]//p[not(@data-placeholder)]"));

                if (!responses.isEmpty()) {
                    WebElement lastResponse = responses.get(responses.size() - 1);
                    System.out.println("Bot: " + lastResponse.getText());
                } else {
                    System.out.println("Bot: (no response detected)");
                }
            }

        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
