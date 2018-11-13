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
        private Vector2 pos1;
        private Vector2 pos2;
        private Vector2 wh;
        private int speed;

        Wall(int offset) {
            loadTextures();
            pos1 = new Vector2(START_X + offset, getRandomHeight());
            pos2 = new Vector2(START_X + offset, pos1.y - SPACE_BETWEEN_WALLS - WALL_HEIGHT);
            wh = new Vector2(WALL_WIDTH, WALL_HEIGHT);
            speed = 4;
        }

        private void update() {
            pos1.x -= speed;
            pos2.x -= speed;
            if (pos1.x <= -WALL_WIDTH) {
                pos1 = new Vector2(START_X + DISTANCE_BTW_WALLS, getRandomHeight());
                pos2 = new Vector2(START_X + DISTANCE_BTW_WALLS, pos1.y - SPACE_BETWEEN_WALLS - WALL_HEIGHT);
            }
        }

        private void draw() {
            MyGDXGame.batch.draw(
                    wall1,
                    pos1.x,
                    pos1.y,
                    wh.x,
                    wh.y
            );
            MyGDXGame.batch.draw(
                    wall2,
                    pos2.x,
                    pos2.y,
                    wh.x,
                    wh.y
            );
        }

        private void dispose() {
            atlas.dispose();
        }

        private void loadTextures() {
            atlas = new TextureAtlas(Gdx.files.internal("wall.atlas"));
            wall1 = atlas.findRegion("0001");
            wall2 = atlas.findRegion("0002");
        }

        private int getRandomHeight() {
            int lower = Ground.getHeight() + SPACE_BETWEEN_WALLS;
            int upper = Gdx.graphics.getHeight()  - (Ground.getHeight() / 2);
            return new Random().nextInt(upper - lower) + lower;
        }

        public Vector2 getPos1() {
            return pos1;
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
        for (Wall wall: wallPair) {
            wall.update();
        }
    }

    public boolean checkIntersection(Bird bird) {

        Wall closest = getClosestWall();

        if (checkFront(bird, closest) ||
                checkTop(bird, closest) ||
                checkBottom(bird, closest) ||
                checkBack(bird, closest))
        {
            return true;
        }

        return false;
    }

    private boolean checkFront(Bird bird, Wall closest) {
        if (bird.getPos().x + Bird.getWidth() >= closest.pos1.x &&
                bird.getPos().x + Bird.getWidth() <= closest.pos1.x + WALL_WIDTH) {
            return true;
        }
        return false;
    }

    private boolean checkBack(Bird bird, Wall closest) {
        if (bird.getPos().x >= closest.pos1.x &&
                bird.getPos().x <= closest.pos1.x + WALL_WIDTH) {
            return true;
        }
        return false;
    }

    private boolean checkTop(Bird bird, Wall closest) {
        if (bird.getPos().y + Bird.getHeight() >= closest.pos1.y &&
                bird.getPos().y + Bird.getHeight() >= closest.pos1.y + WALL_HEIGHT) {
            return true;
        }
        return false;
    }

    private boolean checkBottom(Bird bird, Wall closest) {
        if (bird.getPos().y >= closest.pos1.y &&
                bird.getPos().y >= closest.pos1.y + WALL_HEIGHT) {
            return true;
        }
        return false;
    }

    public void dispose() {
        for (Wall wall: wallPair) {
            wall.dispose();
        }
    }

    public Wall getClosestWall() {
        int birdPos = Bird.getPosX();
        float minPos = 2000;
        for (Wall wall: wallPair) {
            if (wall.pos1.x < minPos && wall.pos1.x >= birdPos) {
                minPos = wall.pos1.x;
            }
        }
        for (Wall wall: wallPair) {
            if (minPos == wall.pos1.x) {
                return wall;
            }
        }
        return null;
    }

    public static int getHeight() {
        return WALL_HEIGHT;
    }

    public static int getWidth() {
        return WALL_WIDTH;
    }

}
