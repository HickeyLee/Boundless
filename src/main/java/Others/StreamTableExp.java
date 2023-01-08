package Others.flink_turial;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.api.TableDescriptor;

import static org.apache.flink.table.api.Expressions.*;

/**
 * @Author: chulang
 * @DateTime: 2022/8/8 12:23
 * @Description: TODO
 **/

//测试通过，主要是版本的之间的依赖问题比较麻烦


public class StreamTableExp {

    public static void main(String[] args) throws Exception {
        // 0 初始化flink环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

//        // 1 kafka消费者配置信息
//        KafkaSource<String> source = KafkaSource.<String>builder()
//                .setBootstrapServers("1.15.120.226:9092")
//                .setTopics("easy-quickstart-events")
//                .setStartingOffsets(OffsetsInitializer.earliest())
//                .setValueOnlyDeserializer(new SimpleStringSchema())
//                .build();
//


//        DataStream<String> inputStream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");
//        inputStream.print();
//        env.execute();


//        tableEnv.executeSql('''''')


        final Schema schema = Schema.newBuilder()
                .column("city", DataTypes.STRING())
                .column("reporttime", DataTypes.STRING())
                .column("date", DataTypes.STRING())
                .column("daytemp", DataTypes.INT())
                .column("nighttemp", DataTypes.INT())
                .column("daywind", DataTypes.STRING())
                .column("nightwind", DataTypes.STRING())
                .build();


        // 2.连接kafka，读取数据
        TableDescriptor tableScriptor = TableDescriptor.forConnector("kafka")
                .schema(schema)
                .option("topic", "easy-quickstart-events-1")
                .option("properties.bootstrap.servers", "1.15.120.226:9092")
                .option("properties.group.id", "dev")
                .option("scan.startup.mode", "latest-offset")
                .option("value.format" ,"json")
                .build();

        // 测试通过，之前的问题主要是kafka的写入问题，应该直接json序列化，而不是转成字符串后序列化
        tableEnv.createTemporaryTable("SourceTable", tableScriptor);
        Table apiTable = tableEnv.from("SourceTable");
//        apiTable.execute().print();



        Table counts = apiTable
                .groupBy($("daywind"))
                .select($("daywind"), $("daytemp").count().as("cnt"));
        counts.execute().print();

//        Table avg = apiTable
//                .groupBy($("daywind"))
//                .select($("daywind"), $("daytemp").avg().as("avg"));
//        avg.execute().print();



        // 4 执行
//        table.execute();
//        table.printSchema();
    }
}






























//                        DataTypes.FIELD("city", DataTypes.STRING())
//                        DataTypes.STRUCTURED(
//                                DataTypes.FIELD("city", DataTypes.STRING()),
//                                DataTypes.FIELD("adcode", DataTypes.STRING()),
//                                DataTypes.FIELD("province", DataTypes.STRING()),
//                                DataTypes.FIELD("reporttime", DataTypes.STRING())
//                                DataTypes.FIELD("casts", DataTypes.ARRAY(
//                                        DataTypes.STRUCTURED(
//                                                DataTypes.FIELD("date", DataTypes.STRING()),
//                                                DataTypes.FIELD("week", DataTypes.STRING()),
//                                                DataTypes.FIELD("dayweather", DataTypes.STRING()),
//                                                DataTypes.FIELD("daytemp", DataTypes.STRING()),
//                                                DataTypes.FIELD("nighttemp", DataTypes.STRING()),
//                                                DataTypes.FIELD("daywind", DataTypes.STRING()),
//                                                DataTypes.FIELD("nightwind", DataTypes.STRING()),
//                                                DataTypes.FIELD("daypower", DataTypes.STRING()),
//                                                DataTypes.FIELD("nightpower", DataTypes.STRING())
//                                        )
//                                )
//                                )
//                        )