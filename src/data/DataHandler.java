package data;

import gui.MainFrame;
import gui.widgets.panels.ScreenPage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import constants.Constants;
import data.types.Value;

public class DataHandler extends Thread {
	boolean bTest = false;
	int iPanelNumber = 0;
	Value valueToChange;
	int iActWebIfPage = 1;
	String sOpMode;
	boolean bOpModeChangeRequest;
	ScreenPage actScreenPage;
	List<ScreenPage> screenPages;
	
	List<Value> params = new ArrayList<Value>();
	Set<JPanel> panels = new HashSet<JPanel>();
	Value[] panelVars;
	Map<JPanel, Value[]> panelsandValues = new LinkedHashMap<JPanel, Value[]>();

	private static DataHandler INSTANCE;

	public DataHandler() {
		INSTANCE = this;
	}

	public static DataHandler getInstance() {
		return INSTANCE;
	}

	public void addParam(Value val) {
		params.add(val);
		if (!panels.add((JPanel) val.getAssignedPanel())) { // add liefert
															// false, wenns das
															// panel schon
															// gibt..
			switch (val.getVarNr()) {
			case (Constants.PANEL_VARIABLE_1): {
				panelVars[0] = val;
				break;
			}
			case (Constants.PANEL_VARIABLE_2): {
				panelVars[1] = val;
				break;
			}
			case (Constants.PANEL_MARK): {
				panelVars[2] = val;
				break;
			}
			}
		} else {
			iPanelNumber++;
			panelVars = new Value[3];
			switch (val.getVarNr()) {
			case (Constants.PANEL_VARIABLE_1): {
				panelVars[0] = val;
				break;
			}
			case (Constants.PANEL_VARIABLE_2): {
				panelVars[1] = val;
				break;
			}
			case (Constants.PANEL_MARK): {
				panelVars[2] = val;
				break;
			}
			}
			panelsandValues.put((JPanel) val.getAssignedPanel(), panelVars);
		}
	}

	public List<Value> getParams() {
		return params;
	}

	public Set<JPanel> getPanels() {
		return panels;
	}

	public Map<JPanel, Value[]> getPanelsAndValues() {
		return panelsandValues;
	}

	public boolean paramFileExists() {
		File paramFile = new File(Constants.PARAM_FILE);
		if (!paramFile.exists()) {
			return false;
		}
		return true;
	}

	public List<String> readParams() {
		LineNumberReader f1 = null;
		String line = null;
		List<String> lines = new ArrayList<String>();
		try {
			f1 = new LineNumberReader(new FileReader(Constants.PARAM_FILE));

			while ((line = f1.readLine()) != null) {
				lines.add(line);
			}
			f1.close();
			return lines;
		} catch (Exception e) {
			System.out.println("Error reading data: " + e);
			return lines;
		}
	}

	public void writeParams() {
		if (!MainFrame.bInitValues) {
			FileWriter f1;
			try {
				f1 = new FileWriter(Constants.PARAM_FILE);

				for (Value param : params) {
					if (!param.isReadOnly()) {
						String line = param.getVarName() + ";"
								+ param.getLongText() + ";"
								+ param.getVarType() + ";" + param.getValue()
								+ ";" + param.getPlaus()[0] + ";"
								+ param.getPlaus()[1] + ";"
								+ param.getUnitText();
						f1.write(line);
						f1.write(System.getProperty("line.separator"));
					}
				}

				f1.close();
			} catch (Exception e) {
				System.out.println("Error writing data: " + e);

			}

		}
	}

	public void setValueToChange(Value valueToChange) {
		this.valueToChange = valueToChange;
	}

	public Value getValueToChange() {
		return valueToChange;
	}

	public void setActWebIfPage(int ipage) {
		iActWebIfPage = ipage;
	}
	public void setActScreenPage(ScreenPage page)
	{
		actScreenPage = page;
	}
	
	public int getActWebIfPage() {
		return iActWebIfPage;
	}
	
	public ScreenPage getActScreenPage()
	{
		return actScreenPage;
	}
	public void setOpMode(String opmode) {
		sOpMode = opmode;
	}
	
	public String getOpMode() {
		return sOpMode;
	}
	
	public void setOpModeChangeRequest(boolean request)
	{
		bOpModeChangeRequest=request;
	}
	
	public boolean isOpModeChangeRequested()
	{
		return bOpModeChangeRequest;
	}
	public List<ScreenPage> getAvailableScreenPages(){
		return screenPages;
	}
	public void setAvailableScreenPages(List<ScreenPage> pages)
	{
		screenPages=pages;
	}
}
