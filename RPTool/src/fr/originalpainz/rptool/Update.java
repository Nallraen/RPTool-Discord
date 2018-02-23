package fr.originalpainz.rptool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Update extends JFrame implements ActionListener{
	
	Main main;

	JPanel be = new JPanel();
	
	JButton update = new JButton("Update !");
	
	JTextArea u = new JTextArea("Votre appilcation n'est pas à jour !"
							   +"\nVotre version: "+ main._VERSION
							   +"\nDernière version: "+ main.Version);
	
	
	public Update() throws IOException, URISyntaxException{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Mise à jour requise");
		setResizable(false);
		setSize(400, 200);
		setLocationRelativeTo(null);
		
		be.setBackground(Color.WHITE);
		be.setLayout(new BorderLayout());
		
		
		JPanel a = new JPanel();

        update.addActionListener(this);
		a.add(u);
		a.add(update);
		
		be.add(a, BorderLayout.CENTER);
		
        setContentPane(be);
        setVisible(true);

        
	//	Desktop d = Desktop.getDesktop();
	//	d.browse(new URI("https://github.com/Nallraen/RPTool-Discord/releases"));
	}
	
	
	
	public void actionPerformed(ActionEvent arg0) {

		Desktop d = Desktop.getDesktop();
		try {
			d.browse(new URI("http://rptool.originalpainz.ovh/"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

}
