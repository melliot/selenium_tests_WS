package co.ebti.rc.wordstat.api;

import co.ebti.rc.wordstat.Api;
import co.ebti.rc.wordstat.Data;
import co.ebti.rc.wordstat.Hostname;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import static org.testng.AssertJUnit.assertEquals;

public class Get {

    private final String USER_AGENT = "Mozilla/5.0";
    private final String getGroupsApiLink = "/api/v2/groups.json";
    private final String getTreeApiLink = "/api/v3/tree.json";
    private final String getAnchorTemplates = "/api/v3/groups_templates.json";
    private final String getGroupKeywords = "/api/v3/group_keywords.json";
    private final String getTopics = "/api/v3/topics.json";
    private final String getTopicGroups = "/api/v3/topic_groups.json";
    private final String getLanguages = "/api/v3/languages.json";
    private final String getTopicLanguages = "/api/v3/topic_language.json";
    private final String getTopicsLanguages = "/api/v3/topics_languages.json";
    private final String getLanguageTopics = "/api/v3/language_topics.json";
    private final String getKeywordsInfo = "/api/v3/keywords_info.json";

    private final String ruCategoryId = "a525ba202523013125b654ae52c4d3c2";
    private JSONObject someGroup;


    @Test (dataProvider = "trueOrFalse")
    public void apiGetGroups(String uniqOrNot) throws Exception {
        //String url = Hostname.getHostName() + getGroupsApiLink +"?secret_token="+ Data.token + "&assembled=" + uniqOrNot;

        //Send Get to URL and retrieve result
        HashMap result = sendGetTo(Api.getGroups(Data.token, uniqOrNot));

        //Check statusCode & body for correct values
        assertEquals("Expected 200, but got " + result.get("statusCode").toString(), true, result.get("statusCode").toString().equals("200"));
        JSONArray responseBody = new JSONArray(result.get("responseBody").toString());
        String firstElementOfResponseArray = responseBody.get(0).toString();

        assertEquals("First record contain text: 'name', 'keywords_count', 'id'. Received response is:" + responseBody.toString(), firstElementOfResponseArray
                .contains("name") && firstElementOfResponseArray
                .contains("keywords_count") && firstElementOfResponseArray
                .contains("id"), true);

        //GroupID needed for other tests
        if(uniqOrNot.equals("false")) {
            for (int i =0; i < responseBody.length(); i++)
            System.out.println(responseBody.get(i));
            someGroup = (JSONObject) responseBody.get(0);
            System.out.println(responseBody.toString());
        }

        if(uniqOrNot.equals("true")) {
            for (int i =0; i < responseBody.length(); i++)
                System.out.println(responseBody.get(i));
            System.out.println(responseBody.toString());
        }
    }

    @Test //(dependsOnMethods = "apiGetGroups")
    public void apiGetGroupTrees() throws Exception {
        //String url = Hostname.getHostName() + getGroupsApiLink +"?secret_token="+ Data.token;
        String url = Hostname.getHostName() + "/api/v2/trees.json?secret_token=" + "bMuIW136cE4PyhjElgXgwrhilgSl6KZMb18vuvos" +"&group_id=8bf72080ec0201324ddd002590e75102"; //Y 836 837
        //String url = Hostname.getHostName() + "/api/v2/trees.json?secret_token=" + "bMuIW136cE4PyhjElgXgwrhilgSl6KZMb18vuvos" +"&group_id=be9db1b0ec0201324ddf002590e75102"; //G 838 839

//8b5876804d4c01328917002590e75102
        //Send Get to URL and retrieve result
        HashMap result = sendGetTo(url);

        //Check statusCode & body for correct values
        assertEquals("Expected 200, but got " + result.get("statusCode").toString(), true, result.get("statusCode").toString().equals("200"));
        JSONArray responseBody = new JSONArray(result.get("responseBody").toString());
        //String firstElementOfResponseArray = responseBody.get(0).toString();
        System.out.println(responseBody.toString());
/*        assertEquals(responseBody.toString(), firstElementOfResponseArray
                .contains("name") && firstElementOfResponseArray
                .contains("keywords_count") && firstElementOfResponseArray
                .contains("id"), true);*/
    }

    @Test //(dependsOnMethods = "apiGetGroups")
    public void apiGetGroupTreeFromPreset() throws Exception {
        //String url = Hostname.getHostName() + getGroupsApiLink +"?secret_token="+ Data.token;
        String url = Hostname.getHostName() + "/api/v2/tree.json?secret_token=" + "bMuIW136cE4PyhjElgXgwrhilgSl6KZMb18vuvos" +"&group_id=be9db1b0ec0201324ddf002590e75102&unique=false&id=838";
//8b5876804d4c01328917002590e75102
        //Send Get to URL and retrieve result
        HashMap result = sendGetTo(url);

        //Check statusCode & body for correct values
        assertEquals("Expected 200, but got " + result.get("statusCode").toString(), true, result.get("statusCode").toString().equals("200"));
        JSONArray responseBody = new JSONArray(result.get("responseBody").toString());
        //String firstElementOfResponseArray = responseBody.get(0).toString();
        System.out.println(responseBody.toString());
/*        assertEquals(responseBody.toString(), firstElementOfResponseArray
                .contains("name") && firstElementOfResponseArray
                .contains("keywords_count") && firstElementOfResponseArray
                .contains("id"), true);*/
    }

    @Test
    public void apiGetGroupsWithAnchorTemplates() throws Exception {
        String url = Hostname.getHostName() + getAnchorTemplates +"?secret_token="+Data.token;

        //Send Get to URL and retrieve result
        HashMap result = sendGetTo(url);

        //Check statusCode & body for correct values
        assertEquals("Expected 200, but got " + result.get("statusCode").toString(), true, result.get("statusCode").toString().equals("200"));

        JSONArray responseBody = new JSONArray(result.get("responseBody").toString());
        String firstElementOfResponseArray = responseBody.get(0).toString();

        System.out.println(firstElementOfResponseArray);
            assertEquals("First record contain text: 'templates', 'name', 'id'. Received response is:" + responseBody.toString(), firstElementOfResponseArray
                    .contains("templates") && firstElementOfResponseArray
                    .contains("name") && firstElementOfResponseArray
                    .contains("id"), true);

        System.out.println(result.get("responseBody").toString());
    }

    @Test
    public void apiGetTopics() throws Exception {
        String url = Hostname.getHostName() + getTopics +"?secret_token="+Data.token;

        //Send Get to URL and retrieve result
        HashMap result = sendGetTo(url);

        //Check statusCode & body for correct values
        assertEquals("Expected 200, but got " + result.get("statusCode").toString(), true, result.get("statusCode").toString().equals("200"));

        JSONArray responseBody = new JSONArray(result.get("responseBody").toString());
        String firstElementOfResponseArray = responseBody.get(0).toString();

        assertEquals("First record contain text: 'name', 'pid', 'id'. Received response is:" + responseBody.toString(), firstElementOfResponseArray
                .contains("name") && firstElementOfResponseArray
                .contains("pid") && firstElementOfResponseArray
                .contains("id"), true);
    }

    @Test
    public void apiGetTopicGroups() throws Exception {
        String url = Hostname.getHostName() + getTopicGroups + "?topic_id=" + ruCategoryId + "&secret_token="+Data.token;

        //Send Get to URL and retrieve result
        HashMap result = sendGetTo(url);

        //Check statusCode & body for correct values
        assertEquals("Expected 200, but got " + result.get("statusCode").toString(), true, result.get("statusCode").toString().equals("200"));

        JSONArray responseBody = new JSONArray(result.get("responseBody").toString());
        String firstElementOfResponseArray = responseBody.get(0).toString();

        assertEquals("First record contain text: 'name', 'id'. Received response is:" + responseBody.toString(), firstElementOfResponseArray
                .contains("name") && firstElementOfResponseArray
                .contains("id"), true);
    }

    @Test
    public void apiGetLanguages() throws Exception {
        String url = Hostname.getHostName() + getLanguages + "?secret_token="+Data.token;

        //Send Get to URL and retrieve result
        HashMap result = sendGetTo(url);

        //Check statusCode & body for correct values
        assertEquals("Expected 200, but got " + result.get("statusCode").toString(), true, result.get("statusCode").toString().equals("200"));

        JSONArray responseBody = new JSONArray(result.get("responseBody").toString());
        String firstElementOfResponseArray = responseBody.get(0).toString();

        assertEquals("First record contain text: 'name', 'id'. Received response is:" + responseBody.toString(), firstElementOfResponseArray
                .contains("name") && firstElementOfResponseArray
                .contains("id"), true);
    }

    @Test
         public void apiGetTopicLanguages() throws Exception {
        String url = Hostname.getHostName() + getTopicLanguages + "?topic_id=" + ruCategoryId + "&secret_token="+Data.token;

        //Send Get to URL and retrieve result
        HashMap result = sendGetTo(url);

        //Check statusCode & body for correct values
        assertEquals("Expected 200, but got " + result.get("statusCode").toString(), true, result.get("statusCode").toString().equals("200"));

        String responseBody = result.get("responseBody").toString();

        assertEquals("Record contain text: 'topic_id', 'lang'. Received response is:" + responseBody, responseBody
                .contains("topic_id") && responseBody
                .contains("lang"), true);
    }

    @Test
    public void apiGetTopicsLanguages() throws Exception {
        String url = Hostname.getHostName() + getTopicsLanguages + "?secret_token="+Data.token;

        //Send Get to URL and retrieve result
        HashMap result = sendGetTo(url);

        //Check statusCode & body for correct values
        assertEquals("Expected 200, but got " + result.get("statusCode").toString(), true, result.get("statusCode").toString().equals("200"));

        JSONArray responseBody = new JSONArray(result.get("responseBody").toString());
        String firstElementOfResponseArray = responseBody.get(0).toString();

        assertEquals("First record contain text: 'topic_id', 'lang'. Received response is:" + responseBody.toString(), firstElementOfResponseArray
                .contains("topic_id") && firstElementOfResponseArray
                .contains("lang"), true);
    }

    @Test
    public void apiGetLanguageTopics() throws Exception {
        String url = Hostname.getHostName() + getLanguageTopics + "?lang_id=" + "ru" + "&secret_token="+Data.token;

        //Send Get to URL and retrieve result
        HashMap result = sendGetTo(url);

        //Check statusCode & body for correct values
        assertEquals("Expected  200, but got " + result.get("statusCode").toString(), true, result.get("statusCode").toString().equals("200"));
        String responseBody1 = result.get("responseBody").toString();
        System.out.println(responseBody1);

        JSONArray responseBody = new JSONArray(result.get("responseBody").toString());
        String firstElementOfResponseArray = responseBody.get(0).toString();

        assertEquals("IN PROGRESSS First record contain text: 'topic_id'. Received response is:" + responseBody.toString(), firstElementOfResponseArray
                .contains("topic_id"), true);
    }

    @Test
    public void apiGetKeywordsInfo() throws Exception {
        String url = Hostname.getHostName() + getKeywordsInfo + "?secret_token="+Data.token + "&keywords[]=NO_WORD_HERE";

        //Send Get to URL and retrieve result
        HashMap result = sendGetTo(url);

        //Check statusCode & body for correct values
        assertEquals("Expected  200, but got " + result.get("statusCode").toString(), true, result.get("statusCode").toString().equals("200"));

        System.out.println("IN PROGRESSS " + result.get("responseBody").toString());
        JSONArray responseBody = new JSONArray(result.get("responseBody").toString());
        String firstElementOfResponseArray = responseBody.get(0).toString();
        String expectedResponce = "{\"full_exact_weight\":null,\"exact_weight\":null,\"weight\":null,\"keyword\":\"NO_WORD_HERE\"}";

        assertEquals("First record contain text" + expectedResponce + "Received response is:" + responseBody.toString(),
                expectedResponce.equals(firstElementOfResponseArray), true);
    }

    @Test (dependsOnMethods = "apiGetGroups")
    public void apiGetGroupKeywords() throws Exception {
        //String url = Hostname.getHostName() + getAnchorTemplates +"?secret_token="+Data.token;
        //String url = Hostname.getHostName() + getGroupKeywords + "?group_id=" + someGroup.get("id") + "&secret_token=" +Data.token;
        String url = Hostname.getHostName() + getGroupKeywords + "?group_id=" + "7d1a52207f9601328bd3002590e75102" + "&secret_token=" +Data.token;

        //Send Get to URL and retrieve result
        HashMap result = sendGetTo(url);

        //Check statusCode & body for correct values
        assertEquals("Expected 200, but got " + result.get("statusCode").toString(), true, result.get("statusCode").toString().equals("200"));

        //Get response body, then get first record from response array
        //JSONArray responseBody = new JSONArray(result.get("responseBody").toString());
        JSONObject responseBody = new JSONObject(result.get("responseBody").toString());
        //String firstElementOfResponseArray = responseBody.get(0).toString();
        System.out.println(responseBody);
        assertEquals("not_implemented yet", responseBody.toString().contains("WHY NPT OMPLEMENTED"),true);

/*        for(int a = 0; a < responseBody.length(); a++){
            System.out.println(responseBody.get(a).toString());
        }

        assertEquals("First record contain text: 'full_exact_weight', 'exact_weight', 'weight', 'keyword'. Received response is:" + responseBody.toString(), firstElementOfResponseArray
                .contains("exclamation_exact_weight") && firstElementOfResponseArray
                .contains("phrase") && firstElementOfResponseArray
                .contains("weight") && firstElementOfResponseArray
                .contains("exact_weight") && firstElementOfResponseArray
                .contains("variants") && firstElementOfResponseArray
                .contains("permutations") && firstElementOfResponseArray
                .contains("id"), true);*/
    }

    @Test (dependsOnMethods = "apiGetGroups")
    public void apiGetGroupKeywordsP() throws Exception {
        //String url = Api.getGroupKeywordsP(Data.token, someGroup.get("id").toString(), 1, 1000);
        String url = Api.getGroupKeywordsP(Data.token, "40059c20457d013288d4002590e75102", 1, 3);

        //Send Get to URL and retrieve result
        HashMap result = sendGetTo(url);

        //Check statusCode & body for correct values
        assertEquals("Expected 200, but got " + result.get("statusCode").toString(), true, result.get("statusCode").toString().equals("200"));
        System.out.println(sendGetTo(url).toString());


        //Get response body, then get first record from response array
        JSONObject responseBody = new JSONObject(result.get("responseBody").toString());
        System.out.println(responseBody);
        JSONArray keywords = (JSONArray) responseBody.get("keywords");
        String firstElementOfResponseArray = keywords.get(0).toString();

        for(int a = 0; a < keywords.length(); a++){
            System.out.println(keywords.get(a).toString());
        }

        assertEquals("First record contain text: 'full_exact_weight', 'exact_weight', 'weight', 'keyword'. Received response is:" + responseBody.toString(), firstElementOfResponseArray
                .contains("full_exact_weight") && firstElementOfResponseArray
                .contains("exact_weight") && firstElementOfResponseArray
                .contains("weight") && firstElementOfResponseArray
                .contains("keyword"), true);
    }

    @DataProvider
    public Object[] [] trueOrFalse () {
        return new Object[][]{
                //email
                {"true"},
                {"false"}
        };
    }

    @Test (dataProvider = "trueOrFalse", dependsOnMethods = "apiGetGroups")
    public void apiGetTree(String uniqOrNot) throws Exception {
        //String url = Hostname.getHostName() + getGroupsApiLink +"?secret_token="+Data.token;
        //String url = Hostname.getHostName() + getTreeApiLink + "?secret_token=" + Data.token+ "&group_id=" + someGroup.get("id") + "&unique=" + uniqOrNot;
        String url = Hostname.getHostName() + getTreeApiLink + "?secret_token=" + Data.token+ "&group_id=" + "760a6ff07e1001328b9b002590e75102" + "&unique=" + uniqOrNot;


        //Send Get to URL and retrieve result
        HashMap result = sendGetTo(url);
        String statusCodeResponce = result.get("statusCode").toString();
        String responceBodyStr = result.get("responseBody").toString();


        //Check statusCode & body for correct values
        assertEquals("Expected 200, but got "+ statusCodeResponce + "("+responceBodyStr+")", true, statusCodeResponce.equals("200")||statusCodeResponce.equals("417"));

        if (statusCodeResponce.equals("417")){
            assertEquals(responceBodyStr,responceBodyStr.contains("\"error\":\"tree  for group")&&responceBodyStr.contains("is not found"),true);
            return;
        }

        //Check some values from first record
        JSONArray responseBody = new JSONArray(responceBodyStr);
        String firstElementOfResponseArray = responseBody.get(0).toString();

        assertEquals("First record contain text: tags/ancestry/id/weight/origin_id/keyword_id. Actual response is: " + firstElementOfResponseArray, firstElementOfResponseArray
                .contains("tags") && firstElementOfResponseArray
                .contains("ancestry") && firstElementOfResponseArray
                .contains("id") && firstElementOfResponseArray
                .contains("weight") && firstElementOfResponseArray
                .contains("origin_id") && firstElementOfResponseArray
                .contains("keyword_id"), true);
        assertEquals("First record contain text: phrase/alias_phrases/full_exact_weight/exact_weight/node_id. Actual response is: " + firstElementOfResponseArray, firstElementOfResponseArray
                .contains("phrase") && firstElementOfResponseArray
                .contains("alias_phrases") && firstElementOfResponseArray
                .contains("full_exact_weight") && firstElementOfResponseArray
                .contains("exact_weight") && firstElementOfResponseArray
                .contains("node_id"), true);
    }

    // HTTP GET request
    public HashMap sendGetTo(String url) throws Exception {

        HashMap hashMap = new HashMap();
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);
        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {result.append(line);}

        hashMap.put("statusCode", response.getStatusLine().getStatusCode());
        hashMap.put("responseBody", result.toString());

        return hashMap;
    }
}