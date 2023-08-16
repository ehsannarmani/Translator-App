package ir.ehsan.asmrtranslator.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import  androidx.lifecycle.viewmodel.compose.viewModel
import ir.ehsan.asmrtranslator.R
import ir.ehsan.asmrtranslator.models.BaseModel
import ir.ehsan.asmrtranslator.ui.theme.SectionColor
import ir.ehsan.asmrtranslator.ui.theme.ubuntuFont
import kotlinx.coroutines.delay

data class Lang(
    @DrawableRes val icon: Int,
    val name: String,
    val symbol: String
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {

    val translation by viewModel.translation.collectAsState()

    val languages = listOf(
        Lang(name = "Persian", icon = R.drawable.iran, symbol = "fa"),
        Lang(name = "Turkish", icon = R.drawable.turkey, symbol = "tr"),
        Lang(name = "Chinese", icon = R.drawable.china, symbol = "chi"),
        Lang(name = "English", icon = R.drawable.united_states, symbol = "en")
    )
    val (source, setSource) = remember {
        mutableStateOf(languages.last())
    }
    val (target, setTarget) = remember {
        mutableStateOf(languages.first())
    }
    var sourceChange by remember {
        mutableStateOf(false)
    }
    var dialogOpen by remember {
        mutableStateOf(false)
    }

    if (dialogOpen) {
        Dialog(onDismissRequest = {
            dialogOpen = false
        }) {
            LazyVerticalGrid(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primary),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(12.dp)
            ) {
                items(languages) { language ->
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(SectionColor)
                            .aspectRatio(1f)
                            .clickable {
                                if (sourceChange) {
                                    setSource(language)
                                } else {
                                    setTarget(language)
                                }
                                dialogOpen = false
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier.size(60.dp),
                            painter = painterResource(id = language.icon),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                        Text(
                            text = language.name,
                            color = Color(0xff212121),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }


    val context = LocalContext.current
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val copyToClipboard: (String) -> Unit = {
        val clipData = ClipData.newPlainText(
            "Translated Text",
            it
        )
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(context, "Copied to Clipboard.", Toast.LENGTH_SHORT).show()
    }


    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text("Asmr Translator", color = Color.White, fontFamily = ubuntuFont)
                }
            )
        }
    ) { paddings ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddings.calculateTopPadding(),
                    end = 12.dp,
                    start = 12.dp
                )
        ) {
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            4.dp,
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                        .background(SectionColor)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.clickable {
                            sourceChange = true
                            dialogOpen = true
                        },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = source.icon),
                            contentDescription = source.name
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = source.name)
                    }
                    Icon(
                        modifier = Modifier.clickable {
                            val s = source
                            val t = target
                            setSource(t)
                            setTarget(s)
                        },
                        painter = painterResource(id = R.drawable.ic_swap),
                        contentDescription = null
                    )
                    Row(
                        modifier = Modifier.clickable {
                            sourceChange = false
                            dialogOpen = true
                        },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = target.name)
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = target.icon),
                            contentDescription = target.name
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .shadow(
                            4.dp,
                            shape = RoundedCornerShape(20.dp),

                            )
                        .clip(RoundedCornerShape(20.dp))
                        .background(SectionColor)
                        .padding(
                            vertical = 10.dp,
                            horizontal = 16.dp
                        )
                ) {
                    val (query, setQuery) = remember {
                        mutableStateOf("")
                    }
                    LaunchedEffect(query) {
                        delay(500)
                        if (query.isNotEmpty()) {
                            viewModel.translate(
                                query = query,
                                source = source.symbol,
                                target = target.symbol
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = source.name, color = MaterialTheme.colorScheme.primary)
                        Icon(
                            modifier = Modifier.clickable {
                                setQuery("")
                                viewModel.clear()
                            }, contentDescription = null,
                            imageVector = Icons.Default.Close
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    BasicTextField(
                        value = query,
                        onValueChange = {
                            setQuery(it)
                        },
                        textStyle = TextStyle(
                            fontFamily = ubuntuFont,
                            fontSize = 16.sp
                        )
                    ) {
                        val int = remember {
                            MutableInteractionSource()
                        }
                        if (query.isNotEmpty()) {
                            TextFieldDefaults.TextFieldDecorationBox(
                                value = query,
                                innerTextField = it,
                                enabled = true,
                                singleLine = false,
                                visualTransformation = VisualTransformation.None,
                                interactionSource = int,
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    textColor = Color(0xff212121)
                                ),
                                contentPadding = PaddingValues(0.dp)
                            )
                        } else {
                            Text(text = "Your Text...", color = Color.Gray)
                        }
                    }
                }

            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .shadow(
                            4.dp,
                            shape = RoundedCornerShape(20.dp),

                            )
                        .clip(RoundedCornerShape(20.dp))
                        .background(SectionColor)
                        .padding(
                            vertical = 10.dp,
                            horizontal = 16.dp
                        )
                ) {
                    Text(text = target.name)
                    Spacer(modifier = Modifier.height(16.dp))
                    when (val response = translation) {
                        is BaseModel.Loading -> {
                            Text(text = "Translating", color = Color.Gray)
                        }

                        is BaseModel.Success -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(1f),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = response.data.responseData.translatedText,
                                    color = Color(0xff212121),
                                    fontWeight = FontWeight.Bold
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Icon(
                                        modifier = Modifier.clickable {
                                            copyToClipboard(response.data.responseData.translatedText)
                                        },
                                        painter = painterResource(id = R.drawable.ic_copy),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }

                        is BaseModel.Error -> {
                            Text(text = response.error, color = Color.Gray)
                        }

                        null -> {
                            Text(text = "Translated Text...", color = Color.Gray)
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item{
                AnimatedVisibility(
                    visible = translation is BaseModel.Success,
                    enter = fadeIn()+ scaleIn(),
                    exit = fadeOut()+ scaleOut()
                ) {
                    runCatching {
                        translation as BaseModel.Success
                    }.onSuccess { data->
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            repeat(data.data.matches.count()){
                                val match = data.data.matches[it]
                                Column(modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(4.dp, shape = RoundedCornerShape(16.dp))
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                                    .clickable {
                                        copyToClipboard(match.translation)
                                    }
                                    .padding(12.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            modifier=Modifier.weight(.7f),
                                            text = match.translation,
                                            color = Color.White,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            modifier=Modifier.weight(.3f),
                                            text = "Match: ${match.match*100}%",
                                            color = Color.White,
                                            fontSize = 12.sp,
                                            textAlign = TextAlign.End
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = match.source + " to " + match.target,
                                        color = Color.Gray,
                                        fontSize = 10.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ){
                                        Text(
                                            text = "By: ${match.createdBy}",
                                            color = Color.White,
                                            fontSize = 12.sp
                                        )
                                        Text(
                                            text = "Usage Count: ${match.usageCount}",
                                            color = Color.Gray,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}