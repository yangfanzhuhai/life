import java.awt.event.ActionEvent;

public class Controller implements java.awt.event.ActionListener {
	private final Model model;

	public Controller(Model m) {
		model = m;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public void initModel() {
		model.initDefault();
	}

}
