package calendars;

import appointments.Appointment;
import appointments.AppointmentNode;
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
    private AppointmentNode head; //First appointment node of the list
    
/******************************************************************************/
/* Method: isEmpty                                                            */
/* Purpose: tells if the list is empty (i.e.) if its number of elements is    */
/*          zero                                                              */
/* Parameters:                                                                */
/*  void                                                                      */
/* Returns: Boolean:    is the list empty?                                    */
/******************************************************************************/
    
    public boolean isEmpty(){
        return (head == null);
    }

/******************************************************************************/
/* Method: size                                                               */
/* Purpose: loops through the list and counts its number of elements          */
/*                                                                            */
/* Parameters:                                                                */
/*  void                                                                      */
/* Returns: int:        number of elements in the list                        */
/******************************************************************************/    
    
    public int size(){
        int count = 0; //counts the number of appointments in the list
        AppointmentNode current = head; //starts counting from the head
            while (current != null){
                count++;
                current = current.getNext();
            }
        return count;
    }

/******************************************************************************/
/* Method: get                                                                */
/* Purpose: retrieves an appointment stored at position specified by          */
/*          parameter index in the list                                       */
/* Parameters:                                                                */
/*  int index:          position that holds the desired item                  */
/* Returns: Appointment:   the desired item                                   */
/******************************************************************************/ 
    
    public Appointment get (int index){
	AppointmentNode current = head; //starts counting from the head		

	while((current != null) && (index != 0)){
	index--;
	current = current.getNext();
	}
	if (current == null)
            throw new IndexOutOfBoundsException();
	else
            return current.getDatum();
    }

/******************************************************************************/
/* Method: add                                                                */
/* Purpose: adds an appointment instance to the calendar at the necessary     */
/*          position so that it remains in sorted order                       */
/* Parameters:                                                                */
/*  Appointment item: item that needs to be included in the list              */
/* Returns: void                                                              */
/******************************************************************************/ 
    
    public void add(AppointmentNode item){
	AppointmentNode previous = null; //stores the previous item to check for
                                         //empty lists
        AppointmentNode current = head; //starts counting from the head
	
        while (current != null){
            if (item.getDatum().getYear() > current.getDatum().getYear()){ 
            //then item is further away in the list
                previous = current;
                current = current.getNext();
                continue;
            }
            //if year is the same, but not the month...
            if (item.getDatum().getYear() == current.getDatum().getYear())
            if (item.getDatum().getMonth() > current.getDatum().getMonth()){
                previous = current;
                current = current.getNext();
                continue;
            }
            //if month is the same, but not the day...
            if (item.getDatum().getMonth() == current.getDatum().getMonth())
            if (item.getDatum().getDay() > current.getDatum().getDay()){
                previous = current;
                current = current.getNext();
                continue;
            }
            //if day is the same, but not the start hour...
            if (item.getDatum().getDay() == current.getDatum().getDay())            
            if (item.getDatum().getHourS() > current.getDatum().getHourS()){
                previous = current;
                current = current.getNext();
                continue;
            }
            //if start hour is the same, but not the start minute...
            if (item.getDatum().getHourS() == current.getDatum().getHourS())  
            if (item.getDatum().getMinuteS() > current.getDatum().getMinuteS()){
                previous = current;
                current = current.getNext();
                continue;
            }            
            break;
        }
            //Now current points to an item that is bigger than the one to be
            //added or it is null
        if (previous == null){   //empty list
            head = item;
        }
        else {
            previous.setNext(item);          
        }
            item.setNext(current);                        
    }
    
/******************************************************************************/
/* Method: remove                                                             */
/* Purpose: removes an appointment from the list                              */
/*                                                                            */
/* Parameters:                                                                */
/*  int index:    index of the item to be removed from list                   */
/* Returns: Appointment: item just removed                                    */
/******************************************************************************/    

    public AppointmentNode remove (int index){
	AppointmentNode previous = null;   //check for empty lists
	AppointmentNode current = head;    //start counting from the head
	while((current != null) && (index != 0)){
		index--;
		previous = current;
		current = current.getNext();
	}
        
	if (index != 0)
            throw new IndexOutOfBoundsException();
        
	if (previous == null){  //remove the first item. Head changes
            head = current.getNext();
	}
        else{
            previous.setNext(current.getNext());  //rearrange the connections  
        }	
	return current;	
}
    
/******************************************************************************/
/* Method: removeAll                                                          */
/* Purpose: removes all appointments from the list                            */
/*                                                                            */
/* Parameters:                                                                */
/*  void                                                                      */
/* Returns: void                                                              */
/******************************************************************************/ 

public void removeAll(){
	head = null;
}

/******************************************************************************/
/* Method: printCalendar / printCalendarToGui                                 */
/* Purpose: prints all the calendar's contents                                */
/*                   *toGui writes to the results board                       */
/* Parameters:                                                                */
/*  void                                                                      */
/* Returns: int    number of printed items                                    */
/******************************************************************************/

public int printCalendar(){
    AppointmentNode auxApp = head; //created as index to loop through the list
    int count = 0;      //counter for the number of items
    while (auxApp != null){
        auxApp.getDatum().print();
        auxApp = auxApp.getNext();
        count++;
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

/******************************************************************************/
/* Method: printBasedOnDescription                                            */
/* Purpose: prints all the calendar's contents that contain the provided      */
/*          description (supplied by the parameter)                           */
/* Parameters:                                                                */
/*  String description:    description we want to look for                    */
/* Returns: int    number of printed items                                    */
/******************************************************************************/

public int printBasedOnDescription(String description){
    AppointmentNode auxApp = head; //created as index to loop through the list
    int count = 0;      //counter for the number of items
    while (auxApp != null){
        if(auxApp.getDatum().getDescription().toLowerCase().contains(
                description.toLowerCase())){
            auxApp.getDatum().print();
            count++;
        }
        auxApp = auxApp.getNext();
    }
    return count;
}

/******************************************************************************/
/* Method: lookForTime                                                        */
/* Purpose: Aid in the search of a period of time that contains one given     */
/* value supplied by the user. Separate block for keeping the methods short   */
/* Parameters:                                                                */
/* String: Date  String representing the date yyyymmdd                        */
/* int: time  the time hhmm                                                   */
/*                                                                            */    
/* Returns: int   Number of matches                                           */
/******************************************************************************/
    
    public int lookForTime (String date, int time){
        int count = 0;    //number of matches
        AppointmentNode auxApp = head; //created as index to loop 
                                       //through the list

        while (auxApp != null){
            if(auxApp.getDatum().getDate().equals(date)){
            //arrange the time in a way that it is an int in the format hhmm
                if(auxApp.getDatum().getHourS()*100 + auxApp.getDatum().
                getMinuteS() <= time){
                    if(auxApp.getDatum().getHourE()*100 + auxApp.getDatum().
                    getMinuteE() >= time){
                        auxApp.getDatum().print();
                        count++;
                    }
                }
            }
            auxApp = auxApp.getNext();
        }
        return count;
    }
    
    String collectLookForTime (String date, int time){
        String returning = ""; //collect and return
        int count = 0;    //number of matches
        AppointmentNode auxApp = head; //created as index to loop 
                                       //through the list

        while (auxApp != null){
            if(auxApp.getDatum().getDate().equals(date)){
            //arrange the time in a way that it is an int in the format hhmm
                if(auxApp.getDatum().getHourS()*100 + auxApp.getDatum().
                getMinuteS() <= time){
                    if(auxApp.getDatum().getHourE()*100 + auxApp.getDatum().
                    getMinuteE() >= time){
                        returning += auxApp.getDatum().collectPrint();
                        count++;
                    }
                }
            }
            auxApp = auxApp.getNext();
        }
        return returning;
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
    
    public AppointmentNode getHead() {
    	return head;
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