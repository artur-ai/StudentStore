package com.maiboroda.StudentFactory;

public class Student {
    public String firstName;
    public String lastName;
    public int course;
    public String birthday;

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
