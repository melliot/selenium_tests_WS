package co.ebti.rc.wordstat;

/**
 * Config for run tests.
 */
public class Hostname {
	public static final String HOSTNAME = "wordstat.rc.ebti.co";

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
