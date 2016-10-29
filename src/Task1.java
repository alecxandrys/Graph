import javax.swing.*;
import java.awt.*;

public class Task1 implements Runnable {

    private int del;
    private int G1[][];
    private int n;

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Task1());
    }

    @Override
    public void run() {

        JFrame jf1 = new JFrame("Максимальная степень полуисхода.Чудинов Александр Алексеевич");
        jf1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jf1.setVisible(true);
        jf1.setResizable(true);

        n = (int) (Math.random() * 6 + 5);

        G1 = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                G1[i][j] = (int) (Math.random() * 2);
            }
        }

        JTable table = new JTable(new ConjunctionTableModel(G1));

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setPreferredSize(new Dimension(table.getWidth()+5,table.getRowHeight()*table.getRowCount()+25));

        JButton button = new JButton("Accept deletion");
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, n , 1));
        JLabel label=new JLabel("Рассчет для указанной вершины не производился");

        button.addActionListener(e -> {
            if (n!=1) {
                int G2[][] = new int[n - 1][n - 1];
                for (int i = 0; i < n; i++) {
                    if (i == del) continue;
                    for (int j = 0; j < n; j++) {
                        if (j == del) continue;
                        if (i < del && j < del) {
                            G2[i][j] = G1[i][j];
                        } else if (i < del && j > del) {
                            G2[i][j - 1] = G1[i][j];
                        } else if (i > del && j < del) {
                            G2[i - 1][j] = G1[i][j];
                        } else if (i > del && j > del) {
                            G2[i - 1][j - 1] = G1[i][j];
                        }
                    }
                }
                G1 = G2;
                table.setModel(new ConjunctionTableModel(G1));

                int max = 0;
                int index = 0;

                for (int i = 0; i < n - 1; i++) {
                    int curr = 0;
                    for (int j = 0; j < n - 1; j++) {
                        curr = curr + G1[i][j];
                    }
                    if (curr > max) {
                        max = curr;
                        index = i;
                    }
                }

                label.setText("Максимальная полустепень исхода при x" + index + '=' + max);
                n--;
            }
            else
            {
                label.setText("Данная операция невозможна");
            }
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

        jf1.setLayout(new BoxLayout(jf1.getContentPane(),BoxLayout.PAGE_AXIS));
        jf1.add(scrollPane);
        jf1.add(panel);
        jf1.pack();

    }
}
