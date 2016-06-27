package com.seleniumtraining.tatoc;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BasicTatocUsingJS {

	public static void main(String[] args) {
		
		String downloadDir = System.getProperty("user.home") + "//Downloads";
		System.setProperty("webdriver.chrome.driver", downloadDir+"//chromedriver");
		WebDriver driver = new ChromeDriver();
		
		driver.get("http://10.0.1.86/tatoc");
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("document.getElementsByTagName('a')[0].click();");
		
		js.executeScript("document.getElementsByClassName('greenbox')[0].click();");

		String color1=(String) js.executeScript("return document.getElementById('main').contentDocument.getElementById('answer').className;");
		String color2= (String)js.executeScript("return document.getElementById('main').contentDocument.getElementById('child').contentDocument.getElementById('answer').className;");
	    
		while(!color1.equals(color2)){
			js.executeScript("document.getElementById('main').contentDocument.getElementsByTagName('a')[0].click();");
			color2= (String)js.executeScript("return document.getElementById('main').contentDocument.getElementById('child').contentDocument.getElementById('answer').className;");
			
		}
		js.executeScript("document.getElementById('main').contentDocument.getElementsByTagName('a')[1].click();");
		js.executeScript("document.getElementById('dragbox').setAttribute('style','position: relative; left: 32px; top: -59px;')");
	    js.executeScript("document.querySelector('a[onclick^=gonext]').click();");
	    
	    String winHandleBefore = driver.getWindowHandle();
	    js.executeScript("document.getElementsByTagName('a')[0].click();");
	    for(String winHandle : driver.getWindowHandles()){
	        driver.switchTo().window(winHandle);
	    }
	    js.executeScript("document.getElementById('name').value='Alexandra Parrish';");
	    js.executeScript("document.getElementById('submit').click();");
	    driver.switchTo().window(winHandleBefore);
	    js.executeScript("document.getElementsByTagName('a')[1].click();");
	
	    js.executeScript("document.getElementsByTagName('a')[0].click();");
	    String tokenString=(String)js.executeScript("return document.getElementById('token').innerHTML;");
	    String token= tokenString.substring(7, tokenString.length());
	    
	    Cookie cookie = new Cookie("Token", token);
	    driver.manage().addCookie(cookie);
	    js.executeScript("document.getElementsByTagName('a')[1].click();");
	    driver.quit();
	}

}
