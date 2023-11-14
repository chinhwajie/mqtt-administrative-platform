package com.mqtt.admin.helper;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Data
@Slf4j
public class MySqlConnector {
    @Getter
    private Connection connection;
    private String url;
    private String user;
    private String password;
    private String driver;

    public MySqlConnector() {
        try {
            //创建Properties集合类
            InputStream inputStream = MySqlConnector.class.getResourceAsStream("/mysql.properties");
            Properties properties = new Properties();

            //加载文件
            properties.load(inputStream);

            //获取数值
            this.url = properties.getProperty("url");
            this.user = properties.getProperty("user");
            this.password = properties.getProperty("password");
            this.driver = properties.getProperty("driver");
            Class.forName(this.driver);

            try {
                this.connection = DriverManager.getConnection(url, user, password);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } catch (IOException | ClassNotFoundException e) {
            log.error(e.getMessage());
        }
    }

    public void close() throws SQLException {
        connection.close();
    }

}
