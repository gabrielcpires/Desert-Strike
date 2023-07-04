package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Helice {
    private Texture[] heliceTextures;
    private float positionX;
    private float positionY;
    private float heliceOffsetX; // Offset da posição X da hélice em relação ao helicóptero
    private int heliceSpriteIndex;
    private float heliceAnimationTime;

    public Helice(float initialX, float initialY) {
        heliceTextures = new Texture[8];
        for (int i = 0; i < 8; i++) {
            heliceTextures[i] = new Texture("assets/helice_" + (i + 1) + ".png");
        }

        positionX = initialX;
        positionY = initialY;
        heliceOffsetX = 0; // Inicialmente, sem offset
        heliceSpriteIndex = 0;
        heliceAnimationTime = 0;
    }

    public void render(Batch batch) {
        batch.draw(heliceTextures[heliceSpriteIndex], positionX + heliceOffsetX, positionY);
    }

    public void update(float deltaTime) {
        updateHeliceSpriteIndex(deltaTime);
    }

    private void updateHeliceSpriteIndex(float deltaTime) {
        heliceAnimationTime += deltaTime;
        float frameDuration = 0.1f; // Duração de cada frame da animação da hélice

        if (heliceAnimationTime >= frameDuration) {
            heliceSpriteIndex = (heliceSpriteIndex + 1) % heliceTextures.length;
            heliceAnimationTime = 0;
        }
    }

    public void setPositionX(float x) {
        positionX = x;
    }

    public void setPositionY(float y) {
        positionY = y;
    }

    public void setHeliceOffsetX(float offsetX) {
        heliceOffsetX = offsetX;
    }

    public void dispose() {
        for (Texture texture : heliceTextures) {
            texture.dispose();
        }
    }
}
