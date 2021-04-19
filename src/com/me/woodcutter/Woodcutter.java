package com.me.woodcutter;

import org.osbot.rs07.api.Bank;
import org.osbot.rs07.api.Inventory;
import org.osbot.rs07.api.Walking;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(
	    author = "Jonas", 
	    info = "No description.", 
	    logo = "",  
	    name = "Woodcutter", 
	    version = 0.1
	)
public class Woodcutter extends Script {

	public void onStart() { 
		// Script starts.
	}

	public void onExit() {
	    // Script exits.
    }
		
	@Override
	public int onLoop() throws InterruptedException {
        /*
         * @TODO Use this.[object] instead of get();
         */
        Inventory inventory = getInventory();
        Player player = myPlayer();
        Bank bank = getBank();
        Walking walking = getWalking();
        
        if (!inventory.isFull()) {
            // chop

            if(new Area(3081, 3223, 3092, 3239).contains(player)){

                Entity willow = getPlayers().closest("Willow");
 
            if (willow != null) {
                if (willow.isVisible()) {
                    if (!player.isAnimating()) {
                        if (!player.isMoving()) {
                            willow.interact("Chop down");
                            sleep(random(700, 800));
                        }
                    }
                } else {
                	this.camera.toEntity(willow);
                }
            }
            }else{
                walking.walk(new Area(3081, 3223, 3092, 3239));
            }
        } else {
            // bank
            if (new Area(3092, 3240, 3097, 3246).contains(player)) {
                Entity bankbooth = getPlayers().closest(23961);
 
                if (bank.isOpen()) {
                    bank.depositAll();
                } else {
                    if (bankbooth != null) {
                        if (bankbooth.isVisible()) {
                            bankbooth.interact("Bank");
                            sleep(random(700, 800));
                        }else{
                            this.camera.toEntity(bankbooth);
                        }
                    }
                }
 
            } else {
               walking.walk(new Area(3092, 3240, 3097, 3246));
            }
        }
 
        return 50;
	}

}