package methodhandle;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.util.Arrays;

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
	
	public String getLastName(String name) {
		String[] splitString = name.split(" ", -1);
		System.out.println(splitString[2]);
		return splitString[splitString.length - 1];
	}
	
}

public class MethodHandleTest {

	public static void main(String[] args) throws Throwable {
		Employee employee = new Employee("Shreya", 21);
		MethodType getNameMT;
		MethodHandle getNameMH, getKlassNameMH, getCtorMH, getGetterMH, getLastNameMH;
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
       
        getLastNameMH = lookup.findVirtual(Employee.class, "getLastName", MethodType.methodType(String.class, String.class));
        Timer.start();
        System.out.println("Last name:- " + (String)getLastNameMH.invokeWithArguments(employee1, "Shreya Rameshwar Gulhane"));
        Timer.end();
        System.out.println("Time through handle : " + Timer.duration());
        
        Timer.start();
        System.out.println("Last name:- " + employee1.getLastName("Shreya Rameshwar Gulhane"));
        Timer.end();
        System.out.println("Time through direct invoke : " + Timer.duration());
        
        System.out.println("Type of Method Handle : " + getNameMH.type());
        System.out.println("Is Method Handle Varag Collector? : " + getNameMH.isVarargsCollector());
       

        MethodHandle deepToStringMH = MethodHandles.publicLookup().findStatic(Arrays.class, "deepToString", MethodType.methodType(String.class, Object[].class));
        Object[] objArr = new Object[] {"Hello", "World"};
        System.out.println(objArr[0]);
        System.out.println(objArr[1]);
        var s = (String) deepToStringMH.invokeExact(objArr);
        System.out.println(s);
        deepToStringMH.asCollector(Object[].class, 0);
        
        System.out.println(deepToStringMH.type());
        
        var s1 = (String)deepToStringMH.invokeExact(new Object[] {"Good", "Morning"});
        System.out.println(s1);
	    }

}