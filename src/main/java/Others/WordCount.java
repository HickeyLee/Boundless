package Others;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/29 10:46 PM
 * 批处理 wordcount
 */



public class WordCount {
    public static void main(String[] args) throws Exception{
        // 创建执行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // 从文件中读取数据
        String inputPath = "src/main/java/data/hello.txt";
        System.out.println(System.getProperty("user.dir"));
        DataSet<String> inputDataSet = env.readTextFile(inputPath);

        // 对数据进行处理，按空格分词展开，转换成(word,1)二元组进行统计
        DataSet<Tuple2<String,Integer>> wordCountDataSet = inputDataSet.flatMap(new MyFlagMapper()).groupBy(0).sum(1);

        // 打印输出
        wordCountDataSet.print();
    }






    public static class MyFlagMapper implements FlatMapFunction<String,Tuple2<String,Integer>>{
        public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
            // 按照空格分词
            String [] words = s.split(" ");
            for (String word:words){
                collector.collect(new Tuple2<String, Integer>(word,1));
            }
        }
    }

}