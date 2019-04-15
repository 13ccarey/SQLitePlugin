package Plugin;

public interface IPersistenceProvider
{
    public boolean beginTransaction();
    public boolean endTransaction();
    public IGameDAO getGameDAO();
    public IUserDAO getUserDAO();
    public String getLabel();
}
