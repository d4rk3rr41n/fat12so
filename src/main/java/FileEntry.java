import java.util.ArrayList;
import java.util.List;

public class FileEntry {
    private String name;
    private List<Integer> clusters;

    public FileEntry(String name) {
        this.name = name;
        this.clusters = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getClusters() {
        return clusters;
    }

    public void setClusters(List<Integer> clusters) {
        this.clusters = clusters;
    }

    @Override
    public String toString() {
        return name;
    }
}

