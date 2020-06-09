import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NodeRank {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] lines = Helper.prepareLines(br).toString().split("\n");

        Helper.generateRanks(Integer.parseInt(lines[0].split("\\s")[0]),
                Integer.parseInt(lines[Integer.parseInt(lines[0].split("\\s")[0]) + 1]),
                lines, Double.parseDouble(lines[0].split("\\s")[1]),
                Helper.createMatrix(lines, Integer.parseInt(lines[0].split("\\s")[0])));
    }
}