package prog5;
/******************************************************************************/
/* Guilherme Virgilio P. O. Simoes                                            */
/* Login ID: pici7127                                                         */
/* CS-102, Winter 2016                                                        */
/* Programming Assignment 5                                                   */
/* Calendar Tree: A binary tree with Calendar instances                       */
/******************************************************************************/

import java.util.NoSuchElementException;

public class CalendarTree {
    private static final int MESSAGE = 0, RESULTS = 1;
    // MESSAGE: reference to the GUI's message board
    // RESULTS: reference to the GUI's results board
        
    CalendarTreeNode root;  //first node of the tree
    Gui theGUI;

/******************************************************************************/
/* Method: CalendarTree (constructor)                                         */
/* Purpose: sets root to null                                                 */
/* Parameters:                                                                */
/*  void                                                                      */
/* Returns: void                                                              */
/******************************************************************************/    
    public CalendarTree(){
        root = null;
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
        return (root == null);
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
        return size(root);
    }

    private int size(CalendarTreeNode current){
    // Define the size recursively
        if (current == null) return 0;
        if (current.getLeft() == null && current.getRight() == null) return 1;
    // Size of the left tree + right tree
        return (size(current.getLeft()) + size(current.getRight()));					//R 
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
        root = null;
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
    public boolean search(Calendar item){
        return search(item, root);
    }
    
    private boolean search(Calendar item, CalendarTreeNode current){
        if (current == null) return false;
        if (current.getDatum().compareTo(item) == 0) return true;
        if (current.getDatum().compareTo(item) < 0) //item on the right
            return search(item, current.getRight());
        else
            return search(item, current.getLeft()); //item on the left
    }
    
/******************************************************************************/
/* Method: search                                                             */
/* Purpose: checks the tree to look for a specific calendar.                  */
/*                                                                            */
/* Parameters:                                                                */
/*  String name:            calendar to be looked for                         */
/*  CalendarTreeNode current:  the tree's root (helper method only)           */    
/* Returns: Calendar        calendar with the name as requested               */
/******************************************************************************/    

    // This method acts in a similar way to the above search
    public CalendarTreeNode search(String name){
        return search(name, root);
    }
    
    private CalendarTreeNode search(String name, CalendarTreeNode current){
        if (current == null) throw new NoSuchElementException();
        if (current.getDatum().getCalendarName().compareTo(name) == 0) 
            return current;
        if (current.getDatum().getCalendarName().compareTo(name) < 0)
            return search(name, current.getRight());
        else
            return search(name, current.getLeft());
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
    void add (Calendar item){
        root = add(item, root);
    }
    
    private CalendarTreeNode add (Calendar item, CalendarTreeNode current){
        if(current == null){
            CalendarTreeNode leaf = new CalendarTreeNode();
            leaf.setDatum(item);
            leaf.setLeft(null);
            leaf.setRight(null);
            return(leaf);
        }
        if(current.getDatum().compareTo(item) < 0)
            current.setRight(add(item, current.getRight())); //item on the right
        else
            current.setLeft(add(item, current.getLeft())); //item on the left
        return current;
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
        root = remove(item, root);
    }
    
    private CalendarTreeNode remove (Calendar item, CalendarTreeNode current){
        if(current == null)
            throw new NoSuchElementException();
        if(current.getDatum().compareTo(item) < 0){
        // Search to the right
            current.setRight(remove(item, current.getRight()));
            return current;
        }
        if(current.getDatum().compareTo(item) > 0){
        // Search to the left
            current.setLeft(remove(item, current.getLeft()));
            return current;
        }
        // Found the item.
        if (current.getLeft() == null)
        /* No left children. Whatever is on the right will take the place of the 
        item that was discarded */
            return current.getRight();
        if (current.getRight() == null)
        /* No right children. Whatever is on the left will take the place of the 
        item that was discarded */            
            return current.getLeft();
        /* Item has both left and right children. Look for heir. Take the
        highest of the lower values */
        CalendarTreeNode heir = current.getLeft();
        while(heir.getRight() != null)
            heir = heir.getRight();
        current.setDatum(heir.getDatum());
        current.setLeft(remove(heir.getDatum(), current.getLeft()));
        return current;
    }

/******************************************************************************/
/* Method: print                                                              */
/* Purpose: Traverses the tree and prints calendars                           */
/*          (use the LNR method so it prints in order)                        */
/* Parameters:                                                                */
/*  void                                                                      */
/*  CalendarTreeNode root:  the tree's root (helper method only)              */     
/* Returns: void                                                              */
/******************************************************************************/
    
    public void print(){
	print(root);
    }

    private void print(CalendarTreeNode current){
            if (current == null) return;
            print(current.getLeft());               //L
            current.getDatum().printCalendar();     //N
            theGUI.bufferToGui(RESULTS);
            print(current.getRight());              //R 
    }   
}
