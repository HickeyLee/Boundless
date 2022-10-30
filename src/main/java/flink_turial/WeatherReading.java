package flink_turial;

public class WeatherReading {
    private String status;
    private String count;
    private String info;
    private String infocode;
    private String forecasts;


    @Override
    public String toString() {
        return "SensorReading{" +
                "status='" + status + '\'' +
                ", count=" + count +
                ", info=" + info +
                ", infocode=" + infocode +
                ", forecasts=" + forecasts +
                '}';
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getCount() {
        return count;
    }
    public void setCount(String count) {
        this.count = count;
    }

    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }
    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public String getForecasts() {
        return forecasts;
    }
    public void setForecasts(String forecasts) {
        this.forecasts = forecasts;
    }

    public WeatherReading(String status, String count, String info, String infocode, String forecasts) {
        this.status = status;
        this.count = count;
        this.info = info;
        this.infocode = infocode;
        this.forecasts = forecasts;
    }

}
