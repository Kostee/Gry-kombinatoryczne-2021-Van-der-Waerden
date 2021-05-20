package game;

public class Main {
    static Globals globals;

    public static void main(String[] args) {
        new Begin();
        new Play(globals);
    }
}
