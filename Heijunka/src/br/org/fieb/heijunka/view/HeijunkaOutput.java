package br.org.fieb.heijunka.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.org.fieb.heijunka.HeijunkaBox;
import br.org.fieb.heijunka.algorithm.DefaultHJVariableSlotsWithMonthlyDemand;
import br.org.fieb.heijunka.algorithm.IHeijunkaStrategy;
import br.org.fieb.heijunka.algorithm.InvalidHeijunkaInputException;
import br.org.fieb.heijunka.model.Demand;
import br.org.fieb.heijunka.model.TimeSlot;
import br.org.fieb.heijunka.model.WorkSchedule;


public class HeijunkaOutput extends JFrame{
	
	public HeijunkaOutput(String title){
		super(title);
	}
		
	public void criaJanela(HeijunkaBox hBox){ 
		
		JTabbedPane tabbedPane = new JTabbedPane();
						
		String [] columns = obterColunas(hBox.getWorkSchedule());
		
		JTable tabela1 = new JTable(hBox.getBoxIntegerArray(), columns);
		
		hBox.setHeijunkaAlgorithm(new DefaultHJVariableSlotsWithMonthlyDemand());
		
		JTable tabela2 = new JTable(hBox.getBoxIntegerArray(), columns);		
		JScrollPane barraRolagem1 = new JScrollPane(tabela1);
		JScrollPane barraRolagem2 = new JScrollPane(tabela2);
		
		tabbedPane.addTab("Estratégia nivelamento 1", null, barraRolagem1, "Estratégia básica do heijunka.");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		
		tabbedPane.addTab("Estratégia nivelamento 2", null, barraRolagem2, "Estratégia básica do heijunka considerando demanda mensal.");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		
		tabela1.setFillsViewportHeight(true);
		tabela2.setFillsViewportHeight(true);
		
		this.add(tabbedPane, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setMinimumSize(new Dimension(300, 300));
		this.setVisible(true);

	}

	private String[] obterColunas(WorkSchedule workSchedule) {
		
		Object[] timeSlots = workSchedule.getWorkTimeSlots().toArray();
		String [] columns = new String[timeSlots.length];
		for (int i = 0; i < timeSlots.length; i++) {
			TimeSlot timeSlot = (TimeSlot)timeSlots[i];
			columns[i] = timeSlot.getStartTime()+"-"+timeSlot.getEndTime();
		}
		return columns;
	}
}
