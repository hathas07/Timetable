import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class teacherWindow extends JFrame {
	
	public teacherWindow() {
		super("Teacher TimeTable");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(1200,800);
		this.setLocationRelativeTo(null);
		
		JPanel contentPane = (JPanel) this.getContentPane();

		contentPane.add(createMenuBar(), BorderLayout.NORTH);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				int btnCroix = JOptionPane.showConfirmDialog(teacherWindow.this, "Etes-vous sur de vouloir quitter ?",
						"Choix de fermeture", JOptionPane.YES_NO_OPTION);
				if (btnCroix == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
			}); 
	}
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		//boutons principaux
		JMenu menuFichier = new JMenu("User");
		JMenu menuView = new JMenu("View"); 
		JMenu menuEdition = new JMenu("Edition"); 
		JMenu menuAide = new JMenu("Help");
		
		//sous boutons du premier bouton
		JMenuItem logout = new JMenuItem("Log out");
		JMenuItem Exit = new JMenuItem("Exit");
		menuFichier.add(logout);
		menuFichier.addSeparator();
		menuFichier.add(Exit);

		
		  //ajout des boutons principaux
		 menuBar.add(menuFichier);
		 menuBar.add(menuEdition);
		 menuBar.add(menuView);
		 menuBar.add(menuAide);
		 
		
		
		//raccourcis clavier
		menuFichier.setMnemonic('F'); //ALT + MNEMONIC
		logout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		
		
		return menuBar;
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		
		teacherWindow window = new teacherWindow();
		window.setVisible(true);
	}

}
