package Plugin;

import java.util.List;

public interface IUserDAO
{
    public void addUser(byte[] s);
    public List<byte[]> getAllUsers();
    public void addAuth(String s);
    public List<String> getAllAuthTokens();
}
