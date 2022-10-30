package org.example;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import com.ververica.cdc.connectors.mysql.source.MySqlSource;

public class CDC_TableAPI_Mysql {
    public static void main(String[] args) throws Exception{

        //设置flink表环境变量
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        // 2.DDL方式建表
        tableEnv.executeSql("CREATE TABLE orders  ( " +
                " name STRING, " +
                " age int " +
                ") WITH ( " +
                " 'connector' = 'mysql-cdc', " + // 连接器的名称
                " 'hostname' = '1.15.120.226', " +
                " 'port' = '3306', " +
                " 'username' = 'root', " +
                " 'password' = 'CJ&hickey00', " +
                " 'database-name' = 'bigdata', " +
                " 'table-name' = 'bigdata.table_test', " +
                " 'debezium.snapshot.mode' = 'never', " +
                " 'decoding.plugin.name' = 'pgoutput' " +
                ")");



//        测试一：打印表结果
//        Table pg = tableEnv.sqlQuery("select * from pgsql_sink");
//        pg.execute().print();

//      测试二：表结果同步，对增删改均有效果,增删column不起作用
        TableResult tableResult = tableEnv.executeSql("SELECT * FROM orders");
        tableResult.print();
    }

}
