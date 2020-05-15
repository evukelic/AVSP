import java.util.Scanner;

public class CF {

        public static void main(String[] args) {
            int items;
            int users;
            int[][] userItemMatrix;

            UserUser userUser;
            ItemItem itemItem;

            int queryNumber;
            int I;
            int J;
            int T;
            int K;

            try(Scanner sc = new Scanner(System.in)) {
                String line = sc.nextLine();
                String[] splitLine = line.split("\\s");
                items = Integer.parseInt(splitLine[0]);
                users = Integer.parseInt(splitLine[1]);
                userItemMatrix = new int[items][users];

                if (items < 0 || users < 0 || items > 100 || users > 100) {
                    throw new IllegalArgumentException("Invalid argument N or M!");
                }

                for (int i=0; i<items; i++) {
                    line = sc.nextLine();
                    splitLine = line.split("\\s");
                    for (int j=0; j<users; j++) {
                        userItemMatrix[i][j] = splitLine[j].equals("X") ? userItemMatrix[i][j] : Integer.parseInt(splitLine[j]);
                    }
                }

                itemItem = new ItemItem(items, users, userItemMatrix);
                userUser = new UserUser(items, users, userItemMatrix);

                queryNumber = Integer.parseInt(sc.nextLine());
                if (queryNumber < 1 || queryNumber > 100) {
                    throw new IllegalArgumentException("Invalid argument Q!");
                }

                for (int i=0; i<queryNumber; i++) {
                    line = sc.nextLine();
                    splitLine = line.split("\\s");
                    I = Integer.parseInt(splitLine[0]);
                    J = Integer.parseInt(splitLine[1]);
                    T = Integer.parseInt(splitLine[2]);
                    K = Integer.parseInt(splitLine[3]);

                    if (I < 1 || I > items || J < 1 || J > users || K < 1 || K > items || K > users) {
                        throw new IllegalArgumentException("Invalid query arguments!");
                    }

                    if (T==0) {
                        itemItem.predictItemValue(I, K, J);
                    } else {
                        userUser.predictUserValue(I, K, J);
                    }
                }
            }
        }
    }
