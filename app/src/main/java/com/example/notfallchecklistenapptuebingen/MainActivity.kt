package com.example.notfallchecklistenapptuebingen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.notfallchecklistenapptuebingen.utility.ReadJSONFromAssets
import com.example.notfallchecklistenapptuebingen.utility.checkliste
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.math.abs

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var showMainMenu by remember { mutableStateOf(true) }
            var selectedChecklist by remember { mutableStateOf<checkliste?>(null) }
            // Read JSON file and put in variable/list
            val jsonString = ReadJSONFromAssets(baseContext, "Notfallchecklisten.json")
            val listchecklisteType = object : TypeToken<List<checkliste>>() {}.type
            val checklisten: List<checkliste> = Gson().fromJson(jsonString, listchecklisteType)
            // Get Screen size
            val configuration = LocalConfiguration.current
            val screenWidth = configuration.screenWidthDp.dp

            // Logic for navigation
            if (showMainMenu) {
                MainMenu(
                    checklisten,
                    onChecklistSelected = { checklist ->
                        selectedChecklist = checklist
                        showMainMenu = false
                    }
                )
            } else {
                selectedChecklist?.let {
                    ChecklistScreen(it, screenWidth.value) {
                        showMainMenu = true
                        selectedChecklist = null
                    }
                }
            }
        }
    }
}

@Composable
//Main Menu design. Get the List and output it as klickable options
fun MainMenu(checklisten: List<checkliste>, onChecklistSelected: (checkliste) -> Unit) {
    Column {
        Text(
            text = "Notfallchecklisten",
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineMedium
        )


        LazyColumn {
            items(checklisten) { checklist ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(checklist.title, modifier = Modifier.weight(1f))
                    Button(onClick = { onChecklistSelected(checklist) }) {
                        Text("Öffnen")
                    }
                }
            }
        }
    }
}

@Composable
// Make the checklist items "checkable" through swiping
fun ChecklistItem(item: String, screenWidth: Float) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var isActivated by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (isActivated) Color.Green else Color.Gray
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(backgroundColor)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX += delta
                    if (abs(offsetX) > screenWidth / 2) {
                        isActivated = offsetX > 0
                        offsetX = 0f
                    }
                },
                onDragStopped = {
                    offsetX = 0f
                }
            )
    ) {
        Text(text = item, modifier = Modifier.padding(16.dp))
    }
}

@Composable
// Get Checklists and output them as checkable/swipeable list elements
fun ChecklistScreen(checklist: checkliste, screenWidth: Float, onBack: () -> Unit) {
    Column {
        // Iterate over checklist elements
        checklist.list.forEach { item ->
            ChecklistItem(item = item, screenWidth = screenWidth)
        }
        // How to Use
        Text(
            text = "Wischen Sie nach links oder rechts, um Elemente zu aktivieren/deaktivieren.",
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
        )
        // Back to Main Menu Button
        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Text("Zurück zum Hauptmenü")
        }
    }
}
