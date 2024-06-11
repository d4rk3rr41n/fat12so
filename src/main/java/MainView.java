import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

public class MainView extends Application {
    private StorageDevice storageDevice;
    private TableView<ClusterEntry> fatTableView;
    private ObservableList<ClusterEntry> fatTableData;
    private Label capacityLabel;
    private Label clustersLabel;

    @Override
    public void start(Stage primaryStage) {
        storageDevice = new StorageDevice();

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label capacityInputLabel = new Label("Capacidad del dispositivo (MB):");
        TextField capacityField = new TextField();

        Label fatTypeLabel = new Label("Seleccione el tipo de sistema de archivos FAT:");
        ComboBox<String> fatTypeComboBox = new ComboBox<>();
        fatTypeComboBox.getItems().addAll("FAT12", "FAT16", "FAT32");

        Button initializeButton = new Button("Inicializar");

        TreeView<Object> fileTreeView = new TreeView<>();
        TreeItem<Object> rootItem = new TreeItem<>("root");
        fileTreeView.setRoot(rootItem);

        Label fileNameLabel = new Label("Nombre del Archivo/Directorio:");
        TextField fileNameField = new TextField();

        Label newNameLabel = new Label("Nuevo Nombre:");
        TextField newNameField = new TextField();
        Button renameButton = new Button("Renombrar");

        Button addFileButton = new Button("Agregar Archivo");
        Button deleteFileButton = new Button("Eliminar Archivo");
        Button addDirectoryButton = new Button("Agregar Directorio");
        Button deleteDirectoryButton = new Button("Eliminar Directorio");

        // Displaying capacity and clusters
        capacityLabel = new Label("Capacidad:");
        clustersLabel = new Label("Clusters:");

        // Initialize FAT Table View
        fatTableView = new TableView<>();
        TableColumn<ClusterEntry, Integer> indexColumn = new TableColumn<>("Índice");
        indexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));

        TableColumn<ClusterEntry, String> valueColumn = new TableColumn<>("Valor");
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        fatTableView.getColumns().add(indexColumn);
        fatTableView.getColumns().add(valueColumn);

        fatTableData = FXCollections.observableArrayList();
        fatTableView.setItems(fatTableData);

        initializeButton.setOnAction(event -> {
            int capacity = Integer.parseInt(capacityField.getText()) * 1024 * 1024; // Convert MB to bytes
            String selectedFATType = fatTypeComboBox.getValue();

            storageDevice.initialize(selectedFATType, capacity);
            updateFileTreeView(rootItem, storageDevice.getFileSystem().getRootDirectory());
            updateFATTable();

            // Update capacity and clusters labels
            capacityLabel.setText("Capacidad: " + capacityField.getText() + " MB");
            clustersLabel.setText("Clusters: " + storageDevice.getFileSystem().getFATTable().length);
        });

        addFileButton.setOnAction(event -> {
            String fileName = fileNameField.getText();
            if (storageDevice.getFileSystem() != null && !fileName.isEmpty()) {
                storageDevice.getFileSystem().addFile(fileName);
                updateFileTreeView(rootItem, storageDevice.getFileSystem().getRootDirectory());
                updateFATTable();
            }
        });

        deleteFileButton.setOnAction(event -> {
            String fileName = fileNameField.getText();
            if (storageDevice.getFileSystem() != null && !fileName.isEmpty()) {
                storageDevice.getFileSystem().deleteFile(fileName);
                updateFileTreeView(rootItem, storageDevice.getFileSystem().getRootDirectory());
                updateFATTable();
            }
        });

        addDirectoryButton.setOnAction(event -> {
            String dirName = fileNameField.getText();
            if (storageDevice.getFileSystem() != null && !dirName.isEmpty()) {
                storageDevice.getFileSystem().addDirectory(dirName);
                updateFileTreeView(rootItem, storageDevice.getFileSystem().getRootDirectory());
                updateFATTable();
            }
        });

        deleteDirectoryButton.setOnAction(event -> {
            String dirName = fileNameField.getText();
            if (storageDevice.getFileSystem() != null && !dirName.isEmpty()) {
                storageDevice.getFileSystem().deleteDirectory(dirName);
                updateFileTreeView(rootItem, storageDevice.getFileSystem().getRootDirectory());
                updateFATTable();
            }
        });

        renameButton.setOnAction(event -> {
            String oldName = fileNameField.getText();
            String newName = newNameField.getText();
            if (storageDevice.getFileSystem() != null && !oldName.isEmpty() && !newName.isEmpty()) {
                storageDevice.getFileSystem().renameEntry(oldName, newName);
                updateFileTreeView(rootItem, storageDevice.getFileSystem().getRootDirectory());
                updateFATTable();
            }
        });

        // Add event listener for selecting and deleting entries directly
        fileTreeView.setOnMouseClicked(event -> {
            TreeItem<Object> selectedItem = fileTreeView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                String selectedName = selectedItem.getValue().toString();
                fileNameField.setText(selectedName.replace("DIR: ", "").replace("FILE: ", ""));
            }
        });

        deleteFileButton.setOnAction(event -> {
            TreeItem<Object> selectedItem = fileTreeView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                String selectedName = selectedItem.getValue().toString().replace("DIR: ", "").replace("FILE: ", "");
                storageDevice.getFileSystem().deleteFile(selectedName);
                updateFileTreeView(rootItem, storageDevice.getFileSystem().getRootDirectory());
                updateFATTable();
            }
        });

        deleteDirectoryButton.setOnAction(event -> {
            TreeItem<Object> selectedItem = fileTreeView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                String selectedName = selectedItem.getValue().toString().replace("DIR: ", "").replace("FILE: ", "");
                storageDevice.getFileSystem().deleteDirectory(selectedName);
                updateFileTreeView(rootItem, storageDevice.getFileSystem().getRootDirectory());
                updateFATTable();
            }
        });

        root.getChildren().addAll(capacityInputLabel, capacityField, fatTypeLabel, fatTypeComboBox, initializeButton, capacityLabel, clustersLabel, fileNameLabel, fileNameField, addFileButton, deleteFileButton, addDirectoryButton, deleteDirectoryButton, newNameLabel, newNameField, renameButton, fileTreeView, fatTableView);

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("Simulador FAT");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateFileTreeView(TreeItem<Object> rootItem, DirectoryEntry rootDirectory) {
        rootItem.getChildren().clear();
        for (FileEntry entry : rootDirectory.getEntries()) {
            String entryName = entry instanceof DirectoryEntry ? "DIR: " + entry.toString() : "FILE: " + entry.toString();
            TreeItem<Object> item = new TreeItem<>(entryName);
            rootItem.getChildren().add(item);
            if (entry instanceof DirectoryEntry) {
                updateFileTreeView(item, (DirectoryEntry) entry);
            }
        }
    }

    private void updateFATTable() {
        fatTableData.clear();
        int[] fatTable = storageDevice.getFileSystem().getFATTable();
        Map<Integer, String> clusterMapping = storageDevice.getFileSystem().getClusterMapping();

        for (int i = 0; i < fatTable.length; i++) {
            String value = clusterMapping.containsKey(i) ? clusterMapping.get(i) : Integer.toString(fatTable[i]);
            fatTableData.add(new ClusterEntry(i, value));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class ClusterEntry {
    private final int index;
    private final String value;

    public ClusterEntry(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }
}
