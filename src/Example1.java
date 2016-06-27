import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;



public class Example1 {
	
	public WebDriver driver;
	public static void main(String[] args) throws InterruptedException {
		System.out.println("*******************");
		System.out.println("launching firefox browser");
		WebDriver driver=new FirefoxDriver();  
	    driver.get("https://accounts.google.com/ServiceLogin?service=mail&continue=https://mail.google.com/mail/#identifier");
	    
		WebElement id = driver.findElement(By.name("Email"));		
		id.sendKeys("ashishgoel68");
	    
		WebElement button = driver.findElement(By.name("signIn"));
		button.submit();
		Thread.sleep(3000);
		WebElement check = driver.findElement(By.name("PersistentCookie"));	
		check.click();
		WebElement pass = driver.findElement(By.name("Passwd"));		
		pass.sendKeys("kismatthreeidiotspass@123");
		button.submit();
		Thread.sleep(4000);
		List<WebElement> elements= driver.findElements(By.xpath("//*[contains(@class,'zA y0')]"));
		System.out.println(elements.size());
		Thread.sleep(2000);
		/*if(elements.size()>0){
			elements.get(0).click();
			Thread.sleep(20000);
			driver.get("https://mail.google.com/mail/u/0/#inbox");
			Thread.sleep(3000);
			elements= driver.findElements(By.xpath("//*[contains(@class,'zA y0')]"));
			System.out.println(elements.size());
		}*/
		
		
		
		List<WebElement> contentElements= driver.findElements(By.cssSelector(".xY.a4W > div > div >div .y2"));
		for(int i=0;i<contentElements.size();i++){
			System.out.println(contentElements.get(i).getText());
		}
	
		
		
		System.out.println("done...");
	}

}
