import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class PCY {
    public static void main(String[] args) throws FileNotFoundException {
        int N;
        double s;
        int b;

        List<Integer[]> baskets = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {
            N = Integer.parseInt(scanner.nextLine());
            s = Double.parseDouble(scanner.nextLine());
            b = Integer.parseInt(scanner.nextLine());

            while (scanner.hasNext()) {
                String[] basketStr = scanner.nextLine().split("\\s");
                Integer[] basketInt = new Integer[basketStr.length];
                for(int i=0; i<basketStr.length; i++) {
                    basketInt[i] = Integer.parseInt(basketStr[i]);
                }
                baskets.add(basketInt);
            }

        }
        int supportThreshold = (int) (s*N);

        calcFrequentItems(supportThreshold, b, baskets);
    }

    private static void calcFrequentItems(int supportThreshold, int b, List<Integer[]> baskets) {
        HashMap<Integer, Integer> itemsCounter = new HashMap<>();
        HashMap<Integer, Integer> slots = new HashMap<>();
        HashMap<Point, Integer> itemPairs = new HashMap<>();

        for (Integer[] basket : baskets) {
            for (Integer item : basket) {
                if (itemsCounter.containsKey(item)) {
                    itemsCounter.put(item, itemsCounter.get(item)+1);
                } else {
                    itemsCounter.put(item, 1);
                }
            }
        }

        for (Integer[] basket: baskets) {
            for (int i=0; i<basket.length; i++){
                for (int j=i+1; j<basket.length; j++) {
                    if ((itemsCounter.get(basket[i]) >= supportThreshold) && (itemsCounter.get(basket[j]) >= supportThreshold)) {
                        int k = (basket[i] * itemsCounter.size() + basket[j]) % b;
                        if (slots.containsKey(k)) {
                            slots.put(k, slots.get(k)+1);
                        } else {
                            slots.put(k, 1);
                        }
                    }
                }
            }
        }


        for (Integer[] basket: baskets) {
            for (int i=0; i<basket.length; i++){
                for (int j=i+1; j<basket.length; j++) {
                    Point point = new Point(basket[i], basket[j]);
                    if ((itemsCounter.get(basket[i]) >= supportThreshold) && (itemsCounter.get(basket[j]) >= supportThreshold)) {
                        int k = (basket[i] * itemsCounter.size() + basket[j]) % b;
                        if (slots.get(k) >= supportThreshold) {
                            if (itemPairs.containsKey(point)) {
                                itemPairs.put(point, itemPairs.get(point)+1);
                            } else {
                                itemPairs.put(point, 1);
                            }
                        }
                    }
                }
            }
        }

        System.out.println(slots.size());
        System.out.println(itemPairs.size());

        ArrayList<Integer> sorted = new ArrayList<>();
        for (Map.Entry<Point, Integer> entry : itemPairs.entrySet()) {
            sorted.add(entry.getValue());
        }
        Collections.sort(sorted, Collections.reverseOrder());

        for(Integer cnt : sorted) {
            System.out.println(cnt);
        }
    }

}
