package flink_turial;

import com.ververica.cdc.debezium.DebeziumSourceFunction;
import io.debezium.connector.postgresql.PostgresConnector;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;
import com.ververica.cdc.connectors.postgres.PostgreSQLSource;
import java.util.Properties;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.CheckpointingMode;
import com.ververica.cdc.connectors.postgres.table.PostgreSQLTableFactory;
import com.ververica.cdc.connectors.mysql.source.MySqlSource;

//https://ververica.github.io/flink-cdc-connectors/master/content/connectors/postgres-cdc.html

public class CDC_DataStreamAPI_PG {
    public static void main(String[] args) throws Exception{
        //设置flink表环境变量

//        Properties props = new Properties();
//        props.setProperty("debezium.snapshot.mode", "never");
//
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        env.setParallelism(1);
//
//        //1.1 开启Checkpoint
//        env.enableCheckpointing(10);
//        env.getCheckpointConfig().setCheckpointTimeout(10);
//        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
//        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
//
//        SourceFunction<String> sourceFunction = PostgreSQLSource.<String>builder()
//                .hostname("127.0.0.1")
//                .port(5432)
//                .database("pgdbspace") // monitor postgres database
//                .schemaList("public")  // monitor inventory schema
//                .tableList("pgdbspace.public.stream_source1") // monitor products table
//                .username("postgres")
//                .password("hickey")
//                .deserializer(new JsonDebeziumDeserializationSchema()) // converts SourceRecord to JSON String
//                .decodingPluginName("pgoutput")
//                .slotName("nerer")
//                .build();
//
//        DataStreamSource<String> dataStreamSource = env.addSource(sourceFunction);
//        dataStreamSource.print();
//        System.out.println("-----1");
//
//        env.execute("CDC_DataStreamAPI_PG");
//        System.out.println("-----2");


        MySqlSource<String> mySqlSource = MySqlSource.<String>builder()
                .hostname("1.15.120.226")
                .port(3306)
                .databaseList("bigdata") // set captured database, If you need to synchronize the whole database, Please set tableList to ".*".
                .tableList("bigdata.table_test") // set captured table
                .username("root")
                .password("CJ&hickey00")
                .deserializer(new JsonDebeziumDeserializationSchema()) // converts SourceRecord to JSON String
                .build();

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // enable checkpoint
        env.enableCheckpointing(10);

        env
                .fromSource(mySqlSource, WatermarkStrategy.noWatermarks(), "MySQL Source")
                // set 4 parallel source tasks
                .setParallelism(1)
                .print().setParallelism(1); // use parallelism 1 for sink to keep message ordering

        env.execute("Print MySQL Snapshot + Binlog");

    }
}


