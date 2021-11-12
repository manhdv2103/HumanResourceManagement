public class Department {
    private int id;
    private String name;
    private int numOfMembers;

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
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

    public int getNumOfMembers() {
        return numOfMembers;
    }

    public void setNumOfMembers(int numOfMembers) {
        this.numOfMembers = numOfMembers;
    }

    public void increaseMember() {
        numOfMembers++;
    }

    // Trả về thông tin bộ phận với độ rộng cột tên bộ phận mặc định
    public String toString() {
        return toString(40);
    }

    // Trả về thông tin bộ phận với độ rộng cột tên bộ phận có thể thay đổi
    public String toString(int nameWidth) {
        return String.format("| %04d | %-" + nameWidth + "s | %17d |", id, name, numOfMembers);
    }
}