package com.example.duolingorecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LessonAdapter(private val lessonList: List<Lesson>) :
    RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {

    class LessonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvLessonName: TextView = view.findViewById(R.id.tvLessonName)
        val lessonIcon: ImageView = view.findViewById(R.id.lessonIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lesson, parent, false)
        return LessonViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val lesson = lessonList[position]
        holder.tvLessonName.text = lesson.name
        holder.lessonIcon.setImageResource(R.drawable.ic_lesson_icon) // Asigna un ícono de lección
    }

    override fun getItemCount(): Int = lessonList.size
}

