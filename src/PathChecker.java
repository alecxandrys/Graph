import java.util.ArrayList;
import java.util.PriorityQueue;

public class PathChecker extends Thread{

    int target=4;

    @Override
    public void run() {
        //вариант 4
        PriorityQueue<Integer> frontline = new PriorityQueue<>();
        ArrayList<Integer> came_from = new ArrayList<>();
        ArrayList<Integer> cost_so_far = new ArrayList<>();

    }



    private int cost() {
        return 1;
    }

    private ArrayList<Integer> neighood(int x)
    {
        ArrayList<Integer> neighood=new ArrayList<>();

        for(int i=0;i<Task4.G1[x].length;i++)
        {
            if (Task4.G1[x][i]==1)
            {
                neighood.add(i);
            }
        }

        neighood.trimToSize();
        return neighood;
    }
}
