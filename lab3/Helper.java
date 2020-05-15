import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

public class Helper {

    public static float[][] transformMatrix(int[][] userItemMatrix, int rows, int columns, boolean itemItem) {
        float[][] transformedMatrix = new float[rows][columns];

        int rowCounter = 0;
        while (rowCounter < rows) {
            float mean = 0;
            int counter = 0;

            int columnCounter = 0;
            while(columnCounter < columns) {
                if (itemItem) {
                    if (userItemMatrix[rowCounter][columnCounter] != 0) {
                        mean += userItemMatrix[rowCounter][columnCounter];
                        counter++;
                    }
                } else {
                    if (userItemMatrix[columnCounter][rowCounter] != 0) {
                        mean += userItemMatrix[columnCounter][rowCounter];
                        counter++;
                    }
                }
                columnCounter++;
            }

            mean = mean / (float) counter;

            columnCounter = 0;
            while (columnCounter < columns) {
                if (itemItem) {
                    if (userItemMatrix[rowCounter][columnCounter] != 0) {
                        transformedMatrix[rowCounter][columnCounter] = userItemMatrix[rowCounter][columnCounter] - mean;
                    } else {
                        transformedMatrix[rowCounter][columnCounter] = userItemMatrix[rowCounter][columnCounter];
                    }
                } else {
                    if (userItemMatrix[columnCounter][rowCounter] != 0) {
                        transformedMatrix[columnCounter][rowCounter] = userItemMatrix[columnCounter][rowCounter] - mean;
                    } else {
                        transformedMatrix[columnCounter][rowCounter] = userItemMatrix[columnCounter][rowCounter];
                    }
                }
                columnCounter++;
            }
            rowCounter++;
        }

        return transformedMatrix;
    }

    private static Map<Point, Float> calculateSortedSimilarities(float[][] transformedMatrix, int rows, int columns, int I, int J, boolean itemItem) {
        Map<Point, Float> similarities = new LinkedHashMap<>();
        int rowCounter = 0;
        while (rowCounter < rows) {
            List<Float> firstRow = new ArrayList<>();
            List<Float> secondRow = new ArrayList<>();
            if (itemItem) {
                if ((I - 1) != rowCounter) {
                    int columnCounter = 0;
                    while (columnCounter < columns) {
                        float firstRowEl = transformedMatrix[I - 1][columnCounter];
                        float secondRowEl = transformedMatrix[rowCounter][columnCounter];
                        firstRow.add(firstRowEl);
                        secondRow.add(secondRowEl);
                        columnCounter++;
                    }
                    similarities.put(new Point(I - 1, rowCounter), compute(rows, firstRow, secondRow));
                }
            } else {
                if ((J - 1) != rowCounter) {
                    int columnCounter = 0;
                    while (columnCounter < columns) {
                        float firstRowEl = transformedMatrix[columnCounter][J - 1];
                        firstRow.add(firstRowEl);
                        float secondRowEl = transformedMatrix[columnCounter][rowCounter];
                        secondRow.add(secondRowEl);
                        columnCounter++;
                    }
                    similarities.put(new Point(J - 1, rowCounter), compute(columns, firstRow, secondRow));
                }
            }
            rowCounter++;
        }

        List<Map.Entry<Point, Float>> list = new LinkedList<>(similarities.entrySet());
        list.sort(Map.Entry.comparingByValue());

        HashMap<Point, Float> sorted = new LinkedHashMap<>();
        for (Map.Entry<Point, Float> entry : list) {
            sorted.put(entry.getKey(), entry.getValue());
        }
        return sorted;
    }

    public static void predictValue(int[][] userItemMatrix, float[][] transformedMatrixItem, int rows, int columns, int I, int K, int J, boolean itemItem) {
        Map<Point, Float> sorted = calculateSortedSimilarities(transformedMatrixItem, rows, columns, I, J, itemItem);

        float numerator = 0;
        float denominator = 0;
        int counter = 1;
        int K_copy = K;

        List<Map.Entry<Point, Float>> entryList = new ArrayList<>(sorted.entrySet());
        for (int i=K; i>0; i=K_copy) {
            Map.Entry<Point, Float> lastEntry = entryList.get(entryList.size() - counter);

            if (itemItem) {
                if (userItemMatrix[(int) lastEntry.getKey().getY()][J - 1] != 0) {
                    numerator = numerator + lastEntry.getValue() * userItemMatrix[(int) lastEntry.getKey().getY()][J - 1];
                    denominator = denominator + lastEntry.getValue();
                    K_copy--;
                }
                counter++;
            } else {
                if (lastEntry.getValue() > 0) {
                    if (userItemMatrix[I - 1][(int) lastEntry.getKey().getY()] != 0) {
                        numerator = numerator + lastEntry.getValue() * userItemMatrix[I - 1][(int) lastEntry.getKey().getY()];
                        denominator = denominator + lastEntry.getValue();
                        K_copy--;
                    }
                    counter++;
                } else {
                    break;
                }
            }
        }
        getResult(numerator / denominator);
    }

    private static void getResult(float result) {
        DecimalFormat df = new DecimalFormat("#.000");
        BigDecimal bd = new BigDecimal(result);
        BigDecimal res = bd.setScale(3, RoundingMode.HALF_UP);
        System.out.println(df.format(res));
    }

    private static float compute(int rows, List<Float> firstRow, List<Float> secondRow) {
        float numerator = 0;
        float firstSquareRoot = 0;
        float secondSquareRoot = 0;
        int count = 0;

        while (count < rows) {
            float firstElement = firstRow.get(count);
            float secondElement = secondRow.get(count);

            numerator = numerator + firstElement * secondElement;
            firstSquareRoot = firstSquareRoot + firstElement * firstElement;
            secondSquareRoot = secondSquareRoot + secondElement * secondElement;

            count++;
        }

        return (float) (numerator / Math.sqrt(firstSquareRoot * secondSquareRoot));
    }
}
