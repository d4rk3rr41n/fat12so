import java.util.ArrayList;
import java.util.List;

public class FAT32 extends FATFileSystem {

    private static final int END_OF_FILE = 0x0FFFFFFF; // Indicador de fin de archivo en FAT32

    public FAT32(int numClusters) {
        super(numClusters);
    }

    @Override
    public void initialize(int capacity) {
        for (int i = 0; i < fatTable.length; i++) {
            fatTable[i] = 0; // Libre
        }
    }

    @Override
    public void addFile(String name) {
        FileEntry file = new FileEntry(name);
        rootDirectory.addEntry(file);
        allocateClusters(file);
    }

    @Override
    public void deleteFile(String name) {
        FileEntry file = rootDirectory.findFile(name);
        if (file != null) {
            deallocateClusters(file);
            rootDirectory.removeEntry(file);
        }
    }

    @Override
    public void addDirectory(String name) {
        DirectoryEntry directory = new DirectoryEntry(name);
        rootDirectory.addEntry(directory);
        allocateClusters(directory);
    }

    @Override
    public void deleteDirectory(String name) {
        DirectoryEntry directory = rootDirectory.findDirectory(name);
        if (directory != null && directory.isEmpty()) {
            deallocateClusters(directory);
            rootDirectory.removeEntry(directory);
        }
    }

    @Override
    public void renameEntry(String oldName, String newName) {
        FileEntry entry = rootDirectory.findEntry(oldName);
        if (entry != null) {
            entry.setName(newName);
            // Actualizar el clusterMapping con el nuevo nombre
            List<Integer> clusters = entry.getClusters();
            for (int cluster : clusters) {
                clusterMapping.put(cluster, newName);
            }
        }
    }

    private void allocateClusters(FileEntry entry) {
        // Implementar la lógica de asignación de clústeres aquí
        List<Integer> clusters = new ArrayList<>();
        int clustersNeeded = 1; // Esto puede cambiar dependiendo del tamaño del archivo

        int lastCluster = -1;
        for (int i = 0; i < fatTable.length && clustersNeeded > 0; i++) {
            if (fatTable[i] == 0) {
                clusters.add(i);
                if (lastCluster != -1) {
                    fatTable[lastCluster] = i; // Apuntar al siguiente clúster
                }
                lastCluster = i;
                clustersNeeded--;
            }
        }

        if (lastCluster != -1) {
            fatTable[lastCluster] = END_OF_FILE; // Marcar el fin del archivo
        }

        for (int cluster : clusters) {
            clusterMapping.put(cluster, entry.getName());
        }

        entry.setClusters(clusters);
    }

    private void deallocateClusters(FileEntry entry) {
        // Implementar la lógica de liberación de clústeres aquí
        for (int cluster : entry.getClusters()) {
            fatTable[cluster] = 0;
            clusterMapping.remove(cluster);
        }
    }
}


