import java.time.LocalDate;

public class Manager extends Staff {
    private String position;

    public Manager(int id, String name, int age, double payRate,
                   LocalDate startDate, Department department, int daysOff, String position) {

        super(id, name, age, payRate, startDate, department, daysOff);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public double calculateSalary() {
        double salary = super.getPayRate() * 5000000;
        switch (position) {
            case "Business Leader" -> salary += 8000000;
            case "Project Leader" -> salary += 5000000;
            case "Technical Leader" -> salary += 6000000;
        }

        return salary;
    }

    @Override
    public void displayInformation() {
        displayInformation(28, 16);
    }

    @Override
    public void displayInformation(int nameWidth, int deptWidth) {
        System.out.printf("| %04d | %-" + nameWidth + "s | %3d | %-16s | %-" +
                        deptWidth + "s | %10s | %14.2f | %8d | %8.2f | %,21.0f |%n",

                super.getId(), super.getName(), super.getAge(), this.position, super.getDepartment().getName(),
                super.getStartDate().format(HumanResources.DATE_FORMAT), 0.0,
                super.getDaysOff(), super.getPayRate(), this.calculateSalary()
        );
    }
}