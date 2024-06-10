public class FAT12 {
    private int[] fat;
    private int totalClusters;

    public void initialize() {
        this.fat = new int[4086]; // Máximo número de clusters en FAT12
        for (int i = 0; i < fat.length; i++) {
            fat[i] = 0; // Marcar todos los clusters como libres
        }
    }

    public void initialize(int totalClusters) {
        this.totalClusters = totalClusters;
        this.fat = new int[totalClusters];
        for (int i = 0; i < fat.length; i++) {
            fat[i] = 0; // Marcar todos los clusters como libres
        }
    }

    public int getNextFreeCluster() {
        for (int i = 2; i < fat.length; i++) { // Clusters 0 y 1 están reservados
            if (fat[i] == 0) {
                return i;
            }
        }
        return -1; // No hay clusters libres
    }

    public void setFatEntry(int cluster, int value) {
        fat[cluster] = value;
    }

    public int getFatEntry(int cluster) {
        return fat[cluster];
    }

    public int getFreeClusters() {
        int count = 0;
        for (int entry : fat) {
            if (entry == 0) {
                count++;
            }
        }
        return count;
    }
}
