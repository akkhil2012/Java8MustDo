package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SingleRecordConsumer {
    void accept(ResultSet rs) throws SQLException;
}

