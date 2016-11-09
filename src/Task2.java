import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

class Task2 {

    private int G2[][];

    private int curr;

    private int count=0;

    private int calc=0;

    Task2() {
        JFrame jf2 = new JFrame("Степень полуисхода указанной вершины.Чудинов Александр Чудинов Александр");

        jf2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf2.setVisible(true);
        jf2.setResizable(true);

        int n = (int) (Math.random() * 5 + 4);

        int[][] G1 = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                G1[i][j] = (int) (Math.random() * 2);
                if (G1[i][j] == 1) count++;
            }
        }

        G2 = new int[n][count];

        JTable tableG1 = new JTable(new ConjunctionTableModel(G1));

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
        scrollPaneG1.setPreferredSize(new Dimension(tableG1.getWidth(),tableG1.getRowHeight()*tableG1.getRowCount()+25));

        JScrollPane scrollPaneG2 = new JScrollPane(tableG2);
        scrollPaneG2.setPreferredSize(new Dimension(tableG2.getWidth(),tableG2.getRowHeight()*tableG2.getRowCount()+25));


        JButton button = new JButton("Accept calculation");

        JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, n , 1));
        spinner.addChangeListener(e -> calc = (int) spinner.getValue());

        JLabel label=new JLabel("Рассчёты для указанной вершины не производились");

        button.addActionListener(e -> {
            button.setVisible(false);
            curr=0;
            for(int i=0;i<count;i++)
            {
                if (G2[calc][i]==1) curr++;
            }
            label.setText("Степень полуисхода для x"+(calc)+"="+curr);
        });

        JButton changeButton=new JButton("На задание 3");

        changeButton.addActionListener(e -> {
            jf2.setVisible(false);
            new Task3();
        });

        JPanel panel=new JPanel();
        panel.add(changeButton);
        panel.add(button);
        panel.add(spinner);
        panel.add(label);

        jf2.setLayout(new BorderLayout());
        jf2.add(panel,BorderLayout.SOUTH);
        jf2.add(scrollPaneG2, BorderLayout.CENTER);
        jf2.add(scrollPaneG1, BorderLayout.NORTH);
        jf2.pack();

    }

    private class Task2_2TableModel implements TableModel {
        private Set<TableModelListener> listeners = new HashSet<>();

        @Override
        public int getRowCount() {
            return G2.length;
        }

        @Override
        public int getColumnCount() {
            return G2[0].length+1;
        }

        @Override
        public String getColumnName(int columnIndex) {
            if (columnIndex == 0) return "";
            return "a" + columnIndex;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 0) return String.class;
            return int.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) {
                return "x" + (rowIndex + 1);
            }
            return G2[rowIndex][columnIndex-1];
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
