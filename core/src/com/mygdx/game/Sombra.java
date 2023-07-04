package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Sombra {
    private Texture[] sombraTextures;
    private float positionX;
    private float positionY;
    private int sombraSpriteIndex;
    private static final int SHADOW_OFFSET = 25; // Quantidade de pixels para deslocar a sombra para baixo

    public Sombra(float initialX, float initialY) {
        sombraTextures = new Texture[8];
        for (int i = 0; i < 8; i++) {
            sombraTextures[i] = new Texture("assets/sombra_" + (i + 1) + ".png");
        }

        positionX = initialX;
        positionY = initialY - SHADOW_OFFSET; // Deslocar a posição inicial da sombra para baixo
        sombraSpriteIndex = 0;
    }

    public void render(Batch batch) {
        batch.draw(sombraTextures[sombraSpriteIndex], positionX, positionY);
    }

    public void update(float movementX, float movementY) {
        // Determinar o índice da sprite com base nas direções de movimento do helicóptero
        if (movementY > 0) {
            if (movementX > 0) {
                sombraSpriteIndex = 1; // sprite_2.png (seta para cima e para a direita)
            } else if (movementX < 0) {
                sombraSpriteIndex = 7; // sprite_8.png (seta para cima e para a esquerda)
            } else {
                sombraSpriteIndex = 0; // sprite_1.png (seta para cima)
            }
        } else if (movementY < 0) {
            if (movementX > 0) {
                sombraSpriteIndex = 3; // sprite_4.png (seta para baixo e para a direita)
            } else if (movementX < 0) {
                sombraSpriteIndex = 5; // sprite_6.png (seta para baixo e para a esquerda)
            } else {
                sombraSpriteIndex = 4; // sprite_5.png (seta para baixo)
            }
        } else {
            if (movementX > 0) {
                sombraSpriteIndex = 2; // sprite_3.png (seta para a direita)
            } else if (movementX < 0) {
                sombraSpriteIndex = 6; // sprite_7.png (seta para a esquerda)
            }
        }
    }

    public void setPositionX(float x) {
        positionX = x;
    }

    public void setPositionY(float y) {
        positionY = y - SHADOW_OFFSET; // Atualizar a posição da sombra com deslocamento para baixo
    }

    public void dispose() {
        for (Texture texture : sombraTextures) {
            texture.dispose();
        }
    }
}
