package Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Plugin implements IPersistenceProvider
{
    private SQLiteGameDAO gameDAO;
    private SQLiteUserDAO userDAO;
    private Connection conn;

    public Plugin()
    {
        gameDAO = new SQLiteGameDAO();
        userDAO = new SQLiteUserDAO();
        createTables();
    }

    @Override
    public boolean beginTransaction()
    {
        try
        {
            final String CONNECTION_URL = "jdbc:sqlite:TicketToRide.sqlite";

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);

            return true;
        }
        catch (SQLException e)
        {
            return false;
        }
    }

    @Override
    public boolean endTransaction()
    {
        try
        {
            conn.commit();
            conn.close();
            conn = null;
            return true;
        }
        catch (SQLException e)
        {
            return false;
        }
    }

    @Override
    public IGameDAO getGameDAO()
    {
        return gameDAO;
    }

    @Override
    public IUserDAO getUserDAO()
    {
        return userDAO;
    }

    @Override
    public String getLabel()
    {
        return "sqlite";
    }

    public Connection getConn()
    {
        return conn;
    }

    private void createTables()
    {
        try
        {
            Statement stmt = null;
            try
            {
                stmt = conn.createStatement();

                //stmt.executeUpdate("drop table if exists users");
                stmt.executeUpdate("create table if not exists users (username varchar(255) not null unique," +
                        "password varchar(255) not null," +
                        "email varchar(255) not null," +
                        "firstName varchar(255) not null," +
                        "lastName varchar(255) not null," +
                        "gender ck_genre check (gender in ('f', 'm'))," +
                        "personID varchar(255) not null unique);");

                //stmt.executeUpdate("drop table if exists authTokens");
                stmt.executeUpdate("create table if not exists authTokens (" +
                        "tokenID varchar(255) not null unique," +
                        "username varchar(255) not null unique" +
                        ");");

                //stmt.executeUpdate("drop table if exists persons");
                stmt.executeUpdate("create table if not exists persons (" +
                        "descendant varchar(255) not null," +
                        "personID varchar(255) not null unique," +
                        "firstName varchar(255) not null," +
                        "lastName varchar(255) not null," +
                        "gender ck_genre check (gender in ('f', 'm'))," +
                        "fatherID varchar(255)," +
                        "motherID varchar(255)," +
                        "spouseID varchar(255)" +
                        ");");

                //stmt.executeUpdate("drop table if exists events");
                stmt.executeUpdate("create table if not exists events (" +
                        "descendant varchar(255) not null," +
                        "eventID varchar(255) not null unique," +
                        "personID varchar(255) not null," +
                        "latitude float(24) not null," +
                        "longitude float(24) not null," +
                        "country varchar(255) not null," +
                        "city varchar(255) not null," +
                        "eventType varchar(255) not null," +
                        "year int not null" +
                        ");");
            }
            finally
            {
                if (stmt != null)
                {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("createtables failed");
        }
    }
}
