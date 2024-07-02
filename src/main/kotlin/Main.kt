package com.tasker

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.http.HttpStatus
import io.javalin.http.NotFoundResponse
import io.javalin.http.bodyAsClass
import io.javalin.http.pathParamAsClass

fun main(){

    val studentDao = StudentDao()

    val app = Javalin.create{
        it.router.apiBuilder{
            get("/") {it.redirect("/students")}

            get("/students") {ctx->
                ctx.json(studentDao.students)
            }

            get("/students/{student-id}") { ctx->
                 val studentId = ctx.pathParamAsClass<Int>("student-id").get()
                val student = studentDao.findById(studentId) ?: run {
                    ctx.status(404)
                    ctx.json(mapOf("error" to "Student not found"))
                    return@get
                }
                ctx.json(student)
            }

            get("/students/name/{student-name}"){ ctx ->
                val studentName = ctx.pathParam("student-name")
                val sName = studentDao.findByStudentName(studentName)
                if (sName != null) {
                    ctx.json(sName)
                } else {
                    ctx.status(404)
                    ctx.json(mapOf("error" to "Student not found with name: $studentName"))
                }
            }

            post("/students") { ctx ->
                val student = ctx.bodyAsClass<Student>()
                studentDao.save(studentName = student.studentName, studentClass = student.studentClass)
                ctx.status(201)
            }

            patch("/students/{student-id}") { ctx ->
                val studentId = ctx.pathParamAsClass<Int>("student-id").get()
                val student = ctx.bodyAsClass<Student>()
                studentDao.update(studentId = studentId, student = student)
                ctx.status(204)
            }

            delete("/students/{student-id}") { ctx ->
                val studentId = ctx.pathParamAsClass<Int>("student-id").get()
                studentDao.delete(studentId)
                ctx.status(204)
            }


        }
    }.apply {
        exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
        error(HttpStatus.NOT_FOUND) { ctx -> ctx.json("not found") }
    }.start()

}