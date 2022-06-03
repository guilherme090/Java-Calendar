package calendars;

import java.util.LinkedList;

import appointments.Appointment;
import calendars.Calendar;
import db.Gui;

/******************************************************************************/
/* Guilherme Virgilio P. O. Simoes                                            */
/* Login ID: pici7127                                                         */
/* CS-102, Winter 2016                                                        */
/* Programming Assignment 5                                                   */
/* Appointment calendar: Calendar instance with a reference to a linked list  */
/* of related appointments                                                    */
/******************************************************************************/

/**
 * 
 * @author Guilherme Virgilio P. O. Simoes 
 * @version 1.1.0
 * 
 * Transformation of CS-102 project into a Maven project
 */
public class Calendar implements Comparable <Calendar>{
    private String fileName = ""; //contains the path to the calendar's file
    private String calendarName = ""; //First line in the file
    private Gui theGUI;
    private LinkedList<Appointment> listOfAppointments = new LinkedList<Appointment>();
    
    /**
     * Checks if no appointments were added to the calendar.
     * 
     * @return true, if the list is empty.
     */
    public boolean isEmpty(){
    	return listOfAppointments.size() == 0;
    }

    /**
     * Gets the number of appointments of the current calendar,
     * not the whole database's.
     * 
     * @return number of appointments.
     */
    public int size(){
    	return listOfAppointments.size();
    }
    
    /**
     * Gets a single appointment from the list.
     * 
     * @param index
     * @return the appointment (if found).
     * @throws IndexOutOfBoundException
     */
    public Appointment get (int index){
    	return listOfAppointments.get(index);
    }
 
    /**
     * Adds an appointment to the list without ordering it.
     * 
     * @param item Appointment to be added to the list
     */
    public void add(Appointment item){
	    listOfAppointments.add(item);                  
    }
    
    /**
     * Removes a single appointment from the list.
     * 
     * @param index
     * @return the appointment (if removed).
     * @throws IndexOutOfBoundException
     */
    public Appointment remove (int index){
	return listOfAppointments.remove(index);	
    }
     
    /**
     * Clears the list
     */
	public void removeAll(){
		listOfAppointments.clear();
	}

	/**
	 * Prints all appointments in the calendar instance.
	 * 
	 * @return number of appointments.
	 */
	public int printCalendar(){
		int count = 0;
	    for(Appointment appointment : listOfAppointments) {
	    	appointment.print();
	    	count ++;
	    }
	    return count;
	}

/******************************************************************************/
/* Method: assignGUI                                                          */
/* Purpose: copies a reference to a database and returns it so that it can be */
/*          re-assigned to the GUI and seen from this class.                  */
/*                                                                            */          
/* Parameters:                                                                */
/*  Database copying: reference to the database being copied to the GUI       */     
/* Returns: void                                                              */
/******************************************************************************/    
    public void assignGUI (Gui copying){
        theGUI = copying;
    }

    /**
     * Prints only the items that contain the provided description.
     * @param description
     * @return number of matching items.
     */
	public int printBasedOnDescription(String description){
	    int count = 0;      //counter for the number of items
	    for(Appointment appointment : listOfAppointments) {
	    	if(appointment.getDescription().toLowerCase()
	    			.contains(description.toLowerCase())) {
	    		appointment.print();
	    		count++;
	    	}
	    }
	    return count;
	}

    /**
     * Looks for appointments in a given time window.
     * 
     * @param date
     * @param time
     * @return number of matches.
     */
    public int lookForTime (String date, int time){
        int count = 0;    //number of matches
        for(Appointment appointment : listOfAppointments) {
        	if(	appointment.getDate().equals(date) && 
        		appointment.getHourS() * 100 + appointment.getMinuteS() <= time &&
        		appointment.getHourE() * 100 + appointment.getMinuteE() >= time) {
        			appointment.print();
        			count++;
        	}
        }  
        return count;
    }

/******************************************************************************/
/* Method: get<FEATURE>                                                       */
/* Purpose: check the calendar's feature specified between < >                */
/*                                                                            */
/* Parameters:                                                                */
/*  void                                                                      */
/* Returns: string:       the calendar's feature                              */
/******************************************************************************/    
    
    public String getfileName (){
        return fileName;
    }

    public String getCalendarName (){
        return calendarName;
    }
    
    public LinkedList<Appointment> getList() {
    	return listOfAppointments;
    }

/******************************************************************************/
/* Method: set<FEATURE>                                                       */
/* Purpose: change the calendar's feature specified between < >               */
/*                                                                            */
/* Parameters:                                                                */
/*  String  new<FEATURE>:   value to be added                                 */
/* Returns: void                                                              */
/******************************************************************************/    
    
    public void setfileName (String newName){
        fileName = newName;
    }

    public void setCalendarName (String newName){
        calendarName = newName;
    }    

/******************************************************************************/
/* Method: compareTo                                                          */
/* Purpose: performs the comparison by looking at the calendar names and      */
/* checking the alphabetic order.                                             */
/* Parameters:                                                                */
/*  Calendar other:         Calendar to be compared with the current one.     */
/* Returns: integer:        < 0 current is less than other                    */
/*                         == 0 current is equal to other                     */   
/*                          > 0 current is greater than other                 */ 
/******************************************************************************/    
@Override
    public int compareTo(Calendar other) {
        return this.getCalendarName().compareTo(other.getCalendarName());
    }
}