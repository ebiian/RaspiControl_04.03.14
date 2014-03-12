package servlet;

import gui.widgets.panels.ScreenPage;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.Constants;
import data.DataHandler;
import data.types.Value;

public class WebIfServlet extends HttpServlet {
	private static final long serialVersionUID = -6617893791621340358L;
	private final DataHandler dataHandler = DataHandler.getInstance();
	List<ScreenPage> screenPages;
	List<Value> params;
	
	public WebIfServlet() {
		screenPages=DataHandler.getInstance().getAvailableScreenPages();
		params = dataHandler.getParams();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		
		String par;
		String wantedPage;
		String sOpModeChangeRequest = req.getParameter("opmode");
		String sWantedOpMode = req.getParameter("operationmode");
		
		//opmode
		if(sWantedOpMode!= null)
		{
			if(sWantedOpMode.equals(Constants.OPMODE_AUTOMATIC))
			{
				gui.MainFrame.getInstance().switchToAutoMode();
			}
			if(sWantedOpMode.equals(Constants.OPMODE_MANUAL))
			{
				gui.MainFrame.getInstance().switchToManualMode();				
			}
			System.out.println(sWantedOpMode);
			dataHandler.setOpModeChangeRequest(false);
			resp.sendRedirect(req.getContextPath() + "/page/center.jsp");
			return;
		}
		if(sOpModeChangeRequest!= null)
		{
			dataHandler.setOpModeChangeRequest(true);
			resp.sendRedirect(req.getContextPath() + "/page/center.jsp");
			return;
			
		}

		//screenpages
		wantedPage = req.getParameter("scrpage");
		for (ScreenPage scrPage : screenPages) {
			if (wantedPage != null) {
				if (wantedPage.equals(scrPage.getPageNumber()+"")) 
				{
					dataHandler.setActScreenPage(scrPage);
					resp.sendRedirect(req.getContextPath() + "/page/center.jsp");
					return;
				}
			}
		}

		//parameters
		for (Value val : params) {
			par = req.getParameter(val.getVarName());
			if (par != null) {
				if (par.equals(val.getVarName())) { // wenn kein Wert sondern
													// der varname übergeben
													// wird...
					dataHandler.setValueToChange(val);
				} else // take over value
				{
					dataHandler.setValueToChange(null);
					if (val.getValue() instanceof Integer) {
						val.setValue(Integer.parseInt(par));
					} else {
						if (val.getValue() instanceof Double) {
							val.setValue(Double.parseDouble(par));
						} else {
							if ((val.getValue() instanceof Boolean)) {
								val.setValue(Boolean.parseBoolean(par));
							} else {
								val.setValue(par);
							}
						}
					}

				}
			}
		}
		resp.sendRedirect(req.getContextPath() + "/page/center.jsp");
	}
}