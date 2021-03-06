package movement;

import gameobjects.Create;
import main.Main;
import graphics.Npc;
import graphics.ShopFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	int key;
	
	int STEP = 10;
	
	//moves the rabbit
	//Move: moving figure and testing collisions
	//Spielfeld.obj_list.get(2) = hase
		public void keyPressed (KeyEvent e){
		key = e.getKeyCode();
		if (Main.ingame) { // game is running
			switch(key) {
				
				case KeyEvent.VK_LEFT:// int 37
					Main.obj_list.get(2).direction = 'l';
					Main.obj_list.get(2).image = local.Pics.bunny_l_motion;
					Move.left(Main.obj_list.get(2) , STEP);
					break;
				
				case KeyEvent.VK_RIGHT: // int 18
					Main.obj_list.get(2).direction = 'r';
					Main.obj_list.get(2).image = local.Pics.bunny_r_motion;
					Move.right(Main.obj_list.get(2) , STEP);
					break;
				
				case KeyEvent.VK_UP: // int 38
					Main.obj_list.get(2).direction = 'u';
					Move.up(Main.obj_list.get(2) , STEP);
					break;
				
				case KeyEvent.VK_DOWN: // int 40
					Main.obj_list.get(2).direction = 'd';
					Move.down(Main.obj_list.get(2) , STEP);
					break;
				
				case KeyEvent.VK_SPACE:								//cast Spell
					Create.hero1.cast_Spell();
					break;
				
				case KeyEvent.VK_ESCAPE:							//open menu
					Main.ingame = false;
					System.exit(0);	//change later
					break;	
				
				case KeyEvent.VK_S:									//open shop
					if(Main.shop)
					{
						@SuppressWarnings("unused")
						ShopFrame shop = new ShopFrame();
						Main.ingame = false;
					}
					else if(Main.npc)
					{
						@SuppressWarnings("unused")
						Npc npc = new Npc();
						Main.ingame = false;	
					}
					break;
					
				case KeyEvent.VK_H:
					Create.hero1.use_hp_pot();
					break;
					
				case KeyEvent.VK_M:
					Create.hero1.use_mp_pot();
					break;
					
				case KeyEvent.VK_DELETE:
					if(Main.music == true) Main.music = false;
					else Main.music = true;
					break;
					
				default:
				// nix
			}
		} else { // in menu
			switch(key) {
				case KeyEvent.VK_N: // int 83
					//calc.neues_spiel = true;
					break;
				case KeyEvent.VK_B: //
					System.exit(0);
					break;
				case KeyEvent.VK_ESCAPE:
					Main.ingame = true;
					break;
				default:
				// nothing
			}
		}
	}
	
	//Angleichen, je nach endgültigem Bewegungssystem
	public void keyReleased(KeyEvent e){
		key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT)Main.obj_list.get(2).image = local.Pics.bunny_l;
		if (key == KeyEvent.VK_RIGHT)Main.obj_list.get(2).image = local.Pics.bunny_r;
		// if (key == KeyEvent.VK_UP)
		// if (key == KeyEvent.VK_DOWN)
	}
	
	public void keyTyped(java.awt.event.KeyEvent e) {
		// notwendig, da abstract inherited
	}

}