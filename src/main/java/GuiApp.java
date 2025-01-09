import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GuiApp extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public GuiApp() {
        setTitle("2D Array Manipulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> loadArray());

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveArray());

        JButton swapButton = new JButton("Swap Columns");
        swapButton.addActionListener(e -> swapColumns());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(swapButton);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(600, 400);
        setVisible(true);
    }

    private void loadArray() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                int[][] array = ArrayUtils.readArrayFromFile(fileChooser.getSelectedFile().getAbsolutePath());

                int rows = array.length;
                int columns = array[0].length;

                tableModel.setRowCount(rows);
                tableModel.setColumnCount(columns);

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        tableModel.setValueAt(array[i][j], i, j);
                    }
                }
            } catch (IOException ex) {
                showError("Error loading file: " + ex.getMessage());
            }
        }
    }

    private void saveArray() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                int[][] array = getTableData();
                ArrayUtils.writeArrayToFile(array, fileChooser.getSelectedFile().getAbsolutePath());
            } catch (IOException ex) {
                showError("Error saving file: " + ex.getMessage());
            }
        }
    }

    private void swapColumns() {
        try {
            int[][] array = getTableData();
            if (!ArrayUtils.isRectangular(array)) {
                showError("Array is not rectangular.");
                return;
            }
            ArrayUtils.swapColumns(array);

            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[0].length; j++) {
                    tableModel.setValueAt(array[i][j], i, j);
                }
            }
        } catch (Exception ex) {
            showError("Error swapping columns: " + ex.getMessage());
        }
    }

    private int[][] getTableData() {
        int rows = tableModel.getRowCount();
        int cols = tableModel.getColumnCount();
        int[][] data = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = (int) tableModel.getValueAt(i, j);
            }
        }

        return data;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuiApp::new);
    }
}
