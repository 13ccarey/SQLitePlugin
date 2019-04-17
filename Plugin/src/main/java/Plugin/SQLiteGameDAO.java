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
                String sql = "SELECT commands FROM games WHERE gameNum = ?;";
                plugin.beginTransaction();
                stmt = plugin.getConn().prepareStatement(sql);
                stmt.setInt(1, gameNum);

                ResultSet rs = stmt.executeQuery();

                String stringArray = rs.getString("commands");

                String addValue;
                if(stringArray == null)
                {
                    addValue = new String(s);
                }
                else
                {
                    addValue = stringArray + "---" + new String(s);
                }

                plugin.endTransaction();

                  sql = "UPDATE games " +
                        "SET commands = ? " +
                        "WHERE " +
                        "gameNum = ?";
                plugin.beginTransaction();
                stmt = plugin.getConn().prepareStatement(sql);

                stmt.setString(1, addValue);
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
                String sql = "UPDATE games SET commands = NULL";
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
    public List<gameWrapper> getAllGames()
    {
        try
        {
            PreparedStatement stmt = null;
            try
            {
                String sql = "SELECT game, gameLabel FROM games;";
                plugin.beginTransaction();
                stmt = plugin.getConn().prepareStatement(sql);

                ResultSet rs = stmt.executeQuery();

                ArrayList<byte[]> gamelist = new ArrayList<>();
                ArrayList<String> labelList = new ArrayList<>();
                while(rs.next())
                {
                    gamelist.add(rs.getString("game").getBytes());
                    labelList.add(rs.getString("gameLabel"));
                }

                ArrayList<gameWrapper> wrapperList = new ArrayList<>();
                for(int i = 0; i < gamelist.size(); i++)
                {
                    wrapperList.add(new gameWrapper(gamelist.get(i), labelList.get(i)));
                }


                return wrapperList;
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
        try
        {
            PreparedStatement stmt = null;
            try
            {
                String sql = "SELECT commands FROM games WHERE gameNum = ?;";
                plugin.beginTransaction();
                stmt = plugin.getConn().prepareStatement(sql);
                stmt.setInt(1, gameNum);

                ResultSet rs = stmt.executeQuery();

                ArrayList<byte[]> commandList = new ArrayList<>();
                String stringArray = rs.getString("commands");

                String[] commandListString = stringArray.split("---");
                for(int i = 0; i < commandListString.length; i++)
                {
                    commandList.add(commandListString[i].getBytes());
                }

                return commandList;
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
            System.out.println("Bad things are happening in getcommands");
            return null;
        }
    }
}
