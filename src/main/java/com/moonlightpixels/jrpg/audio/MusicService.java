package com.moonlightpixels.jrpg.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

public final class MusicService {
    private static Music currentSong;
    private static FileHandle currentSongFileHandle;

    private MusicService() { }

    public static void playMusic(final FileHandle songFileHandle) {
        if (songFileHandle != null && !songFileHandle.equals(currentSongFileHandle)) {
            stopMusic();
            startMusic(songFileHandle);
        }
    }

    private static void startMusic(final FileHandle songFileHandle) {
        currentSongFileHandle = songFileHandle;
        currentSong = Gdx.audio.newMusic(songFileHandle);
        currentSong.setLooping(true);
        currentSong.play();

    }

    public static void stopMusic() {
        if (currentSong != null) {
            currentSong.stop();
            currentSong.dispose();
            currentSong = null;
            currentSongFileHandle = null;
        }
    }
}

