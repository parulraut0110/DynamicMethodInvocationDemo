package methodhandle;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

class Employee {
	String name;
	int id;
	
	public Employee(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public String toString() {
		return "Name : " + name + " Id : " + id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	static public String getKlass() {
		return "Employee.class";
	}
	
}

public class MethodHandleTest {

	public static void main(String[] args) throws Throwable {
		
		Employee employee = new Employee("Shreya", 21);
		MethodType getNameMT;
		MethodHandle getNameMH;
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        getNameMT = MethodType.methodType(String.class);
        getNameMH = lookup.findStatic(Employee.class, "getKlass" , getNameMT);
        getNameMH = lookup.findVirtual(Employee.class, "getName" , getNameMT);
        
        String name = (String)getNameMH.invoke(employee);
        
        System.out.println("Name : " + name);
	}

}
