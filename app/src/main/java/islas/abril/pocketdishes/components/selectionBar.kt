package islas.abril.pocketdishes.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.data.recipeCategories
import islas.abril.pocketdishes.ui.theme.mainOrange

@Composable
fun selectionBar(text:String, showOnlyFavorites: Boolean, onFavoriteClick: () -> Unit,onCategorySelection:(String?)-> Unit) {
    val context = LocalContext.current
    var showFiltersDialog by remember { mutableStateOf(false) }
    var selectedCategory  by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.padding(top = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.labelMedium
        )
        Icon(
            painter = painterResource(
                id =
                    if(selectedCategory.isEmpty())
                        R.drawable.filter_alt_24px
                    else
                        R.drawable.filter_alt_off_24px

            ),
            contentDescription = "filter",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(35.dp)
                .clickable {
                    if (selectedCategory.isNotEmpty()) {
                        selectedCategory = ""
                        onCategorySelection(null)
                    } else {
                        showFiltersDialog = true
                    }
                }
        )

        Icon(
            painter = painterResource(
                id =
                    if (showOnlyFavorites)
                        R.drawable.favoritefilled_24
                    else
                        R.drawable.favorite_24px
            ),
            contentDescription = "heart",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .size(35.dp)
                .clickable {
                    onFavoriteClick()
                }
        )
    }
    if (showFiltersDialog) {

        val categories = recipeCategories
        AlertDialog(
            onDismissRequest  = { showFiltersDialog = false },
            containerColor    = MaterialTheme.colorScheme.background,
            shape             = RoundedCornerShape(20.dp),
            title = {
                Text("Select a category", color = mainOrange)
            },
            text = {
                LazyColumn(
                modifier = Modifier.heightIn(max = 300.dp)
                    ) {
                        items(categories) { category ->

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedCategory = category
                                        onCategorySelection(category)
                                        showFiltersDialog = false
                                    }
                                    .padding(vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                RadioButton(
                                    selected = selectedCategory == category,
                                    onClick = {
                                        selectedCategory = category
                                        onCategorySelection(category)
                                        showFiltersDialog = false
                                    }
                                )

                                Text(category)
                            }
                        }
                    }
                },
            confirmButton = {}
            )

        }
}