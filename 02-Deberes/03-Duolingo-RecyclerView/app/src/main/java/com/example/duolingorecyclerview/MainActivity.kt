package com.example.duolingorecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewCourses: RecyclerView
    private lateinit var recyclerViewLessons: RecyclerView
    private lateinit var courseAdapter: CourseAdapter
    private lateinit var lessonAdapter: LessonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewCourses = findViewById(R.id.recyclerViewCourses)
        recyclerViewLessons = findViewById(R.id.recyclerViewLessons)

        val courses = listOf(
            Course("Inglés", "Aprende inglés desde cero", listOf(
                Lesson("Lesson 1: Greetings"),
                Lesson("Lesson 2: Verbs"),
                Lesson("Lesson 3: Numbers")
            )),
            Course("Francés", "Aprende francés fácilmente", listOf(
                Lesson("Leçon 1: La nourriture"),
                Lesson("Leçon 2: Nature"),
                Lesson("Leçon 3: Voyage")
            ))
        )

        courseAdapter = CourseAdapter(courses) { course ->
            updateLessons(course.lessons)
        }

        recyclerViewCourses.layoutManager = LinearLayoutManager(this)
        recyclerViewCourses.adapter = courseAdapter

        // Initially set lessons of the first course
        if (courses.isNotEmpty()) {
            updateLessons(courses[0].lessons)
        }
    }

    private fun updateLessons(lessons: List<Lesson>) {
        lessonAdapter = LessonAdapter(lessons)
        recyclerViewLessons.layoutManager = LinearLayoutManager(this)
        recyclerViewLessons.adapter = lessonAdapter
    }
}

