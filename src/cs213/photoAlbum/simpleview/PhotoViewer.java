package cs213.photoAlbum.simpleview;

import javax.swing.*;
import javax.swing.event.ChangeListener;

import cs213.photoAlbum.model.album;
import cs213.photoAlbum.model.photo;

import javax.swing.event.ChangeEvent;




import java.awt.*;
import java.util.*;

/**
 * @author Michael
 *
 */
public class PhotoViewer extends JFrame {
	
	JSlider slider = new JSlider(0,100,100);
	JLabel percent = new JLabel("100%");
	
	JLabel tags;
	JLabel name;
	JLabel caption;
	ImageIcon icon;
	ImageView image;
	Image newImage;
	
	JList taglist;
	

	
	/**
	 * @author Michael
	 *
	 */
	private class ImageView extends JScrollPane {
		JPanel panel = new JPanel();
		
		Image originalImage;
		JLabel iconLabel;
		Dimension originalSize = new Dimension();

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
		 * @return updated image object
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
	 * @param al- album 
	 * @param p-photo
	 */
	public PhotoViewer(album al, photo p){
		super(al.name + " - " + p.name);
		
		
			taglist = new JList();
		
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
		
		 icon = p.getIcon();
		
		 image = new ImageView(icon);
		 
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
		
		
		
		
		
		JLabel viewer = new JLabel(icon);
		
		JScrollPane scrollpane = new JScrollPane(viewer);
		
		//viewer.add(scrollpane);

		
		
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
	
		validate();
		
		
		
	}
	
	

}
