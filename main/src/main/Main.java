package main;

import gameobjects.Figure;
import local.Fs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Main{
public static double scale = 0.5;
public static int board_height = 768;
public static int board_width = 1024;
public static int off = 50;						//boarder at the side of bord
public static int Nr_of_Players = 1;			//1 == Singleplayer , 2 == Multiplayer
public static int level = 3;
public static int room = 1;
public static Random rand = new Random();		//variable for random movement
public static boolean run = false;				//movements are made when run == true, then run ist set to false until painted
public static boolean ingame = true;			//ingame == false while in menu
public static boolean shop = false;				//can shop be openend?
public static boolean music = true;				//music on/off
public static boolean go = true;
public static boolean onOff = true;

public static ArrayList<Figure> obj_list = new ArrayList<Figure>();	//holds all figures in game
//0 reserved for board
//1 reserved for goal
//2 reserved for player 1
//3 reserved for player 2
//4 reserved for shop

public graphics.MasterFrame window;



public static void main(String[] args) throws IOException
	{
		Fs.init();
		Game game = new Game();										//creates Game logic		
		game.start();												//starts Game logic
		graphics.MasterFrame window = new graphics.MasterFrame();	//creates RenderFrame
		
		graphics.Npc test = new graphics.Npc();
	
		//Gui start = new Gui();										//Starts Gui
		
		while(onOff)
		{
			if (go == true)											//wait boolean from Gui to start
			{
				window.startNow();									//sets MasterFrame
				onOff = false;										//if started once cannot start again
			};
		}
	}
}