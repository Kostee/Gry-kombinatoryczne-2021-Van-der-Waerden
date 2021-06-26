package game;

public class Globals {
    int n;
    int k;
    int m;
    int l;
    int good;
    int bad;
    boolean isDemo;
    int games;

    public Globals(int n, int k, int m, int l, int good, int bad, boolean isDemo, int games) {
        this.n = n;
        this.k = k;
        this.m = m;
        this.l = l;
        this.good = good;
        this.bad = bad;
        this.isDemo = isDemo;
        this.games = games;
    }

    @Override
    public String toString() {
        return "Globals{" +
                "n=" + n +
                ", k=" + k +
                ", m=" + m +
                ", l=" + l +
                ", good=" + good +
                ", bad=" + bad +
                ", isDemo=" + isDemo +
                ", games=" + games +
                '}';
    }
}
