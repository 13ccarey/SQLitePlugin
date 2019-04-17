package Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteUserDAO implements IUserDAO
{
    private Plugin plugin;

    public SQLiteUserDAO(Plugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void addUser(byte[] s)
    {
        try
        {
            PreparedStatement stmt = null;
            try
            {
                String sql = "insert into users (user) values (?);";
                plugin.beginTransaction();
                stmt = plugin.getConn().prepareStatement(sql);

                stmt.setString(1, new String(s));

                if (stmt.executeUpdate() != 1)
                {
                    System.out.println("Bad things are happening in add user!");
                }
            }
            finally
            {
                if (stmt != null)
                {
                    stmt.close();
                }
                plugin.endTransaction();
            }
        }
        catch (SQLException e)
        {
            System.out.println("Bad things are happening in add user");
        }
    }

    @Override
    public List<byte[]> getAllUsers()
    {
        try
        {
            PreparedStatement stmt = null;
            try
            {
                String sql = "SELECT user FROM users;";
                plugin.beginTransaction();
                stmt = plugin.getConn().prepareStatement(sql);

                ResultSet rs = stmt.executeQuery();

                ArrayList<byte[]> userlist = new ArrayList<>();
                while(rs.next())
                {
                    userlist.add(rs.getString("user").getBytes());
                }

                return userlist;
            }
            finally
            {
                if (stmt != null)
                {
                    stmt.close();
                }
                plugin.endTransaction();
            }
        }
        catch (SQLException e)
        {
            System.out.println("Bad things are happening in getAllUsers");
            return null;
        }
    }

    @Override
    public void addAuth(String s)
    {
        try
        {
            PreparedStatement stmt = null;
            try
            {
                String sql = "insert into authTokens (tokenID) values (?);";
                plugin.beginTransaction();
                stmt = plugin.getConn().prepareStatement(sql);

                stmt.setString(1, new String(s));

                if (stmt.executeUpdate() != 1)
                {
                    System.out.println("Bad things are happening in add auth!");
                }
            }
            finally
            {
                if (stmt != null)
                {
                    stmt.close();
                }
                plugin.endTransaction();
            }
        }
        catch (SQLException e)
        {
            System.out.println("Bad things are happening in add auth");
        }
    }

    @Override
    public List<String> getAllAuthTokens()
    {
        try
        {
            PreparedStatement stmt = null;
            try
            {
                String sql = "SELECT tokenID FROM authTokens;";
                plugin.beginTransaction();
                stmt = plugin.getConn().prepareStatement(sql);

                ResultSet rs = stmt.executeQuery();

                ArrayList<String> authList = new ArrayList<>();
                while(rs.next())
                {
                    authList.add(rs.getString("tokenID"));
                }

                return authList;
            }
            finally
            {
                if (stmt != null)
                {
                    stmt.close();
                }
                plugin.endTransaction();
            }
        }
        catch (SQLException e)
        {
            System.out.println("Bad things are happening in getAllUsers");
            return null;
        }
    }
}
