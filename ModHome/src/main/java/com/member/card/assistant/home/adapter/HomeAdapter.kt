package com.member.card.assistant.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.member.card.assistant.home.R
import com.member.card.assistant.home.databinding.HomeFragmentMainItemBinding
import com.member.card.assistant.home.model.HomeItemModel
import org.jetbrains.anko.sdk27.coroutines.onClick


/**
 * @author 张雷
 * @date 2/23/21
 * @brief
 */
class HomeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list: ArrayList<HomeItemModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: HomeFragmentMainItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.home_fragment_main_item,
            parent,
            false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding: HomeFragmentMainItemBinding = DataBindingUtil.getBinding(holder.itemView)!!
        val item: HomeItemModel = list?.get(position)!!
        binding.homeItem = item
        binding.executePendingBindings()
        binding.root.onClick {
            ARouter.getInstance().build(item.outLink).navigation()
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}