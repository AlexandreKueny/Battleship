package kueny.alexandre;

import java.util.Scanner;

public class Battleship {

    public static void main(String[] args) {
        Game game;
        int mode;

        Scanner sc = new Scanner(System.in);

        System.out.println();

        do {
            System.out.println("Choose game mode : 1 (Human vs Human) 2 (Human vs AI) 3 (AI vs AI)");
            try {
                mode = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                mode = 0;
            }
        } while (mode != 1 && mode != 2 && mode != 3);

        switch (mode) {
            case 1:
                String name1, name2;
                System.out.println("Player 1, enter your name");
                name1 = sc.nextLine();
                System.out.println("Player 2, enter your name");
                name2 = sc.nextLine();
                game = new Game(name1, name2);
                break;
            case 2:
                String name;
                int level;
                System.out.println("Player, enter your name");
                name = sc.nextLine();
                do {
                    System.out.println("Choose AI level : 1 (Easy) 2 (Intermediate) 3 (Expert)");
                    try {
                        level = Integer.parseInt(sc.nextLine());
                    } catch (Exception e) {
                        level = 0;
                    }
                } while (level != 1 && level != 2 && level != 3);
                game = new Game(name, level);
                break;
            case 3:
                int level1, level2;
                do {
                    System.out.println("Choose AI 1 level : 1 (Easy) 2 (Intermediate) 3 (Expert)");
                    try {
                        level1 = Integer.parseInt(sc.nextLine());
                    } catch (Exception e) {
                        level1 = 0;
                    }
                } while (level1 != 1 && level1 != 2 && level1 != 3);
                do {
                    System.out.println("Choose AI 2 level : 1 (Easy) 2 (Intermediate) 3 (Expert)");
                    try {
                        level2 = Integer.parseInt(sc.nextLine());
                    } catch (Exception e) {
                        level2 = 0;
                    }
                } while (level2 != 1 && level2 != 2 && level2 != 3);
                game = new Game(level1, level2);
                break;
            default:
                game = null;
        }

        do {
            game.play();
            game.reset();
            System.out.println("Do you want to replay? y/n");
        } while (sc.nextLine().equals("y"));
    }


}