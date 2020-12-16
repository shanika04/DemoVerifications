package Deserialization;

import java.io.*;
import java.util.*;
import java.util.Set;

public class Test1 {

    private Set<String> wL =
            new HashSet<>(
                    Arrays.asList(
                            "Deserialization.Student",
                            "Deserialization.MathStudent",
                            "Deserialization.GradeSheet",
                            "java.util.Set"));

    public void test(FileInputStream fileIS) throws IOException, ClassNotFoundException {

        ObjectInputStream in = new BetterObjectInputStream(fileIS, wL);
        Student student = (Student) in.readObject();
        MathStudent mathStudent = (MathStudent) student;
        in.close();
    }

    private static class BetterObjectInputStream extends ObjectInputStream {
        public Set<String> cWL;
        private Set<String> sPWL = new HashSet<>(Arrays.asList("java.lang"));

        public BetterObjectInputStream(InputStream inputStream, Set<String> cWL)
                throws IOException {
            super(inputStream);
            this.cWL = cWL;
        }

        @Override
        protected Class<?> resolveClass(ObjectStreamClass cls)
                throws IOException, ClassNotFoundException {
            String className = cls.getName();
            String subPackageName = className.substring(0, className.lastIndexOf("."));
            if (cWL.contains(className) || sPWL.contains(subPackageName)) {
                return super.resolveClass(cls);
            } else {
                throw new java.lang.RuntimeException("class " + className + " is not white listed");
            }
        }
    }
}

class Student {
    private int departmentID;

    public Student(int value) {
        this.departmentID = value;
    }
}

class MathStudent extends Student {
    private GradeSheet grades = null;

    public MathStudent(int value) {
        super(value);
        this.grades = new GradeSheet(value);
    }
}

class GradeSheet {
    private int avgGrade;
    private Set<String> grades = null;

    public GradeSheet(int value) {
        this.avgGrade = value;
    }
}
