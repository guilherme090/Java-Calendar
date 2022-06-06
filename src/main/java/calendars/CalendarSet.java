package calendars;
/******************************************************************************/
/* Guilherme Virgilio P. O. Simoes                                            */
/* Login ID: pici7127                                                         */
/* CS-102, Winter 2016                                                        */
/* Programming Assignment 5                                                   */
/* Calendar Tree: A binary tree with Calendar instances                       */
/******************************************************************************/

import java.util.LinkedList;
import java.util.NoSuchElementException;

import db.Gui;

/**
 * 
 * @author Guilherme Virgilio P. O. Simoes 
 * @version 1.1.0
 * 
 * Transformation of CS-102 project into a Maven project
 */
public class CalendarSet {
    private LinkedList<Calendar> set;    
    Gui theGUI;

/******************************************************************************/
/* Method: CalendarTree (constructor)                                         */
/* Purpose: sets root to null                                                 */
/* Parameters:                                                                */
/*  void                                                                      */
/* Returns: void                                                              */
/******************************************************************************/    
    public CalendarSet(){
        set = new LinkedList<Calendar>();
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
/* Method: isEmpty                                                            */
/* Purpose: tells if the list is empty (i.e.) if its number of elements is    */
/*          zero                                                              */
/* Parameters:                                                                */
/*  void                                                                      */
/* Returns: Boolean:    is the list empty?                                    */
/******************************************************************************/
    
    public boolean isEmpty(){
        return set.size() == 0;
    }
    
/******************************************************************************/
/* Method: size                                                               */
/* Purpose: traverses the list and counts the number of elements              */
/*                                                                            */
/* Parameters:                                                                */
/*  CalendarTreeNode current:  the tree's root (helper method only)           */ 
/* Returns: int:        number of elements in the list                        */
/******************************************************************************/    
    public int size(){
    	return set.size();
    }

/******************************************************************************/
/* Method: clear                                                              */
/* Purpose: removes all calendars from the tree                               */
/*                                                                            */
/* Parameters:                                                                */
/*  void                                                                      */
/* Returns: void                                                              */
/******************************************************************************/
    public void clear(){
        set.clear();
    }
    
/******************************************************************************/
/* Method: search                                                             */
/* Purpose: checks the tree to look for a specific calendar.                  */
/*                                                                            */
/* Parameters:                                                                */
/*  Calendar item:          calendar to be looked for                         */
/*  CalendarTreeNode current:  the tree's root (helper method only)           */    
/* Returns: boolean:        item found?                                       */
/******************************************************************************/    
    /**
     * Returns whether a calendar with the given name exists.
     * 
     * @param name
     * @return boolean
     */
    public boolean existsCalendar(Calendar cal){
    	return set.contains(cal);
//        for(Calendar calendar: set) {
//     	   if (calendar.getCalendarName().toLowerCase().
//     			   equals(cal.getCalendarName().toLowerCase())) {
//     		   return true;
//     	   }
//        }
//        return false;
     } 
    
    /**
     * Returns a calendar based on its name.
     * 
     * @param name
     * @return requested calendar, if found.
     * @throws NoSuchElementException
     */
    public Calendar search(String name){
       for(Calendar calendar: set) {
    	   if (calendar.getCalendarName().toLowerCase().
    			   contains(name.toLowerCase())) {
    		   return calendar;
    	   }
       }
       throw new NoSuchElementException();
    }  
    
/******************************************************************************/
/* Method: add                                                                */
/* Purpose: adds a node to the tree.                                          */
/*                                                                            */
/* Parameters:                                                                */
/*  Calendar item:  value to be added                                         */
/*  CalendarTreeNode root:  the tree's root (helper method only)              */     
/* Returns: void                                                              */
/******************************************************************************/ 
    public void add (Calendar item){
        set.add(item);
    }
    
/******************************************************************************/
/* Method: remove                                                             */
/* Purpose: removes a node from the tree. Keeps the tree shape.               */
/*                                                                            */
/* Parameters:                                                                */
/*  Calendar item:  value to be removed from the tree                         */
/*  CalendarTreeNode root:  the tree's root (helper method only)              */     
/* Returns: void                                                              */
/******************************************************************************/
    public void remove(Calendar item){
        set.remove(item);
    }

    /**
     * Prints all found calendars
     */
    public void print(){
    	for(Calendar calendar: set) {
     	   calendar.printCalendar();
        }
    }
    
    public Calendar getCalendar(int index) {
    	return set.get(index);
    }
}
