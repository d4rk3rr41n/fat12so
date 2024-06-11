public class FileEntry {
    private String name;

    public FileEntry(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "File: " + name;
    }
}
