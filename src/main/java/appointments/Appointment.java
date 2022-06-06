package appointments;

import java.time.LocalDate;
import java.time.LocalTime;

import db.Gui;
import db.MessageBoardOptions;

/******************************************************************************/
/* Guilherme Virgilio P. O. Simoes                                            */
/* Login ID: pici7127                                                         */
/* CS-102, Winter 2016                                                        */
/* Programming Assignment 5                                                   */
/* Appointment calendar: contents of a single appointment are stored here     */
/* (one appointment per instance)                                             */
/******************************************************************************/

/**
 * 
 * @author Guilherme Virgilio P. O. Simoes 
 * @version 1.1.0
 * 
 * Transformation of CS-102 project into a Maven project
 */
public class Appointment {    
    private String calendarName = null; //will contain the calendar it refers to
    private String date;        
    private String startTime;   //when the appointment begins
    private String endTime;     //when the appointment ends (must not be
                                // less than startTime 
    private String description = null; //will contain txt info about appointment
    
    private Gui theGUI; //reference to the GUI
    
    /*
    Adding integer values for day, month, year, hour, minute to facilitate 
    later operations over those items in the appointments.
    */
    
    private int day;            //Part of date that represents the day DD
    private int month;          //Part of date that represents the month MM
    private int year;           //Part of date that represents the year YYYY
    private int hourS;          //Part of startTime that represents the hours
    private int minuteS;        //Part of startTime that represents the minutes
    private int hourE;          //Part of endTime that represents the hours
    private int minuteE;        //Part of endTime that represents the minutes
        
    public void changeCalendarName (String newName){
        this.calendarName = newName;
    }

/******************************************************************************/
/* Method: setDate                                                            */
/* Purpose: change the date in a single appointment                           */
/*                                                                            */
/* Parameters:                                                                */
/*  String  newDate:                                                          */
/* Returns: Boolean:    was able to add (values are legal)?                   */
/******************************************************************************/
    
    public boolean setDate (String newDate){
        try{
            if(isValid (Integer.parseInt(newDate))){
            	this.date = newDate;
                this.year = Integer.parseInt(newDate.substring(0, 4));
                this.month = Integer.parseInt(newDate.substring(4, 6));
                this.day = Integer.parseInt(newDate.substring(6, 8));         
                return (true);
            }
            
            else{
                return(false);
            }
                
        }
        catch (NumberFormatException exc){
            theGUI.printToGui(MessageBoardOptions.MESSAGE.getOption(), newDate + " is not a valid format for "
                    + "dates. "
                    + "It was not set.");
            theGUI.bufferToGui(MessageBoardOptions.MESSAGE.getOption());
            return (false); //added so that the program can continue without
            //breaking
            //System.exit(3);
        }
    }   
    
/******************************************************************************/
/* Method: isValid (one argument)                                             */
/* Purpose: check if the given int correspond to an actual date  of the year  */
/* (Gregorian Calendar)                                                       */
/* Parameters:                                                                */
/*  int date:           given date                                            */
/* Returns: Boolean:    is date valid?                                        */
/******************************************************************************/
    
    public boolean isValid (int date){
        int copyOfDate = date;  //used to keep track of calculations on "date"
    // without changing its value
        int lastDay = 31;    //last day of the month (depends on the month)
        copyOfDate %= 10000; //Takes the year out of the number
        
        switch (copyOfDate / 100){  //gets the month, only. Takes the day out.
            case (1): case (3): case (5): case (7): case (8): case (10):
            case (12): //31 days month
                       //does not enter DEFAULT. Does not change lastDay.
                  break;         
            case (4): case (6): case (9): case (11): //30 days month
                lastDay = 30;
                  break;
            case (2):{
                if (isLeapYear(date/1000)){
                    lastDay = 29;
                }
                else
                    lastDay = 28;
            } break;
            
            default:
                return false;
        }
                        
    // True will be returned if the day is within the limits of the month
        return !((copyOfDate % 100  < 1) || (copyOfDate % 100  > lastDay));
    } 
    
/******************************************************************************/
/* Method: isLeapYear                                                         */
/* Purpose: check if the given year has 29 days for the month of February (2) */
/*                                                                            */
/* Parameters:                                                                */
/*  int year:           given year                                            */
/* Returns: Boolean:    is year leap?                                         */
/******************************************************************************/
    
    public boolean isLeapYear (int year){
    boolean leapyear = false;   //initially assume it is not a leap year
    //Check if divisible by 4
    if (year%4 == 0)
        leapyear = true;
    //Do additional checks (year might not be leap even if divisible by 4)
    if (year % 100 == 0)
        leapyear = false;

    if (year% 400 == 0)
        leapyear = true;
    
    return leapyear;
    }

/******************************************************************************/
/* Method: setTime                                                            */
/* Purpose: change the time in a single appointment                           */
/*                                                                            */
/* Parameters:                                                                */
/*  String  startTime:         appointment starts                             */
/*  String  endTime:           appointment ends                               */
/* Returns: Boolean:    was able to add (values are legal)?                   */
/******************************************************************************/    
    
    public boolean setTime (String startTime, String endTime){
        try{
            if(isValid (Integer.parseInt(startTime), 
                        Integer.parseInt(endTime))){
                this.startTime = startTime;
                    this.hourS = Integer.parseInt(startTime.substring(0, 2));
                    this.minuteS = Integer.parseInt(startTime.substring(2, 4));
                this.endTime = endTime;
                    this.hourE = Integer.parseInt(endTime.substring(0, 2));
                    this.minuteE = Integer.parseInt(endTime.substring(2, 4));
                return (true);
            }           
            else{
                return (false);
            }
        }
        catch (NumberFormatException exc){
            theGUI.printToGui(MessageBoardOptions.MESSAGE.getOption(), startTime + "/" + endTime + " is not a "
                    + "valid format for times.");
            theGUI.bufferToGui(MessageBoardOptions.MESSAGE.getOption());
                return (false); //added so that the program will not crash
                //will not include the invalid time either
                //System.exit(4);            
        }      
    }
    
/******************************************************************************/
/* Method: isValid (two arguments)                                            */
/* Purpose: check if the given int correspond to an actual time of the day    */
/*          and if the end time is greater than the start time                */
/* Parameters:                                                                */
/*  int start:          appointment start time                                */
/*  int end:            appointment end time                                  */
/* Returns: Boolean:    is time valid?                                        */
/******************************************************************************/
    
    public boolean isValid (int start, int end){
        //check if hours or minutes fall out of range
        if (start/100 < 0 || start/100 > 23 || start%100 < 0 || start%100 > 59)
                return false;
        if (end/100 < 0 || end/100 > 23 || end%100 < 0 || end%100 > 59)
                return false;
        //check if start time is greater than end time (this should be false)
        if (start/100 > end/100)
                return false;
        //however, hours can be equal with different minutes.
        if ((start/100 == end/100) && (start/100 > end/100))
                return false;
        //if it doesn't fail those conditions, it is true
        return true;
    }
 
/******************************************************************************/
/* Method: set<FEATURE>                                                       */
/* Purpose: change the calendar's feature specified between < >               */
/*                                                                            */
/* Parameters:                                                                */
/*  String  new<FEATURE>:                                                     */
/* Returns: void                                                              */
/******************************************************************************/    

    public void setDescription (String newDescription){
        this.description = newDescription;
    }
    
    public void setGUI (Gui GUI){
        this.theGUI = GUI;
    }


/******************************************************************************/
/* Method: get<FEATURE>                                                       */
/* Purpose: check the Appointment object's feature specified between < >      */
/*                                                                            */
/* Parameters:                                                                */
/*  void                                                                      */
/* Returns: String:    the calendar's feature                                 */
/******************************************************************************/
    
    public String getCalendarName (){
        return calendarName;
    }
    
    public String getDate (){
        return date;
    }
    
    public String getDescription (){
        return description;
    }
    
    public String getEndTime (){
        return endTime;
    }
    
    public String getStartTime (){
        return startTime;
    }

/******************************************************************************/
/* Method: get<FEATURE>  parsed variables                                     */
/* Purpose: check the calendar's feature specified between < >                */
/*                                                                            */
/* Parameters:                                                                */
/*  void                                                                      */
/* Returns: int:       the calendar's feature                                 */
/******************************************************************************/    
    
    public int getDay (){
        return day;
    }    
    
    public int getMonth (){
        return month;
    }    
    
    public int getYear (){
        return year;
    }    
    
    public int getHourS (){
        return hourS;
    } 

    public int getMinuteS (){
        return minuteS;
    }    

    public int getHourE (){
        return hourE;
    } 

    public int getMinuteE (){
        return minuteE;
    }  
    
/******************************************************************************/
/* Method: print                                                              */
/* Purpose: print the contents stored in the appointment instance             */
/*          collect print just puts the same things together as a string      */
/*          in order to facilitate printing                                   */    
/* Parameters:                                                                */
/*  void                                                                      */
/* Returns:     void                                                          */
/******************************************************************************/    

    public void print (){
        //Print calendar name
        theGUI.printToGui(MessageBoardOptions.RESULTS.getOption(), calendarName + "\n");
        //Print some space to indent
        theGUI.printToGui(MessageBoardOptions.RESULTS.getOption(), "   ");
        //Print year
        theGUI.printToGui(MessageBoardOptions.RESULTS.getOption(), year + "-");
        //Print month
        theGUI.printToGui(MessageBoardOptions.RESULTS.getOption(), month + "-");
        //Print day
        theGUI.printToGui(MessageBoardOptions.RESULTS.getOption(), day + " ");

        //Print times
        //Print start time
        theGUI.printToGui(MessageBoardOptions.RESULTS.getOption(), "(" + startTime);
        theGUI.printToGui(MessageBoardOptions.RESULTS.getOption(), "-");
        //Print end time
        theGUI.printToGui(MessageBoardOptions.RESULTS.getOption(), endTime + ") ");

        //Print description
        theGUI.printToGui(MessageBoardOptions.RESULTS.getOption(), description + "\n");               
    }
    
    
    public String collectPrint (){
        String toBeReturned = ""; //collect data and return
        //Print calendar name
        toBeReturned += calendarName + "\n";
        //Print some space to indent
        toBeReturned += "   ";
        //Print year
        toBeReturned += year + "-";
        //Print month
        toBeReturned += month + "-";
        //Print day
        toBeReturned += day + " ";

        //Print times
        //Print start time
        toBeReturned += "(" + startTime;
        toBeReturned += "-";
        //Print end time
        toBeReturned += endTime + ") ";

        //Print description
        toBeReturned += description + "\n"; 
        
        return toBeReturned;
    }
    
    @Override
    public boolean equals(Object ref) {
    	Appointment second = (Appointment) ref;
    	if(
    		this.getDate() == second.getDate() &&
    		this.getStartTime() == second.getStartTime() &&
    		this.getEndTime() == second.getEndTime() &&
    		this.getDescription() == second.getDescription()
    			) {
    		return true;
    	}
    	return false;
    }
}