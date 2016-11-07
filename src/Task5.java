import javax.swing.*;

class Task5 {

    private static JFrame jf5;

    int G1[][];

    int G2[][];

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
    }
}
