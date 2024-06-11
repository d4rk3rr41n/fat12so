import java.util.HashMap;
import java.util.Map;

public abstract class FATFileSystem {
    protected int[] fatTable;
    protected DirectoryEntry rootDirectory;
    protected Map<Integer, String> clusterMapping;

    public FATFileSystem(int numClusters) {
        fatTable = new int[numClusters];
        rootDirectory = new DirectoryEntry("root");
        clusterMapping = new HashMap<>();
    }

    public int[] getFATTable() {
        return fatTable;
    }

    public DirectoryEntry getRootDirectory() {
        return rootDirectory;
    }

    public Map<Integer, String> getClusterMapping() {
        return clusterMapping;
    }

    public abstract void initialize(int capacity);
    public abstract void addFile(String fileName);
    public abstract void deleteFile(String fileName);
    public abstract void addDirectory(String dirName);
    public abstract void deleteDirectory(String dirName);
    public abstract void renameEntry(String oldName, String newName);
}
