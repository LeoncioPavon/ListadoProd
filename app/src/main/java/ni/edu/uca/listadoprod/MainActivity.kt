package ni.edu.uca.listadoprod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ni.edu.uca.listadoprod.dataadapter.ProductoAdapter
import ni.edu.uca.listadoprod.databinding.ActivityMainBinding
import ni.edu.uca.listadoprod.dataclass.Producto

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    /*Arreglo dinámico de datos*/
    var listaProd = ArrayList<Producto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*Llamamos la funcion limpiar*/
        iniciar()
    }

    /*Funcion de inicio enla que le mandamos las funciones de agregar los productos a nuestro botón
   * y a nuestro botón limpiar la funcion para eliminar datos de los edit text*/
    private fun iniciar(){
        binding.btnAddProd.setOnClickListener{
            agregarProd()
        }
        binding.btnClear.setOnClickListener{
            limpiar()
        }
    }
     /*La siguiente funcion elimina los datos de los edit text*/
    private fun limpiar(){
        with(binding){
            etID.setText("")
            etNombreProd.setText("")
            etPrecio.setText("")
            etID.requestFocus()
        }
    }
    /*Con esta función podremos agregar productos a nuestra lista dinámica*/
    private fun agregarProd(){
        with(binding){
            try{
                val id: Int = etID.text.toString().toInt()
                val nombre: String = etNombreProd.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listaProd.add(prod)

                /*Captura de errores*/
            } catch (ex: Exception){
                Toast.makeText(this@MainActivity, "Error : ${ex.toString()}",
                    Toast.LENGTH_LONG).show()
            }
            rcvLista.layoutManager = LinearLayoutManager(this@MainActivity)
            rcvLista.adapter = ProductoAdapter(listaProd,
                {producto -> onItemSelected(producto) },
                {position -> onDeleteItem(position)},
                {position -> onUpdateItem(position)})
            limpiar()
        }
    }

    /*Funcion para poder editar items de la lista; es decir en este caso productos*/
    fun onItemSelected(producto: Producto) {
        with(binding) {
            etID.text = producto.id.toString().toEditable()
            etNombreProd.text = producto.nombre.toEditable()
            etPrecio.text = producto.precio.toString().toEditable()
        }
    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


    /*Funcion para eliminar items de la lista; es decir productos en este caso*/

    fun onDeleteItem(position: Int){
        with(binding){
            listaProd.removeAt(position)
            rcvLista.adapter?.notifyItemRemoved(position)
            limpiar()
        }
    }

    /*La siguiente función es para poder editrar */
    fun onUpdateItem(position: Int){
        with(binding){
            val id: Int = etID.text.toString().toInt()
            val nombre: String = etNombreProd.text.toString()
            val precio: Double = etPrecio.text.toString().toDouble()
            val prod = Producto(id, nombre, precio)
            listaProd.set(position, prod)
            rcvLista.adapter?.notifyItemChanged(position)
        }
    }
}
