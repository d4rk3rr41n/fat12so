import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            StorageDevice storageDevice = new StorageDevice();
            MainView mainView = new MainView(storageDevice);

            Scene scene = new Scene(mainView.getView(), 800, 600);
            primaryStage.setTitle("Simulador de Sistema de Archivos FAT12");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
