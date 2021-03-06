package gameobjects;

//parent class of Fox, Hero, Goal, Poisonous_Tree, Wall

import java.awt.Image;

public class Figure {
	
	public Image image;
	public Inventory bag;
	public Image off = local.Pics.fireball;
	public Image def;
	public String name = "blubb";
	public int hp = 100;						//health points
	public int hp_pot = 0;
	public int mp = 0;						//mana points
	public int mp_pot = 0;
	public int ep = 0;
	public int level = 0;
	public int defe = 1;
	public int bugs = 0;
	public int lives = 0;
	public int dmg;							//damage
	public boolean destroyable = false;
	public int type;						//describes what type of figure this is:
											//0 == goal, shop
											//1 == wall
											//2 == poisonous tree
											//3 == fox
											//4 == player
											//5 == boss
											//8 == item
											//9 == spell
	public int nr;							//index in obj_list
	public int layer = 0;
	public int pos_x;
	public int start_x;
	public int pos_y;
	public int start_y;
	public int fog = 20;					//only for poisonous trees, shop
	public char direction = 's';			//used for spells, random movement
	
	public Figure(int figure_type, int x, int y, Image img) {
		type = figure_type;
		pos_x = x;
		pos_y = y;
		image = img;

	}
}
