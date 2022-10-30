package flink_turial;
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

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/29 10:46 PM
 * 批处理 wordcount
 */
public class Sql_read_from_txt {

    public static void main(String[] args) throws Exception {

//        创建TableEnvironment方法一
        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()
                //.inBatchMode()
                .build();
        TableEnvironment tableEnv = TableEnvironment.create(settings);//是否是批流一体环境

//        创建TableEnvironment方法二（可以研究与上述方法的区别）
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);


        tableEnv.executeSql("    CREATE TABLE source_user_behavior (\n" +
                "            r_id int,\n" +
                "            user_id string,\n" +
                "            time_stamp bigint,\n" +
                "            temperature float,\n" +
                "            occur_dt TIMESTAMP(3),\n" +
                "            WATERMARK FOR occur_dt AS occur_dt - INTERVAL '10' SECOND \n" +
                "    ) WITH (\n" +
                "            'connector' = 'filesystem', \n" +
                "            'path' = 'E:/FlinkStudyTest/src/main/java/data/sensor_1.csv', \n" +
                "            'format' = 'csv'\n" +
                "    )"
        );

        tableEnv.executeSql("desc source_user_behavior").print();
        tableEnv.executeSql("select * from source_user_behavior").print();

//        sink.parallelism：用于设置任务的并行度，默认情况下，并行度有框架绝对，和链在一起是上游operator一致（可能是根据进程数决定）
        tableEnv.executeSql("    CREATE TABLE sink_user_behavior (\n" +
                "            r_id int,\n" +
                "            user_id string,\n" +
                "            time_stamp bigint,\n" +
                "            temperature float,\n" +
                "            occur_dt TIMESTAMP(3)\n" +
                "    ) WITH (\n" +
                "            'connector' = 'print',\n " +
                "            'sink.parallelism' = '1' " +
                "    )"
        );

        tableEnv.executeSql("    CREATE TABLE sink_user_behavior_distinct (\n" +
                "            user_id string,\n" +
                "            window_start TIMESTAMP(3),\n" +
                "            window_end TIMESTAMP(3),\n" +
                "            temperature float\n" +
                "    ) WITH (\n" +
                "            'connector' = 'print'\n " +
                "    )"
        );

//        tableEnv.executeSql("insert into sink_user_behavior_distinct " +
//                "select user_id, window_start, window_end, sum(temperature) as temperature " +
//                "from TABLE (" +
//                        "TUMBLE(TABLE sink_user_behavior, DESCRIPTOR(occur_dt), INTERVAL '1' MINUTES)" +
//                        ")"
//                );

        tableEnv.executeSql(
                "select user_id, window_start, window_end, sum(temperature) as temperature " +
                "from TABLE (" +
                "TUMBLE(TABLE source_user_behavior, DESCRIPTOR(occur_dt), INTERVAL '1' MINUTES)" +
                ") group by user_id, window_start, window_end "
        ).print();

    }

}


