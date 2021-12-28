package com.my.bubbletea.conversation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MessageViewModel : ViewModel() {
    var msgs by mutableStateOf(MessageRepo.getMsgs())

    fun addMessage(msg: Message) {
        msgs = msgs + listOf<Message>(msg)
    }

    fun removeMessage(msg:Message) {
        msgs = msgs.toMutableList().also {
            it.remove(msg)
        }
    }
}

object MessageRepo {
    fun getMsgs(): List<Message> = generate_msgs()
}

private fun generate_msgs(): List<Message> {
    val cats = mutableListOf<Message>()
    for ((index, m) in initialMessages.withIndex()) {
        cats.add(0,m)
    }
    return cats
}