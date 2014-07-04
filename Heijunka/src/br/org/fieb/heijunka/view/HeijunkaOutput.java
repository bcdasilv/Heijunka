package br.org.fieb.heijunka.view;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.org.fieb.heijunka.HeijunkaBox;
import br.org.fieb.heijunka.algorithm.DefaultHJVariableSlotsWithMonthlyDemand;
import br.org.fieb.heijunka.algorithm.IHeijunkaStrategy;
import br.org.fieb.heijunka.algorithm.InvalidHeijunkaInputException;
import br.org.fieb.heijunka.model.Demand;
import br.org.fieb.heijunka.model.WorkSchedule;


public class HeijunkaOutput extends JFrame{

	private Demand demandMap;
	private WorkSchedule workSchedule;
	private IHeijunkaStrategy heijunkaAlgorithm;
	private HeijunkaBox heijunkaBox;
	
	JPanel painelFundo;	
	JScrollPane barraRolagem;  		   
	String [] column = {"08:00 as 09:00", "09:00 as 10:00", "10:00 as 11:00", "11:00 as 12:00",
			"12:00 as 13:00", "13:00 as 14:00", "14:00 as 15:00", "15:00 as 16:00"};
	
	public void criaJanela(HeijunkaBox hBox){ 
						
		painelFundo = new JPanel();
		painelFundo.setLayout(new GridLayout(1, 1));				
		JTable tabela = new JTable(hBox.getBoxIntegerArray(), column);		
		barraRolagem = new JScrollPane(tabela);
		painelFundo.add(barraRolagem); 
		getContentPane().add(painelFundo); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setSize(500, 120);
		setVisible(true); 
	}
}
