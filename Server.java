import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

class Server extends WindowAdapter implements ActionListener
{
	Frame f;
	Label l;
	TextArea ta;
	TextField tf;
	Button b,reset;
	
	ServerSocket s;
	Socket server;
	InputStream fromClient;
	OutputStream toClient;
	DataInputStream in;
	DataOutputStream out;
	InputStreamReader isr;
	BufferedReader br;
	String dataBuffer="Conversation Started";
	int port;
	String cliName,serName;
	Server()
	{	
		f=new Frame();
		f.setVisible(true);
		f.setBounds(800,100,400,600);
		f.setBackground(Color.black);
		f.setTitle("Server App By Muzafar");
		f.setResizable(false);
		f.setLayout(new FlowLayout());
		
		Font font1=new Font("New Times Roman",1,20);
		Font font2=new Font("New Times Roman",2,10);
		l=new Label("Server Chatting Application");
		l.setFont(font1);
		l.setBackground(Color.yellow);
		l.setForeground(Color.blue);
		ta=new TextArea();
		ta.setFont(font2);
		ta.setForeground(Color.red);
		tf=new TextField();
		tf.setForeground(Color.green);
		b=new Button("Send");
		b.setBackground(Color.red);
		b.setForeground(Color.green);
		
		reset=new Button("Clear History");
		reset.setBackground(Color.red);
		reset.setForeground(Color.green);
		
		f.add(l);
		f.add(ta);
		f.add(tf);
		f.add(b);
		f.add(reset);
		
		l.setBounds(50,50,100,30);
		ta.setBounds(50,100,100,70);
		tf.setBounds(70,175,100,35);
		b.setBounds(110,215,40,25);
		reset.setBounds(50,215,40,25);
		
		f.addWindowListener(this);
		b.addActionListener(this);
		reset.addActionListener(this);
				try{
				Thread.sleep(1500);
				}
				catch(Exception thr)
				{
					
				}
		port=Integer.parseInt(JOptionPane.showInputDialog(f,"Enter Port Number"));
		serName=JOptionPane.showInputDialog(f,"Enter Your Name");
		startSP();
	}
	
	void startSP()
	{
		try
		{
			s=new ServerSocket(port);   //must be outside while...........
			while(true)
			{
					Font font3=new Font("Sans Serif",1,15);
					ta.setFont(font3);
					server=s.accept();
					ta.setText("Connected to Client"+ server.getRemoteSocketAddress()+" \n Start Messaging with Client now");
				ta.setForeground(Color.green);
				
				startChatting();
			}
		}
		catch(Exception es)
		{
			ta.setText("Unable to connect with Client."+es.getMessage());
			ta.setForeground(Color.red);
		}
		finally{
			try{
				s.close();
				server.close();
			}
			catch(Exception scc)
			{
				
			}
		}
	}
	
	void startChatting() throws Exception
	{
		fromClient=server.getInputStream();
		in=new DataInputStream(fromClient);
		toClient=server.getOutputStream();
		out=new DataOutputStream(toClient);
		

		cliName=" "+in.readUTF();   //get name from Server...
		out.writeUTF(serName);    //send name to Server...
		
		
		while(true)
		{
			dataBuffer=dataBuffer.concat("\n "+cliName+" : "+in.readUTF());
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
			dataBuffer=dataBuffer.concat("\n "+serName+" : "+tf.getText());
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
			ta.setText("Send Messages....");
		}
	}
	
	public static void main(String abstul[])
	{
		new Server();
	}
}