package ni.edu.uca.listadoprod

import android.app.ProgressDialog.show
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import ni.edu.uca.listadoprod.dataadapter.ProductoAdapter
import ni.edu.uca.listadoprod.dataclass.Producto
import ni.edu.uca.listadoprod.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var listaProd = ArrayList<Producto>()//Arreglo de datos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniciar()//Llamado de Funciones
    }
 /*Prototipo de funciones*/

    private fun iniciar() {
        binding.btnAddProd.setOnClickListener {
            agregarProd() //Le pasamos la funcion de agregar productos a nuestro botón
        }
        binding.btnClear.setOnClickListener{
            limpiar()
        }
    }


    private fun limpiar() { //Funcion para borrar datos dentro de los edit text
        with(binding) {
            etID.setText("")
            etNombreProd.setText("")
            etPrecio.setText("")
            etID.requestFocus()
        }
    }

    private fun agregarProd() {//Funcion para agregar productos
        with(binding) {
            try {
                val id: Int = etID.text.toString().toInt()
                val nombre: String = etNombreProd.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listaProd.add(prod)
            } catch (ex: Exception) {
                Toast.makeText(
                    this@MainActivity, "Error : ${ex.toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }
            rcvLista.layoutManager = LinearLayoutManager(this@MainActivity)
            rcvLista.adapter = ProductoAdapter(listaProd,
                { producto -> onItemSelected(producto) },
                { position -> onDeleteItem(position) },
                { position -> onUpdateItem(position) })
            limpiar()
        }
    }
    /*EDITAR REGISTROS*/
    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    fun onUpdateItem(position: Int) {
        with(binding) {
            val id: Int = etID.text.toString().toInt()
            val nombre: String = etNombreProd.text.toString()
            val precio: Double = etPrecio.text.toString().toDouble()
            val prod = Producto(id, nombre, precio)
            listaProd.set(position, prod)
            rcvLista.adapter?.notifyItemRemoved(position)
        }
    }

    fun onItemSelected(producto: Producto) {
        with(binding) {
            etID.text = producto.id.toString().toEditable()
            etNombreProd.text = producto.nombre.toEditable()
            etPrecio.text = producto.precio.toString().toEditable()
        }
    }

    /*Elmininar y mandar mensaje */
    fun onDeleteItem(position: Int) {
        val builder =  AlertDialog.Builder(this@MainActivity)
            builder.setTitle("Alerta!")
                .setMessage("Desea eliminar este producto?")
                .setCancelable(false) // dialog box in cancellable
                // set positive button
                //take two parameters dialogInterface and an int
                .setPositiveButton("Si"){dialogInterface,id ->
                    with(binding) {
                        listaProd.removeAt(position)
                        rcvLista.adapter?.notifyItemRemoved(position)
                        limpiar()
                    }
                }.setNegativeButton("No"){dialogInterface,id ->
                    // cancel the dialogbox
                    dialogInterface.cancel()
                }
                // show the builder
                 val alert = builder.create()
                .show()
        }
    }

