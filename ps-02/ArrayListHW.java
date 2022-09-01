
import java.util.*;

public class ArrayListHW {

    public static <E> boolean unique(List<E> list){
        
        // if a list is unique then the size of it and its set
        // should be the same
        //
        return list.size() == new HashSet<E>(list).size();
    }

    public static List<Integer> allMultiples(List<Integer> list, int n) {
        
        // filter the list by modding the number by n;
        // if there's a remainder then it is not a multiple of n
        //
        list.removeIf(num -> (num % n != 0));
        return list;
    }

    public static List<String> allStringsOfSize(List<String> list, int length) {
        
        // filter the list and just look for the lenght of each word
        // by using the lenght() method
        //
        list.removeIf(word -> (word.length() != length));
        return list;
    }

    public static <E> boolean isPermutation(List<E> list1, List<E> list2) {

        if (list1.size() != list2.size()){
            return false;
        }

        // Use a hashmap to keep track of the frequency in the list
        //
        Map<E, Integer> hashmap = new HashMap<E, Integer>();

        // get the frequency of list1
        //
        for (E i: list1){
            
            // see if it's in the hashmap
            //
            Integer count = hashmap.get(i);

            // add to hashmap if it doesn't exit yet
            //
            if (count == null){
                hashmap.put(i, 1);
            }
            // increase its count by 1
            //
            else {
                hashmap.put(i, hashmap.get(i) + 1);
            }
        }

        // decrease the frequency of the hashmap by the item in list2
        //
        for (E i : list2) {
            Integer count = hashmap.get(i);
            
            // if there's an item in list2 that exit and isn't in the hashmap
            // then we know that it is not a permutation of list1 
            //
            if (count == null) {
                return false;
            } else {
                hashmap.put(i, hashmap.get(i) - 1);
            }
        }
        
        // remove any value that is 0
        //
        hashmap.values().removeIf(count -> count == 0);

        // if list1 and list2 are permutation of each other, then the hashmap
        // should be empty
        //
        if (!hashmap.isEmpty()){
            return false;
        }
        
        return true;
    }

    public static List<String> stringToListOfWords(String words) {
        
        // split the list
        //
        List<String> res = new ArrayList<String>(Arrays.asList(words.split(" \\s+")));      
        
        // go through each word in the list and replace all special character
        //
        for (int i = 0; i < res.size(); i++){
            res.set(i, res.get(i).replaceAll("\\p{Punct}", ""));
        }
        return res;
    }

    public static <E> void removeAllInstances(List<E> list, Object item) {

        // remove all the item from the list
        //
        list.removeAll(Arrays.asList(item));
    }
    

}
