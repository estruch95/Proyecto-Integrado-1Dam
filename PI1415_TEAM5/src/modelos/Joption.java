package modelos;

import java.awt.HeadlessException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import controladores.IdiomaController;

public class Joption extends JOptionPane {

	public Joption() {
		// TODO Auto-generated constructor stub
	}
	
	public static String showMesageDialog(final Object message, final Object[] options)
		throws HeadlessException {
		final JOptionPane pane = new JOptionPane(message, INFORMATION_MESSAGE, OK_CANCEL_OPTION,
				null, options, null);
		//pane.setWantsInput(true);
		pane.setMessage(message);
		pane.setComponentOrientation((getRootFrame()).getComponentOrientation());
		pane.setMessageType(OK_CANCEL_OPTION);
		pane.selectInitialValue();
		//final String title = UIManager.getString("OptionPane.messageDialogTitle", null);
		IdiomaController traduccion = IdiomaController.getInstance();

		final JDialog dialog = pane.createDialog(null, traduccion.getTraduccion("nombre_aviso"));
		dialog.setVisible(true);
		dialog.dispose();
		final Object value = pane.getInputValue();
		return (value == UNINITIALIZED_VALUE) ? null : (String) value;
	}
	

}
