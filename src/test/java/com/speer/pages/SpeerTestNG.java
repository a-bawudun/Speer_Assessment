package com.speer.pages;

import com.opencsv.CSVWriter;
import com.speer.utilities.BrowserUtility;
import com.speer.utilities.Driver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpeerTestNG {

    @BeforeClass
    public void setup() {
        Driver.getDriver();
    }

    @Test
    public void wikipediaLinkTest() {

        // 1.) Accepts a Wikipedia link - return/throw an error if the link is not a valid wiki link
        String givenLink = "https://en.wikipedia.org";
        BrowserUtility.isWikipediaLinkValid(givenLink);
        Driver.getDriver().get(givenLink);

        // 2) Accepts a valid integer between 1 to 20 - call it n
        int n = 20;
        if (n < 1 || n > 20) {
            throw new IllegalArgumentException("Please enter a number between 1 to 20");
        }

        // 3) Scrape the link provided in Step 1, for all wiki links embedded in the page and store them in a data structure of your choice.

        List<String> newLinks = BrowserUtility.getLinksInPage();
        List<String> allLinks = new ArrayList<>(newLinks);
       // System.out.println("allLinks = " + allLinks);
       // System.out.println(allLinks.size());
        Set<String> visitedLinks = new HashSet<>();
        Set<String> uniqueLinks = new HashSet<>();
        visitedLinks.add(givenLink);


        // 4) Repeat Step 3 for all newly found links and store them in the same data structure.
        // 5 This process should terminate after n cycles.
        for (int i = 0; i < n; i++) {
            String currentURL = newLinks.get(i);

            if (!visitedLinks.contains(currentURL)) {
                    Driver.getDriver().navigate().to(currentURL);
                    visitedLinks.add(currentURL);
                  // System.out.println("currentURL = " + currentURL);
                }

            newLinks = BrowserUtility.getLinksInPage();
           // System.out.println(i + " newLinks.size() = " + newLinks.size());
            allLinks.addAll(newLinks);
            uniqueLinks.addAll(newLinks);

           // System.out.println(i + " allLinks.size() = " + allLinks.size());
           // System.out.println(i + " uniqueLinks = " + uniqueLinks.size());
        }

       // System.out.println(visitedLinks);
       // System.out.println(visitedLinks.size());
        System.out.println("Total Links Count: " + allLinks.size());
        System.out.println("Unique Links Count: " + uniqueLinks.size());

        // Write results to a CSV file
        try (CSVWriter writer = new CSVWriter(new FileWriter("results.csv"))) {
            writer.writeNext(new String[]{"Total count", String.valueOf(allLinks.size())});
            writer.writeNext(new String[]{"Unique count", String.valueOf(uniqueLinks.size())});
            writer.writeNext(new String[]{"All found links", String.join(",", allLinks)});
        } catch (IOException e) {
            System.out.println("Error writing results to file.");
        }
    }

    @AfterMethod
    public void tearDown() {
        Driver.closeDriver();
    }
}