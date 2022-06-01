package prog5;
/******************************************************************************/
/* Guilherme Virgilio P. O. Simoes                                            */
/* Login ID: pici7127                                                         */
/* CS-102, Winter 2016                                                        */
/* Programming Assignment 5                                                   */
/* Appointment calendar: Node Class for Appointment                           */
/*                                                                            */
/******************************************************************************/

public class AppointmentNode{
    private Appointment datum;          //link to an appointment object
    private AppointmentNode next;       //next object in the list

/******************************************************************************/
/* Method: AppointmentNode (constructor)                                      */
/* Purpose: takes an Appointment object as an attribute and creates a node    */
/*          with this value                                                   */
/* Parameters:                                                                */
/*  Appointment newApp:   new item to be created                              */
/* Returns: void                                                              */
/******************************************************************************/
    
    public AppointmentNode(Appointment newApp){
        datum = newApp;
        next = null;
    }

/******************************************************************************/
/* Method: get<FEATURE>                                                       */
/* Purpose: check the node's feature specified between < >                    */
/*                                                                            */
/* Parameters:                                                                */
/*  void                                                                      */
/* Returns: String:    the node's feature                                     */
/******************************************************************************/
    
    public Appointment getDatum(){
        return datum;
    }
    
    public AppointmentNode getNext(){
        return next;
    }
 
/******************************************************************************/
/* Method: set<FEATURE>                                                       */
/* Purpose: change the node's feature specified between < >                   */
/*                                                                            */
/* Parameters:                                                                */
/*  String  new<FEATURE>:                                                     */
/* Returns: void                                                              */
/******************************************************************************/    
  
    public void setDatum (Appointment datum){
        this.datum = datum;
    }
    
    public void setNext(AppointmentNode next){
        this.next = next;
    }
}