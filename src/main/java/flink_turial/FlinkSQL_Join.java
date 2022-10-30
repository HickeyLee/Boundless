package flink_turial;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;


public class FlinkSQL_Join {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);


        tableEnv.executeSql("SELECT * \n" +
                "FROM (VALUES\n" +
                "    ('hickey', 'product_1', 4),\n" +
                "    ('gailmoon', 'product_1', 3),\n" +
                "    ('hickey', 'product_2', 3),\n" +
                "    ('gailmoon', 'product_1', 4),\n" +
                "    ('yuki', 'product_3', 4),\n" +
                "    ('gailmoon', 'product_2', 4))\n" +
                " AS Orders(buyer_id, product_id, num)\n"
        );


        tableEnv.executeSql("SELECT * \n" +
                "FROM (VALUES\n" +
                "    ('product_1', 'apple'),\n" +
                "    ('product_2', 'banana'),\n" +
                "    ('product_3', 'juice'))\n" +
                " AS Products(buyer_id, name)\n"
        );

        tableEnv.sqlQuery("select * from Products").printSchema();
//        tableEnv.executeSql("" +
//                "select a.buyer_id, " +
//                "a.num, " +
//                "b.name " +
//                "from Orders a " +
//                "left join Products b " +
//                "on a.buyer_id = b.buyer_id"
//                ).print();


    }
    }




