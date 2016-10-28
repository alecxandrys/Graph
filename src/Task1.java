import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Task1 implements Runnable {

    private int del;
    private int G1[][];

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Task1());
    }

    @Override
    public void run() {

        JFrame jf1 = new JFrame("Максимальная степень полуисхода. Чудинов Александр Алексеевич");
        jf1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jf1.setVisible(true);
        jf1.setResizable(true);

        int n = (int) (Math.random() * 6 + 5);

        G1 = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                G1[i][j] = (int) (Math.random() * 2);
            }
        }

        JTable table = new JTable(new Task1TableModel());

        JScrollPane scrollPane = new JScrollPane(table);

        JButton button = new JButton("Accept deletion");
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, n - 1, 1));
        JLabel label=new JLabel();

        button.addActionListener(e -> {
            button.setVisible(false);
            int G2[][] = new int[n - 1][n - 1];

            for (int i = 0; i < n; i++) {
                if (i == del) continue;
                for (int j = 0; j < n; j++) {
                    if (j == del) continue;
                    if (i < del && j < del) {
                        G2[i][j] = G1[i][j];
                    } else if (i < del && j > del) {
                        G2[i][j-1] = G1[i][j];
                    } else if (i > del && j < del) {
                        G2[i-1][j] = G1[i][j];
                    } else if (i > del && j > del){
                        G2[i-1][j-1] = G1[i][j];
                    }
                }
            }
            G1 = G2;
            table.setModel(new Task1TableModel());

            int max=0;
            int index=0;

            for (int i=0;i<n-1;i++)
            {
                int curr=0;
                for (int j=0;j<n-1;j++)
                {
                    curr=curr+G1[i][j];
                }
                if (curr>max) {max=curr;index=i;}
            }

            label.setText("Максимальная полустепень исхода при x"+index+'='+max);
        });

        spinner.addChangeListener(e -> del = (int) spinner.getValue());

        JPanel panel = new JPanel();


        JButton changeButton=new JButton("На задание 2");

        changeButton.addActionListener(e -> {
            jf1.setVisible(false);
            new Task2();
        });

        panel.add(changeButton);
        panel.add(button);
        panel.add(spinner);
        panel.add(label);


        jf1.setLayout(new BorderLayout());
        jf1.add(panel, BorderLayout.CENTER);
        jf1.add(scrollPane, BorderLayout.NORTH);
        jf1.pack();


    }

    private class Task1TableModel implements TableModel {
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
