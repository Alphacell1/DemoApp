package hr.tomislavplaninic.demo.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView ItemDecoration for adding spacing and borders between items in a grid layout.
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int spacing; // Spacing between items
    private int borderWidth; // Width of the border
    private Paint borderPaint; // Paint object for drawing borders

    /**
     * Constructor to initialize the GridSpacingItemDecoration.
     *
     * @param spacing     Spacing between items.
     * @param borderWidth Width of the border.
     * @param borderColor Color of the border.
     */
    public GridSpacingItemDecoration(int spacing, int borderWidth, @ColorInt int borderColor) {
        this.spacing = spacing;
        this.borderWidth = borderWidth;

        // Initialize the Paint object for drawing borders
        borderPaint = new Paint();
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);
    }

    /**
     * Set spacing between items.
     *
     * @param outRect Rect object to set the offsets.
     * @param view    Current item view.
     * @param parent  RecyclerView parent.
     * @param state   RecyclerView state.
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // Set spacing between items
        outRect.left = spacing;
        outRect.right = spacing;
        outRect.bottom = spacing;
        outRect.top = spacing;
    }

    /**
     * Draw borders around each item in the RecyclerView.
     *
     * @param canvas Canvas object for drawing.
     * @param parent RecyclerView parent.
     * @param state  RecyclerView state.
     */
    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);

        // Draw borders around each item
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            drawBorder(canvas, child);
        }
    }

    /**
     * Draw a border around the specified view.
     *
     * @param canvas Canvas object for drawing.
     * @param view   View to draw the border around.
     */
    private void drawBorder(Canvas canvas, View view) {
        // Calculate the coordinates of the border rectangle
        float left = view.getLeft() - spacing / 2f;
        float top = view.getTop() - spacing / 2f;
        float right = view.getRight() + spacing / 2f;
        float bottom = view.getBottom() + spacing / 2f;

        // Draw the border rectangle
        canvas.drawRect(left, top, right, bottom, borderPaint);
    }
}
