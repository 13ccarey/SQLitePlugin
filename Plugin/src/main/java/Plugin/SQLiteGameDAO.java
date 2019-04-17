package Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteGameDAO implements IGameDAO
{
    private Plugin plugin;

    public SQLiteGameDAO(Plugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void addCommand(byte[] s, int gameNum)
    {
        try
        {
            PreparedStatement stmt = null;
            try
            {
                String sql = "UPDATE games" +
                        "SET commands = ?" +
                        "WHERE" +
                        "gameNum = ?";
                plugin.beginTransaction();
                stmt = plugin.getConn().prepareStatement(sql);

                //stmt.setString(1, user.getUsername());
                stmt.setString(2, Integer.toString(gameNum));

                if (stmt.executeUpdate() != 1)
                {
                    System.out.println("Bad things are happenning in the SQLiteGameDAO object!");
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
            System.out.println("Bad things are happening in addcommand");
        }
    }

    @Override
    public void setGameState(byte[] s, String label, int gameNum)
    {
        try
        {
            PreparedStatement stmt = null;
            try
            {
                String sql = "insert into games (gameNum, gameLabel, game, commands) values (?,?,?,?);";
                plugin.beginTransaction();
                stmt = plugin.getConn().prepareStatement(sql);

                stmt.setString(1, Integer.toString(gameNum));
                stmt.setString(2, label);
                stmt.setString(3, new String(s));
                stmt.setString(4, null);

                if (stmt.executeUpdate() != 1)
                {
                    System.out.println("Bad things are happening in set game state!");
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
            System.out.println("Bad things are happening in set game state");
        }
    }

    @Override
    public void clearCommands() {
        try
        {
            PreparedStatement stmt = null;
            try
            {
                String sql = "UPDATE games SET commands=NULL";
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
            System.out.println("Bad things are happening in clear commands!");
        }
    }

    @Override
    public List<byte[]> getAllGames()
    {
        try
        {
            PreparedStatement stmt = null;
            try
            {
                String sql = "SELECT game FROM games;";
                plugin.beginTransaction();
                stmt = plugin.getConn().prepareStatement(sql);

                ResultSet rs = stmt.executeQuery();
                int size = 0;

                ArrayList<byte[]> gamelist = new ArrayList<>();
                /*for(int i = 0; i < size; i++)
                {
                    String output =  (String) rs.getObject(i);
                    gamelist.add(output.getBytes());
                }*/
                while(rs.next())
                {
                    gamelist.add(rs.getString("game").getBytes());
                }


                return gamelist;
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
            System.out.println("Bad things are happening in getAllGames");
            return null;
        }
    }


    @Override
    public List<byte[]> getAllGameCommands(int gameNum)
    {
        return null;
    }
}
