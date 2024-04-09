package org.retailer.demo.config;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.Arrays;

@Configuration
public class InMemoryDB {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server inMemoryH2DatabaseaServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
    }

    @PostConstruct
    private void initDb() {

        String[] sqlStatements = {
                "drop table spendings if exists",
                "drop table customer if exists",
                "create table customer(id IDENTITY NOT NULL PRIMARY KEY, name varchar(255), email varchar(255))",
                "create table spendings(id IDENTITY NOT NULL PRIMARY KEY, customer_id long, description varchar(255)," +
                        " amount number, date date," +
                        " foreign key (customer_id) references customer(id))",
                "insert into customer(name, email) values('John Doe','j.doe@testmail.com')",
                "insert into customer(name, email) values('Jane Doe','j.doe@testmail.com')",
                "insert into spendings(customer_id, description, amount, date) values(1,'magazines', 60, '2023-01-01')",
                "insert into spendings(customer_id, description, amount, date) values(1,'candy', 70, '2023-01-15')",
                "insert into spendings(customer_id, description, amount, date) values(1,'shoes', 80, '2023-02-01')",
                "insert into spendings(customer_id, description, amount, date) values(1,'suit', 110, '2023-02-15')",
                "insert into spendings(customer_id, description, amount, date) values(1,'door', 80, '2023-03-01')",
                "insert into spendings(customer_id, description, amount, date) values(1,'play station', 200, '2023-03-15')",
                "insert into spendings(customer_id, description, amount, date) values(2,'shoes', 80, '2023-01-01')",
                "insert into spendings(customer_id, description, amount, date) values(2,'dinner', 90, '2023-01-15')",
                "insert into spendings(customer_id, description, amount, date) values(2,'dress', 130, '2023-02-01')",
                "insert into spendings(customer_id, description, amount, date) values(2,'phone', 500, '2023-02-15')",
                "insert into spendings(customer_id, description, amount, date) values(2,'tv', 600, '2023-03-01')",
                "insert into spendings(customer_id, description, amount, date) values(2,'make up', 180, '2023-03-15')"
        };

        Arrays.asList(sqlStatements).forEach(sql -> jdbcTemplate.execute(sql));
    }
}
