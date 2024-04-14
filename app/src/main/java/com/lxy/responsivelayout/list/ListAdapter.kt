package com.lxy.responsivelayout.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.lxy.responsivelayout.base.BaseAdapter
import com.lxy.responsivelayout.databinding.ItemListBinding
import com.lxy.responsivelayout.entity.Article

class ListAdapter : BaseAdapter<Article>() {
    override fun onCreateViewBinding(
        viewType: Int,
        inflater: LayoutInflater?,
        parent: ViewGroup?
    ): ViewBinding {
        return ItemListBinding.inflate(inflater!!, parent, false)
    }

    override fun convert(holder: ViewBindHolder?, t: Article?, position: Int) {
        val binding = holder?.binding as ItemListBinding
        t?.let {
            binding.tvName.setText(it.title)
        }
    }
}