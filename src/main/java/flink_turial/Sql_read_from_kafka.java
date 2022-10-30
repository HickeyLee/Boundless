package org.example;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.util.Collector;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;


public class Sql_read_from_kafka {
    public static void main(String[] args) throws Exception {
//        EnvironmentSettings settings = EnvironmentSettings
//                .newInstance()
//                .inStreamingMode()
//                .build();
//        TableEnvironment tableEnv = TableEnvironment.create(settings);//是否是批流一体环境
//

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
//                "            'scan.startup.mode' = 'earliest-offset', "  +
                "            'scan.startup.mode' = 'latest-offset', "  +
                "            'format' = 'json'"  +
                "    )"
        );
//
//        tableEnv.executeSql("desc source_user_behavior").print();
//        tableEnv.executeSql("select * from source_user_behavior").print();
//
//


//        以下两个案例：案例一和案例二中，DESCRIPTOR中的时间描述不一致，案例一一直无法产生数据，但案例二可以生成数据，需要分析DESCRIPTOR参数的含义
//        案例一:有数据（INTERVAL参数为1min时），那么windows_start和windows_end是什么决定的，此时，occur_dt为系统时间-10s
//        tableEnv.executeSql(
//                "select window_start, window_end, SUM(temperature) as total_tp " +
//                        "from table(" +
//                        "TUMBLE(TABLE source_user_behavior, DESCRIPTOR(occur_dt), INTERVAL '1' MINUTES )) " +
//                        "group by window_start, window_end"
//        ).print();
//

//        案例二:occur_dt修改为系统时间减去100s,windows似乎改成最初有数据的时间，但是数据是有的（occur_dt顺序生成）
        tableEnv.executeSql(
                "select window_start, window_end, user_id, count(1) as cnt, sum(temperature) as tp,sum(r_id) as mark  " +
                        "from table(" +
                        "TUMBLE(TABLE source_user_behavior, DESCRIPTOR(occur_dt), INTERVAL '1' MINUTES )) " +
                        "group by window_start, window_end, user_id, temperature"
        ).print();

    }
    }



