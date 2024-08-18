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
                Lesson("Lesson 3: Numbers"),
                Lesson("Lesson 4: Food"),
                Lesson("Lesson 5: Animals")
            )),
            Course("Francés", "Aprende francés fácilmente", listOf(
                Lesson("Leçon 1: La nourriture"),
                Lesson("Leçon 2: Nature"),
                Lesson("Leçon 3: Voyage"),
                Lesson("Leçon 4: Verbes"),
                Lesson("Leçon 5: Famille")
            )),
            Course("Español", "Aprende español desde cero", listOf(
                Lesson("Lección 1: Saludos"),
                Lesson("Lección 2: Verbos"),
                Lesson("Lección 3: Números"),
                Lesson("Lección 4: Comida"),
                Lesson("Lección 5: Animales")
            )),
            Course("Alemán", "Domina el alemán rápidamente", listOf(
                Lesson("Lektion 1: Begrüßungen"),
                Lesson("Lektion 2: Verben"),
                Lesson("Lektion 3: Zahlen"),
                Lesson("Lektion 4: Essen"),
                Lesson("Lektion 5: Tiere")
            )),
            Course("Italiano", "Aprende italiano paso a paso", listOf(
                Lesson("Lezione 1: Saluti"),
                Lesson("Lezione 2: Verbi"),
                Lesson("Lezione 3: Numeri"),
                Lesson("Lezione 4: Cibo"),
                Lesson("Lezione 5: Animali")
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
