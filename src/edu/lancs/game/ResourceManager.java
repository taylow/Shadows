package edu.lancs.game;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ResourceManager {
    private HashMap<String, Sound> sounds;
    private HashMap<String, Texture> textures;
    private HashMap<String, Texture> animations;

    public ResourceManager(String filePath) {
        sounds = new HashMap<>();
        textures = new HashMap<>();
        loadResources(filePath);
    }

    public void loadResources(String filePath) {
        loadtextures(filePath);
        loadSprites(filePath + "sprites/game");
        loadAnimations(filePath + animations);
        loadSounds(filePath + "sounds");
        loadFonts(filePath);
    }

    /***
     * Loads textures from a specific file path.
     *
     * @param filePath - Location of textures
     */
    private void loadtextures(String filePath) {
        //TODO: load textures here
    }

    /***
     * Loads sprites from a specific file path.
     *
     * @param filePath - Location of sprites
     */
    private void loadSprites(String filePath) {
        File directory = new File(filePath);
        File[] directoryListing = directory.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                if(file.isDirectory()) {
                    loadSprites(file.getPath());
                } else {
                    loadSprite(file.getPath());
                }
            }
        } else {
            System.err.println("Sound " + directory.getPath() + " could not be loaded");
        }
    }

    /***
     * Loads a single sprite into the sprites ArrayList using the filename (without extension) as
     * its HashMap name.
     *
     * @param filePath - File to the specific sprite
     */
    private void loadSprite(String filePath) {
        File file = new File(filePath);
        try {
            Image image = new Image();
            image.loadFromFile(file.toPath());

            //TODO: Add masking (if needed)
            //image.createMaskFromColor(Color.BLACK);

            Texture texture = new Texture();
            texture.loadFromImage(image);
            texture.setSmooth(true);

            String rawFileName = file.getName().substring(0, file.getName().lastIndexOf('.'));
            textures.put(rawFileName, texture);

            Debug.print("Loaded sprite: " + rawFileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TextureCreationException e) {
            e.printStackTrace();
        }
    }

    /***
     * Loads animations from a specific file path.
     *
     * @param filePath - Location of animations
     */
    private void loadAnimations(String filePath) {
        File directory = new File(filePath);
        File[] directoryListing = directory.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                if(file.isDirectory()) {
                    loadAnimations(file.getPath());
                } else {
                    loadAnimation(file.getPath());
                }
            }
        } else {
            System.err.println("Sound " + directory.getPath() + " could not be loaded");
        }
    }

    /***
     * Loads a single sound into the sounds ArrayList using the filename (without extension) as
     * its HashMap name.
     *
     * @param filePath - File to the specific sound
     */
    private void loadAnimation(String filePath) {
        File file = new File(filePath);
        try {
            SoundBuffer soundBuffer = new SoundBuffer();
            soundBuffer.loadFromFile(file.toPath());
            Sound sound = new Sound(soundBuffer);

            String rawFileName = file.getName().substring(0, file.getName().lastIndexOf('.'));
            sounds.put(rawFileName, sound);

            Debug.print("Loaded sound: " + rawFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * Loads music from a specific file path.
     *
     * @param filePath - Location of music
     */
    private void loadSounds(String filePath) {
        File directory = new File(filePath);
        File[] directoryListing = directory.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                if(file.isDirectory()) {
                    loadSounds(file.getPath());
                } else {
                    loadSound(file.getPath());
                }
            }
        } else {
            System.err.println("Sound " + directory.getPath() + " could not be loaded");
        }
    }

    /***
     * Loads a single sound into the sounds ArrayList using the filename (without extension) as
     * its HashMap name.
     *
     * @param filePath - File to the specific sound
     */
    private void loadSound(String filePath) {
        File file = new File(filePath);
        try {
            SoundBuffer soundBuffer = new SoundBuffer();
            soundBuffer.loadFromFile(file.toPath());
            Sound sound = new Sound(soundBuffer);

            String rawFileName = file.getName().substring(0, file.getName().lastIndexOf('.'));
            sounds.put(rawFileName, sound);

            Debug.print("Loaded sound: " + rawFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /***
     * Loads fonts from a specific file path.
     *
     * @param filePath - Location of fonts
     */
    private void loadFonts(String filePath) {
        //TODO: load textures here
    }

    public Sound getSound(String name) {
        return sounds.get(name);
    }

    public Texture getTextures(String name) {
        return textures.get(name);
    }
}
