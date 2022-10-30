package org.example;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;


public class FlinkSQL_KS_HopWindow {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        tableEnv.executeSql("    CREATE TABLE source_user_behavior (\n" +
                        "            r_id int,\n" +
                        "            user_id string,\n" +
                        "            time_stamp bigint,\n" +
                        "            temperature float,\n" +
                        "            occur_dt TIMESTAMP(3),\n" +
                        "            send_dt TIMESTAMP(3),\n" +
                        "            WATERMARK FOR occur_dt AS occur_dt - INTERVAL '5' SECOND \n" +
                        "    ) WITH (\n" +
                        "            'connector' = 'kafka', \n" +
                        "            'topic' = 'quickstart-events-2', "  +
                        "            'properties.bootstrap.servers' = '1.15.120.226:9092', "  +
                        "            'properties.group.id' = 'testGroup', "  +
                        "            'scan.startup.mode' = 'latest-offset', "  +
                        "            'format' = 'json'"  +
                        "    )"
        );

//Example_1:Hop window
//        tableEnv.executeSql(
//                "select window_start, window_end, user_id, count(1) as cnt, sum(temperature) as tp,sum(r_id) as mark  " +
//                        "from table(" +
//                        "HOP(TABLE source_user_behavior, DESCRIPTOR(occur_dt), INTERVAL '1' MINUTES, INTERVAL '2' MINUTES) ) " +
//                        "group by window_start, window_end, user_id  "
//        ).print();

//Example_1:CUMULATE window,有一部分累计的功能，但是目前没有搞清楚，window_start和window_end的决定机制是什么
        tableEnv.executeSql(
                "select window_start, window_end, user_id, count(1) as cnt, sum(temperature) as tp,sum(r_id) as mark  " +
                        "from table(" +
                        "CUMULATE(TABLE source_user_behavior, DESCRIPTOR(occur_dt), INTERVAL '1' MINUTES, INTERVAL '3' MINUTES) ) " +
                        "group by window_start, window_end, user_id  "
        ).print();


    }
}
