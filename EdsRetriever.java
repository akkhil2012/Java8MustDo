public abstract class EdsRetriever<T,B> extends AbstractNonParametrizedIgcAssetsRetriever<T>{
    public EdsRetriever() {
    }

    private static final String SQL_EDS_RETRIEVE = """
            SELECT OBJECT_RID, NAME, XMETA_CREATION_TIMESTAMP, XMETA_MODIFICATION_TIMESTAMP,
                   XMETA_CREATED_BY_USER, XMETA_MODIFIED_BY_USER,
                   SHORTDESCRIPTION, LONGDESCRIPTION
             FROM XMDFLTVIEWS.EXTENSIONDATASOURCE where DSTYPE = '%s'""";

    private static final String SQL_EDS_COUNT =
            "select count(*) as TYPE_COUNT from XMDFLTVIEWS.EXTENSIONDATASOURCE where DSTYPE = '%s'";

    public EdsRetriever(String sqlRetrieveQuery, String sqlCountQuery, ResultSetMapper<T> mapper) {
        super(sqlRetrieveQuery, sqlCountQuery, mapper);
    }
}
