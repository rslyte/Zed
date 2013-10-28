
package zed;

import org.newdawn.slick.Animation;

// Slick for drawing to the screen
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Richard Barella Jr
 * @author Adam Bennett
 * @author Ryan Slyter
 */

// ============
// Object class
// ============
// The most basic out of classes that hold information on in-game
// objects and can also print itself to the screen.
public class Object {
    
    private static final int ANIMATION_SPEED = 200;
    
    // Holds the pixel-location of top-left of Object
    int X_Position;
    int Y_Position;
    
    // Holds the animations for the object.
    Animation[] Animation_List;
    Animation Current_Animation;
    int Current_Animation_Index;
    
    int[] Sprite_Shift_X;
    int[] Sprite_Shift_Y;
    
    // Holds the visibility of Object
    boolean Visible;
    
    // Default constructor
    public Object(){
        
        Init(0, 0, false, null, null, 1, null, null, null, null, 0);
    }
    
    // Specific Constructor
    public Object(int tile_x, int tile_y, boolean visible,
            int[] sprite_shift_x, int[] sprite_shift_y, int tilesize, // how far sprite is shifted and size in pixels
            SpriteSheet sprites, int[] spritesheet_index, int[] animation_length, boolean[] looping,
            int current_animation){
        
        this.Init(tile_x, tile_y, visible, sprite_shift_x, sprite_shift_y, tilesize, sprites,
                spritesheet_index, animation_length, looping, current_animation);
    }
    
    public Object(int tile_x, int tile_y, boolean visible,
    		int[] sprite_shift_x, int[] sprite_shift_y, int tilesize, // how far sprite is shifted and size in pixels
    		Animation[] animation_list, int current_animation){
    	
    	this.Init(tile_x, tile_y, visible, sprite_shift_x, sprite_shift_y, tilesize, animation_list, current_animation);
    }
    
    // Initialization function that generates animations based on SpriteSheet
    public void Init(int tile_x, int tile_y, boolean visible,
            int[] sprite_shift_x, int[] sprite_shift_y, int tilesize, // how far sprite is shifted and size in pixels
            SpriteSheet sprites, int[] spritesheet_index, int[] animation_length, boolean[] looping,
            int current_animation){
        
        X_Position = tile_x*tilesize;
        Y_Position = tile_y*tilesize;
        
        Visible = visible;
        
        Sprite_Shift_X = sprite_shift_x;
        Sprite_Shift_Y = sprite_shift_y;
        
        Animation_List = new Animation[spritesheet_index.length];
        
        for (int i = 0; i < spritesheet_index.length; i++)
        {
            Animation_List[i] = new Animation(sprites,
                    0,                       spritesheet_index[i], // first sprite
                    animation_length[i] - 1, spritesheet_index[i], // last sprite
                    false, // horizontalScan true?
                    ANIMATION_SPEED, true /*autoupdate?*/);
            Animation_List[i].setLooping(looping[i]);
            Animation_List[i].setPingPong(true);
        }
        Current_Animation = Animation_List[current_animation];
        Current_Animation_Index = current_animation;
    }
    
    public void Init(int tile_x, int tile_y, boolean visible,
    		int[] sprite_shift_x, int[] sprite_shift_y, int tilesize,
    		Animation[] animation_list, int current_animation)
    {
    	X_Position = tile_x*tilesize;
    	Y_Position = tile_y*tilesize;
    	
    	Visible = visible;
    	
    	Sprite_Shift_X = sprite_shift_x;
    	Sprite_Shift_Y = sprite_shift_y;
    	
    	Animation_List = animation_list;
    	
    	Current_Animation = Animation_List[current_animation];
    }
    
    // Change the position of Object
    public void Move(int new_x_pos, int new_y_pos){
        
        X_Position = new_x_pos;
        Y_Position = new_y_pos;
    }
    
    // Get the object's current x position
    public int Get_X_Position(){
        
        return X_Position;
    }
    
    // Get the object's current y position
    public int Get_Y_Position(){
        
        return Y_Position;
    }
    
    // Can tell if the object is alligned with the tiles displayed
    // on the screen
    public boolean Alligned_With_Tiles(int zoom)
    {
        return (X_Position % zoom == 0 &&
                Y_Position % zoom == 0);
    }
    
    public void Render(int zoom,
            int current_tile_x, int current_tile_y, // position of tiles
            GameContainer gc, Graphics g){
        
        if (Visible)
        {
            Current_Animation.draw(
                    X_Position*zoom + current_tile_x - Sprite_Shift_X[Current_Animation_Index]*zoom,
                    Y_Position*zoom + current_tile_y - Sprite_Shift_Y[Current_Animation_Index]*zoom,
                    Current_Animation.getWidth()*zoom, Current_Animation.getHeight()*zoom);
        }
    }
    // Play a different animation for the object
    public void Change_Animation(int new_animation){
        
        if (new_animation < Animation_List.length
                && new_animation >= 0)
        {
            Current_Animation = Animation_List[new_animation];
        }
        Current_Animation_Index = new_animation;
    }
    
    // Get the number for the current animation being played
    // has an error if current animation can't be found.
    public int Get_Frame_State(){
        
        for (int i = 0; i < Animation_List.length; i++)
        {
            if (Current_Animation == Animation_List[i])
            {
                return i;
            }
        }
        return -1;
    }
}