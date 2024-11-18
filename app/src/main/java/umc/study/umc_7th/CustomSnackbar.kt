package umc.study.umc_7th

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import umc.study.umc_7th.databinding.CustomSnackbarBinding

class CustomSnackbar(view: View, private val message: String) {

    companion object {

        fun make(view: View, message: String) = CustomSnackbar(view, message)
    }

    private val context = view.context
    private val snackbar = Snackbar.make(view, "", 5000)
    private val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

    private val inflater = LayoutInflater.from(context)
    private val snackbarBinding: CustomSnackbarBinding = DataBindingUtil.inflate(inflater, R.layout.custom_snackbar, null, false)

    init {
        initView()
        initData()
    }

    private fun initView() {
        with(snackbarLayout) {
            removeAllViews()
            setPadding(0, 0, 0, 0)
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(snackbarBinding.root, 0)
        }
    }

    private fun initData() {
        snackbarBinding.customSnackbarTv.text = message
        snackbarBinding.customSnackbarBtn.setOnClickListener {
            // OK 버튼을 클릭했을 때 실행할 동작을 정의할 수 있다.
        }
    }

    fun show() {
        snackbar.show()
    }
}