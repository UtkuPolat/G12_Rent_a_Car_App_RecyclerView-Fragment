package ise308.polat.utku.g12rentacarapp

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarsAdapter(private val carList: ArrayList<Cars>,private val mainActivity: MainActivity) : RecyclerView.Adapter<CarsAdapter.CarsViewHolder>() {


    inner class CarsViewHolder(carsItemView: View) : RecyclerView.ViewHolder(carsItemView), View.OnClickListener {
        internal var textViewCarModel = carsItemView.findViewById<TextView>(R.id.tv_car_model)
        internal var textViewCarType = carsItemView.findViewById<TextView>(R.id.tv_car_type)
        internal var textViewProductionYear = carsItemView.findViewById<TextView>(R.id.tv_production_year)
        internal var textViewRentPrice = carsItemView.findViewById<TextView>(R.id.tv_rent_price)
        internal var textViewRentFlag = carsItemView.findViewById<TextView>(R.id.tv_rent_flag)
        internal var textViewCarPlate = carsItemView.findViewById<TextView>(R.id.tv_car_plate)

        init {
            carsItemView.isClickable
            carsItemView.setOnClickListener(this)
        }

        override fun onClick(view : View?) {
            //mainActivity.showNote(adapterPosition)
            val intentToCarsPager = Intent(view!!.context, CarPagerActivity::class.java)
            view.context.startActivity(intentToCarsPager)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {

        val carsItemInflater = LayoutInflater.from(parent.context)
        val carsItemView = carsItemInflater.inflate(R.layout.cars_item, parent, false)
        return CarsViewHolder(carsItemView)

    }

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {

        val carsForDisplay = carList.get(position)
        holder.textViewCarModel.text = carsForDisplay.carModel
        holder.textViewCarType.text = carsForDisplay.carType
        holder.textViewProductionYear.text = carsForDisplay.productionYear.toString()
        holder.textViewRentPrice.text = carsForDisplay.rentPrice.toString()
        if (carsForDisplay.rentFlag.toString() == "true") {
            holder.textViewRentFlag.text = "Available"
        }
        if (carsForDisplay.rentFlag.toString() == "false") {
            holder.textViewRentFlag.text = "Not Available"
        }
        holder.textViewCarPlate.text = carsForDisplay.carPlate
    }

    override fun getItemCount() = carList.size


}
