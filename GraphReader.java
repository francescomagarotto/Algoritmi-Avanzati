import java.io.IOException;
import java.nio.file.*;
public class GraphReader {
    private Path pathFile;
    public GraphReader(String fileName) {
        this.pathFile = Paths.get(fileName);
    }

    private String explodeFirstLine(int index) { 
        //Non uso stringhe statiche intenzionalmente
        String info = new String();
        try {
        info = Files.lines(pathFile).findFirst().get();
        } 
        catch(IOException e) {}
        String result = info.split(" ")[index].trim();
        return result;
    }

    public int getNumberOfVertex() {
        return Integer.parseInt(explodeFirstLine(0));
    }
    public int getNumberOfEdges() {
        return Integer.parseInt(explodeFirstLine(1));
    }

    //TODO leggere le altre linee ed creare un grafo
}