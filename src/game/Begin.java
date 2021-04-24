package game;

import java.util.Scanner;

public class Begin {
    Scanner input = new Scanner(System.in);
    String inputText;

    int n, k, m, l;
    boolean isAuto, isDemo;

    public Begin() {
        System.out.println("Witaj, epicki graczu!! :D");
        setIsAuto();
        setNof('n');
        setNof('k');
        setNof('m');
        setNof('l');
        setIsDemo();

        Main.globals = new Globals(n, k, m, l, isAuto, isDemo);
    }

    private void setIsAuto(){
        System.out.println("Masz kolege z ktorym pragniesz zagrac? Czy moze wolalbys ujrzec symulacje komputerowa?");
        System.out.println("Wpisz: \"1vs1\" dla rozgrywki z kumplem badz \"comp\" dla przeprowadzenia gry automatycznej");
        inputText = input.nextLine();
        while(!inputText.equals("1vs1") & !inputText.equals("comp")){
            System.out.println("Wpisz jedna z dwoch wartosci: 1vs1, comp");
            inputText = input.nextLine();
        }
        isAuto = inputText.equals("comp");
    }

    private void setNof(char w){
        String whichInput;
        int inputNumber;
        boolean shouldBreak = false;

        switch (w) {
            case 'n':
                whichInput = "pol (pozycji do zakolorowania)";
                break;
            case 'k':
                whichInput = "kolorow dostepnych w grze";
                break;
            case 'm':
                whichInput = "pol w ciagu arytmetycznym o tej samej barwie, ku zwyciestwu gracza pierwszego (nie bialej)";
                break;
            default: // case 'l':
                whichInput = "kolorow dostepnych do kolorowania przez gracza drugiego w kazdej turze";
                break;
        }

        while(!shouldBreak) {
            System.out.print("Wpisz prosze, ile w grze ma byc ");
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
                                System.out.println("Liczba jest zbyt duza!! Gra niemozliwa do wygrania przez gracza drugiego.");
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
                                System.out.println("Liczba jest zbyt duża!!Nie ma tyle kolorów w grze.");
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
        System.out.println("No dobrze, ale czy masz czas i serce do ogladania calej rozgrywki?");
        System.out.println("Jeśli tak - spróbuj trybu \"demo\"! W przeciwnym wypadku doskonały dla Ciebie będzie \"test\" :)");
        System.out.println("Wpisz: \"demo\" lub \"test\"");
        inputText = input.nextLine();
        while(!inputText.equals("demo") & !inputText.equals("test")){
            System.out.println("Wpisz jedna z dwoch wartosci: demo, test");
            inputText = input.nextLine();
        }
        isDemo = inputText.equals("demo");
    }

    private void notNaturalError(){
        System.out.println("Musisz wprowadzić liczbę naturalną!");
    }
}
