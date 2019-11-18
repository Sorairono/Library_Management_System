package singleton;

public class SingletonBorrowers {
	private static SingletonBorrowers instance;
	
	private SingletonBorrowers() {
		
	}
	
	public static SingletonBorrowers getInstance() {
		if (instance == null) {
			synchronized (SingletonBorrowers.class) {
				if (instance == null) {
					instance = new SingletonBorrowers();
				}
			}
		}
		return instance;
	}
	
	
}
