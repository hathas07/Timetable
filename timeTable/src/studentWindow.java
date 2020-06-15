import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class studentWindow extends JFrame{
	
	public studentWindow() {
		super("Student TimeTable");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(1200,800);
		this.setLocationRelativeTo(null);
		
		JPanel contentPane = (JPanel) this.getContentPane();

		contentPane.add(createMenuBar(), BorderLayout.NORTH);
	}
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		//boutons principaux
		JMenu menuFichier = new JMenu("User");
		JMenu menuView = new JMenu("View");
		JMenu menuAide = new JMenu("Help");
		
		//sous boutons du premier bouton
		JMenuItem logout = new JMenuItem("Log out");
		JMenuItem Exit = new JMenuItem("Exit");
		menuFichier.add(logout);
		menuFichier.addSeparator();
		menuFichier.add(Exit);

		
		  //ajout des boutons principaux
		 menuBar.add(menuFichier);
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
		
		studentWindow window = new studentWindow();
		window.setVisible(true);
	}

}
