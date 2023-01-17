package app.lahzebar.features.history.continer

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class JustifiedTextView : AppCompatTextView {
    private var isJustChangeString = false
    private var tmpString: String? = null

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!, attrs, defStyle
    ) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
    }

    constructor(context: Context?) : super(context!!) {}

    override fun setText(text: CharSequence, type: BufferType) {
        if (!isJustChangeString) {
            tmpString = text.toString()
        }
        super.setText(text, type)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun changeText(text: String?) {
        isJustChangeString = true
        setText(text)
        isJustChangeString = false
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (!isInEditMode) {
            val str = justifyText(tmpString)
            changeText(str)
            // setTextDirection(TEXT_DIRECTION_RTL);
        }
    }

    private fun justifyText(text: String?): String? {
        val paint = Paint()

        // ==   paint.setColor(getCurrentTextColor());
        paint.typeface = typeface
        paint.textSize = textSize
        // ==   paint.setTextAlign(text_align);
        // == paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        val dirtyRegionWidth = (width - paddingLeft - paddingRight).toFloat()
        if (dirtyRegionWidth < 0) // opt when error occur
            {
                return text
            }
        val maxLines = maxLines
        val blocks = text!!.split(SYSTEM_NEWLINE).toTypedArray()
        val spaceOffset = paint.measureText(" ")
        val lines: MutableList<String> = ArrayList()

        // /////
        var block_index = 0
        val block_len = blocks.size
        for (block in blocks) // create lines
            {
                block_index++
                if (block.isEmpty()) {
                    continue
                }
                var line_text = block
                while (true) {
                    var wrappedLine = createWrappedLine(line_text, paint, spaceOffset, dirtyRegionWidth)
                    line_text = line_text.substring(wrappedLine.length)
                    if (line_text.isEmpty()) // end of block
                        {
                            if (block_index != block_len) // prevent add last \n
                                {
                                    wrappedLine += SYSTEM_NEWLINE
                                }
                            lines.add(wrappedLine)
                            break
                        }
                    lines.add(wrappedLine)
                }
            }
        val smb = StringBuilder()
        val line_len = lines.size
        var line_index = 0
        var is_restrict_line = false

        for (line_txt in lines) {
            line_index++
            if (line_txt.isEmpty()) {
                continue
            }
            val last_char = line_txt[line_txt.length - 1]
            if (line_index == line_len || last_char == SYSTEM_NEWLINE_CHAR) // prevent add space at original text in last line or, when new line char exist on text.
                {
                    smb.append(line_txt)
                } else // append blank space to text.
                {
                    val s_tmp = StringBuilder(line_txt)
                    var index = -1
                    while (paint.measureText(line_txt) < dirtyRegionWidth) {
                        index = line_txt.indexOf(
                            SPACE_CHAR,
                            index + 2
                        ) // 2 for after of this and after of append space
                        if (index >= line_txt.lastIndexOf(SPACE_CHAR)) {
                            index = -1
                        } else {
                            if (index != -1) {
                                s_tmp.insert(index, ' ')
                                // line_txt = s_tmp.toString()
                            }
                        }
                    }
                    smb.append(line_txt)
                }
            if (line_index == maxLines) // end of text
                {
                    is_restrict_line = true
                    break
                }
        }
        if (is_restrict_line) {
            var txt = smb.toString()
            txt = txt.replace(txt.substring(txt.length - 3), "...")
            return txt
        }
        return smb.toString()
    }

    companion object {
        private const val SYSTEM_NEWLINE = "\n"
        private const val SYSTEM_NEWLINE_CHAR = '\n'
        private const val SPACE_CHAR = ' '
        fun createWrappedLine(
            block: String,
            paint: Paint,
            spaceOffset: Float,
            maxWidth: Float
        ): String {
            var cacheWidth = 0f
            val line = StringBuilder()
            val words = block.split("\\s+").toTypedArray()
            if (words.size > 1) // prevent lock on loop when space not exist on text.
                {
                    for (word in words) // or "\\s"
                        {
                            cacheWidth += paint.measureText(word)
                            if (cacheWidth > maxWidth) {
                                return line.toString()
                            }
                            cacheWidth += spaceOffset
                            line.append(word).append(SPACE_CHAR)
                        }
                }
            return block
        }
    }
}
