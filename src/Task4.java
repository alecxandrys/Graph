import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class Task4 {

    //не связанный и оба замыкания для х4
    static int G1[][];

    static JFrame jf4;

    Task4()
    {
        jf4 = new JFrame("Не связанность и замыкания. Чудинов Александр Алексеевич");

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

        JTable table = new JTable(new Task4_1TableModel());

        JScrollPane scrollPane = new JScrollPane(table);


        jf4.add(scrollPane, BorderLayout.NORTH);

        Thread cameTo=new PathChecker(4,true);
        cameTo.start();
        Thread cameFrom=new PathChecker(4,false);
        cameFrom.start();
    }

    static void SetTableModel(ArrayList<Integer> circuitIn,ArrayList<Integer> circuitOut, Boolean divided)
    {

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

        JLabel divideMess=new JLabel("Не связанность-"+divided);

        JPanel answer=new JPanel();

        answer.add(circuitInList);
        answer.add(circuitOutList);
        answer.add(divideMess);

        jf4.add(answer,BorderLayout.CENTER);
        jf4.pack();
    }

    private class Task4_1TableModel implements TableModel {
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
