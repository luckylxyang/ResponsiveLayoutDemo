package com.lxy.responsivelayout.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.lxy.responsivelayout.R
import com.lxy.responsivelayout.databinding.FragmentFirstBinding
import com.lxy.responsivelayout.main.MainViewModel


class FirstFragment : Fragment() {

    private val binding : FragmentFirstBinding by lazy {
        FragmentFirstBinding.inflate(layoutInflater)
    }

    private val mViewModel: ListViewModel by lazy {
        ViewModelProvider(requireActivity())[ListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        mViewModel.select.observe(viewLifecycleOwner){
            binding.webView.loadUrl(it.url)
        }

        binding.tvBack.setOnClickListener {
            mViewModel.back.value?.let {
                if (it){

                }
            }
        }
    }

}