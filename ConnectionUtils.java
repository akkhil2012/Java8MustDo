import java.sql.Connection;
import java.util.function.Function;

public class ConnectionUtils {

    public static <T> T doInConnection(ConnectionSupplier  connectionSupplier, Function<Connection,T> function){
                Connection connection = connectionSupplier.get();
                return function.apply(connection);

    }
}
