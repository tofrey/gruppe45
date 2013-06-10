package gameobjects;

import java.awt.Toolkit;

import local.Fs;

// todesbaum class
public class Poisonous_Tree extends Figure {
	public int fog = 10;
	public Poisonous_Tree (int x, int y) {
		super(2,x,y);
		this.dmg = 1;
		this.image = Toolkit.getDefaultToolkit().getImage(Fs.img_pfad+"todesbaum.png");
	}
}