import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.Callable;

class PathChecker implements Callable<ArrayList> {

    private int target;

    private boolean cameTo;

    private boolean multiThread;

    private int current;

    private ArrayList<Integer> circuit = new ArrayList<>();

    PathChecker(int target, boolean cameTo, boolean multiThread) {
        this.target = target;
        this.cameTo = cameTo;
        this.multiThread = multiThread;
    }

    private int Cost() {
        return 1;
    }

    private ArrayList<Integer> neighbor(int x) {
        ArrayList<Integer> neighbor = new ArrayList<>();

        if (cameTo) {
            for (int i = 0; i < Task4.G1[x].length; i++) {
                if (Task4.G1[x][i] == 1) {
                    neighbor.add(i);
                }
            }
        } else {
            for (int i = 0; i < Task4.G1.length; i++) {
                if (Task4.G1[i][x] == 1) {
                    neighbor.add(i);
                }
            }
        }
        neighbor.trimToSize();
        return neighbor;
    }

    @Override
    public ArrayList call() throws Exception {
        //вариант 4
        ArrayList<Integer> frontline = new ArrayList<>();
        Hashtable<Integer, Integer> came_from = new Hashtable<>();
        Hashtable<Integer, Integer> cost_so_far = new Hashtable<>();

        frontline.add(target);

        cost_so_far.put(target, 0);

        //came_from.put(target, -1); //никогда не учитывает точку начала, иначе её учитывает всегда, так что выкинем на всякий

        while (!frontline.isEmpty()) {
            current = frontline.get(0);
            frontline.remove(0);
            ArrayList<Integer> neighbor = neighbor(current);
            if (multiThread) {
                neighbor.parallelStream()
                        .forEach(next -> {
                            int newCost = cost_so_far.get(current) + Cost();
                            if (!cost_so_far.containsKey(next) || newCost < cost_so_far.get(next)) {
                                cost_so_far.put(next, newCost);
                                frontline.add(next);
                                came_from.put(next, current);
                            }
                        });
            }
            else {
                for (Integer next : neighbor) {
                    int newCost = cost_so_far.get(current) + Cost();
                    if (!cost_so_far.containsKey(next) || newCost < cost_so_far.get(next)) {
                        cost_so_far.put(next, newCost);
                        frontline.add(next);
                        came_from.put(next, current);
                    }

                }
            }
        }

        for (int i = 0; i < Task4.G1.length; i++) {
            if (came_from.containsKey(i)) {
                circuit.add(i);
            }
        }

        return circuit;
    }
}
