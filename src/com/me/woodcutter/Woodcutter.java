package com.me.woodcutter;

import org.osbot.rs07.api.Bank;
import org.osbot.rs07.api.Inventory;
import org.osbot.rs07.api.Walking;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.event.WebWalkEvent;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;

@ScriptManifest(
	    author = "Jonas", 
	    info = "No description.", 
	    logo = "",  
	    name = "Woodcutter", 
	    version = 0.1
	)
public class Woodcutter extends Script {

    boolean reachedArea = false;

    final Area WILLOW_AREA = new Area(3081, 3223, 3092, 3239);
	final Area TREE_AREA = new Area(3138, 3261, 3136, 3200);
	final Area BANK_AREA = new Area(3092, 3240, 3097, 3246);

    int delay = 0;

	public void onStart() { 
		// Script starts.
        logger.info("Woodcutter started!");
	}

	public void onExit() {
	    // Script exits.
    }

    public void walkToDestination(Area area) {
		logger.info("Walking to area.");

		// Walk to area.
		WebWalkEvent webEvent = new WebWalkEvent(area);

		// Compute new path to the destination.
		webEvent.useSimplePath();

		execute(webEvent);
	}

	@SuppressWarnings("unchecked")
    public void chopSimpleTrees() {
		if(TREE_AREA.contains(myPlayer())) {
			reachedArea = true;
		} else if(!reachedArea) {
			walkToDestination(TREE_AREA);
		}

		if(reachedArea) {
			RS2Object tree = getObjects().closest(o -> o.getName().equals("Tree"));

			if (tree != null && tree.exists()
					&& tree.hasAction("Chop down")
					&& !myPlayer().isAnimating() && !myPlayer().isMoving()) {
				if(tree.isVisible()) {
					tree.interact("Chop down");
					delay = random(700, 800);
				} else {
					camera.toEntity(tree);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
    public void chopWillowTrees() {
		if (WILLOW_AREA.contains(myPlayer())) {
			reachedArea = true;
		} else if(!reachedArea) {
			walkToDestination(WILLOW_AREA);
		}

		if(reachedArea) {
			RS2Object tree = getObjects().closest(o -> o.getName().equals("Willow"));

			if (tree != null && tree.exists()
					&& tree.hasAction("Chop down")
					&& !myPlayer().isAnimating() && !myPlayer().isMoving()) {
				if (tree.isVisible()) {
					tree.interact("Chop down");
					delay = random(700, 800);
				} else {
					camera.toEntity(tree);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void goToBank() {
		if (BANK_AREA.contains(myPlayer())) {

			if (bank.isOpen()) {
				bank.depositAll();
			} else {
				RS2Object bankbooth = getObjects().closest(o -> o.getName().equals("Bank booth"));

				logger.debug("Accessing bank.");

				if (bankbooth != null && bankbooth.hasAction("Bank")) {
					if (bankbooth.isVisible()) {
						bankbooth.interact("Bank");
						delay = random(600, 800);
					} else {
						camera.toEntity(bankbooth);
					}
				}
			}
		} else {
			walkToDestination(BANK_AREA);
		}
	}

	@Override
	public int onLoop() {


        if(!this.inventory.isFull()) {

			int cutting_level = skills.getDynamic(Skill.WOODCUTTING);

			if(cutting_level >= 30) {
				chopWillowTrees();
			} else {
				chopSimpleTrees();
			}
		} else {
			reachedArea = false;

			goToBank();
		}
 
        return 50 + delay;
	}

    @Override
    public void onPaint(Graphics2D g) {
        super.onPaint(g);

        g.setColor(Color.BLUE);
        g.draw3DRect(mouse.getPosition().x, mouse.getPosition().y, 16, 16, true);

		g.setColor(Color.WHITE);
        g.drawString("Woodcutter Bot", 10, 10);
    }

}