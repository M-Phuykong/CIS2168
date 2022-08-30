
import java.util.*;

public class ArrayListHW {

    // DONE
    public static <E> boolean unique(List<E> list){
        return list.size() == new HashSet<E>(list).size();
    }

    // DONE
    public static List<Integer> allMultiples(List<Integer> list, int n) {
        list.removeIf(num -> (num % n != 0));
        return list;
    }

    // DONE
    public static List<String> allStringsOfSize(List<String> list, int length) {
        list.removeIf(word -> (word.length() != length));
        return list;
    }

    // DONE?
    public static <E> boolean isPermutation(List<E> list1, List<E> list2) {
        Map<E, Integer> hashmap = new HashMap<E, Integer>();

        for (E i: list1){
            Integer count = hashmap.get(i);
            if (count == null){
                hashmap.put(i, 1);
            }
            else {
                hashmap.put(i, hashmap.get(i) + 1);
            }
        }

        for (E i : list2) {
            Integer count = hashmap.get(i);
            if (count == null) {
                return false;
            } else {
                hashmap.put(i, hashmap.get(i) - 1);
            }
        }
        hashmap.values().removeIf(count -> count == 0);

        if (!hashmap.isEmpty()){
            return false;
        }
        
        return true;
    }

    // DONE
    public static List<String> stringToListOfWords(String words) {
        
        List<String> res = new ArrayList<String>(Arrays.asList(words.split(" ")));      
        for (int i = 0; i < res.size(); i++){
            res.set(i, res.get(i).replaceAll("\\p{Punct}", ""));
        }
        return res;
    }

    // DONE
    public static <E> void removeAllInstances(List<E> list, Object item) {
        list.removeAll(new ArrayList<Object>(
                Arrays.asList(item)));
    }

}
