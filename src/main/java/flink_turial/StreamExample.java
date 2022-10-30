package org.example;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;


/**
 * @Author: chulang
 * @DateTime: 2022/8/8 12:23
 * @Description: TODO
 **/

//测试通过，主要是版本的之间的依赖问题比较麻烦


public class StreamExample {

    public static void main(String[] args) throws Exception {
        // 0 初始化flink环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 1 kafka消费者配置信息
        KafkaSource<String> source = KafkaSource.<String>builder()
                .setBootstrapServers("1.15.120.226:9092")
                .setTopics("quickstart-events")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();


        // 2 创建kafka消费者
        DataStream<String> inputStream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");

        // 2. 转换成POJO, 注意java的写法，复习关于类的初始化


        // 3 消费者和flink流关联
        inputStream.print();

//        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
//        Table table2 = tableEnv.fromDataStream(inputStream);
//        table2.printSchema();


        // 4 执行
        env.execute();
    }

}
