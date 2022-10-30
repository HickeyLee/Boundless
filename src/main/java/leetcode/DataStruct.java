package leetcode;

import scala.runtime.Statics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;

//
//public class DataStruct {
////    List<Integer> array = new ArrayList<>();
//    int[] Array = {2, 3, 1, 0, 2};
//
//
//    public static void main(String[] args){
//        List<Integer> array = new ArrayList<>();
//
//// 向尾部添加元素
//        array.add(2);
//        array.add(3);
//        array.add(1);
//        array.add(0);
//        array.add(2);
//
//
//        System.out.println("hello world");
////        String[] Array = {"2", "3"};
//        System.out.println(array);
//    }

//}



//public class DataStruct {
//    public static String replaceSpace(String s) {
//        StringBuilder res = new StringBuilder();
//        for(Character c : s.toCharArray())
//        {
//            if(c == ' ') res.append("%20");
//            else res.append(c);
//        }
//        return res.toString();
//    }
//
//    public static void main(String[] args){
//        String te = "ds dsf d";
//        String f = replaceSpace(te);
//        System.out.println(f);
//
//    }
//}
//




class DataStruct {
     public static class ListNode {
     int val;
     ListNode next;
     ListNode(int x) { val = x; }
  }

    public static int[] reversePrint(ListNode head) {
        LinkedList<Integer> stack = new LinkedList<Integer>();
        while(head != null) {
            stack.addLast(head.val);
            head = head.next;
        }
        int[] res = new int[stack.size()];
        for(int i = 0; i < res.length; i++)
            res[i] = stack.removeLast();
        return res;
    }


    public static void main(String[] args){
//    int[] te = [1,2,3,4];
//    String f = reversePrint([1,2,3,4]);
//    ListNode ns = new ListNode(3);
//    ns.val = 10;
//    ns.val = 12;
////    ns.next = 3;
//    System.out.println(ns.next);
//    System.out.println(reversePrint());

    int[] ns = { 28, 12, 89, 73, 65, 18, 96, 50, 8, 36 };
    Arrays.sort(ns);
    System.out.println(Arrays.toString(ns));


}

}
