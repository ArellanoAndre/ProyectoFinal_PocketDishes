package islas.abril.pocketdishes.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.size.Size
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.data.Recipe
import islas.abril.pocketdishes.ui.theme.secondaryGreen
import islas.abril.pocketdishes.viewmodel.PocketDishesViewModel

@Composable
fun moreButton(
    onEdit:()->Unit,
    onDelete:()->Unit,
    onShare:()->Unit,
    recipe: Recipe,
    authorName: String = recipe.author,
    viewModel: PocketDishesViewModel,
    color: Color,
    size: Int
    ) {
    var showMenu by remember { mutableStateOf(false) }
    val currentUser by viewModel.currentUser.collectAsState()

    Box(
        modifier = Modifier.size(42.dp),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            painter = painterResource(id = R.drawable.more_vert_24px),
            contentDescription = "more",
            tint = secondaryGreen,
            modifier = Modifier
                .size(size.dp)
                .clickable {
                    showMenu = true
                }
        )

        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = {
                showMenu = false
            }
        ) {

            val isOwner = authorName == currentUser?.name

            if (isOwner) {

                DropdownMenuItem(
                    text = { Text("Edit recipe") },
                    onClick = {
                        showMenu = false
                        onEdit()
                    }
                )

                DropdownMenuItem(
                    text = { Text("Delete recipe") },
                    onClick = {
                        showMenu = false
                        onDelete()
                    }
                )

            } else {

                DropdownMenuItem(
                    text = { Text("Share") },
                    onClick = {
                        showMenu = false
                        onShare()
                    }
                )
            }
        }
    }
}