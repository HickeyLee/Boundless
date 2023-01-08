package flink_turial;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class EG_TableAPI_04_HopWindow {
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
                "            'topic' = 'quickstart-events', " +
                "            'properties.bootstrap.servers' = '1.15.120.226:9092', " +
                "            'properties.group.id' = 'Flink-Group', " +
                "            'scan.startup.mode' = 'earliest-offset', " +
                "            'format' = 'json'" +
                "    )"
        );

//        tableEnv.executeSql("select * from source_user_behavior").print();


////Example_1:CUMULATE window,有一部分累计的功能，但是目前没有搞清楚，window_start和window_end的决定机制是什么
        tableEnv.executeSql(
                "select window_start, window_end, user_name, " +
                        "count(1) as cnt, " +
                        "sum(cast(order_amount as int)) as tp, " +
                        "sum(cast(order_price as decimal(10,2))) as mark " +
                        "from table(" +
                        "CUMULATE(TABLE source_user_behavior, DESCRIPTOR(order_datetime_stp), INTERVAL '1' MINUTES, INTERVAL '3' MINUTES) ) " +
                        "group by window_start, window_end, user_name"
        ).print();
    }
}
