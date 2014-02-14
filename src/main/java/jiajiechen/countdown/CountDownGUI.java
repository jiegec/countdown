package jiajiechen.countdown;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
@SuppressWarnings("serial")
public class CountDownGUI extends javax.swing.JFrame {

	private static Map<String, DateTime> countdown;
	private JTextField time;
	private JButton buttonExport;
	private JButton buttonImport;
	private JButton buttonDelete;
	private JButton buttonNew;
	private JTable table;
	private JButton about;
	private static Locale locale;
	private static ResourceBundle bundle;
	private static String className = new Object() {
		public String getClassName() {
			String clazzName = this.getClass().getName();
			return clazzName.substring(clazzName.lastIndexOf('.') + 1,
					clazzName.lastIndexOf('$'));
		}
	}.getClassName();

	private static String getString(String key) {
		String ret;
		try {
			ret = bundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
		return ret;
	}

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		locale = Locale.getDefault();
		bundle = ResourceBundle.getBundle(className, locale);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				CountDownGUI inst = new CountDownGUI();
				inst.setTitle("CountDown");
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				inst.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						CountDownGUI.writeToFile("");
					}
				});
			}
		});
	}

	public CountDownGUI() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			getContentPane().setLayout(null);
			this.setResizable(false);
			{
				time = new JTextField();
				getContentPane().add(time);
				time.setBounds(10, 10, 300, 25);
			}
			{
				about = new JButton();
				getContentPane().add(about);
				about.setText(getString("About"));
				about.setBounds(320, 10, 80, 25);
				about.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						aboutActionPerformed(evt);
					}
				});
			}
			{
				String[] headers = { getString("Item"), getString("Period") };
				final Object rows[][] = { { "", "" } };
				DefaultTableModel tableModel = new DefaultTableModel(rows,
						headers) {
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				table = new JTable(tableModel);
				JScrollPane scrollpane = new JScrollPane(table);
				scrollpane.setBounds(10, 45, 390, 190);
				getContentPane().add(scrollpane);
			}
			{
				buttonNew = new JButton();
				getContentPane().add(buttonNew);
				buttonNew.setText(getString("New"));
				buttonNew.setBounds(10, 245, 90, 25);
				buttonNew.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						buttonNewActionPerformed(evt);
					}
				});
			}
			{
				buttonDelete = new JButton();
				getContentPane().add(buttonDelete);
				buttonDelete.setText(getString("Delete"));
				buttonDelete.setBounds(110, 245, 90, 25);
				buttonDelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						buttonDeleteActionPerformed(evt);
					}
				});
			}
			{
				buttonImport = new JButton();
				getContentPane().add(buttonImport);
				buttonImport.setText(getString("Import"));
				buttonImport.setBounds(210, 245, 90, 25);
				buttonImport.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						buttonImportActionPerformed(evt);
					}
				});
			}
			{
				buttonExport = new JButton();
				getContentPane().add(buttonExport);
				buttonExport.setText(getString("Export"));
				buttonExport.setBounds(310, 245, 90, 25);
				buttonExport.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						buttonExportActionPerformed(evt);
					}
				});
			}
			pack();
			this.setSize(410, 300);

			countdown = new HashMap<String, DateTime>();
			readFromFile("");
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					doRefresh();
				}

			}, 0, 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buttonNewActionPerformed(ActionEvent evt) {
		DialogNew newdialog = new DialogNew(this);
		newdialog.setLocationRelativeTo(null);
		newdialog.setModal(true);
		newdialog.setVisible(true);
		if (newdialog.isOk()) {
			countdown.put(newdialog.getStrName(), newdialog.getDateTime());
			doRefresh();
		}
	}

	private void doRefresh() {
		DateTime d = new DateTime();
		time.setText(DateTimeFormat.longDateTime().print(d));
		// TableRowModel<String> lm = new DefaultListModel<String>();
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		while (tableModel.getRowCount() < countdown.size()) {
			final Object rows[][] = { { "", "" } };
			tableModel.addRow(rows);
		}
		Integer row = 0;
		for (Entry<String, DateTime> e : countdown.entrySet()) {
			DateTime itemdate = e.getValue();
			Period period = new Period(d, itemdate);
			PeriodFormatter pf = new PeriodFormatterBuilder().appendYears()
					.appendSuffix("y ").appendMonths().appendSuffix("m ")
					.appendWeeks().appendSuffix("w ").appendDays()
					.appendSuffix("d ").appendHours().appendSuffix("h ")
					.appendMinutes().appendSuffix("m ").appendSeconds()
					.appendSuffix("s ").toFormatter();
			tableModel.setValueAt(e.getKey(), row, 0);
			tableModel.setValueAt(pf.print(period), row, 1);
			row++;
		}

	}

	private void buttonDeleteActionPerformed(ActionEvent evt) {
		for (Integer i : table.getSelectedRows()) {
			String key = (String) table.getModel().getValueAt(i, 0);
			((DefaultTableModel) table.getModel()).removeRow(i);
			countdown.remove(key);
		}
		doRefresh();
	}

	private void readFromFile(String filename) {
		Preferences userData = Preferences
				.userNodeForPackage(CountDownGUI.class);
		if (filename.equals("") != true) {
			try {
				FileInputStream f = new FileInputStream(filename);
				Preferences.importPreferences(f);
				f.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidPreferencesFormatException e) {
				e.printStackTrace();
			}

		}
		Integer items = Integer.valueOf(userData.get("items", "0"));
		for (Integer i = 0; i < items; i++) {
			String itemname = userData.get("item" + String.valueOf(i) + "name",
					"");
			DateTime itemdate = DateTime.parse(userData.get(
					"item" + String.valueOf(i) + "date", ""));
			countdown.put(itemname, itemdate);
		}
	}

	public static void writeToFile(String filename) {
		Preferences userData = Preferences
				.userNodeForPackage(CountDownGUI.class);
		if (filename.equals("") != true) {
			FileOutputStream f = null;
			try {
				f = new FileOutputStream(filename);
				userData.exportNode(f);				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (BackingStoreException e) {
				e.printStackTrace();
			}finally {
				if(f != null) {
					try {
						f.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
		Integer size = countdown.size();
		userData.put("items", String.valueOf(size));
		Iterator<Entry<String, DateTime>> iter = countdown.entrySet()
				.iterator();
		Integer i = 0;
		while (iter.hasNext()) {
			Entry<String, DateTime> entry = (Entry<String, DateTime>) iter
					.next();
			userData.put("item" + String.valueOf(i) + "name", entry.getKey());
			userData.put("item" + String.valueOf(i) + "date", DateTimeFormat
					.mediumDate().print(entry.getValue()));
			i++;
		}
	}

	private void buttonImportActionPerformed(ActionEvent evt) {
		JFileChooser fd = new JFileChooser();
		fd.setCurrentDirectory(new File("."));
		if(fd.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			File f = fd.getSelectedFile();
			readFromFile(f.getAbsolutePath());
		}
		
	}

	private void buttonExportActionPerformed(ActionEvent evt) {
		JFileChooser fd = new JFileChooser();
		fd.setCurrentDirectory(new File("."));
		fd.showSaveDialog(null);
		File f = fd.getSelectedFile();
		CountDownGUI.writeToFile(f.getAbsolutePath());
	}
	
	private void aboutActionPerformed(ActionEvent evt) {
	    JOptionPane.showMessageDialog(null, "Jiajie Chen QQ:875053761 Website:http://chenstudio.tk");  
	}

}
