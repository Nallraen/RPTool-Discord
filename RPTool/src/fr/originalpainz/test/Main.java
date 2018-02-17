package fr.originalpainz.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class Main {
	
	
	
	// Le compte "Admin" (juste pour le fun)
	static String _PSEUDO_ADMIN = ""; // Pseudo à rentrer à la compilation ;)
	static String _MDP_ADMIN = ""; // Idem pour le mdp :D

	
	
	
	// Quelques Values
	static int score = 0;
	public static String title = "RPTool by OriginalPainZ";
	public static String pseudo;
	public static String _Dev = "OriginalPainZ";
	public static String AppId;
	public static String _VERSION = "alpha_1.4";
	
	
    public static boolean ready = false;
	private static String Version;

    public static void main(String[] args){
    	// Message préventif --> Betâ build !
    	JOptionPane warnBeta = new JOptionPane();
    	warnBeta.showMessageDialog(null, "Cette application est encore en phase de développement !", title+" | Application en développement !", JOptionPane.WARNING_MESSAGE);
    	
    	// Test de la version
 /*   	try {
			URL url = new URL("https://xixzayn.000webhostapp.com/version.txt");
			URLConnection con=url.openConnection();
			Version = con.getContent().toString();
			System.out.println(con.getContent());
			System.out.println(Version);
			if(Version != _VERSION){
				Update u = new Update();
				return;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
   */ 	
    	
    	// Le pseudo
    	JOptionPane p = new JOptionPane();
    	pseudo = p.showInputDialog(null, "Veuillez entrez votre pseudo: ", title, JOptionPane.QUESTION_MESSAGE);

    	
    	// La connectivité au jeu
    	JOptionPane co = new JOptionPane(), co2 = new JOptionPane();
    	if(pseudo.equals(_Dev)){
    		AppId = co.showInputDialog(null, "Veuillez entrez l'ID de votre jeu/application ! \n \nDebugMode: 413318723085139979 ", title, JOptionPane.QUESTION_MESSAGE);
    	}else{
    		AppId = co.showInputDialog(null, "Veuillez entrez l'ID de votre jeu/application !", title, JOptionPane.QUESTION_MESSAGE);
    	}
    	
    	

        boolean running = true;


        initDiscord(AppId);
        
        co2.showMessageDialog(null, "Votre clé a été défini sur " + AppId + "", title, JOptionPane.INFORMATION_MESSAGE);

        Window w = new Window();
        
        
        while(running){
            DiscordRPC.DiscordRunCallbacks();

            if(!ready)
                continue;

            String input = getInput();

            if(input.equalsIgnoreCase("test")){
                System.out.println("Test.");
                score++;
            }else if(input.equals("shutdown")){
                running = false;
            }else if(input.equals("yolo")){
            	
        	}else{
                System.out.println("Unknown Command.");
            }
            DiscordRichPresence rich = new DiscordRichPresence();
            rich.partySize = 1;
            rich.partyMax = 2;            
            rich.state = "Surement dans un pays lointain...";
            rich.details = "Dev in progress...";
            DiscordRPC.DiscordUpdatePresence(rich);
            ready = true;
        }

        DiscordRPC.DiscordShutdown();
        w.dispose();
    }

    // La fenêtre
    
    static class Panneau extends JPanel{
    	private int posX = -50;
    	private int posY = -50;
    	
    	public void paintComponent(Graphics g){
    		g.setColor(Color.white);
    		g.drawString("My name is PainZ :)", posX, posY);
    	}
    	
    	public int getPosX(){
    		return posX;
    	}
    	
    	public int getPosY(){
    		return posY;
    	}
    	
    	public void setPosX(int posX){
    		this.posX = posX;
    	}
    	
    	public void setPosY(int posY){
    		this.posY = posY;
    	}
    }
    
    static class Update extends JFrame implements ActionListener{

    	
    	JPanel be = new JPanel();
    	
    	JButton update = new JButton("Update !");
    	
    	JLabel u = new JLabel("Votre appilcation n'est pas à jour !");
    	JLabel u2 = new JLabel("Votre version: "+ _VERSION);
    	JLabel u3 = new JLabel("\nDernière version: "+Version);
    	
    	public Update(){
    		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		setTitle("Mise à jour requise");
    		setResizable(false);
    		setSize(400, 200);
    		setLocationRelativeTo(null);
    		
    		be.setBackground(Color.WHITE);
    		be.setLayout(new BorderLayout());
    		
    		
    		JPanel a = new JPanel();
    		
    		a.add(u);
    		a.add(u2);
    		a.add(u3);
    		a.add(update);
    		
    		be.add(a, BorderLayout.CENTER);
    		
            setContentPane(be);
            setVisible(true);
    		
    	}
    	
    	
    	
		public void actionPerformed(ActionEvent arg0) {
			
		}
    	
    }
    
    static class Window extends JFrame implements ActionListener{
    	
    	private Panneau pan = new Panneau();
    	JPanel be = new JPanel();
    	
    	// Une Liste de test
    	String[] options = {"Test 1", "Test 2", "Test 3", "..."};
     	
    	// Les DropList
    	JComboBox c = new JComboBox(options);
    	
    	// Les boutons
    	JButton b1 = new JButton("Bouton A");
    	JButton b2 = new JButton("Bouton B");
    	
    	// Le petit bienvenue au top de l'appli
    	JLabel _p = new JLabel("Bienvenue " + pseudo + " sur RPTool !");
    	
    	// Les TextFields
    	JTextField jtf = new JTextField();
    	JTextField jtf2 = new JTextField();
    	
    	
    	
    	
        public Window(){

            super("OriginalPainZ");
        	
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle(title);
            setSize(500, 350);
            setLocationRelativeTo(null);
            be.setBackground(Color.white);
            be.setLayout(new BorderLayout());
            
            JPanel a = new JPanel();
            JPanel b = new JPanel();
            
            
            Font font = new Font("Tahoma", Font.ITALIC ,15);
            jtf.setFont(font);
            jtf.setPreferredSize(new Dimension(150, 40));
            jtf.setForeground(Color.BLACK);
            jtf.setBorder(BorderFactory.createTitledBorder("Test 1"));
            jtf2.setFont(font);
            jtf2.setPreferredSize(new Dimension(150, 40));
            jtf2.setForeground(Color.BLACK);
            jtf2.setBorder(BorderFactory.createTitledBorder("Test 2"));
            
            a.add(jtf);
            a.add(jtf2);
            a.add(b1);
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
        	String textA = jtf.getText();
        	String textB = jtf2.getText();
        	DiscordRPC.DiscordRunCallbacks();
        	DiscordRichPresence r = new DiscordRichPresence();
        	r.state = textB;
        	r.details= textA;
        	DiscordRPC.DiscordUpdatePresence(r);

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
    }
    
    // FIN DE LA CONFIG DE LA FENEÊTRE
    


    private static String getInput() {
        System.out.print("> ");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        input.toLowerCase();
        return input;
    }

    private static void initDiscord(String str){
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = new Ready();
        DiscordRPC.DiscordInitialize(str, handlers, true);  // D&M -> 413318723085139979
    }

    private static class Ready implements ReadyCallback{
        public void apply(){
            System.out.println("Ready.");
            DiscordRichPresence rich = new DiscordRichPresence();
            rich.largeImageKey = "PainZ1";
            rich.spectateSecret = "";
            rich.joinSecret = "";
            rich.partySize = 1;
            rich.partyMax = 2;
            rich.state = title;
            rich.details = "Dev in progress...";
            DiscordRPC.DiscordUpdatePresence(rich);
            ready = true;
        }
    }
    
    // Première boite de dialogue de connexion !
    public class ConnexionBoxInfo{
    	private String pseudo, appid;
    	
    	public ConnexionBoxInfo(){}
    	public ConnexionBoxInfo(String pseudo, String appid){
    		this.pseudo = pseudo;
    		this.appid = appid;
    	}
    	
    	public String toString(){
    		String str;
    		if(this.pseudo != null && this.appid != null){
    			str = "Desc des valeurs rentrées";
    			str += "Pseudo: "+this.pseudo+"\n";
    			str += "AppId: "+this.appid+"\n";
    		}else{
    			str = "Acune info disponible !";
    		}
    		return str;
    	}
    
    }
    
    public class ConnexionBox extends JDialog{
    	
    	private ConnexionBoxInfo Info = new ConnexionBoxInfo();
    	private boolean sendData;
    	private JLabel pseudoLabel, appidLabel;
    	private JTextField pseudo, appid;
    	
    	public ConnexionBox(JFrame parent, String title, boolean modal){
    		super(parent, title, modal);
    		this.setSize(200, 80);
    		this.setLocationRelativeTo(null);
    		this.setResizable(false);
    		
    		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    	    this.initComponent();
    	}
    	
    	public ConnexionBoxInfo showCB(){
    		this.sendData = false;
    		this.setVisible(true);
    		return this.Info;
    	}
    	
    	private void initComponent(){
    		
    		// Le pseudo
    		JPanel panPseudo = new JPanel();
    		panPseudo.setBackground(Color.white);
    		panPseudo.setPreferredSize(new Dimension(220, 60));
    		pseudo = new JTextField();
    		pseudo.setPreferredSize(new Dimension(100, 25));
    		panPseudo.setBorder(BorderFactory.createTitledBorder("Votre pseudo"));
    		pseudoLabel = new JLabel("Saisir votre pseudo: ");
    		panPseudo.add(pseudoLabel, pseudo);
    		
    		// L'AppId
    		JPanel panApp = new JPanel();
    		panApp.setBackground(Color.WHITE);
    		panApp.setPreferredSize(new Dimension(220,60));
    		appid = new JTextField();
    		appid.setPreferredSize(new Dimension(100,25));
    		panApp.setBorder(BorderFactory.createTitledBorder("Votre AppId"));
    		appidLabel = new JLabel("Saisissez votre AppId");
    		panApp.add(appidLabel, appid);
    		
    		JPanel content = new JPanel();
    		content.setBackground(Color.white);
    		content.add(panApp, panPseudo);
    		
    		JPanel control = new JPanel();
    		JButton okButton = new JButton("Confirmer");
    		
    		okButton.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent arg0){
    				Info = new ConnexionBoxInfo(pseudo.getText(), appid.getText());
    				setVisible(false);
    			}
    			
    		});
    		
    		JButton cancelButton = new JButton("Annuler");
    		cancelButton.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent arg0){
    				setVisible(false);
    			}
    		});
    		
    		control.add(okButton,cancelButton);
    		
    		this.getContentPane().add(content, BorderLayout.CENTER);
    		this.getContentPane().add(control, BorderLayout.SOUTH);
    		
    	}
    }
    
    
    
}
