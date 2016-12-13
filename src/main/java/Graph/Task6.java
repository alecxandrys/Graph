package Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by alecxanrys
 */
class Task6{
    private static JFrame jf6;

    private int[][] map={{0,2,5,3,0,4,0,0},{4,0,0,8,0,15,0,0},{9,1,0,2,5,0,13,0},{0,8,2,0,16,0,3,0},{0,0,5,16,0,0,0,4},{4,15,0,0,0,6,7,0},{0,0,13,0,0,7,0,3},{0,0,0,0,4,0,0,0}};

    private int del;

    private JTextArea log;

    Task6(){
        jf6=new JFrame("Чудинов Александр Алексеевич");

        jf6.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf6.setVisible(true);
        jf6.setResizable(true);

        jf6.setLayout(new  BorderLayout());

        JTable table=new JTable(new ConjunctionTableModel(map));

        JScrollPane scrollPane=new JScrollPane(table);

        scrollPane.setPreferredSize(new Dimension(table.getWidth(),table.getRowHeight()*table.getRowCount()+25));



        JButton button=new JButton("Рассчет для указанной точки");
        button.addActionListener(new Calculate());
        JSpinner spinner=new JSpinner(new SpinnerNumberModel(1,1,map.length,1));
        spinner.addChangeListener(e -> del=(int) spinner.getValue());

        JPanel centralPanel=new JPanel();

        centralPanel.add(button);
        centralPanel.add(spinner);

        log=new JTextArea("Здесь выводится лог операции",15,50);
        log.setEditable(false);
        JScrollPane logPane=new JScrollPane(log);

        jf6.add(logPane,BorderLayout.SOUTH);
        jf6.add(scrollPane,BorderLayout.NORTH);
        jf6.add(centralPanel,BorderLayout.CENTER);
        jf6.pack();
    }

    private class Calculate implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            ExecutorService executor=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

            FutureTask<ArrayList> circuitIn=new FutureTask<>(new PathFinder());

            executor.submit(circuitIn);

        }
    }

    private class PathFinder implements Callable<ArrayList>{
        private int current;
        private int Cost(int curr,int next){
            if (map[curr][next]==0) return 1000;
            else return map[curr][next];
        }

        private ArrayList<Integer> neighbor(int x){
            ArrayList<Integer> neighbor=new ArrayList<>();
            for (int i=0; i<map.length; i++) {
                if (map[x][i]!=0) {
                    neighbor.add(i);
                }
            }
            neighbor.trimToSize();
            return neighbor;
        }

        @Override
        public ArrayList call() throws Exception{
            ArrayList<Integer> frontline=new ArrayList<>();
            Hashtable<Integer,Integer> came_from=new Hashtable<>();
            Hashtable<Integer,Integer> cost_so_far=new Hashtable<>();

            frontline.add(del);

            cost_so_far.put(del,0);

            came_from.put(del,-1);// всегда добавляется начальная точка

            while (!frontline.isEmpty()) {
                current=frontline.get(0);
                frontline.remove(0);
                ArrayList<Integer> neighbor=neighbor(current);
                    neighbor.parallelStream().forEach(next -> {
                        int newCost=cost_so_far.get(current)+Cost(current,next);
                        if (!cost_so_far.containsKey(next) || newCost<cost_so_far.get(next)) {
                            cost_so_far.put(next,newCost);
                            frontline.add(next);
                            came_from.put(next,current);
                        }
                    });
                }
            ArrayList<Integer> path;
            for (int i=0;i<map.length;i++)
            {
                path = new ArrayList<>();
                current=i;
                if (cost_so_far.get(current)<1000) {
                    while (current!=del) {
                        current=came_from.get(current);
                        path.add(current);
                    }
                    log.append("\nДля точки "+(i+1)+" шаги достижения следующие:");
                    log.append("\nЦена"+(cost_so_far.get(i)));
                    for (Integer aPath : path) {
                        log.append("\nStep "+(aPath+1));
                    }
                }
                else
                {
                    log.append("\nДля точки "+(current+1)+" нет проходимого маршрута");
                }
            }
            return null;
        }
    }
}
