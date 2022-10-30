package leetcode;
import leetcode.MaxValueinList;

public class Main {
    public static void main(String[] args) throws Exception{
        MaxValueinList d = new MaxValueinList();
        int[] num = {1,22,11,5,8,15,6,7,34,9};
        int g = MaxValueinList.findKthLargest(num, 5);
        System.out.println(g);

    }
}


