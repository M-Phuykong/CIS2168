import java.util.*;
import java.math.*;

public class test{

    public static char mostCommonChar(String text){
        Map<Character, Integer> counts = new HashMap<>();
         (char c: text.toCharArray()){
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }
        int mostCount = 0;
        char mostLetter = 0;

        for (char c: counts.keySet()){
            int count = counts.get(c);
            if (count > mostCount){
                mostCount = count;
                mostLetter = c;
            }
        }

        return mostLetter;
    }




    public static void main(String[] args) {
        System.out.println(mostCommonChar("aabccd"));
    }



}