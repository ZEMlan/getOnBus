package ru.zemlyanaya.getonbus.mainactivity.routing

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.VectorDrawable
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.zemlyanaya.getonbus.R
import kotlin.Float as Float1


abstract class SwipeToDeleteCallback internal constructor(context: Context) :
    ItemTouchHelper.Callback() {
    var mContext: Context = context
    private val mClearPaint: Paint = Paint()
    private val mBackground: ColorDrawable = ColorDrawable()
    private val editColor: Int = Color.parseColor("#DAE4FF")
    private val editDrawable: VectorDrawable?
    private val intrinsicWidth = 70
    private val intrinsicHeight = 70

    override fun getMovementFlags(@NonNull recyclerView: RecyclerView, @NonNull viewHolder: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(
            0,
            ItemTouchHelper.LEFT
        )
    }

    override fun onMove(@NonNull recyclerView: RecyclerView, @NonNull viewHolder: RecyclerView.ViewHolder, @NonNull viewHolder1: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: kotlin.Float,
        dY: kotlin.Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView: View = viewHolder.itemView
        val itemHeight: Int = itemView.height
        val isCancelled = dX == 0f && !isCurrentlyActive
        if (isCancelled) {
            clearCanvas(
                c,
                itemView.right + dX,
                itemView.top.toFloat(),
                itemView.right.toFloat(),
                itemView.bottom.toFloat()
            )
            super.onChildDraw(
                c,
                recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
            )
            return
        }
        mBackground.color = editColor
        mBackground.setBounds(
            itemView.right + dX.toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
        )
        mBackground.draw(c)
        val deleteIconTop: Int = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
        val deleteIconLeft: Int = itemView.right - deleteIconMargin - intrinsicWidth
        val deleteIconRight: Int = itemView.right - deleteIconMargin
        val deleteIconBottom = deleteIconTop + intrinsicHeight
        editDrawable!!.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        editDrawable.draw(c)
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(
        c: Canvas,
        left: Float1,
        top: Float1,
        right: Float1,
        bottom: Float1
    ) {
        c.drawRect(left, top, right, bottom, mClearPaint)
    }

    override fun getSwipeThreshold(@NonNull viewHolder: RecyclerView.ViewHolder): Float1 {
        return 0.7f
    }

    init {
        mClearPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        editDrawable = getDrawable(mContext, R.drawable.ic_edit) as VectorDrawable
    }
}