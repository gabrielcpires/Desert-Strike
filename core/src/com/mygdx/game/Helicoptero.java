package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Helicoptero {
    private Texture[] helicopterTextures;
    private float positionX;
    private float positionY;
    private int currentSpriteIndex;
    

    public Helicoptero(float initialX, float initialY) {
        helicopterTextures = new Texture[8];
        for (int i = 0; i < 8; i++) {
            helicopterTextures[i] = new Texture("sprite_" + (i + 1) + ".png");
        }

        positionX = initialX;
        positionY = initialY;
        currentSpriteIndex = 0;
    }

    public void render(Batch batch) {
        batch.draw(helicopterTextures[currentSpriteIndex], positionX, positionY);
    }

    public void update(float movementX, float movementY) {
        // Determinar o índice do sprite com base nas direções de movimento
        if (movementY > 0) {
            if (movementX > 0) {
                currentSpriteIndex = 1; // sprite_2.png (seta para cima e para a direita)
            } else if (movementX < 0) {
                currentSpriteIndex = 7; // sprite_8.png (seta para cima e para a esquerda)
            } else {
                currentSpriteIndex = 0; // sprite_1.png (seta para cima)
            }
        } else if (movementY < 0) {
            if (movementX > 0) {
                currentSpriteIndex = 3; // sprite_4.png (seta para baixo e para a direita)
            } else if (movementX < 0) {
                currentSpriteIndex = 5; // sprite_6.png (seta para baixo e para a esquerda)
            } else {
                currentSpriteIndex = 4; // sprite_5.png (seta para baixo)
            }
        } else {
            if (movementX > 0) {
                currentSpriteIndex = 2; // sprite_3.png (seta para a direita)
            } else if (movementX < 0) {
                currentSpriteIndex = 6; // sprite_7.png (seta para a esquerda)
            }
        }

        positionX += movementX;
        positionY += movementY;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void dispose() {
        for (Texture texture : helicopterTextures) {
            texture.dispose();
        }
    }
}
