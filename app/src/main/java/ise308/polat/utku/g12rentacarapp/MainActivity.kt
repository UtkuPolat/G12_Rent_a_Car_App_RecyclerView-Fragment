package ise308.polat.utku.g12rentacarapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import ise308.polat.utku.g12rentacarapp.ui.AllCars
import ise308.polat.utku.g12rentacarapp.ui.InsertFragment
import ise308.polat.utku.g12rentacarapp.ui.RentCarFragment
import ise308.polat.utku.g12rentacarapp.ui.SearchFragment
import java.lang.Exception

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var jsonSerializer: JSONSerializer? = null
    private lateinit var carList: ArrayList<Cars>
    private var recyclerViewCars : RecyclerView? = null
    private var carsAdapter : CarsAdapter? = null

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        jsonSerializer = JSONSerializer("RentACar", applicationContext)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggleBar = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close)

        drawerLayout.addDrawerListener(toggleBar)
        toggleBar.syncState()

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SearchFragment()).commit()
        navigationView.setCheckedItem(R.id.nav_car_search)


        try {
            carList = jsonSerializer!!.load()
        } catch (e: Exception) {
            carList = ArrayList()
        }

        initializeCars()
        val fabNewCar = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fabNewCar.setOnClickListener{
            val newCarDialog = NewCarDialog()
            newCarDialog.show(supportFragmentManager, "123")
        }

        recyclerViewCars = findViewById<RecyclerView>(R.id.recyclerViewCars) as RecyclerView
        carsAdapter = CarsAdapter(carList , this)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerViewCars!!.layoutManager = layoutManager
        recyclerViewCars!!.itemAnimator = DefaultItemAnimator()
        recyclerViewCars!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerViewCars!!.adapter = carsAdapter

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun initializeCars() {
        carList = ArrayList<Cars>()
        carList!!.add(Cars("Opel Astra", "Hatchback", 2014, 150.0, true, "34 DD 2991"))
        carList!!.add(Cars("Ford Focus", "Sedan", 2010, 120.0, true, "06 AD 1267"))
        carList!!.add(Cars("Mercedes GLC", "SUV", 2020, 500.0, false, "38 UD 6958"))
        carList!!.add(Cars("Volkswagen Polo", "Hatchback", 2016, 90.0, true, "32 ZEY 1997"))
        carList!!.add(Cars("Audi A5", "Coupe", 2015, 250.0, false, "06 MET 235"))
    }

    fun createNewCar(newCar : Cars){
        carList?.add(newCar)

    }

    private fun saveCars() {
        try {
            jsonSerializer!!.save(this.carList!!)
        } catch (e: Exception) {
            //Log.e(TAG, "error loading notes :((")
        }
    }

    override fun onPause() {
        super.onPause()
        saveCars()
    }

    fun showNote(adapterPosition: Int) {
        val showCar = DialogShowCars()
        showCar.setCars(carList.get(adapterPosition))
        showCar.show(supportFragmentManager, "124")
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_add_new_car -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, InsertFragment()).commit()
            R.id.nav_car_rent -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, RentCarFragment()).commit()
            R.id.nav_car_search -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SearchFragment()).commit()
            R.id.nav_all_cars -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AllCars()).commit()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}