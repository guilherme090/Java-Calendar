package db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JOptionPane;

import appointments.Appointment;
import appointments.AppointmentNode;

import calendars.Calendar;
import calendars.CalendarTree;
import calendars.CalendarTreeNode;

/******************************************************************************/
/* Guilherme Virgilio P. O. Simoes                                            */
/* Login ID: pici7127                                                         */
/* CS-102, Winter 2016                                                        */
/* Programming Assignment 5                                                   */
/* Appointment calendar: collection of methods that manage the appointment    */
/* objects. Collection of Calendar objects that store appointment instances   */
/******************************************************************************/

/**
 * 
 * @author Guilherme Virgilio P. O. Simoes 
 * @version 1.1.0
 * 
 * Transformation of CS-102 project into a Maven project
 */
public class Database {

Gui thisGUI; //interface assigned to this database

    // Make list of calendar objects that compose the database
    public CalendarTree calendarSet = new CalendarTree();

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
        thisGUI = copying;
    }
    
/******************************************************************************/
/* Method: isEmpty                                                            */
/* Purpose: tells if the list is empty (i.e.) if its number of elements is    */
/*          zero                                                              */
/* Parameters:                                                                */
/*  void                                                                      */
/* Returns: Boolean:    is the list empty?                                    */
/******************************************************************************/
    
    public boolean isEmpty(){
        return calendarSet.isEmpty();
    }

/******************************************************************************/
/* Method: size                                                               */
/* Purpose: loops through the list and counts the number of elements          */
/*                                                                            */
/* Parameters:                                                                */
/*  void                                                                      */
/* Returns: int:        number of elements in the list                        */
/******************************************************************************/    
    
    public int size(){
        return calendarSet.size();
    }

/******************************************************************************/
/* Method: add                                                                */
/* Purpose: adds a calendar instance to the list at the necessary             */
/*          position so that it remains in sorted order                       */
/* Parameters:                                                                */
/*  Calendar item: item that needs to be included in the list                 */
/* Returns: void                                                              */
/******************************************************************************/ 
    
    void add(Calendar item){     
        calendarSet.add(item);               
    }

/******************************************************************************/
/* Method: addToCalendar                                                      */
/* Purpose: adds an appointment instance to an existing calendar in the list  */
/*                                                                            */
/* Parameters:                                                                */
/*  CalendarNode calendar:         the calendar object                        */
/*  String date:                   date of the appointment                    */    
/*  String startTIme:              start time of the appointment              */
/*  String endTime:                end time of the appointment                */
/*  String description:            given description to the appointment       */
/*  Gui    userGUI:                interface to be printed on                 */    
/* Returns: boolean:               Created and attached appointment?          */
/******************************************************************************/
    
    boolean addToCalendar (Calendar calendar, String date, String startTime, 
                           String endTime, String description){
        AppointmentNode temp = new AppointmentNode (new Appointment()); 
        //create temporary appointment 
        boolean legit = false; //are appointment parameters legal?
            temp.getDatum().changeCalendarName(calendar.getCalendarName()); 
            //set name
            legit = temp.getDatum().setDate(date);
            if (legit == true)     //means it is a valid date
                legit = temp.getDatum().setTime(startTime, endTime);
            temp.getDatum().setDescription(description);
            temp.getDatum().setGUI(thisGUI);
            if (legit == true) {
                calendar.add(temp);
                return true;
            }
            else{
                thisGUI.printToGui(MessageBoardOptions.MESSAGE.getOption(), "**** " + calendar.getCalendarName()
                    + "'s \n" +  "**** '" + temp.getDatum().getDescription() 
                    + "' not included!\n");
                thisGUI.bufferToGui(MessageBoardOptions.MESSAGE.getOption());
                return false;
            }
    }
    
/******************************************************************************/
/* Method: deleteFromCalendar                                                 */
/* Purpose: deletes an appointment that exists in the provided list           */
/*                                                                            */
/* Parameters:                                                                */
/*  CalendarNode calendar:         the calendar object                        */
/*  String date:                   date of the appointment                    */    
/*  String startTIme:              start time of the appointment              */
/*  String endTime:                end time of the appointment                */
/*  String description:            given description to the appointment       */   
/* Returns: boolean:               Created and attached appointment?          */
/******************************************************************************/
    
    boolean deleteFromCalendar (Calendar calendar, String date, String startTime 
                           ,String endTime, String descr){
        //Loop throught the list to find the desired appointment
        for(int index = 0 ; index < calendar.size(); index++){
            String answer = ""; //stores user answer
            if(calendar.get(index).getDate().equals(date))
               if(calendar.get(index).getStartTime().equals(startTime))
                   if(calendar.get(index).getEndTime().equals(endTime))
                       if(calendar.get(index).getDescription().contains(descr)){
                          calendar.get(index).print();
                            //keep prompting user until they answer correctly
                            while(answer.compareTo("Y") != 0 && 
                                answer.compareTo("N") != 0 && 
                                answer.compareTo("y") != 0 && 
                                answer.compareTo("n") != 0){
                            answer = JOptionPane.showInputDialog(
                                    "Are you sure you want to delete"
                                  + " the appointment above? Y = yes, N = no");                            
                          }
                          if(answer.toLowerCase().compareTo("n") == 0)
                              return false;
                          calendar.remove(index);
                          return true;
                       }         
        }
        thisGUI.printToGui(MessageBoardOptions.MESSAGE.getOption(), "None of the appointments match the "
                + "description.");
        thisGUI.bufferToGui(MessageBoardOptions.MESSAGE.getOption());
        return false;
    }   
   
    
/******************************************************************************/
/* Method: remove                                                             */
/* Purpose: removes a calendar from the list                                  */
/*                                                                            */
/* Parameters:                                                                */
/*  Calendar cal:    calendar to be removed from the list                     */
/* Returns: void                                                              */
/******************************************************************************/    

    public void remove (Calendar cal){
	calendarSet.remove(cal);
}
    
/******************************************************************************/
/* Method: removeAll                                                          */
/* Purpose: removes all calendars from the tree                               */
/*                                                                            */
/* Parameters:                                                                */
/*  void                                                                      */
/* Returns: void                                                              */
/******************************************************************************/ 

public void removeAll(){
	calendarSet.clear();
}
    
/******************************************************************************/
/* Method: search                                                             */
/* Purpose: search in the list for a particular appointment or type of        */
/* appointment and print all matches                                          */
/* Parameters:                                                                */
/* int: option    Decides the type of search (name, time, description...)     */
/* String: value  Item that will be looked for in the search method           */
/* Returns: int   Number of matches                                           */
/******************************************************************************/
//List of what the user can choose to look for in the calendar    
public static final int EVERYTHING = 1, CALENDAR = 2, TIME = 3, DESCRIPTION = 4;
/*
EVERYTHING :    user wants to print all the calendars
CALENDAR:       user wants to print a specific calendar
TIME:           user wants to look for a particular time in the appointments
DESCRIPTION:    user wants to look for a description among the appointments
*/

    public void search (int theOption, String lookedFor){
        int count = 0; 
        switch (theOption){
            case EVERYTHING:{
            /* The user wants to print all calendars. Loop through the database 
            and print everything.  */
            calendarSet.print();
            //calendarSet.collectPrint(thisGUI);
            }break;
            
            case CALENDAR:{
            /* The user wants to print only one calendar. Look for it */   
            try{
                count += calendarSet.search(lookedFor).getDatum().
                        printCalendar();
                        thisGUI.bufferToGui(MessageBoardOptions.RESULTS.getOption());
            }   catch(NoSuchElementException exc){
                    thisGUI.printToGui(MessageBoardOptions.MESSAGE.getOption(), "The requested calendar does "
                            + "not exist");
                    thisGUI.bufferToGui(MessageBoardOptions.MESSAGE.getOption());
                    break;
            }            
                if (count == 0){
                    thisGUI.printToGui(MessageBoardOptions.MESSAGE.getOption(), "The requested calendar is "
                            + "empty.");
                    thisGUI.bufferToGui(MessageBoardOptions.MESSAGE.getOption());
                }
            }break;
            
            case TIME:{
            /* The user wants to look for a specific time. Loop through the 
            agenda and look for lesser start times with greater end times */
            try{
            //Create substrings representing date and time
            String subDate = lookedFor.substring(0, 8);
            //part of the string that represents the date
            String subTime = lookedFor.substring(8, 12);
            //part of the string that represents the time
            int subTimeInt = Integer.parseInt(subTime);
            //parseInt the string that represents the time
            //it will be used by the lookForTime method
            if (traverseTime(subDate, subTimeInt) == false){
                thisGUI.printToGui(MessageBoardOptions.MESSAGE.getOption(), "The requested time is not included "
                        + "in any of the appointments.");
                thisGUI.bufferToGui(MessageBoardOptions.MESSAGE.getOption());
            }
            //traverseTime(subDate, subTimeInt);
            }catch(StringIndexOutOfBoundsException exc){
                thisGUI.printToGui(MessageBoardOptions.MESSAGE.getOption(), "The requested time is not included "
                        + "in any of the appointments.");
                thisGUI.bufferToGui(MessageBoardOptions.MESSAGE.getOption());
            }

            }break;
            
            case DESCRIPTION:{
            /*  Look for descriptions among the appointments. Similar to the 
                case TIME. Here, an empty search means the entire list of 
                appointments, such as an empty search box. It could also show no
                results. It is NOT case sensitive. */
                                        
            if (traverseDescription(lookedFor) == false){
                thisGUI.printToGui(MessageBoardOptions.MESSAGE.getOption(), "The requested descrption was not "
                        + "found in any of the appointments.");
                thisGUI.bufferToGui(MessageBoardOptions.MESSAGE.getOption());
            }
            }break;            
        }
    }   

/******************************************************************************/
/* Method: getCalendarSet                                                     */
/* Purpose: gets feature                                                      */
/*                                                                            */
/* Parameters:                                                                */
/* void                                                                       */
/* Returns: CalendarTree: the calendar tree                                   */
/******************************************************************************/    
    public CalendarTree getCalendarSet(){
        return calendarSet;
    }
    
/******************************************************************************/
/* Method: traverseTime                                                       */
/* Purpose: traverses the tree and apply the method lookForTime in each of the*/
/*          nodes.                                                            */
/* Parameters:                                                                */
/* String subDate:          substring for date                                */
/* int    subTimeInt:       parsed substring for time                         */
/*  CalendarTreeNode current:  the tree's root (helper method only)           */     
/* Returns: boolean:    found value?                                          */
/******************************************************************************/        
    boolean traverseTime(String subDate, int subTimeInt){
        return traverseTime(calendarSet.getRoot(), subDate, subTimeInt);
    } 
    boolean traverseTime(CalendarTreeNode current, String subDate, 
            int subTimeInt){
        if (current == null) return false;
        /* If left, middle and right functions return false, there is no element 
        to be found */
	if(traverseTime(current.getLeft(), subDate, subTimeInt) == 
                false &&            
	current.getDatum().lookForTime(subDate, subTimeInt) == 0 &&   
	traverseTime(current.getRight(), subDate, subTimeInt) == false)
            return false;
        else{
            thisGUI.bufferToGui(MessageBoardOptions.RESULTS.getOption());
            return true;
        }
            
    }
       
/******************************************************************************/
/* Method: traverseDescription                                                */
/* Purpose: traverses the tree and apply the method lookForTime in each of the*/
/*          nodes.                                                            */
/* Parameters:                                                                */
/* String lookedFor:          substring for date                              */
/*  CalendarTreeNode current:  the tree's root (helper method only)           */    
/* Returns: boolean:    found value?                                          */
/******************************************************************************/        
    boolean traverseDescription(String lookedFor){
        return traverseDescription (calendarSet.getRoot(), lookedFor);
    }
    boolean traverseDescription(CalendarTreeNode current, String lookedFor){
        if (current == null) return false;
        /* If left, middle and right functions return false, there is no element
        to be found */        
	if(traverseDescription(current.getLeft(), lookedFor) == 
                false &&            
	current.getDatum().printBasedOnDescription(lookedFor) == 0 &&   
	traverseDescription(current.getRight(), lookedFor) == false)
            return false;
        else{
            thisGUI.bufferToGui(MessageBoardOptions.RESULTS.getOption());
            return true;
        }
    }
    
/******************************************************************************/
/* Method: wholeDatabase                                                      */
/* Purpose: Prints everything that is in the database. All the appointments   */
/*          of all the calendars.                                             */                                                                                      
/* Parameters:                                                                */   
/*  int      userChoseParsed   the user's option from the menu                */
/*  String   userLooksFor      a string containing the information looked for */
/*                             by the user. In this case, it is empty since   */
/*                             the user wants everything.                     */    
/* Returns: void                                                              */
/******************************************************************************/  
public void wholeDatabase(int userChoseParsed, String userLooksFor) {
    search(userChoseParsed, userLooksFor);
}

/******************************************************************************/
/* Method: printACalendar                                                     */
/* Purpose: Looks for a specific calendar in the database and prints it       */
/*                                                                            */                                                                                      
/* Parameters:                                                                */   
/*  int      userChoseParsed   the user's option from the menu                */
/*  String   keyboard          the requested calendar's name                  */

/* Returns: void                                                              */
/******************************************************************************/
public void printACalendar(int userChoseParsed, String keyboard){ 
    search(userChoseParsed, keyboard);
}

/******************************************************************************/
/* Method: lookForATime                                                       */
/* Purpose: Looks for a specific time in the database and prints all matches  */
/*                                                                            */                                                                                      
/* Parameters:                                                                */    
/*  int      userChoseParsed   the user's option from the menu                */
/*  String   chosenDate        the required date                              */
/*  String   chosenTime        the required time                              */
/* Returns: void                                                              */
/******************************************************************************/

void lookForATime(int userChoseParsed, 
            String chosenDate, String chosenTime){
    /*create an empty appointment just to check validity of date and time */
    Appointment checkVal = new Appointment();
    //avoid getting the user turn it into an invalid string.
    
    try{   
        while(! checkVal.isValid(Integer.parseInt(chosenDate))){
        //it can still be longer or shorter than it should YYYYMMDD
        if (chosenDate.length() != 8) 
            chosenDate = "0";
        Integer.parseInt(chosenDate); //try to force exc. catch
        }
        } catch (NumberFormatException | StringIndexOutOfBoundsException exc){
            chosenDate = "0";
        
    }
                    
        /* in order to check time validity, treat the user time as the start 
        time and choose 23:59 as the end time */
        //avoid getting the user turn it into an invalid string.
    
        try{  
            while(! checkVal.isValid(Integer.parseInt(chosenTime), 2359)){
            //it can still be longer or shorter than it should HHMM
            if (chosenTime.length() != 4) 
                chosenTime = "2460";
            Integer.parseInt(chosenTime); //try to force exc catch
        }
        }catch (NumberFormatException | StringIndexOutOfBoundsException exc){
            chosenTime = "2460";
    }                    
    search(userChoseParsed, chosenDate + chosenTime);
}

/******************************************************************************/
/* Method: lookForADescription                                                */
/* Purpose: Looks for a specific description in the database and prints all   */
/*          matches                                                           */                                                                                      
/* Parameters:                                                                */    
/*  int      userChoseParsed   the user's option from the menu                */
/*  String   keyboard          the description looked for                     */
/* Returns: void                                                              */
/******************************************************************************/

void lookForADescription(int userChoseParsed, String keyboard){
    search(userChoseParsed, keyboard);
}

/******************************************************************************/
/* Method: addNewAppointment                                                  */
/* Purpose: Creates a whole new appointment and adds it to an existing        */
/*          calendar in the database                                          */                                                                                      
/* Parameters:                                                                */   
/*  String chosenCalendar      the calendar looked for                        */
/*  String chosenDate          the date looked for                            */
/*  String chosenSTime         the start time looked for                      */
/*  String chosenETime         the end time looked for                        */
/*  String chosenDescription   the description looked for                     */
/* Returns: void                                                              */
/******************************************************************************/
void addNewAppointment(String chosenCalendar,
        String chosenDate, String chosenSTime, String chosenETime,
        String chosenDescription){                                                                          
    //look for the specified calendar.
    Calendar chosenCal = new Calendar();
    chosenCal.setCalendarName(chosenCalendar);
  
    if (getCalendarSet().search(chosenCal) == false){
        thisGUI.printToGui(MessageBoardOptions.MESSAGE.getOption(), ">>> Calendar '" + chosenCalendar
                          + "' not found in the list.");
        thisGUI.bufferToGui(MessageBoardOptions.MESSAGE.getOption());
    }
    else{
        chosenCal = getCalendarSet().search(chosenCalendar).getDatum();
        if(addToCalendar(chosenCal, chosenDate, chosenSTime, chosenETime,
           chosenDescription)){
        thisGUI.printToGui(MessageBoardOptions.MESSAGE.getOption(),  "**** Appointment included.");
        thisGUI.bufferToGui(MessageBoardOptions.MESSAGE.getOption());
        }                        
    }                   
}

/******************************************************************************/
/* Method: delAppointment                                                     */
/* Purpose: Deletes an appointment instance that currently exists in the      */
/*          database                                                          */                                                                                      
/* Parameters:                                                                */    
/*  String chosenCalendar      the calendar looked for                        */
/*  String chosenDate          the date looked for                            */
/*  String chosenSTime         the start time looked for                      */
/*  String chosenETime         the end time looked for                        */
/*  String chosenDescription   the description looked for                     */
/* Returns: void                                                              */
/******************************************************************************/

void delAppointment(String chosenCalendar,
        String chosenDate, String chosenSTime, String chosenETime,
        String chosenDescription){
    Calendar chosenCal = null;  //node referring to the cal                    
    //look for the specified calendar.
    try{
        chosenCal = getCalendarSet().search(chosenCalendar).getDatum();
    }catch(NoSuchElementException exc){
        thisGUI.printToGui(MessageBoardOptions.MESSAGE.getOption(), ">>> Calendar '" + chosenCalendar
                          + "' not found in the list.");
        thisGUI.bufferToGui(MessageBoardOptions.MESSAGE.getOption());
        return;
    }
    chosenCal.printCalendar();
    try{
        if(deleteFromCalendar(chosenCal, chosenDate, chosenSTime,
           chosenETime, chosenDescription))
            JOptionPane.showMessageDialog(null, "**** Appointment excluded.");
        else    System.out.println( "**** Appointment not excluded."); 
    }catch(IndexOutOfBoundsException exc){
        JOptionPane.showMessageDialog(null, "Illegal parameters. No "
                + "appointments were excluded");
    }                                               
}

/******************************************************************************/
/* Method: addNewCalendar                                                     */
/* Purpose: Creates a whole new calendar to the program based on an existing  */
/*          text file.                                                        */                                                                                      
/* Parameters:                                                                */
/*  String userLooksFor        the name of a calendar file to be added        */
/* Returns: void                                                              */
/******************************************************************************/

void addNewCalendar(String userLooksFor){
    if(loadFile(userLooksFor) == true){
       JOptionPane.showMessageDialog(null, "**** File added.");
    }   
}

/******************************************************************************/
/* Method: loadFile                                                           */
/* Purpose: stores a file's contents in the agenda by creating all the stored */
/*          appointments                                                      */
/* Parameters:                                                                */
/*  String fileName:      the name of the file to be added                    */
/* Returns: boolean:      add successfull?                                    */
/******************************************************************************/    
    
boolean loadFile(String fileName){
    Scanner currentFile = null;     //will contain one file at a time
    try { 
        currentFile = new Scanner(new File(fileName));
    }
    catch (ArrayIndexOutOfBoundsException exc) {
        JOptionPane.showMessageDialog(null, "No arguments given to program.");
        return (false);
    } 
    catch (FileNotFoundException exc) { 
        JOptionPane.showMessageDialog(null, "File ''" + fileName + "'' "
                + "could not be opened at");
        JOptionPane.showMessageDialog(null, System.getProperty("user.dir"));
        return (false); 
    } 

    if (currentFile.hasNext()){  //if there is data            
        String calendarName = currentFile.nextLine(); //get cal name
        /*Check if the file already exists in the database*/           
        Calendar chosenCal = new Calendar();
        chosenCal.setCalendarName(calendarName);

        if (getCalendarSet().search(chosenCal) == true){
            JOptionPane.showMessageDialog(null, "**** File has already been "
                    + "loaded into the program.");
            return(false);
        }             

        //Create a calendar to store what is inside
        Calendar newCal = new Calendar(); //create instance of calendar
        newCal.setCalendarName(calendarName);
        newCal.setfileName(fileName);
        while (currentFile.hasNext()){  //keep filling up the calendar
        /*Create a variable to keep track of illegal data that might appear. The 
        defective chunks of information will not be added to the agenda. */
            String inputStartTime = null, inputEndTime = null;  //temp
            //input strings
            String inputDay = null; //temp input string 
            String description = null; //temp input string
            inputDay = currentFile.nextLine(); //get the next line
            Scanner pieces = new Scanner (inputDay); //break into pieces
            pieces.useDelimiter("/");
            while (pieces.hasNext()){
                inputDay = pieces.next();
                if(pieces.hasNext())inputStartTime = pieces.next(); 
                //get start time
                if(pieces.hasNext())inputEndTime = pieces.next(); 
                //get end time
                if(pieces.hasNext())description = pieces.next();
                addToCalendar(newCal, inputDay, inputStartTime,
                        inputEndTime, description);
            }
        }
        add(newCal); //add the calendar that was just created
    }    
currentFile.close();
return true;
}

/******************************************************************************/
/* Method: delCalendar                                                        */
/* Purpose: Deletes a calendar from the lsit                                  */
/*                                                                            */                                                                                      
/* Parameters:                                                                */
/*  String chosenCalendar      the calendar to be deleted                     */        
/* Returns: void                                                              */
/******************************************************************************/
void delCalendar(String chosenCalendar){
    //Create string to store user input
    Calendar chosenCal = null;  //node referring to the cal                     
    //look for the specified calendar.
    try{
        chosenCal = getCalendarSet().search(chosenCalendar).getDatum();
    }catch(NoSuchElementException exc){
        JOptionPane.showMessageDialog(null, ">>> Calendar '" + chosenCalendar
                          + "' not found in the list.");
        return;
    }
    thisGUI.printToGui(MessageBoardOptions.MESSAGE.getOption(), ">>> Chosen calendar: " + chosenCal.
                getCalendarName()+ "\n\"");
    thisGUI.bufferToGui(MessageBoardOptions.MESSAGE.getOption());
    //create string to store user choice
    String answer = "";
    while(answer.compareTo("Y") != 0 && answer.compareTo("N") != 0
          && answer.compareTo("y") != 0  && answer.compareTo("n") != 0){
            answer = JOptionPane.showInputDialog("Are you sure you want to "
                    + "delete" + " the calendar above? Y = yes, N = no");
    }
    if(answer.toLowerCase().compareTo("n") == 0){
        JOptionPane.showMessageDialog(null,">>> Calendar not deleted");
    }
    else{
        remove(chosenCal);
        JOptionPane.showMessageDialog(null,">>> Calendar deleted");
    }          
}

/******************************************************************************/
/* Method: writeToDisk                                                        */
/* Purpose: saves all content from the calendars and stores them in the       */
/*          original files in the disk                                        */
/* Parameters:                                                                */
/*  CalendarTreeNode current:  the tree's root (helper method only)           */
/* Returns: void                                                              */
/******************************************************************************/
    public void writeToDisk(){
        //Check if user really wants to save to the database
        String answer = "";
        while(answer.toLowerCase().compareTo("y") != 0 && 
        answer.toLowerCase().compareTo("n") != 0 ){
            answer = JOptionPane.showInputDialog("Are you sure you want to save"
                    + " all contents to the database? Y = yes, N = no");
        }
        if(answer.toLowerCase().compareTo("y") == 0)
            writeToDisk(calendarSet.getRoot());
        else
            JOptionPane.showMessageDialog(null,"Database not modified.");
    }
    // Traverse the calendar tree, read from every calendar. Open the files
    private void writeToDisk(CalendarTreeNode current){
        if (current == null) return;        
        writeToDisk(current.getLeft());
        writeToDisk(current.getRight());
    // Sends the calendar and the filename to perform the writing
        if(writeToDisk(current.getDatum()))
            JOptionPane.showMessageDialog(null,"Calendar file ''" + 
                    current.getDatum().
            getfileName() + "'' was successfully written.");
    }        

/******************************************************************************/
/* Method: writeToDisk (with calendar only)                                   */
/* Purpose: saves all content from the calendars and stores them in the       */
/*          original files in the disk                                        */
/* Parameters:                                                                */
/*  Calendar currentCal   the calendar that will provide content to the file  */    
/* Returns: boolean:      write successful?                                   */
/******************************************************************************/
    private boolean writeToDisk(Calendar currentCal){
        try {
            // Open file that will receive the calendar contents
            File file = new File(currentCal.getfileName()); // current file
            // If file doesn't exist, create it.
            if (!file.exists()) {
                file.createNewFile();
            }
            // File writer element
            FileWriter fWritter = new FileWriter(file.getAbsoluteFile());
            // Buffered writer element
            BufferedWriter bWritter = new BufferedWriter(fWritter);
            String currentLine = ""; // will contain data from the calendars
            // Print calendar title
            bWritter.write(currentCal.getCalendarName());
            bWritter.newLine(); // move to the next line
            // Form lines with the calendar contents. Do this for all the 
            // appointments
            AppointmentNode auxApp = currentCal.getHead(); //index to the list
            while (auxApp != null){     // look at the whole list
                currentLine = auxApp.getDatum().getDate() + "/" +
                auxApp.getDatum().getStartTime() + "/" +
                auxApp.getDatum().getEndTime() + "/" +
                auxApp.getDatum().getDescription();
                
                bWritter.write(currentLine); //write formed line
                bWritter.newLine(); //move to the next line
                
                auxApp = auxApp.getNext();  //move on to the next element
            }   
            bWritter.close();   //close the buffer to flush it to disk.
        } catch (IOException exc) {
            //System.out.println("There was a mistake with input data.");
            return false;
        }
        return true;
     }

/******************************************************************************/
/* Method: readFromDisk                                                       */
/* Purpose: restores the database by reloading all the files from all         */
/*          calendars and saving contents to the data structures              */
/* Parameters:                                                                */
/*  CalendarTreeNode current:  the tree's root (helper method only)           */   
/* Returns: void                                                              */
/******************************************************************************/   
    public void readFromDisk(){
        //Check if user really wants to reload the database
        String answer = "";
        while(answer.toLowerCase().compareTo("y") != 0 && 
        answer.toLowerCase().compareTo("n") != 0 ){
            answer = JOptionPane.showInputDialog("Are you sure you want to "
                    + "reload the database? Y = yes, N = no");
        }
        if(answer.toLowerCase().compareTo("y") == 0)
            readFromDisk(calendarSet.getRoot());
        else
            JOptionPane.showMessageDialog(null,"Database not reloaded.");
    }
    // Traverse the calendar tree, delete every calendar. Add them again.
    private void readFromDisk(CalendarTreeNode current){
        if (current == null) return;
        // Since the traversal alters the tree, use LRN. Nodes altered lastly.        
        readFromDisk(current.getLeft());
        readFromDisk(current.getRight());
        // Get file name in order to be able to find it again and reload it.
        String toBeReloaded = current.getDatum().getfileName(); //file name
        remove(current.getDatum()); //remove calendar
        if(loadFile(toBeReloaded)){ //load it again. Check for success
            JOptionPane.showMessageDialog(null,"Calendar ''" +
                    current.getDatum().
            getCalendarName() + "'' reloaded.");
        }    
    }
}