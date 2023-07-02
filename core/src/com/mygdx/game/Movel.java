package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

abstract class Movel {

	protected Texture mImage;
	protected Rectangle mRectangle;
	protected Sound mSound;

	public Movel(Rectangle rectangle, Texture texture, Sound sound) {
		mRectangle = rectangle;
		mImage = texture;
		mSound = sound;
	}

	public abstract void move();

	public abstract void handleEvent(OrthographicCamera camera);

	public void PlaySound(){
		if(mSound != null){
			mSound.play();
		}
		
	}
	
	public Texture getmImage() {
		return mImage;
	}

	public void setmImage(Texture mImage) {
		this.mImage = mImage;
	}

	public Rectangle getmRectangle() {
		return mRectangle;
	}

	public void setmRectangle(Rectangle mRectangle) {
		this.mRectangle = mRectangle;
	}

	public Sound getmSound() {
		return mSound;
	}

	public void setmSound(Sound mSound) {
		this.mSound = mSound;
	}
}