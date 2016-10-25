import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class Main implements Runnable {

    private int del;
    int G1[][];

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Main());
    }

    @Override
    public void run() {

        JFrame jf = new JFrame("Graph1");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jf.setVisible(true);
        jf.setResizable(true);

        int n = (int) (Math.random() * 5 + 6);

        G1 = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                G1[i][j] = (int) (Math.random() * 2);
            }
        }

        JTable table = new JTable(new TableModel() {
            private Set<TableModelListener> listeners = new HashSet<>();

            @Override
            public int getRowCount() {
                return n - 1;
            }

            @Override
            public int getColumnCount() {
                return n - 1;
            }

            @Override
            public String getColumnName(int columnIndex) {
                return "x" + columnIndex;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return int.class;
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {

                return G1[rowIndex][columnIndex];
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

            }

            @Override
            public void addTableModelListener(TableModelListener l) {
                listeners.add(l);
            }

            @Override
            public void removeTableModelListener(TableModelListener l) {
                listeners.remove(l);
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        JButton button = new JButton("Accept deletion");
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, n - 1, 1));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.setVisible(false);
                int G2[][] = new int[n - 1][n - 1];

                for (int i = 0; i < n - 1; i++) {
                    if (i == del) continue;
                    for (int j = 0; j < n - 1; j++) {
                        if (j == del) continue;
                        if (i < del && j < del) {
                            G2[i][j] = G1[i][j];
                        } else if (i < del && j > del) {
                            G2[i][j] = G1[i][j + 1];
                        } else if (i > del && j < del) {
                            G2[i][j] = G1[i + 1][j];
                        } else {
                            G2[i][j] = G1[i + 1][j + 1];
                        }
                    }
                }
                G1 = G2;
            }
        });

        spinner.addChangeListener(e -> del = (int) spinner.getValue());

        JPanel panel = new JPanel();
        panel.add(button);
        panel.add(spinner);

        jf.setLayout(new BorderLayout());
        jf.add(panel, BorderLayout.SOUTH);
        jf.add(scrollPane, BorderLayout.NORTH);
        jf.pack();

    }
}
