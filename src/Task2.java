import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

class Task2 {

    private int G1[][];

    private int G2[][];

    private int curr;

    private int count;

    private int calc;

    Task2() {
        JFrame jf2 = new JFrame("Graph2");

        jf2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf2.setVisible(true);
        jf2.setResizable(true);

        int n = (int) (Math.random() * 4 + 4);

        G1 = new int[n][n];

        count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                G1[i][j] = (int) (Math.random() * 2);
                if (G1[i][j] == 1) count++;
            }
        }

        JTable tableG1 = new JTable(new Task2_1TableModel());

        G2 = new int[n][count];

        curr = 0;

        for (int i = 0; i < n; i++) {
            G2[i][curr] = 0;
            for (int j = 0; j < n; j++) {
                if (G1[i][j] == 1) {
                    if (i == j) G2[i][curr] = 2;
                    else {
                        G2[i][curr] = 1;
                        G2[j][curr] = -1;
                    }
                    curr++;
                    if (curr < count) G2[i][curr] = 0;
                    else break;
                } else {
                    G2[j][curr] = 0;
                }

            }
        }

        JTable tableG2 = new JTable(new Task2_2TableModel());

        JScrollPane scrollPaneG1 = new JScrollPane(tableG1);

        JScrollPane scrollPaneG2 = new JScrollPane(tableG2);


        JButton button = new JButton("Accept calculation");

        JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, n - 1, 1));
        spinner.addChangeListener(e -> calc = (int) spinner.getValue());

        JLabel label=new JLabel();

        button.addActionListener(e -> {
            button.setVisible(false);
            curr=0;
            for(int i=0;i<count;i++)
            {
                if (G2[calc][i]==-1) curr++;
            }
            label.setText("Степень полуисхода для x"+calc+"="+curr);
        });


        JPanel panel=new JPanel();
        panel.add(button);
        panel.add(spinner);
        panel.add(label);

        jf2.setLayout(new BorderLayout());
        jf2.add(panel,BorderLayout.SOUTH);
        jf2.add(scrollPaneG2, BorderLayout.CENTER);
        jf2.add(scrollPaneG1, BorderLayout.NORTH);
        jf2.pack();

    }

    private class Task2_1TableModel implements TableModel {
        private Set<TableModelListener> listeners = new HashSet<>();

        @Override
        public int getRowCount() {
            return G1.length;
        }

        @Override
        public int getColumnCount() {
            return G1.length;
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
    }

    private class Task2_2TableModel implements TableModel {
        private Set<TableModelListener> listeners = new HashSet<>();

        @Override
        public int getRowCount() {
            return G2.length;
        }

        @Override
        public int getColumnCount() {
            return G2[0].length;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return "a" + columnIndex;
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
            return G2[rowIndex][columnIndex];
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
    }
}
