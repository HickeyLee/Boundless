package Others;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

//Table API是单表方式，会对数据库造成较大的压力，测试通过，通过table api的方式实现标对表的cdc同步
public class CDC_TableAPI_PG {
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

        tableEnv.executeSql("CREATE TABLE pgsql_sink ( " +
                " age int, " +
                " name STRING ," +
                " PRIMARY KEY (name) NOT ENFORCED " +
                ") WITH ( " +
                " 'connector' = 'jdbc', " +
                " 'url' = 'jdbc:postgresql://localhost:5432/postgres', " +
                " 'username' = 'postgres', " +
                " 'password' = 'hickey', " +
                " 'table-name' = 'pg_sink' " +
                ")");

//        测试一：打印表结果
//        Table pg = tableEnv.sqlQuery("select * from pgsql_sink");
//        pg.execute().print();

//      测试二：表结果同步，对增删改均有效果,增删column不起作用
        TableResult tableResult = tableEnv.executeSql("INSERT INTO pgsql_sink select age,name from pgsql_source");
        tableResult.print();
    }
}

