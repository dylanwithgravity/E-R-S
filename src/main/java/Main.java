import dao.EmployeeDaoImpl;
import model.Employee;

public class Main {
    public static void main(String[] args) {
        // used for db testing
        EmployeeDaoImpl emp = new EmployeeDaoImpl();

        Employee emp1 = emp.selectEmployeeByUsername("dylwil92");

        System.out.println(emp1);
    }
}
