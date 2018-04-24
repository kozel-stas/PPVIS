package com.PPVIS.Conroller;

import com.PPVIS.model.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Controller {
    private static Data data;
    private WriterXML writerXML;
    private SAXReader saxReader;
    private Pattern FIOPattern = Pattern.compile("(^[A-Z]+[a-z]*$)|(^[А-Я]+[а-я]*$)");

    public Controller() {
        data = new Data();
    }

    public boolean addStudent(String name, String surname, String patronic, String groupText, List<Exam> exams) {
        int group;
        try {
            group = Integer.parseInt(groupText);
        } catch (NumberFormatException ex) {
            return false;
        }
        if (FIOPattern.matcher(name).find() && FIOPattern.matcher(surname).find() && FIOPattern.matcher(patronic).find()) {
            if (data.addStudent(new Student(name, surname, patronic, group, exams)))
                return true;
        }
        return false;
    }

    public boolean save(File file) {
        if (writerXML == null)
            writerXML = new WriterXML(data.getStudents());
        writerXML.setFile(file);
        try {
            writerXML.write();
            return true;
        } catch (TransformerException | ParserConfigurationException e) {
            return false;
        }
    }

    public boolean open(File file) {
        if (saxReader == null) saxReader = new SAXReader();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(file, saxReader);
            data.setStudents(saxReader.getStudents());
            return true;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Student> find(String surname, double top, double bottom) {
        List<Student> students = new ArrayList<>();
        for (Student student : data.getStudents()) {
            if (student.getSurname().equalsIgnoreCase(surname) && student.getAverageScore() > bottom && student.getAverageScore() < top)
                students.add(student);
        }
        return students;
    }

    public List<Student> find(String surname, int group) {
        List<Student> students = new ArrayList<>();
        for (Student student : data.getStudents()) {
            if (student.getSurname().equalsIgnoreCase(surname) && student.getGroup() == group)
                students.add(student);
        }
        return students;
    }

    public List<Student> find(String surname, String subj, int top, int bottom) {
        List<Student> students = new ArrayList<>();
        for (Student student : data.getStudents()) {
            if (student.getSurname().equalsIgnoreCase(surname)) {
                for (Exam exam : student.getExams())
                    if (exam.getNameExam().equals(subj) && exam.getMark() < top && exam.getMark() > bottom)
                        students.add(student);
            }
        }
        return students;
    }

    public void deleteStudent(Student student) {
        data.getStudents().remove(student);
    }

    public void deleteStudent(List<Student> students) {
        for (Student student : students)
            data.getStudents().remove(student);
    }

    public List<Student> getStudent(int page, int size) {
        List<Student> students = data.getStudents();
        if (page * size < students.size())
            return students.subList(page * size, (page + 1) * size > students.size() ? students.size() : (page + 1) * size);
        return new ArrayList<Student>();
    }
}
