package com.example.trigers;

import org.h2.api.Trigger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TeracotaDeleteTriger implements Trigger {
    @Override
    public void init(Connection conn, String schemaName, String triggerName, String tableName, boolean before, int type) throws SQLException {
        //Trigger.super.init(conn, schemaName, triggerName, tableName, before, type);
    }

    @Override
    public void fire(Connection conn, Object[] oldRow, Object[] newRow) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "DELETE log (field1, field2, ...) " + "VALUES (?, ?, ...)")
        ) {
            ps.setObject(1, newRow[0]);
            ps.setObject(2, newRow[1]);
            ps.executeUpdate();
        }
    }

    @Override
    public void close() throws SQLException {
        Trigger.super.close();
    }

    @Override
    public void remove() throws SQLException {
        Trigger.super.remove();
    }
}
