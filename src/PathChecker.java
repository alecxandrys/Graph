import java.util.ArrayList;
import java.util.Hashtable;

class PathChecker extends Thread{

    private int target;

    private boolean cameTo;

    private static ArrayList<Integer> answerListOut=new ArrayList<>();
    private static ArrayList<Integer> answerListIn=new ArrayList<>();

    private static ArrayList<Integer> notOutCircuit;
    private static ArrayList<Integer> notInCircuit;

    PathChecker(int target,boolean cameTo)
    {
        this.target=target;
        this.cameTo=cameTo;
    }

    @Override
    public void run() {
        //вариант 4
        ArrayList<Integer> frontline = new ArrayList<>();
        Hashtable<Integer,Integer> came_from = new Hashtable<>();
        Hashtable<Integer,Integer> cost_so_far = new Hashtable<>();

        frontline.add(target);

        cost_so_far.put(target,0);

        came_from.put(target,-1);

        int current;

        while(!frontline.isEmpty())
        {
            current=frontline.get(0);
            frontline.remove(0);
            ArrayList<Integer> neighbor= neighbor(current);
            for (Integer next:neighbor)
            {
                int newCost=cost_so_far.get(current)+Cost(current,next);
                if (!cost_so_far.containsKey(next)||newCost<cost_so_far.get(next))
                {
                    cost_so_far.put(next,newCost);
                    frontline.add(next);
                    came_from.put(next,current);
                }

            }
        }

        if (cameTo) {
            notOutCircuit=new ArrayList<>();
            for (int i = 0; i < Task4.G1.length; i++) {
                if (came_from.containsKey(i)) {
                    answerListOut.add(i);
                } else {
                    notOutCircuit.add(i);
                }
            }
        }
        else
        {
            notInCircuit=new ArrayList<>();
            for (int i = 0; i < Task4.G1.length; i++) {
                if (came_from.containsKey(i)) {
                    answerListIn.add(i);
                } else {
                    notInCircuit.add(i);
                }
            }
        }

        CallMain();
    }

    private void CallMain()
    {
        if (notOutCircuit!=null && notInCircuit!=null)
        {
            boolean divided=false;
            for (Integer aNotOutCircuit : notOutCircuit) {
                if (!notInCircuit.contains(aNotOutCircuit)) {
                    divided = true;
                    break;
                }
            }
            Task4.SetTableModel(answerListIn,answerListOut,divided);
        }

    }



    private int Cost(int current,int next) {
        return 1;
    }

    private ArrayList<Integer> neighbor(int x)
    {
        ArrayList<Integer> neighbor=new ArrayList<>();

        if (cameTo) {
            for (int i = 0; i < Task4.G1[x].length; i++) {
                if (Task4.G1[x][i] == 1) {
                    neighbor.add(i);
                }
            }
        }
        else
        {
            for (int i = 0; i < Task4.G1.length; i++) {
                if (Task4.G1[i][x] == 1) {
                    neighbor.add(i);
                }
            }
        }

        neighbor.trimToSize();
        return neighbor;
    }
}
