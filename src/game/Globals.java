package game;

public class Globals {
    int n;
    int k;
    int m;
    int l;
    boolean isAuto;
    boolean isDemo;

    public Globals(int n, int k, int m, int l, boolean isAuto, boolean isDemo) {
        this.n = n;
        this.k = k;
        this.m = m;
        this.l = l;
        this.isAuto = isAuto;
        this.isDemo = isDemo;
    }

    @Override
    public String toString() {
        return "Globals{" +
                "n=" + n +
                ", k=" + k +
                ", m=" + m +
                ", l=" + l +
                ", isAuto=" + isAuto +
                ", isDemo=" + isDemo +
                '}';
    }
}
