package game;

import java.util.Scanner;

public class Begin {
    Scanner input = new Scanner(System.in);
    String inputText;

    int n, k, m, l;
    boolean isGoodAuto, isBadAuto, isDemo;

    public Begin() {
        System.out.println("Witaj, epicki graczu!! :D");
        setIsAuto();
        setNof('n');
        setNof('k');
        setNof('m');
        setNof('l');
        setIsDemo();

        Main.globals = new Globals(n, k, m, l, isGoodAuto, isBadAuto, isDemo);
    }

    private void setIsAuto(){
        System.out.println("Masz kolegę, z którym pragniesz zagrać? Czy może wolałbyś ujrzeć symulację komputerową?");
        System.out.println("Wpisz: \"1vs1\" dla rozgrywki z kumplem, \"p1\" dla gry z komputerem jako gracz 1,\n"+
                "\"p2\" dla gry z komputerem jako gracz 2 lub \"comp\" dla przeprowadzenia gry automatycznej");
        inputText = input.nextLine();
        while(!inputText.equals("1vs1") & !inputText.equals("p1") & !inputText.equals("p2") & !inputText.equals("comp")){
            System.out.println("Wpisz jedną z czterech wartości: 1vs1, p1, p2, comp");
            inputText = input.nextLine();
        }
        isGoodAuto = inputText.equals("comp") | inputText.equals("p2");
        isBadAuto = inputText.equals("comp") | inputText.equals("p1");
    }

    private void setNof(char w){
        String whichInput;
        int inputNumber;
        boolean shouldBreak = false;

        switch (w) {
            case 'n':
                whichInput = "pól (pozycji do zakolorowania)";
                break;
            case 'k':
                whichInput = "kolorów dostępnych w grze";
                break;
            case 'm':
                whichInput = "pól w ciągu arytmetycznym o tej samej barwie, ku zwycięstwu gracza pierwszego (nie białej)";
                break;
            default: // case 'l':
                whichInput = "kolorów dostępnych do kolorowania przez gracza drugiego w każdej turze";
                break;
        }

        while(!shouldBreak) {
            System.out.print("Wpisz proszę, ile w grze ma być ");
            System.out.println(whichInput);

            inputText = input.nextLine();
            switch (w) {
                case 'n':
                case 'k':
                    try {
                        inputNumber = Integer.parseInt(inputText);
                        if (inputNumber < 0)
                            notNaturalError();
                        else {
                            if (w == 'n')
                                n = inputNumber;
                            else
                                k = inputNumber;
                            shouldBreak = true;
                        }
                    } catch (NumberFormatException e) {
                        notNaturalError();
                    }
                    break;
                case 'm':
                    try {
                        inputNumber = Integer.parseInt(inputText);
                        if (inputNumber < 0)
                            notNaturalError();
                        else {
                            if (inputNumber > n / 2)
                                System.out.println("Liczba jest zbyt duża!! Gra niemożliwa do wygrania przez gracza drugiego.");
                            else {
                                m = inputNumber;
                                shouldBreak = true;
                            }
                        }
                    } catch (NumberFormatException e) {
                        notNaturalError();
                    }
                    break;
                case 'l':
                    try {
                        inputNumber = Integer.parseInt(inputText);
                        if (inputNumber < 0)
                            notNaturalError();
                        else {
                            if (inputNumber > k)
                                System.out.println("Liczba jest zbyt duża!! Nie ma tyle kolorów w grze.");
                            else {
                                l = inputNumber;
                                shouldBreak = true;
                            }
                        }
                    } catch (NumberFormatException e) {
                        notNaturalError();
                    }
                    break;
            }
            // System.out.println(n + " " + k + " " + m + " " + l);
        }
    }

    private void setIsDemo(){
        System.out.println("No dobrze, ale czy masz czas i serce do oglądania całej rozgrywki?");
        System.out.println("Jeśli tak - spróbuj trybu \"demo\"! W przeciwnym wypadku doskonały dla Ciebie będzie \"test\" :)");
        System.out.println("Wpisz: \"demo\" lub \"test\"");
        inputText = input.nextLine();
        while(!inputText.equals("demo") & !inputText.equals("test")){
            System.out.println("Wpisz jedną z dwóch wartości: demo, test");
            inputText = input.nextLine();
        }
        isDemo = inputText.equals("demo");
    }

    private void notNaturalError(){
        System.out.println("Musisz wprowadzić liczbę naturalną!");
    }
}
