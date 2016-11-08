import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * 
 * Classe qui traite les actions de la barre de menu
 *
 */
public class MenuBarMethod implements ActionListener {
	
	private Plateau plt;
	
	public MenuBarMethod(Plateau p){
		plt = p;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if("quitter".equals(e.getActionCommand())){
            System.exit(0);
        }		
		else if("help".equals(e.getActionCommand())){
			HelpFrame hf = new HelpFrame();
            hf.addWindowListener(new WindowListener() {
				@Override
				public void windowOpened(WindowEvent arg0) {
		            plt.pausePartie();
				}				
				@Override
				public void windowIconified(WindowEvent arg0) {
				}				
				@Override
				public void windowDeiconified(WindowEvent arg0) {
				}				
				@Override
				public void windowDeactivated(WindowEvent arg0) {
				}				
				@Override
				public void windowClosing(WindowEvent arg0) {
				}				
				@Override
				public void windowClosed(WindowEvent arg0) {
		            plt.pausePartie();		            
				}				
				@Override
				public void windowActivated(WindowEvent arg0) {
				}
			});
            hf.setVisible(true);
		}		
		else if("info".equals(e.getActionCommand())){
			plt.afficherAPropos();
		}
		else if("commencer".equals(e.getActionCommand())){
			plt.recommencerPartie();
		}		
		else if("recommencer".equals(e.getActionCommand())){
			plt.recommencerPartie();
		}		
		else if("pause".equals(e.getActionCommand())){
			plt.pausePartie();
		}
	}
}