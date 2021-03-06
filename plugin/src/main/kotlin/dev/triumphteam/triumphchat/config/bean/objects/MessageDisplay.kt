/*
 * MIT License
 *
 * Copyright (c) 2021 Mateus Moreira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.triumphteam.triumphchat.config.bean.objects

import dev.triumphteam.triumphchat.api.ChatUser
import dev.triumphteam.triumphchat.config.bean.objects.elements.ClickData
import dev.triumphteam.triumphchat.func.MESSAGE_PLACEHOLDER
import dev.triumphteam.triumphchat.func.addActions
import dev.triumphteam.triumphchat.func.copyFormat
import dev.triumphteam.triumphchat.func.parseMarkdown
import me.mattstudios.config.annotations.Name
import me.mattstudios.msg.base.FormatData
import me.mattstudios.msg.base.internal.nodes.TextNode
import java.util.Optional

/**
 * Message display is used specifically when it comes to config displays that contain [MESSAGE_PLACEHOLDER]
 * It contains a [FormatData] object containing the formats necessary for the message to have
 */
data class MessageDisplay(
    override var text: String = "",
    @Name("hover") var hoverData: Optional<List<String>> = Optional.empty<List<String>>(),
    @Name("click") var clickData: Optional<ClickData> = Optional.empty()
) : FormatDisplay {

    override val hover: List<String> = hoverData.orElseGet { emptyList() }
    override val click: ClickData = clickData.orElseGet { ClickData() }

    /**
     * Gathers the format settings from the text node that has the message placeholder
     */
    fun createFormatData(author: ChatUser?, recipient: ChatUser?): FormatData {
        val data = text.parseMarkdown()
                .filterIsInstance(TextNode::class.java)
                .find { MESSAGE_PLACEHOLDER in it.text }
                ?.copyFormat() ?: FormatData()

        return data.addActions(hover, click, author, recipient)
    }

}