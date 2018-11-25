package com.flappybird.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.flappybird.View.MyGDXGame;

public class Music {
    private Sound scoredPoint;
    private Sound hit;
    private Sound die;

    Music() {
        scoredPoint = Gdx.audio.newSound(Gdx.files.internal("audio/point.ogg"));
        hit = Gdx.audio.newSound(Gdx.files.internal("audio/hit.ogg"));
        die = Gdx.audio.newSound(Gdx.files.internal("audio/die.ogg"));
    }

    public void playHitSound() {
        hit.play(MyGDXGame.VOLUME);
    }

    public void playPointSound() {
        scoredPoint.play(MyGDXGame.VOLUME - 0.1f);
    }

    public void playDieSound() {
        die.play(MyGDXGame.VOLUME);
    }

    public void dispose() {
        scoredPoint.dispose();
        hit.dispose();
        die.dispose();
    }
}
