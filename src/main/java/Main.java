import org.postgresql.Driver;
import java.sql.*;

public class Main {
    public static void main(String[] args) {

        try{
            Class.forName("org.postgresql.Driver");
            DriverManager.registerDriver(new Driver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("eror");
            return;
        }

        try(Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/university","andrew", "Andrew123");
            Statement statemetn = connection.createStatement()){
            String StudetFak;
            String FlorFak;
            String HostFak;
            String name;
            int floorr;
            int StudentId = 1;
            int paid = 0;
            ResultSet resultStudent  = statemetn.executeQuery("select * from hostel, flor, student left join room on student.id_room = room.id where room.id_hostel = hostel.id and hostel.id_faculty notnull and room.id_floor = flor.id and student.id_faculty = flor.id_faculty;");
            while (resultStudent.next()){
                StudetFak = resultStudent.getString(10);
                FlorFak = resultStudent.getString(5);
                HostFak = resultStudent.getString(2);
                name = resultStudent.getString(9);
                if(StudetFak.equals(FlorFak)&&StudetFak.equals(HostFak)){
                }else System.out.println(name + " живе в кімнаті не від свого універу");
            }
            resultStudent.close();
            ResultSet resultSet = statemetn.executeQuery("select * from payment, student where id_student = student.id and paid ='false';");
            while (resultSet.next()){
                System.out.println(resultSet.getString(6) + " не заплатив за " + resultSet.getString(3) + " семестр");
            }
            resultSet.close();
            ResultSet resultSetr = statemetn.executeQuery("select student.id, first_name, cost, flor.floor_name from flor, hostel, room, student left join payment on student.id = payment.id_student where student.id_room = room.id and hostel.id = room.id_hostel and flor.id = room.id_floor;");
            while (resultSetr.next()){
                if(StudentId == resultSetr.getInt(1)){
                    paid += resultSetr.getInt(3);
                }else{
                    if(paid>=5000 && resultSetr.getInt(4)>=3){
                        System.out.println(resultSetr.getString(2));
                    }
                    paid = 0;
                    StudentId = resultSetr.getInt(1);
                    paid += resultSetr.getInt(3);
                }
            }
            resultSetr.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
