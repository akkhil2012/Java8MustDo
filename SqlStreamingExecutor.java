import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SqlStreamingExecutor {


    public static <T> Stream<T> stream(Connection connection,String query,ResultSetMapper<T> mapper){
        return  stream(connection,query,mapper,null);
    }

    public static <T> Stream<T> stream(Connection connection, String query, ResultSetMapper<T> mapper, List<String> parameters) {
        //S2095 - use try-wit-resources for PreparedStatement, yet we can't as we need it alive when returning stream
        // we do close it in onClose handler
        PreparedStatement preparedStatement = null;
        Stream<T> stream;
        try {
            preparedStatement = connection.prepareStatement(query);
            if (parameters != null) {
                int parameterIndex = 1;
                for (String parameter : parameters) {
                    preparedStatement.setString(parameterIndex++, parameter);
                }
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            stream = StreamSupport.stream(new ResultSetSpliterator<>(resultSet, mapper), false);

            PreparedStatement finalPreparedStatement = preparedStatement;
            return stream.onClose(() -> close(finalPreparedStatement));
        } catch (SQLException e) {
           // throw closeDuringException(preparedStatement, e);
        }
    }

    private static void close(PreparedStatement preparedStatement) {
        try {
            preparedStatement.close(); // should close ResultSet too
        } catch (SQLException e) {
            //throw new MigrationSqlException(e);
        }
    }
}
