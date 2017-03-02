package edu.lancs.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static edu.lancs.game.Constants.HIGHSCORES_URL;
import static edu.lancs.game.Constants.HIGHSCORES_USER_AGENT;

public class HighscoresUpdater implements Runnable {
    private String name;
    private String difficulty;
    private int score;
    private int level;
    private int kills;
    private long time;
    private boolean hasUpdated;

    public HighscoresUpdater(String name, int score, long time, int level, int kills, String difficulty) {
        this.name = name;
        this.score = score;
        this.time = time;
        this.level = level;
        this.kills = kills;
        this.difficulty = difficulty;
        hasUpdated = false;
    }

    @Override
    public synchronized void run() {
        synchronized (this) {
            if (!hasUpdated) {
                updateHighscores(name, score, time, level, kills, difficulty);
                hasUpdated = true;
            }
        }
    }

    /***
     * Sends a GET request to the HighScores server using game information (Score, time, name).
     */
    public synchronized void updateHighscores(String name, int score, long time, int level, int kills, String difficulty) {
        synchronized (this) {
            try {
                if (!hasUpdated) {
                    hasUpdated = true;
                    String url = HIGHSCORES_URL.replace("#", name).replace("~", Integer.toString(score)).replace("@", Long.toString(time)).replace("*", Integer.toString(level)).replace("£", Integer.toString(kills)).replace("!", difficulty);

                    URL obj = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                    // optional default is GET
                    con.setRequestMethod("GET");

                    // add request header
                    con.setRequestProperty("User-Agent", HIGHSCORES_USER_AGENT);

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    Debug.print("High scores server returned: " + response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateHighscores() {
        updateHighscores(name, score, time, level, kills, difficulty);
    }
}
