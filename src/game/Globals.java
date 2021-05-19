package game;

public class Globals {
    int n;
    int k;
    int m;
    int l;
    boolean isGoodAuto;
    boolean isBadAuto;
    boolean isDemo;

    public Globals(int n, int k, int m, int l, boolean isGoodAuto, boolean isBadAuto, boolean isDemo) {
        this.n = n;
        this.k = k;
        this.m = m;
        this.l = l;
        this.isGoodAuto = isGoodAuto;
        this.isBadAuto = isBadAuto;
        this.isDemo = isDemo;
    }

    @Override
    public String toString() {
        return "Globals{" +
                "n=" + n +
                ", k=" + k +
                ", m=" + m +
                ", l=" + l +
                ", isGoodAuto=" + isGoodAuto +
                ", isBadAuto=" + isBadAuto +
                ", isDemo=" + isDemo +
                '}';
    }
}
