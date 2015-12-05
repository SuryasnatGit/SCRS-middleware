package edu.umn.csci5801;

public class Student extends People {
	private String department;
	private String type; // Undergraduate, graduate, phd, etc.
	private int currentCredits;
	private String plan;
	private String status; // Full time or part time
	private String advisor;

	public Student() {
		department = "";
		type = "";
		currentCredits = 0;
		plan = "";
		status = "";
		advisor = "";
	}

	public String getDepartmentInfo() {
		return department;
	}

	public String getStudentTypeInfo() {
		return type;
	}

	public int getCurrentCreditsInfo() {
		return currentCredits;
	}

	public String getPlanInfo() {
		return plan;
	}

	public String getStatusInfo() {
		return status;
	}

	public String getAdvisorInfo() {
		return advisor;
	}

	public void updateDepartment(String newDepartment) {
		department = newDepartment;
	}
	
	public void updateType(String newType) {
		type = newType;
	}
	
	public void updateCurrentCredits(int newCredits) {
		currentCredits = newCredits;
	}
	
	public void updatePlan(String newPlan) {
		plan = newPlan;
	}
	
	public void updateStatus(String newStatus) {
		status = newStatus;
	}
	
	public void updateAdvisor(String newAdvisor) {
		advisor = newAdvisor;
	}
}
