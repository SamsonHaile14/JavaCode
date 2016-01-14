import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.lang.Math.*;

public class BrackBuild extends JPanel
	{
	
	private int num;
	private String[] team;
	
	public BrackBuild(int size,String[] names)
		{
		num = size;
		team = new String[num];
		
		for(int a = 0;a<num;a++)
			team[a] = names[a];
			
		}
	
	public Dimension getPreferredSize(){
		return new Dimension(300*((int)(java.lang.Math.log(num)+2)),40*num+10);
		}
			
	protected void paintComponent(Graphics g)
		{
		super.paintComponent(g);
		setBackground(Color.WHITE);	
			
		int temp = num;
		int[] placement = new int[num];
		
		for(int a = 1;a<num+1;a++)
			placement[a-1] = a*20;
		
		g.setColor(Color.BLACK);
			
		int incr = 1;
		
		int targ = 1;
		
		while(targ<num)
			targ*=2;
		
		if(targ!=num){
			targ/=2;
			for(int a = 0;a<(num-targ);a++){
				g.drawString(team[2*a],0,placement[2*a]);
				g.drawLine(100*incr,placement[2*a],100*(incr+1),placement[2*a]);
				
				g.drawString(team[2*a+1],0,placement[2*a+1]);
				g.drawLine(100*incr,placement[2*a+1],100*(incr+1),placement[2*a+1]);
				
				g.drawLine(100*(incr+1),placement[2*a],100*(incr+1),placement[2*a+1]);
				placement[a] = (placement[2*a]+placement[2*a+1])/2;
				}
				
			for(int b = (num-targ);b<targ;b++){
				placement[b] = (placement[b-1]+40);
				g.drawString(team[(num-targ)+b],100*(incr+1),placement[b]);
				}
				
			temp = targ;
			}
			
		incr++;
			
		while(temp!=0){
			
			for(int a = 0;a<temp;a++)
				g.drawLine(100*incr,placement[a],100*(incr+1),placement[a]);
				
			for(int a = 0;a<temp/2;a++)
				g.drawLine(100*(incr+1),placement[2*a],100*(incr+1),placement[2*a+1]);
				
			temp/=2;
			
			for(int a = 0;a<temp;a++)
				placement[a] = (placement[2*a]+placement[2*a+1])/2;
				
			incr++;
			}
		}
	
	}