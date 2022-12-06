package RealTimeDW;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class RealtimeSalesStatistics {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        tableEnv.executeSql("    CREATE TABLE source_user_behavior (\n" +
                        "            log_id string,\n" +
                        "            order_datetime float,\n" +
                        "            order_datetime_stp as TO_TIMESTAMP_LTZ(floor(order_datetime * 1000), 3) ,\n" +

                        "            user_id string,\n" +
                        "            user_name string,\n" +
                        "            user_location string,\n" +
                        "            sex string,\n" +
                        "            age string,\n" +

                        "            book_id string,\n" +
                        "            book_name string,\n" +
                        "            publish_dt string,\n" +
                        "            mk_price string,\n" +
                        "            category string,\n" +

                        "            order_amount string,\n" +
                        "            order_price string\n" +
//                        "            WATERMARK FOR dt AS dt - INTERVAL '5' SECOND" +

                "    ) WITH (\n" +
                        "            'connector' = 'kafka', \n" +
                        "            'topic' = 'quickstart-events', "  +
                        "            'properties.bootstrap.servers' = '1.15.120.226:9092', "  +
                        "            'properties.group.id' = 'Flink-Group', "  +
                        "            'scan.startup.mode' = 'earliest-offset', "  +
                        "            'format' = 'json'"  +
                        "    )"
        );




//        Table Result = tableEnv.sqlQuery("select * ,user_id as user_id1 from source_user_behavior");
//        这里声明的Result怎么才能在SQL中使用
//        Method 1:直接通过table api进行操作
//        Result.execute().print();
//        注意，这种表只有进行如下的注册操作之后，才可以在sqlQuery中使用，Table的含义其实类似于视图，而不是表
//        register the Table projTable as table "projectedTable"
//        tableEnv.createTemporaryView("projectedTable", projTable);

//        混用table api和sql 的注意事项
//        可以在 SQL 查询返回的 Table 对象上定义 Table API 查询。
//        在 TableEnvironment 中注册的结果表可以在 SQL 查询的 FROM 子句中引用，通过这种方法就可以在 Table API 查询的结果上定义 SQL 查询。




//        Table Result = tableEnv.sqlQuery("select " +
//                "log_id, " +
//                "order_datetime, "+
//                "TO_TIMESTAMP_LTZ(floor(order_datetime * 1000), 3) as form_order_datetime," +
//                "user_id, " +
//                "order_amount, " +
//                "WATERMARK FOR occur_dt AS occur_dt - INTERVAL '5' SECOND \n" +
//                "from source_user_behavior");
//        tableEnv.sqlQuery("select * from "+ Result + " ").execute().print();
//        tableEnv.sqlQuery("select * from tt ").execute().print();


        tableEnv.executeSql("create table Orders_with_watermark (" +
                "WATERMARK FOR order_datetime_stp AS order_datetime_stp - INTERVAL '5' SECOND" +
                ") " +
                "LIKE source_user_behavior(" +
                "    INCLUDING ALL\n" +
                "    INCLUDING GENERATED " +
                "    GENERATED " +
                ")");
        tableEnv.executeSql("select * from Orders_with_watermark").print();

//        tableEnv.executeSql("select * from source_user_behavior").print();

//        tableEnv.executeSql("create table source_user_behavior_water(" +
//                "WATERMARK FOR TO_TIMESTAMP_LTZ(floor(order_datetime * 1000), 3) AS TO_TIMESTAMP_LTZ(floor(order_datetime * 1000), 3) - INTERVAL '5' SECOND " +
//                ")" +
//                "with (" +
//                "'scan.startup.mode' = 'latest-offset'" +
//                ") like source_user_behavior");
//
//        tableEnv.executeSql("select * from source_user_behavior_water").print();
//

        
    }

}







