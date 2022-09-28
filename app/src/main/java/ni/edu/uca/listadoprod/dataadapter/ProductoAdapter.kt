package ni.edu.uca.listadoprod.dataadapter

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.NonDisposableHandle.parent
import ni.edu.uca.listadoprod.databinding.ItemlistaBinding
import ni.edu.uca.listadoprod.dataclass.Producto

/*Crearemos variable para la lista de prudctos, evento OnClick para que los campos reaparezccan en los Et
  variable click para eliminar prod de la lista y  val on click para editar la lista de uno en uno*/
class ProductoAdapter(val listProd: List<Producto>, private val onClickListener:(Producto) ->Unit,
       private val onClickDelete: (Int) -> Unit,
       private val onClickEdit: (Int) -> Unit):

        /*Enlace entre los datos y un el adaptador vista */
    RecyclerView.Adapter<ProductoAdapter.ProductoHolder>() {
        inner class ProductoHolder(val binding: ItemlistaBinding):
            RecyclerView.ViewHolder(binding.root){
              /*Cargamos los datos*/
            fun cargar(producto: Producto,
                       onClickListener: (Producto) -> Unit,
                       onClickDelete: (Int) -> Unit,
                       onClickEdit: (Int) -> Unit){
                with(binding){
                    tvCodProd.text = producto.id.toString()
                    tvNombreProd.text = producto.nombre
                    tvPrecioProd.text = producto.precio.toString()
                    /*Eventos Click*/
                    itemView.setOnClickListener { onClickListener(producto) }
                    binding.btnDelete.setOnClickListener { onClickDelete(adapterPosition) }
                    binding.btnEdit.setOnClickListener{onClickEdit(adapterPosition)}
                }
            }
        }
            /*Diseño individual de la vista de los productos*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoHolder{
       val binding = ItemlistaBinding.inflate(
           LayoutInflater.from(parent.context), parent,
             false
       )
        return ProductoHolder(binding)
    }
            /*Pasamos la posición del producto*/
    override fun onBindViewHolder(holder: ProductoHolder, position: Int)  {
        holder.cargar(listProd[position], onClickListener, onClickDelete, onClickEdit)
    }
       /*Tamaño de la lista*/
    override fun getItemCount(): Int = listProd.size
}
