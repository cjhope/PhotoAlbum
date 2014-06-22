package cs213.photoAlbum.simpleview;

import javax.swing.*;
import javax.swing.event.ChangeListener;

import cs213.photoAlbum.model.album;
import cs213.photoAlbum.model.photo;

import javax.swing.event.ChangeEvent;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * @author Imran & Michael
 *
 */
public class searchSlideShowViewer extends JFrame {
	
	JSlider slider = new JSlider(0,100,100);
	JLabel percent = new JLabel("100%");
	
	
	
	JLabel tags;
	JLabel name;
	JLabel caption;
	ImageIcon icon;
	ImageView image;
	Image newImage;
	
	JButton next, previous;

	ArrayList<photo> tempAl;
	JList taglist;
	photo tempP;
	int i;
	String searchParam;
	
	/**
	 * @author Michael
	 *
	 */
	private class ImageView extends JScrollPane {
		JPanel panel = new JPanel();
		
		Image originalImage;
		JLabel iconLabel;
		Dimension originalSize = new Dimension();

		/**
		 * @param icon - ImageIcon of the photo to be displayed
		 */
		public ImageView(ImageIcon icon) {
			this.originalImage =icon.getImage();
			
			panel.setLayout(new BorderLayout());
			iconLabel = new JLabel(icon);
			panel.add(iconLabel);
			
			setViewportView(panel);
			
			originalSize.width = icon.getIconWidth();
			originalSize.height = icon.getIconHeight();
		}	
		
		/**
		 * @return - scaled version of photo to be displayed
		 */
		public Image  update() {
			int min = slider.getMinimum();
			int max = slider.getMaximum();
			int span = max-min;
			int value = slider.getValue();
			double multiplier = (double)value/span;
			multiplier = multiplier == 0.0 ? 0.01 : multiplier;
			Image scaled = originalImage.getScaledInstance(
					(int)(originalSize.width*multiplier),
					(int)(originalSize.height*multiplier),
					Image.SCALE_FAST);
			return scaled;
		}	
	}
		
		
	/**
	 * @param al - ArrayList of photos to be displayed in slide show
	 * @param p - Current photo to be displayed
	 * @param search - String of text describing search parameters
	 */
	public searchSlideShowViewer(ArrayList<photo> al, photo p, String search){
		super(search + " - " + p.name);
		
		taglist = new JList();
		
			tempAl = al;
			tempP = p;
			i = tempAl.indexOf(tempP);
			searchParam = search;
		
		JPanel labels = new JPanel();
		JPanel image2 = new JPanel();
		
		labels.setLayout(new GridBagLayout());
		
		JPanel sliderOption = new JPanel();
		sliderOption.add(new JLabel("Adjust size of picture: "));
		sliderOption.add(slider);
		sliderOption.add(percent);
		
		setSize(600,480);  
		setVisible(true);
		setResizable(true);
		setLocationRelativeTo(null);
		validate();
		
		ArrayList<String> tempTags = new ArrayList<String>();
		
		int j = 0;
		Set keys = p.tagMap.keySet();
		Iterator<String> it = keys.iterator();
		if(!p.tagMap.isEmpty()){
			while( it.hasNext()){
				String type =  it.next();
				String value =  p.tagMap.get(type);
				StringTokenizer tok = new StringTokenizer(value, ";");
				while(tok.hasMoreTokens()){
				tempTags.add(type +":"+ tok.nextToken());
				}
			}
			
		}
		
		 icon = p.getIcon();
		
		 image = new ImageView(icon);
		 
		 previous = new JButton("Previous photo");
		 if(i==0){
			 previous.setEnabled(false);
		 }
		 next = new JButton("Next photo");
		 if(i==tempAl.size()-1){
			 next.setEnabled(false);
		 }
		 
		 name = new JLabel("Photo name: " + p.name + "\n");
		 caption = new JLabel("Caption: "+p.getCaption() + "\n");
		 tags = new JLabel("Tags:\n"+p.listTags() + "\n");
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridy = 0;
		c.gridx = 0;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;

		labels.add(name, c);
		c.gridy = 1;
		c.gridx = 0;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;

		labels.add(caption, c);
		if(!tempTags.isEmpty()){
			taglist.setListData(tempTags.toArray());
			taglist.setPreferredSize(new Dimension(100, 200));
			c.gridy = 2;
			c.gridx = 0;
			c.weightx = 1.0;
			c.fill = GridBagConstraints.BOTH;
			
			labels.add(taglist, c);
			}else{
				c.gridy = 2;
				c.gridx = 0;
				c.weightx = 1.0;
				c.fill = GridBagConstraints.BOTH;
				
				labels.add(tags, c);
			}
		
		
		c.gridy = 3;
		c.gridx = 0;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 40;

		previous.setPreferredSize(new Dimension(40,25));

		labels.add(previous, c);
		c.gridy = 3;
		c.gridx = 1;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 40;
		//next.setPreferredSize(new Dimension(40,25));
		labels.add(next, c);
		
		
		
		JLabel viewer = new JLabel(icon);
		
		JScrollPane scrollpane = new JScrollPane(viewer);
		
		
		add(sliderOption, BorderLayout.NORTH);
		add(scrollpane, BorderLayout.CENTER);
		add(labels,BorderLayout.WEST);
		
		
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!slider.getValueIsAdjusting()) {
					percent.setText(slider.getValue() + "%");
					//image = new ImageView(icon);					
					newImage = image.update();
					icon.setImage(newImage);
					JLabel viewer = new JLabel(icon);
					add(viewer, BorderLayout.CENTER);
}
			}
		});
		

		next.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			new searchSlideShowViewer(tempAl, tempAl.get(i+1), searchParam);
			dispose();		
			}
	});

		previous.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new searchSlideShowViewer(tempAl, tempAl.get(i-1), searchParam);
			dispose();		
			}
	});
		
	
		validate();
		
		
		
	}
	
	

}
