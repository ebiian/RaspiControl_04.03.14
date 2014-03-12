package gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import data.types.Value;

public class SwitchTimer {
    Timer timer;
    private Value activeMarker;
    private final static long ONE_DAY = 1000*60*60*24;
    private Date timerStart,timerStop;
    private boolean bStopTimer=false;
    private long durationTime;
    private Calendar calTimerStart, calTimerStop, calCurrentTime;
    private int iDayStart, iDayStop;
    private boolean bStopTimeIsNextDay=false;
    private boolean bDebugTimer=false;
    private SimpleDateFormat dateFormat;
    
    public SwitchTimer(Value activeMark, Date start,  Date stop) {
        timerStart = start;
        timerStop = stop;
    	activeMarker = activeMark;
    	timer = new Timer();   	
    	durationTime = timerStop.getTime() - timerStart.getTime();  
        
        calTimerStart = Calendar.getInstance();
        calTimerStart.setTime(timerStart);
        iDayStart=calTimerStart.get(Calendar.DAY_OF_YEAR);
        
        calTimerStop = Calendar.getInstance();
        calTimerStop.setTime(timerStop);
        iDayStop=calTimerStop.get(Calendar.DAY_OF_YEAR);
        
        bStopTimeIsNextDay=iDayStop > iDayStart;
        
        dateFormat = new SimpleDateFormat("d.MM.yyyy  HH:mm:ss");
		if(bDebugTimer)
		{
	        SimpleDateFormat dateFormat = new SimpleDateFormat("d.MM.yyyy  HH:mm:ss");
	        System.out.println("Timer Init: StartTime= "+dateFormat.format(timerStart)+ "->  StopTime= "+dateFormat.format(timerStop));
	        System.out.println("Timer Init: StopTimeIKsNextDay: "+bStopTimeIsNextDay);
		}

        timer.schedule(new SwitchTimerTask(), start, ONE_DAY);
	}

    public void stopTimer()
    {
    	bStopTimer = true;
    }
    
    class SwitchTimerTask extends TimerTask {
        @Override
    	public void run() {
        	try{        	
        		if(bDebugTimer)
        		{
        			System.out.println("SwitchTimerTask was started...");
        		}
        		long currentTimeMS = System.currentTimeMillis();
        		calCurrentTime = Calendar.getInstance();
        		calCurrentTime.setTime(new Date (currentTimeMS));
				long currentTimeTemp=(calCurrentTime.get(Calendar.HOUR_OF_DAY)*3600+calCurrentTime.get(Calendar.MINUTE)*60+calCurrentTime.get(Calendar.SECOND))*1000;				
				long startTimeTemp=(calTimerStart.get(Calendar.HOUR_OF_DAY)*3600+calTimerStart.get(Calendar.MINUTE)*60+calTimerStart.get(Calendar.SECOND))*1000;
				long stopTimeTemp=(calTimerStop.get(Calendar.HOUR_OF_DAY)*3600+calTimerStop.get(Calendar.MINUTE)*60+calTimerStop.get(Calendar.SECOND))*1000;
				if (bStopTimeIsNextDay)
				{
					stopTimeTemp=stopTimeTemp+ONE_DAY;
				}
					
				long stopTime=0;
	        	if((currentTimeTemp > startTimeTemp) && (currentTimeTemp < stopTimeTemp))
	        	{
	        		stopTime=currentTimeMS-(currentTimeTemp-startTimeTemp)+durationTime;
	        	}
	        	else
	        	{
		        	if(currentTimeTemp > stopTimeTemp)
		        	{
	        			bStopTimer=true;
	            		if(bDebugTimer)
	            		{
	            			System.out.println("currentTime > stopTime -> Stop Timer");
	            		}
		        	}
		        	else
		        	{
		        		stopTime = currentTimeMS + durationTime;
		        	}
	        	}
	        	if(!bStopTimer)
	        	{
	        		activeMarker.setValue(true);
	        	}
        		System.out.println(dateFormat.format(currentTimeMS)+": Timer start!");
				
	    		
	            if(bDebugTimer)
	    		{
		            //for debugging:
		            Date dateAct = new Date();
					Date dateStop = new Date();
					dateAct.setTime(System.currentTimeMillis());
					dateStop.setTime(stopTime);
		            System.out.println("Current time= "+dateFormat.format(dateAct)+ "->  Stop time= "+dateFormat.format(dateStop));
		            //	            
	    		}
	            while(System.currentTimeMillis() < stopTime && !bStopTimer){
	        		if(bDebugTimer)
	        		{
	        			System.out.println("Timer running until "+dateFormat.format(stopTime));
	        		}
	            	Thread.sleep(1000);
	              } 
	            System.out.println(dateFormat.format(System.currentTimeMillis())+": Timer stop");
	            activeMarker.setValue(false);
	            bStopTimer=false;
        	}catch(Exception e)
        	{
        		timer.cancel();
        		System.out.println("Error at Timer: "+e);
        	}
        }
    }
}
