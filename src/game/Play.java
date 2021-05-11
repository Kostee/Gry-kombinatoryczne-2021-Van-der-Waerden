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

    public Play(Globals G){
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
            if (G.isAuto) {
                field = inputAuto.nextInt(G.n);
                while (pola[field] > -1) field = inputAuto.nextInt(G.n);
                System.out.println(field+1);

                System.out.println("Podaj kolory");
                for (int K=0; K<G.k; K++) kolory.add(K);
                Collections.shuffle(kolory);
                for (int K=G.l; K<G.k; K++) popped = kolory.pop();
                for (int K=0; K<G.l; K++) System.out.print(Integer.toString(kolory.get(K)+1)+" ");
                System.out.println("");

                System.out.println("Graczu 2, podaj numer koloru");
                pola[field] = kolory.get(inputAuto.nextInt(G.l));
                System.out.println(pola[field]+1);
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

                System.out.println("Graczu 2, podaj numer koloru");
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

            double Sm = G.n/(G.m-1.0);
            int kol;
            boolean won = false;
            for (int S=1; S<Sm; S++)
                for (int i=0; i+(G.m-1)*S<G.n; i++) if (pola[i] > -1){
                    valid = true;
                    kol = pola[i];
                    for (int x=0; x<G.m; x++) if (pola[i+x*S] != kol) valid = false;
                    if (valid) won = true;
                }
            if (won) victor = 1;
        }

        table = fieldTable(pola, tla, G);
        System.out.println(table);
        if (victor==1) System.out.println("Gracz 1 wygrywa!");
        else System.out.println("Gracz 2 wygrywa!");
    }
}
