import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FATEntry {
    private SimpleIntegerProperty cluster;
    private SimpleStringProperty status;

    public FATEntry(int cluster, String status) {
        this.cluster = new SimpleIntegerProperty(cluster);
        this.status = new SimpleStringProperty(status);
    }

    public int getCluster() {
        return cluster.get();
    }

    public SimpleIntegerProperty clusterProperty() {
        return cluster;
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }
}
