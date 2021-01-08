package com.example.deindealtechnicaltest.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deindealtechnicaltest.R
import com.example.deindealtechnicaltest.backend.FiltersObject
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.filter_element.view.*

class FiltersAdapters(
    private val filtersList: List<FiltersObject>,
    private val context: Context,
    private val onClick: (FiltersObject) -> Unit

) : RecyclerView.Adapter<FiltersAdapters.FiltersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltersViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.filter_element, parent, false)
        return FiltersViewHolder(view)

    }

    override fun getItemCount(): Int = filtersList.size

    override fun onBindViewHolder(holder: FiltersViewHolder, position: Int) {
        holder.bind(filtersList[position],context, onClick )
    }

    inner class FiltersViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: FiltersObject,context: Context, onClick: (FiltersObject) -> Unit) {
            itemView.filterName.text = item.label

            GlideToVectorYou
                .init()
                .with(context)
                .setPlaceHolder(R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_background)
                .load(Uri.parse(item.iconUrl), containerView.filterIcon)

            containerView.setOnClickListener {
                onClick(item)
            }
            containerView.isActivated = item.isSelected

        }
    }
}