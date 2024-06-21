package com.lxy.responsivelayout.search

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import com.lxy.responsivelayout.R
import com.lxy.responsivelayout.databinding.DialogAppModeBinding
import com.lxy.responsivelayout.databinding.LayoutDownloadProgressBinding
import com.lxy.responsivelayout.databinding.LayoutPermissionBinding

/**
 *
 * @Author：liuxy
 * @Date：2024/6/20 16:34
 * @Desc：
 *
 */
class AppModeDialog(
    private val mContext : Context
) : BaseDialog(mContext) {

    private val binding = DialogAppModeBinding.inflate(LayoutInflater.from(mContext))

    fun initDialog(
        onSelect : () ->Unit
    ) {
        binding.tvStart.setOnClickListener {
            onSelect()
            dismiss()
        }

        // 显示版本更新对话框
        this.contentView(binding.root)
            .gravity(Gravity.CENTER)
            .canceledOnTouchOutside(false).show()
    }
}