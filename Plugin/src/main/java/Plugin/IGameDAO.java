package Plugin;

import java.util.List;

public interface IGameDAO
{
    public void addCommand(byte[] s, int gameNum);
    public void setGameState(byte[] s, String label, int gameNum);
    public void clearCommands();
    public List<gameWrapper> getAllGames();
    public List<byte[]> getAllGameCommands(int gameNum);
}
