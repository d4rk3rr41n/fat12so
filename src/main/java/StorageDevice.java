public class StorageDevice {
    private FATFileSystem fileSystem;

    public void initialize(String fatType, int capacity) {
        switch (fatType) {
            case "FAT12":
                fileSystem = new FAT12(capacity);
                break;
            case "FAT16":
                fileSystem = new FAT16(capacity);
                break;
            case "FAT32":
                fileSystem = new FAT32(capacity);
                break;
        }
    }

    public FATFileSystem getFileSystem() {
        return fileSystem;
    }
}
