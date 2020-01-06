package sample;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;



public class restApi {
    public static String username = "";
    public static String password = "";
    public static String access_token = "";
 //   public String magayoAPIKey = "qpPN4BEC3dXwMgNhE7";

   public static String getToken() {

       try{
           String url ="https://www.lotterytracker.ca/api/auth";
           URL obj = new URL(url);
         //  HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);
           HttpURLConnection con = (HttpURLConnection) obj.openConnection();
           String json = new JSONObject()
                   .put("UserName",username)
                   .put("Password",password).toString();

           System.out.println(json);

           //Used for special code requiring base64 bytes
        //   String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

           con.setRequestMethod("POST");
           con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:27.0) Gecko/20100101 Firefox/27.0.2 Waterfox/27.0");
           con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
           con.setDoOutput(true);
           DataOutputStream wr = new DataOutputStream(con.getOutputStream());
           wr.writeBytes(json);
           wr.flush();
           wr.close();







          int responseCode = con.getResponseCode();

           System.out.println("\nSending 'GET' request to URL : " + url);

           System.out.println("Response Code : " + responseCode);
           BufferedReader in = new BufferedReader(
                   new InputStreamReader(con.getInputStream()));
           String inputLine;
           StringBuffer response = new StringBuffer();
           while ((inputLine = in.readLine()) != null) {
               response.append(inputLine);
           }
           in.close();
           //print result
           System.out.println(response.toString());


           // below code converts the json response to json object and reads each values
           JSONObject jsonObj = new JSONObject(response.toString());
          access_token = jsonObj.getString("LTToken");
           System.out.println("access_token : "+ access_token);


           return access_token;


       } catch (IOException ioe) {
           ioe.printStackTrace();
       } catch (JSONException e) {
           e.printStackTrace();
       } finally {
       }


       return access_token;
   }

   public static int[] getWinningNums(LocalDate drawDate){
       String[] numbers = new String[0];
       String bonus;
       int[] numbersAsInt = new int[8];
       try{
           String recentDay = drawDate.toString();
           String[] daySplit = recentDay.split("-");
           String year = daySplit[0];
           String month = daySplit[1];
           String day = daySplit[2];

           String url ="https://www.lotterytracker.ca/api/winningNumbers?day="+ day + "&month=" + month + "&year=" + year;
           URL obj = new URL(url);
          // HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);
           HttpURLConnection con = (HttpURLConnection) obj.openConnection();
           String auth = new String("LTToken:"+ access_token);


           String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());


           con.setRequestMethod("GET");
           con.setRequestProperty("Authorization", "Basic " + encodedAuth);
           con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:27.0) Gecko/20100101 Firefox/27.0.2 Waterfox/27.0");
           con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

           con.setDoOutput(true);

           int responseCode = con.getResponseCode();

           System.out.println("\nSending 'GET' request to URL : " + url);

           System.out.println("Response Code : " + responseCode);
           BufferedReader in = new BufferedReader(
                   new InputStreamReader(con.getInputStream()));
           String inputLine;
           StringBuffer response = new StringBuffer();
           while ((inputLine = in.readLine()) != null) {
               response.append(inputLine);
           }
           in.close();
           //print result
           System.out.println(response.toString());


           // below code converts the json response to json object and reads each values
           JSONArray jsonObj = new JSONArray(response.toString());
           JSONObject draw = new JSONObject(jsonObj.get(0).toString());
           numbers = draw.getString("Regular").split(",");
           bonus = draw.getString("Bonus");


           for (int i = 0; i< numbers.length;i++){
               numbersAsInt[i] = Integer.parseInt(numbers[i]);
           }
            numbersAsInt[numbers.length] = Integer.parseInt(bonus);

           //String winningNums = jsonObj.getString("ID");



       }catch(IOException e){
        e.printStackTrace();
       } catch (JSONException e) {
           e.printStackTrace();
       }
        return numbersAsInt;
   }

       public static void main (String[]args){


   }
}