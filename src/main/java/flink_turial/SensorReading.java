package flink_turial;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/31 5:14 PM
 */

public class SensorReading {
    // 属性：id，时间戳，温度值
    private String id;
    private Long timestamp;
    private Double temperature;

//    如果注释，则schema定义报错，待分析
    public SensorReading() {
    }

    public SensorReading(String id, Long timestamp, Double temperature) {
        this.id = id;
        this.timestamp = timestamp;
        this.temperature = temperature;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "SensorReading{" +
                "id='" + id + '\'' +

                
                ", timestamp=" + timestamp +
                ", temperature=" + temperature +
                '}';
    }
}