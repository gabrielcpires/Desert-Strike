package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Inimigo {
    private Texture textureNormal, textureDestruido;
    private float positionX, positionY;
    private float width, height;
    private int vida;
    private int pontos;
    private Sound somExplosao; // Variável para armazenar o som da explosão
    private boolean exploded;

    public Inimigo(float initialX, float initialY, Texture textureNormal, Texture textureDestruido) {
        this.textureNormal = textureNormal;
        this.textureDestruido = textureDestruido;
        exploded = false;
        positionX = initialX;
        positionY = initialY;
        width = textureNormal.getWidth();
        height = textureNormal.getHeight();
        vida = 10; // Defina a quantidade inicial de vida do inimigo
        somExplosao = Gdx.audio.newSound(Gdx.files.internal("assets/explosao.mp3")); // Carregar o som da explosão
    }

    public void render(Batch batch,GameScreen gameScreen) {
        if (vida > 0) {
            batch.draw(textureNormal, positionX, positionY);
        } else if (vida <= 0) {
            if (!exploded) {
                gameScreen.adicionarPontos(100); 
                somExplosao.play();
                exploded = true;
            }
            batch.draw(textureDestruido, positionX, positionY);
        }
    }

    public void diminuirVida(int quantidade) {
        vida -= quantidade;
    }

    public void dispose() {
        textureNormal.dispose();
        textureDestruido.dispose();
        somExplosao.dispose(); // Descartar o som da explosão quando não for mais necessário
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

    public int getPontos() {
        return pontos;
    }

    public Texture getTexture() {
        return textureNormal;
    }

    public void setTexture(Texture texture) {
        textureNormal = texture;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
