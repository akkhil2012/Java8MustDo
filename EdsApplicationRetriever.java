public class EdsApplicationRetriever extends EdsRetriever{
    public EdsApplicationRetriever() {
    }

    public EdsApplicationRetriever(String sqlRetrieveQuery, String sqlCountQuery, ResultSetMapper mapper) {
        super(sqlRetrieveQuery, sqlCountQuery, mapper);
    }
}
