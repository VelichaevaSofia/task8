import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayUtils {

    public static int[][] readArrayFromFile(String filePath) throws IOException {
        List<int[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.trim().split("\s+");
                int[] row = new int[values.length];
                for (int j = 0; j < values.length; j++) {
                    row[j] = Integer.parseInt(values[j]);
                }
                rows.add(row);
            }
        }
        return rows.toArray(new int[0][]);
    }

    public static void writeArrayToFile(int[][] array, String filePath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (int[] row : array) {
                bw.write(String.join(" ", Arrays.stream(row).mapToObj(String::valueOf).toArray(String[]::new)));
                bw.newLine();
            }
        }
    }

    public static void swapColumns(int[][] array) {
        if (array.length == 0) return;

        int minIndex = 0, maxIndex = 0;
        int minSum = Integer.MAX_VALUE, maxSum = Integer.MIN_VALUE;

        for (int j = 0; j < array[0].length; j++) {
            int sum = 0;
            for (int[] row : array) {
                sum += row[j];
            }
            if (sum < minSum) { minSum = sum; minIndex = j; }
            if (sum > maxSum) { maxSum = sum; maxIndex = j; }
        }

        for (int i = 0; i < array.length; i++) {
            int temp = array[i][minIndex];
            array[i][minIndex] = array[i][maxIndex];
            array[i][maxIndex] = temp;
        }
    }

    public static boolean isRectangular(int[][] array) {
        int length = array[0].length;
        for (int[] row : array) {
            if (row.length != length) return false;
        }
        return true;
    }
}
