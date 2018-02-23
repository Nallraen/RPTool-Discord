package fr.originalpainz.rptool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class MainWindow extends JFrame implements ActionListener{
	Main main;
	
	
	// La NavBar
	private JMenuBar mB = new JMenuBar();
	private JMenu t = new JMenu("About");
	private JMenuItem Ab1 = new JMenuItem("Version"),
					  Ab2 = new JMenuItem("Développeurs"),
					  Ab3 = new JMenuItem("Site Web");

	
	// Le dit panel :D
	JPanel be = new JPanel();
	
	// Une Liste de test
	String[] options = {"Test 1", "Test 2", "Test 3", "..."};
 	
	// Les DropList
	JComboBox c = new JComboBox(options);
	
	// Les boutons
	JButton b1 = new JButton("Entrer");
	JButton b2 = new JButton("Bouton B");
	
	// Le petit bienvenue au top de l'appli
	JLabel _p = new JLabel("Bienvenue " + main.pseudo + " sur RPTool !");
	
	JLabel vide = new JLabel("");
	
	JTextArea info = new JTextArea("Alpha_1.6: "
									+ "\n Désormais lorsque vous fermez l'applications,\n "
									+ "les dernières valeurs que vous aurez saisis seront sauvegardés !\n\n"
									+ "Il est déconseillé de mettre des accents, ces derniers\n"
									+"ne sont pas pris en compte et créer des erreurs...\n"
									+ "Cordialement,\nPainZ");
	
	// Les TextFields
	JTextField jtf = new JTextField();
	JTextField jtf2 = new JTextField();
	
	
	
	
    public MainWindow(){

        super("OriginalPainZ");
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(main.title);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);
        be.setBackground(Color.white);
        be.setLayout(new BorderLayout());
        
        
        // La NavBar
        t.add(Ab1);
        t.add(Ab2);
        t.addSeparator();
        t.add(Ab3);
        
        mB.add(t);
        setJMenuBar(mB);
        
        // Paramêtrage des boutons de la NavBar
        Ab1.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent event){
        		showVersion();
        	}


        });
        Ab2.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent event){
        		showDev();
        	}
        });
        Ab3.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent event){
        		openWeb();
        	}
        });
        
        
        // L'application
        JPanel a = new JPanel();
        JPanel b = new JPanel();
        a.setBackground(Color.white);
        b.setBackground(Color.WHITE);
        
        info.setBackground(Color.white);
        
        Font font = new Font("Tahoma", Font.ITALIC ,15);
        jtf.setFont(font);
        jtf.setPreferredSize(new Dimension(150, 40));
        jtf.setForeground(Color.BLACK);
        jtf.setBorder(BorderFactory.createTitledBorder("Ligne haute"));
        jtf2.setFont(font);
        jtf2.setPreferredSize(new Dimension(150, 40));
        jtf2.setForeground(Color.BLACK);
        jtf2.setBorder(BorderFactory.createTitledBorder("Ligne Basse"));
        
        a.add(jtf);
        a.add(jtf2);
        a.add(b1);
        a.add(vide);
        a.add(info);
        b.add(_p);
        
        b1.addActionListener(this);
        
        be.add(a, BorderLayout.CENTER);
        be.add(b, BorderLayout.NORTH);
      
        
        // Test de la DropList 
        
/*         
        
        c.setPreferredSize(new Dimension(200, 30));
        c.setSelectedIndex(3); // Pour choisir il faut faire -> Total options - 1 = numéro option... Ici {...} est la 4è option donc 4 - 1 = 3 :)
        
        
        JPanel a = new JPanel();
        a.add(test);
        a.add(c);
        
        be.add(a, BorderLayout.NORTH);
        be.add(b1, BorderLayout.SOUTH);
        
        b1.addActionListener(this);
 */       
        // Test des boutons
        
/*       
 
        be.add(pan, BorderLayout.CENTER);
        
        b1.addActionListener(this);
        
        be.add(b1, BorderLayout.SOUTH);
        
        // Config du texte
        Font font = new Font("Tahoma", Font.ITALIC+ Font.BOLD, 15);
        test.setFont(font);
        test.setForeground(Color.PINK);
        test.setHorizontalAlignment(JLabel.CENTER);
        
      be.add(test, BorderLayout.NORTH);
        
    */      
        setContentPane(be);
        
        setVisible(true);
    }
    // LE BOUTON !

    
    public void actionPerformed(ActionEvent arg0){
    	
    	Properties prop = new Properties();
    	OutputStream output=null;
    	
    	try{
    		output = new FileOutputStream("config.properties");
    		
        	
        	long i = new Date().getTime();
        	String textA = jtf.getText();
        	String textB = jtf2.getText();
        	DiscordRPC.DiscordRunCallbacks();
        	DiscordRichPresence r = new DiscordRichPresence();
        	if(textA.isEmpty()){
        		r.state = textB;
        		r.details = "";
        		r.startTimestamp = i + 5*60;
        	//	r.largeImageKey = "painz";
        		r.partyId ="NothingRightHere";
                r.matchSecret = "4b2fdce12f639de8bfa7e3591b71a0d679d7c93f";
        		r.spectateSecret ="e7eb30d2ee025ed05c71ea495f770b76454ee4e0";
        		r.joinSecret ="MTI4NzM0OjFpMmhuZToxMjMxMjM=";
        		r.instance = 1;
            	DiscordRPC.DiscordUpdatePresence(r);
            	
            	// Save values in config File
            	prop.setProperty("ligneBasse", textB);
            	prop.store(output, main.ConfigInfo);
            	
        	}else if(textB.isEmpty()){
        		r.state = "";
        		r.details = textA;
        		r.startTimestamp = i + 5*60;
        	//	r.largeImageKey = "painz";
        		r.partyId ="NothingRightHere";
                r.matchSecret = "4b2fdce12f639de8bfa7e3591b71a0d679d7c93f";
        		r.spectateSecret ="e7eb30d2ee025ed05c71ea495f770b76454ee4e0";
        		r.joinSecret ="MTI4NzM0OjFpMmhuZToxMjMxMjM=";
        		r.instance = 1;
            	DiscordRPC.DiscordUpdatePresence(r);
            	
            	// Save values in config file
            	prop.setProperty("ligneHaute", textA);
            	prop.store(output, main.ConfigInfo);
        	}else{
        		r.state = textB;
        		r.details= textA;
        		r.startTimestamp = i + 5*60;
        //		r.largeImageKey = "painz";
        		r.partyId ="NothingRightHere";
                r.matchSecret = "4b2fdce12f639de8bfa7e3591b71a0d679d7c93f";
        		r.spectateSecret ="e7eb30d2ee025ed05c71ea495f770b76454ee4e0";
        		r.joinSecret ="MTI4NzM0OjFpMmhuZToxMjMxMjM=";
        		r.instance = 1;
        	
        		
            	DiscordRPC.DiscordUpdatePresence(r);
            	
            	// Save values
            	prop.setProperty("ligneHaute", textA);
            	prop.setProperty("ligneBasse", textB);
            	
            	prop.store(output, main.ConfigInfo);
        	}
    		
    		
    	} catch(IOException io) {
    		io.printStackTrace();
    	} finally{
    		if(output!=null){
    			try{
    				output.close();
    			}catch(IOException io){
    				io.printStackTrace();
    			}
    		}
    	}
    	


    } 
    
/*    public void actionPerformed(ActionEvent arg0){
    	c.getSelectedItem();
    	String text = c.getSelectedItem().toString(); // Texte à afficher sur le RP Discord
    	DiscordRPC.DiscordRunCallbacks();
    	DiscordRichPresence r = new DiscordRichPresence();
    	r.state = "Suivez mon live : twitch.com/nallraen";
    	r.details= text;
    	DiscordRPC.DiscordUpdatePresence(r);
    } */
    
    
  /*  public void actionPerformed(ActionEvent arg0){
		score++;
        DiscordRPC.DiscordRunCallbacks();
		test.setText("Test : " + score + " ! ");
        DiscordRichPresence rich = new DiscordRichPresence();         
        rich.state = "Surement dans un pays lointain...";
        rich.details = "Vous avez : " + score + " points !";
        DiscordRPC.DiscordUpdatePresence(rich);
    } */
    
    // FIN BOUTON
    
    
	private void showVersion() {
		
		//TODO: Remplir ceci ici...
		
	}
	
	private void showDev() {
		
		// TODO: Remplir ceci ici...
		
		
	}
	
	private void openWeb() {
		
		// TODO: Remplir ceci ici...
		
	}

}
