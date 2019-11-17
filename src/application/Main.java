package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
// import singleton.Singleton_Controller;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	private static Main instance;
	private static Stage primaryStage = null;
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage stage) {
		primaryStage = stage;
	}
	
	public static Main getInstance() {
		if (instance == null) {
			synchronized (Main.class) {
				if (instance == null) {
					instance = new Main();
				}
			}
		}
		return instance;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setMaximized(true);
			primaryStage.setResizable(false);
			setPrimaryStage(primaryStage);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_document/MainView.fxml"));
			// Singleton_Controller.getInstance().setMain_Controller(loader.getController());
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setTitle("Library Management System");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
