package co.ebti.rc.wordstat;

/**
 * Created by Korniev.Oleksandr on 12.12.2014.
 */
public class Api {

    //Список деревьев кейвордов группы
    public String getKeywordTreePresets(String token, String groupID){
       String result = Hostname.getHostName() + "/api/v2/trees.json?secret_token=" + token + "&group_id=" + groupID;
       return result;
    }

    //Дерево кейвордов группы (уникальное или неуникальное)
    public String getKeywordTree(String token, String groupID, boolean uniqueOrNot, String treeID){
        String result = Hostname.getHostName() + "/api/v2/tree.json?secret_token=" + token + "&group_id=" + groupID + "&unique=" + uniqueOrNot + "&id=" + treeID;
        return result;
    }

    //Список доступных групп
    public String getGroups(String token, String groupID, boolean assembledOrNot){
        String result = Hostname.getHostName() + "api/v2/groups.json?secret_token=" + token + "&assembled=" + assembledOrNot;
        return result;
    }

    //Список доступных групп
    public String getGroups(String token, String groupID){
        String result = Hostname.getHostName() + "api/v2/groups.json?secret_token=" + token;
        return result;
    }

    //Список групп с шаблонами анкоров
    public String getAllGroupsWithAhchorTemplates(String token, String groupID){
        String result = Hostname.getHostName() + "api/v2/groups.json?secret_token=" + token;
        return result;
    }

    //Получить анкор файл (из шаблона)
    public String getAllAhchorFileFromTemplates(String token, String domain, String url, String groupID, String template){
        String result = Hostname.getHostName() + "/api/v2/anchor_file.json?domain=" + domain + "url=" + url + "&group_id="
                                               + groupID + "&template=" + template + "&secret_token=" + token;
        return result;
    }

    //Ключевые слова группы
    public String getGroupKeywords(String token, String groupID){
        String result = Hostname.getHostName() + "/api/v2/group_keywords.json?group_id=" + groupID + "&secret_token=" + token;
        return result;
    }

    //Дерево тематик
    public String getTopics(String token){
        String result = Hostname.getHostName() + "/api/v2/topics.json?secret_token=" + token;
        return result;
    }

    //Список групп в тематике
    public String getTopicGroups(String token, String topicID){
        String result = Hostname.getHostName() + "/api/v2/topic_groups.json?topic_id=" + topicID + "&secret_token=" + token;
        return result;
    }

    //Листинг локалей(языков)
    public String getLanguages(String token, String topicID){
        String result = Hostname.getHostName() + "/api/v2/languages.json?secret_token=" + token;
        return result;
    }

    //Язык (локаль) категории(тематики)
    public String getTopicLanguage(String token, String topicID){
        String result = Hostname.getHostName() + "/api/v2/topic_language.json?topic_id=" + topicID + "&secret_token=" + token;
        return result;
    }

    //Листинг категорий с языками
    public String getTopicsLanguages(String token){
        String result = Hostname.getHostName() + "/api/v2/topics_languages.json?secret_token=" + token;
        return result;
    }

    //Листинг категорий по языку
    public String getLanguageTopics(String token, String langID){
        String result = Hostname.getHostName() + "/api/v2/language_topics.json?lang_id=" + langID + "&secret_token=" + token;
        return result;
    }

    //Получить анкор файл (с обязательным и необязательным набором параметров)
    public String postAndGetAnchorFile(String token, String langID){
        String result = Hostname.getHostName() + "/api/v2/anchor_file.json?secret_token=" + token;
        return result;
    }

    //Получение информации по ключевикам, необходимо уточнить как работает
/*    public String getKeywordsInfo(String token, String keyword){
        //GET http://wordstat.lvlp.co    TOKEN&keywords[]=KEYWORD1&keywords[]=KEYWORD2
        String result = Hostname.getHostName() + "/api/v2/keywords_info.json?secret_token=" + token + langID + "&secret_token=" + token;
        return result;
    }*/




}
