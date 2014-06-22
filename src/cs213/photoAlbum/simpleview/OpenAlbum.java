package cs213.photoAlbum.simpleview;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import cs213.photoAlbum.control.*;
import cs213.photoAlbum.model.*;


	
/**
 * @author Michael & Imran
 *
 */
public class OpenAlbum extends JFrame {
	//I tried making the listcellrenderer, because we still need to show that photo, and JLists don't quite do that.
	JList photoList;
	album Al;
	user log;
	Control con;
	
	JPanel list;
	
	JButton addPhoto, displayPhoto, movePhoto;
	JButton deletePhoto;
	JButton recaptionPhoto, slideShow;
	JButton editTags, addTag, removeTag, exit;
	
	album tempAl;
	JScrollPane scrollPane;
	
	/**
	 * @param usr user object
	 * @param alName album object
	 * @param co control object
	 */
	public OpenAlbum(user usr, album alName, Control co){
		super("Album contents.");
		
		tempAl = alName;
		
		setSize(710,400);   // width and height
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		  
		JPanel buttons = new JPanel();
		list = new JPanel();
		list.setPreferredSize(new Dimension(500, 300));

		con = co;
		log = usr;
		Al = alName;
		photoList = new JList(Al.albumContents.toArray());
		thumbnailListRenderer TLR = new thumbnailListRenderer();
		photoList.setCellRenderer(TLR);
		
		photoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		photoList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		
		photoList.setFixedCellHeight(70);
		photoList.setFixedCellWidth(100);
		photoList.setBorder(new EmptyBorder(10,10,10,10));
		photoList.setVisibleRowCount(-1);

		
		scrollPane = new JScrollPane(photoList);
		
		scrollPane.setAlignmentX(LEFT_ALIGNMENT);
		
		scrollPane.setViewportView(photoList);
		scrollPane.setPreferredSize(new Dimension(450,300));
		
		scrollPane.setVisible(true);
		
		
		list.add(scrollPane);
		add(list, BorderLayout.CENTER);
		list.revalidate();

		buttons.setLayout(new GridBagLayout());

		addPhoto = new JButton("Add photo");
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.gridx = 0;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		addPhoto.setPreferredSize(new Dimension(75,25));
		buttons.add(addPhoto, c);
		
		deletePhoto = new JButton("Delete Photo");
		c.gridy = 0;
		c.gridx = 1;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		deletePhoto.setPreferredSize(new Dimension(75,25));
		buttons.add(deletePhoto, c);
		
		recaptionPhoto = new JButton("Recaption Photo");
		c.gridy = 0;
		c.gridx = 2;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		recaptionPhoto.setPreferredSize(new Dimension(75,25));
		buttons.add(recaptionPhoto, c);
		
		displayPhoto = new JButton("Display Photo");
		c.gridy = 0;
		c.gridx = 3;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		displayPhoto.setPreferredSize(new Dimension(75,25));
		buttons.add(displayPhoto, c);
		
		movePhoto = new JButton("Move Photo");
		c.gridy = 0;
		c.gridx = 4;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		movePhoto.setPreferredSize(new Dimension(75,25));
		buttons.add(movePhoto, c);
		
		addTag = new JButton("Add Tag");
		c.gridy = 1;
		c.gridx = 0;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		addTag.setPreferredSize(new Dimension(75,25));
		buttons.add(addTag, c);
		
		removeTag = new JButton("Remove Tag");
		c.gridy = 1;
		c.gridx = 1;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		removeTag.setPreferredSize(new Dimension(75, 25));
		buttons.add(removeTag, c);
		
		slideShow = new JButton("Slide Show");
		c.gridy = 1;
		c.gridx = 3;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		slideShow.setPreferredSize(new Dimension(75,25));
		buttons.add(slideShow, c);
		
		exit = new JButton("Exit Album");
		c.gridy = 1;
		c.gridx = 4;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		exit.setPreferredSize(new Dimension(75,25));
		buttons.add(exit, c);
		
		add(buttons, BorderLayout.SOUTH);
		
		
		
		addPhoto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddPhotoWindow(log, Al, photoList);
			}
		});
		
		deletePhoto.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(photoList.getSelectedValue() != null){
					Al.removePhoto((photo) photoList.getSelectedValue());
					photoList.setListData(Al.albumContents.toArray());
				}else{
					JFrame noSel = new JFrame();
					noSel.setSize(200,100);
					noSel.setLayout(new FlowLayout());
					noSel.add(new JTextArea("Select a photo to remove first."));
					noSel.setVisible(true);
				}
			}
			
		});
		
		recaptionPhoto.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(photoList.getSelectedValue() != null){
					new RecaptionWindow(Al, (photo)photoList.getSelectedValue(),photoList, con);
				}else{
					JFrame noSel = new JFrame();
					noSel.setSize(200,100);
					noSel.setLayout(new FlowLayout());
					noSel.add(new JTextArea("Select a photo to recaption first."));
					noSel.setVisible(true);
				}
			}
		});
		
		addTag.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(photoList.getSelectedIndex()==-1){
				JFrame noSel = new JFrame();
				noSel.setSize(300,150);
				noSel.setLayout(new FlowLayout());
				noSel.add(new JTextArea("Select a photo first."));
				noSel.setVisible(true);
			}else{
				photo tempPho = (photo)photoList.getSelectedValue();
				new AddTagWindow(log, Al, tempPho);
			}
			}
		});
	
		removeTag.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(photoList.getSelectedIndex()==-1){
				JFrame noSel = new JFrame();
				noSel.setSize(300,150);
				noSel.setLayout(new FlowLayout());
				noSel.add(new JTextArea("Select a photo first."));
				noSel.setVisible(true);
			}else{
				photo tempPho = (photo)photoList.getSelectedValue();
				new RemoveTagWindow(log, Al, tempPho, photoList);
			}
			}
		});
		movePhoto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(photoList.getSelectedIndex()==-1){
					JFrame noSel = new JFrame();
					noSel.setSize(300,150);
					noSel.setLayout(new FlowLayout());
					noSel.add(new JTextArea("Select a photo first."));
					noSel.setVisible(true);
				}else{
					photo tempPho = (photo)photoList.getSelectedValue();
					new MovePhotoWindow(log, Al, tempPho, photoList);
					photoList.setListData(Al.albumContents.toArray());
				}
			}
		});
		
		displayPhoto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(photoList.getSelectedIndex()!=-1)
					new PhotoViewer(Al, (photo) photoList.getSelectedValue());
				else{
					JFrame noSel = new JFrame();
					noSel.setSize(300,150);
					noSel.setLayout(new FlowLayout());
					noSel.add(new JTextArea("Select a photo first."));
					noSel.setVisible(true);
				}
				
			}
			
		});
		slideShow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(photoList.getSelectedIndex()!=-1)
					new SlideShowViewer(Al, (photo) photoList.getSelectedValue());
				else{
					JFrame noSel = new JFrame();
					noSel.setSize(300,150);
					noSel.setLayout(new FlowLayout());
					noSel.add(new JTextArea("Select a photo first."));
					noSel.setVisible(true);
				}
				
			}
			
		});
	
		exit.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			con.serUser();
			dispose();		
			}
	});
	
}
	
	
	
	/**
	 * @author Michael & Imran
	 *
	 */
	class thumbnailListRenderer extends JLabel implements ListCellRenderer {
		public thumbnailListRenderer(){
			setOpaque(true);
			setHorizontalAlignment(LEFT);
			setVerticalAlignment(CENTER);
		}
		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			
			if (isSelected){
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			}else{
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			
			ImageIcon icon = Al.albumContents.get(index).getIcon();
			Dimension oSize = new Dimension();
			oSize.width = icon.getIconWidth();
			oSize.height = icon.getIconHeight();
			Image scaled = icon.getImage();
			scaled = scaled.getScaledInstance(50, 50, Image.SCALE_FAST);
			icon.setImage(scaled);
			
			String caption = Al.albumContents.get(index).getCaption();
			setIcon(icon);
			setText(caption);
			setFont(list.getFont());
			return this;
		}
	}
}

/**
 * @author Michael & Imran
 *
 */
class RecaptionWindow extends JFrame implements ActionListener {
	
	private JTextArea recaptioninput;
	JList pList;
	JTextArea messagebox;
	album tempAl;
	photo tempPho;
	Control con;
	
	/**
	 * @param alName album object
	 * @param phoName photo object
	 * @param list list of photos
	 * @param co control object
	 */
	public RecaptionWindow(album alName, photo phoName, JList list, Control co) {
		
		tempAl = alName;
		tempPho = phoName;
		con = co;
		pList = list;
		
		setTitle("Recaption Window");
		setSize(300,170);  
		setVisible(true);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JPanel text = new JPanel(), addbuttons = new JPanel();
		
		JButton recaptionbutton = new JButton("Recaption");
		JButton cancel = new JButton("Cancel");
		
		messagebox=new JTextArea(2,22);
		messagebox.setText("Input new photo caption");
		recaptioninput = new JTextArea(2,15);
		
		JLabel addlabel = new JLabel("Recaption " + tempPho.name + " to: ");
		
		text.add(addlabel);
		text.add(recaptioninput);
		
		addbuttons.add(recaptionbutton);
		addbuttons.add(cancel);
		
		add(text, BorderLayout.NORTH);
		add(addbuttons, BorderLayout.CENTER);
		add(messagebox, BorderLayout.SOUTH);
		
		recaptionbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String input = recaptioninput.getText(); 
			    if(input.isEmpty())
			    	messagebox.setText("Please input text to recaption to.");
			    else{
			    	tempAl.recaptcha(input, tempPho);
			    	con.serUser();
			    	pList.setListData(tempAl.albumContents.toArray());
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
class AddPhotoWindow extends JFrame implements ActionListener {
	
	private JTextArea addphotoinput, captioninput;
	JList aList;
	JTextArea messagebox;
	user temp;
	album tempA;
	String tempAl, tempPho;
	File phoFile;
	
	/**
	 * @param usr user object
	 * @param al album object
	 * @param list photo list
	 */
	public AddPhotoWindow(user usr, album al, JList list) {
		
		tempA = al;
		temp = usr;
		//tempAl = alName;
		aList = list;
		setLayout(new FlowLayout(FlowLayout.LEFT));

		setTitle("Add Photo Window");
		setSize(600,150);  
		setVisible(true);
		setResizable(true);
		setLocationRelativeTo(null);
		validate();
		
		JPanel text = new JPanel(), addbuttons = new JPanel();
		
		JButton addbutton = new JButton("Add");
		JButton cancel = new JButton("Cancel");
		JButton browse = new JButton("Browse photos");
		
		messagebox=new JTextArea(2,22);
		messagebox.setText("Input new photo caption");
		addphotoinput = new JTextArea(2,15);
		captioninput = new JTextArea(2,15);

		JLabel addlabel = new JLabel("Add photo to " + tempA.name + " : ");
		JLabel captionlabel = new JLabel("        Caption: ");

		text.add(addlabel);
		text.add(addphotoinput);
		text.add(captionlabel);
		text.add(captioninput);
		
		addbuttons.add(addbutton);
		addbuttons.add(browse);
		addbuttons.add(cancel);
		
		
		add(text, BorderLayout.NORTH);
		add(addbuttons, BorderLayout.CENTER);
		add(messagebox, BorderLayout.SOUTH);
		
		addbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String input = addphotoinput.getText(); 
			    if(input.isEmpty())
			    	messagebox.setText("Please input the name of the photo");
			    else{
			    	album target = tempA;
			    	if(target == null){
			    		messagebox.setText("Error: Album does not exist for user:" + temp.uniqueID);
			    	}else{
			    		Date lastModified = new Date(phoFile.lastModified());
			    		String caption = captioninput.getText();
			    		if(!caption.equals("")){
			    			photo tempPhoto = new photo(input, caption, tempAl, lastModified, phoFile);
			    			target.addPhoto(tempPhoto, caption);
			    			aList.setListData(target.albumContents.toArray());
			    			messagebox.setText("Added " + tempPhoto.name + "to album.");

			    		}else{
			    			messagebox.setText("Error: please input caption.");
					}
				}}
			}
		});	
		cancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		browse.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "JPG, PNG, & GIF Images", "jpg", "gif", "png");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(getParent());
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			      addphotoinput.setText(chooser.getSelectedFile().getName());
			      phoFile = chooser.getSelectedFile();
			    }
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
class AddTagWindow extends JFrame implements ActionListener {
	
	private JTextArea typeinput, valueinput;
	JList aList;
	JTextArea messagebox;
	user temp;
	album tempAl;
	photo tempPho;
	
	/**
	 * @param usr user object
	 * @param alName album object
	 * @param phoName photo object
	 */
	public AddTagWindow(user usr, album alName, photo phoName) {
		
		
		temp = usr;
		tempAl = alName;
		tempPho = phoName;
		
		setTitle("Add Tag Window");
		setSize(500,160);  
		setVisible(true);
		setResizable(true);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JPanel text = new JPanel(), addbuttons = new JPanel();
		
		JButton addbutton = new JButton("Add");
		JButton cancel = new JButton("Cancel");
		
		messagebox=new JTextArea(2,22);
		messagebox.setText("Input new photo caption");
		messagebox.setEditable(false);
		typeinput = new JTextArea(2,15);
		valueinput = new JTextArea(2,15);

		JLabel typelabel = new JLabel("Tag type: ");
		JLabel valuelabel = new JLabel("Tag value: ");

		
		text.add(typelabel);
		text.add(typeinput);
		
		text.add(valuelabel);
		text.add(valueinput);
		
		addbuttons.add(addbutton);
		addbuttons.add(cancel);
		
		
		add(text, BorderLayout.NORTH);
		add(addbuttons, BorderLayout.CENTER);
		add(messagebox, BorderLayout.SOUTH);
		
		addbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String type = typeinput.getText();
			    String value = valueinput.getText();
			    if(type.isEmpty()||value.isEmpty())
			    	messagebox.setText("Please input a type and a value.");
			    else{
			    
			    if(tempAl == null){
					messagebox.setText("Error: Album does not exist for user:" + temp.uniqueID);
					
				}else{
				
				if(tempPho == null){
					messagebox.setText("Error: Photo does not exist for user:" + temp.uniqueID);
					
				}else
					tempPho.addTag(type, value);
			    	dispose();
			    }
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
class MovePhotoWindow extends JFrame implements ActionListener {
	
	private JTextArea addphotoinput, captioninput;
	JList aList, pList;
	
	user temp;
	album tempA;
	photo tempPho;
	

	/**
	 * @param usr user object
	 * @param al album object
	 * @param photo photo object
	 * @param phoList photo list object
	 */
	public MovePhotoWindow(user usr, album al, photo photo, JList phoList) {

		
		tempA = al;
		temp = usr;
		tempPho = photo;
		
		pList = phoList;
		
		
		ArrayList<album> tempAls;
		tempAls = new ArrayList();
		
		tempAls = (ArrayList<album>) temp.albumNames.clone();
		tempAls.remove(tempA);
		
		aList = new JList();
		aList.setListData(tempAls.toArray());
	
		aList.setPreferredSize(new Dimension(100, 200));
		

		
		setLayout(new FlowLayout(FlowLayout.LEFT));

		
		setTitle("Move Photo Window");
		setSize(180,360);  
		setVisible(true);
		setResizable(true);
		setLocationRelativeTo(null);
		validate();
		
		JPanel text = new JPanel(), addbuttons = new JPanel();
		JPanel text2 = new JPanel();
		
		JButton cancel = new JButton("Cancel");
		JButton move = new JButton("Move");
		
		
		addphotoinput = new JTextArea(2,15);
		captioninput = new JTextArea(2,15);

		JLabel movelabel = new JLabel("Move photo from " + tempA.name + " to :");
		

		text.add(movelabel);
		text2.add(aList);
	
		addbuttons.add(move);
		addbuttons.add(cancel);
		
		
		add(text, BorderLayout.WEST);
		add(text2, BorderLayout.EAST);
		add(addbuttons, BorderLayout.SOUTH);
		
		move.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(aList.getSelectedIndex()==-1){
					JFrame noSel = new JFrame();
					noSel.setSize(300,150);
					noSel.setLayout(new FlowLayout());
					noSel.add(new JTextArea("Select a photo to move to first."));
					noSel.setVisible(true);
				}else{
					temp.movePhotos(tempPho.name, tempA.name, aList.getSelectedValue().toString());
					pList.setListData(tempA.albumContents.toArray());
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
class RemoveTagWindow extends JFrame implements ActionListener {
	
	private JTextArea addphotoinput, captioninput;
	JList tagList;
	
	user temp;
	album tempA;
	photo tempPho;
	

	/**
	 * @param usr user object	
	 * @param al album object
	 * @param photo photo object
	 * @param photoList photolist object
	 */
	public RemoveTagWindow(user usr, album al, photo photo, JList photoList) {

		
		tempA = al;
		temp = usr;
		tempPho = photo;
		
		
		
		ArrayList tempTags;
		tempTags = new ArrayList();
		
		
		
		
		int i = 0;
		Set keys = tempPho.tagMap.keySet();
		Iterator<String> it = keys.iterator();
		if(!tempPho.tagMap.isEmpty()){
			while( it.hasNext()){
				String type =  it.next();
				String value =  tempPho.tagMap.get(type);
				StringTokenizer tok = new StringTokenizer(value, ";");
				while(tok.hasMoreTokens()){
				tempTags.add(type +":"+ tok.nextToken());
				}
			}
			
		}
		
	
		
		
		tagList = new JList();
		tagList.setListData(tempTags.toArray());
	
		tagList.setPreferredSize(new Dimension(100, 200));
		

		
		setLayout(new FlowLayout(FlowLayout.LEFT));

		
		setTitle("Remove Tag Window");
		setSize(210,360);  
		setVisible(true);
		setResizable(true);
		setLocationRelativeTo(null);
		validate();
		
		JPanel text = new JPanel(), addbuttons = new JPanel();
		JPanel text2 = new JPanel();
		
		JButton cancel = new JButton("Cancel");
		JButton move = new JButton("Remove");
		
		
		addphotoinput = new JTextArea(2,15);
		captioninput = new JTextArea(2,15);

		JLabel movelabel = new JLabel("Remove chosen tag from " + tempPho.name + ":");
		

		text.add(movelabel);
		text2.add(tagList);
	
		addbuttons.add(move);
		addbuttons.add(cancel);
		
		
		add(text, BorderLayout.WEST);
		add(text2, BorderLayout.EAST);
		add(addbuttons, BorderLayout.SOUTH);
		
		move.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tagList.getSelectedIndex()==-1){
					JFrame noSel = new JFrame();
					noSel.setSize(300,150);
					noSel.setLayout(new FlowLayout());
					noSel.add(new JTextArea("Select a tag to remove to first."));
					noSel.setVisible(true);
				}else{
					String type, value;
					String[] temp = tagList.getSelectedValue().toString().split(":");
					type = temp[0];
					value = temp[1];
					tempPho.removeTag(type, value);
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



