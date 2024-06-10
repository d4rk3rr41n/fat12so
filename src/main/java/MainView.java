import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MainView {
    private StorageDevice storageDevice;
    private FATFileSystem fileSystem;
    private BorderPane mainLayout;
    private ListView<FileEntry> listView;
    private TableView<FATEntry> fatTable;
    private Label memoryLabel;

    public MainView(StorageDevice storageDevice) {
        this.storageDevice = storageDevice;
        this.fileSystem = storageDevice.getFileSystem();
        mainLayout = new BorderPane();

        listView = new ListView<>();
        updateListView();

        VBox leftPanel = new VBox(10);
        leftPanel.setPadding(new Insets(10));
        leftPanel.getChildren().addAll(new Label("Archivos y Directorios"), listView, createControlPanel());

        fatTable = new TableView<>();
        initializeFATTable();

        memoryLabel = new Label();
        updateMemoryLabel();

        mainLayout.setLeft(leftPanel);
        mainLayout.setCenter(fatTable);
        mainLayout.setBottom(memoryLabel);
    }

    public BorderPane getView() {
        return mainLayout;
    }

    private void updateListView() {
        ObservableList<FileEntry> items = FXCollections.observableArrayList(fileSystem.listDirectory(0));
        listView.setItems(items);
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(FileEntry item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText((item.isDirectory() ? "DIR " : "FILE ") + item.getName());
                }
            }
        });
    }

    private GridPane createControlPanel() {
        GridPane controlPanel = new GridPane();
        controlPanel.setHgap(10);
        controlPanel.setVgap(10);
        controlPanel.setPadding(new Insets(10));

        TextField nameField = new TextField();
        Button createFileButton = new Button("Crear Archivo");
        Button createDirButton = new Button("Crear Directorio");
        Button deleteButton = new Button("Eliminar");
        Button modifyButton = new Button("Modificar");
        TextField memoryField = new TextField();
        Button setMemoryButton = new Button("Establecer Memoria");

        createFileButton.setOnAction(e -> {
            fileSystem.createFile(nameField.getText(), 0);
            updateListView();
            updateFATTable();
            updateMemoryLabel();
        });

        createDirButton.setOnAction(e -> {
            fileSystem.createDirectory(nameField.getText(), 0);
            updateListView();
            updateFATTable();
            updateMemoryLabel();
        });

        deleteButton.setOnAction(e -> {
            FileEntry selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                fileSystem.deleteEntry(selected.getName(), 0);
                updateListView();
                updateFATTable();
                updateMemoryLabel();
            }
        });

        modifyButton.setOnAction(e -> {
            FileEntry selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                fileSystem.modifyEntry(selected.getName(), nameField.getText(), 0);
                updateListView();
                updateFATTable();
                updateMemoryLabel();
            }
        });

        setMemoryButton.setOnAction(e -> {
            int memoryMB = Integer.parseInt(memoryField.getText());
            storageDevice.setMemory(memoryMB);
            updateMemoryLabel();
        });

        controlPanel.add(new Label("Nombre:"), 0, 0);
        controlPanel.add(nameField, 1, 0);
        controlPanel.add(createFileButton, 0, 1);
        controlPanel.add(createDirButton, 1, 1);
        controlPanel.add(deleteButton, 2, 1);
        controlPanel.add(modifyButton, 3, 1);
        controlPanel.add(new Label("Memoria (MB):"), 0, 2);
        controlPanel.add(memoryField, 1, 2);
        controlPanel.add(setMemoryButton, 2, 2);

        return controlPanel;
    }

    private void initializeFATTable() {
        TableColumn<FATEntry, Integer> clusterColumn = new TableColumn<>("Cluster");
        clusterColumn.setCellValueFactory(cellData -> cellData.getValue().clusterProperty().asObject());

        TableColumn<FATEntry, String> statusColumn = new TableColumn<>("Estado");
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        fatTable.getColumns().addAll(clusterColumn, statusColumn);
        updateFATTable();
    }

    private void updateFATTable() {
        ObservableList<FATEntry> fatEntries = FXCollections.observableArrayList();
        for (int i = 0; i < 4086; i++) {
            int value = fileSystem.getFatEntry(i);
            String status = (value == 0) ? "Libre" : (value == 0xFFF) ? "Fin de Archivo" : "Usado";
            fatEntries.add(new FATEntry(i, status));
        }
        fatTable.setItems(fatEntries);
    }

    private void updateMemoryLabel() {
        int totalClusters = storageDevice.getTotalClusters();
        int freeClusters = storageDevice.getFreeClusters();
        memoryLabel.setText("Memoria Total: " + totalClusters + " clusters, Memoria Libre: " + freeClusters + " clusters");
    }
}
