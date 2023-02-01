import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

class Client extends WindowAdapter implements ActionListener
{
	Frame f;
	Label l;
	TextArea ta;
	TextField tf;
	Button b,reset;
	
	Socket client;
	InputStream fromServer;
	OutputStream toServer;
	DataInputStream in;
	DataOutputStream out;
	InputStreamReader isr;
	BufferedReader br;
	String dataBuffer="Conversation Started";
	String cliName,serName;
	int port;
	String ip;
	
	Client()
	{
		f=new Frame();
		f.setVisible(true);
		f.setBounds(100,100,400,600);
		f.setBackground(Color.black);
		f.setTitle("Client App By Muzafar");
		f.setResizable(false);
		f.setLayout(new FlowLayout());
		
		Font font1=new Font("New Times Roman",1,20);
		Font font2=new Font("New Times Roman",2,10);
		l=new Label("Client Chatting Application");
		l.setFont(font1);
		l.setBackground(Color.yellow);
		l.setForeground(Color.blue);
		ta=new TextArea();
		ta.setFont(font2);
		ta.setForeground(Color.red);
		tf=new TextField();
		tf.setForeground(Color.green);
		b=new Button("Send");
		reset=new Button("Clear History");
		b.setBackground(Color.red);
		b.setForeground(Color.green);
		reset.setBackground(Color.red);
		reset.setForeground(Color.green);
		
		f.add(l);
		f.add(ta);
		f.add(tf);
		f.add(b);
		f.add(reset);
		
		f.addWindowListener(this);
		b.addActionListener(this);
		reset.addActionListener(this);
		
		l.setBounds(50,50,100,30);
		ta.setBounds(50,100,100,70);
		tf.setBounds(70,175,100,35);
		b.setBounds(110,215,40,25);
		reset.setBounds(50,215,40,25);
				try{
				Thread.sleep(1500);
				}
				catch(Exception thr)
				{
					
				}
		port=Integer.parseInt(JOptionPane.showInputDialog(f,"Enter Port Number"));
		cliName=JOptionPane.showInputDialog(f,"Enter Your Name");
		ip=JOptionPane.showInputDialog(f,"Enter ip Address");
		startSP();
	}
	
	void startSP()
	{
		try{
			Font font3=new Font("Sans Serif",1,15);
			ta.setText("Connecting to Server...");
			ta.setForeground(Color.red);				ta.setFont(font3);
			client=new Socket(ip,port);                //("localhost",6547);   //10.0.6.31
			ta.setText("Connected to Server"+ client.getRemoteSocketAddress()+" \n Start Messaging with Server now");
			ta.setForeground(Color.green);
			startChatting();
		}
		catch(Exception ec)
		{
			ta.setText("Unable to connect with Server.");
			ta.setForeground(Color.red);
		}
		finally{
			try{
				client.close();
			}
			catch(Exception scc)
			{
				
			}
		}
	}
	
	void startChatting() throws Exception
	{
		fromServer=client.getInputStream();
		in=new DataInputStream(fromServer);
		toServer=client.getOutputStream();
		out=new DataOutputStream(toServer);
		
		out.writeUTF(cliName);    //send name to Server...
		serName=" "+in.readUTF();   //get name from Server...
		
		while(true)
		{
			dataBuffer=dataBuffer+"\n "+serName+" : "+in.readUTF();
			ta.setText(dataBuffer);
		}
		
	}
	
	public void windowClosing(WindowEvent we)
	{
		System.exit(0);
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==b)
		{
			try{
			out.writeUTF(tf.getText());
			dataBuffer=dataBuffer+"\n "+cliName+" : "+tf.getText();
			ta.setText(dataBuffer);
			tf.setText("");
			}
			catch(Exception eee)
			{
				
			}
		}
		if(ae.getSource()==reset)
		{
			dataBuffer="Conversation is Running....";
			ta.setText("Send Messages...");
		}
	}
	
	public static void main(String abstul[])
	{
		new Client();
	}
}