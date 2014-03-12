package gui.widgets.panels;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ScreenPage extends JPanel implements IScreenPage {
	
	private static final long serialVersionUID = 4249705496615242130L;
	private String sPageName;
	private int iPageNumber;
	private JButton pageButton;

	public ScreenPage() {
	}


	@Override
	public void setPageName(String pageName) {
		sPageName=pageName;
	}

	@Override
	public void setPageNumber(int pageNumber) {
		iPageNumber= pageNumber;
	}


	@Override
	public String getPageName() {
		return sPageName;
	}


	@Override
	public int getPageNumber() {
		return iPageNumber;
	}


	@Override
	public void setPageButton(JButton pagebutton) {
		pageButton=pagebutton;
		pageButton.setText(sPageName);
	}


	@Override
	public JButton getPageButton() {
		return pageButton;
	}

}
