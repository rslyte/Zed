package zed;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

public class Dragon extends GCharacter {
	
	static int Type = 100;
	
	private static final int NUMBER_OF_FIREBALLS = 3;
	private GCharacter[] fireballs;
	
	// Zombie default constructor does nothing
	public Dragon() throws SlickException{
	}
	
	// Zombie constructor
	public Dragon(int tile_x, int tile_y, SpriteSheet sprites) throws SlickException {

	    Tilesize = 16;
		Animation[] animations = new Animation[4];
		
		// Define index of first animation on spritesheet
		int i0 = 0;
		// Define relative indexes of animations on spritesheet
		int[] spritesheet_index = {i0, i0 + 1, i0 + 2, i0 + 3};
		// Define the length of each animation
		int[] animation_length = {3, 3, 3, 3};
		// Define whether each animation loops
		boolean[] looping = {true, true, true, true};
		// Define how each animation is shifted relative to position
		int[] sprite_shift_x = {4, 4, 4, 4};
		int[] sprite_shift_y = {8, 8, 8, 8};
		
		// Initialize walking and standing animations
        for (int i = 0; i < 4; i++)
        {
        	STUN_TIME = 1000; // Set the amount of time stunned if
        	                  // hit by player's sword
        	
	        animations[i] = new Animation(
	        		sprites, // spritesheet to use
	        		// location of first sprite in spritesheet
	                0,                       spritesheet_index[i],
	                // location of last sprite in spritesheet
	                animation_length[i] - 1, spritesheet_index[i],
	                false, // horizontalScan value
	                200, // speed value for animation
	                true // autoupdate value
	                );
	        animations[i].setLooping(true); // intialize whether animation loops
	        animations[i].setPingPong(true); // initialize whether animation ping-pongs between last and first frame
        }
        
        // Define which animations are for which states
        FRAME_STATE_UP = 3; FRAME_STATE_DOWN = 0;
        FRAME_STATE_LEFT = 1; FRAME_STATE_RIGHT = 2;
        FRAME_STATE_UP_WALK = 3; FRAME_STATE_DOWN_WALK = 0;
        FRAME_STATE_LEFT_WALK = 1; FRAME_STATE_RIGHT_WALK = 2;
		
        // Initialize zombie based on constructor
		super.Init(
				tile_x, tile_y, // Tile to put in
				24, 24,
				true, true, 1, // Visible? Solid?
				sprite_shift_x, sprite_shift_y, // shift for each animation
				16, // Size of a tile
				animations, 0, // Initialize animations and current animation
				3, // Health value
				1.0f, // Speed value in tiles per second
				0, 0); // Initial movement values
		
		AI_State_Change_Time = 500; // Time to change state for AI
		
		Hurt_Sound = new Sound("soundtrack/effects/sword_hit_flesh.wav");
		
		// Construct fireballs
		fireballs = new GCharacter[NUMBER_OF_FIREBALLS];
		int[] fireball_sprite_shift = {0};
		int[] fireball_spritesheet_index = {0}; // TODO: change later
		int[] fireball_animation_length = {1};
		boolean[] fireball_looping = {true};
		for (int i = 0; i < NUMBER_OF_FIREBALLS; i++)
		{
			fireballs[i] = new GCharacter(
					-100, // initial x position of the character w.r.t. the game tiles
		    		-100, // initial y position of the character w.r.t. the game tiles
		    		16, // initial width in pixels
		    		16, // initial height in pixels
		    		true, // initialize whether the character is visible
		    		true, // initializes whether its solid
		    		1, // initialize damage to the player
		            fireball_sprite_shift, // by how many pixels each animation is shifted in x direction
		            fireball_sprite_shift, // by how many pixels each animation is shifted in y direction
		            16, // Give the character how large each tile is
		            sprites, // Give the character the spritesheet file for fetching its animation frames
		            fireball_spritesheet_index, // Give the character the indexes for the rows of the SpriteSheet to fetch each animation from
		                                     // index are defined {up,left,down,right,upwalk,leftwalk,downwalk,rightwalk}
		            fireball_animation_length, // Give the character the length of each animation
		            fireball_looping, // tell which animations are looping
		            0, // intialize which animation to start out with
		            1, // intialize the character's max_health and health
		            4.0f, // Give the character its speed in tiles per second 
		            0, // Give the character its initial x_movement value (-1, 0, 1)
		            0, // Give the character its initial y_movement value (-1, 0, 1)
		            10
		            );
		}
	}
	
	public void Spit_Fireball()
	{
		boolean spit = false;
		for (int i = 0; i < fireballs.length && !spit; i++)
		{
			if (fireballs[i].Health > 0)
			{
				spit = true;
				fireballs[i].X_Position = X_Position;
				fireballs[i].Y_Position = Y_Position;
				fireballs[i].X_Movement = X_Movement;
				fireballs[i].Y_Movement = Y_Movement;
				fireballs[i].Increase_Health(1);
				fireballs[i].Visible = true;
			}
		}
	}
	
	// Override GCharacter's Artificial Intelligence function
	// Knows if this character has collided and knows player's position
	public void Artificial_Intelligence(boolean collision, Player_Character player)
	{
		int x_distance = player.Get_X_Position() - X_Position;
		int y_distance = player.Get_Y_Position() - Y_Position;
		// Froam around randomly If zombie cannot see the player
		if (x_distance*x_distance + y_distance*y_distance > 16*5*16*5
				|| !player.Visible)
		{
			super.Artificial_Intelligence(collision, player);
		}
		else // Follow the player if zombie can see the player
		{
			if (x_distance*x_distance > y_distance*y_distance)
			{
				if (x_distance > 0) {X_Movement = 1;}
				else                {X_Movement = -1;}
				Y_Movement = 0;
			}
			else // y_distance >= x_distance
			{
				if (y_distance > 0) {Y_Movement = 1;}
				else                {Y_Movement = -1;}
				X_Movement = 0;
			}
		}
		
		if (collision)
		{
			
		}
	}
	
	public GObject X_Collision(GObject[] otherobj){
		
		GObject return_value = super.X_Collision(otherobj);
		GObject fireballcol;
		for (int i = 0; i < fireballs.length; i++)
		{
			if ((fireballcol = fireballs[i].X_Collision(otherobj)) != null)
			{
				if (fireballcol.Get_Type() != -1)
					fireballs[i].Decriment_Health();
			}
		}
		return return_value;
	}
	
	public GObject Y_Collision(GObject[] otherobj){
		
		GObject return_value = super.Y_Collision(otherobj);
		GObject fireballcol;
		for (int i = 0; i < fireballs.length; i++)
		{
			if ((fireballcol = fireballs[i].Y_Collision(otherobj)) != null)
			{
				if (fireballcol.Get_Type() != -1)
					fireballs[i].Decriment_Health();
			}
		}
		return return_value;
	}
	
	public void Update_X_Position(){
		
		super.Update_X_Position();
		for (int i = 0; i < fireballs.length; i++)
		{
			fireballs[i].Update_X_Position();
		}
	}
	
	public void Update_Y_Position(){
		
		super.Update_Y_Position();
		for (int i = 0; i < fireballs.length; i++)
		{
			fireballs[i].Update_Y_Position();
		}
	}
	
	public void Render(int zoom, int cur_tile_x, int cur_tile_y, GameContainer gc, Graphics g){
		
		super.Render(zoom, cur_tile_x, cur_tile_y, gc, g);
		
		if (Visible)
		{
			for (int i = 0; i < fireballs.length; i++)
			{
				fireballs[i].Render(zoom, cur_tile_x, cur_tile_y, gc, g);
			}
		}
	}
	
	public int Get_Type(){
		
		return Type;
	}
}
