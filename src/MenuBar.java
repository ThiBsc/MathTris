import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * 
 * La barre de menu comportant toutes les actions dont nous aurons besoin
 *
 */
public class MenuBar extends JMenuBar {
	
	private static final long serialVersionUID = 7325618304237937679L;
	private JMenu mnuJeu, mnuAide;
	private JMenuItem mniCommencer, mniRecommencer, mniPause, mniQuitter, mniHelp, mniAPropos;
	private MenuBarMethod mbm;
	
	public MenuBar(Plateau p){
		super();
		mbm = new MenuBarMethod(p);
		initUI();
	}
	
	private void initUI(){		 
		mnuJeu = new JMenu("Jeu");
		mnuAide = new JMenu("?");
		
		mniCommencer = new JMenuItem("Commencer");
		mniRecommencer = new JMenuItem("Recommencer");
		mniPause = new JMenuItem("Pause/Reprendre");
		mniQuitter = new JMenuItem("Quitter");
		mniHelp = new JMenuItem("Aide");
		mniAPropos = new JMenuItem("A propos");
		/* action item command */
        mniCommencer.setActionCommand("commencer");
        mniCommencer.addActionListener(mbm);
        mniRecommencer.setActionCommand("recommencer");
        mniRecommencer.addActionListener(mbm);
        mniPause.setActionCommand("pause");
        mniPause.addActionListener(mbm);
        mniQuitter.setActionCommand("quitter");
        mniQuitter.addActionListener(mbm);
        mniHelp.setActionCommand("help");
        mniHelp.addActionListener(mbm);
        mniAPropos.setActionCommand("info");
        mniAPropos.addActionListener(mbm);
        // repartition des menus
	    mnuJeu.add(mniCommencer);
	    mnuJeu.add(mniRecommencer);
	    mnuJeu.add(mniPause);
	    mnuJeu.addSeparator();
		mnuJeu.add(mniQuitter);
		mnuAide.add(mniHelp);
		mnuAide.add(mniAPropos);
		
		this.add(mnuJeu);
		this.add(mnuAide);
	}
}
