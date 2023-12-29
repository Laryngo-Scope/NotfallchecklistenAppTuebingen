package com.example.notfallchecklistentuebingen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.couchbase.lite.*
import java.security.MessageDigest
import android.content.Context
import com.example.notfallchecklistenapptuebingen.utility.ReadJSONFromAssets
import com.example.notfallchecklistenapptuebingen.utility.checkliste
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    private val masterPasswordHash = "9cf0a15f121227e302a036a98d7a2de3d6a48722026bd92e2e8d13d7f63e3805"
   // private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //CouchbaseLite.init(applicationContext)
        // database = Database("ChecklistBase")

        setContent {
            var showEditor by remember { mutableStateOf(false) }
            var passwordEntered by remember { mutableStateOf(false) }
            var showError by remember { mutableStateOf(false) }
            var showMainMenu by remember { mutableStateOf(true) }
            var selectedChecklist by remember { mutableStateOf<String?>(null) }
            val jsonString = ReadJSONFromAssets(baseContext, "Notfallchecklisten.json")
            val data = Gson().fromJson(jsonString, checkliste::class.java)

            if (showEditor && passwordEntered) {
                var checklistItems by remember { mutableStateOf(mutableListOf<String>()) }
                LaunchedEffect(Unit) {
                    checklistItems = data.list
                }

                ChecklistEditorScreen(
                    checklist = checklistItems,
                    onSave = { items ->
                        saveChecklist(data.title, "Meine Checkliste", items)
                        showEditor = false
                        passwordEntered = false
                    }
                )
            } else if (showEditor) {
                PasswordScreen(
                    masterPasswordHash = masterPasswordHash,
                    onPasswordEntered = { isCorrect ->
                        passwordEntered = isCorrect
                        showError = !isCorrect
                    },
                    showError = showError
                )
            } else if (showMainMenu) {
                MainMenu(
                    data.title,
                    onChecklistSelected = { checklist ->
                        selectedChecklist = checklist
                        showMainMenu = false
                    }
                ) {
                    showEditor = true
                }
            } else {
                ChecklistScreen(data.title, selectedChecklist) {
                    showMainMenu = true
                }
            }
        }
    }

}

@Composable
fun PasswordScreen(masterPasswordHash: String, onPasswordEntered: (Boolean) -> Unit, showError: Boolean) {
    var password by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Passwort") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
        Button(
            onClick = {
                val inputHash = hash(password)
                val isCorrect = inputHash == masterPasswordHash
                onPasswordEntered(isCorrect)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Einloggen")
        }

        if (showError) {
            Text(
                "Falsches Passwort. Bitte versuchen Sie es erneut.",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun ChecklistEditorScreen(checklist: MutableList<String>, onSave: (List<String>) -> Unit) {
    val scrollState = rememberScrollState()
    var newItem by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text("Checklisten-Editor", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Anzeige der aktuellen Checklisten-Items
        for ((index, item) in checklist.withIndex()) {
            ChecklistItemEditor(
                item = item,
                onItemChanged = { newItem -> checklist[index] = newItem },
                onRemove = { checklist.removeAt(index) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Eingabefeld für neues Item
        OutlinedTextField(
            value = newItem,
            onValueChange = { newItem = it },
            label = { Text("Neues Item") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Button zum Hinzufügen eines neuen Items
        Button(
            onClick = {
                if (newItem.isNotBlank()) {
                    checklist.add(newItem)
                    newItem = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Hinzufügen")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button zum Speichern der Checkliste
        Button(
            onClick = { onSave(checklist) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Speichern")
        }
    }
}

@Composable
fun ChecklistItemEditor(item: String, onItemChanged: (String) -> Unit, onRemove: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = item,
            onValueChange = onItemChanged,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = onRemove) {
            Text("Entfernen")
        }
    }
}


@Composable
fun MainMenu(data, onChecklistSelected: (String) -> Unit, onOpenEditorClick: () -> Unit) {
    // Hier würden Sie Ihre Logik implementieren, um die Liste der Checklisten zu erhalten
    val checklists = data // Beispiel-Daten

    LazyColumn {
        items(checklists) { checklist ->
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text(checklist, modifier = Modifier.weight(1f))
                Button(onClick = { onChecklistSelected(checklist) }) {
                    Text("Öffnen")
                }
                Button(
                    onClick = {
                        // Rufen Sie die übergebene Funktion auf, um den Editor zu öffnen
                        onOpenEditorClick()
                    }
                ) {
                    Text("Editor öffnen")
                }
            }
        }
    }
}

@Composable
fun ChecklistScreen(data, checklistName: String?, onBack: () -> Unit) {
    // Hier würden Sie Ihre Logik implementieren, um die Inhalte der ausgewählten Checkliste zu erhalten
    val items = data

    Column {
        LazyColumn {
            items(items) { item ->
                Text(item, modifier = Modifier.padding(16.dp))
                // Implementieren Sie hier die Swipe-Logik
            }
        }
        Button(onClick = onBack) {
            Text("Zurück zum Hauptmenü")
        }
    }
}

fun loadChecklist(database: Database, title: String): MutableList<String> {
    val query = QueryBuilder
        .select(SelectResult.all())
        .from(DataSource.database(database))
        .where(Expression.property("type").equalTo(Expression.string("checklist"))
            .and(Expression.property("title").equalTo(Expression.string(title))))

    val result = query.execute()
    val items = mutableListOf<String>()
    for (row in result) {
        val dict = row.getDictionary(database.name)
        val checklistItems = dict?.getArray("items")
        if (checklistItems != null) {
            for (i in 0 until checklistItems.count()) {
                val itemDict = checklistItems.getDictionary(i)
                itemDict?.getString("description")?.let {
                    items.add(it)
                }
            }
        }
    }
    return items
}

fun saveChecklist(database: Database, title: String, items: List<String>) {
    val document = MutableDocument(title)
    document.setString("type", "checklist")
    document.setString("title", title)

    val itemsArray = MutableArray()
    for (item in items) {
        val itemDict = MutableDictionary()
        itemDict.setString("description", item)
        itemsArray.addDictionary(itemDict)
    }
    document.setArray("items", itemsArray)

    database.save(document)
}

fun hash(password: String): String {
    val bytes = password.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.fold("", { str, it -> str + "%02x".format(it) })
}

//fun savePasswordHash(context: Context, hash: String) {
//    val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
//    sharedPreferences.edit().putString("passwordHash", hash).apply()
//}

fun getPasswordHash(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("passwordHash", "") ?: ""
}