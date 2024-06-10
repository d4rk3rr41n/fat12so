public class StorageDevice {
    private FATFileSystem fileSystem;
    private int memoryMB;

    public StorageDevice() {
        fileSystem = new FATFileSystem();
        formatFAT12();
    }

    public void formatFAT12() {
        fileSystem.mount();
        System.out.println("Dispositivo formateado a FAT12");
    }

    public void setMemory(int memoryMB) {
        this.memoryMB = memoryMB;
        fileSystem.initialize(memoryMB * 1024 * 1024 / 512);
        System.out.println("Memoria del dispositivo establecida en " + memoryMB + " MB");
    }

    public int getTotalClusters() {
        return fileSystem.getTotalClusters();
    }

    public int getFreeClusters() {
        return fileSystem.getFreeClusters();
    }

    public FATFileSystem getFileSystem() {
        return fileSystem;
    }
}
