package Others;

import flink_turial.SensorReading;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/2/3 5:47 AM
 */


public class TableTest1_Example {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);

        // 1. 读取数据
        DataStream<String> inputStream = env.readTextFile("E:/Boundless/src/main/data/sensor.txt");
//        inputStream.print();
//        env.execute();

        // 2. 转换成POJO, 注意java的写法，复习关于类的初始化
        DataStream<SensorReading> dataStream = inputStream.map(line -> {
            String[] fields = line.split(",");
            return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
        });

        // 3. 创建表环境
//        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
//
//        // 4. 基于流创建一张表
//        Table dataTable = tableEnv.fromDataStream(dataStream);
//        dataTable.execute().print();

//        // 5. 调用table API进行转换操作
//        Table resultTable = dataTable.select("id, temperature")
//                .where("id = 'sensor_1'");
////        Table res = dataTable.select()
//
//        // 6. 执行SQL
//        tableEnv.createTemporaryView("sensor", dataTable);
//        String sql = "select * from sensor where id = 'sensor_1'";
////        String sql = "SELECT * FROM TABLE(TUMBLE(TABLE dataTable, DESCRIPTOR(timestamp), INTERVAL '2' MINUTES))";
//        Table resultSqlTable = tableEnv.sqlQuery(sql);
//
////        tableEnv.toAppendStream(resultTable, Row.class).print("result");
//        tableEnv.toAppendStream(resultSqlTable, Row.class).print("sql");
//
//        env.execute();
    }
}


