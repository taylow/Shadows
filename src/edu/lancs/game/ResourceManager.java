package edu.lancs.game;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ResourceManager {
    private HashMap<String, Sound> sounds;
    private HashMap<String, Font> fonts;
    private HashMap<String, Texture> textures;
    private HashMap<String, ArrayList<Texture>> animations;

    public ResourceManager(String filePath) {
        sounds = new HashMap<>();
        fonts = new HashMap<>();
        textures = new HashMap<>();
        animations = new HashMap<>();
        loadResources(filePath);
    }

    /***
     * Loads all resources by providing the resource path plus the sub paths.
     * Note: Resource file should follow strict order. You can add any resource
     * just by adding the file, but do not change the directories.
     *
     * @param filePath - The resource path.
     */
    public void loadResources(String filePath) {
        loadTextures(filePath + "sprites/game");
        loadAnimations(filePath + "animations");
        loadSounds(filePath + "sounds");
        loadFonts(filePath + "misc/fonts");
    }

    /***
     * Loads all textures from a specific file path by recursively running until an actual texture is found.
     *
     * @param filePath - Location of textures
     */
    private void loadTextures(String filePath) {
        File directory = new File(filePath);
        File[] directoryListing = directory.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                if (file.isDirectory()) {
                    loadTextures(file.getPath());
                } else {
                    loadTexture(file.getPath());
                }
            }
        } else {
            Debug.error("Texture " + directory.getPath() + " could not be loaded");
        }
    }

    /***
     * Loads a single texture into the textures ArrayList using the filename (without extension) as
     * its HashMap name.
     *
     * @param filePath - File to the specific texture
     */
    private void loadTexture(String filePath) {
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

            Debug.print("Loaded texture: " + rawFileName);
        } catch (IOException | TextureCreationException e) {
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
                if (file.isDirectory()) {
                    loadAnimations(file.getPath());
                } else {
                    String rawFileName = file.getParentFile().getName();
                    animations.put(rawFileName, loadAnimation(file.getParentFile().getPath()));
                    Debug.print("Loaded animation: " + rawFileName);
                    break;
                }
            }
        } else {
            Debug.error("Animation " + directory.getPath() + " could not be loaded");
        }
    }

    /***
     * Loads a single sound into the sounds ArrayList using the filename (without extension) as
     * its HashMap name.
     *
     * @param filePath - File to the specific sound
     */
    private ArrayList<Texture> loadAnimation(String filePath) {
        ArrayList<Texture> animationTextures = new ArrayList<>();
        File directory = new File(filePath);
        File[] directoryListing = directory.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                Image image = new Image();
                try {
                    image.loadFromFile(file.toPath());

                    //TODO: Add proper masking colours if needed (works for the animations we currently have)
                    Color maskColour = new Color(3, 2, 1);
                    image.createMaskFromColor(maskColour, 0);

                    Texture texture = new Texture();
                    texture.loadFromImage(image);
                    texture.setSmooth(true);

                    animationTextures.add(texture);

                    String rawFileName = file.getName().substring(0, file.getName().lastIndexOf('.'));
                    Debug.print("Loaded animation frame: " + rawFileName);
                } catch (IOException | TextureCreationException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Debug.error("Animation " + directory.getPath() + " could not be loaded");
        }
        return animationTextures;
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
                if (file.isDirectory()) {
                    loadSounds(file.getPath());
                } else {
                    loadSound(file.getPath());
                }
            }
        } else {
            Debug.error("Sound " + directory.getPath() + " could not be loaded");
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
        File directory = new File(filePath);
        File[] directoryListing = directory.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                if (file.isDirectory()) {
                    loadFonts(file.getPath());
                } else {
                    loadFont(file.getPath());
                }
            }
        } else {
            Debug.error("Font " + directory.getPath() + " could not be loaded");
        }
    }

    /***
     * Loads a single font into the fonts ArrayList using the filename (without extension) as
     * its HashMap name.
     *
     * @param filePath - File to the specific font
     */
    private void loadFont(String filePath) {
        File file = new File(filePath);
        try {
            Font font = new Font();
            font.loadFromFile(file.toPath());

            String rawFileName = file.getName().substring(0, file.getName().lastIndexOf('.'));
            fonts.put(rawFileName, font);

            Debug.print("Loaded font: " + rawFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Sound getSound(String name) {
        // checks if sound is in the sounds list
        if (sounds.containsKey(name))
            return sounds.get(name);
        else
            Debug.error("Invalid sound: " + name);
        return null;
    }

    public Font getFont(String name) {
        // checks if the font is in the fonts list
        if (fonts.containsKey(name))
            return fonts.get(name);
        else
            Debug.error("Invalid font: " + name);
        return null;
    }

    public Texture getTextures(String name) {
        // checks if texture is in textures list
        if (textures.containsKey(name))
            return textures.get(name);
        else
            Debug.error("Invalid texture: " + name);
        return null;
    }

    public ArrayList<Texture> getAnimations(String animationName) {
        // checks if the animation is in the animation list
        if (animations.containsKey(animationName))
            return animations.get(animationName);
        else
            Debug.error("Invalid animation: " + animationName);
        return null;
    }
}
