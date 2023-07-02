package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Inimigo {
    private Texture inimigoTexture;
    private float positionX;
    private float positionY;
    private int vida;

    public Inimigo(float initialX, float initialY) {
        inimigoTexture = new Texture("inimigos_2.png");
        positionX = initialX;
        positionY = initialY;
        vida = 1000; // Defina a quantidade inicial de vida do inimigo
    }

    public void render(Batch batch) {
        if (vida > 0) {
            batch.draw(inimigoTexture, positionX, positionY);
        }
        if (vida <= 0) {
            vida = 0;
            dispose(); // Remover a textura do inimigo quando a vida for igual a 0
            positionX = -1000; // Defina uma posição fora da tela para o inimigo
            positionY = -1000;
        }
    }

    public void diminuirVida(int quantidade) {
        vida -= quantidade;
        if (vida <= 0) {
            vida = 0;
            dispose(); // Remover a textura do inimigo quando a vida for igual a 0
        }
    }

    public void dispose() {
        inimigoTexture.dispose();
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public int getVida() {
        return vida;
    }

    public Texture getTexture() {
        return inimigoTexture;
    }

    public void setTexture(Texture texture) {
        inimigoTexture = texture;
    }
}
