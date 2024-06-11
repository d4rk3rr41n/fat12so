public class StorageDevice {
    private FATFileSystem fileSystem;
    private int memoryMB;

    public StorageDevice() {

    }

    public void initialize(String fatType, int memoryMB) {
        this.memoryMB = memoryMB;
        switch (fatType) {
            case "FAT12":
                fileSystem = new FAT12();
                break;
            case "FAT16":
                fileSystem = new FAT16();
                break;
            case "FAT32":
                fileSystem = new FAT32();
                break;
        }
        fileSystem.initialize(memoryMB);
        System.out.println("Dispositivo inicializado con " + fatType + " y " + memoryMB + " MB de memoria");
    }

    public int getTotalClusters() {
        if (fileSystem != null) {
            return fileSystem.getTotalClusters();
        }
        return 0;
    }

    public int getFreeClusters() {
        if (fileSystem != null) {
            return fileSystem.getFreeClusters();
        }
        return 0;
    }

    public FATFileSystem getFileSystem() {
        return fileSystem;
    }
}
