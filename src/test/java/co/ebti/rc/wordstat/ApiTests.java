package co.ebti.rc.wordstat;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class ApiTests {

    private final String USER_AGENT = "Mozilla/5.0";
    private final String getGroupsApiLink = "/api/v2/groups.json";
    private final String getTreeApiLink = "/api/v2/tree.json";


    @Test
    public void apiGetGroups() throws Exception {
        String url = Hostname.getHostName() + getGroupsApiLink +"?secret_token="+Data.token;

        //Send Get to URL and retrieve result
        HashMap result = sendGetTo(url);

        //Check statusCode & body for correct values
        assertEquals("Wait for 200, but got " + result.get("statusCode").toString(), true, result.get("statusCode").toString().equals("200"));
        JSONArray responseBody = new JSONArray(result.get("responseBody").toString());
        assertEquals("First record contain text: 'name', 'keywords_count', 'id'", responseBody.get(0).toString()
                .contains("name") && responseBody.toString()
                .contains("keywords_count") && responseBody.toString()
                .contains("id"), true);
    }

    @DataProvider
    public Object[] [] trueOrFalse () {
        return new Object[][]{
                //email
                {"true"},
                {"false"}
        };
    }

    @Test (dataProvider = "trueOrFalse")
    public void apiGetTree(String boo) throws Exception {
        //group_id

        //String url = Hostname.getHostName() + getGroupsApiLink +"?secret_token="+Data.token;
        String url = Hostname.getHostName() + getTreeApiLink + "?secret_token=" + Data.token+ "&group_id=" + "bea46bb0fea00131c92f448a5b2c34d2" + "&unique=" + boo;


        //Send Get to URL and retrieve result
        HashMap result = sendGetTo(url);

        //Check statusCode & body for correct values
        assertEquals("Wait for 200, but got "+ result.get("statusCode").toString(), true, result.get("statusCode").toString().equals("200"));

        //Check some values from first record
        JSONObject firstRecord = (JSONObject) result.get("firstGroupRecord");
        assertEquals("First record contain text: 'suggested', 'phrase', 'alias_phrases'", firstRecord.toString()
                .contains("suggested") && firstRecord.toString()
                .contains("phrase") && firstRecord.toString()
                .contains("alias_phrases"), true);
        assertEquals("First record contain text: 'weight', 'id', 'group_aliases_weights'", firstRecord.toString()
                .contains("weight") && firstRecord.toString()
                .contains("id") && firstRecord.toString()
                .contains("group_aliases_weights"), true);
    }

    // HTTP GET request
    private HashMap sendGetTo(String url) throws Exception {

        HashMap hashMap = new HashMap();

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);

        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        JSONArray resultJsonArray = new JSONArray(result.toString());
        JSONObject firstGroupRecord = (JSONObject) resultJsonArray.get(0);

        hashMap.put("statusCode", response.getStatusLine().getStatusCode());
        hashMap.put("responseBody", result.toString());
        hashMap.put("firstGroupRecord", firstGroupRecord);

        return hashMap;
    }

    public static void main(String[] args) throws Exception {

        ApiTests http = new ApiTests();

        System.out.println("\nTesting 2 - Send Http POST request");
        http.sendPost();
        //System.out.println(sendGetTo("").toString());

    }





    // HTTP POST request
    private void sendPost() throws Exception {
        String url = "http://wordstat.rc.ebti.co/api/v2/anchor_file.json";


        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("secret_token", "bMuIW136cE4PyhjElgXgwrhilgSl6KZMb18vuvos"));
        urlParameters.add(new BasicNameValuePair("group[id]", "86025ac040ca01328894002590e75102"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());

    }

}