import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

class Task3 {

    private int G1[][];

    Task3()
    {
        JFrame jf3 = new JFrame("Graph3");

        jf3.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf3.setVisible(true);
        jf3.setResizable(true);

        int n = (int) (Math.random() * 6 + 5);

        G1 = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                G1[i][j] = (int) (Math.random() * 2);
            }
        }

        boolean symmetry = true;
        for (int i = 0; i<n; i++)
        {
            for (int j=i;j<n;j++)
            {
                if(G1[i][j]!=G1[j][i])
                {
                    symmetry =false;break;
                }
            }
        }

        JTable table = new JTable(new Task3TableModel());

        JScrollPane scrollPane = new JScrollPane(table);

        JLabel label=new JLabel();

        if (symmetry) label.setText("Граф симметричен");
        else label.setText("Граф не симметричен");

        JButton changeButton=new JButton("На задание 4");

        changeButton.addActionListener(e -> {
            jf3.setVisible(false);
            new Task4();
        });

        JPanel panel=new JPanel();

        panel.add(label);
        panel.add(changeButton);

        jf3.add(scrollPane, BorderLayout.NORTH);
        jf3.add(panel,BorderLayout.CENTER);
        jf3.pack();
    }
    private class Task3TableModel implements TableModel {
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
}

