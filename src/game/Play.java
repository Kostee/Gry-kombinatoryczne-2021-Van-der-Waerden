package game;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Play {
    Scanner input = new Scanner(System.in);
    Random inputAuto = new Random();
    String inputText;
    String table;
    int field;
    int inputNumber;
    boolean valid;

    boolean check_for_arithmetical(ArrayList<Integer> pola, int m){
        int n = pola.size(), d;
        boolean arithm;
        for (int i=0; i<n-1; i++) for (int j=i+1; j<n; j++){
            d = pola.get(j)-pola.get(i);
            arithm=true;
            for (int x=1; x<m-1; x++) if (!pola.contains(pola.get(j)+d*x)){
                arithm=false;
                break;
            }
            if (arithm) return true;
        }
        return false;
    }

    boolean check_game_for_arithmetical(ArrayList<ArrayList<Integer>> game_board, int m, int k){
        ArrayList<ArrayList<Integer>> game_sort = new ArrayList<>();
        ArrayList<Integer> game_color;
        game_sort.addAll(game_board);
        game_sort.sort(Comparator.comparing(o -> o.get(0)));
        boolean inside;
        for (int cgfa=0; cgfa<k; cgfa++){
            inside=false;
            for (ArrayList<Integer> s:game_sort) if (s.get(1)==cgfa) inside=true;
            if (inside){
                game_color = new ArrayList<>();
                for (ArrayList<Integer> s:game_sort) if (s.get(1)==cgfa) game_color.add(s.get(0));
                if (game_color.size()<3) continue;
                if (check_for_arithmetical(game_color, m)) return true;
            }
        }
        return false;
    }

    String fieldTable(Integer[] pola, Integer[] tla, Globals G){
        table="";
        String fore;
        for (int i=0; i<G.n; i++){
            if (pola[i] == -1) table += "[ ]";
            else {
                fore = (tla[pola[i]]-16)%36<18?"231":"16";
                table += "[\033[38;5;"+fore+";48;5;"+Integer.toString(tla[pola[i]])+"m"+Integer.toString(pola[i]+1)+"\033[0m]";
            }
        }
        return table;
    }

    ArrayList<ArrayList<Integer>> check_game(ArrayList<ArrayList<Integer>> game_board,
                                             int move, int m, int l, int k){
        ArrayList<ArrayList<Integer>> out_list = new ArrayList<>(),
                game_ch = new ArrayList<>(), gameX, gameX_ch;
        ArrayList<Integer> out_row = new ArrayList<>();
        if (check_game_for_arithmetical(game_board, m, k)){
            out_row.add(1);
            for (int i=0; i<k+1; i++) out_row.add(-1);
            out_list.add(out_row);
            return out_list;
        }
        if (move-1 == game_board.size()){
            out_row.add(0);
            for (int i=0; i<k+1; i++) out_row.add(-1);
            out_list.add(out_row);
            return out_list;
        }

        for (int i=0; i<game_board.size(); i++) if (game_board.get(i).get(0)==-1){
            game_ch = new ArrayList<>();
            out_row = new ArrayList<>();
            for (int j=0; j<k+2; j++) {
                if (j==1) out_row.add(i);
                else out_row.add(-1);
            }
            game_ch.add(out_row);
            int n_prim = 1, how_many_wins = 0;
            for (int color_ch=0; color_ch<k; color_ch++){
                game_ch.get(0).set(color_ch+2, n_prim);
                gameX = new ArrayList<>();
                out_row = new ArrayList<>(Arrays.asList(i, color_ch));
                for (int j=0; j<game_board.size(); j++){
                    if (i==j) gameX.add(out_row);
                    else gameX.add(game_board.get(j));
                }
                gameX_ch = check_game(gameX, move+1, m, l, k);
                int act;
                for (List<Integer> j:gameX_ch) for (int h=2; h<k+2; h++){
                    act = j.get(h);
                    if (act!=-1) j.set(h, act+n_prim);
                }
                n_prim += gameX_ch.size();
                game_ch.addAll(gameX_ch);
                how_many_wins += gameX_ch.get(0).get(0);
                if (how_many_wins>=l) break;
            }

            if (how_many_wins>=l){
                game_ch.get(0).set(0, 1);
                return game_ch;
            }
        }
        game_ch.get(0).set(0, 0);
        return game_ch;
    }

    ArrayList<ArrayList<Integer>> train(int n, int m, int l, int k){
        ArrayList<ArrayList<Integer>> game_board = new ArrayList<>();
        ArrayList<Integer> row = new ArrayList<>();
        row.add(-1);
        row.add(-1);
        for (int i=0; i<n; i++) game_board.add(row);
        return check_game(game_board, 1, m, l, k);
    }

    public Play(Globals G){
        ArrayList<ArrayList<Integer>> strategy, pola_check;
        int index=0;
        Integer[] pola = new Integer[G.n];
        Integer[] tla = new Integer[G.k];
        for (int i=0; i<G.n; i++) pola[i] = -1;
        for (int i=0; i<G.k; i++) tla[i] = inputAuto.nextInt(214)+17;
        ArrayList<Integer> kolory;
        int victor = 0, popped;

        if (G.isGoodAuto) strategy = train(G.n, G.m, G.l, G.k);
        else strategy = new ArrayList<>();

        for (int M=0; M<G.n && victor == 0; M++){
            kolory = new ArrayList<>();
            if (G.isDemo){
                table = fieldTable(pola, tla, G);
                System.out.println(table);
            }
            System.out.println("Graczu 1, podaj numer pola");
            if (G.isGoodAuto) {
                field = strategy.get(index).get(1);
                System.out.println(field+1);

                System.out.println("Podaj kolory");
                for (int K=0; K<G.l; K++) kolory.add(-1);
                int out_i = 0, color_index;
                for (int i=0; i<G.k; i++) {
                    color_index = strategy.get(index).get(i+2);
                    if (color_index > -1)
                        if (strategy.get(color_index).get(0)==1){
                            kolory.set(out_i, i);
                            out_i++;
                        }
                    if (out_i>G.l) break;
                }

                ArrayList<Integer> notin = new ArrayList<>();
                for (int i=0; i<G.k; i++) if (!kolory.contains(i)) notin.add(i);
                int y=0;
                Collections.shuffle(notin);
                for (int x=0; x<kolory.size(); x++){
                    if (kolory.get(x)==-1) {
                        kolory.set(x, notin.get(y));
                        y++;
                    }
                }
                for (int K:kolory) System.out.print(Integer.toString(K+1)+" ");
                System.out.println("");
            }
            else {
                inputText = input.nextLine();
                field = Integer.parseInt(inputText)-1;
                do {
                    if (field>=G.n || field<0) valid = false;
                    else valid = (pola[field] == -1);
                    if (!valid){
                        System.out.println("Nieprawidlowe pole! Podaj jeszcze raz numer pola");
                        inputText = input.nextLine();
                        field = Integer.parseInt(inputText)-1;
                    }
                } while (!valid);

                System.out.println("Podaj kolory");
                inputText = input.nextLine();
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(inputText);
                while (m.find()) kolory.add(Integer.parseInt(m.group())-1);
                do {
                    valid = (kolory.size()==G.l);
                    for (int K=1; K<kolory.size(); K++) for (int L=0; L<K; L++)
                        if (kolory.get(K).equals(kolory.get(L))) valid = false;
                    if (!valid){
                        kolory = new ArrayList<>();
                        System.out.print("Nieprawidlowe numery! Podaj jeszcze raz kolory");
                        inputText = input.nextLine();
                        m = p.matcher(inputText);
                        while (m.find()) kolory.add(Integer.parseInt(m.group()));
                    }
                } while (!valid);
            }

            System.out.println("Graczu 2, podaj numer koloru");
            if (G.isBadAuto){
                pola[field] = kolory.get(inputAuto.nextInt(G.l));
                System.out.println(pola[field]+1);
            }
            else {
                inputText = input.nextLine();
                inputNumber = Integer.parseInt(inputText)-1;
                do {
                    valid = false;
                    for (int i=0; i<G.l; i++)
                        if (kolory.get(i) == inputNumber) {
                            valid = true;
                            break;
                        }
                    if (!valid){
                        System.out.println("Nieprawidłowy numer! Podaj jeszcze raz numer koloru");
                        inputText = input.nextLine();
                        inputNumber = Integer.parseInt(inputText)-1;
                    }
                } while (!valid);
                pola[field] = inputNumber;
            }
            if (G.isGoodAuto){
                index = strategy.get(index).get(pola[field] + 2);
            }

            pola_check = new ArrayList<>();
            for (int i=0; i<G.n; i++)
                pola_check.add(new ArrayList<>(Arrays.asList(i, pola[i])));
            if (check_game_for_arithmetical(pola_check, G.m, G.k)) victor = 1;
        }

        table = fieldTable(pola, tla, G);
        System.out.println(table);
        if (victor==1) System.out.println("Gracz 1 wygrywa!");
        else System.out.println("Gracz 2 wygrywa!");
    }
}