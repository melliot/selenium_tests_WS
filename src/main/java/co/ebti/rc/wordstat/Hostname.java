package co.ebti.rc.wordstat;

public class Hostname {
    //RC
    public static final String HOSTNAME = "wordstat.rc.ebti.co";
    //Production
    //public static final String HOSTNAME = "wordstat.lvlp.co";

    //private static final int DBPORT = 27017;
    private static final String DBNAME = "elen";

    public static String getHostName() {
        return "http://" + HOSTNAME;
    }

    public static String getBasicHostName() {
        return HostHolder.host;
    }

    public static String getUrlHostName() {

        return "https://" + getBasicHostName();
    }


    public static String getDBName() {
        return DBNAME;
    }

    private static class HostHolder {
        private static final String host = getHost();

        private static String getHost() {
            String qaHost = System.getenv("QA_HOST");

            if ((qaHost != null) && (!qaHost.isEmpty())) {

                return qaHost;
            }

            return HOSTNAME;
        }
    }
}
