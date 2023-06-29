import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.*;
import javax.swing.text.*;
import javax.swing.undo.*;

class MyFrame implements Runnable , ActionListener , MouseListener
{
	JFrame fr,fontframe;
	JPanel header,footer;
	JMenuBar mb;
	Dimension dim; 
	JMenu file,edit,format,help,f2,f3,sty4;
	JMenuItem m1,m2,m3,m4,ct,cp,ps,un,f1,sty1,sty2,sty3,uc,lc,fc,sz[];
	JToolBar tb;
	JButton b1,b2,b3,b4,b5,b6,b7,ok,cancel;
	JTextArea ta;
	JList ls;
	JTextField t1,t2;
	JScrollPane jsp,jsp1;
	JPopupMenu pop;
	JMenuItem p1,p2,p3,p4,p5,p6,p7;
	JLabel l1,l2,l3;
	int count=10;
	Thread th;
	String fname = "verdana";
	String fontname = "verdana" , path;
	int style = 0 , size = 12; 
	int a=0;
	UndoManager und;
	
	public MyFrame()
	{
		fr = new JFrame();
		fr.setTitle("Untitled-Notepad");
		fr.setSize(600,600);
		fr.setLocationRelativeTo(null);
		fr.setLayout(new BorderLayout());

		ta = new JTextArea();
		ta.setBorder(BorderFactory.createCompoundBorder(null,BorderFactory.createEmptyBorder(1,7,1,1)));	
		ta.setFont(new Font(fontname,style,size));
		ta.setForeground(Color.black);
		jsp = new JScrollPane(ta);
		fr.add(jsp,BorderLayout.CENTER);		

		header = new JPanel();
		header.setLayout(new BorderLayout());
		mb = new JMenuBar();

		file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		file.setFont(new Font("verdana",Font.PLAIN,14));
		file.setForeground(Color.black);

		m1 = new JMenuItem("  New   ");
		m2 = new JMenuItem("  Open  ");
		m3 = new JMenuItem("  Save  ");
		m4 = new JMenuItem("  Exit  ");
		m1.setMnemonic(KeyEvent.VK_N);
		m2.setMnemonic(KeyEvent.VK_O);
		m3.setMnemonic(KeyEvent.VK_S);
		m4.setMnemonic(KeyEvent.VK_X);
		m1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N , Event.CTRL_MASK));
		m2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O , Event.CTRL_MASK));
		m3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S , Event.CTRL_MASK));
		m4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E , Event.CTRL_MASK));
		m1.setIcon(new ImageIcon("images/new3.png"));
		m2.setIcon(new ImageIcon("images/open1.png"));
		m3.setIcon(new ImageIcon("images/save1.png"));
		m4.setIcon(new ImageIcon("images/exit1.png"));
		m1.setFont(new Font("verdana",Font.PLAIN,14));		
		m2.setFont(new Font("verdana",Font.PLAIN,14));		
		m3.setFont(new Font("verdana",Font.PLAIN,14));		
		m4.setFont(new Font("verdana",Font.PLAIN,14));	
		m1.setForeground(Color.black);
		m2.setForeground(Color.black);
		m3.setForeground(Color.black);
		m4.setForeground(Color.black);
		m1.addActionListener(this);
		m2.addActionListener(this);
		m3.addActionListener(this);
		m4.addActionListener(this);
		file.add(m1);
		file.addSeparator();
		file.add(m2);
		file.addSeparator();
		file.add(m3);
		file.addSeparator();
		file.add(m4);

		help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_F);
		help.setFont(new Font("verdana",Font.PLAIN,14));
		help.setForeground(Color.black);

		edit = new JMenu("Edit");
		edit.setMnemonic(KeyEvent.VK_E);
		edit.setForeground(Color.black);
		edit.setFont(new Font("verdana",Font.PLAIN,14));
		ct = new JMenuItem("  Cut   ");
		cp = new JMenuItem("  Copy  ");
		ps = new JMenuItem("  Paste ");
		un = new JMenuItem("  Undo  ");
		ct.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X , Event.CTRL_MASK));
		cp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C , Event.CTRL_MASK));
		ps.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V , Event.CTRL_MASK));
		un.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z , Event.CTRL_MASK));
		ct.setFont(new Font("verdana",Font.PLAIN,14));
		cp.setFont(new Font("verdana",Font.PLAIN,14));
		ps.setFont(new Font("verdana",Font.PLAIN,14));
		un.setFont(new Font("verdana",Font.PLAIN,14));
		ct.setForeground(Color.black);
		cp.setForeground(Color.black);
		ps.setForeground(Color.black);
		un.setForeground(Color.black);
		ct.setIcon(new ImageIcon("images/cut.png"));
		cp.setIcon(new ImageIcon("images/copy.png"));
		ps.setIcon(new ImageIcon("images/paste.png"));
		ct.addActionListener(this);
		cp.addActionListener(this);
		ps.addActionListener(this);
		un.addActionListener(this);
		edit.add(ct);
		edit.addSeparator();
		edit.add(cp);
		edit.addSeparator();
		edit.add(ps);
		edit.addSeparator();
		edit.add(un);

		format = new JMenu("Format");
		format.setMnemonic(KeyEvent.VK_O);
		format.setForeground(Color.black);
		format.setFont(new Font("verdana",Font.PLAIN,14));
		
		f1 = new JMenuItem("  Font  ");
		f1.setFont(new Font("verdana",Font.PLAIN,14));
		f1.setForeground(Color.black);
		f1.setIcon(new ImageIcon("images/font.png"));
		f1.addActionListener(this);
		format.add(f1);

		f2 = new JMenu("  Style ");
		f2.setFont(new Font("verdana",Font.PLAIN,14));
		f2.setForeground(Color.black);
		f2.setIcon(new ImageIcon("images/style.png"));
		format.addSeparator();
		format.add(f2);
		sty1 = new JMenuItem("Bold            ");
		sty2 = new JMenuItem("Italic          ");
		sty3 = new JMenuItem("Plain           ");
		sty4 = new JMenu("Capitilization  ");

		uc = new JMenuItem("  Uppercase          ");
		lc = new JMenuItem("  Lowercase          ");
		fc = new JMenuItem("  Only First Letter  ");
		uc.setMnemonic(KeyEvent.VK_U);
		lc.setMnemonic(KeyEvent.VK_L);
		fc.setMnemonic(KeyEvent.VK_F);
		
		uc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U , Event.CTRL_MASK));
		lc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L , Event.CTRL_MASK));
		fc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F , Event.CTRL_MASK));
		
		uc.setFont(new Font("verdana",Font.PLAIN,14));		
		lc.setFont(new Font("verdana",Font.PLAIN,14));		
		fc.setFont(new Font("verdana",Font.PLAIN,14));		
			
		uc.setForeground(Color.black);
		lc.setForeground(Color.black);
		fc.setForeground(Color.black);
		
		uc.addActionListener(this);
		lc.addActionListener(this);
		fc.addActionListener(this);
		
		sty4.add(uc);
		sty4.addSeparator();
		sty4.add(lc);
		sty4.addSeparator();
		sty4.add(fc);
		

		sty1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B , Event.CTRL_MASK));
		sty2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I , Event.CTRL_MASK));
		sty3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P , Event.CTRL_MASK));
		// sty4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U , Event.CTRL_MASK));
		sty1.setFont(new Font("verdana",Font.BOLD,14));
		sty2.setFont(new Font("verdana",Font.ITALIC,14));
		sty3.setFont(new Font("verdana",Font.PLAIN,14));
		sty4.setFont(new Font("verdana",Font.PLAIN,14));;


		sty1.addActionListener(this);
		sty2.addActionListener(this);
		sty3.addActionListener(this);
		sty4.addActionListener(this);
		sty1.setForeground(Color.black);
		sty2.setForeground(Color.black);
		sty3.setForeground(Color.black);
		sty4.setForeground(Color.black);
		f2.add(sty1);
		f2.addSeparator();
		f2.add(sty2);
		f2.addSeparator();
		f2.add(sty3);
		f2.addSeparator();
		f2.add(sty4);

		f3 = new JMenu("  Size");
		f3.setFont(new Font("verdana",Font.PLAIN,14));
		f3.setForeground(Color.black);
		f3.setIcon(new ImageIcon("images/size.png"));
		format.addSeparator();
		format.add(f3);
		
		sz = new JMenuItem[10];
		for(int i=0 ; i<10 ; i++)
		{
			sz[i] = new JMenuItem(""+count);
			sz[i].setFont(new Font("verdana",Font.PLAIN,14));		
			sz[i].setForeground(Color.black);
			sz[i].addActionListener(this);
			count = count + 2;
			f3.add(sz[i]);
		}
		// for(int j=0;j<10;j++)
		// {
		// 	gotoxy()
		// }

		mb.add(file);
		mb.add(edit);
		mb.add(format);
		mb.add(help);
		
		header.add(mb,BorderLayout.NORTH);

		tb = new JToolBar();
		b1 = new JButton(new ImageIcon("images/new4.png"));
		b2 = new JButton(new ImageIcon("images/open1.png"));
		b3 = new JButton(new ImageIcon("images/save1.png"));
		b4 = new JButton(new ImageIcon("images/cut.png"));
		b5 = new JButton(new ImageIcon("images/copy.png"));
		b6 = new JButton(new ImageIcon("images/paste.png"));
		b7 = new JButton(new ImageIcon("images/undo.png"));
		b1.setToolTipText("New");
		b2.setToolTipText("Open");
		b3.setToolTipText("Save");
		b4.setToolTipText("Cut");
		b5.setToolTipText("Copy");
		b6.setToolTipText("Paste");
		b7.setToolTipText("Undo");
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);
		tb.add(b1);
		tb.add(b2);
		tb.add(b3);
		tb.add(b4);
		tb.add(b5);
		tb.add(b6);
		tb.add(b7);
		header.add(tb,BorderLayout.SOUTH);

		footer = new JPanel();
		footer.setLayout(new BorderLayout());
		l1 = new JLabel(" Developed at Gautam Verma ");
		l1.setFont(new Font("verdana",Font.PLAIN,13));
		l1.setForeground(Color.blue);
		footer.add(l1,BorderLayout.WEST);

		l2 = new JLabel("");
		l2.setFont(new Font("verdana",Font.PLAIN,13));
		l2.setForeground(Color.blue);
		footer.add(l2,BorderLayout.EAST);

		fr.add(header,BorderLayout.NORTH);
		fr.add(footer,BorderLayout.SOUTH);

		pop = new JPopupMenu();
		p1 = new JMenuItem("  New");
		p2 = new JMenuItem("  Open");
		p3 = new JMenuItem("  Save");
		p4 = new JMenuItem("  Cut");
		p5 = new JMenuItem("  Copy");
		p6 = new JMenuItem("  Paste");
		p7 = new JMenuItem("  Undo");
		p1.setFont(new Font("verdana",Font.PLAIN,14));
		p2.setFont(new Font("verdana",Font.PLAIN,14));
		p3.setFont(new Font("verdana",Font.PLAIN,14));
		p4.setFont(new Font("verdana",Font.PLAIN,14));
		p5.setFont(new Font("verdana",Font.PLAIN,14));
		p6.setFont(new Font("verdana",Font.PLAIN,14));
		p7.setFont(new Font("verdana",Font.PLAIN,14));		
		p1.setForeground(Color.black);
		p2.setForeground(Color.black);
		p3.setForeground(Color.black);
		p4.setForeground(Color.black);
		p5.setForeground(Color.black);
		p6.setForeground(Color.black);
		p7.setForeground(Color.black);
		p1.setIcon(new ImageIcon("images/new4.png"));
		p2.setIcon(new ImageIcon("images/open1.png"));
		p3.setIcon(new ImageIcon("images/save1.png"));
		p4.setIcon(new ImageIcon("images/cut.png"));
		p5.setIcon(new ImageIcon("images/copy.png"));
		p6.setIcon(new ImageIcon("images/paste.png"));
		p7.setIcon(new ImageIcon("images/undo.png"));
		p1.addActionListener(this);
		p2.addActionListener(this);
		p3.addActionListener(this);
		p4.addActionListener(this);
		p5.addActionListener(this);
		p6.addActionListener(this);
		p7.addActionListener(this);
		pop.add(p1);
		pop.add(p2);
		pop.add(p3);
		pop.add(p4);
		pop.add(p5);
		pop.add(p6);
		pop.add(p7);
		
		ta.addMouseListener(new MouseAdapter()
		{
			public void showmenu(MouseEvent me)
			{
				if(me.isPopupTrigger()==true)
				{
					pop.show(fr,me.getX(),me.getY());
				}
			}
			public void mousePressed(MouseEvent me)
			{
				showmenu(me);
			}
			public void mouseReleased(MouseEvent me)
			{
				showmenu(me);
			}
		});


		fr.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // add a window listener to the JFrame
        fr.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit the application?",
                        "Confirm Exit", JOptionPane.YES_NO_CANCEL_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    fr.dispose(); // close the JFrame
                    System.exit(0); // terminate the application
                }
            }
        });

		 fr.setVisible(true);
	
		//  fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
			
		
			th = new Thread(this);
		th.start();
		
		
		
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==m1 || ae.getSource()==b1 || ae.getSource()==p1)
		{
			new MyFrame();
			fr.setTitle("Untitled - Notepad");
		}
		else if(ae.getSource()==m2 || ae.getSource()==b2 || ae.getSource()==p2)
		{
			try
			{
				FileDialog fd = new FileDialog(fr,"File Open",FileDialog.LOAD);
				fd.setVisible(true);
				path = fd.getDirectory()+fd.getFile();
				if(path.equals("nullnull"))
				{
					JOptionPane.showMessageDialog(fr,"Cancel by user");
				}
				else
				{
					File fe = new File(path);
					FileReader frd = new FileReader(fe);
					BufferedReader br = new BufferedReader(frd);
					String str = "";
					StringBuffer sb = new StringBuffer("");
					while((str=br.readLine())!=null)
					{
						sb.append(str+"\n");
					} 
					ta.setText(sb.toString());
					fr.setTitle(fd.getFile());
					br.close();
					frd.close();
				}
			}
			catch(Exception e)
			{}
		}
		else if(ae.getSource()==m3 || ae.getSource()==b3 || ae.getSource()==p3)
		{
			try
			{
				FileDialog fd = new FileDialog(fr,"Save File",FileDialog.SAVE);
				fd.setVisible(true);
				path = fd.getDirectory() + fd.getFile();
				if(path.equals("nullnull"))
				{
					JOptionPane.showMessageDialog(fr,"Cancel by user");
				}
				else
				{
					File fe = new File(path);
					FileWriter fw = new FileWriter(fe);
					CharArrayWriter caw = new CharArrayWriter();
					String data = ta.getText();
					char ch[] = data.toCharArray();
					caw.write(ch);
					caw.writeTo(fw);
					fr.setTitle(fd.getFile());
					caw.close();
					fw.close();
				}				
			}
			catch(Exception e)
			{}
		}
		else if(ae.getSource()==m4)
		{
			int z = JOptionPane.showConfirmDialog(fr,"Are you sure to Quit?","Choose Option",JOptionPane.YES_NO_OPTION);
			if(z==0)
			{
				System.exit(0);		//will shutdown JVM
				//fr.dispose();		//will dispose the JFrame 
			}
			else
			{
				JOptionPane.showMessageDialog(fr,"Cancel by user");
			}				
		}
		else if(ae.getSource()==ct || ae.getSource()==b4 || ae.getSource()==p4)
		{
			ta.cut();
		}
		else if(ae.getSource()==cp || ae.getSource()==b5 || ae.getSource()==p5)
		{
			ta.copy();
		}
		else if(ae.getSource()==ps || ae.getSource()==b6 || ae.getSource()==p6)
		{
			ta.paste();
		}
		else if(ae.getSource()==un || ae.getSource()==b7 || ae.getSource()==p7)
		{
			und = new UndoManager();
			ta.getDocument().addUndoableEditListener(und);
			und.undo();
		}

		else if(ae.getSource()==sty1 || ae.getSource()==sty2 || ae.getSource()==sty3 )
		{

			String st = ae.getActionCommand().trim();	//will return button label
			if(st.equals("Plain"))
			{
				style = 0;
				ta.setFont(new Font(fontname,style,size));
			}
			
			else if(st.equals("Bold"))
			{
				if(style!=1)
				{
					if(style == 2)
					{
						style = 2 + 1;
						ta.setFont(new Font(fontname,style,size));
					}
					else
					{
						if(style == 3)
						{
							style = 2 ;
							ta.setFont(new Font(fontname,style,size));
						}
						else
						{
							style = 1;
							ta.setFont(new Font(fontname,style,size));
						}
					}
				}
				
				else
				{
					style = 0;
					ta.setFont(new Font(fontname,style,size));
				}
			}

			else if(st.equals("Italic"))
			{
				if(style!=2)
				{
					if(style == 1)
					{	
						style = 2 + 1;
						ta.setFont(new Font(fontname,style ,size));
					}
					else
					{
						if(style == 3)
						{
							style = 1 ;
							ta.setFont(new Font(fontname,style,size));
						}
						else
						{
							style = 2;
							ta.setFont(new Font(fontname,style,size));
						}
					}
				}
				else
				{
					if(style == 1)
					{
						ta.setFont(new Font(fontname,style =  1,size));
					}
					ta.setFont(new Font(fontname,style =  0,size));
				}
			}
		}
		
		
		else if(ae.getSource()==uc || ae.getSource()==lc || ae.getSource()==fc )
			{
				String st = ae.getActionCommand().trim();
				if(st.equals("Uppercase"))
				{
				
				String text = ta.getText();
				if (!text.isEmpty()) {
					text = text.toUpperCase();
					ta.setText(text);
				}
				}
		
			else if(st.equals("Lowercase"))
			{
				String text = ta.getText();
				if (!text.isEmpty()) {
					text = text.toLowerCase();
					ta.setText(text);
				}
		}
		else
		{
					String text = ta.getText();
						if (!text.isEmpty()) {
    						String[] lines = text.split("\n");
   							 StringBuilder capitalizedText = new StringBuilder();
    							for (String line : lines) {
        							String[] words = line.split(" ");
        							StringBuilder capitalizedLine = new StringBuilder();
        						for (String word : words) {
           					 if (!word.isEmpty()) {
                word = word.substring(0, 1).toUpperCase() + word.substring(1);
           							 }
           					 capitalizedLine.append(word).append(" ");
       						 }
        						capitalizedText.append(capitalizedLine.toString().trim()).append("\n");
   								 }
    				ta.setText(capitalizedText.toString().trim());
				}
		}
	}

	else if(ae.getSource()==f1)
		{
			fr.setEnabled(false);
			showfontdialog();
		}	
		else if(ae.getSource()==ok)
		{
			fr.setEnabled(true);
			fontname = fname;
			ta.setFont(new Font(fontname,style,size));
			fontframe.dispose();
		}
		else if(ae.getSource()==cancel)
		{
			fr.setEnabled(true);
			ta.setFont(new Font(fontname,style,size));
			fontframe.dispose();
		}
	
		else
		{
			size = Integer.parseInt(ae.getActionCommand());
			ta.setFont(new Font(fontname,style,size));
		}
	
	}






	public void showfontdialog()
	{
		try
		{
			dim = Toolkit.getDefaultToolkit().getScreenSize();
			fontframe = new JFrame("Choose Font");
			fontframe.setSize(400,450);
			fontframe.setLocation((dim.width-400)/2 , (dim.height-450)/2);
			fontframe.setLayout(null);

			l3 = new JLabel("Font:");
			l3.setFont(new Font("verdana",Font.PLAIN,18));	
			l3.setForeground(Color.black);	
			l3.setBounds(30,20,100,30);
			fontframe.add(l3);	

			t1 = new JTextField(fontname);
			t1.setFont(new Font("verdana",Font.PLAIN,18));	
			t1.setForeground(Color.black);	
			t1.setBounds(30,50,330,30);
			fontframe.add(t1);	

			String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
			ls = new JList(fonts);
			ls.setFont(new Font("verdana",Font.PLAIN,16));	
			ls.setForeground(Color.black);
			ls.addMouseListener(this);
			ls.setSelectionBackground(Color.cyan);	
			jsp1 = new JScrollPane(ls); 
			jsp1.setBounds(30,85,330,164);
			fontframe.add(jsp1);
			ls.setSelectedValue(fontname,true);

			t2 = new JTextField("AaBbYyZz");
			t2.setFont(new Font(fname,Font.PLAIN,25));
			t2.setHorizontalAlignment(JTextField.CENTER);
			Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
       	 		t2.setBorder(BorderFactory.createTitledBorder(blackLine, "Preview"));	
			t2.setForeground(Color.black);	
			t2.setBounds(30,259,330,60);
			fontframe.add(t2);

			ok = new JButton("OK");
			cancel = new JButton("Cancel");
			ok.setBounds(90,350,100,30);	
			cancel.setBounds(200,350,100,30);
			ok.setFont(new Font("verdana",Font.PLAIN,16));	
			cancel.setFont(new Font("verdana",Font.PLAIN,16));	
			ok.setForeground(Color.black);	
			cancel.setForeground(Color.black);
			ok.addActionListener(this);
			cancel.addActionListener(this);
			fontframe.add(ok);
			fontframe.add(cancel);

			fontframe.setVisible(true);
			fontframe.addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent we)
				{
					fr.setEnabled(true);
					fontframe.dispose();
				}
			});	
		}
		catch(Exception e)
		{}
	}

	public void mouseClicked(MouseEvent me)
	{
		fname = ls.getSelectedValue().toString();
		t1.setText(ls.getSelectedValue().toString());
		t2.setFont(new Font(fname,Font.PLAIN,25));
	}
	public void mouseEntered(MouseEvent me)
	{}
	public void mouseExited(MouseEvent me)
	{}
	public void mousePressed(MouseEvent me)
	{}
	public void mouseReleased(MouseEvent me)
	{}



	public void run()
	{
		Date dt = null; 
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy hh:mm:ss a");
		while(true)
		{
			dt = new Date();
			l2.setText(sdf.format(dt).toString()+" ");
			try
			{
				Thread.sleep(1000);
			}
			catch(Exception e)
			{}	
		}
	} 

	public static void main(String args[])
	{
		new MyFrame();
	}
}
