import java.util.ArrayList;
import java.util.List;

public class DirectoryEntry {
    private String name;
    private List<FileEntry> files;
    private List<DirectoryEntry> subdirectories;

    public DirectoryEntry(String name) {
        this.name = name;
        this.files = new ArrayList<>();
        this.subdirectories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<FileEntry> getFiles() {
        return files;
    }

    public List<DirectoryEntry> getSubdirectories() {
        return subdirectories;
    }

    public void addFile(FileEntry file) {
        files.add(file);
    }

    public void addSubdirectory(DirectoryEntry directory) {
        subdirectories.add(directory);
    }

    public boolean isEmpty() {
        return files.isEmpty() && subdirectories.isEmpty();
    }

    @Override
    public String toString() {
        return "Directory: " + name;
    }

    public List<Object> getEntries() {
        List<Object> entries = new ArrayList<>();
        entries.addAll(subdirectories);
        entries.addAll(files);
        return entries;
    }

    public boolean isDirectory() {
        return true;
    }

    public void removeFile(String name) {
        files.removeIf(file -> file.getName().equals(name));
    }

    public void removeSubdirectory(String name) {
        subdirectories.removeIf(directory -> directory.getName().equals(name));
    }
}
