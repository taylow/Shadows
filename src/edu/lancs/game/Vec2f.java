package edu.lancs.game;

import org.jsfml.system.Vector2f;

/** This class provides additional functionality to JSFML Vector2f. */
public class Vec2f
{
    /** Normalizes a 2D vector.
     * @param vec the vector to be normalized
     * @return normalized vector
     */
    public static Vector2f normalize(Vector2f vec )
    {
        if( length( vec ) == 0 ) System.out.println( "Division by 0 (in normalize()).");
        return Vector2f.div( vec, length( vec ) );
    }

    /** Gets the length of a 2D vector.
     * @param vec vector that is going to be measured.
     * @return length of the vector.
     */
    public static float length( Vector2f vec )
    {
        return ( float ) Math.sqrt( vec.x*vec.x + vec.y*vec.y );
    }

    /** Gets the dot product of 2 vectors.
     * @param vec1 first vector
     * @param vec2 second vector
     * @return dot product of two vectors.
     */
    public static float dot(Vector2f vec1, Vector2f vec2 )
    {
        return ( vec1.x*vec2.x + vec1.y*vec2.y );
    }

    /** Checks if a vector is bigger than another vector, component-wise.
     * @param vec1 the first vector.
     * @param vec2 the second vector.
     * @return true if the first vector is bigger than the second one, false otherwise.
     */
    public static boolean greaterThan(Vector2f vec1, Vector2f vec2 )
    {
        return ( vec1.x > vec2.x && vec1.y > vec2.y );
    }

    /** Checks if a vector is smaller than another vector, component-wise.
     * @param vec1 the first vector.
     * @param vec2 the second vector.
     * @return true if the first vector is smaller than the second one, false otherwise.
     */
    public static boolean lessThan(Vector2f vec1, Vector2f vec2 )
    {
        return ( vec1.x < vec2.x && vec1.y < vec2.y );
    }
}
