import java.util.ArrayList;
import java.util.List;

public class DirectoryEntry extends FileEntry {
    private List<FileEntry> entries;

    public DirectoryEntry(String name) {
        super(name);
        this.entries = new ArrayList<>();
    }

    public void addEntry(FileEntry entry) {
        entries.add(entry);
    }

    public void removeEntry(FileEntry entry) {
        entries.remove(entry);
    }

    public List<FileEntry> getEntries() {
        return entries;
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public FileEntry findEntry(String name) {
        for (FileEntry entry : entries) {
            if (entry.getName().equals(name)) {
                return entry;
            }
        }
        return null;
    }

    public FileEntry findFile(String name) {
        for (FileEntry entry : entries) {
            if (entry instanceof FileEntry && entry.getName().equals(name)) {
                return entry;
            }
        }
        return null;
    }

    public DirectoryEntry findDirectory(String name) {
        for (FileEntry entry : entries) {
            if (entry instanceof DirectoryEntry && entry.getName().equals(name)) {
                return (DirectoryEntry) entry;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getName();
    }
}

