package com.seleniumtraining.tatoc;

import java.util.List;



import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class Tatoc {

	public static void main(String[] args) throws InterruptedException {
		// launch the driver for chrome....
		
		WebDriver driver = new FirefoxDriver();
		driver.get("http://10.0.1.86/tatoc");
		driver.manage().window().maximize();
		// go to basic course...
        driver.findElement(By.cssSelector(".page a")).click();
		
        
        // solving grid gate.....
        driver.findElement(By.cssSelector(".greenbox")).click();
        
        /*WebDriverWait wait= new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#answer")));*/
        
        // frame Dungeon problem's solution.....
        driver.switchTo().frame(0);
		WebElement box1=driver.findElement(By.cssSelector("#answer"));
		String classBox1= box1.getAttribute("class");
	     driver.switchTo().frame(driver.findElement(By.cssSelector("#child")));
	     WebElement box2=driver.findElement(By.cssSelector("#answer"));
	     String classBox2= box2.getAttribute("class");
	     
        driver.switchTo().defaultContent();
        WebElement link;
       
	    while(!classBox1.equals(classBox2)){
	    	driver.switchTo().frame(0);
	    	link=  driver.findElement(By.cssSelector("a"));
	    	link.click();
	    	box1=driver.findElement(By.cssSelector("#answer"));
	    	classBox1= box1.getAttribute("class");
	    	driver.switchTo().frame(driver.findElement(By.cssSelector("#child")));
	    	box2= driver.findElement(By.cssSelector("#answer"));
	    	classBox2= box2.getAttribute("class");
	    	driver.switchTo().defaultContent();
        }
	    driver.switchTo().frame(0);
	    driver.findElement(By.cssSelector("a:last-child")).click();
	    
	    
	    // Dungeon problem ends here.....
	    
	    // Drag and drop problem starts here....
	   
	    WebElement to= driver.findElement(By.cssSelector("#dropbox"));
	    WebElement from= driver.findElement(By.cssSelector(".ui-draggable"));
	    Actions act= new Actions(driver);
	    act.dragAndDrop(from, to).build().perform();
	    Thread.sleep(1000);
	    driver.findElement(By.cssSelector(".page a")).click();
	    Thread.sleep(1000);
	    // Drag and drop problem ends here.....
	    
	    // Pop-up window problem's solution....
	    // Store the current window handle
	    String winHandleBefore = driver.getWindowHandle();
	    driver.findElement(By.cssSelector(".page a")).click();
	    
	 // Switch to new window opened
	    for(String winHandle : driver.getWindowHandles()){
	        driver.switchTo().window(winHandle);
	    }
	    
	    driver.findElement(By.cssSelector("#name")).sendKeys("Alexandra parrish");
	    driver.findElement(By.cssSelector("#submit")).click();
	    // Close the new window, if that window no more required
	  //  driver.quit();
	    
	    // Switch back to original browser (first window)
	    driver.switchTo().window(winHandleBefore);
	  //  driver.close();
	      List<WebElement> elements=driver.findElements(By.cssSelector(".page a"));
	      elements.get(1).click();
	    // Pop-up windows hurdle ends....
	    
	      Thread.sleep(1000);
	    //Token generation problem's solution starts.....
	      driver.findElement(By.cssSelector(".page a")).click();
	      Thread.sleep(200);
	       String tokenText= driver.findElement(By.cssSelector("#token")).getText();
	       String token= tokenText.substring(7, tokenText.length());
	       Cookie cookie = new Cookie("Token", token);
	       driver.manage().addCookie(cookie);
	       elements=driver.findElements(By.cssSelector(".page a"));
		   elements.get(1).click();
	    //Token generation solution ends....  
	      
		   // Now  finally close the browser....
		  driver.quit();
	}

}
