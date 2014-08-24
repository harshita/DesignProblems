/**
 * Created with IntelliJ IDEA.
 */
/**
 * An item consist of different keys say k1, k2, k3. User can insert any number of items in database, search for item using any key,
 * delete it using any key and iterate through all the items in sorted order using any key.
 * Give the most efficient way such that it supports insertion, search based on a key, iteration and deletion.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;

class MyEmployee {
    private int id;  //Key 1
    private String name;     //Key 2
    private Double salary;   //Key 3

    public MyEmployee(int id, String name, Double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public int getID(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Double getSalary() {
        return this.salary;
    }

    public String toString() {
        return "Name: " + this.getName()  + " ID: "+ this.getID() + " Salary: "+this.getSalary();

    }

}

public class DesignProblem1 {
    private final HashMap<String, TreeMap<? extends Object, ArrayList<Integer>>>keyTypeHash;
    private TreeMap<Integer, ArrayList<Integer>> treeMap1;
    private TreeMap<String, ArrayList<Integer>> treeMap2;
    private TreeMap<Double, ArrayList<Integer>> treeMap3;
    private ArrayList<MyEmployee> items;

    DesignProblem1() {
       this.keyTypeHash = new HashMap<String, TreeMap<? extends Object, ArrayList<Integer>>>();
       this.treeMap1 = new TreeMap<Integer, ArrayList<Integer>>();
       this.treeMap2 = new TreeMap<String, ArrayList<Integer>>();
       this.treeMap3 = new TreeMap<Double, ArrayList<Integer>>();
       this.keyTypeHash.put("ID", treeMap1);
       this.keyTypeHash.put("Name", treeMap2);
       this.keyTypeHash.put("Salary", treeMap3);
       items = new ArrayList<MyEmployee>();

    }

    public void printArrayList(ArrayList<Integer> itemList) {
        for(Integer i : itemList) {
            System.out.println(items.get(i));
        }
    }

    public void addItem(MyEmployee e) {
        items.add(e);
        //insert in treeMap 1
        if (treeMap1.containsKey(e.getID())) {
            ArrayList<Integer> position = (ArrayList<Integer>)treeMap1.get(e.getID());
            position.add(items.size() - 1);

        }
        else  {
            ArrayList<Integer> positions = new ArrayList<Integer>();
            positions.add(items.size()-1);
            treeMap1.put(e.getID(), positions);
        }

        //insert in treeMap 2
        if (treeMap2.containsKey(e.getName())) {
            ArrayList<Integer> position = (ArrayList<Integer>)treeMap2.get(e.getName());
            position.add(items.size() - 1);

        }
        else {
            ArrayList<Integer> positions = new ArrayList<Integer>();
            positions.add(items.size()-1);
            treeMap2.put(e.getName(), positions);
        }


        //insert in treeMap 3
        if (treeMap3.containsKey(e.getSalary())) {
            ArrayList<Integer> position = (ArrayList<Integer>)treeMap3.get(e.getSalary());
            position.add(items.size() - 1);

        }
        else {
            ArrayList<Integer> positions = new ArrayList<Integer>();
            positions.add(items.size()-1);
            treeMap3.put(e.getSalary(), positions);
        }



    }

    public void deleteItem(String keyType, String value) {
        ArrayList<Integer> positions = searchItem(keyType, value);
        for (Integer position: positions) {
            MyEmployee e = items.get(position);
            treeMap1.remove(e.getID());
            treeMap2.remove(e.getName());
            treeMap3.remove(e.getSalary());
        }

    }

    public ArrayList<Integer> searchItem(String keyType, String value) {
        TreeMap tm = keyTypeHash.get(keyType);
        if (keyType == "ID") {
            if (tm.containsKey(Integer.parseInt(value))) {
                ArrayList<Integer> positions = (ArrayList<Integer>)tm.get(Integer.parseInt(value));
                return positions;
            }
        }
        else if (keyType == "Name") {
            if (tm.containsKey(value)) {
                ArrayList<Integer> positions = (ArrayList<Integer>)tm.get(value);
                return positions;
            }
        }
        else {
            if (tm.containsKey(Double.parseDouble(value))) {
                ArrayList<Integer> positions = (ArrayList<Integer>)tm.get(Double.parseDouble(value));
                return positions;
            }

        }
        return null;
    }

    public Iterator iterate(String keyType) {
        return keyTypeHash.get(keyType).entrySet().iterator();
    }

    public static void main(String args[]) {
        DesignProblem1 dp = new DesignProblem1();
        dp.addItem(new MyEmployee(1, "Adam", 3000.0));
        dp.addItem(new MyEmployee(2, "Eve", 4000.0));
        dp.addItem(new MyEmployee(3, "John", 4000.0));
        dp.addItem(new MyEmployee(4, "Jim", 5000.0));

        System.out.println("Searching for name: Jim");
        ArrayList<Integer> searchedList = dp.searchItem("Name", "Jim");
        dp.printArrayList(searchedList);

        System.out.println();
        System.out.println("Searching for salary: 4000.0");
        searchedList = dp.searchItem("Salary", "4000.0");
        dp.printArrayList(searchedList);

        System.out.println();
        System.out.println("Searching for ID: 1");
        searchedList = dp.searchItem("ID", "1");
        dp.printArrayList(searchedList);

        System.out.println();
        System.out.println("Iteration results");
        Iterator it = dp.iterate("Name");
        while(it.hasNext()) {
            Map.Entry<String, ArrayList<Integer>> entry = (Map.Entry<String, ArrayList<Integer>>) it.next();
            dp.printArrayList(entry.getValue());

        }

        dp.deleteItem("Name", "Eve");
        System.out.println();
        System.out.println("List after deleting Eve");
        it = dp.iterate("Name");
        while(it.hasNext()) {
            Map.Entry<String, ArrayList<Integer>> entry = (Map.Entry<String, ArrayList<Integer>>) it.next();
            dp.printArrayList(entry.getValue());
        }


    }


}
