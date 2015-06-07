package twitter;

import java.awt.Component;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.User;

public class Panel_Tweets extends javax.swing.JPanel implements
		ListCellRenderer {

	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JTextField jTextField1;

	Twitter twitter;

	public Panel_Tweets(Twitter twitter) {
		initComponents();
		this.twitter = twitter;
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jTextField1 = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();

		jLabel1.setText("jLabel1");

		jLabel2.setText("jLabel2");
		jLabel2.setMaximumSize(new java.awt.Dimension(48, 48));
		jLabel2.setMinimumSize(new java.awt.Dimension(48, 48));
		jLabel2.setPreferredSize(new java.awt.Dimension(48, 48));

		jTextField1.setText("jTextField1");

		jLabel3.setFont(new java.awt.Font("Ubuntu", 2, 12)); // NOI18N
		jLabel3.setText("jLabel3");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jLabel1)
																.addGap(0,
																		0,
																		Short.MAX_VALUE))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jLabel2,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addGap(18, 18,
																		18)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										jLabel3)
																								.addGap(0,
																										0,
																										Short.MAX_VALUE))
																				.addComponent(
																						jTextField1,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						310,
																						Short.MAX_VALUE))))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jLabel1)
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														jLabel2,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														jTextField1,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										9, Short.MAX_VALUE)
								.addComponent(jLabel3).addContainerGap()));
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		Status status = (Status) value;
		jLabel1.setText("@" + status.getUser().getScreenName());
		jLabel3.setText(status.getCreatedAt() + "");
		jTextField1.setText(status.getText());

		URL urlIMG = null;
		try {
			urlIMG = new URL(status.getUser().getProfileImageURL());
		} catch (MalformedURLException ex) {
			Logger.getLogger(Panel_Tweets.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		ImageIcon img = new ImageIcon(urlIMG);
		jLabel2.setIcon(img);
		return this;
	}
}