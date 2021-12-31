package com.my.bubbletea.conversation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.*
//import androidx.compose.material.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
import com.my.bubbletea.conversation.ui.theme.BubbleTeaTheme

//
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.snapshots.SnapshotStateList
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsPadding
import com.my.bubbletea.SymbolAnnotationType
import com.my.bubbletea.messageFormatter
//import com.example.compose.jetchat.FunctionalityNotAvailablePopup
//import com.example.compose.jetchat.R
//import com.example.compose.jetchat.components.JetchatAppBar
//import com.example.compose.jetchat.data.exampleUiState
//import com.example.compose.jetchat.theme.JetchatTheme
//import com.google.accompanist.insets.LocalWindowInsets
//import com.google.accompanist.insets.navigationBarsWithImePadding
//import com.google.accompanist.insets.rememberInsetsPaddingValues
//import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch
import com.parse.ParseUser
import com.parse.ParseQuery
import com.parse.FindCallback
import com.parse.ParseObject


class ConversationUiState(
    val channelName: String,
    initialMessages: List<Message>
) {
    private val _messages: MutableList<Message> =
        mutableStateListOf(*initialMessages.toTypedArray())
    val messages: List<Message> = _messages

    fun addMessage(msg: Message) {
        _messages.add(0, msg) // Add to the beginning of the list
    }
}

@Immutable
data class Message(
    val author: String,
    val content: String,
    val timestamp: String,
    val image: Int? = null,
)


class Conversation : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BubbleTeaTheme {
                ConversationContent(navigateToProfile = {})
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationContent(
//    uiState: ConversationUiState,
    navigateToProfile: (String) -> Unit,
    modifier: Modifier = Modifier,
    onNavIconPressed: () -> Unit = { }
) {
//    val authorMe = stringResource(R.string.author_me)
//    val timeNow = stringResource(id = R.string.now)

    val authorMe = "Me"
    val timeNow = "8:00 PM"

    val scrollState = rememberLazyListState()
    val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior() }
    val scope = rememberCoroutineScope()

    val messageList = remember { mutableStateListOf<Message>() }

//    LaunchedEffect(Unit) {
//        messageList = fetchMessages()
//    }
    anotherFetch(messageList);
//    messageList.swapList(fetchMessages())
    androidx.compose.material3.Surface(modifier = modifier) {

        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                Text(text = "fuck")
                Messages(
                    messages = messageList,
//                    messages = uiState.messages,
                    navigateToProfile = navigateToProfile,
                    modifier = Modifier.weight(1f),
                    scrollState = scrollState
                )
                UserInput(
                    onMessageSent = { content ->
                        messageList.add(0, Message(authorMe, content, timeNow))
//                        messageList.swapList(fetchMessages())
//                        Log.e("UserInput",messageList.size.toString())
//                        uiState.addMessage(
//                            Message(authorMe, content, timeNow)
//                        )
                    },
                    resetScroll = {
                        scope.launch {
                            scrollState.scrollToItem(0)
                        }
                    },

                    modifier = Modifier.navigationBarsWithImePadding(),
                )
            }

            ChannelNameBar(
                channelName = "❤️同客服互动❤️",
                onNavIconPressed = onNavIconPressed,
                scrollBehavior = scrollBehavior,
                modifier = Modifier.statusBarsPadding(),
                onClick = {
                    Log.e("Fucn", messageList.size.toString())
                }
            )
        }
    }
}


fun anotherFetch(messages: SnapshotStateList<Message>) {
    val messageFetched = fetchMessages()
    Log.e("msgFetchSize", messageFetched.size.toString())
    messageFetched.forEach {
        Log.e("msgFetch", it.content)
    }
    messages.swapList(messageFetched)
}


fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
//fun <T> SnapshotStateList<T>.swapList(newList: ArrayList<T>){
    clear()
    addAll(newList)
}

fun fetchMessages(): ArrayList<Message> {
    Log.e("Su", "fetchMessages")
    val currentUser = ParseUser.getCurrentUser()
    var arr = ArrayList<Message>()
    arr.add(
            0,
            Message(
                    author = "客服",
                    content = "今天有什么可以帮到你？",
                    timestamp = "now",
            )
    )
    if (currentUser != null) {

        val query = ParseQuery.getQuery<ParseObject>("Chat")
        query.whereEqualTo("sender", currentUser)
        val chatLists = query.find()
        if (chatLists != null) {
            chatLists.forEach { _chat ->
                val msgList = _chat.getList<ParseObject>("messages")
                if (msgList != null) {
                    msgList.forEach { msg ->
                        if (msg != null && msg.fetchIfNeeded<ParseObject>().getBoolean("isAdmin")) {
                            arr.add(
                                0,
                                Message(
                                    author = "客服",
                                    content = msg.fetchIfNeeded<ParseObject>()
                                        .getString("content")!!,
                                    timestamp = "now",
                                )
                            )
                        } else {
                            val c = msg.fetchIfNeeded<ParseObject>().getString("content")
                            if (c != null) {
                                arr.add(
                                    0,
                                    Message(
                                        author = "Me",
                                        content = c,
                                        timestamp = "now",
                                    )
                                )
                            }

                        }
                    }

                }
            }
        }

    }
    return arr
}

@Composable
fun ChannelNameBar(
    channelName: String,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onNavIconPressed: () -> Unit = { },
    onClick: () -> Unit = {},
) {
    AppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        onNavIconPressed = onNavIconPressed,
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                androidx.compose.material3.Text(
                    text = channelName,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        actions = {
            // Search icon
            Icon(
                imageVector = Icons.Outlined.Search,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .clickable(onClick = onClick)
                    .padding(horizontal = 12.dp, vertical = 16.dp)
                    .height(24.dp),
                contentDescription = "SEARCH"
            )
            // Info icon
            Icon(
                imageVector = Icons.Outlined.Info,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .clickable(onClick = {
//                        Log.e("SIZE", me.messages.size.toString())
                    })
                    .padding(horizontal = 12.dp, vertical = 16.dp)
                    .height(24.dp),
                contentDescription = "INFOOO"
            )
        }
    )
}

const val ConversationTestTag = "ConversationTestTag"


@Composable
fun Messages(
    messages: List<Message>,
    navigateToProfile: (String) -> Unit,
    scrollState: LazyListState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()


    Box(modifier = modifier) {

        val authorMe = "Me"
        LazyColumn(
            reverseLayout = true,
            state = scrollState,
            contentPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.statusBars,
                additionalTop = 90.dp
            ),
            modifier = Modifier
                .testTag(ConversationTestTag)
                .fillMaxSize()
        ) {
            for (index in messages.indices) {
                val prevAuthor = messages.getOrNull(index - 1)?.author
                val nextAuthor = messages.getOrNull(index + 1)?.author
                val content = messages[index]
                val isFirstMessageByAuthor = prevAuthor != content.author
                val isLastMessageByAuthor = nextAuthor != content.author

                // Hardcode day dividers for simplicity
                if (index == messages.size) {
                    item {
                        DayHeader("Today")
                    }
                }

                item {
                    Message(
                        onAuthorClick = { name -> navigateToProfile(name) },
                        msg = content,
                        isUserMe = content.author == authorMe,
                        isFirstMessageByAuthor = isFirstMessageByAuthor,
                        isLastMessageByAuthor = isLastMessageByAuthor
                    )
                }
            }
        }

        val jumpThreshold = with(LocalDensity.current) {
            JumpToBottomThreshold.toPx()
        }

        val jumpToBottomButtonEnabled by remember {
            derivedStateOf {
                scrollState.firstVisibleItemIndex != 0 ||
                        scrollState.firstVisibleItemScrollOffset > jumpThreshold
            }
        }

        JumpToBottom(
            // Only show if the scroller is not at the bottom
            enabled = jumpToBottomButtonEnabled,
            onClicked = {
                scope.launch {
                    scrollState.animateScrollToItem(0)
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun Message(
    onAuthorClick: (String) -> Unit,
    msg: Message,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean
) {
    val borderColor = if (isUserMe) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.tertiary
    }

    val spaceBetweenAuthors = if (isLastMessageByAuthor) Modifier.padding(top = 8.dp) else Modifier
    Row(modifier = spaceBetweenAuthors) {
        Spacer(modifier = Modifier.width(14.dp))
        AuthorAndTextMessage(
            msg = msg,
            isUserMe = isUserMe,
            isFirstMessageByAuthor = isFirstMessageByAuthor,
            isLastMessageByAuthor = isLastMessageByAuthor,
            authorClicked = onAuthorClick,
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f)
        )
    }
}


@Composable
fun AuthorAndTextMessage(
    msg: Message,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
    authorClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (isLastMessageByAuthor) {
            AuthorNameTimestamp(msg)
        }
        ChatItemBubble(msg, isUserMe, authorClicked = authorClicked)
        if (isFirstMessageByAuthor) {
            // Last bubble before next author
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            // Between bubbles
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun AuthorNameTimestamp(msg: Message) {
    Row(modifier = Modifier.semantics(mergeDescendants = true) {}) {
        Text(
            text = msg.author,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .alignBy(LastBaseline)
                .paddingFrom(LastBaseline, after = 8.dp) // Space to 1st bubble
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = msg.timestamp,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.alignBy(LastBaseline),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private val ChatBubbleShape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)

@Composable
fun DayHeader(dayString: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .height(16.dp)
    ) {
        DayHeaderLine()
        androidx.compose.material3.Text(
            text = dayString,
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        DayHeaderLine()
    }
}

@Composable
private fun RowScope.DayHeaderLine() {
    Divider(
        modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    )
}

@Composable
fun ChatItemBubble(
    message: Message,
    isUserMe: Boolean,
    authorClicked: (String) -> Unit
) {

    val backgroundBubbleColor = if (isUserMe) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    Column {
        androidx.compose.material3.Surface(
            color = backgroundBubbleColor,
            shape = ChatBubbleShape
        ) {
            ClickableMessage(
                message = message,
                isUserMe = isUserMe,
                authorClicked = authorClicked
            )
        }
    }
}

@Composable
fun ClickableMessage(
    message: Message,
    isUserMe: Boolean,
    authorClicked: (String) -> Unit
) {
    val uriHandler = LocalUriHandler.current

    val styledMessage = messageFormatter(
        text = message.content,
        primary = isUserMe
    )

    ClickableText(
        text = styledMessage,
        style = MaterialTheme.typography.bodyLarge.copy(color = LocalContentColor.current),
        modifier = Modifier.padding(16.dp),
        onClick = {
            styledMessage
                .getStringAnnotations(start = it, end = it)
                .firstOrNull()
                ?.let { annotation ->
                    when (annotation.tag) {
                        SymbolAnnotationType.LINK.name -> uriHandler.openUri(annotation.item)
                        SymbolAnnotationType.PERSON.name -> authorClicked(annotation.item)
                        else -> Unit
                    }
                }
        }
    )
}

@Preview
@Composable
fun ConversationPreview() {
    MaterialTheme() {
        ConversationContent(
//            uiState = exampleUiState,
            navigateToProfile = { }
        )
    }
}

@Preview
@Composable
fun channelBarPrev() {
    BubbleTeaTheme() {
        ChannelNameBar(channelName = "联系客服")
    }
}

@Preview
@Composable
fun DayHeaderPrev() {
    DayHeader("Aug 6")
}

private val JumpToBottomThreshold = 56.dp

private fun ScrollState.atBottom(): Boolean = value == 0


/*

        Paste BELOW

 */

enum class InputSelector {
    NONE,
}

@Preview
@Composable
fun UserInputPreview() {
    UserInput(onMessageSent = {})
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserInput(
    onMessageSent: (String) -> Unit,
    modifier: Modifier = Modifier,
    resetScroll: () -> Unit = {},
) {
    var currentInputSelector by rememberSaveable { mutableStateOf(InputSelector.NONE) }
    val dismissKeyboard = { currentInputSelector = InputSelector.NONE }

    var textState by remember { mutableStateOf(TextFieldValue()) }

    // Used to decide if the keyboard should be shown
    var textFieldFocusState by remember { mutableStateOf(false) }

    val border = if (!textState.text.isNotBlank()) {
        BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        )
    } else {
        null
    }
    Spacer(modifier = Modifier.height(8.dp))
    val disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)

    val buttonColors = ButtonDefaults.buttonColors(
        disabledContainerColor = Color.Transparent,
        disabledContentColor = disabledContentColor
    )
    textState.addText(" ")

    Surface(tonalElevation = 2.dp) {
        ConstraintLayout(modifier = modifier) {
            val (inp, button) = createRefs()
            Box(
                modifier = Modifier.constrainAs(inp) {
                    end.linkTo(button.start)
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            ) {
                var lastFocusState by remember { mutableStateOf(false) }
                BasicTextField(
                    value = textState,
                    onValueChange = { textState = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 68.dp)
                        .align(Alignment.CenterStart)
                        .onFocusChanged { state ->
                            if (lastFocusState != state.isFocused) {
                                if (state.isFocused) {
                                    currentInputSelector = InputSelector.NONE
                                    resetScroll()
                                }
                                textFieldFocusState = state.isFocused
                            }
                            lastFocusState = state.isFocused
                        },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Send
                    ),
                    maxLines = 1,
                    cursorBrush = SolidColor(LocalContentColor.current),
                    textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current)
                )
                val disableContentColor =
                    MaterialTheme.colorScheme.onSurfaceVariant
                if (textState.text.isEmpty() && !textFieldFocusState) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterStart).padding(start = 68.dp),
                        text = "输入你的消息",
                        style = MaterialTheme.typography.bodyLarge.copy(color = disableContentColor)
                    )
                }
            }
            Button(
                modifier = Modifier
                    .wrapContentWidth().padding(end = 60.dp)
                    .constrainAs(button) {
                        start.linkTo(inp.end)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                enabled = textState.text.isNotBlank(),
                onClick = {
                    val saveText = textState.text

                    onMessageSent(textState.text)
                    // Reset text field and close keyboard
                    textState = TextFieldValue()
                    // Move scroll to bottom
                    resetScroll()
                    dismissKeyboard()

                    val currentUser = ParseUser.getCurrentUser();
                    if (currentUser != null) {
                        val query = ParseQuery.getQuery<ParseObject>("Chat");
                        query.whereEqualTo("sender", currentUser);
                        query.getFirstInBackground { chatObj, e ->

                            if (!(e == null && chatObj != null)) {
                                val newChat = ParseObject("Chat")
                                newChat.put("sender", currentUser)


                                val message = ParseObject("MessageChunks")
                                message.put("isAdmin", false)
                                message.put("sender", currentUser)
                                message.put("content", saveText)
                                message.save()

                                val msgList = arrayListOf<ParseObject>()
                                msgList.add(message)
                                newChat.put("messages", msgList)
                                newChat.saveInBackground()
                            } else {
                                val message = ParseObject("MessageChunks")
                                message.put("isAdmin", false)
                                message.put("sender", currentUser)
                                message.put("content", saveText)
                                message.save()
                                val msgList = chatObj.getList<ParseObject>("messages")
                                if (msgList == null) {
                                    val msgList = arrayListOf<ParseObject>()
                                    msgList.add(message)
                                    chatObj.put("messages", msgList)
                                } else {
                                    msgList.add(message)
                                    chatObj.put("messages", msgList)
                                }
                                chatObj.saveInBackground()
                            }
                        }
                    } else {
                        Log.e("Err", "currUser NULL")
                    }

                },
                colors = buttonColors,
                border = border,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    "Send",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

private fun TextFieldValue.addText(newString: String): TextFieldValue {
    val newText = this.text.replaceRange(
        this.selection.start,
        this.selection.end,
        newString
    )
    val newSelection = TextRange(
        start = newText.length,
        end = newText.length
    )

    return this.copy(text = newText, selection = newSelection)
}


val KeyboardShownKey = SemanticsPropertyKey<Boolean>("KeyboardShownKey")
var SemanticsPropertyReceiver.keyboardShownProperty by KeyboardShownKey