package com.PPVIS;

import com.PPVIS.Conroller.Controller;
import com.PPVIS.model.Exam;
import com.PPVIS.model.Student;
import com.PPVIS.model.WriterXML;
import com.PPVIS.view.MainDisplay;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.widgets.TableComposite;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        genListStudent(60);
        new MainDisplay();
    }

    public static void genListStudent(int len) {
        List<Student> list = new ArrayList(len);
        for (int i = 0; i < len; i++) {
            list.add(genStudent());
        }
        WriterXML writerXML = new WriterXML(list);
        writerXML.setFile(new File("test"+len+".xml"));
        try {
            writerXML.write();
        } catch (TransformerException | ParserConfigurationException e) {
        }
    }

    public static Student genStudent() {
        Random random = new Random();
        List<String> list = new ArrayList(5);
        List<Exam> exams = new ArrayList<>(5);
        list.add("ППВИС");
        list.add("МОИС");
        list.add("ОТС");
        list.add("МРЗ");
        list.add("СИМОИБ");
        for (String str : list) {
            exams.add(new Exam(str, random.nextInt(7) + 4));
        }
        return new Student(genName(), genName(), genName(), Integer.parseInt(genGroup()), exams);
    }

    public static String genGroup() {
        Random random = new Random(System.currentTimeMillis());
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            name.append(random.nextInt(9) + 1);
        }
        return name.toString();
    }

    public static String genName() {
        Random random = new Random();
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < random.nextInt(10) + 4; i++) {
            name.append((char) ((97) + random.nextInt(26)));
        }
        return name.toString();
    }
}
