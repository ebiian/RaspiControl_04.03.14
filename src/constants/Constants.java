package constants;

import java.awt.Color;
import java.io.File;

public class Constants {
	public static final String PARAM_FILE = System.getProperty("user.dir")
			+ File.separator + "piControl.data";
	public static final String BOOL_ACTIVATE = "Einschalten?";
	public static final String BOOL_DEACTIVATE = "Ausschalten?";
	public static final String VARTYPE_INT = "i";
	public static final String VARTYPE_DOUBLE = "d";
	public static final String VARTYPE_BOOLEAN = "b";
	public static final String VARTYPE_STRING = "s";
	public static final String BOOLEAN_TRUE = "true";
	public static final String BOOLEAN_FALSE = "false";
	public static final int PANEL_VARIABLE_1 = 1;
	public static final int PANEL_VARIABLE_2 = 2;
	public static final int PANEL_MARK = 3;
	public static final Color MARK_COLOR_INPUT = Color.green;
	public static final Color MARK_COLOR_OUTPUT = Color.red;
	public static final Color MARK_COLOR_TOLERANCE = Color.CYAN;
	public static final Color MARK_COLOR_ACTIVE = Color.MAGENTA;
	public static final boolean READONLY = true;
	public static final boolean READWRITE = false;
	public static final String PAGE = "page_";
	public static final String OPMODE_MANUAL = "Manual";
	public static final String OPMODE_AUTOMATIC = "Automatic";
}
