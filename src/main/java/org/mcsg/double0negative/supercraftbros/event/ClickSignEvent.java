package org.mcsg.double0negative.supercraftbros.event;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.mcsg.double0negative.supercraftbros.Game;
import org.mcsg.double0negative.supercraftbros.GameManager;

public class ClickSignEvent implements Listener{

    @EventHandler(priority = EventPriority.HIGHEST)
    public void clickHandler(PlayerInteractEvent e){

        if(!(e.getAction()==Action.RIGHT_CLICK_BLOCK || e.getAction()==Action.LEFT_CLICK_BLOCK)) return;
        

        Block clickedBlock = e.getClickedBlock(); 
        if(!(clickedBlock.getType()==Material.SIGN_POST || clickedBlock.getType()==Material.WALL_SIGN)) return;
        Sign thisSign = (Sign) clickedBlock.getState();
        String[] lines = thisSign.getLines();
        if(lines[0].equalsIgnoreCase("[class]")){
        	String cl = lines[1];
        	Game g = GameManager.getInstance().getGame(GameManager.getInstance().getPlayerGameId(e.getPlayer()));
        	g.setPlayerClass(e.getPlayer(), GameManager.getInstance().classList.get(cl.toLowerCase()).newInstance(e.getPlayer()));
        	g.getPlayerClass(e.getPlayer()).PlayerSpawn();
        }
        else if(lines[0].equalsIgnoreCase("[join]")){
        	int game = Integer.parseInt(lines[1]);
        	Game g = GameManager.getInstance().getGame(game);
        	g.addPlayer(e.getPlayer());
        }
        else if (lines[0].equalsIgnoreCase("[leave]")) {
        	int game = GameManager.getInstance().getPlayerGameId(e.getPlayer());
    		if(game != -1){
    			GameManager.getInstance().getGame(game).removePlayer(e.getPlayer(), false);
    		}
        }
	}	
}
