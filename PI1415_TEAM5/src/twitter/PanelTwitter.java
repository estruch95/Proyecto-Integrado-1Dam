package twitter;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class PanelTwitter extends JPanel{

	private JList jList1;
	private DefaultListModel statuses;
	private JScrollPane jScrollPane1;
	Twitter twitter;

	public PanelTwitter(Twitter twitter){
		
		this.twitter = twitter;
		jList1 = new JList();
		statuses = new DefaultListModel();
		setSize(400, 300);
		
		jList1.setModel(statuses);
		jList1.setCellRenderer(new Panel_Tweets(this.twitter));
		jScrollPane1 = new JScrollPane(jList1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//jScrollPane1.setViewportView(jList1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
						//.addContainerGap())
		)));
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
				.addContainerGap())
		);
		
		actualizar();

	}
	
	public void actualizar() {
		Paging pagina = new Paging();
		pagina.setCount(50);
		ResponseList<Status> listado = null;
		try {
			listado = twitter.getHomeTimeline(pagina);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < listado.size(); i++) {
			statuses.addElement(listado.get(i));
		}
	}
}
