package com.fakhrulasa.jetpackcomposebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fakhrulasa.jetpackcomposebasics.network.PostData
import com.fakhrulasa.jetpackcomposebasics.network.Retrofitlient
import com.fakhrulasa.jetpackcomposebasics.ui.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    lateinit var postData: List<PostData>
    val vm:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.listData.observe(this,{
            setContent {
                MyApp(it)
            }
        })
    }
}

@Composable
private fun MyApp(postData: List<PostData>) {
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen(postData, onContinueClicked = { shouldShowOnboarding = false })
    } else {
        Greetings(postData)
    }
}

@Composable
private fun OnboardingScreen(postData: List<PostData>, onContinueClicked: () -> Unit) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                title = { Text(text = "Squishy Post") },
                backgroundColor = colorResource(id = R.color.purple_500),
                contentColor = Color.White
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Let's see the post's")
                Button(
                    modifier = Modifier
                        .padding(20.dp)
                        .background(colorResource(id = R.color.purple_500)),
                    onClick = onContinueClicked,

                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
                ) {

                    Text(
                        text = "CHECKOUT POST",
                        color = Color.White
                    )
                }
                Card(
                    backgroundColor = colorResource(id = R.color.teal_700),
                    modifier = Modifier.padding(20.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = 15.dp
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "Total posts available",
                            style = MaterialTheme.typography.h4.copy(
                                fontWeight = FontWeight.ExtraBold
                            ),
                            fontSize = 18.sp
                        )
                        Text(text = postData.size.toString(), fontSize = 30.sp)
                    }
                }
            }

        }
    }
}

@Composable
private fun Greetings(names: List<PostData>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TopAppBar(
            title = { Text(text = "Squishy Post") },
            backgroundColor = colorResource(id = R.color.purple_500),
            contentColor = Color.White
        )
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
            items(items = names) { name ->
                Greeting(name = name.title, msg = name.body, id = name.id)
            }
        }
    }

}

@Composable
private fun Greeting(name: String, msg: String, id: Int) {
    Card(
        backgroundColor = colorResource(id = R.color.purple_200),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name, msg, id)
    }
}

@Composable
private fun CardContent(name: String, msg: String, id: Int) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {

            Text(text = id.toString())
            Text(
                text = name,
                color = colorResource(id = R.color.dark_green),
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                fontSize = 18.sp
            )
            if (expanded) {
                Text(
                    text = msg,
                    color = colorResource(id = R.color.dark_green)
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }

            )
        }
    }
}

