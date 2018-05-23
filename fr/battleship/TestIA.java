package fr.battleship;

import kueny.alexandre.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;
import java.io.FileWriter;

public class TestIA {

    public static void main(String[] args) {
        List<Hashtable<String, Integer>> scores = new ArrayList<>();
        scores.add(new Hashtable<>());
        scores.add(new Hashtable<>());
        scores.add(new Hashtable<>());

        Game game = new Game(1, 2);
        for (int i = 0; i < 100; i++) {
            game.play();
            String winner = game.winner().getClass().getSimpleName();
            try {
                scores.get(0).put(winner, scores.get(0).get(winner) + 1);
            } catch (NullPointerException e) {
                scores.get(0).put(winner, 1);
            }
            game.reset();
        }
        scores.get(0).putIfAbsent(game.getCurrentPlayer().getClass().getSimpleName(), 0);
        scores.get(0).putIfAbsent(game.getOtherPlayer().getClass().getSimpleName(), 0);

        game = new Game(1, 3);
        for (int i = 0; i < 100; i++) {
            game.play();
            String winner = game.winner().getClass().getSimpleName();
            try {
                scores.get(1).put(winner, scores.get(1).get(winner) + 1);
            } catch (NullPointerException e) {
                scores.get(1).put(winner, 1);
            }
            game.reset();
        }
        scores.get(1).putIfAbsent(game.getCurrentPlayer().getClass().getSimpleName(), 0);
        scores.get(1).putIfAbsent(game.getOtherPlayer().getClass().getSimpleName(), 0);

        game = new Game(2, 3);
        for (int i = 0; i < 100; i++) {
            game.play();
            String winner = game.winner().getClass().getSimpleName();
            try {
                scores.get(2).put(winner, scores.get(2).get(winner) + 1);
            } catch (NullPointerException e) {
                scores.get(2).put(winner, 1);
            }
            game.reset();
        }
        scores.get(2).putIfAbsent(game.getCurrentPlayer().getClass().getSimpleName(), 0);
        scores.get(2).putIfAbsent(game.getOtherPlayer().getClass().getSimpleName(), 0);

        System.out.println(scores);

        try {
            FileWriter file = new FileWriter("ai_proof.csv");

            file.append("AI Name; score; AI Name2; score2\n");
            for (Hashtable<String, Integer> h : scores) {
                for (String k : h.keySet()) {
                    file.append(k + ";" + h.get(k) + ";");
                }
                file.append("\n");
            }
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
