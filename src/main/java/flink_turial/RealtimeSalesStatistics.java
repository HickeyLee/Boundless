package flink_turial;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class RealtimeSalesStatistics {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        tableEnv.executeSql("    CREATE TABLE source_user_behavior (\n" +
                        "            log_id string,\n" +
                        "            order_datetime string,\n" +

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
                        "    ) WITH (\n" +
                        "            'connector' = 'kafka', \n" +
                        "            'topic' = 'quickstart-events', "  +
                        "            'properties.bootstrap.servers' = '1.15.120.226:9092', "  +
                        "            'properties.group.id' = 'Flink-Group', "  +
                        "            'scan.startup.mode' = 'earliest-offset', "  +
                        "            'format' = 'json'"  +
                        "    )"
        );
        tableEnv.executeSql("select * from source_user_behavior").print();


        
    }

}







