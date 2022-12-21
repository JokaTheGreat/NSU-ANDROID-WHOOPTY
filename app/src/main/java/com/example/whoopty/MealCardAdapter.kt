package com.example.whoopty

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MealCardAdapter(
    private val fragmentArray: List<Fragment>,
    private val activity: AppCompatActivity
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = fragmentArray.size

    override fun createFragment(position: Int): Fragment = fragmentArray[position]
    //TODO: мне не нравится, что createFragment не создает фрагмент, а просто возвращает
    // но если пробросить сюда данные и создавать фрагменты тут то не ясно как определить getItemCount()
}