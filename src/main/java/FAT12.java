public class FAT12 extends FATFileSystem {
    private int totalClusters;
    private int freeClusters;

    @Override
    public void initialize(int capacity) {
        this.capacity = capacity;
        this.totalClusters = calculateTotalClusters(capacity);
        this.freeClusters = totalClusters;
        System.out.println("FAT12 initialized with capacity: " + capacity + " MB");
    }

    @Override
    public void showFATTable() {
        System.out.println("Showing FAT12 Table");
        // Implementación específica para mostrar la tabla FAT12
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
        // Implementar cálculo específico de FAT12 para clusters totales
        return capacity / 2; // Suponiendo que cada cluster es de 2 KB
    }
}
