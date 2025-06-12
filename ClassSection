public class ClassSection {

    //Variables that belongs to the class
    private String courseNumber;
    private int sectionNumber;
    private int registrationNumber;
    private String day;
    private int credits;
    private int startTime;
    private String[] enrolledStudents;
    private int enrolledCount;

    //Constructors to set up new course section
    public ClassSection( String courseNumber, int sectionNumber, int registrationNumber, int credits, String day ) {
        this.courseNumber = courseNumber;
        this.sectionNumber = sectionNumber;
        this.day = day;
        this.registrationNumber = registrationNumber;
        this.credits = credits;
        this.startTime = 9;
        this.enrolledStudents = new String[ 20 ]; //Holds up to 20 students
        this.enrolledCount = 0;

    }

    //Getters to return value stores in each field
    public int getSectionNumber() {
        return sectionNumber;
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public int getCredits(){return credits;}

    public String getDay() {
        return day;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEnrolledNumber() {
        int count = 0;
        for(String enrolled : enrolledStudents){
            if ( enrolled != null ) {
                count++;
            }
        }
        return count;
    }

    //Returns amount of seats available in the course section
    public int getSeatsAvailable() {
        int total = (20 - getEnrolledNumber ());
        return total;
    }

    // Returns a list of enrolled students IDs as a comma separated string in the course section 
    public StringBuilder getEnrolledStudentList() {
        StringBuilder enrolled = new StringBuilder ();

        for(String student : enrolledStudents){
            if ( student != null ) {
                enrolled.append ( student ).append ( "," );

            }

        }
        return enrolled;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    // set or update start time. If if goes over 23, reset to 0-23 range
    public void setStartTime( int startTime ) {
        this.startTime = startTime;
        if ( this.startTime > 23 ) {
            this.startTime -= 24; 
        }
    }

    // Checks if the student is already enrolled in the course section
    public boolean studentsIsEnrolled( String isEnrolled ) {
        for(String enrolledStudent : enrolledStudents){
            //If the student is enrolled, return true
            if ( enrolledStudent != null && enrolledStudent.equals ( isEnrolled ) ) {
                return true;
            }
        }
        //Return false if not found on the list
        return false;
    }

    //Add student to the chosen course section
    public boolean addStudent( String studentId ) {
        //If already enrolled, don't enroll. Return false
        if ( studentsIsEnrolled ( studentId ) ) {
            return false;
        }

        //Add student to the list and increase the amount of students enrolled
        if ( enrolledCount < 20) {
            enrolledStudents[ enrolledCount ] = studentId;
            enrolledCount++;
            return true;
        }

        //Course is full otherwise
        return false;
    }


    //Display course info as a formatted string
    @Override
    public String toString() {
        return String.format( "%9s  %3d  %12d %10d %8s  %2d  %10d", courseNumber, sectionNumber, registrationNumber, credits, day, startTime, getSeatsAvailable () );
    }

}

/* https://medium.com/javarevisited/5-effective-string-practices-you-should-know-e9a75811b123
 *
 */
