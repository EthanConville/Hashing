import java.util.*;
public class Hashing2 {
    //make sure table size is prime
    static Integer tableSize = 60961;
    static int currentSize = 0;
    static int quadCollisions = 0;
    static int linearCollisions = 0;
    static int dubCollisions = 0;

    private static List<String> [ ] theLists;



    //Simple hash function. O(1) time
    public static Integer hash(Integer key){
        return key % tableSize;
    }

    //creates empty array of linked lists. O(N) time for for loop
    public static List<String>[] chain(){
        theLists = new LinkedList[tableSize];
        for( int i = 0; i < theLists.length; i++ )
            theLists[ i ] = new LinkedList<>( );
        return theLists;
    }
    //insertion takes O(1) time. O(1) access to array + O(1) to add to list
    //Could add the ability to accept arrays as inputs to add more than one value
    //at once.
    public static void InsertChain(Integer key, String data, List<String> [] list){
        Integer hash = hash(key);
        if(!list[hash].contains(data)) {
            list[hash].add(data);
            currentSize++;
        }

    }
    //Complexity O(1) end for access time
    public static void RemoveChain(Integer key, String data, List<String> [] list){
        Integer hash = hash(key);
        if(list[hash] == null){
            return;
        }
        list[hash].remove(data);
        currentSize--;
    }

    //Complexity O(N) for iteration through list worst case
    public static void print(Integer key, List<String> [] list){
        Integer hash = hash(key);
        if(list[hash] != null){
            System.out.println("List : " + list[hash].toString());
        }
    }

    //Worst Case complexity O(N) for iteration.
    public static void printAllChain(List<String> [] list){
        for(int i = 0; i < tableSize; i++){
            if(list[i] != null){
                System.out.println("List : " + list[i].toString());
            }
        }
    }

    //Makes all values in the array -1. O(N) for array fill (From what I can find upper bound is O(N)
    //but some implementations may not actually add the element until that array value is accessed
    //so the complexity could be lower)
    public static int [] linear(){
        int[] linear = new int[tableSize];
        Arrays.fill(linear, -1);
        return linear;
    }

    public static void addLinear(int x, int[] list){
        int hash = hash(x);
        if(list[hash] == -1){
            list[hash] = x;
            return;
        }
        //hash already occupied
        else
        {
            linearCollisions++;
            int probe = hash + 1;
            int check = 0;
            //check every other value in array for open spot, if none found print failed to add.
            for(int i = 0; i < tableSize; i++){

                probe += i;
                if(probe >= tableSize){
                    if(check == 1 && probe > hash){
                        System.out.println("Could not enter value, array full");
                        return;
                    }
                    probe = 0;
                    check++;
                    continue;
                }
                if(list[probe] == -1){
                    list[probe] = x;
                    return;
                }
                linearCollisions++;
            }

        }
    }
    public static void addQuadratic(int x, int[] list){
        int hash = hash(x);
        if(list[hash] == -1){
            list[hash] = x;
            return;
        }
        //hash already occupied
        else{
            quadCollisions++;
            int check = 0;
            int temp = hash(x);
            for(int i = 1; i < tableSize; i++){
                temp = i * i * temp;
                if((temp) >= tableSize){
                    if(check == 1 && temp > hash) {
                        System.out.println("Could not enter value, all probes full");
                        return;
                    }
                    check++;
                    continue;
                }
                if(list[temp] == -1){
                    list[temp] = x;
                    return;
                }
                quadCollisions++;
            }
        }
    }
    public static int hash2(int x){
        return (7 - (x % 7));
    }
    public static void addDoubleHash(int x, int[] list){
        int hash = hash(x);
        if(list[hash] == -1){
            list[hash] = x;
            return;
        }
        //hash already occupied. time complexity is O(log(N)) because hash function is exponential
        else{
            dubCollisions++;
            int temp = hash2(x);
            for(int i = 1; (i * temp) < tableSize; i++){
                temp = temp * i;
                if(list[temp] == -1)
                {
                    list[temp] = x;
                    return;
                }

                dubCollisions++;
            }
            System.out.println("Value Could not be added");
        }
    }
    public static void printLinear(int[] list){
        for(int i = 0; i < tableSize; i++){
            System.out.println("Index " + i +  " has: " + list[i] + "\n");
        }
    }


    public static void main(String[] args) {

        //Question 5.3
        int max = 30000;
        int min = 1;
        int range = max - min + 1;
        int[] Linear2 = linear();
        int[] quadratic2 = linear();
        int[] dubHash2 = linear();

        for(int i =0; i < 5000; i++){
            int rand = (int)(Math.random() * range) + min;
            addLinear(rand, Linear2);
            addDoubleHash(rand, quadratic2);
            addQuadratic(rand, dubHash2);
        }
        System.out.println("Linear Collisions: " + linearCollisions + "\n");
        System.out.println("Quadratic Collisions: " + quadCollisions + "\n");
        System.out.println("Double Hash Collisions: " + dubCollisions + "\n");



    }
}