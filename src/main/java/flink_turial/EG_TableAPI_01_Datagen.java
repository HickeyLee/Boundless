package flink_turial;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class EG_TableAPI_01_Datagen {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        tableEnv.executeSql("    CREATE TABLE source_user_behavior (\n" +
                "f_sequence INT," +
                "f_random INT," +
                "f_random_str STRING," +
                "occur_ts AS localtimestamp , " +
                "process_ts as PROCTIME() , " +
                "WATERMARK FOR occur_ts AS occur_ts" +
                ") WITH (" +
                "'connector' = 'datagen'," +
                "'rows-per-second'='1'," +
                "'fields.f_random_str.length'='2' " +
                ")"
        );
//        tableEnv.executeSql("select * from source_user_behavior").print();

//        sink.parallelism：用于设置任务的并行度，默认情况下，并行度有框架绝对，和链在一起是上游operator一致（可能是根据进程数决定）
//        tableEnv.executeSql("    CREATE TABLE sink_user_behavior (\n" +
//                "            r_id int,\n" +
//                "            user_id string,\n" +
//                "            time_stamp BIGINT,\n" +
//                "            temperature float\n" +
//                "    ) WITH (\n" +
//                "            'connector' = 'print',\n " +
//                "            'sink.parallelism' = '1' " +
//                "    )"
//        );


//        通过datagen数据源，可以正常消费数据
        tableEnv.executeSql("select f_sequence, f_random, occur_ts, process_ts from source_user_behavior").print();
//        tableEnv.executeSql(
//                "select window_start, window_end, f_random_str, count(1) ss" +
//                        "" +
//                        "" +
//                        "" +
//                        "from table(" +
//                        "TUMBLE(TABLE source_user_behavior, DESCRIPTOR(process_ts), INTERVAL '1' MINUTES )) " +
//                        "group by window_start, window_end, f_random_str"
//        ).print();
//      实验表明，当数据生成结束后，滚动窗口的数据才会一次性生成，为什么？滑动窗口又是怎么样的机制？

    }
}
