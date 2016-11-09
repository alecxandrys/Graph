import javax.swing.*;
import java.awt.*;

class Task5 {

    private static JFrame jf5;

    int G1[][];

    int G2[][];

    int G3[][];

    Task5()
    {
        jf5 = new JFrame("Чудинов Александр Алексеевич");

        jf5.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf5.setVisible(true);
        jf5.setResizable(true);

        int n = (int) (Math.random() * 6 + 5);

        G1 = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                G1[i][j] = (int) (Math.random() * 2);
            }
        }

        n = (int) (Math.random() * 6 + 5);

        G2 = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                G2[i][j] = (int) (Math.random() * 2);
            }
        }

        G3=new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (G1[i][j]==1 && G2[i][j]==1)
                {
                    G3[i][j]=1;
                }
                else
                {
                    G3[i][j]=0;
                }
            }
        }

        JTable tableG1 = new JTable(new ConjunctionTableModel(G1));

        JScrollPane scrollPaneG1 = new JScrollPane(tableG1);

        scrollPaneG1.setPreferredSize(new Dimension(tableG1.getWidth() + 5, tableG1.getRowHeight() * tableG1.getRowCount() + 25));

        JTable tableG2 = new JTable(new ConjunctionTableModel(G2));

        JScrollPane scrollPaneG2 = new JScrollPane(tableG2);

        scrollPaneG2.setPreferredSize(new Dimension(tableG2.getWidth() + 5, tableG2.getRowHeight() * tableG2.getRowCount() + 25));

        JTable tableG3 = new JTable(new ConjunctionTableModel(G3));

        JScrollPane scrollPaneG3 = new JScrollPane(tableG3);

        scrollPaneG3.setPreferredSize(new Dimension(tableG3.getWidth() + 5, tableG3.getRowHeight() * tableG3.getRowCount() + 25));


        JPanel tables=new JPanel();
        tables.setLayout(new BoxLayout(tables, BoxLayout.PAGE_AXIS));

        tables.add(new Label("Граф G1"));
        tables.add(scrollPaneG1);
        tables.add(new Label("Граф G2"));
        tables.add(scrollPaneG2);
        tables.add(new Label("Граф G3"));
        tables.add(scrollPaneG3);

        jf5.add(tables,BorderLayout.NORTH);
        jf5.pack();
    }
}
