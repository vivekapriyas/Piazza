package piazza.src;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


//usecase 4. kontroller for søk i poster
public class SearchCtrl extends ConnectorClass{
    public String keyword;

    public SearchCtrl(){
        connect();
    }
    
    //utfører spørring mot post, comment, followup, instructorsanswer og studentsanswer tabellene
    public void executeSearch(String keyword){
        setKeyword(keyword);
        String query = "select distinct postID from post left outer join (_comment inner join followup using(followupnr)) using (postID)" 
                        +"where title like ? or followup.content like ? or post.content like ?;";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%"+keyword+"%");
            stmt.setString(2, "%"+keyword+"%");
            stmt.setString(3, "%"+keyword+"%");
            stmt.executeQuery();
            
            ResultSet rs = stmt.getResultSet();

            String firstCol = "PostID";
            System.out.println("\n\n----------");
            System.out.format("|%-8s|%n", firstCol);
            System.out.println("----------");
            
            while (rs.next()) {
                int postID = rs.getInt("postID");
                System.out.format("|%-8s|%n", postID);

            }
            
            System.out.println("----------");
        }
        catch (SQLException e){
            System.out.println("DB error while trying to add post: " + e);
        }
        
    }

    //helper
    public void setKeyword(String keyword){
        this.keyword = keyword;
    }

}