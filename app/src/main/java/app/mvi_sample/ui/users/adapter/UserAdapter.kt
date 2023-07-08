package app.mvi_sample.ui.users.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.mvi_sample.R
import app.mvi_sample.data.model.User
import app.mvi_sample.databinding.ItemLayoutBinding
import com.bumptech.glide.Glide
class UserAdapter(
    private val users: ArrayList<User>,
) : RecyclerView.Adapter<UserAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            val binding = ItemLayoutBinding.bind(itemView)
            binding.textViewUserName.text = user.name
            binding.textViewUserEmail.text = user.email
            Glide.with(binding.imageViewAvatar.context)
                .load(user.avatar)
                .into(binding.imageViewAvatar)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))

    override fun onBindViewHolder(
        holder: DataViewHolder,
        position: Int
    ) = holder.bind(users[position])



    override fun getItemCount(): Int = users.size


     fun addData(list : List<User>){
        users.addAll(list)
    }

}