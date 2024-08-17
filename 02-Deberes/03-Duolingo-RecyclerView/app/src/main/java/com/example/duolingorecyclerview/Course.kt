package com.example.duolingorecyclerview

data class Course(
    val name: String,
    val description: String,
    val lessons: List<Lesson>
)