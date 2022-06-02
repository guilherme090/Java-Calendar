package calendars;

/******************************************************************************/
/* Guilherme Virgilio P. O. Simoes                                            */
/* Login ID: pici7127                                                         */
/* CS-102, Winter 2016                                                        */
/* Programming Assignment 5                                                   */
/* CalendarTreeNode: Nodes for the Calendar binary tree.                      */
/******************************************************************************/

/**
 * 
 * @author Guilherme Virgilio P. O. Simoes 
 * @version 1.1.0
 * 
 * Transformation of CS-102 project into a Maven project
 */
public class CalendarTreeNode {
    private Calendar datum;     //calendar stored here
    private CalendarTreeNode left;      //first child
    private CalendarTreeNode right;     //second child
    
/******************************************************************************/
/* Method: get<FEATURE>                                                       */
/* Purpose: check the tree's features specified between < >                   */
/*                                                                            */
/* Parameters:                                                                */
/*  void                                                                      */
/* Returns: Calendar/CalendarTreeNode:   the tree's feature                   */
/******************************************************************************/
public Calendar getDatum(){
    return datum;
}

public CalendarTreeNode getLeft(){
    return left;
}

public CalendarTreeNode getRight(){
    return right;
}

/******************************************************************************/
/* Method: set<FEATURE>                                                       */
/* Purpose: change the tree's feature specified between < >                   */
/*                                                                            */
/* Parameters:                                                                */
/*  Calendar / CalendarNode  new<FEATURE>:                                    */
/* Returns: void                                                              */
/******************************************************************************/
public void setDatum(Calendar newDatum){
    datum = newDatum;
}

public void setLeft(CalendarTreeNode newLeft){
    left = newLeft;
}

public void setRight(CalendarTreeNode newRight){
    right = newRight;
}
}
