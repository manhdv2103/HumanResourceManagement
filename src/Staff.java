import java.time.LocalDate;

public abstract class Staff implements ICalculator {
    private int id;
    private String name;
    private int age;
    private double payRate;
    private LocalDate startDate;
    private Department department;
    private int daysOff;

    public Staff(int id, String name, int age, double payRate, LocalDate startDate, Department department, int daysOff) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.payRate = payRate;
        this.startDate = startDate;
        this.department = department;
        this.daysOff = daysOff;

        // Tăng số lượng nhân viên cho bộ phận tương ứng
        this.department.increaseMember();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getPayRate() {
        return payRate;
    }

    public void setPayRate(double payRate) {
        this.payRate = payRate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getDaysOff() {
        return daysOff;
    }

    public void setDaysOff(int daysOff) {
        this.daysOff = daysOff;
    }

    // Hiển thị thông tin nhân viên với chiều rộng cột tên và cột bộ phận mặc định
    abstract void displayInformation();

    // Hiển thị thông tin nhân viên với chiều rộng cột tên và cột bộ phận có thể thay đổi
    abstract void displayInformation(int nameWidth, int deptWidth);
}