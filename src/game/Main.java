package game;

public class Main {
    static Globals globals;

    public static void main(String[] args) {
        new Begin();
        System.out.println(globals);
        new Play(globals);
    }
}
