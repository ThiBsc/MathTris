import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 * 
 * Fenetre d'aide du jeu
 *
 */
public class HelpFrame extends JFrame {
	
	private static final long serialVersionUID = 8983235293628278541L;
	private JEditorPane area;
	private JScrollPane editorScrollPane;
	private HTMLEditorKit kit;
	
	public HelpFrame(){
		area = new JEditorPane();
		initCSS();
		area.setEditable(false);
		area.setEditorKit(kit);
		setTitle("Aide");
		setSize(400, 500);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		URL helpPage = getClass().getResource("/help/help.html");
		try {
			area.setPage(helpPage);
		} catch (IOException e) {
			area.setText("Erreur de chargement de l'aide!");
		}
		editorScrollPane = new JScrollPane(area);
		editorScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setPreferredSize(new Dimension(250, 145));
		editorScrollPane.setMinimumSize(new Dimension(10, 10));
		add(editorScrollPane, BorderLayout.CENTER);
	}
	public void initCSS(){
		kit = new HTMLEditorKit();
		StyleSheet css = kit.getStyleSheet();
		css.addRule("div.text{word-wrap: break-word; width 950px;}");
		css.addRule("h1, h3{color: blue;}");
		css.addRule("h2{color: green;}");
		kit.setStyleSheet(css);
	}
}
