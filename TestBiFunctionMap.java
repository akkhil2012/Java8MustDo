
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class TestBiFunctionMap<T, B> {

    private final ConnectionSupplier connectionSupplier;

    public static final int BATCH_SIZE = 1000;

    private final EdsRetriever<T, B> retriever;



    public TestBiFunctionMap() {
        EdsApplicationRetriever retriever1 = new EdsApplicationRetriever();
        this.retriever = retriever1;
        // get the connection supplier here:
        CliDatabaseOptions cliDatabaseOptions = new CliDatabaseOptions();
        this.connectionSupplier = cliDatabaseOptions.getConnectionSupplier();
    }

    public static void main(String args[]) {
        new TestBiFunctionMap().export(Map.of());
    }


    // case:1 Dummy Context and the Database context using hikari DB Pool
    private void export(Map<String, String> contextByAssetId) {
        //case1:
        /*Stream.of("akhil", "kumar", "gupta")
                .map(eds -> this.toCamsAsset(eds, Map.of()))
                .collect(Collectors.toList())
                .forEach(System.out::println);*/


        // case 2: retriver case
        new TestBiFunctionMap().testRetriver(Map.of());


    }

    // case 2:
    void testRetriver(Map<String, String> contextByAssetId) {
        StreamMappingSizedChunkedSubCollector StreamMappingSizedChunkedSubCollector
                = new StreamMappingSizedChunkedSubCollector();
        Connection connection = null;

        Set<String> assets =
                retriever.retrieve(connection, stream ->
                        StreamMappingSizedChunkedSubCollector.chunked(stream.map(eds -> this.toCamsAsset(eds, contextByAssetId)), BATCH_SIZE,
                                        new CamsBatchAssetCollector())
                                .flatMap(Collection::stream)
                                .collect(Collectors.toSet());
        )

    }


    // case 1:
    private AssetWithExportState toCamsAsset(T eds, Map<String, String> contextByAssetId) {
        AtomicReference<ExportState> state = new AtomicReference<>();
        AssetWithExportState assetWithExportState
                = new AssetWithExportState(state.get());

        return assetWithExportState;

    }


    protected <T> T doConnection(Function<Connection, T> function) {
        return ConnectionUtils.doInConnection(connectionSupplier, function);
        // Connection connection = null;
        // return  function.apply(Optional.ofNullable(connection).get());
    }


}



/*

  To retreive the Database Connection
 */

class CliDatabaseOptions {

    protected String databaseDriver;
    protected String databaseUsername;
    protected String databaseUrl;
    protected String databasePassword;


    public ConnectionSupplier getConnectionSupplier() {
        PropertyDecoder decoder = PropertyDecoderProvider.getPropertyDecoder("iis_installPath");
        return new ConnectionSupplierProvider(databaseDriver, databaseUrl, decoder.getDecodedValue(databaseUsername),
                decoder.getDecodedValue(databasePassword)).validateAndCreate();
    }
}


class ConnectionSupplierProvider {
    private final ConnectionSupplier connectionSupplier;

    // ConnectionSupplier introduced so that the new DB type can also be added
    public ConnectionSupplierProvider(String databaseDriver, String databaseUrl, String databaseUsername, String databasePassword) {
        connectionSupplier = new ConnectionSupplierImpl(databaseDriver, databaseUrl, databaseUsername, databasePassword, 4);
    }

    public ConnectionSupplier validateAndCreate() {
        try (Connection connection = connectionSupplier.get()) {
            if (!connection.isValid(5)) {
                // throw new ValidationException(MigrationError.CONNECTION_VALIDATION_FAILURE);
            }
        } catch (SQLException e) {
            //throw new ValidationException(MigrationError.CONNECTION_VALIDATION_FAILURE, e);
        }
        return connectionSupplier;
    }
}


class ConnectionSupplierImpl implements ConnectionSupplier {

    private final HikariDataSource dataSource;
    private final String databaseUser;


    public ConnectionSupplierImpl(String databaseDriver, String databaseUrl, String databaseUsername, String databasePassword, int poolSize) {
        this.databaseUser = databaseUsername;

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(databaseUrl);
        config.setDriverClassName(databaseDriver);
        config.setUsername(databaseUsername);
        config.setPassword(databasePassword);
        config.setMaximumPoolSize(poolSize);
        dataSource = new HikariDataSource(config);
    }

    @Override
    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            try {
                dataSource.close();
            } catch (Exception e) {
                // LOGGER.error("Unable to close connection pool");
            }
        }
    }

    @Override
    public Connection get() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}



