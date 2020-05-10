package client;

public final class Data {


    public static int last = -1;
    public static int turn = 0;

    public static int myId = 0;
    public static int oppoId = 0;

    public static String myName = null;

    public static int BLACK = 1;
    public static int WHITE = -1;

    public static int myChess = 0;
    public static int oppoChess = 0;

    public static boolean connected = false;
    public static boolean ready = false;
    public static boolean started = false;

    public static int[][] chessBoard = new int[15][15];

}