import java.time.LocalDate;

public class Athlete {
    private long id;
    private String name;
    private String surname;
    private String nacionality;
    private LocalDate birthday;
    public Athlete() {
    }
    public Athlete(String name, String surname, String nacionality, LocalDate birthday) {
        this.name = name;
        this.surname = surname;
        this.nacionality = nacionality;
        this.birthday = birthday;
    }
    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getSurname() {return surname;}
    public void setSurname(String surname) {this.surname = surname;}
    public String getNacionality() {return nacionality;}
    public void setNacionality(String nacionality) {this.nacionality = nacionality;}
    public LocalDate getBirthday() {return birthday;}
    public void setBirthday(LocalDate birthday) {this.birthday = birthday;}
    @Override
    public String toString() {
        return "Athlete{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", nacionality='" + nacionality + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
