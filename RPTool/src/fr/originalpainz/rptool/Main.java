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
	public static String _VERSION = "alpha_2.6";
	public static String Version;
	
	public static String ConfigInfo = "  English :\n"
									+ "  This is not a real config file! \n"
									+ "  It just contain a save of your previous RP (Before to close the app) \n \n"
			 						+ "  Français: \n"
			 						+ "  Ceci n\'est pas réellement un fichier de config!\n"
			 						+ "  Il contient seulement ce que vous avez mis dans votre dernier RP (avant de fermer l\'appli)";
	
	
	 /* D&M -> 413318723085139979
	 * RPTool -> 414595521592950784
	 */
	
	// Les comptes
	static Accounts acc;
	
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
    		prop.setProperty("", "");
    		prop.setProperty("", "");
    		
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
			URL url = new URL("http://dedsec-dev.890m.com/version.txt");
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
    	
    	if(){
        	mdp = p.showInputDialog(null, "Veuillez entrez votre mot de passe: ", title, JOptionPane.QUESTION_MESSAGE);
    	}else{
    		JOptionPane error = null;
    		error.showConfirmDialog(null, "Une erreur est survenue...\n Vous ne faites pas partie de la liste des personnes autorisés à utilisé l'appli... \n Esssayez de contacter son développeur !",title,JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
    		return;
    	}

    	
    	// La connectivité au jeu
    	JOptionPane co = new JOptionPane(), co2 = new JOptionPane();
    	if(){
    		
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

        MainWindow w1 = new MainWindow();
        
        
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
        		rich.partyId ="NothingRightHere";
                rich.matchSecret = "4b2fdce12f639de8bfa7e3591b71a0d679d7c93f";
        		rich.spectateSecret ="e7eb30d2ee025ed05c71ea495f770b76454ee4e0";
        		rich.joinSecret ="MTI4NzM0OjFpMmhuZToxMjMxMjM=";
        		rich.instance = 1;
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
    
    //..................................................//
    //................ BOITE CUSTOM ...................//
    //................................................//
    
	
	public class ZDialog extends JDialog{
		public ZDialog(JFrame parent, String title, boolean modal){
			super(parent, title, modal);
			setSize(200,80);
			setLocationRelativeTo(null);
			setResizable(false);
			setVisible(true);
		}
	}
	
	public class Test extends JFrame {
		private JButton b1 = new JButton("Test alpha");
		
		public Test(){
			setTitle(title);
			setSize(300,100);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
			getContentPane().setLayout(new FlowLayout());
			getContentPane().add(b1);
			b1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0){
					ZDialog zd = new ZDialog(null, "Test", true);
				}
			});
			setVisible(true);
		}
		
	}
    
    
}
