package test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import Plugin.IPersistenceProvider;
import Plugin.Plugin;
import Plugin.IGameDAO;
import Plugin.SQLiteGameDAO;

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
        games = dao.getAllGames();

        System.out.println(new String(games.get(0)));
        System.out.println(new String(games.get(1)));
    }
}
