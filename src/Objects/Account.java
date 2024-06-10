package Objects;

public class Account {
    private String FullName;
    private int Age;
    private String PhoneNumber;
    private String Email;
    private String Password;

    public Account(String fullName, int age, String phoneNumber, String email, String password) {
        FullName = fullName;
        Age = age;
        PhoneNumber = phoneNumber;
        Email = email;
        Password = password;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
