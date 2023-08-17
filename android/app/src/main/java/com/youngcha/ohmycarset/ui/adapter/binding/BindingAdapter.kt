package com.youngcha.ohmycarset.ui.adapter.binding

import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.youngcha.ohmycarset.model.car.Car
import com.youngcha.ohmycarset.model.car.OptionInfo
import com.youngcha.ohmycarset.ui.adapter.recyclerview.EstimateSummaryAdapter
import com.youngcha.ohmycarset.ui.customview.CircleView
import com.youngcha.ohmycarset.ui.customview.HyundaiButtonView
import com.youngcha.ohmycarset.ui.interfaces.OnHyundaiButtonClickListener


@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String) {
    // 여기에 이미지 로딩 로직 (Glide | Coil)
    // coil
    view.load(imageUrl)
}

@BindingAdapter("testImageSource")
fun loadImage(view: ImageView, imageUrl: Int) {
    if (imageUrl == 1) return
    view.setImageResource(imageUrl)
}

@BindingAdapter("optionImage")
fun setOptionImage(view: ImageView, imageUrl: Int) {
    if (imageUrl <= 0) return
    view.setImageResource(imageUrl)
}

@BindingAdapter("optionImage_circle")
fun setFillColor(view: CircleView, @ColorRes colorResId: Int?) {
    if (colorResId != null && colorResId != 0) {
        val color = ContextCompat.getColor(view.context, colorResId)
        view.setFillColor(color)
    }
}

@BindingAdapter("app:fillColor")
fun setFillColor(view: CircleView, colorStr: String?) {
    if (!colorStr.isNullOrEmpty()) {
        try {
            view.setFillColor(Color.parseColor(colorStr))
        } catch (e: IllegalArgumentException) {
            // 색상 문자열이 잘못된 경우 처리
        }
    }
}

/*
<ImageView
    ...
    app:testImageSource="@{@drawable/img_test1}" />
 */

@BindingAdapter("app:isVisibleForRankZero")
fun View.isVisibleForRankZero(rank: Int) {
    visibility = if (rank == 0) View.VISIBLE else View.GONE
}

@BindingAdapter("dynamicWidth")
fun setDynamicWidth(cardView: CardView, widthDp: Float) {
    val widthPx = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        widthDp,
        cardView.resources.displayMetrics
    )
    val layoutParams = cardView.layoutParams
    layoutParams.width = widthPx.toInt()
    cardView.layoutParams = layoutParams
}

@BindingAdapter("dynamicHeight")
fun setDynamicHeight(cardView: CardView, heightDp: Float) {
    val heightPx = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        heightDp,
        cardView.resources.displayMetrics
    )
    val layoutParams = cardView.layoutParams
    layoutParams.height = heightPx.toInt()
    cardView.layoutParams = layoutParams
}

@BindingAdapter("car", "componentName")
fun setOptionsVisibility(view: View, car: Car?, componentName: String?) {
    car?.mainOptions?.find { it.keys.first() == componentName }?.values?.first()?.let {
        view.visibility = if (it.size >= 2) View.VISIBLE else View.GONE
    } ?: run {
        view.visibility = View.GONE
    }
}

@BindingAdapter("mainOptionImage", "componentName")
fun setMainOptionImage(
    view: ImageView,
    mainOptionImages: List<Map<String, Int>>,
    componentName: String?
) {
    componentName?.let {
        for (map in mainOptionImages) {
            map[componentName]?.let { resId ->
                view.setImageResource(resId)
            }
        }
    }
}

@BindingAdapter("testImageResourceCar", "testImageResourceComponentName")
fun ImageView.setTestImageResource(car: Car?, componentName: String?) {
    when (componentName) {
        "옵션 선택" -> {
            car?.subOptionImage?.get(componentName)?.let { setImageResource(it) }
        }
        else -> {
            car?.mainOptionImages?.let {
                for (map in it) {
                    val resourceId = map[componentName]
                    if (resourceId != null) {
                        setImageResource(resourceId)
                        break
                    }
                }
            }
        }
    }
}

@BindingAdapter("optionInfo")
fun setOptionInfo(view: HyundaiButtonView, optionInfo: OptionInfo?) {
    view.binding.optionInfo = optionInfo
}

@BindingAdapter("visible")
fun setOnCustomClick(view: HyundaiButtonView, isVisible: Int) {
    view.binding.isVisible = isVisible
}

@BindingAdapter("customOnClickAction")
fun setCustomOnClickAction(view: HyundaiButtonView, listener: OnHyundaiButtonClickListener?) {
    if (listener != null) {
        view.setOnClickAction { listener.onHyundaiButtonClick() }
    }
}

@BindingAdapter("customTopConstraint")
fun setCustomTopConstraint(view: View, targetId: Int) {
    val parent = view.parent as? ConstraintLayout ?: return
    val constraintSet = ConstraintSet()
    constraintSet.clone(parent)
    constraintSet.connect(view.id, ConstraintSet.TOP, targetId, ConstraintSet.BOTTOM)
    constraintSet.applyTo(parent)
}

@BindingAdapter("customLayoutHeight")
fun setCustomLayoutHeight(view: View, height: Float) {
    val layoutParams = view.layoutParams
    layoutParams.height = height.toInt()
    view.layoutParams = layoutParams
}

@BindingAdapter("customMarginBottom")
fun setCustomMarginBottom(view: View, marginBottom: Float) {
    if (view.layoutParams is MarginLayoutParams) {
        val p = view.layoutParams as MarginLayoutParams
        p.setMargins(p.leftMargin, p.topMargin, p.rightMargin, marginBottom.toInt())
        view.requestLayout()
    }
}

@BindingAdapter(value = ["myCarData", "viewType"], requireAll = false)
fun bindRecyclerView(recyclerView: RecyclerView, myCarData: List<Map<String, List<OptionInfo>>>?, viewType: String) {
    val adapter = recyclerView.adapter as? EstimateSummaryAdapter ?: EstimateSummaryAdapter()
    recyclerView.adapter = adapter

    if (recyclerView.layoutManager == null) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    }

    val matchedOptionsMap = mutableMapOf<String, MutableList<OptionInfo>>()

    myCarData?.forEach { mapEntry ->
        mapEntry.forEach { (key, optionList) ->
            optionList.forEach { option ->
                if (option.optionType == viewType) {
                    matchedOptionsMap[key] = matchedOptionsMap.getOrDefault(key, mutableListOf()).apply {
                        add(option)
                    }
                }
            }
        }
    }
    adapter.updateOptionInfo(matchedOptionsMap)
}
