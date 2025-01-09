import java.io.IOException;

public class ConsoleApp {

    public static void main(String[] args) {
        if (args.length != 4 || !args[0].equals("-i") || !args[2].equals("-o")) {
            System.err.println("Usage: java ConsoleApp -i <inputFile> -o <outputFile>");
            return;
        }

        String inputFile = args[1];
        String outputFile = args[3];

        try {
            int[][] array = ArrayUtils.readArrayFromFile(inputFile);
            if (!ArrayUtils.isRectangular(array)) {
                System.err.println("Array is not rectangular.");
                return;
            }
            ArrayUtils.swapColumns(array);
            ArrayUtils.writeArrayToFile(array, outputFile);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}