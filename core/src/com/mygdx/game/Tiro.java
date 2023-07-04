package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Tiro {
    private static final float TIRO_VELOCITY = 500; // Velocidade do tiro
    private static final float TIRO_WIDTH = 10; // Largura do tiro
    private static final float TIRO_HEIGHT = 10; // Altura do tiro
    
    private Texture tiroTexture;
    private float positionX;
    private float positionY;
    private float directionX;
    private float directionY;

    public Tiro(float initialX, float initialY, float directionX, float directionY) {
        tiroTexture = new Texture("assets/tiro.png");
        positionX = initialX;
        positionY = initialY;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public void render(Batch batch) {
    batch.draw(tiroTexture, positionX, positionY, TIRO_WIDTH, TIRO_HEIGHT);
}

    public void update(float deltaTime) {
        float velocityX = TIRO_VELOCITY * directionX;
        float velocityY = TIRO_VELOCITY * directionY;
        positionX += velocityX * deltaTime;
        positionY += velocityY * deltaTime;
    }

    public void dispose() {
        tiroTexture.dispose();
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getWidth() {
        return TIRO_WIDTH;
    }

    public float getHeight() {
        return TIRO_HEIGHT;
    }
}
