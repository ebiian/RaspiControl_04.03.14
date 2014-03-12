package data.types;

import gui.widgets.panels.BooleanSwitchPanel;
import gui.widgets.panels.DoubleInputPanel;
import gui.widgets.panels.MarkPanel;
import gui.widgets.panels.SingleInputPanel;

import java.util.Observable;

import com.pi4j.io.gpio.GpioPin;

import constants.Constants;
import data.DataHandler;

public class Value extends Observable {
	Object value;
	Object value2;
	Object assignedPanel;
	String sVarName, sLongText, sUnitText, sVarType;
	String sVarName2, sLongText2, sUnitText2, sVarType2;
	double dMinPlaus, dMaxPlaus;
	double dMinPlaus2, dMaxPlaus2;
	int iVarNr;
	boolean bReadOnly;
	GpioPin gpioPin;

	public Value(String varName, String longText, Object val, double minPlaus,
			double maxPlaus, String unit, Object inputPanel, int varNr,
			boolean readOnly, GpioPin pin) {
		iVarNr = varNr;
		sVarName = varName;
		sLongText = longText;
		sUnitText = unit;
		value = val;
		assignedPanel = inputPanel;
		dMinPlaus = minPlaus;
		dMaxPlaus = maxPlaus;
		bReadOnly = readOnly;
		gpioPin = pin;

		getDataHandler().addParam(this);
		if (inputPanel instanceof SingleInputPanel) {
			((SingleInputPanel) inputPanel).initPanel(this);
		}
		if (inputPanel instanceof DoubleInputPanel) {
			if (varNr == Constants.PANEL_VARIABLE_1)
				((DoubleInputPanel) inputPanel).initPanelVar1(this);
			if (varNr == Constants.PANEL_VARIABLE_2)
				((DoubleInputPanel) inputPanel).initPanelVar2(this);
			if (varNr == Constants.PANEL_MARK)
				((DoubleInputPanel) inputPanel).initPanelMark(this);
		}
		if (inputPanel instanceof BooleanSwitchPanel) {
			((BooleanSwitchPanel) inputPanel).initPanel(this);
		}
		if (inputPanel instanceof MarkPanel) {
			((MarkPanel) inputPanel).initPanel(this);
		}

	}

	private DataHandler getDataHandler() {
		return DataHandler.getInstance();
	}

	public void setValue(Object val) {
		if (val instanceof Double) {
			value = (double) val;
			sVarType = Constants.VARTYPE_DOUBLE;
		} else {
			if (val instanceof Integer) {
				value = (int) val;
				sVarType = Constants.VARTYPE_INT;
			} else {
				if (val instanceof Boolean) {
					value = (boolean) val;
					sVarType = Constants.VARTYPE_BOOLEAN;
				} else {
					if (val instanceof String) {
						value = val;
						sVarType = Constants.VARTYPE_STRING;
					}

				}
			}
		}
		if (assignedPanel instanceof SingleInputPanel) {
			((SingleInputPanel) assignedPanel).getInputField().setText(
					value + "");
		}
		if (assignedPanel instanceof DoubleInputPanel) {
			if (iVarNr == Constants.PANEL_VARIABLE_1)
				((DoubleInputPanel) assignedPanel).getInputField().setText(
						value + "");
			if (iVarNr == Constants.PANEL_VARIABLE_2)
				((DoubleInputPanel) assignedPanel).getInputField2().setText(
						value + "");
			if (iVarNr == Constants.PANEL_MARK)
				((DoubleInputPanel) assignedPanel).getPanelMark().setActive(
						(boolean) value);

		}
		if (assignedPanel instanceof BooleanSwitchPanel) {
			((BooleanSwitchPanel) assignedPanel).getSwitch().setActive(
					(boolean) value);
		}
		if (assignedPanel instanceof MarkPanel) {
			((MarkPanel) assignedPanel).setMarkActive((boolean) value);
		}
		getDataHandler().writeParams();
		setChanged();// ObserverTest
		notifyObservers(val);// ObserverTest
	}

	public void setPlaus(double dMin, double dMax) {
		dMinPlaus = dMin;
		dMaxPlaus = dMax;
	}

	public Object getValue() {
		return value;
	}

	public String getVarType() {
		return sVarType;
	}

	public String getLongText() {
		return sLongText;
	}

	public double[] getPlaus() {
		return new double[] { dMinPlaus, dMaxPlaus };
	}

	public Object getAssignedPanel() {
		return assignedPanel;
	}

	public String getVarName() {
		return sVarName;
	}

	public String getUnitText() {
		return sUnitText;
	}

	public boolean isReadOnly() {
		return bReadOnly;
	}

	public int getVarNr() {
		return iVarNr;
	}
	public GpioPin getGpioPin()
	{
		return gpioPin;
	}
}
