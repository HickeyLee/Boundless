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
                        "            order_price string,\n" +
                        "            process_ts as PROCTIME() , " +
                        "            WATERMARK FOR order_datetime_stp AS order_datetime_stp - INTERVAL '10' SECOND" +
                        "    ) WITH (\n" +
                        "            'connector' = 'kafka', \n" +
                        "            'topic' = 'quickstart-events', "  +
                        "            'properties.bootstrap.servers' = '1.15.120.226:9092', "  +
                        "            'properties.group.id' = 'Flink-Group', "  +
                        "            'scan.startup.mode' = 'earliest-offset', "  +
                        "            'format' = 'json'"  +
                        "    )"
        );

//        Task 1:每隔2min生成最近2min购买次数最多的3本图书
//        注意，这里必须结合where条件使用，当前模式下，如果少一层嵌套会报错，原因待分析： The window can only be ordered in ASCENDING mode
//        tableEnv.executeSql("select * from (select " +
//                "*, ROW_NUMBER() OVER (PARTITION BY window_start, window_end ORDER BY cnt DESC) as r " +
//                "from(select " +
//                "window_start, " +
//                "window_end, " +
//                "book_id, " +
//                "book_name, " +
//                "count(1) as cnt " +
//                "FROM TABLE(" +
//                "TUMBLE(TABLE source_user_behavior, DESCRIPTOR(order_datetime_stp), INTERVAL '2' MINUTES) ) " +
//                "group by book_id, book_name, window_start ,window_end ) t " +
//                ") cc where r<3").print();

//          task2:每隔2min生成当日累计销售金额最多的3本图书，注意限定词，当日累计；CUMULATE作为计数窗口，可以实现累计计数效果
//        tableEnv.executeSql("select * from (select " +
//                "*, ROW_NUMBER() OVER (PARTITION BY window_start, window_end ORDER BY cnt DESC) as r " +
//                "from(select " +
//                "window_start, " +
//                "window_end, " +
//                "book_id, " +
//                "book_name, " +
//                "sum(cast(mk_price as decimal(10,2))) as cnt " +
//                "FROM TABLE(" +
//                "CUMULATE(TABLE source_user_behavior, DESCRIPTOR(order_datetime_stp), INTERVAL '2' MINUTES, INTERVAL '10' MINUTES) ) " +
//                "group by book_id, book_name, window_start ,window_end ) t " +
//                ") cc where r<=3").print();


//          task3:每个1min输出最近5min购买金额最多的5个人
//        tableEnv.executeSql("select * from (select " +
//                "*, ROW_NUMBER() OVER (PARTITION BY window_start, window_end ORDER BY cnt DESC) as r " +
//                "from(select " +
//                "window_start, " +
//                "window_end, " +
//                "user_id, " +
//                "user_name, " +
//                "sum(cast(mk_price as decimal(10,2))) as cnt " +
//                "FROM TABLE(" +
//                "CUMULATE(TABLE source_user_behavior, DESCRIPTOR(order_datetime_stp), INTERVAL '1' MINUTES, INTERVAL '5' MINUTES) ) " +
//                "group by user_id, user_name, window_start ,window_end ) t " +
//                ") cc where r<=5").print();
        tableEnv.executeSql("select * from source_user_behavior").print();


    }

}







