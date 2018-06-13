package kz.halyqsoft.univercity.rs.client;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Author: Omarbek Dinassil
 * Created: 22.03.2018 11:07
 */
public class TestRestClient {

    private static final String REAL_URL = "http://78.40.108.39:8080/univercity-rs-server/service/";
    private static final String LOCAL_URL = "http://localhost:8080/service/";

    public static void main(String[] args) throws Exception {

//        getUserArrival();
//        getUsers();
        getPhoto();
    }

    private static void getPhoto() throws IOException {
        String request = "{\n" +
                "   \"login\":          \"system\",\n" +
                "   \"user_login\":     \"180157\",\n" +
                "   \"passwd\":         \"fda0f1f49f35c4bf82153072e1414ce65ce30b32cf4e7b0c98edbdb597338d15\"\n" +
                "}";
        System.out.println(request);
        StringBuilder mainSB = getStringBuilder(request, REAL_URL + "photo");
        JSONObject myResponse = new JSONObject(mainSB.toString());
        System.out.println(myResponse.toString());
    }

    private static void getUsers() throws IOException {
        String request = "{\n" +
                "   \"login\":          \"a_bortan\",\n" +
                "   \"query\":          \"select id,first_name,last_name,middle_name,email from users" +
                " where login='a_bortan'\",\n" +
                "   \"passwd\":         \"c775e7b757ede630cd0aa1113bd102661ab38829ca52a6422ab782862f268646\"\n" +
                "}";
        System.out.println(request);
        StringBuilder mainSB = getStringBuilder(request, REAL_URL + "login");
        JSONObject myResponse = new JSONObject(mainSB.toString());
        System.out.println(myResponse.toString());
    }

    private static void getUserArrival() throws IOException {//180061
        String request = "{\n" +
                "   \"user_code\":          \"456789\",\n" +
                "   \"login\":              \"system\",\n" +
                "   \"turnstile_type_id\":  \"5\",\n" +
                "   \"come_in\":            \"true\",\n" +
                "   \"passwd\":             \"fda0f1f49f35c4bf82153072e1414ce65ce30b32cf4e7b0c98edbdb597338d15\"\n" +
                "}";
        StringBuilder mainSB = getStringBuilder(request, REAL_URL + "user_arrival");
        String response = URLDecoder.decode(mainSB.toString(), "UTF-8");
        System.out.println("Response:\n" + response);
    }

    private static StringBuilder getStringBuilder(String request, String urlType) throws IOException {
        URL url = new URL(urlType);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.connect();
        OutputStream os = conn.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        osw.write(request);
        osw.flush();
        osw.close();
        int rc = conn.getResponseCode();
        System.out.println("Response code: " + rc);
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String inputLine;
        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine);
        }
        br.close();
        return sb;
    }
}
