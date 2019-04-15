package Plugin;

import java.util.List;

public interface IGameDAO
{
    public void addCommand(byte[] s, int gameNum);
    public void setGameState(byte[] s, int gameNum);
    public void clearCommands();
    public List<byte[]> getAllGames();
    public List<byte[]> getAllGameCommands(int gameNum);
}
