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
                        "            age1 as cast(age as int) + 1 ,\n" +

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

        Table tt = tableEnv.sqlQuery("select * from source_user_behavior");
        tt.printSchema();
//        tableEnv.executeSql("create table Orders_with_watermark (" +
//                "WATERMARK FOR order_datetime_stp AS order_datetime_stp - INTERVAL '5' SECOND" +
//                ") " +
//                "LIKE source_user_behavior(" +
//                "    EXCLUDING ALL\n" +
//                "    INCLUDING GENERATED " +
//                ")");

        tableEnv.executeSql("create table Orders_with_watermark (" +
                "WATERMARK FOR order_datetime_stp AS order_datetime_stp - INTERVAL '5' SECOND" +
                ") " +
                "LIKE source_user_behavior(" +
                "    EXCLUDING ALL\n" +
                "    INCLUDING GENERATED " +
                ")").getResolvedSchema();
//        tableEnv.executeSql("select * from Orders_with_watermark").print();

//        tableEnv.executeSql("select * from source_user_behavior").print();



        
    }

}







