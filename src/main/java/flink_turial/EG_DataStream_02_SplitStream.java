package flink_turial;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class EG_DataStream_02_SplitStream {

    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 1. 读取数据
        DataStreamSource<String> inputStream = env.readTextFile("E:/Boundless/src/main/data/sensor.txt");

        // 2. 转换成POJO
        DataStream<SensorReading> dataStream = inputStream.map(line -> {
            String[] fields = line.split(",");
            return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
        });

//        DataStream<SensorReading> dataStream1 = dataStream.filter();

        DataStream<SensorReading> dataStream1 = dataStream.filter(new FilterFunction<SensorReading>() {
            @Override
            public boolean filter(SensorReading value) throws Exception {
                return value.getId().equals("taojinsha");

            }
        });
        dataStream1.print();
        // 1. 分流，按照温度值30度为界分为两条流
//        SplitStream<SensorReading> splitStream = dataStream.split(new OutputSelector<SensorReading>() {
//            @Override
//            public Iterable<String> select(SensorReading value) {
//                return (value.getTemperature() > 30) ? Collections.singletonList("high") : Collections.singletonList("low");
//            }
//        });

//        DataStream<SensorReading> highTempStream = splitStream.select("high");
//        DataStream<SensorReading> lowTempStream = splitStream.select("low");
//        DataStream<SensorReading> allTempStream = splitStream.select("high", "low");


        env.execute();
    }

}
