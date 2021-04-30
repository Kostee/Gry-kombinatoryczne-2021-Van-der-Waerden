package game;

import java.util.*;

public class Play {
    Scanner input = new Scanner(System.in);
    Random inputAuto = new Random();
    String inputText;
    String table;
    int field;
    int inputNumber;
    boolean valid;

    String fieldTable(Integer[] pola, Globals G){
        table="";
        for (int i=0; i<G.n; i++){
            table = table+"[";
            if (pola[i] == -1) table = table+" ";
            else table = table+Integer.toString(pola[i]+1);
            table = table+"]";
        }
        return table;
    }

    public Play(Globals G){
        Integer[] pola = new Integer[G.n];
        for (int i=0; i<G.n; i++) pola[i] = -1;
        Integer[] kolory = new Integer[G.l];
        for (int i=0; i<G.l; i++) kolory[i] = -1;
        int victor = 0;

        for (int M=0; M<G.n && victor == 0; M++){
            if (G.isDemo){
                table = fieldTable(pola, G);
                System.out.println(table);
            }
            System.out.println("Graczu 1, podaj numer pola");
            if (G.isAuto) {
                field = inputAuto.nextInt(G.n);
                while (pola[field] > -1) field = inputAuto.nextInt(G.n);
                System.out.println(field+1);

                System.out.println("Podaj kolory");
                List<Integer> lista = new ArrayList<Integer>();
                for (int K=0; K<G.k; K++) lista.add(K);
                Collections.shuffle(lista);
                for (int K=0; K<G.k-G.l; K++) lista.remove(0);
                kolory = lista.toArray(kolory);
                for (int K=0; K<G.l; K++) System.out.print(Integer.toString(kolory[K]+1)+" ");
                System.out.println("");

                System.out.println("Graczu 2, podaj numer koloru");
                pola[field] = kolory[inputAuto.nextInt(G.l)];
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

                for (int K=0; K<G.l; K++){
                    System.out.print("Podaj kolor nr ");
                    System.out.println(K+1);
                    inputText = input.nextLine();
                    inputNumber = Integer.parseInt(inputText)-1;
                    do {
                        valid = true;
                        if (inputNumber>=G.k || inputNumber<0) valid = false;
                        for (int K2=0; K2<K; K2++) if (kolory[K2] == inputNumber) valid = false;
                        if (!valid){
                            System.out.print("Nieprawidlowy kolor! Podaj jeszcze raz kolor nr ");
                            System.out.println(K+1);
                            inputText = input.nextLine();
                            inputNumber = Integer.parseInt(inputText)-1;
                        }
                    } while (!valid);
                    kolory[K] = inputNumber;
                }

                System.out.println("Graczu 2, podaj numer koloru");
                inputText = input.nextLine();
                inputNumber = Integer.parseInt(inputText)-1;
                do {
                    valid = false;
                    for (int i=0; i<G.l; i++) if (kolory[i] == inputNumber) valid = true;
                    if (!valid){
                        System.out.println("Nieprawidlowy numer! Podaj jeszcze raz numer koloru");
                        inputText = input.nextLine();
                        inputNumber = Integer.parseInt(inputText)-1;
                    }
                } while (!valid);
                pola[field] = inputNumber;
            }

            /*for (int back=2; back<=field && victor == 0; back+=2)
                if (pola[field] == pola[field-back/2] && pola[field] == pola[field-back]) victor = 1;
            for (int front=2; front<=G.n-1-field && victor == 0; front+=2)
                if (pola[field] == pola[field+front/2] && pola[field] == pola[field+front]) victor = 1;
            int border = Math.min(G.n-1-field, field);
            for (int two=1; two<=border; two++)
                if (pola[field] == pola[field-two] && pola[field] == pola[field+two]) victor = 1;*/

            int Sm = G.n/(G.m-1);
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

        table = fieldTable(pola, G);
        System.out.println(table);
        if (victor==1) System.out.println("Gracz 1 wygrywa!");
        else System.out.println("Gracz 2 wygrywa!");
    }
}
