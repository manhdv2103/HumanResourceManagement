import java.time.LocalDate;

public class Employee extends Staff {
    private double overtimeHours;

    public Employee(int id, String name, int age, double payRate,
                    LocalDate startDate, Department department, int daysOff, double overtimeHours) {

        super(id, name, age, payRate, startDate, department, daysOff);
        this.overtimeHours = overtimeHours;
    }

    public double getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(double overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    @Override
    public double calculateSalary() {
        return super.getPayRate() * 3000000 + overtimeHours * 200000;
    }

    @Override
    public void displayInformation() {
        displayInformation(28, 16);
    }

    @Override
    public void displayInformation(int nameWidth, int deptWidth) {
        System.out.printf("| %04d | %-" + nameWidth + "s | %3d | %-16s | %-" +
                        deptWidth + "s | %10s | %14.2f | %8d | %8.2f | %,21.0f |%n",

                super.getId(), super.getName(), super.getAge(), "Employee", super.getDepartment().getName(),
                super.getStartDate().format(HumanResources.DATE_FORMAT), this.overtimeHours,
                super.getDaysOff(), super.getPayRate(), this.calculateSalary()
        );
    }
}