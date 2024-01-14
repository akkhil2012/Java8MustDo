import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class AbstractNonParametrizedIgcAssetsRetriever<T>{
    public AbstractNonParametrizedIgcAssetsRetriever() {
       // this.mapper = mapper;
    }

    private final String sqlRetrieveQuery;
    private final String sqlCountQuery;

    public AbstractNonParametrizedIgcAssetsRetriever(String sqlRetrieveQuery, String sqlCountQuery, ResultSetMapper<T> mapper) {
        this.sqlRetrieveQuery = sqlRetrieveQuery;
        this.sqlCountQuery = sqlCountQuery;
        this.mapper = mapper;
    }

    private final ResultSetMapper<T> mapper;

    public <R> R retrieve(Connection connection, Function<Stream<T>,R> igcAssetTransformFunction){
        // resultSet as the chunk
        try(Stream<T> str = SqlStreamingExecutor.stream(connection,sqlRetrieveQuery,mapper)){
            return igcAssetTransformFunction.apply(str);
        }
       // Stream<T> stream = StreamSupport.stream(new ResultSetSpliterator(1l,1),false);

    }


    public static <T> Stream<T> createGenericStream(List<T> elements) {
        return elements.stream();
    }
}


class ResultSetSpliterator<T> extends Spliterators.AbstractSpliterator<T>{

    private final ResultSet resultSet;
    private final ResultSetMapper<T> mapper;
    private boolean completed = false;

    public ResultSetSpliterator(ResultSet resultSet, ResultSetMapper<T> mapper) {
        super(Long.MAX_VALUE, Spliterator.ORDERED);
        this.resultSet = resultSet;
        this.mapper = mapper;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> consumer) {
        try {
            if (completed) {
                return false;
            }
            if (!resultSet.next()) {
                completed = true;
                return false;
            }
            consumer.accept(mapper.map(resultSet));
        } catch (SQLException e) {
            //throw new MigrationSqlException(e);
        }
        return true;
    }

}













