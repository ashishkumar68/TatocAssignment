package com.seleniumtraining.tatoc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class RestfulApiTatocProblem {

	public static void main(String[] args) throws IOException, ParseException {
		

		WebDriver driver= new FirefoxDriver();
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
		
	}

}
