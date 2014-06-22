package cs213.photoAlbum.simpleview;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import cs213.photoAlbum.control.*;
import cs213.photoAlbum.model.user;

/**
 * @author Imran
 *
 */
public class logIn extends JFrame{
	
	Control con = new Control();
	JTextField logField;
	JButton logButton;
	JTextArea logText;
	
	public static void main(String[] args0){
		logIn login = new logIn();
		login.pack();
		login.setVisible(true);
		login.setLocationRelativeTo(null);
		login.setResizable(true);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	/**
	 * logs the user in
	 */
	public logIn(){
		super("Photo Album - group 51");
		con.readUsers();
		setLayout(new GridLayout(3,3,5,5));
		makeElements();
		add(logField);
		add(logButton);
		add(logText);
		
	}
	
	/**
	 * makes elements in the jframe 
	 */
	public void makeElements(){
		logField = new JTextField();
		logField.setEditable(true);
		logText = new JTextArea(5,16);
		logText.setText("Enter username in the text field above.");
		logButton = new JButton("Log in");
		logButton.addActionListener(new ActionListener(){
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String uid = logField.getText();
				if(logField.getText().equals("admin")){
					/**************INSERT ADMIN PANEL HERE*****************/
					logText.setText("Logged in as admin.");
					makeAdminFrame();
				}
				else if(con.findUser(uid)!=null){
					/**************INSERT USER LOG IN HERE*****************/
					logText.setText("Logged in as "+uid);
					user log = con.findUser(uid);
					JFrame av = new AlbumView(uid+"'s Albums.", log, con);
					av.setSize(710,400);   // width and height
					av.setResizable(true);
					av.setLocationRelativeTo(null);
					av.setVisible(true);
					av.setDefaultCloseOperation(EXIT_ON_CLOSE);  
					av.validate();
					validate();
				}
				else{
					logText.setText("Could not log in as "+uid+", try again.");
					logField.setText("");
				}
			}
		});
		
		
	}
	
	
	/**
	 * creates the admin frame on admin login
	 */
	public void makeAdminFrame(){
		adminFrame aF = new adminFrame(con);
		aF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aF.pack();
		aF.setVisible(true);
		aF.setLocationRelativeTo(null);
		aF.setResizable(true);
		
		
	}
}

/**
 * @author Imran
 *
 */
class adminFrame extends JFrame{
	Control con;
	JTextField nameSpace;
	JButton add;
	JButton remove;
	JButton list;
	JTextArea messagebox;
	JButton logout;
	JButton confirm;
	JButton cancel;
	
	/**
	 *  creates elements of admin frame
	 */
	public void makeElements(){
		nameSpace = new JTextField();
		nameSpace.setEditable(true);
		add = new JButton("Add user");
		remove = new JButton("Remove user");
		list = new JButton("List users");
		logout = new JButton("Log out");
		messagebox = new JTextArea(5,5);
		confirm = new JButton("Confirm");
		cancel = new JButton("Cancel");
		
		confirm.setEnabled(false);
		cancel.setEnabled(false);
		
		messagebox.setText("To add a user, use the following format:\"Username - full name\"\n(no quotes) in the above text box and click the \"add\" button.\nTo remove a user, simply input the user name in the box above\nand click the \"remove\" button.\nTo list users, click the \"list\" button.");
		
		add.addActionListener(new ActionListener(){
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String mani[] = nameSpace.getText().split("-");
				if(mani.length != 2)
					return;
				
				String uid;
				String fName;
				
				uid = mani[0].trim();
				
				if(uid.contains(" ")){
					messagebox.setText("Username cannot contain spaces.\nPlease use the following format:\"Username - full name\"\n(no quotes)");
					return;
				}
				
				fName = mani[1].trim();
				
				String stat = con.addUser(uid, fName);
				nameSpace.setText("");
				messagebox.setText(stat);
			}
		});
		remove.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String uid = nameSpace.getText();
				if(uid.isEmpty()){
					messagebox.setText("Please specify a user to remove.");
					return;
				}
				String stat = con.deleteUser(uid);
				messagebox.setText(stat);
				nameSpace.setText("");
			}
		});
		list.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				messagebox.setText(con.listUsers());
			}
		});
		logout.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
			
		});
	}
	
	/**
	 * adds panels
	 */
	public void makePanel(){
		setLayout(new GridLayout(0,1));
		add(nameSpace);
		add(add);
		add(remove);
		add(list);
		add(messagebox);
		add(logout);
	}
	
	/**
	 * @param c control object
	 */
	public adminFrame(Control c){
		super("Photo album admin window");
		con = c;
		makeElements();
		makePanel();
	}
}