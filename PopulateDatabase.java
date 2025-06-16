import java.sql.*;

class populateDatabase {

    //Database connection object
    private static Connection conn = null;

    //Method used for testing purposes
    // to run the program and populate the database
    public static void main( String[] args ) {
        connect ();
        insert ();
    }

    //Method to connect to the SQLite database
    protected static Connection connect() {
        //Database file location
        String url = "jdbc:sqlite:data/course-finder.sqlite";
        conn = null;
        try {
            //Tries to connect to SQLite file
            conn = DriverManager.getConnection ( url );
        } catch (SQLException e) {
            System.out.println ( e.getMessage () );
        }
        return conn;
    }

    //Method to close the connection when finished
    protected static void closeConnection() {
        try {
            conn.close ();
        } catch (Exception e) {
            throw new RuntimeException ( e );
        }
    }

    //Gets all course records from the database
    //and returns them as an array of ClassSection objects
    public static ClassSection[] selectAll() {
        ClassSection[] courses = new ClassSection[ getCourseCount () ];
        int i = 0;

        String sql = "SELECT * FROM courses";
        try (Statement stmt = conn.createStatement ();
             ResultSet rs = stmt.executeQuery ( sql )) {

            //Loop through each row in the result set
            while (rs.next ()) {
                //Extract each column value from the row
                int rn = rs.getInt ( "registrationNumber" );
                int sn = rs.getInt ( "sectionNumber" );
                String cn = rs.getString ( "courseNumber" );
                int cr = rs.getInt ( "credits" );
                String cd = rs.getString ( "day" );
                
                //Create a new ClassSection object with the extracted values
                ClassSection c = new ClassSection ( cn, sn, rn, cr, cd );

                //Call the set time method to set start time
                int startTime = rs.getInt ( "startTime" );
                c.setStartTime ( startTime );

                //Restore enrolled students from the database
                //If there are enrolled students, split them and add to the ClassSection object
                String enrolledStudents = rs.getString ( "enrolledStudents" );
                if ( enrolledStudents != null && !enrolledStudents.trim ().isEmpty () ){
                String[] studentsIds = enrolledStudents.split ( "," );
                  for(String studentId : studentsIds){
                      if ( !studentId.trim ().isEmpty () ) {
                          c.addStudent ( studentId );
                      }
                  }

                }
                //Add the ClassSection object to the array
                courses[ i++ ] = c;
            }

        } catch (SQLException e) {
            System.out.println ( e.getMessage () );
        }

        return courses; //return an array of courses
    }


    //Insert initial course and student data into the database
    public static void insert() {
        try {
            Statement stmt = conn.createStatement ();
            
            //Loading classes into the database
            stmt.executeUpdate ( "INSERT OR IGNORE INTO courses VALUES ('114568', '1', 'CSCI 111', 'M', '9', '3', '') " );
            stmt.executeUpdate ( "INSERT OR IGNORE INTO courses VALUES ('246851', '2', 'CSCI 112', 'T', '10', '3', '') " );
            stmt.executeUpdate ( "INSERT OR IGNORE INTO courses VALUES ('246580', '3', 'ENG 101', 'F', '13', '3', '') " );
            stmt.executeUpdate ( "INSERT OR IGNORE INTO courses VALUES ('436785', '4', 'ENG 102', 'R', '18','3', '') " );
            stmt.executeUpdate ( "INSERT OR IGNORE INTO courses VALUES ('680764', '5', 'MATH 171', 'W', '10', '4', '') " );
            stmt.executeUpdate ( "INSERT OR IGNORE INTO courses VALUES ('125642', '5', 'MATH 172', 'R', '10', '4','') " );
            stmt.executeUpdate ( "INSERT OR IGNORE INTO courses VALUES ('564852', '5', 'CIS 221', 'M', '13', '4','') " );

            //Loading students into the database
            stmt.executeUpdate ( "INSERT OR IGNORE INTO students VALUES('J000000' )" );
            stmt.executeUpdate ( "INSERT OR IGNORE INTO students VALUES('J000000')" );
            stmt.executeUpdate ( "INSERT OR IGNORE INTO students VALUES('J000000')" );
            stmt.executeUpdate ( "INSERT OR IGNORE INTO students VALUES('J000000')" );
            stmt.executeUpdate ( "INSERT OR IGNORE INTO students VALUES('J000000' )" );
            stmt.executeUpdate ( "INSERT OR IGNORE INTO students VALUES('J000000' )" );
            stmt.executeUpdate ( "INSERT OR IGNORE INTO students VALUES('J000000' )" );
            stmt.executeUpdate ( "INSERT OR IGNORE INTO students VALUES('J000000' )" );
            stmt.executeUpdate ( "INSERT OR IGNORE INTO students VALUES('J000000' )" );
            stmt.executeUpdate ( "INSERT OR IGNORE INTO students VALUES('J000000' )" );


        } catch (SQLException e) {
            System.out.println ( e.getMessage () );
        }
    }

    //Update a course by adding a student ID to the enrolledStudents column
    public static void updateData( String studentId, int registrationNumber ) {
        String sql = "UPDATE courses SET enrolledStudents = enrolledStudents || ?  WHERE registrationNumber = ?";

        try (PreparedStatement pstmt = conn.prepareStatement ( sql )) {
            //Append student ID with a comma to the enrolledStudents column
            //This allows multiple student IDs to be stored in the same column
            pstmt.setString ( 1, studentId + "," );
            pstmt.setInt ( 2, registrationNumber );

            //Execute the update statement
            pstmt.executeUpdate ();

        } catch (SQLException e) {
            System.out.println ( e.getMessage () );
        }
    }

    //Get the total number of courses in the database
    public static int getCourseCount() {
        String sql = "SELECT COUNT(*) AS totalRows FROM courses";

        try (PreparedStatement stmt = conn.prepareStatement ( sql )) { 
            ResultSet rs = stmt.executeQuery ();
            rs.next ();
            return rs.getInt ( "totalRows" );

        } catch (SQLException e) {
            System.out.println ( e.getMessage () );
    }
        return 0;
    }

    //Check if a student ID exists in the database
    public static boolean verifyStudentId( String studentId ) {
        String sql = "SELECT COUNT(*) AS count FROM students WHERE studentId = ?";
        try (PreparedStatement stmt = conn.prepareStatement ( sql )) {
            stmt.setString ( 1, studentId );
            ResultSet rs = stmt.executeQuery ();
            rs.next ();
            return rs.getInt ( "count" ) > 0;

        } catch (SQLException e) {
            System.out.println ( e.getMessage () );
        }
        return false;
    }
}
/*
 * https://stackoverflow.com/questions/49497556/how-to-update-column-with-multiple-values
 */


