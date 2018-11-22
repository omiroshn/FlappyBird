package com.flappybird.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
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
        private Rectangle topRect;
        private Rectangle botRect;
        private int speed;
        private boolean checked;

        Wall(int offset) {
            loadTextures();
            topRect = new Rectangle();
            botRect = new Rectangle();
            top = new Vector2(START_X + offset, getRandomHeight());
            bottom = new Vector2(START_X + offset, top.y - SPACE_BETWEEN_WALLS - WALL_HEIGHT);
            wh = new Vector2(WALL_WIDTH, WALL_HEIGHT);
            updateRect();
            speed = 4;
            checked = false;
        }

        private void update() {
            top.x -= speed;
            bottom.x -= speed;
            updateRect();
            if (top.x <= -WALL_WIDTH) {
                top = new Vector2(START_X + DISTANCE_BTW_WALLS, getRandomHeight());
                bottom = new Vector2(START_X + DISTANCE_BTW_WALLS, top.y - SPACE_BETWEEN_WALLS - WALL_HEIGHT);
                checked = false;
            }
            updateRect();
        }

        private void updateRect() {
            topRect.set(
                    top.x,
                    top.y,
                    wh.x,
                    wh.y + 1000
            );
            botRect.set(
                    bottom.x,
                    bottom.y,
                    wh.x,
                    wh.y
            );
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

        public Rectangle getTopRect() {
            return topRect;
        }

        public Rectangle getBotRect() {
            return botRect;
        }

        private boolean isNotChecked() {
            return !checked;
        }

        private float getPosX() {
            return top.x;
        }

    }

    public void default_() {
        int offset = 300;
        for (int i = 0; i < wallPair.length; i++) {
            wallPair[i] = new Wall(offset);
            offset += DISTANCE_BTW_WALLS;
        }
    }

    private Wall wallPair[];

    public Obstacles() {
        wallPair = new Wall[NUMBER_OF_WALLS];
        default_();
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

    public boolean checkIntersection(MyGDXGame game, Bird bird) {
        Circle c = bird.getCircle();
        float x = bird.getPos().x;
        for (Wall wall: wallPair) {
            if (wall.isNotChecked() && (x > wall.getPosX())) {
                wall.checked = true;
                game.setScore(game.getScore() + 1);
            }
            if (Intersector.overlaps(c, wall.getBotRect())
                    || Intersector.overlaps(c, wall.getTopRect()))
                return true;
        }
        return false;
    }

    public void dispose() {
        for (Wall wall: wallPair) {
            wall.dispose();
        }
    }

    public Wall[] getWallPair() { return wallPair; }

    public static int getHeight() {
        return WALL_HEIGHT;
    }

    public static int getWidth() {
        return WALL_WIDTH;
    }

}
