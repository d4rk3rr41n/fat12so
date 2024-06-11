public class FAT16 extends FATFileSystem {
    private int totalClusters;
    private int freeClusters;

    @Override
    public void initialize(int capacity) {
        this.capacity = capacity;
        this.totalClusters = calculateTotalClusters(capacity);
        this.freeClusters = totalClusters;
        System.out.println("FAT16 initialized with capacity: " + capacity + " MB");
    }

    @Override
    public void showFATTable() {
        System.out.println("Showing FAT16 Table");
        // Implementación específica para mostrar la tabla FAT16
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
        // Implementar cálculo específico de FAT16 para clusters totales
        return capacity / 4; // Suponiendo que cada cluster es de 4 KB
    }
}
