import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Pattern;

public class HumanResources {
    // Định dạng ngày chuẩn của chương trình
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static Scanner input = new Scanner(System.in);
    public static String[] managerPositions = { "Business Leader", "Project Leader", "Technical Leader" };
    public static ArrayList<Staff> staffList = new ArrayList<>();
    public static ArrayList<Department> departmentList = new ArrayList<>();

    public static void main(String[] args) {
        // Tạo sẵn một số bộ phận
        departmentList.add(new Department(departmentList.size() + 1, "Marketing"));
        departmentList.add(new Department(departmentList.size() + 1, "Human Resources"));
        departmentList.add(new Department(departmentList.size() + 1, "IT"));
        departmentList.add(new Department(departmentList.size() + 1, "Finance"));
        departmentList.add(new Department(departmentList.size() + 1, "Accounting"));

        // Tạo sẵn một số nhân viên
        createStaff(50);

        // Chương trình chính
        displayTitle();
        do {
            // Điều khiển luồng chính của chương trình theo lựa chọn của người dùng
            displayMenu();
            switch (getChoice(7)) {
            case 1 -> {
                displayStaffList("Company's Staff list", staffList);
                pause();
            }

            case 2 -> {
                displayDepartmentList();
                pause();
            }

            case 3 -> {
                do {
                    displayStaffListByDepartment();
                } while (yesNo("Do you want to view another Department's Staff list?"));
            }

            case 4 -> {
                do {
                    addStaff();
                } while (yesNo("Do you want to add another Staff member?"));
            }

            case 5 -> {
                do {
                    searchStaff();
                } while (yesNo("Do you want to search for other Staff members?"));
            }

            case 6 -> {
                displaySortedPayroll();
                pause();
            }

            case 7 -> {
                System.out.print("\nProgram Exiting...");
                System.exit(0);
            }
            }
        } while (true); // Cho phép người dùng chọn lại chức năng
    }

    // Hiển thị tên chương trình
    public static void displayTitle() {
        System.out.println("\nHUMAN RESOURCE MANAGEMENT PROGRAM");
    }

    // Hiển thị menu gồm các chức năng của chương trình
    public static void displayMenu() {
        System.out.println("=--------------------------------=\n");
        displayChoiceList("Select one of the following actions:",
                new String[] { "View Staff List", "View Department List", "View Staff List by Department",
                        "Add New Staff Members", "Search for Staff Members", "View Staff List Sorted by Salary",
                        "Exit Program" });
    }

    // Hiển thị danh sách nhân viên được truyền vào
    public static void displayStaffList(String tableTitle, ArrayList<Staff> list) {
        System.out.println();

        // Lấy độ dài tên và bộ phận dài nhất làm độ rộng cột tên và cột bộ phận
        // Nếu tất cả tên đều ngắn hơn 28 thì dùng 28 làm độ rộng cột tên
        // Nếu tất cả tên bộ phận đều ngắn hơn 16 thì dùng 16 làm độ rộng cột bộ phận
        int nameWidth = 28;
        int deptWidth = 16;
        for (Staff staff : list) {
            if (staff.getName().length() > nameWidth) {
                nameWidth = staff.getName().length();
            }

            if (staff.getDepartment().getName().length() > deptWidth) {
                deptWidth = staff.getDepartment().getName().length();
            }
        }

        // Tạo đường kẻ bảng ngang
        String tableLine = String.format("%1$s   %1$4s   %1$" + nameWidth + "s   %1$3s   %1$16s   %1$" + deptWidth + "s"
                + "   %1$10s   %1$14s   %1$8s   %1$8s   %1$21s", "+").replace(" ", "-");

        // Hiển thị tiêu đề bảng
        // 115 là độ rộng của bảng trừ cột tên và cột bộ phận
        System.out.println(centerString(115 + nameWidth + deptWidth, tableTitle.toUpperCase()));

        // Hiển thị bảng với thông tin của các nhân viên trong danh sách truyền vào
        System.out.println(tableLine);
        System.out.printf(
                "| %4s | %-" + nameWidth + "s | %3s | %-16s | %-" + deptWidth
                        + "s | %10s | %14s | %8s | %8s | %21s |%n",
                "ID", "Name", "Age", "Position", "Department", "Start Date", "Overtime Hours", "Days Off", "Pay Rate",
                "Salary (VND)");
        System.out.println(tableLine);

        for (Staff staff : list) {
            staff.displayInformation(nameWidth, deptWidth);
        }

        System.out.println(tableLine);
    }

    // Hiển thị thông tin các bộ phận trong công ty
    public static void displayDepartmentList() {
        System.out.println();

        // Lấy độ dài tên bộ phận dài nhất làm độ rộng cột tên bộ phận
        // Nếu tất cả tên đều ngắn hơn 40 thì dùng 40 làm độ rộng cột
        int nameWidth = 40;
        for (Department department : departmentList) {
            if (department.getName().length() > nameWidth) {
                nameWidth = department.getName().length();
            }
        }

        // Tạo đường kẻ bảng ngang
        String tableLine = String.format("%1$s   %1$4s   %1$" + nameWidth + "s   %1$17s", "+").replace(" ", "-");

        // Hiển thị tiêu đề bảng
        // 31 là độ rộng của bảng trừ cột tên bộ phận
        System.out.println(centerString(31 + nameWidth, "Company's Departments list".toUpperCase()));

        // Hiển thị bảng với thông tin của các bộ phận trong danh sách
        System.out.println(tableLine);
        System.out.printf("| %4s | %-" + nameWidth + "s | %17s |%n", "ID", "Department Name", "Number of Members");
        System.out.println(tableLine);

        for (Department department : departmentList) {
            System.out.println(department.toString(nameWidth));
        }

        System.out.println(tableLine);
    }

    // Hiển thị các nhân viên theo từng bộ phận
    public static void displayStaffListByDepartment() {
        System.out.println();

        // Lấy lựa chọn bộ phận muốn xem
        displayChoiceList("Select Department to view:", departmentNamesArray());
        Department dept = departmentList.get(getChoice(departmentList.size()) - 1);

        // Lọc lấy những nhân viên thuộc bộ phận được chọn
        ArrayList<Staff> resultList = new ArrayList<>();
        staffList.forEach(staff -> {
            if (staff.getDepartment().equals(dept)) {
                resultList.add(staff);
            }
        });

        // Hiển thị thông tin các nhân viên thuộc bộ phận được chọn
        displayStaffList(String.format("%04d - %s Department", dept.getId(), dept.getName()), resultList);
        System.out.println("Total members: " + dept.getNumOfMembers());
    }

    // Thêm nhân viên mới vào công ty
    public static void addStaff() {
        System.out.println();

        // Lấy những thông tin cần thiết về nhân viên mới từ người dùng
        displayChoiceList("Type of Staff member: ", new String[] { "Normal employee", "Manager" });
        int staffType = getChoice(2);

        String name = getInput("Name: ", ".*", "");

        int age = Integer
                .parseInt(getInput("Age: ", "(?=.*[1-9])[+]?0*(1?\\d{0,2}|200)", "an integer between 1 and 200"));

        // Hiển thị danh sách lựa chọn bộ phận cùng với lựa chọn thêm bộ phận mới
        displayChoiceList("Department: ", departmentNamesArray());
        System.out.printf("%4d. %s%n", departmentList.size() + 1, "Add new Department...");

        Department department;
        int deptChoice = getChoice(departmentList.size() + 1);
        if (deptChoice == departmentList.size() + 1) {
            // Nếu người dùng chọn thêm bộ phận thì lấy tên bộ phận mới từ người dùng
            department = new Department(departmentList.size() + 1, getInput("New Department name: ", ".*", ""));
            departmentList.add(department);
        } else {
            department = departmentList.get(deptChoice - 1);
        }

        LocalDate startDate = LocalDate.parse(
                getInput("Start date (dd/mm/yyyy): ", "(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[0-2])[/]((?!0000)\\d{4})",
                        "a valid date in the format dd/mm/yyyy"),
                DATE_FORMAT);

        double payRate = Double.parseDouble(getInput("Pay rate: ", "[+]?0*(\\d{1,4}(\\.\\d*)?|\\.\\d+|10000(\\.0*)?)",
                "a number between 0.0 and 10000.0"));

        int daysOff = Integer
                .parseInt(getInput("Days off: ", "[+]?0*(\\d{1,7}|10000000)", "an integer between 0 and 10000000"));

        switch (staffType) {
        case 1 -> {
            double overtimeHours = Double
                    .parseDouble(getInput("Overtime hours: ", "[+]?0*(\\d{1,10}(\\.\\d*)?|\\.\\d+|10000000000(\\.0*)?)",
                            "a number between 0.0 and 10000000000.0"));

            staffList.add(new Employee(staffList.size() + 1, name, age, payRate, startDate, department, daysOff,
                    overtimeHours));
        }

        case 2 -> {
            displayChoiceList("Position: ", managerPositions);
            String position = managerPositions[getChoice(3) - 1];

            staffList.add(
                    new Manager(staffList.size() + 1, name, age, payRate, startDate, department, daysOff, position));
        }
        }

        // Hiển thị thông tin của nhân viên mới
        displayStaffList("New Staff member information",
                new ArrayList<>(staffList.subList(staffList.size() - 1, staffList.size())));
    }

    // Tìm kiếm thông tin nhân viên
    public static void searchStaff() {
        System.out.println();

        // Lấy lựa chọn tìm kiếm bằng tên hay mã nhân viên
        displayChoiceList("Search Staff members by:", new String[] { "Name", "Id" });

        ArrayList<Staff> resultList = new ArrayList<>();
        String searchTarget = "";
        switch (getChoice(2)) {
        // Tìm kiếm bằng tên
        case 1 -> {
            String searchPhrase = getInput("Enter the name you want to search: ", ".*", "").toLowerCase()
                    .replaceAll("\\s+", "|");

            searchTarget = "Staff members whose name contains \"" + searchPhrase.replace("|", "\"/\"") + "\"";

            // Lọc lấy những nhân viên tên có chứa các từ trong cụm từ được tìm kiếm
            staffList.forEach(staff -> {
                if (staff.getName().toLowerCase().matches(".*(" + searchPhrase + ").*"))
                    resultList.add(staff);
            });

            // Sắp xếp nhân viên theo mức độ khớp với cụm từ được tìm kiếm
            resultList.sort((staff1, staff2) -> {
                String name1 = staff1.getName().toLowerCase();
                String name2 = staff2.getName().toLowerCase();
                Pattern startWordPattern = Pattern.compile("\\b(" + searchPhrase + ")");
                Pattern wholeWordPattern = Pattern.compile("\\b(" + searchPhrase + ")\\b");
                int result = 0;

                // Ưu tiên tên bắt đầu bằng những từ được tìm kiếm
                result -= startWordPattern.matcher(name1).results().count();
                result += startWordPattern.matcher(name2).results().count();

                // Ưu tiên hơn tên khớp hoàn toàn với những từ được tìm kiếm
                result -= wholeWordPattern.matcher(name1).results().count();
                result += wholeWordPattern.matcher(name2).results().count();

                return result;
            });
        }

        // Tìm kiếm bằng mã nhân viên
        case 2 -> {
            int searchId = Integer
                    .parseInt(getInput("Enter the id you want to search: ", "(?=.*[1-9])\\d+", "a positive integer"));

            searchTarget = String.format("Staff members with id %04d", searchId);

            // Lấy nhân viên với mã nhận được từ người dùng
            if (searchId <= staffList.size()) {
                resultList.addAll(new ArrayList<>(staffList.subList(searchId - 1, searchId)));
            }
        }
        }

        // Hiển thị danh sách nhân viên tìm được
        // Nếu không tìm thấy nhân viên nào thì báo lại cho người dùng
        if (resultList.isEmpty()) {
            System.out.println(searchTarget + " do not exist.");
        } else {
            displayStaffList(searchTarget, resultList);
        }
    }

    // Hiển thị bảng lương của nhân viên toàn công ty
    public static void displaySortedPayroll() {
        System.out.println();

        // Lấy lựa chọn sắp xếp theo thứ tự tăng dần hay giảm dần
        displayChoiceList("View list sorted in:", new String[] { "Ascending order", "Descending order" });

        // Hiển thị danh sách nhân viên theo thứ tự được chọn
        ArrayList<Staff> resultList = new ArrayList<>(staffList);
        switch (getChoice(2)) {
        case 1 -> {
            resultList.sort(Comparator.comparingDouble(ICalculator::calculateSalary));
            displayStaffList("Staff salary in ascending order", resultList);
        }

        case 2 -> {
            resultList.sort(Comparator.comparingDouble(ICalculator::calculateSalary).reversed());
            displayStaffList("Staff salary in descending order", resultList);
        }
        }
    }

    // Hiển thị danh sách lựa chọn
    public static void displayChoiceList(String displayText, String[] list) {
        System.out.println(displayText);
        for (int i = 0; i < list.length; i++) {
            System.out.printf("%4d. %s%n", i + 1, list[i]);
        }
    }

    // Lấy lựa chọn từ người dùng
    public static int getChoice(int numOfChoices) {
        return Integer.parseInt(getInput("Enter your choice: ", "0*[1-" + numOfChoices + "]",
                "a number between 1 and " + numOfChoices));
    }

    // Lấy đầu vào từ người dùng theo mẫu xác định
    public static String getInput(String displayText, String pattern, String suggestion) {
        while (true) {
            System.out.print(displayText);

            String result = input.nextLine().trim();

            if (result.isEmpty()) {
                System.out.println("Input cannot be empty!");
            } else if (!result.matches(pattern)) {
                System.out.println("Input must be " + suggestion + "!");
            } else {
                return result;
            }
        }
    }

    // Xác nhận người dùng trả lời có hay không
    public static boolean yesNo(String displayText) {
        while (true) {
            System.out.print(displayText + " [y/n] ");

            String yesNo = input.nextLine().trim();
            if ((yesNo.equalsIgnoreCase("yes")) || (yesNo.equalsIgnoreCase("y"))) {
                return true;
            } else if ((yesNo.equalsIgnoreCase("no")) || (yesNo.equalsIgnoreCase("n"))) {
                return false;
            }

            System.out.println("Input must be \"yes\", \"y\" or \"no\", \"n\"!");
        }
    }

    // Dừng chương trình tạm thời cho người dùng xem thông tin
    public static void pause() {
        System.out.print("Press Enter to continue ");
        input.nextLine();
    }

    // Trả về mảng tên các bộ phận
    public static String[] departmentNamesArray() {
        String[] deptNames = new String[departmentList.size()];
        for (int i = 0; i < departmentList.size(); i++) {
            deptNames[i] = departmentList.get(i).getName();
        }

        return deptNames;
    }

    // Trả về chuỗi đã được căn giữa
    public static String centerString(int width, String str) {
        return String.format("%-" + width + "s",
                String.format("%" + (str.length() + (width - str.length()) / 2) + "s", str));
    }

    // Tạo nhân viên ngẫu nhiên với số lượng xác định
    public static void createStaff(int numOfStaff) {
        String[] lastName = { "Tran ", "Nguyen ", "Vu ", "Le ", "Ly ", "Bui " };
        String[] middleName = { "Van ", "Thi " };

        for (int i = 0; i < numOfStaff; i++) {
            // Tạo các thuộc tính ngẫu nhiên cho nhân viên
            String name = lastName[(int) randNum(0, 5)] + middleName[(int) randNum(0, 1)] + (char) randNum(65, 90);
            int age = (int) randNum(18, 60);
            double payRate = Math.random() + 1;
            LocalDate startDate = LocalDate.of((int) randNum(2000, 2021), (int) randNum(1, 12), (int) randNum(1, 28));
            Department department = departmentList.get((int) randNum(0, departmentList.size() - 1));
            int daysOff = (int) randNum(0, 50);

            if ((int) randNum(0, 1) == 0) {
                staffList.add(new Employee(staffList.size() + 1, name, age, payRate, startDate, department, daysOff,
                        Math.floor(randNum(0, 200)) / 4));
            } else {
                staffList.add(new Manager(staffList.size() + 1, name, age, payRate, startDate, department, daysOff,
                        managerPositions[(int) randNum(0, 2)]));
            }
        }
    }

    // Trả về một số ngẫu nhiên có phần nguyên trong khoảng xác định
    public static double randNum(int min, int max) {
        return ((Math.random() * (max - min + 1)) + min);
    }
}