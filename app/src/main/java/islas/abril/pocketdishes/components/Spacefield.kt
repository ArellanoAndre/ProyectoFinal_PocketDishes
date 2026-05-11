package islas.abril.pocketdishes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.ui.theme.typoColorBrown

@Composable
fun textField(
    textFieldName: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column {
        if (textFieldName.isNotEmpty()) {
            Text(
                text = textFieldName,
                color = MaterialTheme.colorScheme.outline,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 5.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .border(1.dp, Color.Transparent, RoundedCornerShape(10.dp))
                .padding(horizontal = 15.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = typoColorBrown,
                    fontSize = 14.sp
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
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
        if (label.isNotEmpty()) {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.outline,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 5.dp)
            )
        }

        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .clickable { expanded = true }
                    .padding(horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (selectedOption.isEmpty()) "Select option" else selectedOption,
                    color = if (selectedOption.isEmpty()) Color.LightGray else typoColorBrown,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_recipe),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .background(Color.White)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option,
                                color = typoColorBrown,
                                fontSize = 14.sp
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
        Spacer(modifier = Modifier.height(12.dp))
    }
}