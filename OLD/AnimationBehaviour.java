package edu.lancs.game.entity.behaviour;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.ArrayList;


/**
 * Try not to touch this code. Just use it if you want animations.
 * Animates an entity using a sprite sheet.
 */
public class AnimationBehaviour {
    private final int SEQUENCE_SPEED_NORMAL = 10; // Milliseconds spent on 1 sprite.
    public int currentSequence; // The sprite that is currently shown.
    public int sequenceEnd; // The index of last sprite from sprite sheet.
    public int maxAnimationTimeMillis;
    public Vector2f originPoint;
    private Clock sequenceTimer = new Clock();
    private Sprite sprite;
    private Texture texture;
    private ArrayList<Texture> textures;
    private int texWidth; // Width of texture used.
    private int texHeight; // Height of texture.
    private int totalSequences; // Total sprites in the sprite sheet.
    private int sequenceStart; // The index of the first sprite that is going to be used from the sprite sheet.
    private int millisPerSequence = SEQUENCE_SPEED_NORMAL;
    private int numSequencesRow; // Number of sprites per row in the sprite sheet.
    private int numSequencesColumn; // Number of sprites per column in the sprite sheet.
    // Used for idle animations.
    private boolean showOnlyOneFrame; // Supports the option to show only the first frame of a certain animation.
    private boolean spriteSheetMode = false;

    /**
     * Automatically animates the sprite based on the spritesheet provided.
     *
     * @param spr             the sprite that is going to be animated.
     * @param numSequencesRow the number of sprites per row in the sprite texture.
     *                        for example there are 15 sprites per row in this texture here: http://www.gamefromscratch.com/image.axd?picture=a.png
     * @param totalSequences  the total number of sprites in the spritesheet/texture. in the link above, there are total of 32 sprites.
     */
    public AnimationBehaviour(Sprite spr, int sequenceStart, int sequenceEnd, int numSequencesRow, int totalSequences) {
        spriteSheetMode = true;
        sprite = spr;
        texture = (Texture) sprite.getTexture();
        texWidth = texture.copyToImage().toBufferedImage().getWidth();
        texHeight = texture.copyToImage().toBufferedImage().getHeight();
        this.numSequencesRow = numSequencesRow;
        this.numSequencesColumn = (totalSequences % numSequencesRow == 0) ?
                totalSequences / numSequencesRow : totalSequences / numSequencesRow + 1;
        this.sequenceStart = sequenceStart;
        currentSequence = sequenceStart;
        this.sequenceEnd = sequenceEnd;
        this.totalSequences = totalSequences;
        maxAnimationTimeMillis = totalSequences * millisPerSequence;
    }

    public AnimationBehaviour(Sprite sprite, ArrayList<Texture> textures, Vector2f originPoint) {
        this.sequenceStart = 0;
        this.sprite = sprite;
        this.textures = textures;
        maxAnimationTimeMillis = textures.size() * millisPerSequence;
        sequenceEnd = textures.size();
        this.sprite.setTexture(textures.get(0));
        this.originPoint = originPoint;
    }

    /**
     * Sets the animation speed.
     */
    public void setAnimationSpeed(float factor) {
        millisPerSequence = (int) ((float) SEQUENCE_SPEED_NORMAL / factor);
    }

    public void reset() {
        sequenceTimer.restart();
        currentSequence = sequenceStart;
        sprite.setTexture(textures.get(currentSequence));
        sprite.setTextureRect(new IntRect(new Vector2i(0, 0), new Vector2i(sprite.getTexture().getSize().x, sprite.getTexture().getSize().y)));
        sprite.setOrigin(originPoint);
    }

    public boolean isEnding() {
        return currentSequence == sequenceEnd - 1 || currentSequence == sequenceEnd - 2;
    }

    public void showOnlyFirstFrame() {
        showOnlyOneFrame = true;
    }

    public void showAllFrames() {
        showOnlyOneFrame = false;
    }

    /**
     * Updates the animation. Should be called every frame.
     */
    public void update() {

        if (spriteSheetMode == true) {
            if (sequenceTimer.getElapsedTime().asMilliseconds() > millisPerSequence) {
                currentSequence++;
                if (currentSequence == sequenceEnd) {
                    currentSequence = sequenceStart;
                }
                if (showOnlyOneFrame == true) {
                    currentSequence = sequenceStart;
                }
                sequenceTimer.restart();
                int rectX = (currentSequence % numSequencesRow) * texWidth / numSequencesRow;
                int rectY = (currentSequence / numSequencesRow) * texHeight / numSequencesColumn;
                int rectW = texWidth / numSequencesRow;
                int rectH = texHeight / numSequencesColumn;
                sprite.setTextureRect(new IntRect(rectX, rectY, rectW, rectH));
            }
        } else {
            if (sequenceTimer.getElapsedTime().asMilliseconds() > millisPerSequence) {
                sequenceTimer.restart();
                currentSequence++;
                if (currentSequence == textures.size()) {
                    currentSequence = sequenceStart;
                }

                sprite.setTexture(textures.get(currentSequence));
                sprite.setTextureRect(new IntRect(new Vector2i(0, 0), new Vector2i(sprite.getTexture().getSize().x, sprite.getTexture().getSize().y)));

            }

            //System.out.println( currentSequence );
        }
    }
}
