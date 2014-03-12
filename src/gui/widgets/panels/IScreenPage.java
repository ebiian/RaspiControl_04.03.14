package gui.widgets.panels;

import javax.swing.JButton;

public interface IScreenPage {

	public void setPageName(String pageName);
	public void setPageNumber (int pageNumber);
	public void setPageButton(JButton pageButton);

	public String getPageName();
	public int getPageNumber();
	public JButton getPageButton();
}
