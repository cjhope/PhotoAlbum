package cs213.photoAlbum.simpleview;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.model.album;
import cs213.photoAlbum.model.photo;
import cs213.photoAlbum.model.user;
import cs213.photoAlbum.simpleview.OpenAlbum.thumbnailListRenderer;

/**
 * @author Imran
 *
 */
public class DateSearchView extends JFrame {
		//I tried making the listcellrenderer, because we still need to show that photo, and JLists don't quite do that.
		JList photoList;
		user log;
		Control con;
		
		JPanel list;
		
		JButton newAlbum, displayPhoto, movePhoto;
		JButton deletePhoto;
		JButton recaptionPhoto, slideShow;
		JButton editTags, addTag, removeTag, exit;
		
		JScrollPane scrollPane;
		
		ArrayList<photo> results;
		
		AlbumView toUpdate;
		
		String searchRange;
		
		/**
		 * @param usr user object
		 * @param res arraylist of resources
		 * @param co control object
		 * @param range range of dates
		 * @param av albumview object
		 */
		public DateSearchView(user usr, ArrayList<photo> res, Control co, String range, AlbumView av){
			super("Search results");
										
			setSize(710,400);   // width and height
			setResizable(true);
			setLocationRelativeTo(null);
			setVisible(true);
			  
			JPanel buttons = new JPanel();
			list = new JPanel();
			list.setPreferredSize(new Dimension(500, 300));

			con = co;
			log = usr;
			results = res;
			photoList = new JList(results.toArray());
			thumbnailListRenderer TLR = new thumbnailListRenderer();
			photoList.setCellRenderer(TLR);
			searchRange = range;
			toUpdate = av;
			
			photoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			photoList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			
			photoList.setFixedCellHeight(70);
			photoList.setFixedCellWidth(100);
			photoList.setBorder(new EmptyBorder(10,10,10,10));
			
			scrollPane = new JScrollPane(photoList);
			
			scrollPane.setAlignmentX(LEFT_ALIGNMENT);
			
			scrollPane.setViewportView(photoList);
			scrollPane.setPreferredSize(new Dimension(450,300));
			
			scrollPane.setVisible(true);
			
			
			list.add(scrollPane);
			add(list, BorderLayout.CENTER);
			list.revalidate();

			buttons.setLayout(new GridBagLayout());

			newAlbum = new JButton("Create new album");
			GridBagConstraints c = new GridBagConstraints();
			c.gridy = 0;
			c.gridx = 0;
			c.weightx = 1.0;
			c.fill = GridBagConstraints.BOTH;
			newAlbum.setPreferredSize(new Dimension(75,25));
			buttons.add(newAlbum, c);
			
			deletePhoto = new JButton("Delete Photo");
			c.gridy = 0;
			c.gridx = 1;
			c.weightx = 1.0;
			c.fill = GridBagConstraints.BOTH;
			deletePhoto.setPreferredSize(new Dimension(75,25));
			//buttons.add(deletePhoto, c);
			
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
			
			exit = new JButton("Exit");
			c.gridy = 1;
			c.gridx = 4;
			c.weightx = 1.0;
			c.fill = GridBagConstraints.BOTH;
			exit.setPreferredSize(new Dimension(75,25));
			buttons.add(exit, c);
			
			add(buttons, BorderLayout.SOUTH);
			
			newAlbum.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent arg0) {
					String newName = "Search results: "+searchRange;
					log.addAlbum(newName);
					album newAl = log.getAlbum(newName);
					for(int i=0; i<results.size(); i++){
						photo temp = results.get(i).clone();
						temp.addAlbum(newName);
						newAl.addPhoto(temp, temp.getCaption());
					}
					toUpdate.updateList();
					dispose();
				}				
			});
			
			recaptionPhoto.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(photoList.getSelectedValue() != null){
						new dateRecaptionWindow(results, (photo)photoList.getSelectedValue(),photoList, con, log);
					}else{
						JFrame noSel = new JFrame();
						noSel.setSize(200,100);
						noSel.setLayout(new FlowLayout());
						noSel.add(new JTextArea("Select a photo to remove first."));
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
						int i = 0;
						album tempA;
						do{
							tempA = log.getAlbum(tempPho.albumName.get(i));
							i++;
						}while(tempA==null);
						new dateAddTagWindow(log, tempA, tempPho);
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
					int i = 0;
					album tempA;
					do{
						tempA = log.getAlbum(tempPho.albumName.get(i));
						i++;
					}while(tempA==null);
					new dateRemoveTagWindow(log, tempA, tempPho);
					//new RemoveTagWindow(log, Al, tempPho, photoList);
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
						noSel.add(new JTextArea("Select a photo to move."));
						noSel.setVisible(true);
					}else{
					photo tempPho = (photo)photoList.getSelectedValue();
					int i = 0;
					album tempAl;
					do{
						tempAl = log.getAlbum(tempPho.albumName.get(i));
						i++;
					}while(tempAl==null);
					new dateMovePhotoWindow(log, tempAl, tempPho, photoList);
					}
				}
			});
			
			displayPhoto.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(photoList.getSelectedIndex()==-1){
						JFrame noSel = new JFrame();
						noSel.setSize(300,150);
						noSel.setLayout(new FlowLayout());
						noSel.add(new JTextArea("Select a photo to move."));
						noSel.setVisible(true);
					}else{
						photo tempPho = (photo)photoList.getSelectedValue();
						int i = 0;
						album tempAl;
						do{
							tempAl = log.getAlbum(tempPho.albumName.get(i));
							i++;
						}while(tempAl==null);
						new PhotoViewer(tempAl, (photo) photoList.getSelectedValue());
					}
				}
				
			});
			
			slideShow.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(photoList.getSelectedIndex()!=-1)
						new searchSlideShowViewer(results , (photo) photoList.getSelectedValue(), searchRange);
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
				
				ImageIcon icon = results.get(index).getIcon();
				Dimension oSize = new Dimension();
				oSize.width = icon.getIconWidth();
				oSize.height = icon.getIconHeight();
				Image scaled = icon.getImage();
				scaled = scaled.getScaledInstance(50, 50, Image.SCALE_FAST);
				icon.setImage(scaled);
				
				String caption = results.get(index).getCaption();
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
class dateRecaptionWindow extends JFrame implements ActionListener {
	
	private JTextArea recaptioninput;
	JList pList;
	JTextArea messagebox;
	album tempAl;
	photo tempPho;
	Control con;
	ArrayList<photo> pArr;
	
	/**
	 * @param arr arraylist of photos
	 * @param phoName name of photo
	 * @param list list of photos
	 * @param co control object
	 * @param usr user object
	 */
	public dateRecaptionWindow(ArrayList<photo> arr, photo phoName, JList list, Control co, user usr) {
		
		tempPho = phoName;
		int i = 0;
		do{
			tempAl = usr.getAlbum(tempPho.albumName.get(i));
			i++;
		}while(tempAl==null);
		con = co;
		pList = list;
		pArr = arr;
		
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
			    	messagebox.setText("Please input a name to recaption the photo to.");
			    else{
			    	tempAl.recaptcha(input, tempPho);
			    	con.serUser();
			    	pList.setListData(pArr.toArray());
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
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

/**
 * @author Michael & Imran
 *
 */
class dateAddTagWindow extends JFrame implements ActionListener {
	
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
	public dateAddTagWindow(user usr, album alName, photo phoName) {
		
		
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
class dateRemoveTagWindow extends JFrame implements ActionListener {
	
	private JTextArea addphotoinput, captioninput;
	JList tagList;
	
	user temp;
	album tempA;
	photo tempPho;
	

	/**
	 * @param usr user object
	 * @param al album object
	 * @param photo photo object
	 */
	public dateRemoveTagWindow(user usr, album al, photo photo) {

		
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
					noSel.add(new JTextArea("Select an album to move to first."));
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

/**
 * @author Michael & Imran
 *
 */
class dateMovePhotoWindow extends JFrame implements ActionListener {
	
	private JTextArea addphotoinput, captioninput;
	JList aList, pList;
	
	user temp;
	album tempA;
	photo tempPho;
	

	/**
	 * @param usr user object
	 * @param al album object
	 * @param photo photo object
	 * @param phoList jlist of photos
	 */
	public dateMovePhotoWindow(user usr, album al, photo photo, JList phoList) {

		
		tempA = al;
		temp = usr;
		tempPho = photo;
		
		
		
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
					noSel.add(new JTextArea("Select an album to move to first."));
					noSel.setVisible(true);
				}else{
					temp.movePhotos(tempPho.name, tempA.name, aList.getSelectedValue().toString());
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