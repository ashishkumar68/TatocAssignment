package com.seleniumtraining.tatoc;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class OOPlayerTatocTask {

	public static void main(String[] args) throws InterruptedException {
		

		WebDriver driver= new FirefoxDriver();
		driver.get("http://10.0.1.86/tatoc/advanced/video/player");
		JavascriptExecutor js = (JavascriptExecutor)driver; 
		Thread.sleep(1000);
		js.executeScript("document.getElementsByClassName('video')[0].getElementsByTagName('object')[0].playMovie();");
		Thread.sleep(25000);
		
		driver.findElement(By.cssSelector(".page a")).click();
	}

}
