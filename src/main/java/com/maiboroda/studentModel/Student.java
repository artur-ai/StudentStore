package com.maiboroda.studentModel;

public class Student {
    private String firstName;
    private String lastName;
    private int course;
    private String birthday;

    public Student (String firstName, String lastName, int course, String birthday) {
        this.birthday = birthday;
        this.course = course;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getCourse() {
        return course;
    }

    public String getBirthday() {
        return  birthday;
    }

}
