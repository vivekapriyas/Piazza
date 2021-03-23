package piazza;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class LogInCtrl extends ConnectorClass{
    private String username;
    private String role;
    private String passord;
    private boolean success;

    public LogInCtrl(String username, String passord ){
        connect();
        this.username = username;
        this.passord = passord;
        this.role = "";
        this.success = false;
    }

    public boolean executeLogIn(){
        String query = "select password, role from _user where email=?";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            while (rs.next()){
                String userpassword = rs.getString(1);
                String userrole = rs.getString(2);

                if(this.passord.equals(userpassword) ){
                    success = true;
                    role = userrole;
                }
                else{
                }
            }
        }
        catch(SQLException e){
            System.out.println("Error in fetching password: "+e);
        }

        return success;
    }

    public void setInput(String username, String password){
        this.username = username;
        this.passord = password;
        return;
    }


    public String getRole(){
        return role;
    }

    public static void main(String[] args) {
        LogInCtrl test = new LogInCtrl("anders@ntnu.no","anderspassord");
        boolean check = test.executeLogIn();
        System.out.println(check);
    }

}
