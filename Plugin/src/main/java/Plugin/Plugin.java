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
        gameDAO = new SQLiteGameDAO(this);
        userDAO = new SQLiteUserDAO(this);
        createTables();
    }

    @Override
    public boolean beginTransaction()
    {
        try
        {
            final String CONNECTION_URL = "jdbc:sqlite:C:/sqlite/ticket.db";

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
        beginTransaction();
        try
        {
            Statement stmt = null;
            try
            {
                stmt = conn.createStatement();

                //stmt.executeUpdate("drop table if exists users");
                stmt.executeUpdate("create table if not exists users (" +
                        "user varchar(255) not null unique);");

                //stmt.executeUpdate("drop table if exists authTokens");
                stmt.executeUpdate("create table if not exists authTokens (" +
                        "tokenID varchar(255) not null unique);");

                stmt.executeUpdate("create table if not exists games (" +
                        "gameNum integer not null unique, " +
                        "gameLabel varchar(255) not null, " +
                        "game varchar(255) not null, " +
                        "commands varchar(255));");

            }
            finally
            {
                if (stmt != null)
                {
                    stmt.close();
                    stmt = null;
                }
                endTransaction();
            }
        }
        catch (SQLException e)
        {
            System.out.println("createtables failed");
        }
    }
}
