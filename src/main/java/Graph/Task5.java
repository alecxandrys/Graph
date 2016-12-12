package Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

class Task5{

    private static JFrame jf5;

    private int G1[][];

    private int G2[][];

    private int G3[][];

    private JPanel tables;

    private int n;

    Task5(){
        jf5=new JFrame("Чудинов Александр Алексеевич");

        jf5.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf5.setVisible(true);
        jf5.setResizable(true);

        n=(int) (Math.random()+5);

        G1=new int[n][n];

        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                G1[i][j]= (int) (Math.random() * 2);
            }
        }

        n=(int) (Math.random()+5);

        G2=new int[n][n];

        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                G2[i][j]= (int) (Math.random() * 2);
            }
        }


        JTable tableG1=new JTable(new ConjunctionTableModel(G1));

        JScrollPane scrollPaneG1=new JScrollPane(tableG1);

        scrollPaneG1.setPreferredSize(new Dimension(tableG1.getWidth()+5,tableG1.getRowHeight()*tableG1.getRowCount()+25));

        JTable tableG2=new JTable(new ConjunctionTableModel(G2));

        JScrollPane scrollPaneG2=new JScrollPane(tableG2);

        scrollPaneG2.setPreferredSize(new Dimension(tableG2.getWidth()+5,tableG2.getRowHeight()*tableG2.getRowCount()+25));

        JButton calcG3=new JButton("Рассчитать граф G3");
        calcG3.addActionListener(new calculateG3());

        tables=new JPanel();
        tables.setLayout(new BoxLayout(tables,BoxLayout.PAGE_AXIS));

        tables.add(new Label("Граф G1"));
        tables.add(scrollPaneG1);
        tables.add(new Label("Граф G2"));
        tables.add(scrollPaneG2);
        tables.add(calcG3);


        jf5.add(tables,BorderLayout.NORTH);
        JButton changeButton=new JButton("На задание 5");
        changeButton.addActionListener(e -> {
            jf5.setVisible(false);
            new Task6();
        });
        jf5.add(changeButton,BorderLayout.SOUTH);
        jf5.pack();
    }

    private class calculateG3 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){

            JButton source=(JButton) e.getSource();
            source.setVisible(false);
            if (G2.length<=G1.length) n=G2.length;
            else n=G1.length;

            G3=new int[n][n];

            for (int i=0; i<n; i++) {
                for (int j=0; j<n; j++) {
                    if (G1[i][j]==1 && G2[i][j]==1) {
                        G3[i][j]=1;
                    }
                    else {
                        G3[i][j]=0;
                    }
                }
            }
            JTable tableG3=new JTable(new ConjunctionTableModel(G3));

            JScrollPane scrollPaneG3=new JScrollPane(tableG3);

            scrollPaneG3.setPreferredSize(new Dimension(tableG3.getWidth()+5,tableG3.getRowHeight()*tableG3.getRowCount()+25));

            JButton calcSubGraph=new JButton("Рассчитать субграфы");
            calcSubGraph.addActionListener(new calculateSubGraph());

            tables.add(new Label("Граф G3"));
            tables.add(scrollPaneG3);
            tables.add(calcSubGraph);
            jf5.pack();
        }
    }

    private class calculateSubGraph implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            JButton butt=(JButton) e.getSource();
            butt.setVisible(false);
            JTextArea log=new JTextArea("Вывод лога поиска сильно связанных подграфов по методу Мальгранжа");
            JScrollPane logPane=new JScrollPane(log);

            ArrayList<Integer> usedComp=new ArrayList<>();

            for (int i=0; i<n; i++) {
                usedComp.add(i,0);
            }

            int[][] temp=new int[n][n];

            for (int i=0; i<n; i++) {
                System.arraycopy(G3[i],0,temp[i],0,n);
            }

            int currpoint=0;

            while (true) {

                Task4.G1=temp;
                ExecutorService executor=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

                FutureTask<ArrayList> circuitIn=new FutureTask<>(new PathChecker(0,true,false));
                FutureTask<ArrayList> circuitOut=new FutureTask<>(new PathChecker(0,false,false));

                long start=System.currentTimeMillis();

                executor.submit(circuitIn);
                executor.submit(circuitOut);

                while (!circuitIn.isDone() && !circuitOut.isDone()) {

                }

                long finish=System.currentTimeMillis();

                ArrayList<Integer> circuitInArray=null;
                ArrayList<Integer> circuitOutArray=null;
                try {
                    circuitInArray=circuitIn.get();
                    circuitOutArray=circuitOut.get();
                } catch (InterruptedException|ExecutionException e1) {
                    e1.printStackTrace();
                }


                if (circuitInArray==null || circuitOutArray==null) {
                    log.append("\nДля компоненты "+(currpoint+1)+" подграфа не выявлено\n Время исполнения равняется "+(finish-start));
                    break;
                }


                ArrayList<Integer> subGraph=new ArrayList<>();

                for (Integer element : circuitInArray) {
                    if (circuitOutArray.contains(element)) {
                        subGraph.add(element);
                    }
                }

                log.append("\nДля компоненты "+(currpoint+1)+" выявлены следущие компоненты подграфа ");
                for (Integer element : subGraph) {
                    log.append((String.valueOf(usedComp.indexOf(0)+element+1))+" ");
                }
                log.append("\nВремя исполнения равняется "+(finish-start));

                if (subGraph.size()>=n) {
                    break;
                }
                else {
                    n=n-(subGraph.size());
                    int[][] tempo=new int[n][n];
                    int k=0;//line (x)
                    int p=0;//column (y)
                    for (int i=0; i<temp.length; i++) {//дописывать usedComp здесь
                        if (subGraph.contains(i)) {
                            usedComp.set(usedComp.indexOf(0),-1);//порядок линии не меняется, 0-еще в темпе, -1-уже выкинута, 1-пропущена. Берём всегда первую.
                            continue;
                        }
                        for (int j=0; j<temp[i].length; j++) {
                            if (subGraph.contains(j)) {
                                continue;
                            }
                            tempo[k][p]=temp[i][j];
                            p++;
                        }
                        usedComp.set(usedComp.indexOf(0),1);//замещение неиспользуемой строки, чтобы не попала под раздачу без смещения.
                        k++;
                        p=0;
                    }
                    temp=tempo;//новый расходник с убранными элементами уже которые использовались
                    while (usedComp.indexOf(1)!=-1)//возвращаем все элементы с 1 в 0
                    {
                        usedComp.set(usedComp.indexOf(1),0);
                    }
                }
                if (usedComp.indexOf(0)==-1) {
                    break;
                }
                currpoint=usedComp.indexOf(0);
            }

            jf5.add(logPane,BorderLayout.CENTER);
            jf5.pack();
        }
    }
}
