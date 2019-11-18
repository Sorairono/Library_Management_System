package singleton;

public class SingletonBooks {
	private static SingletonBooks instance;
	
	private SingletonBooks() {
		
	}
	
	public static SingletonBooks getInstance() {
		if (instance == null) {
			synchronized (SingletonBooks.class) {
				if (instance == null) {
					instance = new SingletonBooks();
				}
			}
		}
		return instance;
	}
}
