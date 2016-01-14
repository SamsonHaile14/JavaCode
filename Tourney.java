import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.Random;

public class Tourney extends JFrame
	{
	//general constants
	final int sx = 100,sy = 100,ex =350,ey=325;
	int page = 0;
	
	//pg. 1
	protected JTextField numPlayers;
		
	protected JLabel numPlayersLabel;

	protected JButton proceed;	//also pg. 2
	protected JButton incNum;
	protected JButton decNum;
	
	//pg. 2
	protected JLabel direct;
	protected JTextField[] party;
	protected JPanel panel;
	protected JScrollPane scroll;
	protected JCheckBox randomize;
	protected boolean makeRandom = false;
	
	//pg. 3
	protected JScrollPane matchup;
	protected JPanel panel2;
		
	public static void main(String argv[])
		{
		new Tourney("Tournament Bracket Generator");
		}
		
	public Tourney(String title)
		{
		super(title);

		setBounds(sx,sy,ex,ey);
		addWindowListener(new WindowDestroyer());
		
		//initialize JFrame components
		numPlayers = new JTextField("2");
		numPlayersLabel = new JLabel("Participant count");
		proceed = new JButton("Proceed");
		incNum = new JButton("+");
		decNum = new JButton("-");
		
		//action listener
		incNum.addActionListener(new ActionHandler());
		decNum.addActionListener(new ActionHandler());	
		proceed.addActionListener(new ActionHandler());
		
		
		numPlayers.setEditable(false);

		getContentPane().setLayout(null);
		
		getContentPane().add(numPlayers);
		getContentPane().add(numPlayersLabel);
		getContentPane().add(proceed);
		getContentPane().add(incNum);
		getContentPane().add(decNum);
		
		//set component placement
		final int row1Y = (ey-sy)/2,sHeight = 28,nplx=100,npx = 50,ix = 45;
		int progCol = (ex-sx)/4;
		
		numPlayersLabel.setBounds(progCol,row1Y,nplx,sHeight);
		progCol+=nplx+5;
		numPlayers.setBounds(progCol,row1Y,npx,sHeight);
		progCol+=npx+5;
		incNum.setBounds(progCol,row1Y,ix,sHeight);
		progCol+=ix;
		decNum.setBounds(progCol,row1Y,ix,sHeight);
		
		proceed.setBounds((ex-sx)/2,(ey-sy)*3/4,100, 30);

		setVisible(true);
		
		}
		
	void refresh()
		{
		getContentPane().invalidate();
		getContentPane().validate();
		getContentPane().repaint();
		}
		
	void getParticipants(int size)
		{
		
		//remove old material
		getContentPane().remove(numPlayersLabel);
		getContentPane().remove(numPlayers);
		getContentPane().remove(incNum);
		getContentPane().remove(decNum);
		getContentPane().remove(proceed);		
		
		setLayout(null);
		
		direct = new JLabel("Enter names of participants");
		party = new JTextField[size];
		panel = new JPanel();
		randomize = new JCheckBox("randomize matchups");
		
		randomize.addItemListener(new ItemHandler());
		
		GridLayout exp = new GridLayout(size,0);
		panel.setLayout(exp);
		
		for(int a = 0;a<size;a++){
			party[a] = new JTextField("");
			panel.add(party[a]);
			}
		direct.setBounds(0,0,200,20);
		
		randomize.setBounds(190,30,200,40);
			
		proceed.setBounds(55,225,100,25);
		getContentPane().add(proceed);

		scroll = new JScrollPane(panel);
		scroll.setBounds(25,20,150,160);
		
		getContentPane().add(scroll);
		getContentPane().add(direct);
		getContentPane().add(randomize);

		setLocationRelativeTo(null);
		
		refresh();

		}
		
	void makeBracket()
		{

		getContentPane().remove(scroll);
		getContentPane().remove(direct);
		getContentPane().remove(proceed);
		getContentPane().remove(randomize);
		
		setLayout(new BorderLayout());
		
		int number = Integer.parseInt(numPlayers.getText());
		String[] names = new String[number];
		
		if(!makeRandom){
			for(int a = 0;a<number;a++)
				names[a] = party[a].getText();
			}
			
		else{
			Random randomGenerator = new Random();
			boolean[] used = new boolean[number];
			
			for(int a = 0;a<number;a++)
				used[a] = false;
			
			for(int a = 0;a<number;a++){
				int randInt = randomGenerator.nextInt(number-a);
				for(int b = 0;b<=randInt;b++){
					if(used[b])
						randInt++;
					}
				names[a] = party[randInt].getText();
				used[randInt] = true;
				}
			}
		
		BrackBuild brack = new BrackBuild(number, names);
		matchup = new JScrollPane(brack);
		matchup.setBounds(25,25,250,250);
		
		getContentPane().add(matchup);
		pack();
		
		refresh();

		}
		
	private class ItemHandler implements ItemListener
		{
		public void itemStateChanged(ItemEvent e){
			if(e.getItemSelectable()==randomize){
					if(e.getStateChange()==ItemEvent.DESELECTED)
						makeRandom = false;
					
					else if(e.getStateChange()==ItemEvent.SELECTED)
						makeRandom = true;
					}
			}
		}

	//utilized for handling actions
	private class ActionHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
				{
						
				if(e.getSource()==incNum){
					int val = Integer.parseInt(numPlayers.getText());
					if(val<=10000)
						val++;
					
					numPlayers.setText(""+val);
					}
					
				else if(e.getSource()==decNum){
					int val = Integer.parseInt(numPlayers.getText());
					if(val>1)
						val--;
					
					numPlayers.setText(""+val);
					}
					
				else if(e.getSource()==proceed){
					if(page == 0)
						getParticipants(Integer.parseInt(numPlayers.getText()));
							
					else if(page == 1)
						makeBracket();
						
					page++;
						
					}
					
				}
		}
		
	//utilized for exiting application
	private class WindowDestroyer extends WindowAdapter
		{
			public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				}
		}
		
	}