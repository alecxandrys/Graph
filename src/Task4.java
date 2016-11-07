import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Task4 {

    //не связанный и оба замыкания для х4
    static int G1[][];

    private static JFrame jf4;
    private static long start;

    Task4()
    {
        jf4 = new JFrame("Связанность и замыкания. Чудинов Александр Алексеевич");

        jf4.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf4.setVisible(true);
        jf4.setResizable(true);

        int n = (int) (Math.random() * 6 + 5);

        G1 = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                G1[i][j] = (int) (Math.random() * 2);
            }
        }

        JTable table = new JTable(new ConjunctionTableModel(G1));

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setPreferredSize(new Dimension(table.getWidth()+5,table.getRowHeight()*table.getRowCount()+25));

        jf4.add(scrollPane, BorderLayout.NORTH);

        start = System.currentTimeMillis();
        Thread cameTo=new PathChecker(4,true);
        cameTo.start();
        Thread cameFrom=new PathChecker(4,false);
        cameFrom.start();

    }

    static void SetTableModel(ArrayList<Integer> circuitIn,ArrayList<Integer> circuitOut, Boolean divided)
    {
        long finish = System.currentTimeMillis();

        JLabel circuitInList=new JLabel("Элементы прямого замыкания: ");

        for (Integer element:circuitIn)
        {
            circuitInList.setText(circuitInList.getText()+" "+element);
        }

        JLabel circuitOutList=new JLabel("Элементы обратного замыкания: ");

        for (Integer element:circuitOut)
        {
            circuitOutList.setText(circuitOutList.getText()+" "+element);
        }

        JLabel divideMess=new JLabel("Связанность-"+!divided);

        JPanel answer=new JPanel();

        answer.add(circuitInList);
        answer.add(circuitOutList);
        answer.add(divideMess);

        JButton changeButton=new JButton("Исполнить многопоточное сравнение");
        changeButton.addActionListener(e -> {
            jf4.setVisible(false);
            new Task5();
        });

        JLabel timeMess=new JLabel("Время выполнения штатного задания (2 потока)="+(finish - start)+"мс");

        JPanel charge=new JPanel();

        charge.add(changeButton);
        charge.add(timeMess);

        jf4.add(answer,BorderLayout.CENTER);
        jf4.add(charge,BorderLayout.SOUTH);
        jf4.pack();
    }
}
