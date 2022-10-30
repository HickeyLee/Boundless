//package org.example;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.table.api.*;
////import org.apache.flink.connector.datagen.table.DataGenConnectorOptions;
//import org.apache.flink.types.Row;
////import org.apache.flink.connector.datagen.table.DataGenConnectorOptions;
//
//
////note
////1、Table API和SQL接口不互通，不能共享变量
//
////SQL查询可以在Table API定义的表上直接查询？how to?
//
//
//
//public class Datagen {
//    public static void main(String[] args) throws Exception {
//
//// Create a TableEnvironment for batch or streaming execution.
//// See the "Create a TableEnvironment" section for details.
//        EnvironmentSettings settings = EnvironmentSettings
//                .newInstance()
//                .inStreamingMode()
////                .inBatchMode() //when we can use batch mode and how to use it;
//                .build();
//        TableEnvironment tableEnv = TableEnvironment.create(settings);
//
//
//// Create a source table
//        TableDescriptor tableDescriptor_ex = TableDescriptor.forConnector("datagen")
//                .schema(Schema.newBuilder()
//                        .column("t_date", DataTypes.DATE())
//                        .column("t_time", DataTypes.TIME())
//                        .column("t_string", DataTypes.STRING())
//                        .column("t_num", DataTypes.INT())
//                        .build())
//                .build();
////        tableEnv.createTemporaryTable("SourceTable", tableDescriptor_ex);
//        tableEnv.createTable("SourceTable", tableDescriptor_ex);
//
//
//// Create a sink table (using SQL DDL)
////        tableEnv.executeSql("CREATE TEMPORARY TABLE SinkTable WITH ('connector' = 'blackhole') LIKE SourceTable");
//
//// Create a Table object from a Table API query
////        Table table2 = tableEnv.from("SourceTable");
////        table2.execute();
////        table2.execute().print();
//
////        Table SourceTable = tableEnv.from("SourceTable");
////        SourceTable.limit(10).execute().print();
//
////        Table SourceTable = tableEnv.from("SourceTable");
////        tableEnv.sqlQuery("select * from SourceTable where t_num>0").execute().print();
//
//
////        Table table_2 = tableEnv.from("SourceTable");
////        table_2.execute();
////        tableEnv.sqlQuery("select * from table_2 where t_num>0").execute().print();
//
//
//
//// Create a Table object from a SQL query
////        Table table3 = tableEnv.sqlQuery("SELECT * FROM SourceTable where t_num > 4 limit 10");
////        tableEnv.sqlQuery("select * from table3 where t_num>0").execute().print();
//
//
//// Emit a Table API result Table to a TableSink, same for SQL result
////        TableResult tableResult = table2.executeInsert("SinkTable");
//
//
//
//    }
//}
//
//
