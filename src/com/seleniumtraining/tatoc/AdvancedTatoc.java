package com.seleniumtraining.tatoc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class AdvancedTatoc {

	

	
	public static void main(String[] args) throws InterruptedException, SQLException, IOException, ParseException {
		String downloadDir = System.getProperty("user.home") + "//Downloads";
		System.setProperty("webdriver.chrome.driver", downloadDir+"//chromedriver");
		WebDriver driver = new ChromeDriver();
		
		driver.get("http://10.0.1.86/tatoc");
		driver.findElement(By.cssSelector(".page a:last-child")).click();

		/*driver.findElement(By.cssSelector(".menutitle")).click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector(".menutop span:nth-last-child(2)")).click();*/
		Actions actions = new Actions(driver);
		WebElement mainMenu = driver.findElement(By.className("menutitle"));
		actions.moveToElement(mainMenu);
		actions.perform();
		 List <WebElement> we = driver.findElements(By.cssSelector(".menuitem"));
	     we.get(3).click();
	//	Thread.sleep(2000);
		
		
		String symbol= driver.findElement(By.cssSelector("#symboldisplay")).getText();
	//	System.out.println(symbol);
		Connection con=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		String id=null;
		String name=null;
		String passkey= null;
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			  
			con=DriverManager.getConnection( "jdbc:mysql://10.0.1.86:3306/tatoc","tatocuser","tatoc01"); 
		
			pstmt= con.prepareStatement("select id from identity where symbol=?;");
			pstmt.setString(1, symbol);
			rs= pstmt.executeQuery();
			if(rs.next()){
				id= rs.getString("id");
			}
		//	System.out.println(id);
			int identity= Integer.parseInt(id);
			rs.close();
			pstmt.close();
			pstmt= con.prepareStatement("select name,passkey from credentials where id=?;");
			pstmt.setInt(1, identity);
			rs= pstmt.executeQuery();
			
			if(rs.next()){
				name= rs.getString("name");
				passkey= rs.getString("passkey");
			}
			rs.close();
			pstmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		finally {
			if(rs!=null){
				rs.close();
			}
			if(pstmt!=null){
				pstmt.close();
			}
			if(con!=null){
				con.close();
			}
		}
		
		driver.findElement(By.cssSelector("#name")).sendKeys(name);
		driver.findElement(By.cssSelector("#passkey")).sendKeys(passkey);
		driver.findElement(By.cssSelector("#submit")).click();
		
		
		// Query Gate problem solved....
		/*Thread.sleep(3000);
		JavascriptExecutor js = (JavascriptExecutor)driver; 
		Thread.sleep(1000);
		js.executeScript("document.getElementsByClassName('video')[0].getElementsByTagName('object')[0].playMovie();");
		Thread.sleep(25000);
		
		driver.findElement(By.cssSelector(".page a")).click();*/
		
		Thread.sleep(2000);
		
		driver.get("http://10.0.1.86/tatoc/advanced/rest");
		String sessionId= driver.findElement(By.cssSelector("#session_id")).getText();
		sessionId=sessionId.substring(12);
		System.out.println(sessionId);
		URL url= new URL("http://10.0.1.86/tatoc/advanced/rest/service/token/"+sessionId);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
		throw new RuntimeException("Failed str: HTTP error code :"
		+ conn.getResponseCode());
		}

		Scanner scan = new Scanner(url.openStream());
		String str = new String();
		while (scan.hasNext())
		str += scan.nextLine();
		scan.close();

		System.out.println("str : " + str);
	
		JSONParser parser = new JSONParser();
		JSONObject object= (JSONObject) parser.parse(str);
		String token= (String) object.get("token");
		System.out.println(token);
		
		url= new URL("http://10.0.1.86/tatoc/advanced/rest/service/register");
		conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
	
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		String string= "id="+sessionId+"&signature="+token+"&allow_access=1";
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(string);
		wr.flush();
		wr.close();

		int responseCode = conn.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + string);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		System.out.println(response.toString());

		conn.disconnect();

	    driver.findElement(By.cssSelector(".page a")).click();
		
	    
	    Thread.sleep(3000);
	    
	    driver.findElement(By.cssSelector(".page a")).click();
		Thread.sleep(3000);
		BufferedReader in1 = new BufferedReader(new FileReader(downloadDir+"//file_handle_test.dat"));
		String line;
		List<String> fileStrings= new ArrayList<String>();
		while((line = in1.readLine()) != null)
		{
		    fileStrings.add(line);
		}
		in1.close();
		String signature= fileStrings.get(2);
	    signature= signature.substring(11);
	    driver.findElement(By.cssSelector("#signature")).sendKeys(signature);
	    driver.findElement(By.cssSelector(".submit")).click();
	}
	
	 
	
	
		 
}
