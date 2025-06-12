/*
 * Registration enrollment program.
 * This program asks for a 6-digits student unique ID (J number) to let student register for classes
 * Student must be a in the database in order to register for a class
 * The program uses SQLite to store records of students and classes
 * It prevents students from enrolling in the same class twice or going over 18 credits
 */

import java.util.Scanner;

import javax.swing.JOptionPane;

class Main {
    //Input scanner for keyboard input
    public static Scanner kbInput = new Scanner(System.in);

    public static void main(String[] args) {
        //Array to store all courses
        ClassSection[] courses;
        System.out.println("*--- Registration Enrollment for Spring Semester --*");
        
        //Database connection and data population
        populateDatabase.connect();
        populateDatabase.insert();

        //Loop to allow student access to registration terminal
        while (true) {
            System.out.println("Enter your 6 digits J-number (J000000). If you are done enter zero: ");
            String jNum = kbInput.nextLine();

            //Exit condition for the loop
            if (jNum.equals("0")) {
                break;
            }

            //Ask again if J number is not valid
            if (!populateDatabase.verifyStudentId(jNum)) {
                System.out.println("Invalid J number. Please, enter a valid number. \n");
                continue;
            }

            //Loop to allow multiple class registrations
            while (true) {
                System.out.println(" Course - " + "Section - " + "Registration - " + "Credits - " + "Day/Time - "
                        + "Available seats");

                //Fetch couses from the database        
                courses = populateDatabase.selectAll();

                //Display courses available for registration
                for (ClassSection course : courses) {
                    if (course != null) {
                        System.out.println(course);

                    }
                }

                //Prompt user for registration number
                System.out.println("Enter a registration number or zero to return: ");
                int regNumber = kbInput.nextInt();
                System.out.println(" ");
                
                //Close registration terminal if user enters zero
                if (regNumber == 0) {
                    System.out.println("Registration terminal closed for J number " + jNum + "\n"
                            + "-----------------------------------------------" + "\n");
                    break;

                }

                
                int currentCredits = 0;
                
                //Calculate current credits for the student
                for (ClassSection c : courses) {
                    if (c.studentsIsEnrolled(jNum)) {
                        currentCredits += c.getCredits();
                    }
                }
                
                //Check if the registration number is valid
                for (ClassSection classSection : courses) {
                    if ((classSection.getRegistrationNumber() == regNumber)) { 
                        
                        //Add student to the class section if they are not already enrolled
                        int courseCredits = classSection.getCredits();

                        // Check if the student is already enrolled in the class
                        if(classSection.studentsIsEnrolled(jNum)){

                            //Give warning if student is already enrolled in the selected class
                            JOptionPane.showMessageDialog(null, 
                                "Student is already enrolled!", "Try another course!", 
                                JOptionPane.WARNING_MESSAGE);
                        
                        //If student has 18 credits and can't register for more classes, give warning
                        }else if ((currentCredits + courseCredits > 18)) {
                            JOptionPane.showMessageDialog(null, 
                                    "You cannot enroll in this course. You already have " + currentCredits +
                                    " credits and the maximum is 18 credits per semester." +
                                    "\n" + "Please, contact advisor if you want to add more classes22.", 
                                    "Credit Limit Reached",
                                    JOptionPane.WARNING_MESSAGE);

                        //If student can enroll, add them to the class section and update database
                        } else if (classSection.addStudent(jNum)) {
                            populateDatabase.updateData(jNum, regNumber);

                        }
                        //If student can't enroll, give warning
                        else {
                            JOptionPane.showMessageDialog(null, 
                                    "Unable to enroll. The course is full or you are already enrolled.", 
                                    "Enrollment Error", 
                                    JOptionPane.ERROR_MESSAGE);
                        }

                        break;

                    }
                }

            }

        }

        //Close the scanner and database connection
        System.out.println("Registration terminal closed!");
        populateDatabase.closeConnection();
    }

} // end main

/*
 * sources used:
 * "https://java-programming.mooc.fi/part-2/2-repeating"
 * https://www.geeksforgeeks.org/program-to-check-if-the-string-is-null-in-java
 */
