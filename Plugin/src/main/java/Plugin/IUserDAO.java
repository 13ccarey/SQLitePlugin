package Plugin;

import java.util.List;

public interface IUserDAO
{
    public void addUser(byte[] s);
    public List<byte[]> getAllUsers();
}
