package Plugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SQLiteGameDAO implements IGameDAO
{
    private Plugin plugin;

    @Override
    public void addCommand(String command)
    {
        try
        {
            PreparedStatement stmt = null;
            try
            {
                String sql = "insert into users (username, password, email, firstName, lastName," +
                        " gender, personID) values (?,?,?,?,?,?,?);";
                plugin.beginTransaction();
                stmt = plugin.getConn().prepareStatement(sql);

                //stmt.setString(1, user.getUsername());
                //stmt.setString(2, user.getPassword());
                //stmt.setString(3, user.getEmail());
                //stmt.setString(4, user.getFirstName());
                //stmt.setString(5, user.getLastName());
                //stmt.setString(6, String.valueOf(user.getGender()));
                //stmt.setString(7, user.getPersonID());

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
            System.out.println("Bad things are happenning in the SQLiteGameDAO object!");
        }
    }

    @Override
    public void setGameState(String state)
    {

    }

    @Override
    public void clearCommands()
    {

    }

    @Override
    public List<byte[]> getAllGames()
    {
        return null;
    }

    @Override
    public List<byte[]> getAllGameCommands()
    {
        return null;
    }
}
