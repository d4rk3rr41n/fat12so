import java.util.Arrays;

public class FATTable {
    private int[] table;
    private int clusterSize;
    private int totalClusters;

    public FATTable(int totalClusters, int clusterSize) {
        this.totalClusters = totalClusters;
        this.clusterSize = clusterSize;
        this.table = new int[totalClusters];
        Arrays.fill(this.table, -1); // -1 indicates a free cluster
    }

    public int allocateCluster() {
        for (int i = 0; i < totalClusters; i++) {
            if (table[i] == -1) {
                table[i] = 0; // Mark cluster as used, with no continuation
                return i;
            }
        }
        return -1; // No free clusters available
    }

    public void freeCluster(int clusterIndex) {
        if (clusterIndex >= 0 && clusterIndex < totalClusters) {
            table[clusterIndex] = -1; // Mark cluster as free
        }
    }

    public int getClusterSize() {
        return clusterSize;
    }

    public int[] getTable() {
        return table;
    }

    public int getTotalClusters() {
        return totalClusters;
    }
}
