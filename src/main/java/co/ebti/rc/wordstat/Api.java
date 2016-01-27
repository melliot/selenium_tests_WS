package co.ebti.rc.wordstat;

public class Api {

    //Список деревьев кейвордов группы
    public static String getKeywordTreePresets(String token, String groupID){
       String result = Hostname.getHostName() + "/api/v2/trees.json?secret_token=" + token + "&group_id=" + groupID;
       return result;
    }

    //Дерево кейвордов группы (уникальное или неуникальное)
    public static String getKeywordTree(String token, String groupID, boolean uniqueOrNot, String treeID){
        String result = Hostname.getHostName() + "/api/v2/tree.json?secret_token=" + token + "&group_id=" + groupID + "&unique=" + uniqueOrNot + "&id=" + treeID;
        return result;
    }

    //Список доступных групп
    public static String getGroups(String token, String assembledOrNot){
        String result = Hostname.getHostName() + "/api/v2/groups.json?secret_token=" + token + "&assembled=" + assembledOrNot;
        return result;
    }

    //Список доступных групп
    public static String getGroups(String token){
        String result = Hostname.getHostName() + "/api/v2/groups.json?secret_token=" + token;
        return result;
    }

    //Список групп с шаблонами анкоров
    public static String getAllGroupsWithAhchorTemplates(String token){
        String result = Hostname.getHostName() + "/api/v2/groups.json?secret_token=" + token;
        return result;
    }

    //Получить анкор файл (из шаблона)
    public static String getAllAhchorFileFromTemplates(String token, String domain, String url, String groupID, String template){
        String result = Hostname.getHostName() + "/api/v2/anchor_file.json?domain=" + domain + "url=" + url + "&group_id="
                                               + groupID + "&template=" + template + "&secret_token=" + token;
        return result;
    }

    //Ключевые слова группы
    public static String getGroupKeywords(String token, String groupID){
        String result = Hostname.getHostName() + "/api/v2/group_keywords.json?group_id=" + groupID + "&secret_token=" + token;
        return result;
    }

    //Ключевые слова группы постранично
    public static String getGroupKeywordsP(String token, String groupID, int page, int per_page){
        String result = Hostname.getHostName() + "/api/v2/group_keywords_p.json?group_id=" + groupID + "&page=" + page + "&per_page=" + per_page + "&secret_token=" + token;
        return result;
    }

    //Дерево тематик
    public static String getTopics(String token){
        String result = Hostname.getHostName() + "/api/v2/topics.json?secret_token=" + token;
        return result;
    }

    //Список групп в тематике
    public static String getTopicGroups(String token, String topicID){
        String result = Hostname.getHostName() + "/api/v2/topic_groups.json?topic_id=" + topicID + "&secret_token=" + token;
        return result;
    }

    //Листинг локалей(языков)
    public static String getLanguages(String token){
        String result = Hostname.getHostName() + "/api/v2/languages.json?secret_token=" + token;
        return result;
    }

    //Язык (локаль) категории(тематики)
    public static String getTopicLanguage(String token, String topicID){
        String result = Hostname.getHostName() + "/api/v2/topic_language.json?topic_id=" + topicID + "&secret_token=" + token;
        return result;
    }

    //Листинг категорий с языками
    public static String getTopicsLanguages(String token){
        String result = Hostname.getHostName() + "/api/v2/topics_languages.json?secret_token=" + token;
        return result;
    }

    //Листинг категорий по языку
    public static String getLanguageTopics(String token, String langID){
        String result = Hostname.getHostName() + "/api/v2/language_topics.json?lang_id=" + langID + "&secret_token=" + token;
        return result;
    }

    //Получить анкор файл (с обязательным и необязательным набором параметров)
    public static String postAndGetAnchorFile(String token){
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
