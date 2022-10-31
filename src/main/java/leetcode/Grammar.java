package leetcode;
import com.esotericsoftware.minlog.Log;
import leetcode.MaxValueinList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Grammar {
    public static void main(String[] args) throws Exception {
//        List arrayList = new ArrayList<String>();
//        即为泛型，帮助在编译的时候报错
        List<String> arrayList = new ArrayList<String>();
        arrayList.add("aaaa");
        arrayList.add("bbb");
        arrayList.stream();

        for (int i = 0; i < arrayList.size(); i++) {
            String item = (String) arrayList.get(i);
            System.out.println("item = " + item);
        }
    }
}

class ContainsDuplicate {
    public static boolean containsDuplicate(int[] nums) {
//        stream类型，是干什么的

        return IntStream.of(nums).distinct().count() != nums.length;
    }
}



