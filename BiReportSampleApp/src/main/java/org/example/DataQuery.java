package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class DataQuery extends AbstractRetriever {
    private static Map<String, BiReport> allReports;

    private static final String COUNT_BI_REPORT_SQL = "select count(*) from T";
    private static final String QUERY_BI_REPORT_SQL = "";

    private static int reportCount;

    public static Map<String, BiReport> getAllReports() {
        return allReports;
    }


    public static void fetchAllReports(Connection connection) throws SQLException {// this should have connection as arguments
        reportCount = executeCountQuery(connection, COUNT_BI_REPORT_SQL);

        allReports = new HashMap<>();

        retrieve(connection, QUERY_BI_REPORT_SQL, rs -> {
            String id = rs.getString(1);
            BiReport biReport = createBiAsset(new BiReport(id), rs.getString(2), rs.getString(3));
            // asset creation implementation defered for now
            allReports.put(id, biReport);
        });

    }


    private static <T extends BIAssetWthMetaData> T createBiAsset(T asset, String name, String shortDescription) {
        BiMetadata biMetadata = asset.getMetadata();
        biMetadata.setName(name);
        biMetadata.setDescription(shortDescription);
        return asset;
    }


}














