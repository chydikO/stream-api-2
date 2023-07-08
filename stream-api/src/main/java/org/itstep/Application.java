package org.itstep;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

public class Application {
    private final static Logger logger = Logger.getLogger();

    public static void main(String[] args) {
        logger.info("Start program");
        // Terminal method
        // Intermediate operation
        Sun sun = Sun.getInstance(); //new Sun();
        Sun sun1 = Sun.getInstance(); // new Sun();
        System.out.println("(sun == sun1) = " + (sun == sun1));

        if(1 == 1) {
            logger.error("Something wrong");
        }

        Student.StudentBuilder builder = Student.builder();
        builder.firstName("Вася");
        builder.lastName("Пупкін");
        builder.datOfBirth(LocalDate.of(2000, Month.APRIL, 1));
        builder.group("Java20");
        Student student = builder.build();

        System.out.println(student);

        student = Student.builder()
                .firstName("Маша")
                .lastName("Ефросініна")
                .datOfBirth(LocalDate.of(1991, Month.APRIL, 1))
                .group("Java20")
                .build();
        System.out.println(student);

        List<Integer> list = new ListBuilder<Integer>()
                        .add(1)
                        .add(2)
                        .add(3)
                        .build();
        System.out.println(list); // [1, 2, 3]

        logger.info("End program");
    }

    static class ListBuilder<T> {
        private final List<T> list = new ArrayList<>();
        ListBuilder<T> add(T item) {
            list.add(item);
            return this;
        }
        List<T> build() {
            return list;
        }
    }
}

class Sun {
    private static Sun instance;
    private List<String> planets = new ArrayList<>();

    private Sun() {
    }

    public static Sun getInstance() {
        if (instance == null) {
            instance = new Sun();
        }
        return instance;
    }
}

class Logger {
    private static Logger logger;
    private Logger(){}
    private final static String MESSAGE_PATTERN = "%s [%s] - %s%n";

    public static Logger getLogger() {
        return logger == null ? new Logger() : logger;
    }

    public void info(String message) {
        System.out.printf(MESSAGE_PATTERN, new Date(), "INFO", message);
    }

    public void error(String message) {
        System.err.printf(MESSAGE_PATTERN, new Date(), "ERROR", message);
    }

    public void debug(String message) {
        System.out.printf(MESSAGE_PATTERN, new Date(), "DEBUG", message);
    }
}

class Student {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String group;

    private Student() {

    }

    public static StudentBuilder builder() {
        return new StudentBuilder();
    }

    public static class StudentBuilder {
        private final Student student;

        private StudentBuilder() {
            student = new Student();
        }

        public StudentBuilder firstName(String firstName) {
            student.firstName = firstName;
            return this;
        }

        public StudentBuilder lastName(String lastName) {
            student.lastName = lastName;
            return this;
        }

        public StudentBuilder datOfBirth(LocalDate dateOfBirth) {
            student.dateOfBirth = dateOfBirth;
            return this;
        }

        public StudentBuilder group(String group) {
            student.group = group;
            return this;
        }

        public Student build() {
            return student;
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", group='" + group + '\'' +
                '}';
    }
}
