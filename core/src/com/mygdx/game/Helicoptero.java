package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Helicoptero {
    private Texture[] helicopterTextures;
    private Helice helice;
    private Sombra sombra;
    private float positionX;
    private float positionY;
    private int currentSpriteIndex;
    private float tiroDirectionX;
    private float tiroDirectionY;
    private float tiroCooldownTimer;
    private static final float TIRO_COOLDOWN = 0.1f; // Tempo de espera entre os disparos
    private ArrayList<Tiro> tiros;
    private Sound somTiro; // Variável para armazenar o som do tiro
    private Music somFundo; // Variável para armazenar o som de fundo

    public static final float HELICOPTER_WIDTH = 32; // Largura do helicóptero

    public Helicoptero(float initialX, float initialY) {
        
        helicopterTextures = new Texture[8];
        for (int i = 0; i < 8; i++) {
            helicopterTextures[i] = new Texture("assets/sprite_" + (i + 1) + ".png");
        }

        positionX = initialX;
        positionY = initialY;
        currentSpriteIndex = 0;
        helice = new Helice(initialX, initialY + 8); // Posição inicial da hélice
        sombra = new Sombra(initialX, initialY);
        tiroCooldownTimer = 0;
        somFundo = Gdx.audio.newMusic(Gdx.files.internal("som_helicoptero.mp3")); // Carregar o som de fundo
        somFundo.setLooping(true); // Definir a reprodução em loop
        somFundo.play(); // Iniciar a reprodução do som de fundo
        somFundo.setVolume(0.2f);
        somTiro = Gdx.audio.newSound(Gdx.files.internal("tiro.mp3")); // Carregar o som do tiro
        tiros = new ArrayList<>();
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
                currentSpriteIndex = 1;
                helice.setHeliceOffsetX(0);
                tiroDirectionX = 1; // tiro irá para a direita
                tiroDirectionY = 1; // tiro irá para cima
            } else if (movementX < 0) {
                currentSpriteIndex = 7;
                helice.setHeliceOffsetX(0);
                tiroDirectionX = -1; // tiro irá para a esquerda
                tiroDirectionY = 1; // tiro irá para cima
            } else {
                helice.setHeliceOffsetX(-8);
                currentSpriteIndex = 0;
                tiroDirectionX = 0; // tiro irá para cima
                tiroDirectionY = 1; // tiro irá para cima
            }
        } else if (movementY < 0) {
            if (movementX > 0) {
                helice.setHeliceOffsetX(0);
                currentSpriteIndex = 3;
                tiroDirectionX = 1; // tiro irá para a direita
                tiroDirectionY = -1; // tiro irá para baixo
            } else if (movementX < 0) {
                helice.setHeliceOffsetX(0);
                currentSpriteIndex = 5;
                tiroDirectionX = -1; // tiro irá para a esquerda
                tiroDirectionY = -1; // tiro irá para baixo
            } else {
                helice.setHeliceOffsetX(-8);
                currentSpriteIndex = 4;
                tiroDirectionX = 0; // tiro irá para baixo
                tiroDirectionY = -1; // tiro irá para baixo
            }
        } else {
            if (movementX > 0) {
                helice.setHeliceOffsetX(16);
                currentSpriteIndex = 2;
                tiroDirectionX = 1; // tiro irá para a direita
                tiroDirectionY = 0; // tiro irá para a direita
            } else if (movementX < 0) {
                helice.setHeliceOffsetX(0);
                currentSpriteIndex = 6;
                tiroDirectionX = -1; // tiro irá para a esquerda
                tiroDirectionY = 0; // tiro irá para a esquerda
            }
        }
        // Capturar o input do usuário
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
           atirar();
        }

        positionX += movementX;
        positionY += movementY;

        helice.update(deltaTime);
        helice.setPositionX(positionX);
        helice.setPositionY(positionY + 8); // Mantém a helice sempre acima do helicóptero

        sombra.update(movementX, movementY);
        sombra.setPositionX(positionX);
        sombra.setPositionY(positionY);

        if (tiroCooldownTimer > 0) {
            tiroCooldownTimer -= deltaTime;
        }
    }

    public void atirar() {
        if (tiroCooldownTimer <= 0) {
            Tiro tiro = new Tiro(positionX, positionY, tiroDirectionX, tiroDirectionY);
            tiros.add(tiro);
            somTiro.play(1); // Definir volume do som do tiro
            tiroCooldownTimer = TIRO_COOLDOWN;
        }
    }

    public void removerTiro(Tiro tiro) {
        tiros.remove(tiro);
    }

    public void dispose() {
        for (Texture texture : helicopterTextures) {
            texture.dispose();
        }
        somTiro.dispose();
        helice.dispose();
        sombra.dispose();
        somFundo.stop(); // Parar a reprodução do som de fundo
        somFundo.dispose(); // Descartar o som de fundo
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getWidth() {
        return HELICOPTER_WIDTH;
    }

    public ArrayList<Tiro> getTiros() {
        return tiros;
    }
}
