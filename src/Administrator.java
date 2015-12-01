
public class Administrator extends People{
	private String department;
	private String type;
	
	public String getDepartmentInfo() {
		return department;
	}
	
	public String getTypeInfo() {
		return type;
	}
	
	public void updateDepartment(String newDepartment) {
		department = newDepartment;
	}
	
	public void updateType(String newType) {
		type = newType;
	}
}
