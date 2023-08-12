package methodhandle;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;

class Timer {
	static long start;
	static long end;
	
	static public void start(){
		start = System.nanoTime();
	}
	
	static public void end(){
		end = System.nanoTime();
	}
	
	static public int duration(){
		return (int)(end - start);
	}
}

class Employee {
	String name;
	int id;
	
	public Employee(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	/*
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
	*/
	
	static public String getKlass() {
		return "Employee.class";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}

public class MethodHandleTest {

	public static void main(String[] args) throws Throwable {
		
		Employee employee = new Employee("Shreya", 21);
		MethodType getNameMT;
		MethodHandle getNameMH, getKlassNameMH, getCtorMH, getGetterMH;
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        getNameMT = MethodType.methodType(String.class);
        getKlassNameMH = lookup.findStatic(Employee.class, "getKlass" , getNameMT);
        getNameMH = lookup.findVirtual(Employee.class, "getName", getNameMT);
        
        String className = (String) getKlassNameMH.invokeExact();
        System.out.println("Class Name is : " + className);
        
        Timer.start();
        String name = (String)getNameMH.invoke(employee);
        Timer.end();
        System.out.println("Time Duration : " + Timer.duration());
        System.out.println("Name : " + name);
        
        Timer.start();
        name = (String)getNameMH.invoke(employee);
        Timer.end();
        System.out.println("Time Duration : " + Timer.duration());
        
        Timer.start();
        name = employee.getName();
        Timer.end();
        System.out.println("Time Duration : " + Timer.duration());
        
        getCtorMH = lookup.findConstructor(Employee.class, MethodType.methodType(void.class, String.class, int.class));
        Timer.start();
        Employee employee1 = (Employee)getCtorMH.invokeExact("Shreya Gulhane", 21);
        Timer.end();
        System.out.println("Employee Details : " + employee1);
        System.out.println("Time Duration : " + Timer.duration());
        
        getGetterMH = lookup.findGetter(Employee.class, "name", String.class);
        String name2 = (String) getGetterMH.invoke(employee);
        System.out.println("Name: " + name2);
        
        
	}

}
