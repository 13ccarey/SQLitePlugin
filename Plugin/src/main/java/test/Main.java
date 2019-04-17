package test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import Plugin.IPersistenceProvider;
import Plugin.Plugin;
import Plugin.IGameDAO;
import Plugin.IUserDAO;
import Plugin.SQLiteGameDAO;
import Plugin.SQLiteUserDAO;

public class Main
{
    public Main()
    {

    }

    public static void main(String[] args)
    {
        IPersistenceProvider plugin = new Plugin();
        IGameDAO dao = plugin.getGameDAO();

        byte[] s = "Any string you want".getBytes();
        String label = "active";
        int num = 1;

        dao.setGameState(s, label, num);

        byte[] ss = "Any string you want2".getBytes();
        String labels = "active";
        int nums = 2;

        dao.setGameState(ss, labels, nums);

        List<byte[]> games = new ArrayList<>();
        dao.getAllGames();

        //System.out.println(new String(games.get(0)));
        //System.out.println(new String(games.get(1)));

        byte[] command = "my little command".getBytes();
        byte[] command2 = "my other little command".getBytes();
        dao.addCommand(command, 1);
        dao.addCommand(command2, 1);

        List<byte[]> commands = dao.getAllGameCommands(1);
        for(int i = 0; i < commands.size(); i++)
        {
            System.out.println(new String(commands.get(i)));
        }

        byte[] myUser1 = "Hello there, I'm a user!".getBytes();
        IUserDAO UDAO = plugin.getUserDAO();
        UDAO.addUser(myUser1);
        byte[] myUser2 = "Hi, um, I am also a user...".getBytes();
        UDAO.addUser(myUser2);
        List<byte[]> users = UDAO.getAllUsers();
        for(int i = 0; i < users.size(); i++)
        {
            System.out.println(new String(users.get(i)));
        }


        String auth1 = "clakjf;lakdsjf;alksdfj;asdlfjadslkf;sdlfa;sljf";
        UDAO.addAuth(auth1);
        String auth2 = "XXXLASDAHE ADFSLIHESFH LSEIHFSLDIHFHF     DD";
        UDAO.addAuth(auth2);
        List<String> auths = UDAO.getAllAuthTokens();
        for(int i = 0; i < auths.size(); i++)
        {
            System.out.println(auths.get(i));
        }
    }
}
