import java.sql.Connection;
import java.util.function.Supplier;

public interface ConnectionSupplier extends AutoCloseable, Supplier<Connection> {

    @Override
    void close();

}
