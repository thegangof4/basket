package codingtest.basket;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

/**
 * A GUI program to enter a Basket of items, and to compute the total cost of the basket
 * @author tom - Black Saturn Software
 *
 */
public class BasketTestGUI implements Runnable {

	private Basket basket;
	private BasketItem[] items;
	public BasketTestGUI(Basket basket, BasketItem[] items) {
		this.basket = basket;
		this.items = items;
	}

	public class BasketFrame extends JFrame implements ActionListener{
		
		public BasketFrame() {
			super("Basket Test GUI");
		}

		public void actionPerformed(ActionEvent e) {
			Object src = e.getSource();
			if (src instanceof JMenuItem) {
			    JMenuItem source = (JMenuItem)(src);
			    String item = source.getText();
			    if (item.equals("Quit")) {
			    	System.exit(0);
			    }
			}
		}
	}

	class BasketContentPanel extends JPanel implements TableModelListener {
		private BorderLayout frameLayout = new BorderLayout();
		Banner banner;
		CenterPanel centerPanel;
		Answer answer;
		Basket basket;
		public BasketContentPanel(Banner banner, CenterPanel centerPanel, Answer answer, Basket basket) {
			this.banner = banner;
			this.centerPanel = centerPanel;
			this.answer = answer;
			this.basket = basket;
			setLayout(frameLayout);
			add(banner, BorderLayout.NORTH);
			add(centerPanel, BorderLayout.CENTER);
			add(answer, BorderLayout.SOUTH);
			centerPanel.addTableModelListener(this);
		}
		
		public void tableChanged(TableModelEvent e) {
			answer.setAnswer("Basket total value is " + String.format("%5.2f", basket.totalCost()));
	    }

	}

	class Banner extends JLabel {
		String banner;
		public Banner(String banner) {
			super(banner);
			this.banner = banner;
		}	
	}

	class Answer extends JLabel {
		String answer;
		public Answer(String answer) {
			super(answer);
			this.answer = answer;
		}
		public void setAnswer(String answer) {
			this.answer = answer;
			this.setText(answer);
		}
	}

	class CenterPanel extends JPanel {
		BasketTable table;
		CenterPanel(BasketTable table) {
			super();
			this.table = table;
			this.setPreferredSize(new Dimension(400,110));
			setLayout(new GridLayout(0,1));
			add(new JScrollPane(table));
		}
		public void addTableModelListener(TableModelListener tml) {
			table.getModel().addTableModelListener(tml);
		}
	}
	
	class BasketTable extends JTable {
		BasketTable(BasketTableModel model) {
			super(model);
		}
	}
	
	class BasketTableModel extends AbstractTableModel {
		private String[] colNames = {"Item" , "Price", "Qty"};
		BasketItem[] items;
		Basket basket;
		BasketTableModel(Basket basket, BasketItem[] items) {
			this.basket = basket;
			this.items = items;
		}
		public int getColumnCount() { 	return 3; 	}
		public int getRowCount() { return items.length; }
		public String getColumnName(int col) {  return colNames[col]; }
		public boolean isCellEditable(int row, int col) { return (col < 1 ? false : true); }
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int c) { return getValueAt(0, c).getClass(); }
		public Object getValueAt(int row, int col) {
			if (col == 0) { // item types
				return items[row].toString();
			} else if (col == 1) { // item price
				return new Float(items[row].getCost());
			} else if (col == 2) { // item qty
				return new Integer(basket.getItemCount(items[row]));
			}
			return null;
		}
		public void setValueAt(Object value, int row, int col) {
			if (col == 1) { // prices
				Float f = (Float)value;
				items[row].setCost(f.floatValue());
			} else if (col == 2) { // qtys
				Integer qty = (Integer)value;
				basket.setItemCount(items[row], qty.intValue());
			}
	        fireTableCellUpdated(row, col);
	    }
	}
	
	class BasketMenuBar extends JMenuBar {
		JMenu file = new JMenu("File");
		JMenuItem quit = new JMenuItem("Quit");
		BasketMenuBar() {
			this.add(file);
			file.add(quit);
		}
		public void addActionListener(ActionListener l) {
			quit.addActionListener(l);
		}
	}
	
	public void run() {
		BasketTableModel model = new BasketTableModel(basket, items);
		BasketTable table = new BasketTable(model);
		Banner banner = new Banner("Basket Contents");
		CenterPanel cp = new CenterPanel(table);
		Answer answer = new Answer("Enter prices and qtys");	
		BasketContentPanel contentPane = new BasketContentPanel(banner, cp, answer, basket);
		BasketMenuBar menu = new BasketMenuBar();
		BasketFrame frame = new BasketFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(contentPane);
        frame.setJMenuBar(menu);
 
        contentPane.setOpaque(true); //content panes must be opaque
        menu.addActionListener(frame);
        
        frame.pack();
        frame.setVisible(true);
	}
 
    public static void main(String[] args) {
    	Basket basket = new Basket();
    	BasketItem[] items = BasketItem.values();
    	BasketTestGUI gui = new BasketTestGUI(basket, items);
        javax.swing.SwingUtilities.invokeLater(gui);
    }

}
