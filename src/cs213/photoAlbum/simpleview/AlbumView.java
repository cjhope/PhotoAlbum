package cs213.photoAlbum.simpleview;

import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.model.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

/**
 * @author Michael & Imran
 *
 */
public class AlbumView extends JFrame{

	JPanel buttons;
	JPanel buttons2;
	JPanel list;
	
	JButton createAlbum;
	JButton deleteAlbum;
	JButton renameAlbum;
	JButton open;
	JButton searchByTags;
	JButton searchByDates;
	JButton quit;
	
	user log;
	Control con;
	
	AlbumView me;
	
	
	DefaultListModel albums;
	JList albumList;
	JScrollPane scrollPane;
	
	/**
	 * @param title title of albumview window
	 * @param usr user object
	 * @param cin control object
	 */
	/**
	 * @param title
	 * @param usr
	 * @param cin
	 */
	/**
	 * @param title
	 * @param usr
	 * @param cin
	 */
	public  AlbumView(String title, user usr, Control cin)  {
		super(title);
		
		setSize(710,400);   // width and height
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);  
		
		log = usr;
		con = cin;
		con.setUser(log);
		me = this;
		
		buttons = new JPanel();
		buttons2 = new JPanel();
		list = new JPanel();
		list.setSize(500, 500);
		
		createAlbum = new JButton("Create Album");
		deleteAlbum = new JButton("Delete Album");
		renameAlbum = new JButton("Rename Album");
		open = new JButton("Open");
		searchByTags = new JButton("Search By Tags");
		searchByDates = new JButton("Search By Dates");
		quit = new JButton("Quit");
		
		
		
	//	flow.setAlignment(FlowLayout.TRAILING);
		
		albums = new DefaultListModel();
		
		/*int i = 0;
	
		while(i <log.albumNames.size()){
			albums.addElement(log.albumNames.get(i));
		}*/
		
		albumList = new JList();
		scrollPane = new JScrollPane(albumList);
		

		albumList.setListData(log.albumNames.toArray());

		scrollPane = new JScrollPane(albumList);
		
		albumList.setFixedCellHeight(50);
		albumList.setFixedCellWidth(490);
		
		scrollPane.setAlignmentX(LEFT_ALIGNMENT);
		albumList.setVisibleRowCount(6);
		
		scrollPane.setViewportView(albumList);
		scrollPane.setVisible(true);
		
		list.add(scrollPane);
		//list.add(albumList);

		list.revalidate();

		buttons.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.gridx = 0;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		createAlbum.setPreferredSize(new Dimension(75,25));
		buttons.add(createAlbum, c);

		c.gridy = 0;
		c.gridx = 1;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		deleteAlbum.setPreferredSize(new Dimension(75,25));
		buttons.add(deleteAlbum, c);

		c.gridy = 0;
		c.gridx = 2;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		renameAlbum.setPreferredSize(new Dimension(75,25));
		buttons.add(renameAlbum, c);
		
		c.gridy = 0;
		c.gridx = 3;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		open.setPreferredSize(new Dimension(40,25));
		buttons.add(open, c);
		
		c.gridy = 0;
		c.gridx = 4;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		searchByTags.setPreferredSize(new Dimension(80,25));
		buttons.add(searchByTags, c);

		

		c.gridy = 1;
		c.gridx = 4;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		searchByDates.setPreferredSize(new Dimension(80,25));
		buttons.add(searchByDates, c);

		c.gridy = 0;
		c.gridx = 5;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		buttons.add(quit, c);
		
		
		add(list, BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);
		
		 createAlbum.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new AddWindow(log, albumList);
					albumList.setListData(log.albumNames.toArray());
				}  
			});
		 
		 quit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				con.serUser();
				dispose();
			} 
		 });
		 
		 renameAlbum.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(albumList.getSelectedIndex()==-1){
					JFrame noSel = new JFrame();
					noSel.setSize(710,400);
					noSel.setLayout(new FlowLayout());
					noSel.add(new JTextArea("Select an album to rename first."));
					noSel.setVisible(true);
				}else{
					new RenameWindow(log, albumList, albumList.getSelectedValue().toString());
				}
			}
			 
		 });
		 
		 deleteAlbum.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(albumList.getSelectedValue() != null){
					log.deleteAlbum(albumList.getSelectedValue().toString());
					albumList.setListData(log.albumNames.toArray());}
				else{
					JFrame noSel = new JFrame();
					noSel.setSize(100,200);
					noSel.setLayout(new FlowLayout());
					noSel.add(new JTextArea("Select an album to remove first."));
					noSel.setVisible(true);
				}
				
			}
			 
		 });
		 
		 searchByTags.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
				
					new searchByTagWindow(log, con, me);
					
				
				}
			 });
		 
		 searchByDates.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					new searchByDateWindow(log, con, me);
					
				}
				 
			 });
		 open.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(albumList.getSelectedIndex()==-1){
						JFrame noSel = new JFrame();
						noSel.setSize(100,200);
						noSel.setLayout(new FlowLayout());
						noSel.add(new JTextArea("Select an album to open first."));
						noSel.setVisible(true);
					}else{
						String albumName = albumList.getSelectedValue().toString();
						album tempAlbum = log.getAlbum(albumName);
						new OpenAlbum(log, tempAlbum, con);
					}
				}
				 
			 });
	}
	
	/**
	 * Updates the albumList
	 */
	public void updateList(){
		albumList.setListData(log.albumNames.toArray());
		return;
	}

}

/**
 * @author Michael & Imran
 *
 */
class AddWindow extends JFrame implements ActionListener {
	
	private JTextArea addalbuminput;
	user temp;
	JTextArea messagebox;
	JList aList;
	
	public AddWindow(user usr, JList albList) {
		
		temp = usr;
		aList = albList;

		setTitle("Add Album");
		setSize(300,200);  
		setVisible(true);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JPanel text = new JPanel(), addbuttons = new JPanel();
		
		JButton addbutton = new JButton("Add Album");
		JButton cancel = new JButton("Cancel");
		messagebox=new JTextArea(2,22);
		messagebox.setEditable(false);
		messagebox.setText("Input album name");
		addalbuminput = new JTextArea(2,15);
		
		JLabel addlabel = new JLabel("Add Album: ");
		
		text.add(addlabel);
		text.add(addalbuminput);
		
		addbuttons.add(addbutton);
		addbuttons.add(cancel);
		
		add(text, BorderLayout.NORTH);
		add(addbuttons, BorderLayout.CENTER);
		add(messagebox, BorderLayout.SOUTH);
		
		
		addbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tempinput = "";
			    String input = addalbuminput.getText(); 
			    tempinput =  temp.addAlbum(input);
			    messagebox.setText(tempinput);
			    aList.setListData(temp.albumNames.toArray());
			    addalbuminput.setText("");
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// new AddWindow();
	}
}

/**
 * @author Michael & Imran
 *
 */
class RenameWindow extends JFrame implements ActionListener {
	
	private JTextArea renameinput;
	JList aList;
	JTextArea messagebox;
	user temp;
	
	public RenameWindow(user usr, JList albumList, String alName) {
		
		aList = albumList;
		temp = usr;
		
		setTitle("Rename Window");
		setSize(300,150);  
		setVisible(true);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JPanel text = new JPanel(), addbuttons = new JPanel();
		
		JButton recaptionbutton = new JButton("Rename");
		JButton cancel = new JButton("Cancel");
		
		messagebox=new JTextArea(2,22);
		messagebox.setText("Input album name to rename to.");
		renameinput = new JTextArea(2,15);
		
		JLabel addlabel = new JLabel("Rename " + alName + " to: ");
		
		text.add(addlabel);
		text.add(renameinput);
		
		addbuttons.add(recaptionbutton);
		addbuttons.add(cancel);
		
		add(text, BorderLayout.NORTH);
		add(addbuttons, BorderLayout.CENTER);
		add(messagebox, BorderLayout.SOUTH);
		
		recaptionbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String input = renameinput.getText(); 
			    if(input.isEmpty())
			    	messagebox.setText("Please input a name to rename the album to.");
			    else{
			    album target = (album)aList.getSelectedValue();
			    target.name=input;
			    aList.setListData(temp.albumNames.toArray());
			    dispose();
			    }
			}
		});	
		cancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}

/**
 * @author Michael & Imran
 *
 */
class searchByTagWindow extends JFrame implements ActionListener {
	
	private JTextArea searchinput, messagebox;
	user usr1;
	Control tempcon;
	AlbumView av;
	
	
	public searchByTagWindow(user usr, Control con, AlbumView me) {
		usr1 = usr;
		tempcon = con;
		av = me;
		
		setTitle("Search By Tags");
		setSize(300,175);  
		setVisible(true);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JPanel text = new JPanel(), addbuttons = new JPanel();
		
		JButton search = new JButton("Search");
		JButton cancel = new JButton("Cancel");
		
		searchinput = new JTextArea(2,15);
		messagebox = new JTextArea(2,15);
		messagebox.setEditable(false);
		messagebox.setText("Input type:value or just value");
		JLabel addlabel = new JLabel("Input tags: ");
		
		text.add(addlabel);
		text.add(searchinput);
		
		addbuttons.add(search);
		addbuttons.add(cancel);
		
		add(text, BorderLayout.NORTH);
		add(addbuttons, BorderLayout.CENTER);
		add(messagebox, BorderLayout.SOUTH);

		
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<photo> tagPhotos;
			    String input = searchinput.getText(); 
			   tagPhotos = tempcon.getPhotosByTag(usr1, input);
			   if(tagPhotos == null){
				   messagebox.setText("No photos found");
			   }else{
				   new TagSearchView(usr1, tagPhotos, tempcon, input, av);
			   }
			}
		});		
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			dispose();
			    
			}
		});	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
	}
}
/**
 * @author Michael & Imran
 *
 */
class searchByDateWindow extends JFrame implements ActionListener {
	
	private JTextArea searchinput, searchinput2, messagebox;
	user usr1;
	Control tempcon;
	AlbumView av;
	
	
	public searchByDateWindow(user usr, Control con, AlbumView me) {
		usr1 = usr;
		tempcon = con;
		av = me;
		
		setTitle("Search By Dates");
		setSize(480,175);  
		setVisible(true);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JPanel text = new JPanel(), addbuttons = new JPanel();
		
		JButton search = new JButton("Search");
		JButton cancel = new JButton("Cancel");
		
		searchinput = new JTextArea(2,15);
		searchinput2 = new JTextArea(2,15);
		messagebox = new JTextArea(2,15);
		messagebox.setText("Input in MM/DD/YYYY form");
		messagebox.setEditable(false);

		JLabel addlabel = new JLabel("Start date: ");
		JLabel add2label = new JLabel("End date: ");

		text.add(addlabel);
		text.add(searchinput);
		text.add(add2label);

		text.add(searchinput2);
		
		addbuttons.add(search);
		addbuttons.add(cancel);
		
		add(text, BorderLayout.NORTH);
		add(addbuttons, BorderLayout.CENTER);
		add(messagebox, BorderLayout.SOUTH);
		

		
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!searchinput.getText().isEmpty() && !searchinput2.getText().isEmpty()){
					ArrayList<photo> tagPhotos;
					String input = searchinput.getText()+" "+searchinput2.getText(); 
					tagPhotos = tempcon.getPhotosByDate(usr1, input);
					if(tagPhotos == null){
						messagebox.setText("No photos found");
					}
					else{
						new DateSearchView(usr1, tagPhotos, tempcon, input, av);
						dispose();
					}
			   }
			    
			}
		});		
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			dispose();
			    
			}
		});	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
	}
}