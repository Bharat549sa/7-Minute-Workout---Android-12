package com.example.a7minuiteworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
//import com.example.a7minuiteworkout.databinding.ItemExcerciseStatusBinding
import com.example.a7minuiteworkout.databinding.ItemExerciseStatusBinding
class ExerciseStatusAdapter(val items:ArrayList<ExerciseModel>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    /**
     * Inflates the item view which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemExerciseStatusBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

//    RecyclerView.Adapter<ExerciseStatusAdpater.ViewHolder>{
//
//        class ViewHolder(binding: ItemExerciseStatusBinding):
//            RecyclerView.ViewHolder(binding.root)
//    val tvItem = binding.tvItem
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ViewHolder {
//
//
//        return ViewHolder(ItemexerciseStatusBinding.inflate(
//            LayoutInflater.from(parent.context), parent, false))
//
//    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel = items[position]

        holder.tvItem.text = model.getId().toString()
        when{
            model.getIsSelected() -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(
                        holder.itemView.context,
                        R.drawable.item_cicular_thin_color_accent_border
                )
                holder.tvItem.setTextColor(Color.parseColor("#21212"))
            }


    model.getIsCompleted() ->{


        holder.tvItem.background =
            ContextCompat.getDrawable(holder.itemView.context,
                R.drawable.item_cicular_thin_color_accent_background
            )
            holder.tvItem.setTextColor(Color.parseColor("#FFFFF"))
        }
            else ->{

                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.item_cicular_color_grey_background
                )
                holder.tvItem.setTextColor(Color.parseColor("#21212"))
            }            }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(binding: ItemExerciseStatusBinding) : RecyclerView.ViewHolder(binding.root) {
        // Holds the TextView that will add each item to
        val tvItem = binding.tvItem
    }
}