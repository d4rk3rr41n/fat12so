import java.util.ArrayList;
import java.util.HashMap;

public class FATFileSystem {
    private FAT12 fat12;
    private HashMap<Integer, ArrayList<FileEntry>> directoryStructure;
    private int totalClusters;

    public FATFileSystem() {
        this.fat12 = new FAT12();
        this.directoryStructure = new HashMap<>();
        this.directoryStructure.put(0, new ArrayList<>()); // Asegurarse de que el directorio raíz esté inicializado
    }

    public void mount() {
        fat12.initialize();
        directoryStructure.put(0, new ArrayList<>()); // Asegurarse de que el directorio raíz esté inicializado
    }

    public void initialize(int totalClusters) {
        this.totalClusters = totalClusters;
        fat12.initialize(totalClusters);
        directoryStructure.put(0, new ArrayList<>()); // Asegurarse de que el directorio raíz esté inicializado
    }

    public ArrayList<FileEntry> listDirectory(int cluster) {
        return directoryStructure.get(cluster);
    }

    public void createFile(String name, int parentCluster) {
        int newCluster = fat12.getNextFreeCluster();
        if (newCluster == -1) {
            System.out.println("No hay espacio disponible");
            return;
        }
        fat12.setFatEntry(newCluster, 0xFFF); // Marcar el cluster como final de archivo
        FileEntry newFile = new FileEntry(name, false, newCluster);
        directoryStructure.get(parentCluster).add(newFile);
        System.out.println("Archivo creado: " + name);
    }

    public void createDirectory(String name, int parentCluster) {
        int newCluster = fat12.getNextFreeCluster();
        if (newCluster == -1) {
            System.out.println("No hay espacio disponible");
            return;
        }
        fat12.setFatEntry(newCluster, 0xFFF); // Marcar el cluster como final de directorio
        FileEntry newDir = new FileEntry(name, true, newCluster);
        directoryStructure.get(parentCluster).add(newDir);
        directoryStructure.put(newCluster, new ArrayList<>());
        System.out.println("Directorio creado: " + name);
    }

    public void deleteEntry(String name, int parentCluster) {
        ArrayList<FileEntry> directory = directoryStructure.get(parentCluster);
        for (FileEntry entry : directory) {
            if (entry.getName().equals(name)) {
                directory.remove(entry);
                fat12.setFatEntry(entry.getStartCluster(), 0); // Marcar el cluster como libre
                if (entry.isDirectory()) {
                    directoryStructure.remove(entry.getStartCluster());
                }
                System.out.println("Entrada eliminada: " + name);
                return;
            }
        }
        System.out.println("Entrada no encontrada: " + name);
    }

    public void modifyEntry(String oldName, String newName, int parentCluster) {
        ArrayList<FileEntry> directory = directoryStructure.get(parentCluster);
        for (FileEntry entry : directory) {
            if (entry.getName().equals(oldName)) {
                entry.setName(newName);
                System.out.println("Entrada modificada: " + oldName + " a " + newName);
                return;
            }
        }
        System.out.println("Entrada no encontrada: " + oldName);
    }

    public int getFatEntry(int cluster) {
        return fat12.getFatEntry(cluster);
    }

    public int getTotalClusters() {
        return totalClusters;
    }

    public int getFreeClusters() {
        return fat12.getFreeClusters();
    }
}
