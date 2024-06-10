public class FileEntry {
    private String name;
    private final boolean isDirectory;
    private final int startCluster;

    public FileEntry(String name, boolean isDirectory, int startCluster) {
        this.name = name;
        this.isDirectory = isDirectory;
        this.startCluster = startCluster;
    }

    public String getName() {
        return name;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public int getStartCluster() {
        return startCluster;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return (isDirectory ? "DIR " : "FILE ") + name;
    }
}
