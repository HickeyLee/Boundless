package flink_turial;
/**
 * @author : hickey email: hiceky_lee@outlook.com
 * @date : 2323/2/3 5:47 AM
 * 通过datastream模型，读取txt文件，并量datastream转化为table，并测试常用函数
 */
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;


public class EG_TableAPI_03_Common_Usage {
    public static void main(String[] args) throws Exception{
        // 1. 创建环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);


        // 1. 读取数据
        DataStreamSource<String> inputStream = env.readTextFile("E:/Boundless/src/main/data/sensor.txt");

        // 2. 转换成POJO
        DataStream<SensorReading> dataStream = inputStream.map(line -> {
            String[] fields = line.split(",");
            return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
        });

//        inputTable.printSchema();
//        tableEnv.toAppendStream(inputTable, Row.class).print();


        // 4. 基于流创建一张表
        Table dataTable = tableEnv.fromDataStream(dataStream);
//        dataTable.execute().print();

        // 3. 查询转换
        // 3.1 Table API
        // 简单转换
        Table resultTable = dataTable.select("id, temperature")
                .filter("id === 'sensor_6'");
//        resultTable.execute().print();
//
//        // 聚合统计
        Table aggTable = dataTable.groupBy("id")
                .select("id, id.count as count, temperature.avg as avgTemp");
//        aggTable.execute().print();

//        // 3.2 SQL
        tableEnv.createTemporaryView("dataTable", dataTable);
        tableEnv.sqlQuery("select id, temperature from dataTable where id = 'senosr_6'");
        Table sqlAggTable = tableEnv.sqlQuery("select id, count(id) as cnt, avg(temperature) as avgTemp from dataTable group by id");
        sqlAggTable.execute().print();

//        // 打印输出
        tableEnv.toAppendStream(resultTable, Row.class).print("result");
        tableEnv.toRetractStream(aggTable, Row.class).print("agg");
        tableEnv.toRetractStream(sqlAggTable, Row.class).print("sqlagg");

        env.execute();
    }
}