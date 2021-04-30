package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ComputerGame {
    static Globals globals;
    int[][] game = new int[globals.n][2];

    public boolean check_game_for_arithmetical(int[][] game, int m, int k){
        int[][] game_sort = game;
        Arrays.sort(game_sort, Comparator.comparingInt(o -> o[0]));
        for (int cgfa=0; cgfa<k; cgfa++){
            ArrayList<Integer> game_color = new ArrayList<Integer>();
            for (int i=0; i<globals.n; i++) if (cgfa == game_sort[i][1]){
                game_color.add(game_sort[i][0]);
                if (game_color.size()<3) break;
                if (check_for_arithmetical(game_color, m)) return true;
            }
        }
        return false;
    }

    public boolean check_for_arithmetical(ArrayList<Integer> vect, int m){
        int n = vect.size(), d;
        for (int i=0; i<n-1; i++) for (int j=i+1; j<n; j++){
            d = vect.get(j) - vect.get(i);
            for (int x=vect.get(j)+d; x<vect.get(j)+d*(m-1); x+=d) if (!vect.contains(x)) return false;
        }
        return true;
    }

    public ArrayList<String> check_game(int[][] game, int move, int m, int l, int k){
        ArrayList<String> game_ch, game_x_ch, res;
        int victories;
        int[][] game_x;
        if (check_game_for_arithmetical(game, m, k)) return new ArrayList<>(Arrays.asList("T", "win"));
        if (move-1==game.length) return new ArrayList<>(Arrays.asList("F", "lose"));

        for (int i=0; i<game.length; i++) if (game[i][0]==-1){
            game_ch = new ArrayList<>();
            victories = 0;
            for (int color_ch=0; color_ch<m; color_ch++){
                game_x = game;
                game_x[i] = new int[]{i, color_ch};
                game_x_ch = check_game(game_x, move+1, m, l, k);
                game_ch.addAll(game_x_ch);
                victories += game_x_ch.get(0).equals("T") ? 1 : 0;
                if (victories>=l) break;
            }
            if (victories>=l) {
                res = new ArrayList<>(Arrays.asList("T", Integer.toString(i)));
                res.addAll(game_ch);
                return res;
            }
        }
        res = new ArrayList<>(/*Arrays.asList("F", Integer.toString(i))*/);
        //res.addAll(game_ch);
        //res.add("lose");
        return res;
    }
}
