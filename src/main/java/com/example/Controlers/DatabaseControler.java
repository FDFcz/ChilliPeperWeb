package com.example.Controlers;
import com.example.Structures.*;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.imageio.plugins.tiff.TIFFDirectory;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class DatabaseControler {

    private DataSource dataSource;
    public Boolean initDB()
    {
        dataSource = createSource();
        try(Connection connection = dataSource.getConnection())
        {
            System.out.println("sucess");
            return true;
        } catch (SQLException e) {
            //return false;
            throw new RuntimeException(e);
        }
    }
    public int registryNewUser(String name, String password)
    {
        try(Connection connection = dataSource.getConnection())
        {
            PreparedStatement ps = connection.prepareStatement("insert into CUSTOMER (username,password)  VALUES (?,?)");
            ps.setString(1, name);
            ps.setString(2, password);
            int result = ps.executeUpdate();
            return getUserID(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String[][] getAllTAble(String nameOfTable)
    {
        try(Connection connection = dataSource.getConnection())
        {
            String avoidPreparedStatement = "SELECT * FROM "+nameOfTable+";";
            PreparedStatement ps = connection.prepareStatement(avoidPreparedStatement);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData metadata = rs.getMetaData();
            int columnCount = metadata.getColumnCount();
            List<String> stringStream = new ArrayList<>();
            while(rs.next())
               for (int i=1;i<=columnCount;i++) {
                       switch (metadata.getColumnTypeName(i)) {
                           case "CHARACTER VARYING","TIMESTAMP":
                               stringStream.add(rs.getString(i));
                               break;
                           case "INTEGER":
                               stringStream.add(String.valueOf(rs.getInt(i)));
                               break;
                           case "BOOLEAN":
                               stringStream.add(String.valueOf(rs.getBoolean(i)));
                               break;
                           case "FLOAT","DOUBLE PRECISION":
                               stringStream.add(String.valueOf(rs.getFloat(i)));
                               break;
                           default:
                               System.out.println(metadata.getColumnTypeName(i));
                               break;
                   }
               }
            String[][] returnDictionary = new String[stringStream.size()/columnCount+1][columnCount];
            for (int i = 0; i < columnCount; i++) {
                returnDictionary[0][i]= metadata.getColumnName(i+1);
            }
            int n =0;
            for (int i = 0; i < returnDictionary.length-1; i++) {
                for (int j = 0; j < returnDictionary[0].length; j++) {
                    returnDictionary[i+1][j] = stringStream.get(n++);
                }
            }
            return returnDictionary;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static DataSource createSource()
    {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:h2:mem:;INIT=RUNSCRIPT FROM 'classpath:db/Tables.sql'");
        return ds;
    }
    public String getUserPassword(int userID){return getUserPassword(getUser(userID).getUserName());}
    public String getUserPassword(String username)
    {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) return rs.getString("password");
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public int getUserID(String username)
    {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) return rs.getInt("customer_id");
            return -1;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Customer getUser(int userID)
    {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE customer_id = ?");
            ps.setString(1, String.valueOf(userID));
            ResultSet rs = ps.executeQuery();
            if(rs.next()) return new Customer(userID,rs.getString("username"));
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Customer getUserWithTeracotas(int userID)
    {
        try(Connection connection = dataSource.getConnection()) {
            Customer retUser;
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE customer_id = ?");
            ps.setString(1, String.valueOf(userID));
            ResultSet rs = ps.executeQuery();
            if(rs.next()) retUser = new Customer(userID,rs.getString("username"));
            else return null;
            ps = connection.prepareStatement("SELECT * FROM terracotta WHERE owner = ?");
            ps.setInt(1,userID);
            rs = ps.executeQuery();
            while(rs.next()) retUser.addTeracota(new Teracota(rs.getInt("terracotta_id"),rs.getString("name"), Teracota.PlantTypes.values()[rs.getInt("plant")], rs.getDate("planted_at")));
            return retUser;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void changeUser(int userID, String userName, String password)
    {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("UPDATE customer SET username = ?, password = ? WHERE customer_id = ?");
            ps.setString(1,userName);
            ps.setString(2, password);
            ps.setInt(3, userID );
            int result = ps.executeUpdate();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void addTeracota(int userID, String teracotaName,int plantID)
    {
        try(Connection connection = dataSource.getConnection())
        {
            PreparedStatement ps = connection.prepareStatement("insert into terracotta (name,owner,plant,planted_at,PLC)  VALUES (?,?,?,?,1)",Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,teracotaName);
            ps.setInt(2, userID);
            ps.setInt(3, plantID);
            ps.setDate(4, Date.valueOf(LocalDate.now()));
            int result = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int identity = rs.getInt(1);
            //todo: automaticli add crons
            //addNewCron(identity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Teracota getTeracota(int teraID)
    {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM TERRACOtTA WHERE terracotta_id = ?");
            ps.setString(1, String.valueOf(teraID));
            ResultSet rs = ps.executeQuery();
            if(rs.next()) return new Teracota(teraID,rs.getString("name"), Teracota.PlantTypes.values()[rs.getInt("plant")],rs.getDate("planted_at"));
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteTeracota(int teraID)
    {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SET REFERENTIAL_INTEGRITY FALSE; BEGIN TRANSACTION;DELETE FROM CRON WHERE tracota = ?;DELETE FROM TERRACOTTA WHERE terracotta_id = ? ;COMMIT;SET REFERENTIAL_INTEGRITY TRUE;");
            ps.setInt(1, teraID);
            ps.setInt(2, teraID);
            ps.execute();

            //Todo: delete Schedule
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Cron> getCronToTeracota(int teraID)
    {
        try(Connection connection = dataSource.getConnection()) {
            List<Cron> cronList = new ArrayList<>();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM CRON WHERE tracota = ?");
            ps.setInt(1,teraID);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                int cronID = rs.getInt("cron_id");
                int scheduleID = rs.getInt("Schedl");
                int start= rs.getInt("start");
                int endTime= rs.getInt("endTime");

                ps = connection.prepareStatement("SELECT * FROM schedule WHERE schedule_id = ?");
                ps.setInt(1,scheduleID);
                ResultSet rs2 = ps.executeQuery();
                ResultSetMetaData mt = rs.getMetaData();

                rs2.next();
                float temperature = rs2.getFloat("temp");
                float light=rs2.getFloat("light");
                int humidity =rs2.getInt("humidity");

                Schedule sch= new Schedule(scheduleID,temperature,light,humidity);
                cronList.add(new Cron(cronID,sch,start,endTime));
            }
            return cronList;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void addNewCron(int teraID)
    {
        try(Connection connection = dataSource.getConnection())
        {
            //todo: BEGIN TRANSACTION; not working
            PreparedStatement ps = connection.prepareStatement("insert into schedule (temp,light,humidity) VALUES (?,?,?)",Statement.RETURN_GENERATED_KEYS);
            ps.setFloat(1,30);
            ps.setFloat(2, 1);
            ps.setInt(3, 1);
            int ef= ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int identity =rs.getInt("schedule_id");

            ps = connection.prepareStatement("insert into Cron (tracota,Schedl,start,endTime) VALUES (?,?,?,?);");
            ps.setInt(1, teraID);
            ps.setInt(2, identity);
            ps.setInt(3, 0);
            ps.setInt(4,23);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateCron(Cron cron)
    {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("UPDATE Cron SET start = ?, endTime= ? WHERE cron_id = ?");
            ps.setInt(1,cron.getStartTime());
            ps.setInt(2, cron.getEndTime());
            ps.setInt(3, cron.getId());
            int result = ps.executeUpdate();

            ps = connection.prepareStatement("UPDATE schedule SET temp = ?, light =? WHERE schedule_id = ?");
            ps.setFloat(1,cron.getSchedule().getTemperature());
            ps.setFloat(2, cron.getSchedule().getLight());
            ps.setInt(3, cron.getSchedule().getId());
            result = ps.executeUpdate();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteCron(int cronID)
    {
        try(Connection connection = dataSource.getConnection())
        {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM CRON WHERE cron_id = ?;");
            ps.setInt(1, cronID);
            ps.execute();
            //Todo: delete Schedule
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    public Plant getPlant(int plantID)
    {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM plantType WHERE plantType_id = ?");
            ps.setString(1, String.valueOf(plantID));
            ResultSet rs = ps.executeQuery();
            if(rs.next()) return new Plant(Teracota.PlantTypes.values()[plantID],Integer.valueOf(rs.getInt("growtimeindays")));
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
