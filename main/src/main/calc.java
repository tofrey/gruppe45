package main;

import java.awt.Rectangle;
import java.io.File;
import lokal.Fs;
import spielobjekte.Spielfeld;

/*
 * 
 * 	Berechnet Kollisionen und Auswirkungen auf Spielfigur
 *  Wird in der Renderer-Klasse erstellt und aufgerufen
 */

public class calc {
	
    public static int P1_richtung_x = 0;              //Startposition X-Achse
    public static int P1_richtung_y = 0;            //Startposition Y-Achse
    public static boolean ingame=true;
    public static boolean neues_spiel = false;
    public Spielfeld board;
    public int held_breite;
    public int held_hoehe;
    public int wand_breite;
    public int wand_hoehe;
    public int killer_breite;
    public int killer_hoehe;
    public int ziel_breite;
    public int ziel_hoehe;
    Rectangle w;
    Rectangle h;
    Rectangle k;
    Rectangle kb;
    Rectangle z;
	public calc(Spielfeld board) {
		this.board = board;
	}
	
	// adjusting values for new level (bg image, level file, ...)
	public void go_to_next_room(){
		board.datei = Integer.toString(board.naechster_raum)+".txt";
		board.create_room(Integer.toString(board.naechster_raum));
		board.set_board_bg_image(board.naechster_raum);
		board.naechster_raum++;
		board.datei = Integer.toString(board.naechster_raum)+".txt";
		board.fdatei = new File(Fs.data_pfad+board.datei);
	}
	
	public void updateData () { // Berechnungen, Kollisionen
		boolean collision = false;
		int pos_x_alt;
		int pos_y_alt;
		int schaden;
		board.nachricht = "";
		// für jeden Held Kollisionen berechnen
		// Kollision wird gegen jedes Objekt einzeln geprüft. Geht bestimmt schöner
	for (int i=0 ; i < Spielfeld.obj_list.size() ; i++){
		switch(Spielfeld.obj_list.get(i).type){
			case 4:
				pos_x_alt = Spielfeld.obj_list.get(1).pos_x;
				pos_y_alt = Spielfeld.obj_list.get(1).pos_y;
				schaden=0;
				Spielfeld.obj_list.get(1).pos_x = Spielfeld.obj_list.get(1).pos_x + calc.P1_richtung_x * Spielfeld.obj_list.get(1).geschwindigkeit;
				Spielfeld.obj_list.get(1).pos_y = Spielfeld.obj_list.get(1).pos_y + calc.P1_richtung_y * Spielfeld.obj_list.get(1).geschwindigkeit;
				held_breite = Spielfeld.obj_list.get(1).image.getWidth(null);
				held_hoehe = Spielfeld.obj_list.get(1).image.getHeight(null);
				if (Spielfeld.obj_list.get(1).pos_y>GUI_Main.FRAMESIZE_Y-board.rand_x-held_hoehe || Spielfeld.obj_list.get(1).pos_y<board.rand_x) {
					collision = true;
				}
				if (Spielfeld.obj_list.get(1).pos_x>GUI_Main.FRAMESIZE_X-board.rand_y-held_breite || Spielfeld.obj_list.get(1).pos_x<board.rand_y) {
					collision = true;
				}
				h = new Rectangle(Spielfeld.obj_list.get(1).pos_x+40, Spielfeld.obj_list.get(1).pos_y-50, held_breite-40, held_hoehe-50);
			case 1:
				wand_breite = Spielfeld.obj_list.get(i).image.getWidth(null);
				wand_hoehe = Spielfeld.obj_list.get(i).image.getHeight(null);
				w = new Rectangle(Spielfeld.obj_list.get(i).pos_x+40, Spielfeld.obj_list.get(i).pos_y-50, wand_breite-40, wand_hoehe-50);
				if (h.intersects(w)) {
					collision = true;
				}
				break;
			case 3:
	        	killer_breite = Spielfeld.obj_list.get(i).image.getWidth(null);
	        	killer_hoehe = Spielfeld.obj_list.get(i).image.getWidth(null);
	        	k = new Rectangle(Spielfeld.obj_list.get(i).pos_x+20, Spielfeld.obj_list.get(i).pos_y-50, killer_breite+5, killer_hoehe);
	        	if (h.intersects(k)) {
	        		collision = true;
	        		schaden = 1000;
	        		board.nachricht = "...von Killerhasen zerfetzt.";
	        	}
	        	break;
			case 2: 
	        	killer_breite = Spielfeld.obj_list.get(i).image.getWidth(null);
	        	killer_hoehe = Spielfeld.obj_list.get(i).image.getWidth(null);
	        	k = new Rectangle(Spielfeld.obj_list.get(i).pos_x+40, Spielfeld.obj_list.get(i).pos_y-50, killer_breite-40, killer_hoehe-50);
	        	if (h.intersects(k)) {
	        		collision = true;
	        		schaden = 1;
	        		board.nachricht = "...am Baum vergiftet.";
	        	}
	        	break;
			case 0:
	        	ziel_breite = Spielfeld.obj_list.get(i).image.getWidth(null);
	        	ziel_hoehe = Spielfeld.obj_list.get(i).image.getWidth(null);
	        	z = new Rectangle(Spielfeld.obj_list.get(i).pos_x+5, Spielfeld.obj_list.get(i).pos_y-ziel_hoehe,ziel_breite-5, ziel_hoehe);
	        	if (h.intersects(z)) {
	        		if (board.fdatei.exists()) {
	        			this.go_to_next_room();
	        		} else { // Es gibt keinen nächsten Raum. Gewonnen!!
	        			Spielfeld.obj_list.get(1).leben_punkte=0;
	        			Spielfeld.obj_list.get(1).anz_leben=0;
	        			board.nachricht = "GEWONNEN! ";
	        		}
	        	}
	        	break;
		}
	}
	if (collision==true && calc.ingame){
		Spielfeld.obj_list.get(1).pos_x = pos_x_alt;
		Spielfeld.obj_list.get(1).pos_y = pos_y_alt;
		Spielfeld.obj_list.get(1).leben_punkte = Spielfeld.obj_list.get(1).leben_punkte-schaden;
	}
	if (Spielfeld.obj_list.get(1).leben_punkte<=0) {
		if (Spielfeld.obj_list.get(1).anz_leben>0) {
			Spielfeld.obj_list.get(1).pos_x = Spielfeld.obj_list.get(1).start_x;
			Spielfeld.obj_list.get(1).pos_y = Spielfeld.obj_list.get(1).start_y;
			Spielfeld.obj_list.get(1).anz_leben--;
		}
		else {
			calc.ingame = false;
			board.nachricht = board.nachricht+"Spielende";
		}
	}
	if (calc.ingame) {
		board.nachricht = "";
		// für jeden Held Kollisionen berechnen
		// Kollision wird gegen jedes Objekt einzeln geprüft. Geht bestimmt schöner
		for (Spielfeld.hero hase: board.heros){
			pos_x_alt = hase.pos_x;
			pos_y_alt = hase.pos_y;
			schaden=0;
			hase.pos_x = hase.pos_x + calc.P1_richtung_x * hase.geschwindigkeit;
			hase.pos_y = hase.pos_y + calc.P1_richtung_y * hase.geschwindigkeit;
			held_breite = hase.image.getWidth(null);
			held_hoehe = hase.image.getHeight(null);
			if (hase.pos_y>GUI_Main.FRAMESIZE_Y-board.rand_x-held_hoehe || hase.pos_y<board.rand_x) {
				collision = true;
			}
			if (hase.pos_x>GUI_Main.FRAMESIZE_X-board.rand_y-held_breite || hase.pos_x<board.rand_y) {
				collision = true;
			}
			h = new Rectangle(hase.pos_x+40, hase.pos_y-50, held_breite-40, held_hoehe-50);
	        for (Spielfeld.wall wand: board.walls){
	        	wand_breite = wand.image.getWidth(null);
	        	wand_hoehe = wand.image.getHeight(null);
	        	w = new Rectangle(wand.pos_x+40, wand.pos_y-50, wand_breite-40, wand_hoehe-50);
	        	if (h.intersects(w)) {
	        		collision = true;
	        	}

        	//collision killerbunny with tree
	        for (Spielfeld.killerbunny killerhase: board.killers){
	        	killer_breite = killerhase.image.getWidth(null);
	        	killer_hoehe = killerhase.image.getHeight(null);
	        	k = new Rectangle(killerhase.pos_x+20, killerhase.pos_y-50, killer_breite+5, killer_hoehe);
	        	// if collision change direction
	        	if (k.intersects(w)) {
	        		killerhase.set_movement_direction();
	        	}
	        }
        }
        for (Spielfeld.todesbaum plant: board.plants){
	        	killer_breite = plant.image.getWidth(null);
	        	killer_hoehe = plant.image.getHeight(null);
	        	k = new Rectangle(plant.pos_x+40, plant.pos_y-50, killer_breite-40, killer_hoehe-50);
	        	if (h.intersects(k)) {
	        		collision = true;
	        		schaden = 1;
	        		board.nachricht = "...am Baum vergiftet.";
	        	}
	        	// collision killerbunny with todesbaum
		        for (Spielfeld.killerbunny killerhase: board.killers){
		        	killer_breite = killerhase.image.getWidth(null);
		        	killer_hoehe = killerhase.image.getHeight(null);
		        	kb = new Rectangle(killerhase.pos_x+20, killerhase.pos_y-50, killer_breite+5, killer_hoehe);
		        	// if collision change direction
		        	if (kb.intersects(w)) {
		        		killerhase.set_movement_direction();
		        	}
		        }
	        }
	        
	        // collision with hero
	        for (Spielfeld.killerbunny killerhase: board.killers){
	        	killerhase.move();
	        	// get killerbunny size
	        	killer_breite = killerhase.image.getWidth(null);
	        	killer_hoehe = killerhase.image.getHeight(null);
	        	k = new Rectangle(killerhase.pos_x+20, killerhase.pos_y-50, killer_breite+5, killer_hoehe);
				// killerbunny collision with frame y value
	        	if (killerhase.pos_y>GUI_Main.FRAMESIZE_Y-board.rand_x-held_hoehe || killerhase.pos_y<board.rand_x) {
					killerhase.move_y = killerhase.move_y*killerhase.geschwindigkeit* (-1);
				}
	        	// killerbunny collision with frame x value
	        	if (killerhase.pos_x>GUI_Main.FRAMESIZE_X-board.rand_y-held_breite || killerhase.pos_x<board.rand_y) {
					killerhase.move_x = killerhase.move_x*killerhase.geschwindigkeit* (-1);
					killerhase.set_image();
				}
	        	// killerbunny collision with hero
	        	if (h.intersects(k)) {
	        		collision = true;
	        		schaden = 1000;
	        		board.nachricht = "...von Killerhasen zerfetzt.";
	        	}
	        }
	        for (Spielfeld.ziel ziel: board.ziele){
	        	ziel_breite = ziel.image.getWidth(null);
	        	ziel_hoehe = ziel.image.getHeight(null);
	        	z = new Rectangle(ziel.pos_x+5, ziel.pos_y-ziel_hoehe,ziel_breite-5, ziel_hoehe);
	        	if (h.intersects(z)) {
	        		if (board.fdatei.exists()) {
	        			this.go_to_next_room();
	        		} else { // Es gibt keinen nächsten Raum. Gewonnen!!
	        			hase.leben_punkte=0;
	        			hase.anz_leben=0;
	        			board.nachricht = "GEWONNEN! ";
	        		}
	        	}
	        }
			if (collision==true && calc.ingame){
				hase.pos_x = pos_x_alt;
				hase.pos_y = pos_y_alt;
				hase.leben_punkte = hase.leben_punkte-schaden;
			}
			if (hase.leben_punkte<=0) {
				if (hase.anz_leben>0) {
					hase.pos_x = hase.start_pos_x;
					hase.pos_y = hase.start_pos_y;
					
					 for (Spielfeld.killerbunny killerhase: board.killers){
						 killerhase.pos_x = killerhase.start_pos_x;
						 killerhase.pos_y = killerhase.start_pos_y;
					 }
					hase.anz_leben--;
				} else {
					calc.ingame = false;
					board.nachricht = board.nachricht+" Spielende.";
				}
				hase.leben_punkte = hase.start_leben_punkte;
			}
		}		
	}
}
