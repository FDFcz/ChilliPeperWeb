package com.example.triggers;

import org.h2.api.Trigger;

import java.sql.*;

public class TeracotaCreateTrigger implements Trigger {
    @Override
    public void fire(Connection conn, Object[] oldRow, Object[] newRow) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("insert into schedule (temp,light,humidity) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setFloat(1,30);
            ps.setFloat(2, 1);
            ps.setInt(3, 1);
            int ef= ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int identity =rs.getInt("schedule_id");

            ps = conn.prepareStatement("insert into Cron (tracota,Schedl,start,endTime) VALUES (?,?,?,?);");

            ps.setObject(1, newRow[0]);
            ps.setInt(2, identity);
            ps.setInt(3, 0);
            ps.setInt(4,23);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
