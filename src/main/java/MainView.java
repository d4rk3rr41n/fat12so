import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.geometry.Insets;

public class MainView extends Application {
    private StorageDevice storageDevice;

    @Override
    public void start(Stage primaryStage) {
        storageDevice = new StorageDevice();

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label capacityLabel = new Label("Capacidad del dispositivo (MB):");
        TextField capacityField = new TextField();

        Label fatTypeLabel = new Label("Seleccione el tipo de sistema de archivos FAT:");
        ComboBox<String> fatTypeComboBox = new ComboBox<>();
        fatTypeComboBox.getItems().addAll("FAT12", "FAT16", "FAT32");

        Button initializeButton = new Button("Inicializar");
        Button showTableButton = new Button("Mostrar Tabla FAT");

        TreeView<Object> fileTreeView = new TreeView<>();
        TreeItem<Object> rootItem = new TreeItem<>("root");
        fileTreeView.setRoot(rootItem);

        Label fileNameLabel = new Label("Nombre del Archivo/Directorio:");
        TextField fileNameField = new TextField();

        Button addFileButton = new Button("Agregar Archivo");
        Button deleteFileButton = new Button("Eliminar Archivo");
        Button addDirectoryButton = new Button("Agregar Directorio");
        Button deleteDirectoryButton = new Button("Eliminar Directorio");

        initializeButton.setOnAction(e -> {
            int capacity = Integer.parseInt(capacityField.getText());
            String selectedFATType = fatTypeComboBox.getValue();

            storageDevice.initialize(selectedFATType, capacity);
            updateFileTreeView(rootItem, storageDevice.getFileSystem().getRootDirectory());
        });

        showTableButton.setOnAction(e -> {
            if (storageDevice.getFileSystem() != null) {
                storageDevice.getFileSystem().showFATTable();
            } else {
                System.out.println("Primero inicialice el sistema de archivos FAT.");
            }
        });

        addFileButton.setOnAction(e -> {
            String fileName = fileNameField.getText();
            if (storageDevice.getFileSystem() != null && !fileName.isEmpty()) {
                storageDevice.getFileSystem().addFile(fileName);
                updateFileTreeView(rootItem, storageDevice.getFileSystem().getRootDirectory());
            }
        });

        deleteFileButton.setOnAction(e -> {
            String fileName = fileNameField.getText();
            if (storageDevice.getFileSystem() != null && !fileName.isEmpty()) {
                storageDevice.getFileSystem().deleteFile(fileName);
                updateFileTreeView(rootItem, storageDevice.getFileSystem().getRootDirectory());
            }
        });

        addDirectoryButton.setOnAction(e -> {
            String dirName = fileNameField.getText();
            if (storageDevice.getFileSystem() != null && !dirName.isEmpty()) {
                storageDevice.getFileSystem().addDirectory(dirName);
                updateFileTreeView(rootItem, storageDevice.getFileSystem().getRootDirectory());
            }
        });

        deleteDirectoryButton.setOnAction(e -> {
            String dirName = fileNameField.getText();
            if (storageDevice.getFileSystem() != null && !dirName.isEmpty()) {
                storageDevice.getFileSystem().deleteDirectory(dirName);
                updateFileTreeView(rootItem, storageDevice.getFileSystem().getRootDirectory());
            }
        });

        root.getChildren().addAll(capacityLabel, capacityField, fatTypeLabel, fatTypeComboBox, initializeButton, showTableButton, fileNameLabel, fileNameField, addFileButton, deleteFileButton, addDirectoryButton, deleteDirectoryButton, fileTreeView);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Simulador FAT");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateFileTreeView(TreeItem<Object> rootItem, DirectoryEntry rootDirectory) {
        rootItem.getChildren().clear();
        for (Object entry : rootDirectory.getEntries()) {
            TreeItem<Object> item = new TreeItem<>(entry);
            rootItem.getChildren().add(item);
            if (entry instanceof DirectoryEntry) {
                updateFileTreeView(item, (DirectoryEntry) entry);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
