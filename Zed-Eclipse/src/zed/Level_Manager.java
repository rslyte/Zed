/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zed;

// Java for file reading
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Java for exception handling
import java.util.logging.Logger;

// Slick for drawing to screen and input
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Richard Barella Jr.
 * @author Adam Bennett
 * @author Ryan Slyter
 */

// TODO: Edit Level Manager

public class Level_Manager {   
    
    private static final int MAX_WIDTH = 100;
    private static final int MAX_HEIGHT = 100;
    private static final int TILE_SIZE = 16;
    
    int width;
    int height;
    int xpos;
    int ypos;
    int scale;
    Image[][] bot_tile = new Image[MAX_HEIGHT][MAX_WIDTH];
    
    Player_Character player;
    
    // Instantiate Level
    public Level_Manager(String filepath){ // TODO: design lvl file and then make this to parse that file
        
        BufferedReader br = null;
        
        width = 0;
        height = 0;
        xpos = 0;
        ypos = 0;
        scale = 0;
        
        try
        {
            String cur_line;
            char[] file_chars = new char[256];
            String[] file_files = new String[256];
            int file_num = 0;
            int cur_y = 0;
            
            br = new BufferedReader(new FileReader(filepath));
            
            while ((cur_line = br.readLine()) != null)
            {
                if (cur_line.contains(" = "))
                {
                    file_files[file_num] = cur_line.substring(4);
                    file_chars[file_num] = cur_line.charAt(0);
                    file_num++;
                }
                else if (cur_line.trim().startsWith("width:"))
                {
                    width = Integer.parseInt(cur_line.trim().substring(6).trim());
                }
                else if (cur_line.trim().startsWith("height:"))
                {
                    height = Integer.parseInt(cur_line.trim().substring(7).trim());
                }
                else if (cur_line.trim().startsWith("xpos:"))
                {
                    xpos = Integer.parseInt(cur_line.trim().substring(5).trim());
                }
                else if (cur_line.trim().startsWith("ypos:"))
                {
                    ypos = Integer.parseInt(cur_line.trim().substring(5).trim());
                }
                else if (cur_line.trim().startsWith("scale:"))
                {
                    scale = Integer.parseInt(cur_line.trim().substring(6).trim());
                }
                else if (!cur_line.trim().isEmpty())
                {
                    for (int i = 0; i < cur_line.trim().length(); i++)
                    {
                        try {
                            int loc = 0;
                            while (loc < 256 && file_chars[loc] != cur_line.charAt(i))
                            {
                                loc++;
                            }
                            if (i < MAX_WIDTH && cur_y < MAX_HEIGHT && loc < 256)
                            {
                                bot_tile[i][cur_y] = new Image(
                                        file_files[loc],
                                        false, Image.FILTER_NEAREST);
                            }
                        } catch (SlickException ex) {
                            ex.printStackTrace();
                            Logger.getLogger(Level_Manager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                    }
                    cur_y++;
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    // Edit class variables
    public void setwidth(int newwidth){
        
        width = newwidth;
    }
    public void setheight(int newheight){
        
        height = newheight;
    }
    public void setxpos(int newxpos){
        
        xpos = newxpos;
    }
    public void setypos(int newypos){
        
        ypos = newypos;
    }
    public void setscale(int newscale){
        
        scale = newscale;
    }
    public void settile(Image newtile, int x, int y){
        
        bot_tile[x][y].equals(newtile);
    }
    
    public void display(GameContainer gc, Graphics g){
        
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                g.drawImage(bot_tile[i][j],
                        xpos + i*TILE_SIZE*scale,
                        ypos + j*TILE_SIZE*scale,
                        xpos + i*TILE_SIZE*scale + TILE_SIZE*scale,
                        ypos + j*TILE_SIZE*scale + TILE_SIZE*scale,
                        0, 0,
                        TILE_SIZE, TILE_SIZE);
            }
        }
    }
}
