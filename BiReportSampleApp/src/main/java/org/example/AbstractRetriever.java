package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class AbstractRetriever {
  /*
    Example to use custom consumer implementation
   */

    protected static void retrieve(Connection connection, String sqlQuery, Consumer<PreparedStatement> applyParams,SingleRecordConsumer consumer)
            throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            applyParams.accept(preparedStatement);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    consumer.accept(rs);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    protected static void retrieve(Connection connection,String sqlQuery,SingleRecordConsumer consumer) throws SQLException {
        retrieve(connection,sqlQuery,preparedStatement -> {},consumer);

    }

    protected static int executeCountQuery(Connection connection, String countQuery) throws SQLException {
        int cnt = -1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(countQuery)) {
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    cnt = rs.getInt(1);
                }
            }
        }
        return cnt;
    }
}
