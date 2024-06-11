public class FAT32 extends FATFileSystem {
    private int totalClusters;
    private int freeClusters;

    @Override
    public void initialize(int capacity) {
        this.capacity = capacity;
        this.totalClusters = calculateTotalClusters(capacity);
        this.freeClusters = totalClusters;
        System.out.println("FAT32 initialized with capacity: " + capacity + " MB");
    }

    @Override
    public void showFATTable() {
        System.out.println("Showing FAT32 Table");
        // Implementación específica para mostrar la tabla FAT32
    }

    @Override
    public int getTotalClusters() {
        return totalClusters;
    }

    @Override
    public int getFreeClusters() {
        return freeClusters;
    }

    private int calculateTotalClusters(int capacity) {
        // Implementar cálculo específico de FAT32 para clusters totales
        return capacity / 8; // Suponiendo que cada cluster es de 8 KB
    }
}
