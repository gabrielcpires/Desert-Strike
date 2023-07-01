package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Helicoptero {
    private Texture[] helicopterTextures;
    private Helice helice;
    private Sombra sombra;
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
        helice = new Helice(initialX, initialY + 8); // Posição inicial da hélice
        sombra = new Sombra(initialX, initialY);
    }

    public void render(Batch batch) {
        batch.draw(helicopterTextures[currentSpriteIndex], positionX, positionY);
        helice.render(batch);
        sombra.render(batch);
    }

    public void update(float movementX, float movementY, float deltaTime) {
        // Determinar o índice do sprite com base nas direções de movimento
        if (movementY > 0) {
            if (movementX > 0) {
                currentSpriteIndex = 1; // sprite_2.png (seta para cima e para a direita)
                helice.setHeliceOffsetX(0);
            } else if (movementX < 0) {
                currentSpriteIndex = 7; // sprite_8.png (seta para cima e para a esquerda)
                helice.setHeliceOffsetX(0);
            } else {
                helice.setHeliceOffsetX(-8);
                currentSpriteIndex = 0; // sprite_1.png (seta para cima)
            }
        } else if (movementY < 0) {
            if (movementX > 0) {
                helice.setHeliceOffsetX(0);
                currentSpriteIndex = 3; // sprite_4.png (seta para baixo e para a direita)
            } else if (movementX < 0) {
                helice.setHeliceOffsetX(0);
                currentSpriteIndex = 5; // sprite_6.png (seta para baixo e para a esquerda)
            } else {
                helice.setHeliceOffsetX(-8);
                currentSpriteIndex = 4; // sprite_5.png (seta para baixo)
            }
        } else {
            if (movementX > 0) {
                helice.setHeliceOffsetX(0);
                currentSpriteIndex = 2; // sprite_3.png (seta para a direita)
            } else if (movementX < 0) {
                helice.setHeliceOffsetX(0);
                currentSpriteIndex = 6; // sprite_7.png (seta para a esquerda)
            }
        }

        positionX += movementX;
        positionY += movementY;
        
        helice.update(deltaTime);
        helice.setPositionX(positionX);
        helice.setPositionY(positionY + 8); // Mantém a helice sempre acima do helicóptero
        
        sombra.update(movementX, movementY);
        sombra.setPositionX(positionX);
        sombra.setPositionY(positionY);
    }

    public void dispose() {
        for (Texture texture : helicopterTextures) {
            texture.dispose();
        }
        
        helice.dispose();
        sombra.dispose();
    }
    
    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }
}
