package com.PPVIS.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class WriterXML {
    private File file;
    private Document document;
    private List<Student> students;

    public WriterXML(File file, List<Student> students) {
        this.file = file;
        this.students = students;
    }

    public WriterXML(List<Student> students) {
        this.students = students;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void write() throws TransformerException, ParserConfigurationException {
        if (file != null && students != null) {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element elementList = document.createElement("listStudent");
            for (Student studentIter : students) {
                Element elementStudent = document.createElement("student");
                Element elementFIO = document.createElement("FIO");

                Element elementName = document.createElement("name");
                elementName.setTextContent(studentIter.getName());
                elementFIO.appendChild(elementName);

                Element elementSurname = document.createElement("surname");
                elementSurname.setTextContent(studentIter.getSurname());
                elementFIO.appendChild(elementSurname);

                Element elementPatronymic = document.createElement("patronymic");
                elementPatronymic.setTextContent(studentIter.getPatronymic());
                elementFIO.appendChild(elementPatronymic);

                elementStudent.appendChild(elementFIO);

                Element elementGroup = document.createElement("group");
                elementGroup.setTextContent(String.valueOf(studentIter.getGroup()));
                elementStudent.appendChild(elementGroup);

                Element elementExams = document.createElement("exams");
                int count = 0;
                for (Exam examIter : studentIter.getExams()) {
                    Element elementExam = document.createElement("exam");
                    elementExam.setAttribute("id", String.valueOf(count++));

                    Element elementNameSubj = document.createElement("nameSubj");
                    elementNameSubj.setTextContent(examIter.getNameExam());
                    elementExam.appendChild(elementNameSubj);

                    Element elementMark = document.createElement("mark");
                    elementMark.setTextContent(String.valueOf(examIter.getMark()));
                    elementExam.appendChild(elementMark);

                    elementExams.appendChild(elementExam);
                }
                elementStudent.appendChild(elementExams);
                elementList.appendChild(elementStudent);
            }
            document.appendChild(elementList);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(file);
            transformer.transform(domSource, streamResult);

        }
    }
}
