package com.lxy.responsivelayout.search

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import com.lxy.responsivelayout.R
import com.lxy.responsivelayout.databinding.LayoutDownloadProgressBinding

/**
 *
 * @Author：liuxy
 * @Date：2024/6/20 14:44
 * @Desc：
 *
 */
class DownloadDialog(
    private val mContext : Context
) : BaseDialog(mContext) {

    private val binding = LayoutDownloadProgressBinding.inflate(LayoutInflater.from(mContext))

    fun initDialog() {

        binding.progress.setProgressColor(R.color.primaryColor)
        // 显示版本更新对话框
        this.contentView(binding.root)
            .gravity(Gravity.CENTER)
            .canceledOnTouchOutside(false).show()
    }

    fun setProgress(progress: Int) {
        binding.progress.setProgress(progress)
        binding.tvProgress.text = "$progress%"
    }
}