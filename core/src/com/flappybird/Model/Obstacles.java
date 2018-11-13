package com.flappybird.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.flappybird.View.MyGDXGame;

import java.util.Random;

public class Obstacles implements Drawable {

    private final static int WALL_WIDTH = 104;
    private final static int WALL_HEIGHT = 640;
    private final int SPACE_BETWEEN_WALLS = 200;
    private final float START_X = Gdx.graphics.getWidth();
    private final float DISTANCE_BTW_WALLS = 300;
    private final int NUMBER_OF_WALLS = 3;

    public class Wall {
        private TextureRegion wall1;
        private TextureRegion wall2;
        private TextureAtlas atlas;
        private Vector2 top;
        private Vector2 bottom;
        private Vector2 wh;
        private int speed;

        Wall(int offset) {
            loadTextures();
            top = new Vector2(START_X + offset, getRandomHeight());
            bottom = new Vector2(START_X + offset, top.y - SPACE_BETWEEN_WALLS - WALL_HEIGHT);
            wh = new Vector2(WALL_WIDTH, WALL_HEIGHT);
            speed = 4;
        }

        private void update() {
            top.x -= speed;
            bottom.x -= speed;
            if (top.x <= -WALL_WIDTH) {
                top = new Vector2(START_X + DISTANCE_BTW_WALLS, getRandomHeight());
                bottom = new Vector2(START_X + DISTANCE_BTW_WALLS, top.y - SPACE_BETWEEN_WALLS - WALL_HEIGHT);
            }
        }

        private void draw() {
            MyGDXGame.batch.draw(
                    wall1,
                    top.x,
                    top.y,
                    wh.x,
                    wh.y
            );
            MyGDXGame.batch.draw(
                    wall2,
                    bottom.x,
                    bottom.y,
                    wh.x,
                    wh.y
            );
        }

        private void dispose() {
            atlas.dispose();
        }

        private void loadTextures() {
            atlas = new TextureAtlas(Gdx.files.internal("atl/wall.atlas"));
            wall1 = atlas.findRegion("0001");
            wall2 = atlas.findRegion("0002");
        }

        private int getRandomHeight() {
            int lower = Ground.getHeight() + SPACE_BETWEEN_WALLS;
            int upper = Gdx.graphics.getHeight()  - (Ground.getHeight() / 2);
            return new Random().nextInt(upper - lower) + lower;
        }
    }

    private Wall wallPair[];

    public Obstacles() {
        wallPair = new Wall[NUMBER_OF_WALLS];
        int offset = 300;
        for (int i = 0; i < wallPair.length; i++) {
            wallPair[i] = new Wall(offset);
            offset += DISTANCE_BTW_WALLS;
        }
    }

    public void draw() {
        for (Wall wall: wallPair) {
            wall.draw();
        }
    }

    public void update() {
        if (MyGDXGame.getGameMode() == GameMode.GAME) {
            for (Wall wall : wallPair) {
                wall.update();
            }
        }
    }

    public boolean checkIntersection(Bird bird) {
        for (Wall wall: wallPair) {
            if (isInside(bird, wall))
                return true;
        }
        return false;
    }

    private boolean isInside(Bird bird, Wall closest) {
        if (checkFront(bird, closest) || checkBack(bird, closest)) {
            if (checkTop(bird, closest) || checkBottom(bird, closest))
                return true;
        }
        return false;
    }

    private boolean checkTop(Bird bird, Wall closest) {
        return (bird.getPos().y + Bird.getHeight() >= closest.top.y);
    }

    private boolean checkBottom(Bird bird, Wall closest) {
        return (bird.getPos().y <= closest.bottom.y + WALL_HEIGHT);
    }

    private boolean checkFront(Bird bird, Wall closest) {
        return (bird.getPos().x + Bird.getWidth() >= closest.top.x
                && bird.getPos().x + Bird.getWidth() <= closest.top.x + WALL_WIDTH);
    }

    private boolean checkBack(Bird bird, Wall closest) {
        return (bird.getPos().x >= closest.top.x && bird.getPos().x <= closest.top.x + WALL_WIDTH);
    }

    public void dispose() {
        for (Wall wall: wallPair) {
            wall.dispose();
        }
    }

    public static int getHeight() {
        return WALL_HEIGHT;
    }

    public static int getWidth() {
        return WALL_WIDTH;
    }

}
