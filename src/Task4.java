import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

class Task4 {

    //не связанный и оба замыкания для х4
    static int G1[][];

    private static JFrame jf4;

    Task4() {
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

        scrollPane.setPreferredSize(new Dimension(table.getWidth() + 5, table.getRowHeight() * table.getRowCount() + 25));

        JLabel circuitInList = new JLabel("Элементы обратного замыкания: ");
        JLabel circuitOutList = new JLabel("Элементы прямого замыкания: ");
        JLabel divideMess = new JLabel("Связанность- ");

        JPanel answer = new JPanel();

        answer.add(circuitInList);
        answer.add(circuitOutList);
        answer.add(divideMess);

        boolean divide = true;

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        FutureTask<ArrayList> circuitIn = new FutureTask<>(new PathChecker(4, true));
        FutureTask<ArrayList> circuitOut = new FutureTask<>(new PathChecker(4, false));

        long start = System.currentTimeMillis();

        executor.submit(circuitIn);
        executor.submit(circuitOut);

        while (!circuitIn.isDone() && !circuitOut.isDone()) {

        }

        long finish = System.currentTimeMillis();

        ArrayList<Integer> circuitInArray;
        ArrayList<Integer> circuitOutArray;
        try {
            circuitInArray = circuitIn.get();
            circuitOutArray = circuitOut.get();
            for (Integer element : circuitInArray) {
                circuitInList.setText(circuitInList.getText() + " " + (element + 1));
            }
            for (Integer element : circuitOutArray) {
                circuitOutList.setText(circuitOutList.getText() + " " + (element + 1));
            }
            if (G1.length != circuitInArray.size() || G1.length != circuitOutArray.size()) divide = false;

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        divideMess.setText(divideMess.getText() + divide);

        JButton changeButton = new JButton("Исполнить многопоточное сравнение");
        changeButton.addActionListener(e -> {
            jf4.setVisible(false);
            new Task5();
        });

        JLabel timeMess = new JLabel("Время выполнения штатного задания (2 потока)=" + (finish - start) + "мс");

        JPanel charge = new JPanel();

        charge.add(changeButton);
        charge.add(timeMess);

        jf4.add(scrollPane, BorderLayout.NORTH);
        jf4.add(answer, BorderLayout.CENTER);
        jf4.add(charge, BorderLayout.SOUTH);
        jf4.pack();
    }
}
