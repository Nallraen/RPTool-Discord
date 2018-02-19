package fr.originalpainz.rptool;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import fr.originalpainz.rptool.utils.Accounts;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class Main {
	
	// Quelques Values
	static int score = 0;
	public static String title = "RPTool by OriginalPainZ";
	public static String pseudo;
	public static String mdp;
	public static String _Dev = "OriginalPainZ";
	public static String AppId;
	public static String _VERSION = "alpha_1.6";
	private static String Version;
	
	public static String ConfigInfo = "  English :\n"
									+ "  This is not a real config file! \n"
									+ "  It just contain a save of your previous RP (Before to close the app) \n \n"
			 						+ "  Français: \n"
			 						+ "  Ceci n\'est pas réellement un fichier de config!\n"
			 						+ "  Il contient seulement ce que vous avez mis dans votre dernier RP (avant de fermer l\'appli)";
	
	
	/* 
	 * Quelques ClientID Utiles :
	 *
	 * D&M -> 413318723085139979
	 * RPTool -> 414595521592950784
	 */
	
	// Les comptes
	Accounts acc;
	
	// Test
	static int a;
	
	
    public static boolean ready = false;

    public static void main(String[] args) throws URISyntaxException{
    	
    	
    	// La config.properties
    	
    	Properties prop = new Properties();
    	OutputStream output = null;
    	InputStream config = null;

    	try {
			config = new FileInputStream("config.properties");
		} catch (FileNotFoundException e1) {    	
		try{
    		output = new FileOutputStream("config.properties");
    		
    		// Initialize values
    		prop.setProperty("ligneHaute", "Nothing on top line");
    		prop.setProperty("ligneBasse", "Nothing on bot line");
    		
    		// Save values
    		prop.store(output, ConfigInfo);
    		
    	} catch (IOException io){
    		io.printStackTrace();
    	} finally{
    		if (output !=null){
    			try{
    				output.close();
    			} catch (IOException io){
    				io.printStackTrace();
    			}
    		}
    	}
		}

    	
		
    	// Message préventif --> Betâ build !
    	JOptionPane warnBeta = new JOptionPane();
    	warnBeta.showMessageDialog(null, "Cette application est encore en phase de développement !", title+" | Application en développement !", JOptionPane.WARNING_MESSAGE);
    	
    	// Test de la version
    	try {
			URL url = new URL("/*Un truc top secret */");
			InputStream is = url.openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = rd.read()) != -1)
			   sb.append((char) cp);
			Version = sb.toString();
			
			
			System.out.println(Version);
			if(!Version.equalsIgnoreCase(_VERSION)){
				Update u = new Update();
				return;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	// Le pseudo
    	JOptionPane p = new JOptionPane();
    	pseudo = p.showInputDialog(null, "Veuillez entrez votre pseudo: ", title, JOptionPane.QUESTION_MESSAGE);
    	
    	if(pseudo.equalsIgnoreCase(/*Encore plus de trucs secret :P*/){
        	mdp = p.showInputDialog(null, "Veuillez entrez votre mot de passe: ", title, JOptionPane.QUESTION_MESSAGE);
    	}else{
    		JOptionPane error = null;
    		error.showConfirmDialog(null, "Une erreur est survenue...\n Vous ne faites pas partie de la liste des personnes autorisés à utilisé l'appli... \n Esssayez de contacter son développeur !",title,JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
    		return;
    	}

    	
    	// La connectivité au jeu
    	JOptionPane co = new JOptionPane(), co2 = new JOptionPane();
    	if(mdp.equalsIgnoreCase(/* Variables des mdp comptes */)){
    		
    		AppId = co.showInputDialog(null, "Veuillez entrez l'ID de votre jeu/application !", title, JOptionPane.QUESTION_MESSAGE);
    		if(AppId.isEmpty()){
    			JOptionPane error = null;
    			error.showConfirmDialog(null, "Une erreur est survenu...\nL'ID ne peux pas être vide !", title, JOptionPane.ERROR_MESSAGE);
    			
    		}
    	}else{
    		JOptionPane error = null;
    		error.showConfirmDialog(null, "Une erreur est survenue...\n Le mot de passe est incorrect ! \n Ou vous êtes un petit malin mais vous ne faites pas partie de la liste des personnes autorisés à utilisé l'appli... \n Esssayez de contacter son développeur !",title,JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
    		return;
    		//	AppId = co.showInputDialog(null, "Veuillez entrez l'ID de votre jeu/application !", title, JOptionPane.QUESTION_MESSAGE);
    	}
    	
    	

        boolean running = true;


        initDiscord(AppId);
        
        co2.showMessageDialog(null, "Votre clé a été défini sur " + AppId + "", title, JOptionPane.INFORMATION_MESSAGE);

        Window w1 = new Window();
        
        
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
        w1.dispose();
    }

    // La fenêtre
    
    static class Update extends JFrame implements ActionListener{

    	JPanel be = new JPanel();
    	
    	JButton update = new JButton("Update !");
    	
    	JTextArea u = new JTextArea("Votre appilcation n'est pas à jour !"
    							   +"\nVotre version: "+ _VERSION
    							   +"\nDernière version: "+Version);
    	
    	
    	public Update() throws IOException, URISyntaxException{
    		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		setTitle("Mise à jour requise");
    		setResizable(false);
    		setSize(400, 200);
    		setLocationRelativeTo(null);
    		
    		be.setBackground(Color.WHITE);
    		be.setLayout(new BorderLayout());
    		
    		
    		JPanel a = new JPanel();
    		
    		a.add(u);
    		a.add(update);
    		
    		be.add(a, BorderLayout.CENTER);
    		
            setContentPane(be);
            setVisible(true);

			Desktop d = Desktop.getDesktop();
			d.browse(new URI("https://github.com/Nallraen/RPTool-Discord/releases"));
    	}
    	
    	
    	
		public void actionPerformed(ActionEvent arg0) {
			try {
				WebUpdate();
			} catch (IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}



		private void WebUpdate() throws IOException, URISyntaxException {	
			Desktop d = Desktop.getDesktop();
			d.browse(new URI("www.google.com"));
			
		}
    	
    }
    
    static class Window extends JFrame implements ActionListener{
    	
    	JPanel be = new JPanel();
    	
    	// Une Liste de test
    	String[] options = {"Test 1", "Test 2", "Test 3", "..."};
     	
    	// Les DropList
    	JComboBox c = new JComboBox(options);
    	
    	// Les boutons
    	JButton b1 = new JButton("Entrer");
    	JButton b2 = new JButton("Bouton B");
    	
    	// Le petit bienvenue au top de l'appli
    	JLabel _p = new JLabel("Bienvenue " + pseudo + " sur RPTool !");
    	
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
    	
    	
    	
    	
        public Window(){

            super("OriginalPainZ");
        	
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle(title);
            setSize(500, 350);
            setLocationRelativeTo(null);
            setBackground(Color.WHITE);
            be.setBackground(Color.white);
            be.setLayout(new BorderLayout());
            
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
            		r.startTimestamp = (i + 1)*60;
            	//	r.largeImageKey = "painz";
            		r.spectateSecret ="MTIzNDV8MTIzNDV8MTMyNDU0";
                	DiscordRPC.DiscordUpdatePresence(r);
                	
                	// Save values in config File
                	prop.setProperty("ligneBasse", textB);
                	prop.store(output, ConfigInfo);
                	
            	}else if(textB.isEmpty()){
            		r.state = "";
            		r.details = textA;
            		r.startTimestamp = (i + 1)*60;
            	//	r.largeImageKey = "painz";
            		r.spectateSecret ="MTIzNDV8MTIzNDV8MTMyNDU0";
                	DiscordRPC.DiscordUpdatePresence(r);
                	
                	// Save values in config file
                	prop.setProperty("ligneHaute", textA);
                	prop.store(output, ConfigInfo);
            	}else{
            		r.state = textB;
            		r.details= textA;
            		r.startTimestamp = (i + 1)*60;
            //		r.largeImageKey = "painz";
            		r.spectateSecret ="MTIzNDV8MTIzNDV8MTMyNDU0";
            		
                	DiscordRPC.DiscordUpdatePresence(r);
                	
                	// Save values
                	prop.setProperty("ligneHaute", textA);
                	prop.setProperty("ligneBasse", textB);
                	
                	prop.store(output, ConfigInfo);
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
        DiscordRPC.DiscordInitialize(str, handlers, true);  
    }

    private static class Ready implements ReadyCallback{
    	
    	Properties prop = new Properties();
    	InputStream input = null;
    	

    	
        public void apply(){
        	
        	try{
        		input = new FileInputStream("config.properties");
        		
        		// Load values
        		
        		prop.load(input);
        		
        		// Get Values
        		
        		String topLine = prop.getProperty("ligneHaute");
        		String botLine = prop.getProperty("ligneBasse");
            	
            	System.out.println("Ready.");
                DiscordRichPresence rich = new DiscordRichPresence();
            //    rich.largeImageKey = "painz";
                rich.spectateSecret = "";
                rich.joinSecret = "";
                rich.partySize = 1;
                rich.partyMax = 2;
                rich.state = botLine; // Ligne basse
                rich.details = topLine; // Ligne haute
                DiscordRPC.DiscordUpdatePresence(rich);
                ready = true;
        	} catch (IOException io){
        		io.printStackTrace();
        	} finally {
        		if (input !=null){
        			try{
        				input.close();
        			} catch (IOException io){
        				io.printStackTrace();
        			}
        		}
        	}

        }
    }
    
}
