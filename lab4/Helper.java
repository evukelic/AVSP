import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Helper {
    public static void printResult(double estimation) {
        DecimalFormat df = new DecimalFormat("0.0000000000");
        BigDecimal bd = new BigDecimal(estimation);
        BigDecimal res = bd.setScale(10, RoundingMode.HALF_UP);
        System.out.println(df.format(res));
    }

    public static List<List<Integer>> createMatrix(String[] lines, int N) {
        List<List<Integer>> matrix = new ArrayList<>();
        int index = 1;

        while (index < N+1) {
            String[] line = lines[index].split("\\s");
            List<Integer> nodes = new ArrayList<>();

            for (int i=0; i<line.length; i++) {
                int node = Integer.parseInt(line[i]);
                nodes.add(node);
            }
            matrix.add(nodes);
            index++;
        }

        return matrix;
    }

    public static void generateRanks(int N, int Q, String[] lines, double b, List<List<Integer>> matrix) {
        List<List<Double>> ranks = generateRanks(N);

        int index = N+2;

        while (index < N+2+Q) {
            String[] line = lines[index].split("\\s");
            int ind = Integer.parseInt(line[0]);
            int iter = Integer.parseInt(line[1]);

            for (; iter >= ranks.size();) {
                List<Double> current= ranks.get(ranks.size() - 1);
                List<Double> next = new ArrayList<>();
                int j = 0;
                while (j < N) {
                    next.add((1 - b) / N);
                    j++;
                }
                int m = 0;
                while (m < N) {
                    List<Integer> sljCv = matrix.get(m);
                    for (int i=0; i<sljCv.size(); i++) {
                        double pom = next.get(sljCv.get(i));
                        pom = pom + b*current.get(m)/sljCv.size();
                        next.set(sljCv.get(i), pom);
                    }
                    m++;
                }
                ranks.add(next);

            }
            printResult(ranks.get(iter).get(ind));
            index++;
        }
    }

    public static List<List<Double>> generateRanks(int N) {
        List<List<Double>> ranks = new ArrayList<>();
        List<Double> zero = new ArrayList<>();
        int index = 0;
        while (index < N) {
            zero.add(1.0/N);
            index++;
        }
        ranks.add(zero);
        return ranks;
    }

    public static StringBuilder prepareLines(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (br.ready()) {
            sb.append(br.readLine());
            sb.append("\n");
        }
        br.close();
        return sb;
    }


}
