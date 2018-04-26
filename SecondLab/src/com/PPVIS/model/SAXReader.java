package com.PPVIS.model;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class SAXReader extends DefaultHandler {
    private List<Student> students;
    private Student student;
    private List<Exam> exams;
    private Exam exam;
    private Element thisElem;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("student")) {
            student = new Student();
            thisElem = Element.student;
            return;
        }
        if (qName.equals("mark")) {
            thisElem = Element.mark;
            return;
        }
        if (qName.equals("nameSubj")) {
            thisElem = Element.nameSubj;
            return;
        }
        if (qName.equals("name")) {
            thisElem = Element.name;
            return;
        }
        if (qName.equals("surname")) {
            thisElem = Element.surname;
            return;
        }
        if (qName.equals("patronymic")) {
            thisElem = Element.patronymic;
            return;
        }
        if (qName.equals("group")) {
            thisElem = Element.group;
            return;
        }
        if (qName.equals("FIO")) {
            thisElem = Element.FIO;
            return;
        }
        if (qName.equals("exam")) {
            exam = new Exam();
            thisElem = Element.exam;
            return;
        }
        if (qName.equals("exams")) {
            exams = new ArrayList<>();
            thisElem = Element.exams;
            return;
        }
        if (qName.equals("listStudent")) {
            students = new ArrayList<>();
            thisElem = Element.listStudent;
            return;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String str = new String(ch, start, length).trim();
        if ("".equals(str)) return;
        if (thisElem == Element.mark) {
            exam.setMark(Integer.parseInt(str));
            return;
        }
        if (thisElem == Element.nameSubj) {
            exam.setNameExam(str);
            return;
        }
        if (thisElem == Element.name) {
            student.setName(str);
            return;
        }
        if (thisElem == Element.surname) {
            student.setSurname(str);
            return;
        }
        if (thisElem == Element.patronymic) {
            student.setPatronymic(str);
            return;
        }
        if (thisElem == Element.group) {
            student.setGroup(Integer.parseInt(str));
            return;
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if (qName.equals("student")) {
            students.add(student);
            student = null;
            return;
        }
        if (qName.equals("exam")) {
            exams.add(exam);
            exam = null;
            return;
        }
        if (qName.equals("exams")) {
            student.setExams(exams);
            exams = null;
            return;
        }
    }

    public List<Student> getStudents() {
        List list = students;
        students = null;
        return list;
    }
}

enum Element {
    student, exams, listStudent, exam, group, mark, name, surname, patronymic, FIO, nameSubj;
}
