package hu.daniel.dailyastronomy.presentation.main.solarsystem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.OnRebindCallback
import androidx.recyclerview.widget.RecyclerView
import hu.daniel.dailyastronomy.R
import hu.daniel.dailyastronomy.databinding.ComponentSolarSystemListItemBinding
import hu.daniel.dailyastronomy.dto.SolarSystemObject

class SolarSystemListAdapter : RecyclerView.Adapter<SolarSystemListAdapter.SolarSystemViewHolder>() {

    var solarSystemObjects = listOf<SolarSystemObject>()
    private var bindListener: (() -> Unit)? = null

    override fun onBindViewHolder(holder: SolarSystemViewHolder, position: Int) {
        holder.binding.apply {
            solarSystemObject = solarSystemObjects[position]
            addOnRebindCallback(object : OnRebindCallback<ComponentSolarSystemListItemBinding>() {
                override fun onBound(binding: ComponentSolarSystemListItemBinding?) {
                    if (position == 0 && bindListener != null) {
                        bindListener!!.invoke()
                        bindListener = null
                    }
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            SolarSystemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.component_solar_system_list_item, parent, false))

    class SolarSystemViewHolder(val binding: ComponentSolarSystemListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = solarSystemObjects.size

    fun setOnBindListener(listener: () -> Unit) {
        bindListener = listener
    }
}