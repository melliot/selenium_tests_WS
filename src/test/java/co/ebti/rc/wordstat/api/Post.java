package co.ebti.rc.wordstat.api;

import co.ebti.rc.wordstat.Data;
import co.ebti.rc.wordstat.Hostname;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class Post {

    private String createProjectId;
    private final String USER_AGENT = "Mozilla/5.0";
    private final String anchorPostUrl = "http://" + Hostname.HOSTNAME + "/api/v2/anchor_file.json";
    private final String comparatorUrl = "http://" + Hostname.HOSTNAME + "/api/v2/compare.json";
    private final String addPhrasesUrl = "http://" + Hostname.HOSTNAME + "/api/v2/compare/id/phrases.json";

    private String getStatusUrl(String id){

        return "http://" + Hostname.HOSTNAME + "/api/v2/compare/"
                + id + ".json?secret_token=" + Data.token;
    }

    private String getResultsUrl(String project_id, int list_num){

        return "http://" + Hostname.HOSTNAME
                + "/api/v2/compare/" + project_id + "/list/"
                + list_num + "/results.json?secret_token=" + Data.token + "&limit=10000&cursor=0";
    }

    private String startCalculations(String id){

        return "http://" + Hostname.HOSTNAME +"/api/v2/compare/"+id+"/start.json";
    }

    public void setCreateProjectId(String createProjectId) {
        this.createProjectId = createProjectId;
    }

    public String getCreateProjectId() {

        return createProjectId;
    }

    @Test
    public void getAnchorFile() throws Exception {
        //Generate POST request body
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("secret_token", Data.token));
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

/*  //comparator not needed anymore

    @DataProvider
    public Object[] [] comparatorTestSets () {
        return new Object[][]{
                //email, error message
                {"Case1: all is ok", "secret_token", Data.token, "config", "\"buzzwords\": [\"s\", \"sh\"]", "{\"status\":\"OK\",\"project\":{\"id\":\""},
                {"Case2: without buzzwords array", "secret_token", Data.token, "config", "", "{\"status\":\"OK\",\"project\":{\"id\":\""},                               //
                {"Case3: with empty buzzword array", "secret_token", Data.token, "config", "\"buzzwords\": []", "{\"status\":\"OK\",\"project\":{\"id\":\""},
                {"Case4: with wrong buzzword name", "secret_token", Data.token, "config", "\"buzzwordsssss\": []", "{\"status\":\"OK\",\"project\":{\"id\":\""},
                {"Case5: with wrong config value", "secret_token", Data.token, "config", "111", "HTTP Error 500 Internal server error"},
                {"Case6: with wrong buzzword value", "secret_token", Data.token, "config", "\"buzzwords\": [111]", "HTTP Error 400 Bad request"},
                {"Case7: with empty buzzword value", "secret_token", Data.token, "config", "\"buzzwords\": [\"\", \"\"]", "HTTP Error 400 Bad request"},
                {"Case8: with wrong config name", "secret_token", Data.token, "configggggg", "", "HTTP Error 400 Bad request"},
                {"Case9: with wrong token name", "secret_tokAn", Data.token, "config", "", "HTTP Error 403 Forbidden"},
                {"Case10: with wrong token value", "secret_tokAn", Data.token+"RootsBloodyRoots", "config", "", "HTTP Error 403 Forbidden"}
        };
    }

    @Test(dataProvider = "comparatorTestSets")
    public void comparator(String caseAbout, String tokenName, String tokenValue, String configName, String buzzwordsArray, String expectedResult){
        String urlParams = "{\""+tokenName+"\": \""+tokenValue+"\",\""+configName+"\":{"+buzzwordsArray+"}}";
        String actualResult = executePost(comparatorUrl, urlParams);
        Assert.assertTrue(actualResult.contains(expectedResult), caseAbout + ". Actual result is: " + actualResult);
        System.out.println(actualResult);

    @Test
    public void addPhrases(){
        String caseAbout = "";
        String expectedResult = "   ";
        String urlParams = "{\"secret_token\": \""+Data.token+"\", \"list_num\": 1, \"phrases\": [{\"id\": 123, \"string\": \"asd\"}]}";
        String actualResult = executePost(addPhrasesUrl, urlParams);
        System.out.println(actualResult);
        Assert.assertTrue(actualResult.contains(expectedResult), caseAbout + ". Actual result is: " + actualResult);
    }
    }*/

    @Test (groups = "startCalc")
    public void startCalculations() throws Exception {
        String urlParams = "{\"secret_token\": \""+Data.token+"\", \"config\": {\"buzzwords\": [\"с\", \"на\"]}}";
        JSONObject actualResult = new JSONObject(executePost(comparatorUrl, urlParams));
        JSONArray idArray = new JSONArray("[" + actualResult.get("project") + "]");
        String startCalculationsID =  new JSONObject(idArray.get(0).toString().toString()).get("id").toString();
        //          http://wordstat.lvlp.co/api/v2/compare/id/start.json


        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("secret_token", Data.token));

        //Send POST to URL and retrieve result
        HashMap result = sendPostTo(startCalculations(startCalculationsID), urlParameters);
        assertTrue("Received message: " + result.toString(), result.toString()
                .contains("statusCode=200")&result.toString()
                .contains("{\"status\":\"OK\"}"));

        setCreateProjectId(startCalculationsID);
        System.out.println(getCreateProjectId());
    }

    @Test (groups = "startCalc", dependsOnMethods = "startCalculations")
    public void getStatus() throws Exception {
        Get get = new Get();
        String result = get.sendGetTo(getStatusUrl(getCreateProjectId())).toString();
        System.out.println(result);
        assertTrue("Received message: " + result, result.contains("statusCode=200")
                && result.contains("responseBody={\"status\":\"OK\",\"state\"")
        );
    }

    @Test (groups = "startCalc", dependsOnMethods = "getStatus")
    public void getResults() throws Exception {
        Get get = new Get();
        String result = get.sendGetTo(getResultsUrl(getCreateProjectId(), 0)).toString();
        System.out.println(result);
        assertTrue("Received message: " + result,
                result.contains("statusCode=200")
                && result.contains("responseBody={\"status\":\"OK\",\"phrases\":[],\"cursor\":0}")
        );
    }

    public static String executePost(String targetURL, String urlParameters)
    {
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/json");

            connection.setRequestProperty("Content-Length", "" +
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream ());
            wr.writeBytes (urlParameters);
            wr.flush ();
            wr.close ();

            if (connection.getResponseCode()==500){return "HTTP Error 500 Internal server error";}
            if (connection.getResponseCode()==403){return "HTTP Error 403 Forbidden";}
            if (connection.getResponseCode()==400){return "HTTP Error 400 Bad request";}

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if(connection != null) {
                connection.disconnect();
            }
        }
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
