package Others;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.api.Table;
//测试通过




public class PgCDC {
    public static void main(String[] args) throws Exception{
        //设置flink表环境变量

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);


        // 2.DDL方式建表
        tableEnv.executeSql("CREATE TABLE pgsql_source ( " +
                " age int, " +
                " name STRING " +
                ") WITH ( " +
                " 'connector' = 'postgres-cdc', " + // 连接器的名称
                " 'hostname' = '127.0.0.1', " +
                " 'port' = '5432', " +
                " 'username' = 'postgres', " +
                " 'password' = 'hickey', " +
                " 'database-name' = 'pgdbspace', " +
                " 'schema-name' = 'public', " +
                " 'table-name' = 'pg_source', " +
                " 'debezium.snapshot.mode' = 'never', " +
                " 'decoding.plugin.name' = 'pgoutput' " +
                ")");

        //拼接sinkDLL
//        tableEnv.executeSql("CREATE TABLE pgsql_source ( " +
//                " age int, " +
//                " name STRING " +
//                ") WITH ( " +
//                " 'connector' = 'postgres-cdc', " + // 连接器的名称
//                " 'hostname' = '127.0.0.1', " +
//                " 'port' = '5432', " +
//                " 'username' = 'postgres', " +
//                " 'password' = 'hickey', " +
//                " 'database-name' = 'pgdbspace', " +
//                " 'schema-name' = 'public', " +
//                " 'table-name' = 'pg_table1', " +
//                " 'debezium.snapshot.mode' = 'never', " +
//                " 'decoding.plugin.name' = 'pgoutput' " +
//                ")");

//        String sinkDDL =
//                "CREATE TABLE print_sink (\n" +
//                        " age int,\n" +
//                        " name STRING" +
//                        "\n" +
//                        ") WITH (\n" +
//                        " 'connector' = 'print',\n" +
//                        " 'table-name' = 'test'\n" +
//                        ")";

//
//        String transformSQL =
//                "INSERT INTO print_sink " +
//                        "SELECT age,name " +
//                        "FROM pgsql_source";

        //执行source表ddl
//        System.out.println("Deep in Flink");

        Table table = tableEnv.sqlQuery("select * from pgsql_source");
        table.execute().print();


//        tableEnv.executeSql(sourceDDL);
//        TableResult tableResult = tableEnv.executeSql(sourceDDL);
//        tableResult.print();



        //执行sink表ddl
//        tableEnv.executeSql(sinkDDL);
////        //执行逻辑sql语句
//        TableResult tableResult = tableEnv.executeSql(transformSQL);
//
//        //控制塔输出
//        tableResult.print();


//        SourceFunction<String> sourceFunction = PostgreSQLSource.<String>builder()
//                .hostname("localhost")
//                .port(5432)
//                .database("pgdbspace") // monitor postgres database
//                .schemaList("public")  // monitor inventory schema
//                .tableList("public.pg_table1") // monitor products table
//                .username("postgres")
//                .password("hickey")
//                .decodingPluginName("pgoutput")
//                .deserializer(new JsonDebeziumDeserializationSchema()) // converts SourceRecord to JSON String
//                .build();


//
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//
//        env.addSource(sourceFunction)
//           .print().setParallelism(1); // use parallelism 1 for sink to keep message ordering
//
//        env.execute();

    }
}

//http://t.zoukankan.com/xiongmozhou-p-14817641.html


