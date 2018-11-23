package com.flappybird.Model;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseFB {

    private Connection connection;

    public DatabaseFB(String nameOfDb) {
        try {
            createDataBase(nameOfDb);
            createTable();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection connect(String nameOfDb) {
        String url = "jdbc:sqlite:" + nameOfDb;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            if (conn == null)
                closeConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    private void createDataBase(String nameOfDb) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = connect(nameOfDb);
        DatabaseMetaData meta = connection.getMetaData();
        System.out.println("The driver name is " + meta.getDriverName());
        System.out.println("Database was created with name " + nameOfDb + " successfully.");
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS score_table(\n"
                + " score integer"
                + ");";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfValueExists(int value) {
        String sql = "SELECT * FROM score_table WHERE score = " + value + ";";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (!resultSet.isBeforeFirst())
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void insertValueToTable(int value) {
        boolean exists = checkIfValueExists(value);

        if (exists == false) {
            String sql = "INSERT INTO score_table VALUES(?);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, value);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getMaxScoreFromTable() {
        String sql = "SELECT MAX(score) FROM score_table;";

        int score = 0;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            score = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }

    public ArrayList<Integer> getTopFiveFromTable() {
        String sql = "SELECT DISTINCT * FROM score_table WHERE score NOT LIKE 0 ORDER BY score DESC LIMIT 5;";

        ArrayList<Integer> n = new ArrayList<>();
        try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                n.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    private void selectAll() throws SQLException {
        String sql = "SELECT * FROM score_table;";

        try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("score"));
            }
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
