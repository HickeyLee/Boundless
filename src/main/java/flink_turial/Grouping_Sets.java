package org.example;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class Grouping_Sets {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
//        tableEnv.executeSql("SELECT supplier_id, rating, COUNT(*) AS total\n" +
//                "FROM (VALUES\n" +
//                "    ('supplier1', 'product1', 4),\n" +
//                "    ('supplier1', 'product2', 3),\n" +
//                "    ('supplier2', 'product3', 3),\n" +
//                "    ('supplier2', 'product4', 4))\n" +
//                "AS Products(supplier_id, product_id, rating)\n" +
//                "GROUP BY GROUPING SETS ((supplier_id, rating), (supplier_id), ())").print();

        tableEnv.executeSql("SELECT supplier_id, product_id, sum(rating) AS total\n" +
                "FROM (VALUES\n" +
                "    ('supplier1', 'product1', 4),\n" +
                "    ('supplier1', 'product2', 3),\n" +
                "    ('supplier2', 'product1', 3),\n" +
                "    ('supplier2', 'product2', 4))\n" +
                "AS Products(supplier_id, product_id, rating)\n" +
                "GROUP BY GROUPING SETS ((supplier_id, product_id), (product_id) )").print();


    }

    }
