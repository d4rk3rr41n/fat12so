public abstract class FATFileSystem {
    protected int capacity;
    protected DirectoryEntry rootDirectory;

    public FATFileSystem() {
        this.rootDirectory = new DirectoryEntry("root");
    }

    public abstract void initialize(int capacity);

    public abstract void showFATTable();

    public DirectoryEntry getRootDirectory() {
        return rootDirectory;
    }

    public void addFile(String fileName) {
        rootDirectory.addFile(new FileEntry(fileName));
    }

    public void deleteFile(String fileName) {
        rootDirectory.removeFile(fileName);
    }

    public void addDirectory(String dirName) {
        rootDirectory.addSubdirectory(new DirectoryEntry(dirName));
    }

    public void deleteDirectory(String dirName) {
        rootDirectory.removeSubdirectory(dirName);
    }

    public int getTotalClusters() {
        // Implementación básica, debe ser sobrescrita por las clases derivadas
        return 0;
    }

    public int getFreeClusters() {
        // Implementación básica, debe ser sobrescrita por las clases derivadas
        return 0;
    }
}
