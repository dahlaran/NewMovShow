package com.dahlaran.newmovshow.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun BottomNavBar(
    itemList: List<BottomNavItem>,
    modifier: Modifier,
    selectedItemIndex: Int,
    onItemClick: (BottomNavItem) -> Unit
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        itemList.forEachIndexed { index, item ->
            val isSelected = selectedItemIndex == index
            NavigationBarItem(
                icon = {
                    NavigationIcon(item, isSelected)
                },
                label = {
                    Text(text = item.name, textAlign = TextAlign.Center)
                },
                selected = isSelected,
                onClick = {
                    onItemClick(item)
                },
            )
        }
    }
}

@Composable
private fun NavigationIcon(item: BottomNavItem, isSelected: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (item.badgeCount > 0) {
            BadgedBox(badge = {
                Badge {
                    Text(text = item.badgeCount.toString())
                }
            }) {
                Icon(
                    imageVector = if (isSelected) item.imageSelected else item.imageNotSelected,
                    contentDescription = item.name
                )
            }
        } else {
            Icon(
                imageVector = if (isSelected) item.imageSelected else item.imageNotSelected,
                contentDescription = item.name
            )
        }
    }
}