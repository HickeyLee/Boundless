package flink_turial;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;


public class CreateStatements {


    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);


//      方法1：通过sql创建
        tableEnv.executeSql("    CREATE TABLE source_user_behavior (\n" +
                "            log_id string,\n" +
                "            order_datetime float,\n" +
                "            dt as TO_TIMESTAMP_LTZ(floor(order_datetime * 1000), 3) ,\n" +

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
                "            'topic' = 'quickstart-events', " +
                "            'properties.bootstrap.servers' = '1.15.120.226:9092', " +
                "            'properties.group.id' = 'Flink-Group', " +
                "            'scan.startup.mode' = 'earliest-offset', " +
                "            'format' = 'json'" +
                "    )"
        );


//        Table Result = tableEnv.sqlQuery("select * ,user_id as user_id1 from source_user_behavior");
//        这里声明的Result怎么才能在SQL中使用
//        方法2:直接通过table api进行操作
//        Result.execute().print();
//        注意，这种表只有进行如下的注册操作之后，才可以在sqlQuery中使用，Table的含义其实类似于视图，而不是表
//        register the Table projTable as table "projectedTable"
//        tableEnv.createTemporaryView("projectedTable", projTable);

//        混用table api和sql 的注意事项
//        可以在 SQL 查询返回的 Table 对象上定义 Table API 查询。
//        在 TableEnvironment 中注册的结果表可以在 SQL 查询的 FROM 子句中引用，通过这种方法就可以在 Table API 查询的结果上定义 SQL 查询。


//      创建水位线的方法1：通过先创建衍生列，再创建水位线去操作
        tableEnv.executeSql("    CREATE TABLE source_user_behavior (\n" +
                "            log_id string,\n" +
                "            order_datetime float,\n" +
                "            dt as TO_TIMESTAMP_LTZ(floor(order_datetime * 1000), 3) ,\n" +
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
                "            order_price string,\n" +
                "            WATERMARK FOR dt AS dt - INTERVAL '5' SECOND" +

                "    ) WITH (\n" +
                "            'connector' = 'kafka', \n" +
                "            'topic' = 'quickstart-events', " +
                "            'properties.bootstrap.servers' = '1.15.120.226:9092', " +
                "            'properties.group.id' = 'Flink-Group', " +
                "            'scan.startup.mode' = 'earliest-offset', " +
                "            'format' = 'json'" +
                "    )"
        );


//        创建水位线的方法2:
//        tableEnv.executeSql("create table Orders_with_watermark (" +
//                "mk_price/2 t1" +
//                ") WITH  (" +
//                "'scan.startup.mode' = 'earliest-offset'" +
//                ")" +
//                "LIKE  source_user_behavior");


//        这里还需要进一步介绍关于physical column列和 virtual Columns（for example Computed Columns）的区别，需要确认like是否支持计算列，可能因为计算列未存储实际元数据，导致无法在like子句中使用
//        A computed column evaluates an expression that can reference other columns declared in the same table. Both physical columns and metadata columns can be accessed. The column itself is not physically stored within the table. The column’s data type is derived automatically from the given expression and does not have to be declared manually.
//        The planner will transform computed columns into a regular projection after the source. For optimization or watermark strategy push down, the evaluation might be spread across operators, performed multiple times, or skipped if not needed for the given query.    }
//

    }
}
