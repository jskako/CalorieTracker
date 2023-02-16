package com.jskako.tracker_presentation.tracker_overview.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import com.jskako.core_ui.LocalSpacing
import com.jskako.tracker_presentation.components.NutrientInfo
import com.jskako.tracker_presentation.components.UnitDisplay
import com.jskako.tracker_presentation.tracker_overview.Meal

@Composable
fun ExpandableMeal(
    meal: Meal,
    onToggleClick: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleClick() }
                .padding(spacing.spaceMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = meal.drawableRes),
                contentDescription = meal.name.asString(context)
            )
            Spacer(modifier = Modifier.width(spacing.spaceMedium))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = meal.name.asString(context),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Icon(
                        imageVector = if (meal.isExpanded) {
                            Icons.Default.KeyboardArrowUp
                        } else Icons.Default.KeyboardArrowDown,
                        contentDescription = if(meal.isExpanded) {
                            stringResource(id = com.jskako.core.R.string.collapse)
                        } else stringResource(id = com.jskako.core.R.string.extend)
                    )
                }
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    UnitDisplay(
                        amount = meal.calories,
                        unit = stringResource(id = com.jskako.core.R.string.kcal),
                        amountTextSize = 30.sp
                    )
                    Row {
                        NutrientInfo(
                            name = stringResource(id = com.jskako.core.R.string.carbs),
                            amount = meal.carbs,
                            unit = stringResource(id = com.jskako.core.R.string.grams)
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))
                        NutrientInfo(
                            name = stringResource(id = com.jskako.core.R.string.protein),
                            amount = meal.protein,
                            unit = stringResource(id = com.jskako.core.R.string.grams)
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))
                        NutrientInfo(
                            name = stringResource(id = com.jskako.core.R.string.fat),
                            amount = meal.fat,
                            unit = stringResource(id = com.jskako.core.R.string.grams)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        AnimatedVisibility(visible = meal.isExpanded) {
            content()
        }
    }
}