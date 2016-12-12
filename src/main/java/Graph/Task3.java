package Graph;

import javax.swing.*;
import java.awt.*;

class Task3{

    private int G1[][];

    Task3(){
        JFrame jf3=new JFrame("Симметрия графа. Чудинов Александр Алексеевич");

        jf3.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf3.setVisible(true);
        jf3.setResizable(true);

        int n=(int) (Math.random()*6+5);

        G1=new int[n][n];

        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                G1[i][j]=(int) (Math.random()*2);
            }
        }

        boolean symmetry=true;
        for (int i=0; i<n; i++) {
            for (int j=i; j<n; j++) {
                if (G1[i][j]!=G1[j][i]) {
                    symmetry=false;
                    break;
                }
            }
        }

       ConjunctionTableModel tableModel=new ConjunctionTableModel(G1);


        JTable table=new JTable(tableModel);

        JScrollPane scrollPane=new JScrollPane(table);

        scrollPane.setPreferredSize(new Dimension(table.getWidth()+5,table.getRowHeight()*table.getRowCount()+25));

        JLabel label=new JLabel();

        if (symmetry) label.setText("Проверка произведена: Граф симметричен");
        else label.setText("Проверка произведена: Граф не симметричен");

        JButton changeButton=new JButton("На задание 4");

        changeButton.addActionListener(e -> {
            jf3.setVisible(false);
            new Task4();
        });

        JPanel panel=new JPanel();

        panel.add(changeButton);
        panel.add(label);

        jf3.add(scrollPane,BorderLayout.NORTH);
        jf3.add(panel,BorderLayout.CENTER);
        jf3.pack();

        tableModel.addTableModelListener(e -> {
            boolean symmetry1=true;
            for (int i=0; i<n; i++) {
                for (int j=i; j<n; j++) {
                    if (G1[i][j]!=G1[j][i]) {
                        symmetry1=false;
                        break;
                    }
                }
            }
            if (symmetry1) label.setText("Проверка произведена: Граф симметричен");
            else label.setText("Проверка произведена: Граф не симметричен");
        });
    }

}

