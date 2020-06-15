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
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;


public class FirstTimeTable extends JFrame {

	/*
	 * private JButton btn1 = new JButton("Fichier"); private JButton btn2 = new
	 * JButton("Edit"); private JButton btn3 = new JButton("Search"); private
	 * JButton btn4 = new JButton("Run"); private JButton btn5 = new
	 * JButton("Help");
	 */
	
	private void randomEvent(ActionEvent event){
		System.out.println("test event!");
		JOptionPane.showMessageDialog(this, "Événement random");
	}
	
	
	
	private void openLoginWindow(ActionEvent event){
		loginWindow window = new loginWindow();
		window.setVisible(true);
		dispose();
	}
	
	public FirstTimeTable() {
		super("TimeTable");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(1200,800);
		this.setLocationRelativeTo(null);
		
		JPanel contentPane = (JPanel) this.getContentPane();

		contentPane.add(createMenuBar(), BorderLayout.NORTH);
		contentPane.add(new JLabel("Please Log in to start"), BorderLayout.SOUTH);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				int btnCroix = JOptionPane.showConfirmDialog(FirstTimeTable.this, "Etes-vous sur de vouloir quitter ?",
						"Choix de fermeture", JOptionPane.YES_NO_OPTION);
				if (btnCroix == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
			}); 
		
		contentPane.add(createCenter(), BorderLayout.CENTER);
		
			/*
			 * ImageIcon image1 = new ImageIcon("imt.jpg"); JLabel label1 = new JLabel("",
			 * image1, JLabel.CENTER); JPanel panel1 = new JPanel(new BorderLayout());
			 * panel1.add( label1, BorderLayout.CENTER ); JLabel lblNewLabel1 = new
			 * JLabel("", image1, JLabel.CENTER); contentPane.add(lblNewLabel1,
			 * BorderLayout.CENTER);
			 */
		
		}
	
	private JPanel createCenter() {
		JPanel center = new JPanel();
		JLabel welcome = new JLabel("Welcome !", SwingConstants.CENTER);
		center.add(welcome);
		
		return center;
	}
	
		/*
		 * private JToolBar createToolBar() { JToolBar ToolBar = new JToolBar();
		 * 
		 * ToolBar.add(btn1); btn1.addActionListener( (e) -> randomEvent(e) );
		 * 
		 * ToolBar.add(btn2); btn2.addActionListener( (event) ->
		 * System.out.println("non!") );
		 * 
		 * ToolBar.add(btn3); btn3.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { System.out.print("ptet"); } });
		 * 
		 * ToolBar.add(btn4); ToolBar.add(btn5);
		 * 
		 * colorMouseOver(btn1); colorMouseOver(btn2); colorMouseOver(btn3);
		 * colorMouseOver(btn4); colorMouseOver(btn5);
		 * 
		 * return ToolBar; }
		 */
	
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		//boutons principaux
		JMenu menuFichier = new JMenu("User");
		JMenu menuAide = new JMenu("Help");
		
		//sous boutons du premier bouton
		JMenuItem login = new JMenuItem("Log in");
		login.addActionListener(this::openLoginWindow);
		JMenuItem Exit = new JMenuItem("Exit");
		menuFichier.add(login);
		menuFichier.addSeparator();
		menuFichier.add(Exit);
		 
		  //ajout des boutons principaux
		 menuBar.add(menuFichier);
		 menuBar.add(menuAide);
		 	
		//raccourcis clavier
		menuFichier.setMnemonic('F'); //ALT + MNEMONIC
		login.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK));


		return menuBar;
	}
	

	
	/*
	 * private void colorMouseOver(JButton btn) { btn.addMouseListener(new
	 * MouseAdapter() {
	 * 
	 * @Override public void mouseExited(MouseEvent e) { // TODO Auto-generated
	 * method stub super.mouseExited(e); btn.setForeground(Color.BLACK); }
	 * 
	 * @Override public void mouseEntered(MouseEvent e) { // TODO Auto-generated
	 * method stub super.mouseEntered(e); btn.setForeground(Color.RED); } }); }
	 */


	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		
		FirstTimeTable firstWindow = new FirstTimeTable();
		firstWindow.setVisible(true);
	}

}
