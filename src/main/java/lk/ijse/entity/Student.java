package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "student_table")
public class Student {
    @Id
    private String studentId;
    private String name;
    private String address;
    private Long contact;
    private Date regDate;
    private  int someInt;

    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons = new ArrayList<>();


    public Student(String studentId, String name, String address, Long tel, java.sql.Date registrationDate) {
        this.studentId = studentId;
        this.name = name;
        this.address = address;
        this.contact = tel;
        this.regDate = registrationDate;
        this.someInt = 0;
        this.courses = new ArrayList<>();
        this.lessons = new ArrayList<>();
        this.payments = new ArrayList<>();
    }


    public Student(String studentId) {
        this.studentId = studentId;
        this.name = "";
        this.address = "";
        this.contact = 0L;
        this.regDate = new java.sql.Date(System.currentTimeMillis());
        this.someInt = 0;
        this.courses = new ArrayList<>();
        this.lessons = new ArrayList<>();
        this.payments = new ArrayList<>();
    }
}
