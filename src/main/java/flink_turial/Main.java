package flink_turial;
//import org.example.Datagen;
//import org.example.CDC_DataStreamAPI_PG;
import flink_turial.CDC_TableAPI_Mysql;

public class Main {
    public static void main(String[] args) throws Exception
    {
        System.out.println("Hello Flink");

//        TableTest5_TimeAndWindow flink_test = new TableTest5_TimeAndWindow();
//        Datagen flink_test = new Datagen();
//        String[] str = {"***","---","***"};
//        flink_test.main(str);

        CDC_TableAPI_Mysql flink_test = new CDC_TableAPI_Mysql();
        String[] str = {"***","---","***"};
        flink_test.main(str);

        System.out.println("Deep in Flink");
    }
}


