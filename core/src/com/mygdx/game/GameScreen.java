package com.mygdx.game;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
    private DesertStrike game;
    private static final float HELICOPTER_SPEED = 1000.0f;
    private static final float HELICOPTER_MAX_DISTANCE_FROM_CENTER = 200.0f;
    private static final float WINDOW_WIDTH = 1280;
    private static final float WINDOW_HEIGHT = 720;
    private Batch batch;
    private OrthographicCamera camera;
    private Helicoptero helicoptero;
    private Cenario cenario;
    private ArrayList<Inimigo> inimigos;
    private int pontos;

    public GameScreen(final DesertStrike gam) {
        this.game = gam;
        batch = new SpriteBatch();
        camera = new OrthographicCamera(WINDOW_WIDTH, WINDOW_HEIGHT);
        cenario = new Cenario();
        helicoptero = new Helicoptero(525, 618);
        inimigos = new ArrayList<>();
        camera.position.set(cenario.getWidth() / 2, cenario.getHeight() / 2, 0);
        camera.update();
        Gdx.graphics.setWindowedMode((int) WINDOW_WIDTH, (int) WINDOW_HEIGHT);
    }

    @Override
    public void render(float delta) {
        float deltaTime = Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float helicopterMovementX = 0;
        float helicopterMovementY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            helicopterMovementX = -HELICOPTER_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            helicopterMovementX = HELICOPTER_SPEED;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            helicopterMovementY = HELICOPTER_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            helicopterMovementY = -HELICOPTER_SPEED;
        }

        float movementX = helicopterMovementX * deltaTime;
        float movementY = helicopterMovementY * deltaTime;

        float newHelicopterX = helicoptero.getPositionX() + movementX;
        float newHelicopterY = helicoptero.getPositionY() + movementY;

        // Verificar os limites horizontais
        float minHelicopterX = HELICOPTER_MAX_DISTANCE_FROM_CENTER;
        float maxHelicopterX = cenario.getWidth() - HELICOPTER_MAX_DISTANCE_FROM_CENTER;
        if (newHelicopterX < minHelicopterX) {
            newHelicopterX = minHelicopterX;
        } else if (newHelicopterX > maxHelicopterX) {
            newHelicopterX = maxHelicopterX;
        }

        // Verificar os limites verticais
        float minHelicopterY = HELICOPTER_MAX_DISTANCE_FROM_CENTER;
        float maxHelicopterY = cenario.getHeight() - HELICOPTER_MAX_DISTANCE_FROM_CENTER;
        if (newHelicopterY < minHelicopterY) {
            newHelicopterY = minHelicopterY;
        } else if (newHelicopterY > maxHelicopterY) {
            newHelicopterY = maxHelicopterY;
        }

        // Calcular o movimento atualizado
        float updatedMovementX = newHelicopterX - helicoptero.getPositionX();
        float updatedMovementY = newHelicopterY - helicoptero.getPositionY();

        helicoptero.update(updatedMovementX, updatedMovementY, deltaTime);
        for (Tiro tiro : helicoptero.getTiros()) {
            tiro.update(deltaTime);
        }
        verificarColisoes();
        float helicopterX = helicoptero.getPositionX();
        float helicopterY = helicoptero.getPositionY();

        float minCameraX = WINDOW_WIDTH / 2;
        float maxCameraX = cenario.getWidth() - WINDOW_WIDTH / 2;
        float minCameraY = WINDOW_HEIGHT / 2;
        float maxCameraY = cenario.getHeight() - WINDOW_HEIGHT / 2;

        if (helicopterX < minCameraX) {
            helicopterX = minCameraX;
        } else if (helicopterX > maxCameraX) {
            helicopterX = maxCameraX;
        }

        if (helicopterY < minCameraY) {
            helicopterY = minCameraY;
        } else if (helicopterY > maxCameraY) {
            helicopterY = maxCameraY;
        }

        camera.position.set(helicopterX, helicopterY, 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        cenario.render(batch);
        for (Inimigo inimigo : inimigos) {
            inimigo.render(batch,this);
        }
        for (Tiro tiro : helicoptero.getTiros()) {
            tiro.render(batch);
        }
        helicoptero.render(batch);
        String pontosTexto = "Pontos: " +  getPontos();
       game.font.draw(batch, pontosTexto, camera.position.x - camera.viewportWidth / 2 + 10, camera.position.y + camera.viewportHeight / 2 - 10);
        batch.end();
        if(pontos >= 100){
        game.setScreen(new GameOverScreen(game));
    }

    }

    private void verificarColisoes() {
        ArrayList<Tiro> tirosParaRemover = new ArrayList<>();

        for (Tiro tiro : helicoptero.getTiros()) {
            for (Inimigo inimigo : inimigos) {
                if (colisaoEntreObjetos(tiro.getPositionX(), tiro.getPositionY(), tiro.getWidth(), tiro.getHeight(),
                    inimigo.getPositionX(), inimigo.getPositionY(), inimigo.getWidth(), inimigo.getHeight())) {
                    inimigo.diminuirVida(1);
                    tiro.dispose();
                    tirosParaRemover.add(tiro); // Adicionar o tiro à lista de tiros para remover
                    break; // Sair do loop interno, pois o tiro já colidiu com um inimigo
                }
            }
        }

        // Remover os tiros da lista de tiros
        for (Tiro tiro : tirosParaRemover) {
            helicoptero.removerTiro(tiro);
        }
    }

    private boolean colisaoEntreObjetos(float x1, float y1, float w1, float h1, float x2, float y2, float w2,
            float h2) {
        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
    }

     public int getPontos() {
        return pontos;
    }

    public void adicionarPontos(int quantidade) {
        pontos += quantidade;
    }

    @Override
    public void dispose() {
        batch.dispose();
        helicoptero.dispose();
        // Descartar as texturas dos inimigos
        for (Inimigo inimigo : inimigos) {
            inimigo.dispose();
        }
        cenario.dispose();
    }

    @Override
    public void show() {

        // Criar alguns inimigos e adicioná-los à lista
        Texture inimigoTexture1 = new Texture("predio1.png");
        Texture inimigoTexture1Destruido = new Texture("predio1_destruido.png");
        Texture inimigoTexture2 = new Texture("predio2.png");
        Texture inimigoTexture2Destruido = new Texture("predio2_destruido.png");
        Texture inimigoTexture3 = new Texture("predio3.png");
        Texture inimigoTexture3Destruido = new Texture("predio3_destruido.png");
        Texture inimigoTexture4 = new Texture("predio4.png");
        Texture inimigoTexture4Destruido = new Texture("predio4_destruido.png");
        Texture inimigoTexture5 = new Texture("predio5.png");
        Texture inimigoTexture5Destruido = new Texture("predio5_destruido.png");

        inimigos.add(new Inimigo(3550, cenario.getHeight() - 1050, inimigoTexture1, inimigoTexture1Destruido));
        inimigos.add(new Inimigo(3770, cenario.getHeight() - 1200, inimigoTexture2, inimigoTexture2Destruido));
        inimigos.add(new Inimigo(3550, cenario.getHeight() - 1310, inimigoTexture3, inimigoTexture3Destruido));
        inimigos.add(new Inimigo(3250, cenario.getHeight() - 1450, inimigoTexture4, inimigoTexture4Destruido));
        inimigos.add(new Inimigo(3750, cenario.getHeight() - 1430, inimigoTexture5, inimigoTexture5Destruido));
        inimigos.add(new Inimigo(4050, cenario.getHeight() - 1330, inimigoTexture3, inimigoTexture3Destruido));
        inimigos.add(new Inimigo(3510, cenario.getHeight() - 1570, inimigoTexture2, inimigoTexture2Destruido));
        inimigos.add(new Inimigo(3000, cenario.getHeight() - 1330, inimigoTexture2, inimigoTexture2Destruido));
        inimigos.add(new Inimigo(3290, cenario.getHeight() - 1200, inimigoTexture1, inimigoTexture1Destruido));
        inimigos.add(new Inimigo(4800, cenario.getHeight() - 680, inimigoTexture1, inimigoTexture1Destruido));
        inimigos.add(new Inimigo(4830, cenario.getHeight() - 930, inimigoTexture2, inimigoTexture2Destruido));
        inimigos.add(new Inimigo(5060, cenario.getHeight() - 1050, inimigoTexture3, inimigoTexture3Destruido));
        inimigos.add(new Inimigo(5320, cenario.getHeight() - 1190, inimigoTexture3, inimigoTexture3Destruido));
        inimigos.add(new Inimigo(5320, cenario.getHeight() - 1460, inimigoTexture4, inimigoTexture4Destruido));

        camera.update();

        Gdx.graphics.setWindowedMode((int) WINDOW_WIDTH, (int) WINDOW_HEIGHT);
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}
