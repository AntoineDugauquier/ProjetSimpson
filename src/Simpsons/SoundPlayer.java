package Simpsons;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class SoundPlayer {

    private InputStream inputStream;

    private Player player;
    private boolean repeat, paused;
    private long pauseLocation, totalSongLength;
    private String name;

    public SoundPlayer(String name, boolean repeat) {
        this.name = name;
        this.repeat = repeat;
    }

    SoundPlayer(String victorySoundmp3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void play() {
        inputStream = this.getClass().getResourceAsStream("/resources/" + name);
//        System.out.println("Hello :" + inputStream);
        try {
            totalSongLength = inputStream.available();
        } catch (IOException ex) {
            Logger.getLogger(SoundPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            player = new Player(inputStream);
        } catch (JavaLayerException ex) {
            Logger.getLogger(SoundPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }

        new Thread() {
            @Override
            public void run() {
                try {
                    player.play();

                    if (player.isComplete() && repeat) {
                        play();
                    }
                } catch (JavaLayerException ex) {
                    System.err.println("There was an error to play /resources/" + name);
                }
            }
        }.start();
    }

    public void resume() {
        try {
            paused = false;
            inputStream = this.getClass().getResourceAsStream("/resources/" + name);
            try {
                inputStream.skip(totalSongLength - pauseLocation);
            } catch (IOException ex) {
                Logger.getLogger(SoundPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
            player = new Player(inputStream);
            new Thread() {
                @Override
                public void run() {
                    try {
                        player.play();
                    } catch (JavaLayerException ex) {
                        System.err.println("There was an error to resume " + "/resources/" + name);
                    }
                }
            }.start();
        } catch (JavaLayerException ex) {
            Logger.getLogger(SoundPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stop() {
        paused = false;
        if (null != player) {
            player.close();

            totalSongLength = 0;
            pauseLocation = 0;
        }
    }

    public void pause() {
        paused = true;
        if (null != player) {
            try {
                pauseLocation = inputStream.available();
                player.close();
            } catch (IOException ex) {
                System.out.println("Error when song is paused");
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
