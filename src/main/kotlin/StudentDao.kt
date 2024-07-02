package com.tasker

import java.util.concurrent.atomic.AtomicInteger

class StudentDao {
    val students = hashMapOf(
        0 to Student(studentName = "John Onyango", studentId = 1, studentClass = 1),
        1 to Student(studentName = "Mary Jones", studentId = 2, studentClass = 2),
        3 to Student(studentName = "Mark Jones", studentId = 3, studentClass = 3),
        4 to Student(studentName = "Tony Jones", studentId = 4, studentClass = 4),
        5 to Student(studentName = "Zack Jones", studentId = 5, studentClass = 5)
    )

    var lastid: AtomicInteger = AtomicInteger(students.size - 1)

    fun save(studentName: String,  studentClass: Int) {
        var studentId = lastid.incrementAndGet()
        students[studentId] = Student(studentName = studentName, studentId = studentId, studentClass = studentClass)
    }

    fun findById(studentId: Int): Student?{
        return students[studentId]
    }

    fun findByStudentName(studentName: String): Student?{
        return students.values.find { it.studentName == studentName }
    }

    fun update(studentId: Int, student: Student){
        students[studentId] = Student(studentName = student.studentName, student.studentId, student.studentClass)
    }

    fun delete(studentId: Int){
        students.remove(studentId)
    }
}