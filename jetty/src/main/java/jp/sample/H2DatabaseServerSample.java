package jp.sample;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.h2.tools.Server;
import org.h2.util.JdbcUtils;

public class H2DatabaseServerSample {

    public static void main(String[] args) throws SQLException {
        String baseDir = args[0];
        // DBサーバの起動
        Server server = Server.createTcpServer("-baseDir", baseDir);
        server.start();
        System.out.println("server started");
        // スキーマの設定
        Properties props = new Properties();
        props.setProperty("user", "sa");
        props.setProperty("password", "");
        String dbName = "testdb1";
        String url = "jdbc:h2:" + server.getURL() + "/" + dbName;
        Connection conn = org.h2.Driver.load().connect(url, props);
        String schemaName = "testschema1";
        try {
            conn.createStatement().execute(
                    "CREATE SCHEMA IF NOT EXISTS " + schemaName);
        } catch (Exception e) {
            JdbcUtils.closeSilently(conn);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        server.shutdown();
        System.out.println("server shutdown");
    }

}
