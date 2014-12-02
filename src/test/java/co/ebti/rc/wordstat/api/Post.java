package co.ebti.rc.wordstat.api;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by Korniev.Oleksandr on 02.12.2014.
 */

public class Post {

    private final String USER_AGENT = "Mozilla/5.0";
    private final String anchorPostUrl = "http://wordstat.rc.ebti.co/api/v2/anchor_file.json";


    @Test
    public void getAnchorFile() throws Exception {
        //Generate POST request body
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("secret_token", "bMuIW136cE4PyhjElgXgwrhilgSl6KZMb18vuvos"));
        urlParameters.add(new BasicNameValuePair("group[id]", "40059c20457d013288d4002590e75102"));
        urlParameters.add(new BasicNameValuePair("template_keywords", "8"));
        urlParameters.add(new BasicNameValuePair("additional_keywords[][domain]", "false"));
        urlParameters.add(new BasicNameValuePair("additional_keywords[][frequency]", "8"));
        urlParameters.add(new BasicNameValuePair("additional_keywords[][keyword]", "stopppp"));
        urlParameters.add(new BasicNameValuePair("additional_keywords[][domain]", "false"));
        urlParameters.add(new BasicNameValuePair("additional_keywords[][frequency]", "33"));
        urlParameters.add(new BasicNameValuePair("additional_keywords[][keyword]", "waittttt"));

        //Send POST to URL and retrieve result
        HashMap result = sendPostTo(anchorPostUrl, urlParameters);

        //Check statusCode & body for correct values
        assertEquals("Expected 200, but got "+ result.get("statusCode").toString(), true, result.get("statusCode").toString().equals("200"));

        //Check some values from first record
        assertEquals(result.get("responseBody").toString().contains("stopppp")&&
                result.get("responseBody").toString().contains("waittttt"),true);
    }

    // HTTP POST request
    private HashMap sendPostTo(String url, List<NameValuePair> urlParameters) throws Exception {
        HashMap hashMap = new HashMap<>();

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", USER_AGENT);
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        //POST
        HttpResponse response = client.execute(post);

        //Migrate result to string
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {result.append(line);}

        //Generate result
        hashMap.put("statusCode", response.getStatusLine().getStatusCode());
        hashMap.put("responseBody", result);
        return hashMap;
    }
}
