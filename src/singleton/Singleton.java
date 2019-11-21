package singleton;

public class Singleton {

	private static Singleton instance;

	private Singleton() {

	}

	public static Singleton getInstance() {
		if (instance == null) {
			synchronized (Singleton.class) {
				if (instance == null) {
					instance = new Singleton();
				}
			}
		}
		return instance;
	}

	private String decline_message;

	public String getDecline_message() {
		return decline_message;
	}

	public void setDecline_message(String decline_message) {
		this.decline_message = decline_message;
	}

}
