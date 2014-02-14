package jiajiechen.countdown;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.toedter.calendar.JDateChooser;

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
public class DialogNew extends javax.swing.JDialog {
	private JDateChooser datePick;
	private JTextField itemname;
	private JButton submit;
	private JButton cancel;
	private JLabel name;
	private DateTime datetime;
	private String strName;
	private boolean ok;

	/**
	 * Auto-generated main method to display this JDialog
	 */

	public DialogNew(JFrame frame) {
		super(frame);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				this.setResizable(false);
				{
					datePick = new JDateChooser(new Date());
					getContentPane().add(datePick);
					datePick.setBounds(10, 10, 280, 50);
				}
				{
					name = new JLabel();
					getContentPane().add(name);
					name.setText("Name:");
					name.setBounds(10, 60, 50, 20);
				}
				{
					itemname = new JTextField();
					getContentPane().add(itemname);
					itemname.setBounds(70, 60, 220, 20);
				}
				{
					submit = new JButton();
					getContentPane().add(submit);
					submit.setText("Submit");
					submit.setBounds(10, 90, 75, 20);
					submit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							submitActionPerformed(evt);
						}
					});
				}
				{
					cancel = new JButton();
					getContentPane().add(cancel);
					cancel.setText("Cancel");
					cancel.setBounds(215, 90, 75, 20);
					cancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							cancelActionPerformed(evt);
						}
					});
				}
			}
			this.setSize(310, 140);
			setOk(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void submitActionPerformed(ActionEvent evt) {
		setDateTime(LocalDate.fromDateFields(datePick.getDate())
				.toDateTimeAtStartOfDay());
		setStrName(itemname.getText());
		ok = true;
		setVisible(false);
	}

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public DateTime getDateTime() {
		return datetime;
	}

	public void setDateTime(DateTime datetime) {
		this.datetime = datetime;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	private void cancelActionPerformed(ActionEvent evt) {
		setVisible(false);
	}

}
