import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.Set;


class ConjunctionTableModel implements TableModel {
    private Set<TableModelListener> listeners = new HashSet<>();
    private int G1[][];

    ConjunctionTableModel(int G1[][])
    {
     this.G1=G1;
    }
    @Override
    public int getRowCount() {
        return G1.length;
    }

    @Override
    public int getColumnCount() {
        return G1.length + 1;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex == 0) return "";
        return "x" + columnIndex;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) return String.class;
        return Integer.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex!=0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return "x" + (rowIndex + 1);
        }

        return G1[rowIndex][columnIndex - 1];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if ((int) aValue == 0 || (int) aValue == 1) {
                G1[rowIndex][columnIndex - 1] = (int) aValue;
            }
            listeners.forEach(e->{e.tableChanged(new TableModelEvent(this));});
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
