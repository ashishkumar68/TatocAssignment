package com.seleniumtraining.tatoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class FileHandleProblem {

	public static void main(String[] args) throws InterruptedException, IOException {
		
		
		String downloadDir = System.getProperty("user.home") + "//Downloads";
		System.setProperty("webdriver.chrome.driver", downloadDir+"//chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.get("http://10.0.1.86/tatoc/advanced/file/handle");
		driver.findElement(By.cssSelector(".page a")).click();
		Thread.sleep(3000);
		BufferedReader in = new BufferedReader(new FileReader(downloadDir+"//file_handle_test.dat"));
		String line;
		List<String> fileStrings= new ArrayList<String>();
		while((line = in.readLine()) != null)
		{
		    fileStrings.add(line);
		}
		in.close();
		String signature= fileStrings.get(2);
	    signature= signature.substring(11);
	    driver.findElement(By.cssSelector("#signature")).sendKeys(signature);
	    driver.findElement(By.cssSelector(".submit")).click();
	}

}
