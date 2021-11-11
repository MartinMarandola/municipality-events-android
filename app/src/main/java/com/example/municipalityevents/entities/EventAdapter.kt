package com.example.municipalityevents.entities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.item_event.view.*


class EventAdapter(private val mContext: Context, private val eventList: List<Event>) : ArrayAdapter<Event>(mContext, 0, eventList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var layout = LayoutInflater.from(mContext).inflate(com.example.municipalityevents.R.layout.item_event, parent, false)

        val event = eventList[position]

        layout.nombre.text = event.nombre
        layout.descripcion.text = event.descripcion
        layout.imageView.setImageResource(event.imagen)


        return layout

    }


}