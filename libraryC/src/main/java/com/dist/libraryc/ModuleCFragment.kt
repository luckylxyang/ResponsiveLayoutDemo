package com.dist.libraryc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dist.libraryc.databinding.FragmentModuleBinding

/**
 *
 * @Author：liuxy
 * @Date：2024/6/19 15:59
 * @Desc：
 *
 */
class ModuleCFragment : Fragment() {

    private val binding : FragmentModuleBinding by lazy {
        FragmentModuleBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initView()
        return binding.root
    }

    private fun initView() {
        binding.tv.text = "ModuleCFragment"
    }
}