package islas.abril.pocketdishes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.ui.theme.PocketDishesTheme
import islas.abril.pocketdishes.ui.theme.backgroundOrange
import islas.abril.pocketdishes.ui.theme.darkerGrey
import islas.abril.pocketdishes.ui.theme.lightGray
import islas.abril.pocketdishes.ui.theme.typoColorBeige
import islas.abril.pocketdishes.ui.theme.typoColorBrown

@Composable
fun textField(
    textFieldName:String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column() {
        Text(text = textFieldName,
                color = typoColorBrown,
                fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start=10.dp, top=5.dp, bottom=10.dp)
        )
    Box(
        modifier = Modifier
           // .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 15.dp, vertical = 15.dp),
        contentAlignment = Alignment.CenterStart

    ) {
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                color = typoColorBeige,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                color = typoColorBrown,
                fontSize = 22.sp
            ),
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.CenterStart)
        )
    }
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
fun combobox(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = label,
            color = typoColorBrown,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 10.dp)
        )

        Box {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { expanded = true }
                    .padding(horizontal = 15.dp, vertical = 15.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row() {
                    Text(
                        text = if (selectedOption.isEmpty()) "Select option"
                        else selectedOption,
                        color = if (selectedOption.isEmpty()) typoColorBeige else typoColorBrown,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_recipe),
                            contentDescription = "Add",
                            modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                        )
                }
            }
            // DROPDOWN
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option,
                                fontSize = 18.sp,
                                color = typoColorBrown
                            )
                        },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))
    }
}
@Preview (showBackground = false)
@Composable
fun previewTextField(){
    PocketDishesTheme() {
        textField("Name","", onValueChange = { }, "Hola")
    }
}
