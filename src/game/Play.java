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

    boolean check_for_arithmetical(Integer[] pola, int m){
        int n = pola.length;
        double Sm = n/(m-1.0);
        int kol;
        boolean won = false;
        for (int S=1; S<Sm; S++)
            for (int i=0; i+(m-1)*S<n; i++) if (pola[i] > -1){
                valid = true;
                kol = pola[i];
                for (int x=0; x<m; x++) if (pola[i+x*S] != kol) valid = false;
                if (valid) won = true;
            }
        return won;
    }

    boolean check_game_for_arithmetical(List<List<Integer>> game_board, int m, int k){
        List<List<Integer>> game_sort = game_board;
        List<Integer> game_color = new ArrayList<>();
        game_sort.sort(Comparator.comparing(o -> o.get(0)));
        for (int cgfa=0; cgfa<k; cgfa++){
            for (int s=0; s<game_sort.size(); s++)
                if (game_sort.get(s).get(1)==cgfa) game_color.add(game_sort.get(s).get(0));
            if (game_color.size()<3) continue;
            if (check_for_arithmetical(game_color.toArray(new Integer[game_color.size()]), m)) return true;
        }
        return false;
    }

    String fieldTable(Integer[] pola, Integer[] tla, Globals G){
        table="";
        String fore;
        for (int i=0; i<G.n; i++){
            if (pola[i] == -1) table = table+"[ ]";
            else {
                fore = (tla[pola[i]]-16)%36<18?"231":"16";
                table = table+"[\033[38;5;"+fore+";48;5;"+Integer.toString(tla[pola[i]])+"m"+Integer.toString(pola[i]+1)+"\033[0m]";
            }
        }
        return table;
    }

    List<List<Integer>> check_game(List<List<Integer>> game_board, int move, int m, int l, int k){
        List<List<Integer>> out_list = new ArrayList<>(), game_ch = new ArrayList<>();
        List<Integer> out_row = new ArrayList<>();
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

        for (int i=0; i<game_board.size(); i++) if (game_board.get(i).get(0)==0){
            game_ch = new ArrayList<>();
            game_ch.add(new ArrayList<>());
            for (int j=0; j<k+2; j++) game_ch.get(0).add(-1);
            game_ch.get(0).set(1, i);
            int n_prim = 1, how_many_wins = 0;
            for (int color_ch=0; color_ch<k; color_ch++){
                game_ch.get(0).set(color_ch+2, n_prim+1);
                List<List<Integer>> gameX = game_board;
                gameX.set(i, new ArrayList<>(Arrays.asList(i, color_ch)));
                List<List<Integer>> gameX_ch = check_game(gameX, move+1, m, l, k);
                int act;
                for (int j=0; j<gameX_ch.size(); j++){
                    for (int h=2; h<k+2; h++){
                        act = gameX_ch.get(j).get(h);
                        if (act!=-1) gameX_ch.get(j).set(h, act+n_prim);
                    }
                }
                n_prim += gameX_ch.size();
                game_ch.addAll(gameX_ch);
                how_many_wins += gameX_ch.get(0).get(0);
                if(how_many_wins>=l) break;
            }

            if (how_many_wins>=l){
                game_ch.get(0).set(0, 1);
                return game_ch;
            }
        }
        game_ch.get(0).set(0, 0);
        return game_ch;
    }

    List<List<Integer>> train(int n, int m, int l, int k){
        List<List<Integer>> game_board = new ArrayList<>();
        List<Integer> row = new ArrayList<>(Arrays.asList(0, 0));
        for (int i=0; i<n; i++) game_board.add(row);
        List<List<Integer>> strategy = check_game(game_board, 1, m, l, k);
        return strategy;
    }

    public Play(Globals G){
        List<List<Integer>> strategy = train(G.n, G.m, G.l, G.k);
        int index=0;
        Integer[] pola = new Integer[G.n];
        Integer[] tla = new Integer[G.k];
        for (int i=0; i<G.n; i++) pola[i] = -1;
        for (int i=0; i<G.k; i++) tla[i] = inputAuto.nextInt(214)+17;
        LinkedList<Integer> kolory;
        int victor = 0, popped;

        for (int M=0; M<G.n && victor == 0; M++){
            kolory = new LinkedList<Integer>();
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
                    if (color_index > -1){
                        if (strategy.get(color_index).get(0)==1){
                            kolory.set(out_i, i);
                            out_i++;
                        }
                    }
                    if (out_i>G.l) break;
                }

                List<Integer> notin = new ArrayList<>();
                for (int i=0; i<G.k; i++) if (!kolory.contains(i)) notin.add(i);
                int x=0;
                for (int i=0; i<notin.size(); i++){
                    while (kolory.get(x)>-1) x++;
                    kolory.set(x, notin.get(i));
                }
                for (int K=0; K<G.l; K++) System.out.print(Integer.toString(kolory.get(K)+1)+" ");
                System.out.println("");
            }
            else {
                inputText = input.nextLine();
                field = Integer.parseInt(inputText)-1;
                do {
                    if (field>=G.n || field<0) valid = false;
                    else if (pola[field] > -1) valid = false;
                    else valid = true;
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
                    valid = true;
                    if (kolory.size()!=G.l) valid = false;
                    for (int K=1; K<kolory.size(); K++)
                        for (int L=0; L<K; L++)
                            if (kolory.get(K) == kolory.get(L)) valid = false;
                    if (!valid){
                        kolory = new LinkedList<Integer>();
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
                    for (int i=0; i<G.l; i++) if (kolory.get(i) == inputNumber) valid = true;
                    if (!valid){
                        System.out.println("Nieprawidlowy numer! Podaj jeszcze raz numer koloru");
                        inputText = input.nextLine();
                        inputNumber = Integer.parseInt(inputText)-1;
                    }
                } while (!valid);
                pola[field] = inputNumber;
            }
            index = strategy.get(index).get(pola[field]+2);

            if (check_for_arithmetical(pola, G.m)) victor = 1;
        }

        table = fieldTable(pola, tla, G);
        System.out.println(table);
        if (victor==1) System.out.println("Gracz 1 wygrywa!");
        else System.out.println("Gracz 2 wygrywa!");
    }
}