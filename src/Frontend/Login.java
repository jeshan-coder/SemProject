package Frontend;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.*;

import DataModels.LoginModel;

public class Login extends JFrame{

	public Login()
	{
	// Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
	Container	visiblecontainer=getContentPane();
	visiblecontainer.setLayout(new BorderLayout());
	setSize(500,500);
	LoginPanel loginpanel=new LoginPanel(this,visiblecontainer);
	visiblecontainer.add(loginpanel,BorderLayout.CENTER);
	setVisible(true);
	}
}



class LoginPanel extends JPanel
{
	JTextField username;
	JTextField password;
	JButton login;
	JLabel pagename;
	Container visiblecontainer;
	JFrame frame;

	public LoginPanel(JFrame frame,Container visibleContainer)
	{
		this.frame=frame;
		this.visiblecontainer=visibleContainer;	
		pagename=new JLabel("Login Page");
		username=new JTextField(20);
		password=new JTextField(20);
		login=new JButton("Login");
		add(pagename,BorderLayout.NORTH);
		add(username,BorderLayout.NORTH);
		add(password,BorderLayout.CENTER);
		add(login,BorderLayout.SOUTH);
		login.addActionListener(new LoginListener(username,password,frame,visiblecontainer));


	}
	public JTextField getusername ()
	{
		return this.username;
	}

	public JTextField getPassword()
	{
		return this.password;
	}



}



class LoginListener implements ActionListener
{	JTextField username;
	JTextField password;
	JFrame jframe;
	Container container;
	
	public LoginListener(JTextField username,JTextField password,JFrame jframe,Container container)
	{
		this.username=username;
		this.password=password;
		this.jframe=jframe;
		this.container=container;
	}

	@Override
	public void actionPerformed(ActionEvent g) {
		LoginModel loginmodel=new LoginModel(username.getText(),password.getText());
		Backend.DataBase db=new Backend.DataBase(loginmodel);
		Connection c=db.setConnection();
		if(c!=null)
		{
			ArrayList<String> tables=db.getTables(c);
			Databaseinfo databaseinfo=new Databaseinfo(container,jframe,c,tables,db);
		}
		else{
			return;
		}
	}
}


