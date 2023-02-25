package com.speer.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class BrowserUtility {
    public static void waitForAllLinks(String tag){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),10);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName(tag)));
    }

    public static void isWikipediaLinkValid(String link) {

        try {
           link.matches("^https?://en\\.wikipedia\\.org/wiki/[\\w()]+$");

        } catch (IllegalArgumentException ex) {
            System.out.println(ex + " is not valid url");
        }
    }

    public static List<String> getLinksInPage() {

        List<String> newlyFoundedLinks = new ArrayList<>();
//        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),10);
//        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));
        waitForAllLinks("a");
        //driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        List<WebElement> allLinks = Driver.getDriver().findElements(By.tagName("a"));
        for (WebElement element : allLinks) {
            String newlyUrl = element.getAttribute("href");

            if (newlyUrl != null) {
                newlyFoundedLinks.add(newlyUrl);
            }


        }
        return newlyFoundedLinks;
    }
}
