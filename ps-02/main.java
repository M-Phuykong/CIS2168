import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ArrayListHW;

public class main {
    
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>(
            Arrays.asList(1, 25, 2, 5, 30, 19, 57, 2, 25));
        List<String> list1 = new ArrayList<String>(
            Arrays.asList("I", "like", "to", "eat", "eat", "eat",
                        "apples", "and", "bananas"));
        // list.add("e");
        // list.add("this");
        // list.add("a");

        // boolean res = ArrayListHW.unique(list);

        // System.out.println(list);
        List<Integer> list3 = new ArrayList<Integer>(
                Arrays.asList(2,1,4));
        List<Integer> list4 = new ArrayList<Integer>(
                Arrays.asList(1, 2, 4,1));
        String test = "Hello, World! aldkfj,!adf ";
        List<Integer> list5 = new ArrayList<Integer>(
                Arrays.asList(1, 4, 5, 6, 5, 5, 2));
        // System.out.println(ArrayListHW.allMultiples(list, 5));
        System.out.println(ArrayListHW.allStringsOfSize(list1, 3));
        // System.out.println(ArrayListHW.isPermutation(list3, list4));
        // System.out.println(ArrayListHW.stringToListOfWords(test));
        // System.out.println(list1);
        // ArrayListHW.removeAllInstances(list1, "eat");
        // System.out.println(list1);
        
    }

}
