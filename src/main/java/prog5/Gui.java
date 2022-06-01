
package prog5;

/******************************************************************************/
/* Guilherme Virgilio P. O. Simoes                                            */
/* Login ID: pici7127                                                         */
/* CS-102, Winter 2016                                                        */
/* Programming Assignment 5                                                   */
/* Graphical User Interface for accessing the various methods of the CS-102   */
/* calendar program                                                           */
/******************************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gui extends JFrame{
    // Available commands. The following are the user's options in the menu
    public static final int EVERYTHING = 1, CALENDAR = 2, TIME = 3, 
    DESCRIPTION = 4, ADD_APPOINTMENT = 5, ADD_CAL_FILE = 6, DEL_APPOINTMENT = 7,
    DEL_CAL = 8, SAVE_DATABASE = 9, LOAD_DATABASE = 10;    
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
    
    private static final int MESSAGE = 0, RESULTS = 1;
    // MESSAGE: reference to the GUI's message board
    // RESULTS: reference to the GUI's results board
    
    private final int INPUT_OPTIONS = 18; // number of input options available
    private final int OUTPUT_OPTIONS = 2; // number of output options available
    
    /*
    List of GUI elements needed for the program. They are grouped by functions
    in order to make them more readable.
    */
    private Container contentBox; //container that will store the GUI elements
    /*
    Radio buttons will store each of the user inputs and therefore will be named
    in a similar fashion as the constants that have been used in the previous
    versions of the program.
    */
    private JRadioButton everything, calendar, time, description,
            add_appointment, add_cal_file, del_appointment, del_cal, 
            save_database, load_database;
    private int currentSelection = EVERYTHING; // will store the current option
    private ButtonGroup optionGroup; //group of options for radio buttons
    private JButton go; // button for confirming user option
    /*
    Output texts will store:
    messageBoard: erro messages / warnings / messages
    resultsBoard: data selected from the calendars    
    */
    private JTextArea messageBoard, resultsBoard;
    
    // Scroll panes will make the text fit on the screen
    private JScrollPane messageScrollPane, resultsScrollPane;
    
    /*
    Text fields will serve for the user to provide information that will be used
    as inputs for the calendar searches.
    fileNameField: calendar file name in the disk
    calNameField: calendar name in the database
    s(attribute)Field: start time or date
    e(attribute)Field: end time
    descField: description attribute of an appointment
    */
    private JTextField fileNameField, calNameField, sYearField, sMonthField,
            sDayField, sHourField, sMinuteField, eHourField, eMinuteField,
            descField;
    /*
    Labels will store useful information for the user to know about the text
    fields below them.
    titleLabel: CS-102 CALENDAR PROGRAM text
    sTimeLabel: Marks the start date and time for an appointment
    eTimeLabel: Marks the end date and time for an appointment
    */
    private JLabel titleLabel, sTimeLabel, eTimeLabel;  
    /*
    The following labels will serve solely for the purpose of separating date
    data in a more readable way
    */
    private JLabel slash1, slash2, trace1, colon1, colon2;
    
    /*
    Panels will make the interface more readable.
    inputs: will contain lines of inputs
    outputs: will contain lines of outputs
    startFlow: will contain a line of inputs for a single appointment (start)
    endFlow: will contain a line of inputs for a single appointment (end)
    */
    
    private JPanel inputs, outputs, startFlow, endFlow;
    
    private Database agenda; //will contain a reference to the created database
    
    private String mBuffer; //contains data to be showed on screen (message)
    private String rBuffer; //contains data to be showed on screen (results)
    
    public Gui(){
        // Open the frame
        super ("CS-102 Calendar Program");
        contentBox = getContentPane();
        contentBox.setLayout(new GridLayout(1,2));
        
        mountInputs(); // add all the input fields
               
        mountOutputs(); // add all the output fields 

        // Create event handler for radio buttons
        OptionHandler groupOfOptions = new OptionHandler( );
        // Set all fields initially to disabled (everything will be selected)
        groupOfOptions.setParameters(false, false, "Start time:", false, false, 
                false, false, false, EVERYTHING);
        // Add appropriate listeners       
        everything.addItemListener( groupOfOptions );
        calendar.addItemListener( groupOfOptions );
        time.addItemListener( groupOfOptions );
        description.addItemListener( groupOfOptions );
        add_appointment.addItemListener( groupOfOptions );
        add_cal_file.addItemListener( groupOfOptions );
        del_appointment.addItemListener( groupOfOptions );
        del_cal.addItemListener( groupOfOptions );
        save_database.addItemListener( groupOfOptions );
        load_database.addItemListener( groupOfOptions );
        
        // Create event handler for "go" button
        ButtonHandler buttonConfirm = new ButtonHandler();
        // Add appropriate listener
        go.addActionListener(buttonConfirm);
        
        setSize(800,800); // default size for the screen
        setVisible(true); // show GUI contents
    }

/******************************************************************************/
/* Method: mountInputs                                                       */
/* Purpose: creates all the input GUI elements and adds them to the          */
/*          interface                                                         */
/*                                                                            */          
/* Parameters:                                                                */
/*  void                                                                      */        
/* Returns: void                                                              */
/******************************************************************************/     
    void mountInputs(){
        // Add inputs to the big screen
        inputs = new JPanel();
        inputs.setLayout(new GridLayout(INPUT_OPTIONS,1));
        contentBox.add(inputs);
        
        // Fill inputs with data
        everything = new JRadioButton("Print all records", true);
        calendar = new JRadioButton("Print one calendar");
        time = new JRadioButton("Search for a time");
        description = new JRadioButton("Search for a description");
        add_appointment = new JRadioButton("Add new appointment");
        add_cal_file = new JRadioButton("Add calendar file to program");
        del_appointment = new JRadioButton("Delete appointment");
        del_cal = new JRadioButton("Delete calendar file");
        save_database = new JRadioButton("Save from database to disk");
        load_database = new JRadioButton("Load from disk to database");
        
        // Add inputs that were just created
        inputs.add(everything);
        inputs.add(calendar);
        inputs.add(time);
        inputs.add(description);
        inputs.add(add_appointment);
        inputs.add(add_cal_file);
        inputs.add(del_appointment);
        inputs.add(del_cal);
        inputs.add(save_database);
        inputs.add(load_database);
        
        // Make them all part of the same group
        optionGroup = new ButtonGroup();
        optionGroup.add(everything);
        optionGroup.add(calendar);
        optionGroup.add(time);
        optionGroup.add(description);
        optionGroup.add(add_appointment);
        optionGroup.add(add_cal_file);
        optionGroup.add(del_appointment);
        optionGroup.add(del_cal);
        optionGroup.add(save_database);
        optionGroup.add(load_database);
        
        // Add fields for file name and calendar name
        fileNameField = new JTextField("File name");
        calNameField =  new JTextField("Calendar name");
        inputs.add(fileNameField);
        inputs.add(calNameField);
        
        // Add label and fields for start time
        sTimeLabel = new JLabel("Start time:");
        inputs.add(sTimeLabel);
        startFlow = new JPanel();
        startFlow.setLayout(new FlowLayout());
        // Add date and time 
        sYearField = new JTextField("YYYY");
        slash1 = new JLabel("/");
        sMonthField = new JTextField("MM");
        slash2 = new JLabel("/");
        sDayField = new JTextField("DD");
        trace1 = new JLabel("-");
        sHourField = new JTextField("HH");
        colon1 = new JLabel(":");
        sMinuteField = new JTextField("MM");
        startFlow.add(sYearField);
        startFlow.add(slash1);
        startFlow.add(sMonthField);
        startFlow.add(slash2);
        startFlow.add(sDayField);
        startFlow.add(trace1);
        startFlow.add(sHourField);
        startFlow.add(colon1);
        startFlow.add(sMinuteField);
        // Add start flow to the inputs panel 
        inputs.add(startFlow);
        
        // Add label and fields for end time
        eTimeLabel = new JLabel("End time:");
        inputs.add(eTimeLabel);
        endFlow = new JPanel();
        endFlow.setLayout(new FlowLayout());
        // Add time 
        eHourField = new JTextField("HH");
        colon2 = new JLabel(":");
        eMinuteField = new JTextField("MM");
        endFlow.add(eHourField);
        endFlow.add(colon2);
        endFlow.add(eMinuteField);
        // Add end flow to the inputs panel 
        inputs.add(endFlow);
        
        // Add a field for appointment description
        descField = new JTextField("Description");
        inputs.add(descField);
        
        // Add the final button
        go = new JButton("GO");
        inputs.add(go);
    }
    
/******************************************************************************/
/* Method: mountOutputs                                                       */
/* Purpose: creates all the output GUI elements and adds them to the          */
/*          interface                                                         */
/*                                                                            */          
/* Parameters:                                                                */
/*  void                                                                      */        
/* Returns: void                                                              */
/******************************************************************************/    
    void mountOutputs(){
        // Add outputs to the content box
        outputs = new JPanel();
        outputs.setLayout(new GridLayout(OUTPUT_OPTIONS,1));
        contentBox.add(outputs);
        
        // Add the message board. It is not editable
        messageBoard = new JTextArea("Welcome to the CS-102 Calendar Program."
                + "\n");
        outputs.add(messageBoard);
        messageBoard.setEditable(false);
        // Add a scoll pane for expanding text area
        messageScrollPane = new JScrollPane(messageBoard);
        messageScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.
                VERTICAL_SCROLLBAR_ALWAYS);
        outputs.add(messageScrollPane); 
        
        // Add the results board.  It is not editable
        resultsBoard = new JTextArea("Results:\n");
        resultsBoard.setEditable(false);
        outputs.add(resultsBoard);  
        // Add a scoll pane for expanding text area
        resultsScrollPane = new JScrollPane(resultsBoard);
        resultsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.
                VERTICAL_SCROLLBAR_ALWAYS);
        outputs.add(resultsScrollPane);
    }
    
/******************************************************************************/
/* Guilherme Virgilio P. O. Simoes                                            */
/* Login ID: pici7127                                                         */
/* CS-102, Winter 2016                                                        */
/* Programming Assignment 5                                                   */
/* Listener for radio buttons to administer the GUI                           */
/* (SUB-CLASS)                                                                */
/******************************************************************************/
    
    private class OptionHandler implements ItemListener{
        public void itemStateChanged( ItemEvent iEvent )
        {
            if (iEvent.getSource( ) == everything){
            // no arguments to ask for
            setParameters(false, false, "Start time:", false, false, false, 
                    false, false, EVERYTHING);
            }
            
            else if (iEvent.getSource( ) == calendar){
            // Ask for calendar name only
            setParameters(false, true, "Start time:", false, false, false,
                    false, false, CALENDAR);
            }
            
            else if (iEvent.getSource( ) == time){
            // Ask for date and time
            setParameters(false, true, "Desired time:", true, true, true, false, 
                    false, TIME);
            }
            
            else if (iEvent.getSource( ) == description){
            // Ask for description only
            setParameters(false, false, "Start time:", false, false, false,
                    false, true, DESCRIPTION);
            }
            
            else if (iEvent.getSource( ) == add_appointment){
            // Ask for cal name, date, start and end time, description
            setParameters(false, true, "Start time:", true, true, true,
                    true, true, ADD_APPOINTMENT);
            }
            
            else if (iEvent.getSource( ) == add_cal_file){
            // Ask for calendar file only
            setParameters(true, false, "Start time:", false, false, false,
                    false, false, ADD_CAL_FILE);
            }
            
            else if (iEvent.getSource( ) == del_appointment){
            // Ask for everything except calendar file
            setParameters(false, true, "Start time:", true, true, true,
                    true, true, DEL_APPOINTMENT);
            }
            
            else if (iEvent.getSource( ) == del_cal){
            // Ask for calendar name only
            setParameters(false, true, "Start time:", false, false, false,
                    false, false, DEL_CAL);
            }
            
            else if (iEvent.getSource( ) == save_database){
            // no arguments to ask for
            setParameters(false, false, "Start time:", false, false, false, 
                    false, false, SAVE_DATABASE);
            }
            
            else if (iEvent.getSource( ) == load_database){
            // no arguments to ask for
            setParameters(false, false, "Start time:", false, false, false, 
                    false, false, LOAD_DATABASE);
            }     
        }
        
/******************************************************************************/
/* Method: setParameters                                                      */
/* Purpose: makes it easier to set the input boxes for the GUI. Activates,    */
/*          de-activates, sets text according to parameters                   */
/*                                                                            */          
/* Parameters:                                                                */
/*  boolean file:     file name(activate?)                                    */
/*  boolean cal:      calendar name                                           */
/*  String timeLabel: new Label for start time input(activate?)               */
/*  boolean sTLabel:  start time label(activate?)                             */
/*  boolean sDate:    start date(activate?)                                   */
/*  boolean sTime:    start time(activate?)                                   */
/*  boolean eTime:    end time(activate?)                                     */
/*  boolean desc:     appointment description(activate?)                      */
/*  int sel:          selected item                                           */        
/* Returns: void                                                              */
/******************************************************************************/
        private void setParameters(boolean file, boolean cal, String timeLabel,
                boolean sTLabel, boolean sDate, boolean sTime, boolean eTime,
                boolean desc, int sel){
            fileNameField.setEnabled(file);
            calNameField.setEnabled(cal);
            sTimeLabel.setText(timeLabel);
            sTimeLabel.setEnabled(sTLabel);
            sYearField.setEnabled(sDate);
            sMonthField.setEnabled(sDate);
            sDayField.setEnabled(sDate);
            sHourField.setEnabled(sTime);
            sMinuteField.setEnabled(sTime);
            eTimeLabel.setEnabled(eTime);
            eHourField.setEnabled(eTime);
            eMinuteField.setEnabled(eTime);
            descField.setEnabled(desc);
            currentSelection = sel;           
        }
    }
    
/******************************************************************************/
/* Guilherme Virgilio P. O. Simoes                                            */
/* Login ID: pici7127                                                         */
/* CS-102, Winter 2016                                                        */
/* Programming Assignment 5                                                   */
/* Listener for the "go" button to confirm user input                         */
/* (SUB-CLASS)                                                                */
/******************************************************************************/
    
    private class ButtonHandler implements ActionListener{
        public void actionPerformed( ActionEvent aEvent )
        {
            if (aEvent.getSource( ) == go){
                clearGui(MESSAGE);
                clearGui(RESULTS);
                switch (currentSelection){
                case (EVERYTHING):{
                    agenda.wholeDatabase(currentSelection, "");
                } break;
                
                case (CALENDAR):{
                    agenda.printACalendar(currentSelection, 
                        calNameField.getText());
                } break;
                
                case (TIME):{
                    agenda.lookForATime(currentSelection, 
                            sYearField.getText() + sMonthField.getText() +
                            sDayField.getText(), sHourField.getText() +
                            sMinuteField.getText() );
                }break;
                
                case (DESCRIPTION):{
                    agenda.lookForADescription(currentSelection, 
                            descField.getText());
                } break;
                
                case (ADD_APPOINTMENT):{
                    agenda.addNewAppointment(calNameField.getText(),
                            sYearField.getText() + sMonthField.getText() +
                            sDayField.getText(), sHourField.getText() +
                            sMinuteField.getText(),eHourField.getText() +
                            eMinuteField.getText(), descField.getText());
                }break;
                
                case (ADD_CAL_FILE):{
                    agenda.addNewCalendar(fileNameField.getText());     
                }break;
                
                 case (DEL_APPOINTMENT):{
                    agenda.delAppointment(calNameField.getText(),
                            sYearField.getText() + sMonthField.getText() +
                            sDayField.getText(), sHourField.getText() +
                            sMinuteField.getText(),eHourField.getText() +
                            eMinuteField.getText(), descField.getText());
                }break;
                
                case (DEL_CAL):{
                    agenda.delCalendar(calNameField.getText());     
                }break;

                case (SAVE_DATABASE):{
                    agenda.writeToDisk();     
                }break;

                case (LOAD_DATABASE):{
                    agenda.readFromDisk();     
                }
                }
            }
            
        }    
    }
    
/******************************************************************************/
/* Method: assignDatabase                                                     */
/* Purpose: copies a reference to a database and returns it so that it can be */
/*          re-assigned to the GUI and seen from this class.                  */
/*                                                                            */          
/* Parameters:                                                                */
/*  Database copying: reference to the database being copied to the GUI       */     
/* Returns: void                                                              */
/******************************************************************************/    
    public void assignDatabase (Database copying){
        agenda = copying;
    }
    
/******************************************************************************/
/* Method: printToGui                                                         */
/* Purpose: prints to the boards of the interface                             */
/*                          (prints to buffer)                                */          
/* Parameters:                                                                */
/*  int    boardID:    MESSAGE for writing to the message board               */ 
/*                     RESULTS for writing to the results board               */     
/*  String text:       text to be appended to the board's previous contents   */     
/* Returns: void                                                              */
/******************************************************************************/    
    public void printToGui (int boardID, String text){
        if(boardID == MESSAGE)
            mBuffer += text;
        else if (boardID == RESULTS)
            rBuffer += text;
    }
    
/******************************************************************************/
/* Method: bufferToGui                                                        */
/* Purpose: prints to the boards of the interface                             */
/*                (whatever is in the buffer)                                 */          
/* Parameters:                                                                */
/*  int    boardID:    MESSAGE for writing to the message board               */ 
/*                     RESULTS for writing to the results board               */     
/* Returns: void                                                              */
/******************************************************************************/    
    public void bufferToGui (int boardID){
        if(boardID == MESSAGE)
            messageBoard.setText(mBuffer);
        else if (boardID == RESULTS)
            resultsBoard.setText(rBuffer);
    }    
    
/******************************************************************************/
/* Method: clearGui                                                           */
/* Purpose: clear one of the boards of the interface                          */
/*                                                                            */          
/* Parameters:                                                                */
/*  int    boardID:    MESSAGE for clearing the message board                 */ 
/*                     RESULTS for clearing the results board                 */          
/* Returns: void                                                              */
/******************************************************************************/    
    public void clearGui (int boardID){
        if(boardID == MESSAGE){
            messageBoard.setText("Welcome to the CS-102 Calendar Program.\n");
            mBuffer = "";
        }
        else if (boardID == RESULTS){
            resultsBoard.setText("Results:\n");
            rBuffer = "";
        }    
    }
}
