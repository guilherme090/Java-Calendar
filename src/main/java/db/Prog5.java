
package db;

/******************************************************************************/
/* Guilherme Virgilio P. O. Simoes                                            */
/* Login ID: pici7127                                                         */
/* CS-102, Winter 2016                                                        */
/* Programming Assignment 5                                                   */
/* Appointment calendar: user interaction module (use of linked lists)        */
/******************************************************************************/

import javax.swing.JFrame;

/**
 * 
 * @author Guilherme Virgilio P. O. Simoes 
 * @version 1.1.0
 * 
 * Transformation of CS-102 project into a Maven project
 */
public class Prog5 {
//List of available commands. The following are the user's options in the menu
public static final int EVERYTHING = 1, CALENDAR = 2, TIME = 3, DESCRIPTION = 4,
ADD_APPOINTMENT = 5, ADD_CAL_FILE = 6, DEL_APPOINTMENT = 7, DEL_CAL = 8,
SAVE_DATABASE = 9, LOAD_DATABASE = 10;

/*
EVERYTHING :    user wants to print the whole calendar
CALENDAR:       user wants to print a specific calendar
TIME:           user wants to look for a particular time in the appointments
DESCRIPTION:    user wants to look for a description among the appointments
ADD_APPOINTMENT:user wants to add an appointment to an existent calendar
ADD_CAL_FILE:   user wants to add a calendar file to the program
DEL_APPOINTMENT:user wants to delete an appointment from the database
DEL_CAL:        user wants to delete a whole calendar from the database
SAVE_DATABASE:  user wants to write all data to the files in the disk
LOAD_DATABASE:  user wants to restore all the contents saved in the disk
*/

/******************************************************************************/
/* Method: main                                                               */
/* Purpose: executes the main actions of the program. Load files in the       */
/*          database, creates corresponding calendars, presents MENU to the   */
/*          user                                                              */          
/* Parameters:                                                                */
/*  String[] args:    string of arguments given by command line               */
/* Returns: void                                                              */
/******************************************************************************/

    public static void main(String[] args) {
        
        Gui calendarGUI = new Gui();
        calendarGUI.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        int index = 0; //indexes the loop for opening all the files                                       
        
//        if (args.length == 0){
//            System.out.println("No files given as arguments to the program.");
//            System.exit(2); 
//        }
        
        // Create a database for the program.
        Database agenda = new Database(); //will contain all appointments
        agenda.assignGUI(calendarGUI);
        calendarGUI.assignDatabase(agenda); //copies the agenda to the GUI
        agenda.calendarSet.assignGUI(calendarGUI); //copy to the GUI
        
        for ( ; index < args.length ; index++)
        {
            if (agenda.loadFile(args[index]) == false) //tries to 
            //load the file pointed by index
            System.exit(1);
        }    
                 
    }
}