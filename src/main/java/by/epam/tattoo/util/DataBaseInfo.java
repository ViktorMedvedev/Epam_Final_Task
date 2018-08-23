package main.java.by.epam.tattoo.util;

public interface DataBaseInfo {
    String USER = "root";
    String PASSWORD = "1234";
    String URL = "jdbc:mysql://localhost:3306/" +
            "tattooparlor?" +
            "autoReconnect=true&" +
            "useSSL=false&useUnicode=true&" +
            "useJDBCCompliantTimezoneShift=true&" +
            "useLegacyDatetimeCode=false&" +
            "serverTimezone=UTC";
}